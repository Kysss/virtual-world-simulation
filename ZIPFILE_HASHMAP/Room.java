import java.util.HashMap;

public class Room {

    protected int top = 0;
//    protected Creature[] Creatures = new Creature[10];
//    protected LinkedList <Creature> Creatures = new LinkedList<Creature>();
    protected HashMap<String, Creature> Creatures = new HashMap<String, Creature>();
    protected Room east;
    protected Room west;
    protected Room north;
    protected Room south;
    protected String statebeforechange = this.state;
    private String state; //can be half dirty, dirty, or clean
    private String roomname;
    private String description;

    public Room(String rn, String d, String s) {
        roomname = rn;
        description = d;
        state = s;

    }

    public String getState() {
        return state;
    }

    public String getName() {
        return roomname;
    }
   

    public String getDescription() {
        return description;
    }

    public void changingState(String s, PC pc) {
        statebeforechange = this.state;
        if (this.state.equalsIgnoreCase("clean") && s.equalsIgnoreCase("clean")) {
            System.out.println("The room is already clean.");
        } else if (this.state.equalsIgnoreCase("clean") && s.equalsIgnoreCase("dirty")) {
            state = "half-dirty";
            System.out.println("The current room state is now:" + state);

        } else if (this.state.equalsIgnoreCase("dirty") && s.equalsIgnoreCase("clean")) {
            state = "half-dirty";
            System.out.println("The current room state is now:" + state);

        } else if (this.state.equalsIgnoreCase("dirty") && s.equalsIgnoreCase("dirty")) {
            System.out.println("The room is already dirty.");
        } else if (this.state.equalsIgnoreCase("half-dirty") && s.equalsIgnoreCase("clean")) {
            state = "clean";
            System.out.println("The current room state is now:" + state);

        } else if (this.state.equalsIgnoreCase("half-dirty") && s.equalsIgnoreCase("dirty")) {
            state = "dirty";
            System.out.println("The current room state is now:" + state);

        }
        //shallowcopy of hashmap
       HashMap<String, Creature> copy = new HashMap<String, Creature>();
        for (String c: Creatures.keySet()) {
            copy.put(c, Creatures.get(c));
        }
        for(String c:copy.keySet()){
            if(Creatures.containsKey(c)){
                Creatures.get(c).react(s, pc);
            }
        }
       
//        for (String key : Creatures.keySet()) {
//            Creatures.get(key).react(s, pc);
//        }
//        LinkedList <Creature> copy = Creatures.shallowCopy();
//        for (int i = 0; i < copy.length(); i++) {
//            Creature c = copy.get(i);
//           if( Creatures.exists(c)){
//               c.react(s, pc);
//           }
        
//        
//        for (int i = 0; i < top; i++) {
//            Creature temp = null;
//            while (Creatures[i] != temp) {
//                temp = Creatures[i];
//                Creatures[i].react(s, pc);
//                Creatures[i].react(s, pc);
//                temp = Creatures[i];
//            }
//        }
    }

