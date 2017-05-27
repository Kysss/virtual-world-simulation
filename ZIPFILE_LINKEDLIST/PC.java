import java.util.Scanner;
public class PC extends Creature {

    protected static int respect = 40;
    

    PC(String pcn, String pcd, int r) {
        super.name = pcn;
        super.description = pcd;
        respect = r;
    }

    protected String preference() {
        return null;
    }

    protected String type() {
        return "PC";
    }


    @Override
    public void react(String s, PC pc) {
        // PC does not care about the state of the room. 
        // whatever the state is, PC stays
    }

    public String toString() {
        return "PC:" + name + "," + description;
    }

//    public Creature findCreature(String creaturename) {
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

        public Creature findCreature(String creaturename) {
        Creature find = null;
        for (int i = 0; i < room.Creatures.length(); i++) {
            if (room.Creatures.get(i) != null && room.Creatures.get(i).name.equals(creaturename)) {
                find = room.Creatures.get(i);
                break;
            } else {
                find = null;
            }
        }
        return find;
    }

    public void play(Scanner s, PC pc) {
        boolean gameon = true;
        System.out.println("\n" + "Welcome! You are representing the PC squash in this game. The goal of the game is to achieve a PC respect of at least 80."
                + " You will lose this game if the respect value falls below 0. Good luck.");
        System.out.println("\n" + "The following is a list of commands that you can use.");
        System.out.println("Look - look at all the information of the the current room." + "\n"
                + "Clean - clean the room accordingly. Dirty to half-dirty, half-dirty to clean, clean to clean."
                + "\n" + "Dirty - dirty the room accordingly. Dirty to Dirty, half-dirty to dirty, clean to half-dirty."
                + "\n" + "North/East/South/West - leaves the current room and enter the respective one."
                + "\n" + "[Creaturename]:[command] (without the brackets and case-sensitive) - command other creatures to perform actions."
                + "For example - [Peter:clean] (without the brackets) will make the Creature Peter clean the room."
                + "\n" + "Exit/Quit - End the game");
        System.out.println("\n" + "You can pull up this command menu whenever you like by typing in [help] (without brackets).");
        System.out.println("Please enter a command, or enter [help] for a full list of commands. ");

        while (gameon && respect < 80 && respect > 0) {
            String command = s.nextLine();
            String[] words;
            words = command.split(":");

            if (words.length < 2) {
                if (command.equalsIgnoreCase("help")) {
                    System.out.println("Look - look at all the information of the the current room." + "\n"
                            + "Clean - clean the room accordingly. Dirty to half-dirty, half-dirty to clean, clean to clean."
                            + "\n" + "Dirty - dirty the room accordingly. Dirty to Dirty, half-dirty to dirty, clean to half-dirty"
                            + "\n" + "North/East/South/West - leaves the current room and enter the respective one."
                            + "\n" + "[Creaturename]:[command] (without the brackets and case-sensitive) - command other creatures to perform actions"
                            + "\n" + "Exit/Quit - End the game");
                    System.out.println("Next command?");
                } else if (command.equalsIgnoreCase("clean")) {
                    room.changingState("clean", pc);
                    System.out.println("After the execution of this command, PC's respect values is " + respect + ". Next command?");
                } else if (command.equalsIgnoreCase("dirty")) {
                    room.changingState("dirty", pc);
                    System.out.println("After the execution of this command, PC's respect values is " + respect + ". Next command?");
                } else if (command.equalsIgnoreCase("north")) {
                    if (this.room.north != null) {
                        this.changeRoom(room.north);

                    } else if (this.room.north == null) {
                        System.out.println("North room does not exist.");
                    }
                    System.out.println("After the execution of this command, PC's respect values is " + respect + ". Next command?");
                } else if (command.equalsIgnoreCase("east")) {
                    if (room.east != null) {
                        changeRoom(room.east);

                    } else {
                        System.out.println("East room does not exist.");
                    }
                    System.out.println("After the execution of this command, PC's respect values is " + respect + ". Next command?");
                } else if (command.equalsIgnoreCase("south")) {
                    if (room.south != null) {

                        this.changeRoom(room.south);

                    } else {
                        System.out.println("South room does not exist.");
                    }
                    System.out.println("After the execution of this command, PC's respect values is " + respect + ". Next command?");
                } else if (command.equalsIgnoreCase("west")) {
                    if (room.west != null) {
                        this.changeRoom(room.west);

                    } else {
                        System.out.println("West room does not exist.");
                    }
                    System.out.println("After the execution of this command, PC's respect values is " + respect + ". Next command?");
                } else if (command.equalsIgnoreCase("quit")) {
                    System.out.println("Goodbye!");
                    gameon = false;
                } else if (command.equalsIgnoreCase("exit")) {
                    System.out.println("Goodbye!");
                    gameon = false;
                } else if (command.equalsIgnoreCase("look")) {
                    System.out.println(room);
                    System.out.println("After the execution of this command, PC's respect values is " + respect + ". Next command?");
                } else {
                    System.out.println("Invalid command. Please enter a valid command. "
                            + "You can enter [help] for a full list of valid commands.");

                }

            } else if (command.length() >= 2) {

                if (this.room.findCreature(words[0]) != null) {
                    if (words[1].equalsIgnoreCase("clean")) {
                        System.out.println(this.name + " has forced " + this.room.findCreature(words[0]) + " to clean the room.");
//                        room.changingState("clean", pc);
                        if (!words[1].equalsIgnoreCase(this.room.findCreature(words[0]).preference())) {
                            this.room.findCreature(words[0]).discontent(pc);
                            this.room.findCreature(words[0]).discontent(pc);
                            room.changingState("clean", pc);
                        } else {
                            this.room.findCreature(words[0]).gladness(pc);
                            this.room.findCreature(words[0]).gladness(pc);
                            room.changingState("clean", pc);
                        }
                        
                    } else if (words[1].equalsIgnoreCase("dirty")) {
                        System.out.println(this.name + " has forced " + this.room.findCreature(words[0]) + " to dirty the room.");
//                        room.changingState("dirty", pc);
                        if (!words[1].equalsIgnoreCase(this.room.findCreature(words[0]).preference())) {
                            this.room.findCreature(words[0]).discontent(pc);
                            this.room.findCreature(words[0]).discontent(pc);
                            room.changingState("dirty", pc);
                        } else if(words[1].equalsIgnoreCase(this.room.findCreature(words[0]).preference())) {
                            this.room.findCreature(words[0]).gladness(pc);
                            this.room.findCreature(words[0]).gladness(pc);
                            room.changingState("dirty", pc);
                        }
                       
                    } else if (words[1].equalsIgnoreCase("east")) {
                        if(this.room.east!=null){
                        System.out.println(this.name + " has forced " + this.room.findCreature(words[0]) + " to move to room " + room.east.getName());
                        this.room.findCreature(words[0]).changeRoom(room.east);
                        } else {
                            System.out.println("East room does not exist.");
                        }

                    } else if (words[1].equalsIgnoreCase("north")) {
                        if(this.room.north!=null){
                        System.out.println(this.name + " has forced " + this.room.findCreature(words[0]) + " to move to room " + room.north.getName());
                        this.room.findCreature(words[0]).changeRoom(room.north);
                        }else {
                            System.out.println("North room does not exist.");
                        }
                    } else if (words[1].equalsIgnoreCase("west")) {
                        if(this.room.west!=null){
                        System.out.println(this.name + " has forced " + this.room.findCreature(words[0]) + " to move to room " + room.west.getName());
                        this.room.findCreature(words[0]).changeRoom(room.west);
                        }else {
                            System.out.println("West room does not exist.");
                        }
                    } else if (words[1].equalsIgnoreCase("south")) {
                        if(this.room.south!=null){
                        System.out.println(this.name + " has forced " + this.room.findCreature(words[0]) + " to move to room " + room.south.getName());
                        this.room.findCreature(words[0]).changeRoom(room.south);
                        }else{
                            System.out.println("South room does not exist.");
                        }
                    } else {
                        System.out.println("Invalid order command.");
                    }
                    System.out.println("After the execution of this command, PC's respect values is " + respect + " Next command?");
                } else {
                    System.out.println("Creature does not exist. Please enter an existing one.");
                    System.out.println("Please enter a command, or enter [help] for a full list of commands. ");
                }

            } else {
                System.out.println("Invalid command. Please try again.");
            }

        }
        if (respect >= 80) {
            System.out.println("Congratulations! You have won the game! ");
            gameon=false;
        } else if (respect <= 0) {
            System.out.println("Sorry! You have lost the game. Try again next time!");
            gameon= false;
        }
    }
}