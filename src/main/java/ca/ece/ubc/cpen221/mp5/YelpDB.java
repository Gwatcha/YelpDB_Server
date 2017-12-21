
package ca.ece.ubc.cpen221.mp5;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.ToDoubleBiFunction;
import RecordClasses.*;
import RecordClasses.Review.RestaurantVotes;
import RecordClasses.User.UserVotes;


public class YelpDB implements MP5Db<Restaurant> {

	List<Table> dataBase;
    //An ordered list of table names
    private List<String> order;

	public YelpDB(String restaurantsFile, String reviewsFile, String usersFile) throws FileNotFoundException {
		dataBase = Collections.synchronizedList(new ArrayList<Table>());
		order = Collections.synchronizedList(new ArrayList<String>());

		// Initialize parsers.
		ParseJsonFile restaurantsParser = new ParseJsonFile(restaurantsFile, Restaurant.class);
		ParseJsonFile reviewsParser = new ParseJsonFile(reviewsFile, Review.class);
		ParseJsonFile usersParser = new ParseJsonFile(usersFile, User.class);

		// Add tables to DB.
		dataBase.add(restaurantsParser.makeTable());
		dataBase.add(reviewsParser.makeTable());
		dataBase.add(usersParser.makeTable());

        order.add("restaurants");
        order.add("reviews");
        order.add("users");
	}


    /**
     * Returns a list of Records which contain a field equal to the
     * one specified.
     *
     * @param tableName, the table to search through, usually the name of the record + "s"
     * @param field,     the field to match records to
     * @return a list of records that contain a field equal to 'field', empty if no matches.
     */
    public List<Record> getRecordMatches(String tableName, Field<?> field) {
        List<Record> matchedRecords = new ArrayList<>();
        //Num of this table in database, -1 if invalid table.
        int tableNum = order.indexOf(tableName.toLowerCase());
        if (tableNum == - 1 || dataBase.isEmpty()) {
            return matchedRecords;
        }

        Table tableToSearch = dataBase.get(tableNum);

        //If this field does not exist in the records of this table,
        //then return the empty list. Trust that table is synchronized.
        int indexOfTargetField = tableToSearch.indexOfField(field);
        if (indexOfTargetField == -1) {
            return matchedRecords; //empty
        }

        List<Record> recordsToSearch = tableToSearch.getRecords();
        //Search through this tables records to find matches.
        for (Record record : recordsToSearch) {
            if (record.getFieldAt(indexOfTargetField).equals(field)) {
                matchedRecords.add(record);
            }
        }
        return matchedRecords;
    }

	// ~~~~~~~~~~~~Interface methods~~~~~~~~~~~~~~~ \\

	@Override
	public Set<Restaurant> getMatches(String queryString) {

		int restaurant_index = -1;
		for (int i = 0; i < dataBase.size(); i++) {
			if (dataBase.get(i).getName().equals("Restaurants")) {
				restaurant_index = i;
			}
		}

		List<Record> records = dataBase.get(restaurant_index).getRecords();

		List<Record> thequeryStringRecords = new LinkedList<Record>();
		// Get all the Record of the Restaurants that contain the queryString
		for (Record rec : records) {
			if (rec.toString().contains(queryString)) {
				thequeryStringRecords.add(rec);
			}
		}

		return new HashSet<Restaurant>(getRestaurants(thequeryStringRecords));
	}

