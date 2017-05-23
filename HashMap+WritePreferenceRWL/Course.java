/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc375hw2.part1;

import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Yingying Xia
 */
public class Course {

    private String courseLabel;
    private String courseName;
    private String courseCRN;
    private int courseCredit;
    private String courseTime;
    private final int totalSeats;
    private int availableSeats;
    private int seatsTaken;
    private ReentrantLock lock = new ReentrantLock();

    public Course(String cl, String cn, String CRN, int cc, String ct, int ts, int as) {
        courseLabel = cl;
        courseName = cn;
        courseCRN = CRN;
        courseCredit = cc;
        courseTime = ct;
        totalSeats = ts;
        availableSeats = as;
        seatsTaken = totalSeats - availableSeats;
    }

    public String getLabel() {
        lock.lock();
        try {
            return courseLabel;
        } finally {
            lock.unlock();
        }
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCRN() {
        return courseCRN;
    }

    public int getCredit() {
        return courseCredit;
    }

    public  String getScheduleTime() {
        return courseTime;
    }

    public  int getTotalSeats() {
        return totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public  int getSeatsTaken() {
        return seatsTaken;
    }

    public synchronized void incrementOpenSeats() {
        lock.lock();
        try {
            this.availableSeats = this.getAvailableSeats() + 1;
            this.seatsTaken = this.getSeatsTaken() - 1;
        } finally {
            lock.unlock();
        }
    }

    public synchronized void decrementOpenSeats() {
        lock.lock();
        try {
            this.availableSeats = this.getAvailableSeats() - 1;
            this.seatsTaken = this.getSeatsTaken() + 1;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return "\n" + "Course: " + this.getLabel() + " - " + this.getCourseName() + "\n"
                + "CRN: " + this.getCRN() + "\n"
                + "Time: " + this.getScheduleTime() + "\n"
                + "Total Seats: " + this.getTotalSeats() + "\n"
                + "Seats Taken: " + this.getSeatsTaken() + "\n"
                + "Available Seats: " + this.getAvailableSeats() + "\n";

    }
}