    // changing state with no creature reactions
    public void changingState(String s) {
        statebeforechange = this.state;

        if (this.state.equalsIgnoreCase("clean") && s.equalsIgnoreCase("clean")) {
            System.out.println("The room is already clean.");
        } else if (this.state.equalsIgnoreCase("clean") && s.equalsIgnoreCase("dirty")) {
            state = "half-dirty";
            System.out.println("The current room state of " + this.getName() + " is now:" + state);

        } else if (this.state.equalsIgnoreCase("dirty") && s.equalsIgnoreCase("clean")) {
            state = "half-dirty";
            System.out.println("The current room state of " + this.getName() + " is now:" + state);

        } else if (this.state.equalsIgnoreCase("dirty") && s.equalsIgnoreCase("dirty")) {
            System.out.println("The room is already dirty.");
        } else if (this.state.equalsIgnoreCase("half-dirty") && s.equalsIgnoreCase("clean")) {
            state = "clean";
            System.out.println("The current room state of " + this.getName() + " is now:" + state);

        } else if (this.state.equalsIgnoreCase("half-dirty") && s.equalsIgnoreCase("dirty")) {
            state = "dirty";
            System.out.println("The current room state of " + this.getName() + " is now:" + state);

        }
    }

//////////////////////////////////////////////////////
//    public void quickSort(Creature[] c, int leftbound, int rightbound) {
//        int pivot = leftbound + (rightbound - leftbound) / 2;
////        Creature pivot = c[leftbound+(rightbound-leftbound)/2];
//        int i = leftbound;
//        int j = rightbound;
//
//        while (i <= pivot && j >= pivot) {
//
//            while (c[pivot].name.compareTo(c[i].name) > 0) {
//                //if the string is smaller then keep on going
//                i++;
//            }
//            while (c[pivot].name.compareTo(c[j].name) < 0) {
//                //if the string is bigger than keep on going 
//                j--;
//            }
//            if (c[i].name.compareTo(c[j].name) >= 0) {
//                swap(i, j);
//
//                i++;
//                j--;
//            }
//
//            if (i == j) {
//                break;
//            }
//
//            if (i < rightbound) {
//                quickSort(c, i, rightbound);
//            }
//            if (leftbound < j) {
//                quickSort(c, leftbound, j);
//            }
//        }
//    }
//
//    public void swap(int a, int b) {
//        Creature temp = Creatures[a];
//        Creatures[a] = Creatures[b];
//        Creatures[b] = temp;
//    }
////////////////////////////////////////////////////////////////////////////
//    public Creature binarySearch(Creature[] c, String name) {
//
//        int low = 0;
//        int high = top - 1;
//        while (low <= high) {
//            int middle = (low + high) / 2;
//            if (c[middle].name != null && c[middle].name.compareTo(name) > 0) {
//                high = middle - 1;
//            } else if (c[middle].name != null && c[middle].name.compareTo(name) < 0) {
//                low = middle + 1;
//            } else if (c[middle].name != null && c[middle].name.compareTo(name) == 0) {
//                return c[middle];
//
//            }
//        }
//        return null;
//    }
    public void addCreature(Creature a) {
//        if (top >= 10) {
//            System.out.println("Room full.");
//        } else {
//            Creatures[top] = a;
//            top++;
//            quickSort(this.Creatures, 0, top - 1);
//        }

        Creatures.put(a.name, a);
        top++;

    }

    public void removeCreature(Creature a) {

//        for (int i = 0; i < Creatures.length; i++) {
//
//            if (Creatures[i] == null) {
////                System.out.println("No one is in the room.");
//            } else if (Creatures[i].equals(a)) {
//                Creatures[i] = null;
//
//                // to move the last Creature in the array into the empty spot
//                // so that no "gap" is in the array
//                Creatures[i] = Creatures[top - 1];
//                Creatures[top - 1] = null;
//                top--;
//                quickSort(this.Creatures, 0, top - 1);
//            }
//        for (int i = 0; i < Creatures.length(); i++) {
//
//            if (Creatures.isEmpty()) {
////                System.out.println("No one is in the room.");
//            } else if (Creatures.exists(a)) {
//                Creatures.remove(a);
//                top--;
//                
//               
//            }
//        }
        if (Creatures.isEmpty()) {
//                System.out.println("No one is in the room.");
        } else {
            Creatures.remove(a.name);
            top--;

        }
    }



//        

//         public Creature findCreature(String creaturename) {
//        Creature find = null;
//        for (int i = 0; i < Creatures.length(); i++) {
//            if (Creatures.get(i) != null && Creatures.get(i).name.equals(creaturename)) {
//                find = Creatures.get(i);
//                break;
//            } else {
//                find = null;
//            }
//        }
//        return find;
//    }

    public String toString() {

        String info = "\n" + "Room:" + roomname + "\n" + "State:" + state + "\n" + "Containing: "
                + "\n";
for (String name: Creatures.keySet()) {
    info = info + Creatures.get(name).toString() + "\n";
}
        
        
        
        
        
//        for (int i = 0; i < top; i++) {
////            if (Creatures[i] != null) {
////                info = info + " " + Creatures[i].toString() + "\n";
////            }
//            if(Creatures.get(i)!=null){
//                info = info+ " "+ Creatures.get(i).toString() + "\n";
//            }
//        }
        info = info + "\n" + "Neighbors:" + "\n";
        if (this.north != null) {
            info = info + " north:" + this.north.getName() + "\n";
        }
        if (this.south != null) {
            info = info + " south:" + this.south.getName() + "\n";
        }
        if (this.east != null) {
            info = info + " east:" + this.east.getName() + "\n";
        }
        if (this.west != null) {
            info = info + " west:" + this.west.getName() + "\n";
        }

        return info;
        

        
        
        
        
    }
   
        }
    
    
//    public void notifyCreatures() {
//
//        for (Creature Creatures : Creatures) {
//            Creatures.react();
//        }
//    }