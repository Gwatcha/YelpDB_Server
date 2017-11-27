import RecordClasses.*;
import ca.ece.ubc.cpen221.mp5.database.ParseJsonFile;
import ca.ece.ubc.cpen221.mp5.database.Table;
import org.junit.Test;

import static org.junit.Assert.*;

//Checks size for each file and optionally, prints it.
public class ParseJsonFileTest {

	// Fixed path for both
    String pathToData = "data";
    @Test
    public void testRestaurants() throws Exception {

        ParseJsonFile parser;
        parser= new ParseJsonFile(
                pathToData + "/restaurants.json"
                , Restaurant.class);

        Table table = parser.makeTable();
        System.out.println(table.getName());
      //  System.out.println(table.toString());
        assertEquals(135, table.getSize());
    }

    @Test
    public void testReviewsFile() throws Exception {

        ParseJsonFile parser;
        parser= new ParseJsonFile(
                pathToData + "/reviews.json"
                , Review.class);

        Table table = parser.makeTable();
        System.out.println(table.getName());
      //  System.out.println(table.toString());
        assertEquals(17396, table.getSize());
    }

    @Test
    public void testUsersFile() throws Exception {

        ParseJsonFile parser;
        parser= new ParseJsonFile(
                pathToData + "/users.json"
                , User.class);

        Table table = parser.makeTable();
        System.out.println(table.getName());
        //  System.out.println(table.toString());
        assertEquals(8556, table.getSize());
    }


}