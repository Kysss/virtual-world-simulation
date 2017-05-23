/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc375hw2.part1;

import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;
import java.util.HashMap;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
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
    public static class Course {

        private String courseLabel;
        private String courseName;
        private String courseCRN;
        private int courseCredit;
        private String courseTime;
        private int totalSeats;
        private int availableSeats;
        private int seatsTaken;

    }

    public static Course returnCourse(String cl, String cn, String CRN, int cc, String ct, int ts, int as) {
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

    public void incrementOpenSeats(Course c) {
        c.availableSeats = getAvailableSeats(c) + 1;
        c.seatsTaken = getSeatsTaken(c) - 1;
    }

    public void decrementOpenSeats(Course c) {
        c.availableSeats = getAvailableSeats(c) - 1;
        c.seatsTaken = getSeatsTaken(c) + 1;
    }

    public String toString(Course c) {
        return "\n" + "Course: " + getLabel(c) + " - " + getCourseName(c) + "\n"
                + "CRN: " + getCRN(c) + "\n"
                + "Time: " + getScheduleTime(c) + "\n"
                + "Total Seats: " + getTotalSeats(c) + "\n"
                + "Seats Taken: " + getSeatsTaken(c) + "\n"
                + "Available Seats: " + getAvailableSeats(c) + "\n";

    }

    @State(Scope.Thread)
    public static class WriterPreferenceRWL {

        private final ReentrantLock lock = new ReentrantLock();
        // private int currentWriters;
        private boolean writing;
        private int readers;
        private int writers;
        private int pendingReaders;
        private int pendingWriters;
        final private Condition canWrite = lock.newCondition();
        final private Condition canRead = lock.newCondition();
    }

    public void readLock(WriterPreferenceRWL rwl) {
        rwl.lock.lock();
        try {
            while (rwl.writing && rwl.pendingReaders > 0) {
                ++rwl.pendingReaders;
                try {
                    rwl.canRead.await();
                } catch (InterruptedException ex) {
                    rwl.canRead.signal();
                } finally {
                    --rwl.pendingReaders;
                }
            }
            ++rwl.readers;
        } finally {
            rwl.lock.unlock();
        }
    }

    public void writeLock(WriterPreferenceRWL rwl) {
        rwl.lock.lock();
        try {
            while (rwl.writing || rwl.readers > 0) {
                ++rwl.pendingWriters;
                try {
                    rwl.canWrite.await();
                } catch (InterruptedException ex) {
                    rwl.canWrite.signal();
                } finally {
                    --rwl.pendingWriters;
                }
            }
            rwl.writing = true;
        } finally {
            rwl.lock.unlock();
        }
    }

    public void unlockRead(WriterPreferenceRWL rwl) {
        rwl.lock.lock();
        try {
            if (--rwl.readers == 0) {
                if (rwl.pendingWriters == 0) {
                    rwl.canWrite.signal();
                }
            } else {
                rwl.canRead.signalAll();
            }
        } finally {
            rwl.lock.unlock();
        }
    }

    public void unlockWrite(WriterPreferenceRWL rwl) {
        rwl.lock.lock();
        try {
            rwl.writing = false;
            if (rwl.pendingWriters > 0) {
                rwl.canWrite.signal();
            } else {
                rwl.canRead.signalAll();
            }

        } finally {
            rwl.lock.unlock();
        }
    }

    @State(Scope.Benchmark)
    public static class CourseSystem {

        public final WriterPreferenceRWL rwl = new WriterPreferenceRWL();
        final HashMap<String, Course> database = new HashMap<String, Course>();

        @Setup(Level.Iteration)
        public void prepare() {

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
        writeLock(cs.rwl);
        try {
            //means to register, -1;
            boolean updated = false;
            if (indicator == -1) {
                readLock(cs.rwl);
                try {
                    if (getAvailableSeats(cs.database.get(CRN)) != 0) {
                        decrementOpenSeats(cs.database.get(CRN));
                        //System.out.println("----- OPERATION SUCCEED -----" + "\n" + database.get(CRN).toString());
                        updated = true;
                    } else {
                        //System.out.println("----- OPERATION FAILED -----" + "\n" + database.get(CRN).toString());
                        updated = false;
                    }
                } finally {
                    unlockRead(cs.rwl);
                }
            }
            //else means to unregister +1
            if (indicator == 1) {
                readLock(cs.rwl);
                try {
                    incrementOpenSeats(cs.database.get(CRN));
                    // System.out.println("----- OPERATION SUCCEED-----" + "\n" + database.get(CRN).toString());
                    updated = true;
                } finally {
                    unlockRead(cs.rwl);
                }
            }
            return updated;
        } finally {
            unlockWrite(cs.rwl);
        }

    }

    public void csread(CourseSystem cs, String CRN) {
        readLock(cs.rwl);
        try {
          //  System.out.println("----- READING: -----" + "\n" + database.get(CRN).toString());
            toString(cs.database.get(CRN));
        } finally {
            unlockRead(cs.rwl);
        }
    }

    @State(Scope.Group)
    @Threads(2)
    public static class Student {

        //private final String name;
        private final CourseSystem cs = new CourseSystem();
        private boolean done = false;
        private final HashMap<String, Integer> registeredClasses = new HashMap<String, Integer>();
        private final ReentrantLock lock = new ReentrantLock();
        private int count = 0;
        private final double updateProbability = 0.2;
        private final Random random = new Random();

    }

    public String randomCRNfromDatabase(CourseSystem cs, Student s) {
        List<String> keys = new ArrayList<String>(cs.database.keySet());
        return keys.get(s.random.nextInt(keys.size()));
    }

    @Benchmark
    @Group("part1")
    @GroupThreads(1)
    public void update(CourseSystem cs, Student s) {
        s.lock.lock();
        try {
            String randomCRN = randomCRNfromDatabase(cs, s);
            //System.out.println("randomCRN:" + randomCRN);
            if (s.registeredClasses.containsKey(randomCRN)) {
                //   registeredClasses.remove(randomCRN);
                unregister(cs, s, randomCRN);

                //   System.out.println(registeredClasses);
            } else {
                //    registeredClasses.add(randomCRN);
                register(cs, s, randomCRN);

                //     System.out.println(registeredClasses);
            }
        } finally {
            s.lock.unlock();
        }
    }

    @Group("part1")
    public void register(CourseSystem cs, Student s, String CRN) {
        s.lock.lock();
        try {
            // System.out.println(this.getName() + "is registering  " + CRN);
            if (csupdate(cs, CRN, -1) == true) {
                if (!s.registeredClasses.containsKey(CRN)) {
                    s.registeredClasses.putIfAbsent(CRN, 1);
                }
            }
        } finally {
            s.lock.unlock();
        }
    }

    @Group("part1")
    public void unregister(CourseSystem cs, Student s, String CRN) {
        s.lock.lock();
        try {
            //    System.out.println(this.getName() + " is ungistering****************************" + CRN);
            if (csupdate(cs, CRN, 1) == true) {
                if (s.registeredClasses.containsKey(CRN)) {
                    s.registeredClasses.remove(CRN);
                }
            }
        } finally {
            s.lock.unlock();
        }
    }

    @Benchmark
    @Group("part1")
    @GroupThreads(1)
    public void read(CourseSystem cs, Student s) {
        s.lock.lock();
        try {
            String randomCRN = randomCRNfromDatabase(cs, s);
            // System.out.println(this.getName() + " is reading" + randomCRN);
            csread(cs, randomCRN);
        } finally {
            s.lock.unlock();
        }
    }

}
