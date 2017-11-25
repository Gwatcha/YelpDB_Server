package ca.ece.ubc.cpen221.mp5.database;

import java.util.ArrayList;

/**
 * A record is one line in a .json file.
 * It is essentially a collection of fields.
 * Records with the same name do not guarantee
 * that they hold the same fields, that needs to be 
 * checked separately.
 * 
 * @author Michael
 */
public class Record {

	private String name;
	private ArrayList<Field<?>> record;

	/**
	 * Creates an empty record with label 'name'.
	 * @param name
	 */
	public Record(String name) {
		this.name = name;
		this.record = new ArrayList<Field<?>>();
	}

	/**
	 * Adds a field to this record, if a field of this type already
	 * exists in this record, replace that one with the passed one.
	 * 
	 * @param field, can not be null
	 */
	public void addField(Field<?> field) {

		Boolean replacedAlready = false;

		for (Field<?> existantField : record) {
			if (existantField.sameTypeAs(field)) {
				int i = record.indexOf(existantField);
				record.add(i, field);
				// The existing one got shifted right by add.
				record.remove(i + 1);
				replacedAlready = true;
				break;
			}
		}

		if (!replacedAlready) {
			record.add(field);
		}
	}

	/**
	 * getFieldAt, which returns the Field at the specified position. 
	 * Field positions start at 0.
	 * @param index
	 * @return the Field<?> at the specified positions of the Record. 
	 * 			null if there is no Field at this index.
	 */
	public Field<?> getFieldAt(int index) {
		if (index < record.size() && index >= 0) {
			return record.get(index);
		}
		return null;
	}

	public String getName() {
		return name;
	}

	/**
	 * @return the number of fields stored in this record.
	 */
	public int getSize() {
		return record.size();
	}

	/**
	 * Method which checks if this record and another is of the same type.
	 * Two records are the same type if their sequence of fields are of equal type at matching spots.
	 * A sequence of fields is the same type of another field if they match in type and name.
	 * 
	 * @param record2
	 * @return true if records are matching type, false otherwise.
	 */
	public boolean sameTypeAs(Record record2) {

		if (record.size() == record2.getSize() && record2.getSize() > 0) {
			for (int i = 0; i < record.size(); i++) {
				if (!this.getFieldAt(i).sameTypeAs(record2.getFieldAt(i))) {
					return false;
				}
			}
			return true;
		}
		return false;

	}

	@Override
	public String toString() {
		String id = "";

		for (Field<?> field : record) {
			id = new String(id + field.toString() + ", ");
		}

		return ("{" + id.substring(0, id.length() - 2) + "}");
	}

	/**
	 * Returns unique hashcode for this sequence.
	 */
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

}
