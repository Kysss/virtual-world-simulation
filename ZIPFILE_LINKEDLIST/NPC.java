import java.util.Random;
public class NPC extends Creature {

   

    public NPC(String npcn, String npcd) {
        super(npcn, npcd);
    }
//
//    protected void preference() {
//        System.out.println("clean");
//    }
    @Override
     protected String preference(){
        return "dirty";
    }
     protected String type(){
         return "NPC";
     }

    @Override
    public void discontent(PC pc) {
        // grumble or growl
        System.out.println(this.name +" the "+ this.type() + " grumbles at you.");
        if (room.getState().equalsIgnoreCase("clean")) {
            changeRoom();
        }
        
        pc.respect--;

    }

    @Override
    public void gladness(PC pc) {
        // smile or lickFace
        System.out.println(this.name+ " the " + this.type() + " smiles at you.");
        pc.respect++;
    }

    @Override
    protected void react(String s, PC pc) {
        if (this.room.statebeforechange.equalsIgnoreCase("half-dirty")) {
            if (this.room.getState().equalsIgnoreCase("dirty")) {
                gladness(pc);

            } else if (this.room.getState().equalsIgnoreCase("clean")) {
                discontent(pc);

            }
        } else if (this.room.statebeforechange.equalsIgnoreCase("dirty")) {
            if (this.room.getState().equalsIgnoreCase("half-dirty")) {
                discontent(pc);

            } else if (this.room.getState().equalsIgnoreCase("dirty")) {
                System.out.println(this.name + " the " + this.type() +" smiles at you.");
            }
        } else if (this.room.statebeforechange.equalsIgnoreCase("clean")) {
            if (this.room.getState().equalsIgnoreCase("half-dirty")) {
                gladness(pc);
            } else if (this.room.getState().equalsIgnoreCase("clean")) {
                System.out.println(this.name + " the "+ this.type() + " grumbles at you");
                changeRoom();
            }
        }
//        if (s.equalsIgnoreCase("dirty")) {
//            gladness(pc);
//        } else {
//            discontent(pc);
//        }
    }

    public String toString() {
        return "NPC:" + name + "," + description;
    }

    public void changeRoom(PC pc) {
        Random random = new Random();
        int r = random.nextInt(4); //0,1,2,3 
        if ((room.north != null && room.north.top < 10) || (room.south != null && room.south.top < 10)
                && (room.east != null && room.east.top < 10) || (room.west != null && room.west.top < 10)) {
            if (r == 0) {
                if (room.north != null && room.north.top < 10) {
                    this.changeRoom(room.north);
                    if (room.north.getState().equalsIgnoreCase("clean")) {
                        room.north.changingState("dirty", pc);
                    }
                } else {
                    this.changeRoom();
                }
            }
        } else if (r == 1) {
            if (room.south != null && room.south.top < 10) {
                this.changeRoom(room.south);
                if (room.south.getState().equalsIgnoreCase("clean")) {
                    room.south.changingState("dirty", pc);
                } else {
                    this.changeRoom();
                }
            }
        } else if (r == 2) {
            if (room.east != null && room.east.top < 10) {
                this.changeRoom(room.east);
                if (room.east.getState().equalsIgnoreCase("clean")) {
                    room.east.changingState("dirty", pc);
                } else {
                    this.changeRoom();
                }
            }
        } else if (r == 3) {
            if (room.west != null && room.west.top < 10) {
                this.changeRoom(room.west);
                if (room.west.getState().equalsIgnoreCase("clean")) {
                    room.west.changingState("dirty", pc);
                } else {
                    this.changeRoom();
                }
            }
        } else {
            this.disappear(this);
        }
    }

}