	@Override
	public String kMeansClusters_json(int k) {
		List<Set<Restaurant>> list = kMeansClustering(k);
		String query = "";
		String str = "[";

		// Not sure which JSON format so, pick one and comment the other

		// for(int i = 0; i < list.size(); i++) {
		// query = "";
		//
		// for(Restaurant r: list.get(i)) {
		//
		// query += "{\"open\": " + r.getOpen() + ", \"url\": " + r.getUrl() + ",
		// \"longitude\": " +
		// r.getLongitude() + ", \"neighborhoods\": [";
		//
		// for(String s : r.getNeighborhoods())
		// query += "\"" + s + "\"";
		//
		// query += "], \"business_id\": " +r.getBusiness_id() + ", \"name\": " +
		// r.getName() +
		// ", \"categories\": [";
		//
		// for(String s : r.getCategories())
		// query += "\"" + s + "\"";
		//
		// query += "], \"state\": " + r.getState() + ", \"type\": " + r.getType() + ",
		// \"stars\": " +
		// r.getStars() + ", \"city\": " + r.getCity() + ", \"full_address\": " +
		// r.getFull_address() +
		// ", \"review_count\": " + r.getReview_count() + ", \"photo_url\": "
		// +r.getPhoto_url() + ", \"schools\": [";
		// for(String s : r.getSchools())
		// query += "\"" + s + "\"";
		//
		// query += "], \"latitude\": " + r.getLatitude() + ", \"price\": " +
		// r.getPrice() + "}, ";
		// }
		// str += query;
		// }
		// str = str.substring(0, str.length() - 2);
		// str += "]";
		//

		for (int i = 0; i < list.size(); i++) {
			query = "";

			for (Restaurant r : list.get(i)) {

				query += "{\"x\": " + r.getLatitude() + ", \"y\": " + r.getLongitude() + ", \"name\": \"" + r.getName()
						+ "\", \"cluster\": " + (i + 1) + ", \"weight\": 1.0}, ";

			}
			str += query;
		}

		str = str.substring(0, str.length() - 2);

		str += "]";

		return str;

	}
	/**
	 * @throws IllegalException for invalid inputs, see StackTrace for the specifics
	 */
	@Override
	public ToDoubleBiFunction<MP5Db<Restaurant>, String> getPredictorFunction(String user) {

		// Which index in dataBase contains the reviews.json table
		int user_index = -1;
		for (int i = 0; i < dataBase.size(); i++) {
			if (dataBase.get(i).getName().equals("Reviews")) {
				user_index = i;
			}
		}

		// Gets the all records for all the reviews
		List<Record> records = dataBase.get(user_index).getRecords();

		List<Record> theUserReviewRecords = new LinkedList<Record>();

		// Filters the records for reviews written by user
		for (Record rec : records) {
			if (rec.toString().contains(user)) {
				theUserReviewRecords.add(rec);
			}
		}
		// Gets the Review object for each review.json line in the filtered records
		List<Review> theUserReviews = getReviews(theUserReviewRecords);

		// Which index in dataBase contains the restaurants.json table
		for (int i = 0; i < dataBase.size(); i++) {
			if (dataBase.get(i).getName().equals("Restaurants")) {
				user_index = i;
			}
		}

		// Gets all the records
		records = dataBase.get(user_index).getRecords();

		List<Record> theUserRestaurantReviewRecords = new LinkedList<Record>();
		// Get all the Records of the Restaurants Reviewed by the User
		for (Record rec : records) {
			for (Review rev : theUserReviews) {
				if (rec.toString().contains(rev.getBusiness_id())) {
					theUserRestaurantReviewRecords.add(rec);
				}
			}
		}

		List<Restaurant> ReviewedRestaurants = getRestaurants(theUserRestaurantReviewRecords);

		// ~~~~~~~~~~~~ MATH STUFF  ~~~~~~~~~~~~ \\

		Double mean_y = 0.0, mean_x = 0.0;
		Integer xi = 0;
		Double sumXX = 0.0, sumYY = 0.0, sumXY = 0.0;
		Double b = null;
		Double a = null;
//		Double r_squared;  // Comment out if r_squared is needed

		// Gets the sum of y and sum of x
		for (Review r : theUserReviews) {
			mean_y += r.getStars();

			// Goes through the list of reviewedRestaurants to
			// find the restaurant with the same business_ids
			for (Restaurant rest : ReviewedRestaurants) {
				if (rest.getBusiness_id().equals(r.getBusiness_id())) {
					mean_x += rest.getPrice();
					break;
				}
			}
		}

		// Dividing the sums by the size to get the mean
		mean_y = mean_y / theUserReviews.size();
		mean_x = mean_x / theUserReviews.size();

		// Getting the sumXX, sumYY, sumXY
		for (Review r : theUserReviews) {
			for (Restaurant rest : ReviewedRestaurants) {
				if (rest.getBusiness_id().equals(r.getBusiness_id())) {
					xi = rest.getPrice();
					break;
				}
			}
			sumXX += (xi - mean_x) * (xi - mean_x);
			sumYY += (r.getStars() - mean_y) * (r.getStars() - mean_y);
			sumXY += (r.getStars() - mean_y) * (xi - mean_x);

		}

		// checks if sumXX is zero to throw an error due to the lack of
		// review data to produce a proper predication
		if (sumXX != 0) {
			b = sumXY / sumXX;
			a = mean_y - (b * mean_x);
		//	r_squared = (sumXY * sumXY) / (sumXX * sumYY); // Comment out if r_squared is needed
		} else {
			throw new IllegalArgumentException("sumXX == 0");

		}

		// finalizing the values to parse into the lambda function
		final Double aa = a;
		final Double bb = b;

		ToDoubleBiFunction<MP5Db<Restaurant>, String> func = (x, y) -> {
			// Gets the restaurant(s) that matches the String y.
			Set<Restaurant> set = x.getMatches(y);

			// Should return the prediction
			for (Restaurant res : set) {

				if ((((aa * res.getPrice()) + bb) > 5.0))
					return 5.0;
				else if (((aa * res.getPrice()) + bb) < 1.0)
					return 1.0;
				else
					return ((aa * res.getPrice()) + bb);
			}
			// Throw error since there were no matches for the String y
			throw new IllegalArgumentException("No Restaurant Found in Database for: " + y);
		};

		return func;
	}

