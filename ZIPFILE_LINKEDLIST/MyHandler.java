import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;

public class MyHandler extends DefaultHandler {
  private Room currentRoom;
  private PC pc;
//  private Room[] rooms = new Room[100];
LinkedList <Room> rooms = new LinkedList<Room>();  
  private int count =0;
  public PC getPC(){ 
      return pc; 
  }

  public void startDocument() {
    System.out.println("Game loading. Please wait.");
    
  }

  public void endDocument() {
      
    System.out.println("Loading completed.");
      
  }
  public Room CheckExistRoom(String roomname){
//     for (int i = 0; i < rooms.length; i++) {
//          if (rooms[i] != null&& rooms[i].getName().equals(roomname)) {
//              Room exist = rooms[i];
//              return exist;
//  }
//     } return null;
     for (int i = 0; i < rooms.length(); i++) {
          if (rooms.get(i) != null&& rooms.get(i).getName().equals(roomname)) {
              Room exist = rooms.get(i);
              return exist;
  }
     } return null;
  }

  @Override
  public void startElement(String uri,
                String localName,
                String qName,
                Attributes attributes) {
//    String name = attributes.getValue("name");
//    String desc = attributes.getValue("description");
//    String north = attributes.getValue("north");
// System.out.println("Starting tag " + qName + ", name: " + name + ", desc: " + desc);
//    if (north == null)
//      System.out.println("No north neighbor.");
//    else{
//      System.out.println("North neighbor: " + north);
//    }
//      System.out.println("Starting tag " + qName + " "+ attributes.getValue("name"));
    if (qName.equals("room")) {
            currentRoom = new Room(attributes.getValue("name"), attributes.getValue("description"), attributes.getValue("state"));
           
//            rooms[count]=currentRoom;
           
            rooms.append(currentRoom);  
            count++;
            
            
           if (CheckExistRoom(attributes.getValue("north"))!= null){
               Room n = CheckExistRoom(attributes.getValue("north"));
               currentRoom.north= n;
               n.south= currentRoom;      
               
           }
           if (CheckExistRoom(attributes.getValue("south"))!=null){
               Room s = CheckExistRoom(attributes.getValue("south"));
              currentRoom.south = s;
               s.north = currentRoom;
           }
           
           if (CheckExistRoom(attributes.getValue("east"))!=null){
               Room e = CheckExistRoom(attributes.getValue("east"));
              currentRoom.east = e;
               e.west = currentRoom;
           } 
           
          if (CheckExistRoom(attributes.getValue("west"))!=null){
               Room w = CheckExistRoom(attributes.getValue("west"));
           currentRoom.west = w;
               w.east = currentRoom;
          }
    } else if (qName.equals("animal")) {
      Animal a = new Animal(attributes.getValue("name"), attributes.getValue("description"));
//      currentRoom.Creatures.append(a);
      currentRoom.addCreature(a);
      a.room = currentRoom;
      

    } else if (qName.equals("NPC")){
        NPC npc = new NPC(attributes.getValue("name"), attributes.getValue("description"));
        currentRoom.addCreature(npc);
        npc.room=currentRoom;
 
    
        } else if (qName.equals("PC")){
        pc = new PC(attributes.getValue("name"), attributes.getValue("description"), 40);
        
        currentRoom.addCreature(pc);
        pc.room = currentRoom;
   
        
        }
  }
  
  
  public void endElement(String uri,
              String localName,
              String qName) {
//    System.out.println("Ending tag " + qName);
  }
}