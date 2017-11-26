package ca.ece.ubc.cpen221.mp5.database;

import com.google.gson.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.*;

public class ParseJsonFile {

    private String tableName;
    private File file;
    private Class definingClass;

    /**
     * Parser, which takes the fileName of data in JSON format that should contain
     * rows of records of the same type which fields satisfy the definingClass.
     * <p>
     * The definingClass is used to define the type of the records (each row in the file).
     * The class must contain properties of the correct type, correct name, and correct
     * amount to satisfy every field in a row in the file. This class's only methods ust be
     * standard getter's and setter's for each variable and nothing more. This class must be in the
     * package src.main.java.RecordClasses as well.
     * <p>
     * See examples of 'Restaurant', 'Review', and 'User' in the RecordClasses package.
     *
     * @param filePath the path for the .json file to parse. Must contain a table.
     * @param definingClass the class that defines the type for records (rows) in the file.
     * @throws FileNotFoundException if the file passed can not be read.
     */
    public ParseJsonFile(String filePath, Class definingClass) throws FileNotFoundException {

        this.tableName = (definingClass.getSimpleName() + "s");
        this.file = new File(filePath);
        this.definingClass = definingClass;

        if (!file.canRead()) {
            throw new FileNotFoundException();
        }
    }

    /**
     * Parses this file and returns a Table representation containing
     * all the records in the file that fit the class defined by 'definingClass'
     * <p>
     * A Table is a list of records of the same type.
     * If the file given does not match the format of a table
     * and/or rows in the table do not match the type, then
     * this method returns an empty table.
     *
     * @return Table
     */
    public Table makeTable() {

        Scanner scanner;
        try {
            scanner = new Scanner(file);

            Table table = new Table(tableName);
            int i = 0;

            //Scan each row of the file.
            while (scanner.hasNextLine()) {
                String row = scanner.nextLine();
                i++;

                try {
                    //Parse this row into a record in terms of the defining class and add
                    //it to the table. If the row is invalid, catch the exception.
                    table.addRecord(parseLine(row));
                } catch (Exception e) {
                    System.out.println("Invalid Data found in file when generating table" +
                            ", at row number " + i + ".\n\tRow was not added to table.");
                }
            }

            scanner.close();
            return table;

            //The file should open since we checked in constructor, so return null.
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
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
    private Record parseLine(String jsonRow) throws Exception {
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

}
