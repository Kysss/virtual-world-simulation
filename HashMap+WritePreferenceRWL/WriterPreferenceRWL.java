/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc375hw2.part1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yingying Xia
 */
public class WriterPreferenceRWL {

    private final ReentrantLock lock = new ReentrantLock();
   // private int currentWriters;
    private boolean writing;
    private int readers;
    private int writers;
    private int pendingReaders;
    private int pendingWriters;
    final private Condition canWrite = lock.newCondition();
    final private Condition canRead = lock.newCondition();

    public void readLock() {
        lock.lock();
        try {
            while (writing && pendingReaders > 0) {
                ++pendingReaders;
                try {
                    canRead.await();
                } catch (InterruptedException ex) {
                    canRead.signal();
                } finally {
                    --pendingReaders;
                }
            }
            ++readers;
        } finally {
            lock.unlock();
        }
    }

    public void writeLock() {
        lock.lock();
        try {
            while (writing || readers > 0) {
                ++pendingWriters;
                try {
                    canWrite.await();
                } catch (InterruptedException ex) {
                    canWrite.signal();
                } finally {
                    --pendingWriters;
                }
            }
            writing = true;
        } finally {
            lock.unlock();
        }
    }

    public void unlockRead() {
        lock.lock();
        try {
            if (--readers == 0) {
                if (pendingWriters == 0) {
                    canWrite.signal();
                }
            } else {
                canRead.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }

    public void unlockWrite() {
        lock.lock();
        try {
            writing = false;
            if (pendingWriters > 0) {
                canWrite.signal();
            } else {
                canRead.signalAll();
            }

        } finally {
            lock.unlock();
        }
    }
}
