
package ca.ece.ubc.cpen221.mp5.database;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.ToDoubleBiFunction;

import RecordClasses.*;

public class YelpDB<T> implements MP5Db<T> {

	ArrayList<Table> dataBase;

	public YelpDB(String restaurantsFile, String reviewsFile, String usersFile) throws FileNotFoundException {
		dataBase = new ArrayList<>();

		// Initialize parsers.
		ParseJsonFile restaurantsParser = new ParseJsonFile(restaurantsFile, Restaurant.class);
		ParseJsonFile reviewsParser = new ParseJsonFile(reviewsFile, Review.class);
		ParseJsonFile usersParser = new ParseJsonFile(usersFile, User.class);

		// Add tables to DB.
		dataBase.add(restaurantsParser.makeTable());
		dataBase.add(reviewsParser.makeTable());
		dataBase.add(usersParser.makeTable());
	}

	// ~~~~~~~~~~~~Interface methods~~~~~~~~~~~~~~~ \\

	@Override
	public Set<T> getMatches(String queryString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String kMeansClusters_json(int k) {
		// TODO Auto-generated method stub
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
		// r.getPrice() + "}";
		// }
		//
		// if(i < list.size() + 1)
		// query += ", ";
		//
		// str += query;
		// }

		for (int i = 0; i < list.size(); i++) {
			query = "";

			for (Restaurant r : list.get(i)) {

				query += "{\"x\": " + r.getLatitude() + ", \"y\": " + r.getLongitude() + ", \"name\": \"" + r.getName()
						+ "\", \"cluster\": " + (i+1) + ", \"weight\": 1.0}, ";
				
			}
			str += query;
		}
		
		str = str.substring(0, str.length() - 2);

		str += "]";

		return str;

	}

	// Helper method for kMeansClusters_json
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

		// // Creates clusters from the initial starting points
		// for (int i = 0; i < restaurants.size(); i++) {
		// smallestDistance = Double.MAX_VALUE;
		//
		// for (int j = 0; j < k; j++) {
		// distance = distanceBetweenRestaurants(restaurants.get(i).getLatitude(),
		// restaurants.get(i).getLongitude(), locations.get(j).get(0),
		// locations.get(j).get(1));
		// if (distance < smallestDistance) {
		// smallestDistance = distance;
		// locationIndex = j;
		// restaurant_index = i;
		// }
		// }
		//
		// // Adds restaurants to the list
		// set = list.get(locationIndex);
		// set.add(restaurants.get(restaurant_index));
		// list.set(locationIndex, set);
		//
		// }

		return new LinkedList<Set<Restaurant>>(list);
	}

	@Override
	public ToDoubleBiFunction<MP5Db<T>, String> getPredictorFunction(String user) {
		// TODO Auto-generated method stub
		return null;
	}

	// ~~~~~~~~~~~~ Helper Methods ~~~~~~~~~~~~ \\

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
			List<Set<Restaurant>> listy = new LinkedList<Set<Restaurant>>();

			// Creates clusters from the initial starting points
			for (int i = 0; i < restaurants.size(); i++) {
				smallestDistance = Double.MAX_VALUE;

				// Restaurant closest to the centroids
				for (int j = 0; j < locations.size() ; j++) {

					distance = distanceBetweenRestaurants(restaurants.get(i).getLatitude(),
							restaurants.get(i).getLongitude(), locations.get(j).get(0), locations.get(j).get(1));
					if (distance < smallestDistance) {
						smallestDistance = distance;
						locationIndex = j;
						restaurant_index = i;
					}
				}

				if (!list.get(locationIndex).contains(restaurants.get(restaurant_index))) {
					didChange = true;
					set = list.get(locationIndex);
					set.add(restaurants.get(restaurant_index));
					list.set(locationIndex, set);

					for (int z = 0; z < list.size(); z++) {
						if (z != locationIndex) {
							list.get(z).remove(restaurants.get(restaurant_index));
						}

					}
				}

			}

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

	private List<Restaurant> getRestaurants(List<Record> records) {
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

	// Helper method to do pathagarous
	private double distanceBetweenRestaurants(Double x1, Double y1, Double x2, Double y2) {
		double newX = x1 - x2;
		double newY = y1 - y2;
		newX = newX * newX;
		newY = newY * newY;
		return Math.sqrt(newX + newY);
	}

}
