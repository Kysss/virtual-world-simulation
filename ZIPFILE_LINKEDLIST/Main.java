import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.helpers.DefaultHandler;

public class Main {

    public static void main(String[] a) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        //   saxParser.parse("input.xml", new MyHandler());
        MyHandler mh = new MyHandler();
        Scanner sc = new Scanner(System.in);
        Boolean inputFileExist = true;

        do {
            System.out.println("Please enter a valid file name so we can build the game world for you.");
            try {
                String inputFile = sc.nextLine();
                saxParser.parse(inputFile, mh);
                inputFileExist = true;
                mh.getPC().play(sc, mh.getPC());

//         System.out.println("Please enter a room name.");
//         String userString = sc.nextLine();
//         if (mh.CheckExistRoom(userString)!=null){
//          System.out.println(mh.CheckExistRoom(userString).toString());
//         } else {
//             System.out.println("Room " + userString + " does not exist.");
//             
//         }
            } catch (FileNotFoundException fnf) {
                inputFileExist = false;
                System.out.println("File not found. Please try again.");
            }

        } while (!inputFileExist);

    }
}