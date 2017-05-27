import java.util.Random;
abstract class Creature implements Comparable<Creature> {

    protected String name;
    protected String description;
    protected Room room;
    
    Creature() {
    }

    public Creature(String n, String d) {
        name = n;
        description = d;
    }

    public String toString() {
        return "Creature " + name + "," + description;
    }

    protected abstract void react(String s, PC pc);

    protected abstract String preference();

    protected abstract String type();

    public void discontent(PC pc) {
    }

    ;
public void gladness(PC pc) {
    }
//public Creature findCreature(String creaturename) {
//        Creature find = null;
//        for (int i = 0; i < room.Creatures.length; i++) {
//            if (room.Creatures[i] != null && room.Creatures[i].name.equals(creaturename)) {
//                find = room.Creatures[i];
//                break;
//            } else {
//                find = null;
//            }
//        }
//        return find;
//    }

    ;
@Override
    public int compareTo(Creature o) {
        int compare;
        if (this.name.compareTo(o.name) > 0) {
            compare = 1;
        } else if (this.name.compareTo(o.name) == 0) {
            compare = 0;
        } else {
            compare = -1;

        }
        return compare;
    }

    public void clean(Room room, PC pc) {
        // all creatures can clean the room they are in. 
        //room conditions -different cases
        if (room.getState().equalsIgnoreCase("dirty")) {
            room.changingState("half-dirty", pc);

        } else if (room.getState().equalsIgnoreCase("half-dirty")) {
            room.changingState("clean", pc);

        } else if (room.getState().equalsIgnoreCase("clean")) {
            room.changingState("clean", pc);
        }

        // once the state is changed, notify all creatures in the room immediately.
    }

    public void dirty(Room room, PC pc) {
        // all creatures can dirty the room.
        //conditions
        if (room.getState().equalsIgnoreCase("dirty")) {
            room.changingState("dirty", pc);
        } else if (room.getState().equalsIgnoreCase("half-dirty")) {
            room.changingState("dirty", pc);

        } else if (room.getState().equalsIgnoreCase("clean")) {
            room.changingState("half-dirty", pc);

        }
        // once the state is changed, notify all creatures in the room immediately.

    }

    // move into a specified room.
    public void changeRoom(Room r) {
        if (r.top < 10) {
            String temp = room.getName();
            room.removeCreature(this);
            room = r;
            r.addCreature(this);
            this.room = r;
            System.out.println(this.name + " the " + this.type() + " left the room " + temp + " and changed into room " + r.getName());
            if(!this.type().equalsIgnoreCase("PC")){
                  this.perform();
            }
        } else {
            changeRoom();
        }
    }

//     Creature Perform (actions) in room that they dont like
    public void perform() {

        if ((!room.getState().equalsIgnoreCase(this.preference()))
                && (!room.getState().equalsIgnoreCase("half-dirty"))) {
            System.out.println(this.name + " does not like the state of room " + this.room.getName());
            if (this.preference().equalsIgnoreCase("clean")) {
                System.out.println(this.name + " cleaned the room "+ this.room.getName()+ " to make itself more comfortable.");
            } else if (this.preference().equalsIgnoreCase("dirty")) {
                System.out.println(this.name + " dirtied the room "+ this.room.getName()+ " to make itself more comfortable.");
            } else {
                
            }
            room.changingState(this.preference());

        }
    }

//enter neighbor room in random
    public void changeRoom() {
        Random random = new Random();
        int r = random.nextInt(4); //0,1,2,3 
        if ((room.north != null && room.north.top < 10) || (room.south != null && room.south.top < 10)
                && (room.east != null && room.east.top < 10) || (room.west != null && room.west.top < 10)) {
            if (r == 0) {
                if (room.north != null && room.north.top < 10) {
                    this.changeRoom(room.north);
                    this.perform();

                } else {
                    this.changeRoom();
                }
            } else if (r == 1) {
                if (room.south != null && room.south.top < 10) {
                    this.changeRoom(room.south);
                    this.perform();
                } else {
                    this.changeRoom();
                }
            } else if (r == 2) {
                if (room.east != null && room.east.top < 10) {
                    this.changeRoom(room.east);
                    this.perform();
                } else {
                    this.changeRoom();
                }
            } else if (r == 3) {
                if (room.west != null && room.west.top < 10) {
                    this.changeRoom(room.west);
                    this.perform();
                } else {
                    this.changeRoom();
                }
            }
            

        } else {
            disappear(this);
        }

    }

    public void look() {
        System.out.println(this.room);
    }

    public void disappear(Creature a) {
        //some conditions here. if the second time a creatures tries to enter an adjacent room and the room is full.
        //it will disappear completely.
        System.out.println(this.name + " the " + this.type() + "  angrily drills a hole in the ceiling and crawls out of the house.");
        room.removeCreature(a);
    }

}