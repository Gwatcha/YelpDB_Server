package ca.ece.ubc.cpen221.mp5;

import ca.ece.ubc.cpen221.mp5.YelpDB;

import java.io.*;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.google.gson.Gson;

import RecordClasses.Restaurant;
import RecordClasses.User;

/**
 * Connection handler, which runs on a separate thread handling a connection
 * from one clientSocket.
 */
public class ConnectionHandler implements Runnable {

	private final Socket clientSocket;
	private final YelpDB database;

	public ConnectionHandler(Socket clientSocket, YelpDB database) {
		this.clientSocket = clientSocket;
		this.database = database;
	}

	@Override
	public void run() {
		try {
			try {
				try {
					handle();
				} catch (Exception e) {
				}
			} finally {
				clientSocket.close();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private void handle() throws Exception {
		System.out.println("Client " + clientSocket.toString() + " connected.");

		// Create reader and writer.
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

		// As long as the client has not closed the socket..
		while (!clientSocket.isClosed()) {
			// each request is a single line containing a string
			for (String line = in.readLine(); line != null; line = in.readLine()) {
				System.out.println(clientSocket.toString() + " requested: " + line);
				try {
					String response = executeRequest(line);
					if (!response.equals("")) {
						out.print(response + "\n");
						out.flush(); // send to client
					}
				} catch (IllegalArgumentException e) {
					// complain about ill-formatted request
					out.print("ERR: ILLEGAL_REQUEST\n");
					out.flush();
				}
			}
		}

		// Client is gone, start cleaning up.
		out.close();
		in.close();
	}

	/**
	 * Parses a request line and uses the database to execute it.
	 *
	 * @param request
	 * @return the string to send to the user if elicited, otherwise, an empty
	 *         string.
	 * @throws IllegalArgumentException
	 *             if invalid or improperly formatted request.
	 */
	private String executeRequest(String request) throws Exception {
		// Response is assumed to be empty in case this command
		// Does not elicit a respons.
		String response = "";
		String command = "";
		String details = "";

		// Split the request into the command and details.
		String[] arr = request.split(" ");
		// Splitting the Request String
		if (arr.length >= 2) {
			command = arr[0]; // Can change it to .toUpperCase but i think it should count as invalid command
			for (int i = 1; i < arr.length; i++) {
				details += arr[i];
			}
		} else
			return "ERR: ILLEGAL_REQUEST";

		switch (command) {
		case "GETRESTAURANT": {
			
			// Get all the restaurants matching the id
			Set<Restaurant> matches = database.getMatches(details);
			// Check to see if something was found
			if (matches.size() == 0) {
				return "ERR: NO_SUCH_RESTAURANT";
			} else {
				for (Restaurant res : matches) {
					// An extra check
					if (res.getBusiness_id().equals(details)) {
						return res.getName();
					}
				}
			}
		}
		case "ADDUSER": {
			String name = "", user_id = "";
			
			// Has atleast "{\"name\": \"" and   "\"}"
			if (details.length() > 12) {

				String str = details.substring(0, 10);
				
				if (!str.matches("{\"name\": \"")) {
					return "ERR: INVALID_USER_STRING";
				}
				// the name
				name = details.substring(9, details.length() - 1);

				str = details.substring(details.length() - 2);

				if (!str.matches("\"}")) {
					return "ERR: INVALID_USER_STRING";
				}

				Table userTable = database.getTableOf("users");
				List<User> users = YelpDB.getUsers(userTable.getRecords());
				boolean isUnique = true;
				while (isUnique) {
					boolean isNotUnique = false;

					UUID id = UUID.randomUUID();
					user_id = id.toString().substring(0, 22);

					for (User user : users) {
						if (user.getUser_id().equals(user_id))
							isNotUnique = true;
					}
					isUnique = isNotUnique;
				}
				response = "{\"url\": \"http://www.yelp.com/user_details?userid=" + user_id + "\", "
						+ "\"votes\": {}, \"review_count\": 0, \"type\": \"user\", \"user_id\": " + "\"" + user_id
						+ "\", \"name\": \"" + name + "\", \"average_stars\": 0}";
				
				Record record = parseLine(response, new User().getClass());
				
				// adding to Database
				userTable.addRecord(record);
				
				return response;
			} else
				return "ERR: INVALID_USER_STRING";

		}
		case "ADDRESTAURANT": {
			List<Record> restaurantRecord = new LinkedList<Record>();
			
			Record record = parseLine(details, new Restaurant().getClass());
			restaurantRecord.add(record);
			List<Restaurant> restaurant = YelpDB.getRestaurants(restaurantRecord);
			if(restaurant.size() == 0)
				return "ERR: INVALID_RESTAURANT_STRING";
			Restaurant resta = null;
			
			for(Restaurant res : restaurant) {
				resta = res;
				break;
			}
			
			// Checks if the details is a valid JSON line for ADDRESTAURANT
			for(Method method : resta.getClass().getDeclaredMethods()) {
				if (method.getName().startsWith("get") && (!method.getName().equals("getStars")
						|| !method.getName().equals("getBusiness_id"))) {
					String str = (String) method.invoke(resta);
					if(str.isEmpty()) {
						return "ERR: INVALID_RESTAURANT_STRING";
					}	
				}
			}
			
			
			Table restaurantTable = database.getTableOf("restaurants");
			List<Restaurant> restaurants = YelpDB.getRestaurants(restaurantTable.getRecords());
			boolean isUnique = true;
			String business_id = "";
			while (isUnique) {
				boolean isNotUnique = false;

				UUID id = UUID.randomUUID();
				business_id = id.toString().substring(0, 22);

				for (Restaurant res : restaurants) {
					if (res.getBusiness_id().equals(business_id))
						isNotUnique = true;
				}
				isUnique = isNotUnique;
			}
			
			
			resta.setBusiness_id(business_id);
			resta.setStars(0.0);
			String neighbourhoods = converStringArray(resta.getNeighborhoods());
			String categories = converStringArray(resta.getCategories());
			String schools = converStringArray(resta.getSchools());
			
			response = "{\"open\": " + resta.getOpen()  + ", \"url\": \"" + resta.getUrl() + "\", "
					+ "\"longitude\": "+ resta.getLongitude() + ", \"neighborhoods\": "
					+ "[" + neighbourhoods + "], \"business_id\": "
					+ "\"" + resta.getBusiness_id() + "\", \"name\": \""+ resta.getName() + "\", "
					+ "\"categories\": [" + categories + "], \"state\": \""+ resta.getState() + "\", "
					+ "\"type\": \""+ resta.getType() +"\", \"stars\": "+ resta.getStars() + ", \"city\": \""
					+ resta.getCity() +"\", \"full_address\": \""+ resta.getFull_address() +"\", "
					+ "\"review_count\": 9, \"photo_url\": \""+ resta.getPhoto_url() +"\", "
					+ "\"schools\": ["+ schools  +"], \"latitude\": "+ resta.getLatitude() 
					+", \"price\": " + resta.getPrice() + "}";
			
			record = parseLine(response, new Restaurant().getClass());
			// Updating restaurantTable 
			restaurantTable.addRecord(record);
			
			return response;

		}
		case "ADDREVIEW": {
			return response;

		}
		case "PING": {
			response = "I'm alive!";
			return response;
		}
		default:
			throw new IllegalArgumentException();
		}
	}
	
    /**
     * Method parseLine, which takes a line of json following
     * the form of a row defined by this instances definingClass.
     * Returns a record representation of the row with no guarantee on order.
     *
     * @param jsonRow is a String for a row of json that fits the definedClass of this instance.
     *                eg.    String: {"emp_id": 1017, "address": {"street":"MG Road","city":"Bangalore"}}
     *                Now in RecordClasses..
     *                Employee {int emp_id; Address address; //+Standard Getters and Setters}
     *                Address {String street; String city; //+Standard Getters and Setters}
     * @return A record with the type defined by this objects table type
     * (which is set during object construction).
     * @throws Exception, thrown if the jsonLine passed does not match the type
     *                    defined by the definingClass.
     */
    private Record parseLine(String jsonRow, Class definingClass) throws Exception {
        //The record to return
        Record record = new Record(definingClass.getSimpleName().toLowerCase());

        //Parse the line and store into a new object of type defined in the constructor.
        Gson gson = new Gson();

        //This line parses the row and populates the 'output' Objects properties
        //According to the values of fields in this jsonRow.
        //The output object is of type 'definingClass', which has to have
        //The same property names and types as the fields in the jsonRow string,
        //As well as respective getter and setter methods for each one.
        Object output = gson.fromJson(jsonRow, definingClass);
        //Throws exception if the jsonRow being parsed does not fit the class.

        for (Method method : definingClass.getDeclaredMethods()) {
            //Only use getter methods.
            if (method.getName().startsWith("get")) {
                //Use this getter that is assumed to be labeled "get[parameterName]" to create a field.
                Field field = new Field<>(method.getName().substring(3).toLowerCase(), method.invoke(output));
                record.addField(field);
            }
        }

        return record;
    }
	
    private String converStringArray(String[] arr) {
    	String s = "";
    	for(int i = 0; i < arr.length; i++) {
    		s += "\"";
    		s += arr[i];
    		s += "\"";
    		
    		if((i+1) < arr.length) {
    			s += ", ";
    		}
    	}
    	return s;
    }
	
}
