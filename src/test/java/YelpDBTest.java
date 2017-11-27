import RecordClasses.*;
import ca.ece.ubc.cpen221.mp5.database.*;

import org.junit.Test;

public class YelpDBTest {

	@Test 
	 public void test() throws Exception {
	// Fixed path for both
    String restaurantsFile = "data/restaurants.json";
    String reviewsFile = "data/reviews.json";
    String usersFile = "data/users.json";
    YelpDB<String> yelp = new YelpDB(restaurantsFile,  reviewsFile,  usersFile);
    String query = yelp.kMeansClusters_json(3);
    System.out.println(query);
    
	}
	
}
