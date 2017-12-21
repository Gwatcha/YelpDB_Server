package ca.ece.ubc.cpen221.mp5;

import ca.ece.ubc.cpen221.mp5.YelpDB;

import java.io.*;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.antlr.v4.gui.Trees;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.misc.TestRig;

import com.google.gson.Gson;

import RecordClasses.Restaurant;
import RecordClasses.Review;
import RecordClasses.Review.RestaurantVotes;
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
		// Does not elicit a response.
		String response = "";
		String command = "";
		String details = "";

		// Split the request into the command and details.
		String[] arr = request.split(" ");

		// Splitting the Request String
		if (arr.length >= 2) {
			command = arr[0];
			for (int i = 1; i < arr.length; i++)
				details += arr[i];
		} else {

			try {
				return handleStructuredQueries(request);
			} catch (Exception e) {

				return "ERR: INVALID_QUERY";

			}

		}

		switch (command) {
		case "GETRESTAURANT": {

			// Get all the restaurants matching the id.
			Field<String> idField = new Field<>("business_id", details);
			List<Record> matches = database.getRecordMatches("restaurants", idField);

			// Make sure rep invariant holds. (Id is unique).
			assert (matches.size() == 1);

			// Return json of this restaurant.
			return (matches.get(0).toString());
		}
		case "ADDUSER": {
			String name = "", user_id = "";

			// Has atleast "{\"name\": \"" and "\"}"
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
			if (restaurant.size() == 0)
				return "ERR: INVALID_RESTAURANT_STRING";
			Restaurant resta = null;

			for (Restaurant res : restaurant) {
				resta = res;
				break;
			}

			// Checks if the details is a valid JSON line for ADDRESTAURANT
			for (Method method : resta.getClass().getDeclaredMethods()) {
				if (method.getName().startsWith("get")
						&& (!method.getName().equals("getStars") || !method.getName().equals("getBusiness_id"))) {
					String str = (String) method.invoke(resta);
					if (str.isEmpty()) {
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

			
			response = "{\"open\": " + resta.getOpen() + ", \"url\": \"" + resta.getUrl() + "\", " + "\"longitude\": "
					+ resta.getLongitude() + ", \"neighborhoods\": " + "[" + neighbourhoods + "], \"business_id\": "
					+ "\"" + resta.getBusiness_id() + "\", \"name\": \"" + resta.getName() + "\", "
					+ "\"categories\": [" + categories + "], \"state\": \"" + resta.getState() + "\", " + "\"type\": \""
					+ resta.getType() + "\", \"stars\": " + resta.getStars() + ", \"city\": \"" + resta.getCity()
					+ "\", \"full_address\": \"" + resta.getFull_address() + "\", " + "\"review_count\": "
					+ resta.getReview_count() + ", " + "\"photo_url\": \"" + resta.getPhoto_url() + "\", "
					+ "\"schools\": [" + schools + "], \"latitude\": " + resta.getLatitude() + ", \"price\": "
					+ resta.getPrice() + "}";

			record = parseLine(response, new Restaurant().getClass());
			// Updating restaurantTable
			restaurantTable.addRecord(record);

			return response;

		}
		case "ADDREVIEW": {
			List<Record> reviewRecord = new LinkedList<Record>();

			// Parses in the review as a record
			Record record = parseLine(details, new Review().getClass());
			reviewRecord.add(record);
			
			// converts it into a review
			List<Review> review = YelpDB.getReviews(reviewRecord);
			
			// Checks if a review was made.
			if (review.size() == 0)
				return "ERR: INVALID_REVIEW_STRING";
			
			Review revi = null;
			
			// Finally have a pointer to the review that was made.
			for (Review rev : review) {
				revi = rev;
				break;
			}

			
			// Checks if the details is a valid JSON line for ADDREVIEW
			if (revi.getBusiness_id().isEmpty()) {
				return "ERR: INVALID_RESTAURANT_STRING";
			}
			
			if (revi.getDate().isEmpty()) {
				return "ERR: INVALID_RESTAURANT_STRING";
			}
			
			if (revi.getStars() > 0 && revi.getStars() <=5) {
				return "ERR: INVALID_RESTAURANT_STRING";
			}
			
			if (revi.getUser_id().isEmpty()) {
				return "ERR: INVALID_RESTAURANT_STRING";
			}
			
			if (revi.getText() == null) {
				revi.setText("");
			}
			
			// Checking if the user exists
			Table userTable = database.getTableOf("users");
			List<User> users = YelpDB.getUsers(userTable.getRecords());
			String ids = revi.getUser_id();
			int user_id_index = -1;
			
			for(int i = 0; i < users.size(); i++) {
				if(users.get(i).getUser_id().equals(ids)) {
					user_id_index = i;
					break;
				}
			}
			
			if(user_id_index == -1)
				return "ERR: NO_SUCH_USER";
			
			// checking if the restaurant exists
			Table restaurantTable = database.getTableOf("restaurants");
			List<Restaurant> restaurants = YelpDB.getRestaurants(restaurantTable.getRecords());
			
			ids = revi.getBusiness_id();
			int business_id_index = -1;
			
			for(int i = 0; i < restaurants.size(); i++) {
				if(restaurants.get(i).getBusiness_id().equals(ids)) {
					business_id_index = i;
					break;
				}
			}
			
			if(business_id_index == -1)
				return "ERR: NO_SUCH_RESTAURANT";
			
			// Updating user 
			int count = users.get(user_id_index).getReview_count();
			double stars = users.get(user_id_index).getAverage_stars();
			
			stars = ((stars * count) + revi.getStars()) / (count + 1);
			count++;
			
			users.get(user_id_index).setAverage_stars(stars);
			users.get(user_id_index).setReview_count(count);
			
			List<Record> table = userTable.getRecords();
			
			int review_count_index = -1;
			int average_stars_index = -1;
			user_id_index = -1;
			for(int i = 0; i < table.get(0).getSize(); i++) {
				
				if(table.get(0).getFieldAt(i).getTypeName().equals("user_id")) {
					user_id_index = i;
				}
				
				if(table.get(0).getFieldAt(i).getTypeName().equals("review_count")) {
					review_count_index = i;
				}
				
				if(table.get(0).getFieldAt(i).getTypeName().equals("average_stars")) {
					average_stars_index = i;
				}
				
				if(average_stars_index != -1 && review_count_index != -1 && user_id_index != -1) {
					break;
				}
			}
			
			
			// Actually updating the database for users
			for(int i = 0; i < table.size(); i++) {
				if(((String) table.get(i).getFieldAt(user_id_index).getValue()).equals(revi.getUser_id())) {
					table.get(i).addField(new Field<Integer>("review_count", count));
					table.get(i).addField(new Field<Double>("average_stars", stars));
					break;
				}
			}
			
			// Updating restaurant
			count = restaurants.get(business_id_index).getReview_count();
			stars = restaurants.get(business_id_index).getStars();
			
			stars = ((stars * count) + revi.getStars()) / (count + 1);
			count++;
			
			restaurants.get(business_id_index).setStars(stars);
			restaurants.get(business_id_index).setReview_count(count);
			
			table = restaurantTable.getRecords();
			
			review_count_index = -1;
			average_stars_index = -1;
			business_id_index = -1;
			for(int i = 0; i < table.get(0).getSize(); i++) {
				
				if(table.get(0).getFieldAt(i).getTypeName().equals("business_id")) {
					business_id_index = i;
				}
				
				if(table.get(0).getFieldAt(i).getTypeName().equals("review_count")) {
					review_count_index = i;
				}
				
				if(table.get(0).getFieldAt(i).getTypeName().equals("stars")) {
					average_stars_index = i;
				}
				
				if(average_stars_index != -1 && review_count_index != -1 && user_id_index != -1) {
					break;
				}
			}
			
			
			// Actually updating the database for restaurants
			for(int i = 0; i < table.size(); i++) {
				if(((String) table.get(i).getFieldAt(business_id_index).getValue()).equals(revi.getBusiness_id())) {
					table.get(i).addField(new Field<Integer>("review_count", count));
					table.get(i).addField(new Field<Double>("stars", stars));
					break;
				}
			}
			
			// Generating a unique review_id
			Table reviewTable = database.getTableOf("reviews");
			List<Review> reviews = YelpDB.getReviews(reviewTable.getRecords());
			boolean isUnique = true;
			String review_id = "";
			while (isUnique) {
				boolean isNotUnique = false;

				UUID id = UUID.randomUUID();
				review_id = id.toString().substring(0, 22);

				for (Review rev : reviews) {
					if (rev.getReview_id().equals(review_id))
						isNotUnique = true;
				}
				isUnique = isNotUnique;
			}
			
			revi.setReview_id(review_id);
			
			// precaution
			revi.setType("review");
			
			revi.getVotes().setCool(0);
			revi.getVotes().setFunny(0);
			revi.getVotes().setUseful(0);
			
			
			response = "{\"type\": \"review\", \"business_id\": \""+ revi.getBusiness_id()  +"\", "
					+ "\"votes\": {\"cool\": 0, \"useful\": 0, \"funny\": 0}, \"review_id\": "
					+ "\""+ revi.getReview_id() + "\", \"text\": \"" + revi.getText() + "\", "
					+ "\"stars\": "+ revi.getStars() + ", \"user_id\": \"" + revi.getUser_id() + "\", "
					+ "\"date\": \""+  revi.getDate()  + "\"}";
			
			// parsing the final version of the record
			record = parseLine(response, new Review().getClass());
			// Updating reviewTable
			reviewTable.addRecord(record);
			
			return response;

		}
		case "PING": {
			response = "ALIVE";
			return response;
		}
		default:
			return "ERR: ILLEGAL_REQUEST";
		}
	}

	/**
	 * Method parseLine, which takes a line of json following the form of a row
	 * defined by this instances definingClass. Returns a record representation of
	 * the row with no guarantee on order.
	 *
	 * @param jsonRow
	 *            is a String for a row of json that fits the definedClass of this
	 *            instance. eg. String: {"emp_id": 1017, "address": {"street":"MG
	 *            Road","city":"Bangalore"}} Now in RecordClasses.. Employee {int
	 *            emp_id; Address address; //+Standard Getters and Setters} Address
	 *            {String street; String city; //+Standard Getters and Setters}
	 * @return A record with the type defined by this objects table type (which is
	 *         set during object construction).
	 * @throws Exception,
	 *             thrown if the jsonLine passed does not match the type defined by
	 *             the definingClass.
	 */
	private Record parseLine(String jsonRow, Class definingClass) throws Exception {
		// The record to return
		Record record = new Record(definingClass.getSimpleName().toLowerCase());

		// Parse the line and store into a new object of type defined in the
		// constructor.
		Gson gson = new Gson();

		// This line parses the row and populates the 'output' Objects properties
		// According to the values of fields in this jsonRow.
		// The output object is of type 'definingClass', which has to have
		// The same property names and types as the fields in the jsonRow string,
		// As well as respective getter and setter methods for each one.
		Object output = gson.fromJson(jsonRow, definingClass);
		// Throws exception if the jsonRow being parsed does not fit the class.

		for (Method method : definingClass.getDeclaredMethods()) {
			// Only use getter methods.
			if (method.getName().startsWith("get")) {
				// Use this getter that is assumed to be labeled "get[parameterName]" to create
				// a field.
				Field field = new Field<>(method.getName().substring(3).toLowerCase(), method.invoke(output));
				record.addField(field);
			}
		}

		return record;
	}
	
	private String handleStructuredQueries(String line) {

		CharStream stream = new ANTLRInputStream(line);
		StructuredQueriesLexer lexer = new StructuredQueriesLexer(stream);
		TokenStream tokens = new CommonTokenStream(lexer);
		StructuredQueriesParser parser = new StructuredQueriesParser(tokens);
		ParseTree tree = parser.andExpr();
		ParseTreeWalker walker = new ParseTreeWalker();
		StructuredQueriesListener listener = new StructuredQueriesDoer(database);
		Trees.inspect(tree, parser);
		walker.walk(listener, tree);
		
		return ((StructuredQueriesDoer) listener).toString();
	}

	// This one is for testing purposes... comment out when not needed...
	public static String handleStructuredQueries(String line, YelpDB data) {

		CharStream stream = new ANTLRInputStream(line);
		StructuredQueriesLexer lexer = new StructuredQueriesLexer(stream);
		TokenStream tokens = new CommonTokenStream(lexer);
		StructuredQueriesParser parser = new StructuredQueriesParser(tokens);
		ParseTree tree = parser.andExpr();
		ParseTreeWalker walker = new ParseTreeWalker();
		StructuredQueriesListener listener = new StructuredQueriesDoer(data);
		Trees.inspect(tree, parser);
		walker.walk(listener, tree);

		return ((StructuredQueriesDoer) listener).toString();
	}

	public static String converStringArray(String[] arr) {
		String s = "";
		for (int i = 0; i < arr.length; i++) {
			s += "\"";
			s += arr[i];
			s += "\"";

			if ((i + 1) < arr.length) {
				s += ", ";
			}
		}
		return s;
	}

}
