package ca.ece.ubc.cpen221.mp5.database;

import java.util.ArrayList;

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
public class Table {

	private String name;
	private ArrayList<Record> table;

	/**
	 * Creates an empty record with label 'name'.
	 * @param name
	 */
	public Table(String name) {
		this.name = name;
		this.table = new ArrayList<Record>();
	}

	/**
	 * Adds a record to this table
	 * @param a record which is of the type that this table.
	 * @return true if the record was successfully added, false if this record
	 * 		  is either empty or not of the same type of records already in this table.
	 */
	public Boolean addRecord(Record record) {
		if (table.isEmpty() && record.getSize() != 0) {
			table.add(record);
			return true;
		}

		// The first record added defines the type, so compare it to that one.
		else if (record.sameTypeAs(table.get(0))) {
			table.add(record);
			return true;
		}

		return false;
	}

	public String getName() {
		return name;
	}
	
	/**
	 * method which returns size meaning the number of records or 'rows' 
	 * in this table.
	 * @return
	 */
	public int getSize() {
		return table.size();
	}

	/**
	 * Returns unique hashcode for this table.
	 */
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

	@Override
	public String toString() {
		String id = "";

		for (Record field : table) {
			id = new String(id + "\n" + field.toString());
		}

		return (id);
	}

}
