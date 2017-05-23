/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc375hw2.part1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Yingying Xia
 */
public class Student extends Thread {

    private final String name;
    private final CourseSystem cs;
    private boolean done = false;
    private final HashMap<String,Integer> registeredClasses = new HashMap<String,Integer>();
    private final ReentrantLock lock = new ReentrantLock();
    private int count = 0;
    private final double updateProbability = 0.2;
    private final Random random = new Random();

    public Student(String n, CourseSystem courseSystem) {
        this.cs = courseSystem;
        this.name = n;

    }

    @Override
    public void run() {
        while (done == false) {
            try {
                if (count <= 100) {
                    if (random.nextDouble() <= updateProbability) {
                        update();
                    } else {
                        read();
                    }

                    count += 1;
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public String randomCRNfromDatabase() {
        List<String> keys = new ArrayList<String>(cs.database.keySet());
        return keys.get(random.nextInt(keys.size()));
    }

    public void update() {
        lock.lock();
        try {
            String randomCRN = randomCRNfromDatabase();
            System.out.println("randomCRN:" + randomCRN);
            if (registeredClasses.containsKey(randomCRN)) { 
             //   registeredClasses.remove(randomCRN);
                unregister(randomCRN);
               
                System.out.println(registeredClasses);
            } else { 
            //    registeredClasses.add(randomCRN);
                register(randomCRN);
               
                System.out.println(registeredClasses);
            }
        } finally {
            lock.unlock();
        }
    }

    public void register(String CRN) {
        lock.lock();
        try {
            System.out.println(this.getName() + "is registering  " + CRN);
            if(cs.update(CRN, -1)==true){
                registeredClasses.putIfAbsent(CRN,1);
            }
        } finally {
            lock.unlock();
        }
    }

    public void unregister(String CRN) {
        lock.lock();
        try {
            System.out.println(this.getName() + " is ungistering****************************" + CRN);
            if(cs.update(CRN, 1)==true){
                registeredClasses.remove(CRN);
            }
        } finally {
            lock.unlock();
        }
    }

    public void read() {
        lock.lock();
        try {
            String randomCRN = randomCRNfromDatabase();
            System.out.println(this.getName() + " is reading" + randomCRN);
            cs.read(randomCRN);
        } finally {
            lock.unlock();
        }
    }
}
