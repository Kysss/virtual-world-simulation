/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc375hw2.part2;

import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 3, timeUnit = TimeUnit.SECONDS)
@Fork(5)


public class MyBenchmark {

    @State(Scope.Thread)
	public static class Course{

    private String courseLabel;
    private String courseName;
    private String courseCRN;
    private int courseCredit;
    private String courseTime;
    private int totalSeats;
    private int availableSeats;
    private int seatsTaken;


}

public static Course returnCourse(String cl, String cn, String CRN, int cc, String ct, int ts, int as){
	Course c = new Course();
	c.courseLabel = cl;
	c.courseName = cn;
	c.courseCRN = CRN;
	c.courseCredit = cc;
	c.courseTime = cl;
	c.totalSeats = ts;
	c.availableSeats = as;
	c.seatsTaken = c.totalSeats - c.availableSeats;
	return c;
}

public String getLabel(Course c) {
        return c.courseLabel;
    }

    public String getCourseName(Course c) {
        return c.courseName;
    }

    public static String getCRN(Course c) {
        return c.courseCRN;
    }

    public int getCredit(Course c) {
        return c.courseCredit;
    }

    public String getScheduleTime(Course c) {
        return c.courseTime;
    }

    public int getTotalSeats(Course c) {
        return c.totalSeats;
    }

    public int getAvailableSeats(Course c) {
        return c.availableSeats;
    }

    public int getSeatsTaken(Course c) {
        return c.seatsTaken;
    }

    public  void incrementOpenSeats(Course c) {
        c.availableSeats = getAvailableSeats(c) + 1;
        c.seatsTaken = getSeatsTaken(c) - 1;
    }

    public  void decrementOpenSeats(Course c) {
        c.availableSeats = getAvailableSeats(c) - 1;
        c.seatsTaken = getSeatsTaken(c) + 1;
    }

    
    public  String toString(Course c) {
        return "\n" + "Course: " + getLabel(c) + " - " + getCourseName(c) + "\n"
                + "CRN: " + getCRN(c) + "\n"
                + "Time: " + getScheduleTime(c) + "\n"
                + "Total Seats: " + getTotalSeats(c) + "\n"
                + "Seats Taken: " + getSeatsTaken(c) + "\n"
                + "Available Seats: " + getAvailableSeats(c) + "\n";

    }

 @State(Scope.Benchmark)
public static class CourseSystem{

 //public final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
 final ConcurrentHashMap<String, Course> database = new ConcurrentHashMap<String, Course>();
private final Random random = new Random();
	
@Setup(Level.Iteration)
public void prepare(){


Course c = returnCourse("CSC375", "Parallel Programming", "12345", 3, "T TH 9:35AM", 20, 20);
Course d = returnCourse("CSC385", "Software Quality", "19196", 3, "M W F 2:50PM", 20, 20);
Course e = returnCourse("CSC416", "Artificial Intelligence", "15943", 3, "M W F 9:10AM", 20, 20);
database.putIfAbsent(getCRN(c), c);
database.putIfAbsent(getCRN(d), d);
database.putIfAbsent(getCRN(e), e);
System.out.println("Putting in data");

}
}




public boolean csupdate(CourseSystem cs, String CRN, int indicator) {
        boolean updated = false;
        //means to register, -1;
        if (indicator == -1) {
            if (getAvailableSeats(cs.database.get(CRN)) != 0) {
                decrementOpenSeats(cs.database.get(CRN));
                //System.out.println("----- OPERATION SUCCEED -----" + "\n" + database.get(CRN).toString());
                updated = true;
            } else {
               // System.out.println("----- OPERATION FAILED -----" + "\n" + database.get(CRN).toString());
                updated = false;
            }
        }
        //else means to unregister +1
        if (indicator == 1) {
            incrementOpenSeats(cs.database.get(CRN));
            //System.out.println("----- OPERATION SUCCEED-----" + "\n" + database.get(CRN).toString());
            updated = true;
        }
        return updated;
    }


    public void csread(CourseSystem cs, String CRN) {
      //  System.out.println("----- READING: -----" + "\n" + 
       //toString(database.get(CRN)));
toString(cs.database.get(CRN));

    }





@State(Scope.Group)
@Threads (2)
public static class Student{

   // private final String name;
    private final CourseSystem cs =new CourseSystem();
    private boolean done = false;
 //   private final List<String> registeredClasses = new ArrayList<String>();
    private final ConcurrentHashMap<String, Integer> registeredClasses = new ConcurrentHashMap<String, Integer>();
    private int count = 0;
    private final double updateProbability = 0.2;
    private final Random random = new Random();


}
//public void update(Student thread){
//String randomCRN = thread.randomCRNfromDatabase();
//if(thread.registeredClasses.contains(randomCRN)){
//thread.unregister(randomCRN);
//}
//}


@Group("part2")
public String randomCRNfromDatabase(CourseSystem cs, Student s){
	List<String> keys = new ArrayList<String>(cs.database.keySet());
        return keys.get(s.random.nextInt(keys.size()));
}

@Benchmark
@Group("part2")
@GroupThreads (1)
public void update(CourseSystem cs, Student s) {
        String randomCRN = randomCRNfromDatabase(cs,s);
      //  System.out.println("randomCRN:" + randomCRN);
        if (s.registeredClasses.containsKey(randomCRN)) {
            unregister(cs,s,randomCRN);
         
            //System.out.println(registeredClasses);
        } else {
            register(cs,s,randomCRN);
         
          //  System.out.println(registeredClasses);
        }
    }


@Group("part2")
    public void register(CourseSystem cs,Student s, String CRN) {
       // System.out.println(this.getName() + "is registering  " + CRN);
        if(csupdate(cs, CRN, -1) == true){
		if(!s.registeredClasses.containsKey(CRN)){
            s.registeredClasses.putIfAbsent(CRN,1);
        }
}
        
    }


@Group("part2")
    public void unregister(CourseSystem cs, Student s,String CRN) {
      //  System.out.println(this.getName() + " is ungistering****************************" + CRN);
        if(csupdate(cs,CRN,1)){
		if(s.registeredClasses.contains(CRN)){
            s.registeredClasses.remove(CRN);
}
        }
    }

@Benchmark
@Group("part2")
@GroupThreads (1)
    public void read(CourseSystem cs, Student s) {
        
       // System.out.println(this.getName() + "is reading.");
        String randomCRN = randomCRNfromDatabase(cs, s);
        csread(cs,randomCRN);
    }


}
