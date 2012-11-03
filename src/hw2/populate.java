/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hw2;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


/**
 *
 * @author Hardik
 * Usage: java populate building.xy people.xy ap.xy
 */
public class populate {
    
    public static void main(String argv[]) throws FileNotFoundException {
        if(argv.length == 3) {
            popBuildings(argv[0]);
            popPersons(argv[1]);
            popAPs(argv[2]);
        }
        else {
            System.out.println("Usage: java populate building.xy people.xy ap.xy");
            popBuildings("data/building.xy");
            popPersons("data/people.xy");
            popAPs("data/ap.xy");
            //return;
        }
    }
    
    
    public static ArrayList<String> readFile(String file) throws FileNotFoundException {
        Scanner in = new Scanner(new File(file));
        ArrayList<String> lines = new ArrayList<String>();
        while(in.hasNext()){
            lines.add(in.nextLine());
        }
        return lines;
    }
    
    public static void popBuildings(String bldg) throws FileNotFoundException {
        ArrayList<String> f = readFile(bldg);
        DBUtils db = new DBUtils();
        String coordinates = "";
        String query = "";
        db.connect();
        // First Delete existing records
        db.delete("building");
        // Then add new
        for (String element : f){
            String components[] = element.split(", ");
            coordinates = "";
            for(int i=3;i<components.length;i++) {
                coordinates = (i==3)? (coordinates+components[i]):(coordinates+","+components[i]);
            }
            query = "INSERT INTO building VALUES ('" + components[0] + "','" + components[1]+ "'," + components[2] + ", SDO_GEOMETRY(2003,NULL,NULL,MDSYS.SDO_ELEM_INFO_ARRAY(1,1003,1),MDSYS.SDO_ORDINATE_ARRAY(" + coordinates +")))";
            db.insert(query);
        }
        db.close();
    }
    
    public static void popPersons(String per) throws FileNotFoundException {
        ArrayList<String> f = readFile(per);
        DBUtils db = new DBUtils();
        String coordinates = "";
        String query = "";
        db.connect();
        // First Delete existing records
        db.delete("person");
        // Then add new
        for (String element : f){
            String components[] = element.split(", ");
            coordinates = "";
            for(int i=1;i<components.length;i++) {
                coordinates = (i==1)? (coordinates+components[i]):(coordinates+","+components[i]);
            }
            System.out.println(coordinates);
            query = "INSERT INTO person VALUES ('" + components[0] + "', SDO_GEOMETRY(2001,NULL,MDSYS.SDO_POINT_TYPE(" + coordinates + ",NULL),NULL,NULL))";
            db.insert(query);
        }
        db.close();
    }
    
    public static void popAPs(String ap) throws FileNotFoundException {
        ArrayList<String> f = readFile(ap);
        DBUtils db = new DBUtils();
        String coordinates = "";
        String query = "";
        String p1,p2,p3,ps;
        db.connect();
        // First Delete existing records
        db.delete("ap");
        // Then add new
        for (String element : f) {
            String components[] = element.split(", ");
            // only 3 - so we can hardcode!!
            coordinates = components[1] + "," + components[2];
            p1 = "" + (Integer.parseInt(components[1]) + Integer.parseInt(components[3])) + "," + components[2];
            p2 = "" + components[1] + "," +(Integer.parseInt(components[2]) + Integer.parseInt(components[3]));
            p3 = "" + (Integer.parseInt(components[1]) - Integer.parseInt(components[3])) + "," + components[2];
            ps = p1+","+p2+","+p3;
            query = "INSERT INTO ap VALUES ('" + components[0] + "', SDO_GEOMETRY(2001,NULL,MDSYS.SDO_POINT_TYPE(" + coordinates + ",NULL),NULL,NULL)," + components[3] + ", SDO_GEOMETRY(2003,NULL,NULL,SDO_ELEM_INFO_ARRAY(1,1003,4),MDSYS.SDO_ORDINATE_ARRAY(" + ps + ")))";
            db.insert(query);
        }
        db.close();
    }
}
