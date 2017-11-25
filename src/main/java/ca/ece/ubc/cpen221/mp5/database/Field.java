package ca.ece.ubc.cpen221.mp5.database;

/*
 * *Fields are the basic units in a DB, as an example
 * "neighborhood": ["Tsawwassen", "UBC"] is a field with type string array.
 * Once created, it is immutable unless the value passed it mutable and it mutated.
*/
public class Field<T> {

	private String typeName;
	private T value;

	
	public Field(String typeName, T value) {
		this.typeName = typeName;
		this.value = value;
	}

	public Class<?> getType() {
		return value.getClass();
	}

	/**
	 * getValue method, which returns a value of type T which this field was defined with,
	 * if T is not a primitive type, then this will return the pointer to the Object<T> on the heap 
	 * and thus any changes to the object through the returned value reflect in this field object.
	 * 
	 * @return
	 */
	T getValue() {
		return value;
	}
	
	/**
	 * Returns the name of this field.
	 * eg. "neighborhood", "name", "rating".
	 * @return
	 */
	public String getTypeName() {
		return typeName;
	}
	
	/**
	 * Method sameTypeAs which returns true if this field and another field
	 * match in type and name. 
	 */
	public Boolean sameTypeAs(Field<?> field2) {
		
		//field2.getTypeName();
		if (typeName.equals(field2.getTypeName())) {
			if (this.getType().equals(field2.getType())) {
				return true;
			}
		}
		
		return false;
	}
	

	@Override
	public String toString() {
		String id = ("\"" + typeName + "\": \"" + value.toString() + "\"");
		return id;
	}

}
