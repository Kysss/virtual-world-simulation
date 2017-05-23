/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc375hw2.part2;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @author Yingying Xia
 */
public class CourseSystem {

    //public final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    final ConcurrentHashMap<String, Course> database = new ConcurrentHashMap<String, Course>();
//    Course c = new Course("CSC375", "Parallel Programming", "12345", 3, "T TH 9:35AM", 20, 20);
//    Course d = new Course("CSC385", "Software Quality", "19196", 3, "M W F 2:50PM", 20, 20);
//    Course e = new Course("CSC416", "Artificial Intelligence", "15943", 3, "M W F 9:10AM", 20, 20);

    //  File file = new File()
    public CourseSystem() {
        setUp();
    }

    public void setUp() {
        Course c = new Course("CSC375", "Parallel Programming", "12345", 3, "T TH 9:35AM", 20, 20);
        Course d = new Course("CSC385", "Software Quality", "19196", 3, "M W F 2:50PM", 20, 20);
        Course e = new Course("CSC416", "Artificial Intelligence", "15943", 3, "M W F 9:10AM", 20, 20);
        database.putIfAbsent(c.getCRN(), c);
        database.putIfAbsent(d.getCRN(), d);
        database.putIfAbsent(e.getCRN(), e);
    }

    public boolean update(String CRN, int indicator) {
        boolean updated = false;
        //means to register, -1;
        if (indicator == -1) {
            if (database.get(CRN).getAvailableSeats() != 0) {
                database.get(CRN).decrementOpenSeats();
                System.out.println("----- OPERATION SUCCEED -----" + "\n" + database.get(CRN).toString());
                updated = true;
            } else {
                System.out.println("----- OPERATION FAILED -----" + "\n" + database.get(CRN).toString());
                updated = false;
            }
        }
        //else means to unregister +1
        if (indicator == 1) {
            database.get(CRN).incrementOpenSeats();
            System.out.println("----- OPERATION SUCCEED-----" + "\n" + database.get(CRN).toString());
            updated = true;
        }
        return updated;
    }

    public void read(String CRN) {
        System.out.println("----- READING: -----" + "\n" + database.get(CRN).toString());

    }
}
