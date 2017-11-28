For the meeting:
Both students should attend.
You should identify the datatypes needed to implement the in-memory database.
	What are the methods?
	What is the representation?
	What are the rep invariants and abstraction functions?
Think about extensibility.
	Can your design be used beyond the Yelp dataset and the algorithms needed for MP5?
	Are you using subtypes to allow for reuse/extensibility?
Bring a printout with key details (as indicated above). Ideally, you should ensure that this document is available on your github repo as a markdown file (with a .md extension).

YelpDB methods:
    
 Constructor:
 YelpDB(String restaurantsFile, String reviewsFile, String usersFile);

    /**
     * Perform a structured query and return the set of objects that matches the
     * query
     *
     * @param queryString
     * @return the set of objects that matches the query
     */
    Set<T> getMatches(String queryString);

     /**
     * Cluster objects into k clusters using k-means clustering
     *
     * @param k
     *            number of clusters to create (0 < k <= number of objects)
     * @return a String, in JSON format, that represents the clusters
     */
    String kMeansClusters_json(int k);

     /**
     *
     * @param user
     *            represents a user_id in the database
     * @return a function that predicts the user's ratings for objects (of type
     *         T) in the database of type MP5Db<T>. The function that is
     *         returned takes two arguments: one is the database and other other
     *         is a String that represents the id of an object of type T.
     */
    ToDoubleBiFunction<MP5Db<T>, String> getPredictorFunction(String user);


    /**
     * Get a list of all the restaurants in the Database.
     *
     * @param records
     *             List of JSON lines, that represent the Restaurant class
     * @return List of Restaurant objects.
     */
    List<Restaurant> getRestaurants(List<Record> records);

  
    /**
     * Get a list of all the reviews in the Database.
     *
     * @param records
     *             List of JSON lines, that represent the Reviews class
     * @return List of Review objects.
     */
    List<Review> getReviews(List<Record> records);


    /**
     * Get a list of all the Users in the Database.
     *
     * @param records
     *             List of JSON lines, that represent the User class
     * @return List of User objects.
     */
    List<User> getusers (List<Record> records);
