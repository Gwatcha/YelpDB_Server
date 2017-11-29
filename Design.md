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

JSON Datatypes:

Field Class:

	/**
 	* *Fields are the basic units in a DB, as an example
 	* "neighborhood": ["Tsawwassen", "UBC"] is a field with type string array.
	 * Once created, it is immutable unless the value passed it mutable and it mutated.
	*/
	public Field(String typeName, T value) ;

Field Class methods: 
	
	/**
	 * getValue method, which returns a value of type T which this field was defined with,
	 * if T is not a primitive type, then this will return the pointer to the Object<T> on the heap 
	 * and thus any changes to the object through the returned value reflect in this field object.
	 * 
	 * @return
	 */
	 
	T getValue()

	/**
	 * Returns the name of this field.
	 * eg. "neighborhood", "name", "rating".
	 * @return
	 */
	 
	public String getTypeName()
	
	/**
	* Returns the name of this field and the value
	*
	*/
	public String toString()
	
Record Class:	

	/**
	 * A record is one line in a .json file.
	 * It is essentially a collection of fields.
	 * Records with the same name do not guarantee
	 * that they hold the same fields, that needs to be 
	 * checked separately.
	 * 
	 * @author Michael
 	*/	
	public Record(String name)
	
Record Class Methods:

	/**
	 * Adds a field to this record, if a field of this type already
	 * exists in this record, replace that one with the passed one.
	 * 
	 * @param field, can not be null
	 */
	public void addField(Field<?> field)
	
	
	/**
	 * getFieldAt, which returns the Field at the specified position. 
	 * Field positions start at 0.
	 * @param index
	 * @return the Field<?> at the specified positions of the Record. 
	 * 			null if there is no Field at this index.
	 */
	public Field<?> getFieldAt(int index)
	
	
	/**
	 * @return the number of fields stored in this record.
	 */
	public int getSize()
	
	/**
	 * Method which checks if this record and another is of the same type.
	 * Two records are the same type if their sequence of fields are of equal type at matching spots.
	 * A sequence of fields is the same type of another field if they match in type and name.
	 * 
	 * @param record2
	 * @return true if records are matching type, false otherwise.
	 */
	public boolean sameTypeAs(Record record2)


	/**
	* Returns the entire record ( JSON line) as a String
	*
	*/
	public String toString()
	
	
	
Table Class:

	/**
	 * A table is a collection of records of the same type.
	 * Two records are the same type of if they have the same name
	 * and sequence of fields with equal type.
	 * A sequence of fields is the same type of another field if they match in type and name.
	 * 
	 * An example of a table would be one of .json files for the YELP data. (restaurants users)
	 * @author Michal
	 *
	 */
	public class Table
	
Table Class Methods:
	/**
	 * Adds a record to this table, if this function returns false, it means the record
	 * was not added to the table due to an improper record being passed.
    	 *
	 * @param record which is a non empty record of the type that this table holds.
	 * @return true if the record was successfully added, false if this record
	 * 		  is either empty or not of the same type of records already in this table.
	 */
	public Boolean addRecord(Record record)
	
	/**
	 * method which returns size meaning the number of records or 'rows' 
	 * in this table.
	 * @return
	 */
	public int getSize()
	
	/**
	 * Returns all the records in this table
	 * @return 
	 * 			a list of all the records in this table
	 */
	public ArrayList<Record> getRecords()
	
YelpDB methods:
    
    /**
    * Constructor
    * YelpDB(String restaurantsFile, String reviewsFile, String usersFile);
    */

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