	// ~~~~~~~~~~~~ Helper Methods ~~~~~~~~~~~~ \\

	/**
<<<<<<< HEAD
=======
	 *  Helper method for kMeansClusters_json
	 * Returns a reference to the one of the tables in the database.
	 * 
	 * @param fileName
	 * 				is the name of one of the Tables in the database.
	 * @return
	 * 			a reference to the Table in database.
	 * @throws IllegalArgumentException
	 * 				if no such table that has the same name as the fileName exists.
	 */
	public Table getTableOf(String fileName) {
		for(Table t: dataBase) {
			if(t.getName().equals(fileName))
				return t;
		}
		throw new IllegalArgumentException("No Such Table in the database");
		
	}
	
	
	
	/**
>>>>>>> StructuredQueriesUsingAntlr
	 * Helper method for kMeansClusters_json
	 * 
	 * @param k
	 *            is the number of clusters to form
	 * @return a list of sets of restaurants, where each set in the list is a
	 *         cluster
	 */
	private List<Set<Restaurant>> kMeansClustering(int k) {

		List<Set<Restaurant>> list = new LinkedList<Set<Restaurant>>();
		List<Restaurant> restaurants;
		List<List<Double>> locations = new LinkedList<List<Double>>();
		int restaurant_index = -1;
		for (int i = 0; i < dataBase.size(); i++) {
			if (dataBase.get(i).getName().equals("Restaurants")) {
				restaurant_index = i;
			}
		}

		List<Record> records = dataBase.get(restaurant_index).getRecords();
		restaurants = getRestaurants(records);

		// Initial k locations, starting point
		for (int i = 0; i < k; i++) {
			Set<Restaurant> set = new HashSet<Restaurant>();
			List<Double> setty = new LinkedList<Double>();

			setty.add(restaurants.get(i).getLatitude());
			setty.add(restaurants.get(i).getLongitude());
			locations.add(setty);

			set.add(restaurants.get(i));
			list.add(set);
			set.clear();
		}

		// Does the clustering magic
		recursiveKclusters(list, locations, restaurants, true);

		return new LinkedList<Set<Restaurant>>(list);
	}

