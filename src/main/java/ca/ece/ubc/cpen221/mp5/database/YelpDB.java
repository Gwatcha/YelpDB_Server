package ca.ece.ubc.cpen221.mp5.database;

import java.util.Set;
import java.util.function.ToDoubleBiFunction;

import ca.ece.ubc.cpen221.mp5.database.MP5Db;

public class YelpDB implements MP5Db<T> {

	
	YelpDB (String restrauntsFile, String reviewsFile, String usersFile) {
		
	}

	@Override
	public Set<T> getMatches(String queryString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String kMeansClusters_json(int k) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ToDoubleBiFunction<MP5Db<T>, String> getPredictorFunction(String user) {
		// TODO Auto-generated method stub
		return null;
	}
}
