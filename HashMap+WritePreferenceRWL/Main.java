/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc375hw2.part1;

import java.util.HashMap;

/**
 *
 * @author Yingying Xia
 */
public class Main {

    public static void main(String[] args) {
        CourseSystem cs = new CourseSystem();
     //   Course c = new Course("CSC375", "Parallel Programming", "12345", 3, "T TH 9:35AM", 20, 10);
        // System.out.println(c);
        HashMap<String, Student> hm = new HashMap<String, Student>();
        Student avery = new Student("avery", cs);
        Student bob = new Student("bob", cs);
        Student chris = new Student("chris", cs);
        Student dolma = new Student("dolma", cs);
        Student edison = new Student("edison", cs);
        hm.put(avery.getName(), avery);
        hm.put(bob.getName(), bob);
        hm.put(chris.getName(), chris);
        hm.put(dolma.getName(), dolma);
        hm.put(edison.getName(), edison);
      
        for (Student x : hm.values()) {
            x.start();
        }

    }
}