	/**
	 * Helper method to perform recursion to keep doing the clustering to the point
	 * where no changes occur in the clusters
	 * 
	 * @param list
	 *            contains the clusters
	 * @param locations
	 *            average latitude and longitude for each cluster
	 * @param restaurants
	 *            is the list containing all the restaurants
	 * @param changed
	 *            is a boolean to trigger the recursion
	 * 
	 * @modifies the parameters list, locations, and changed
	 */
	private void recursiveKclusters(List<Set<Restaurant>> list, List<List<Double>> locations,
			List<Restaurant> restaurants, boolean changed) {

		if (changed) {
			int locationIndex = 0, restaurant_index = 0;
			double distance, smallestDistance;
			List<Double> clusterAvgLocation;
			Set<Restaurant> set;
			double SumLong;
			double SumLat;
			boolean didChange = false;
			boolean splitLargest = false;
			int indexOfLargest = 0;
			int indexOfZero = 0;
			int size = 0;
			// Creates clusters from the locations
			for (int i = 0; i < restaurants.size(); i++) {
				smallestDistance = Double.MAX_VALUE;

				// Finds the centroid that is closest to the restaurant
				for (int j = 0; j < locations.size(); j++) {

					distance = distanceBetweenRestaurants(restaurants.get(i).getLatitude(),
							restaurants.get(i).getLongitude(), locations.get(j).get(0), locations.get(j).get(1));
					if (distance < smallestDistance) {
						smallestDistance = distance;
						locationIndex = j;
						restaurant_index = i;
					}
				}

				// Checks if the Restaurant is already in the cluster
				if (!list.get(locationIndex).contains(restaurants.get(restaurant_index))) {
					// adds it to cluster
					didChange = true;
					set = list.get(locationIndex);
					set.add(restaurants.get(restaurant_index));
					list.set(locationIndex, set);

					// Removes from all other clusters
					for (int z = 0; z < list.size(); z++) {
						if (z != locationIndex) {
							list.get(z).remove(restaurants.get(restaurant_index));
						}
					}
				}

				// Checks for clusters with zero elements
				for (int j = 0; j < list.size(); j++) {

					if (list.get(j).size() == 0) {
						indexOfZero = j;
						splitLargest = true;
					}

					if (list.get(j).size() > size) {
						size = list.get(j).size();
						indexOfLargest = j;
					}
				}

				// Splits the largest cluster in half by adding it to the cluster with zero
				// elements
				if (splitLargest) {

					for (int j = 0; j < Math.round((list.get(indexOfLargest).size() + 0.5) / 2); j++) {
						list.get(indexOfZero).add((Restaurant) list.get(indexOfLargest).toArray()[j]);
						list.get(indexOfLargest).remove((Restaurant) list.get(indexOfLargest).toArray()[j]);
					}

					splitLargest = false;
					didChange = true;
				}

			}
			// Gets the new Averages for the latitude and longitude
			if (didChange) {
				locations = new LinkedList<List<Double>>();
				clusterAvgLocation = new LinkedList<Double>();
				// getting new Average Locations for k clusters
				for (int i = 0; i < list.size(); i++) {
					SumLat = 0;
					SumLong = 0;

					for (Restaurant res : list.get(i)) {
						SumLat += res.getLatitude();
						SumLong += res.getLongitude();
					}

					SumLat = SumLat / list.get(i).size();
					SumLong = SumLong / list.get(i).size();
					clusterAvgLocation.add(SumLat);
					clusterAvgLocation.add(SumLong);

					locations.add(new LinkedList<Double>(clusterAvgLocation));
					clusterAvgLocation.clear();
				}

				recursiveKclusters(list, locations, restaurants, didChange);
			}

		}

	}

	/**
	 * Get a list of all the restaurants in the Database.
	 * 
	 * @param records
	 *            List of JSON lines, that represent the Restaurant class
	 * @return List of Restaurant objects.
	 */
	public static List<Review> getReviews(List<Record> records) {
		List<Review> list = new LinkedList<Review>();
		Review review;
		// Get indexes
		for (int i = 0; i < records.size(); i++) {
			review = new Review();

			for (int j = 0; j < records.get(i).getSize(); j++) {

				if (records.get(i).getFieldAt(j).getTypeName().equals("type"))
					review.setType((String) records.get(i).getFieldAt(j).getValue());

				if (records.get(i).getFieldAt(j).getTypeName().equals("business_id"))
					review.setBusiness_id((String) records.get(i).getFieldAt(j).getValue());

				if (records.get(i).getFieldAt(j).getTypeName().equals("votes"))
					review.setVotes((RestaurantVotes) records.get(i).getFieldAt(j).getValue());

				if (records.get(i).getFieldAt(j).getTypeName().equals("review_id"))
					review.setReview_id((String) records.get(i).getFieldAt(j).getValue());

				if (records.get(i).getFieldAt(j).getTypeName().equals("text"))
					review.setText((String) records.get(i).getFieldAt(j).getValue());

				if (records.get(i).getFieldAt(j).getTypeName().equals("stars"))
					review.setStars((Integer) records.get(i).getFieldAt(j).getValue());

				if (records.get(i).getFieldAt(j).getTypeName().equals("user_id"))
					review.setUser_id((String) records.get(i).getFieldAt(j).getValue());

				if (records.get(i).getFieldAt(j).getTypeName().equals("date"))
					review.setDate((String) records.get(i).getFieldAt(j).getValue());

			}

			list.add(review);
		}

		return new LinkedList<Review>(list);
	}

