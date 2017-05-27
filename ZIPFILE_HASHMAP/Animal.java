import java.util.Random;


public class Animal extends Creature {
    
    public Animal(String an, String ad) {
        super(an, ad);
    }
    
//preferred action
    protected String preference(){
        return "clean";
    }
    protected String type (){
        return "Animal";
    }
    
    @Override
    public String toString() {
        return "Animal:" + name + "," + description;
    }

    @Override
    public void discontent(PC pc) {
        // grumble or growl
       
        System.out.println(this.name + " the "+ this.type()+ " growls at you.");
        if (room.getState().equals("dirty")) {
            changeRoom();
        }
        pc.respect--;

    }

    public void gladness(PC pc) {
        // smile or lickFace
        System.out.println(this.name +" the "+ this.type() + " licks your face.");
        pc.respect++;
    }

    // react should be continued....and fixed...
    @Override
    protected void react(String s, PC pc) {
        //animal only stays in room that is clean or half-dirty
        //if the room is dirty, animal leaves the room and enter a neighbor room

        if (this.room.statebeforechange.equalsIgnoreCase("half-dirty")) {
            if (this.room.getState().equalsIgnoreCase("dirty")) {
                
                discontent(pc);

            } else if (this.room.getState().equalsIgnoreCase("clean")) {
                gladness(pc);

            }
        } else if (this.room.statebeforechange.equalsIgnoreCase("dirty")) {
            if (this.room.getState().equalsIgnoreCase("half-dirty")) {
                gladness(pc);

            } else if (this.room.getState().equalsIgnoreCase("dirty")) {
                changeRoom();
                System.out.println(this.name + " the "+ this.type()+ " growls at you.");
            }
        } else if (this.room.statebeforechange.equalsIgnoreCase("clean")) {
            if (this.room.getState().equalsIgnoreCase("half-dirty")) {
                discontent(pc);
            } else if (this.room.getState().equalsIgnoreCase("clean")) {
                System.out.println(this.name +" the " +this.type() +" licks your face.");
            }
        }
//        if (s.equalsIgnoreCase("dirty")){
//            discontent(pc);
//        } else {
//            gladness(pc);
//        }
    }

    
    public void changeRoom(PC pc) {
        Random random = new Random();
        int r = random.nextInt(4); //0,1,2,3 
        if (!(room.north == null && room.east == null && room.west == null && room.south == null)
                && !(room.north.top >= 10 && room.east.top >= 10 && room.west.top >= 10 && room.south.top >= 10)) {
            if (r == 0) {
                if (room.north != null && room.north.top < 10) {
                    this.changeRoom(room.north);
                    if (room.north.getState().equalsIgnoreCase("dirty")) {
                        room.north.changingState("clean", pc);
                    }
                } else {
                    this.changeRoom();
                }
            }
        } else if (r == 1) {
            if (room.south != null && room.south.top < 10) {
                this.changeRoom(room.south);
                if (room.south.getState().equalsIgnoreCase("dirty")) {
                    room.south.changingState("clean", pc);
                } else {
                    this.changeRoom();
                }
            }
        } else if (r == 2) {
            if (room.east != null && room.east.top < 10) {
                this.changeRoom(room.east);
                if (room.east.getState().equalsIgnoreCase("dirty")) {
                    room.east.changingState("clean", pc);
                } else {
                    this.changeRoom();
                }
            }
        } else if (r == 3) {
            if (room.west != null && room.west.top < 10) {
                this.changeRoom(room.west);
                if (room.west.getState().equalsIgnoreCase("dirty")) {
                    room.west.changingState("clean", pc);
                } else {
                    this.changeRoom();
                }
            }
        } else {
            this.disappear(this);
        }
    }
}