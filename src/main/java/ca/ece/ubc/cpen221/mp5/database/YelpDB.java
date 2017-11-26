
package ca.ece.ubc.cpen221.mp5.database;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Set;
import java.util.function.ToDoubleBiFunction;

import RecordClasses.*;

public class YelpDB<T> implements MP5Db<T> {

    ArrayList<Table> dataBase;

    YelpDB(String restaurantsFile, String reviewsFile, String usersFile) throws FileNotFoundException {
        dataBase = new ArrayList<>();

        //Initialize parsers.
        ParseJsonFile restaurantsParser = new ParseJsonFile(restaurantsFile, Restaurant.class);
        ParseJsonFile reviewsParser = new ParseJsonFile(reviewsFile, Review.class);
        ParseJsonFile usersParser = new ParseJsonFile(usersFile, User.class);

        //Add tables to DB.
        dataBase.add(restaurantsParser.makeTable());
        dataBase.add(reviewsParser.makeTable());
        dataBase.add(usersParser.makeTable());
    }


    //~~~~~~~~~~~~Interface methods~~~~~~~~~~~~~~~

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