	/**
	 * Get a list of all the restaurants in the Database.
	 * 
	 * @param records
	 *            List of JSON lines, that represent the Restaurant class
	 * @return List of Restaurant objects.
	 */
	public static List<Restaurant> getRestaurants(List<Record> records) {
		List<Restaurant> list = new LinkedList<Restaurant>();
		Restaurant res;
		// Get indexes
		for (int i = 0; i < records.size(); i++) {
			res = new Restaurant();

			for (int j = 0; j < records.get(i).getSize(); j++) {

				if (records.get(i).getFieldAt(j).getTypeName().equals("open"))
					res.setOpen((Boolean) records.get(i).getFieldAt(j).getValue());

				if (records.get(i).getFieldAt(j).getTypeName().equals("url"))
					res.setUrl((String) records.get(i).getFieldAt(j).getValue());

				if (records.get(i).getFieldAt(j).getTypeName().equals("longitude"))
					res.setLongitude((Double) records.get(i).getFieldAt(j).getValue());

				if (records.get(i).getFieldAt(j).getTypeName().equals("neighborhoods"))
					res.setNeighborhoods((String[]) records.get(i).getFieldAt(j).getValue());

				if (records.get(i).getFieldAt(j).getTypeName().equals("name"))
					res.setName((String) records.get(i).getFieldAt(j).getValue());

				if (records.get(i).getFieldAt(j).getTypeName().equals("business_id"))
					res.setBusiness_id((String) records.get(i).getFieldAt(j).getValue());

				if (records.get(i).getFieldAt(j).getTypeName().equals("categories"))
					res.setCategories((String[]) records.get(i).getFieldAt(j).getValue());

				if (records.get(i).getFieldAt(j).getTypeName().equals("state"))
					res.setState((String) records.get(i).getFieldAt(j).getValue());

				if (records.get(i).getFieldAt(j).getTypeName().equals("type"))
					res.setType((String) records.get(i).getFieldAt(j).getValue());

				if (records.get(i).getFieldAt(j).getTypeName().equals("stars"))
					res.setStars((Double) records.get(i).getFieldAt(j).getValue());

				if (records.get(i).getFieldAt(j).getTypeName().equals("city"))
					res.setCity((String) records.get(i).getFieldAt(j).getValue());

				if (records.get(i).getFieldAt(j).getTypeName().equals("full_address"))
					res.setFull_address((String) records.get(i).getFieldAt(j).getValue());

				if (records.get(i).getFieldAt(j).getTypeName().equals("review_count"))
					res.setReview_count((Integer) records.get(i).getFieldAt(j).getValue());

				if (records.get(i).getFieldAt(j).getTypeName().equals("photo_url"))
					res.setPhoto_url((String) records.get(i).getFieldAt(j).getValue());

				if (records.get(i).getFieldAt(j).getTypeName().equals("schools"))
					res.setSchools((String[]) records.get(i).getFieldAt(j).getValue());

				if (records.get(i).getFieldAt(j).getTypeName().equals("latitude"))
					res.setLatitude((Double) records.get(i).getFieldAt(j).getValue());

				if (records.get(i).getFieldAt(j).getTypeName().equals("price"))
					res.setPrice((Integer) records.get(i).getFieldAt(j).getValue());
			}

			list.add(res);
		}

		return new LinkedList<Restaurant>(list);
	}

	/**
	 * Get a list of all the Users in the Database.
	 * 
	 * @param records
	 *            List of JSON lines, that represent the User class
	 * @return List of User objects.
	 */


	public static List<User> getUsers(List<Record> records) {
		List<User> list = new LinkedList<User>();
		User user;
		// Get indexes
		for (int i = 0; i < records.size(); i++) {
			user = new User();

			for (int j = 0; j < records.get(i).getSize(); j++) {

				if (records.get(i).getFieldAt(j).getTypeName().equals("url"))
					user.setUrl((String) records.get(i).getFieldAt(j).getValue());

				if (records.get(i).getFieldAt(j).getTypeName().equals("votes"))
					user.setVotes((UserVotes) records.get(i).getFieldAt(j).getValue());

				if (records.get(i).getFieldAt(j).getTypeName().equals("review_count"))
					user.setReview_count((Integer) records.get(i).getFieldAt(j).getValue());

				if (records.get(i).getFieldAt(j).getTypeName().equals("type"))
					user.setType((String) records.get(i).getFieldAt(j).getValue());

				if (records.get(i).getFieldAt(j).getTypeName().equals("user_id"))
					user.setUser_id((String) records.get(i).getFieldAt(j).getValue());

				if (records.get(i).getFieldAt(j).getTypeName().equals("name"))
					user.setName((String) records.get(i).getFieldAt(j).getValue());

				if (records.get(i).getFieldAt(j).getTypeName().equals("average_stars"))
					user.setAverage_stars((Double) records.get(i).getFieldAt(j).getValue());
			}

			list.add(user);
		}

		return new LinkedList<User>(list);
	}

	// Helper method to do the pythagorean theorem
	private double distanceBetweenRestaurants(Double x1, Double y1, Double x2, Double y2) {
		double newX = x1 - x2;
		double newY = y1 - y2;
		newX = newX * newX;
		newY = newY * newY;
		return Math.sqrt(newX + newY);
	}

}
