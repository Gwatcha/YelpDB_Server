import ca.ece.ubc.cpen221.mp5.database.Field;
import ca.ece.ubc.cpen221.mp5.database.Record;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestRecord {

	@Test
	public void test() {
		Record bookRecord = new Record("book");
		bookRecord.addField(new Field<String>("name", "East Of Eden"));
		bookRecord.addField(new Field<String>("author", "John Steinbeck"));
			assertEquals("book", bookRecord.getName());
			assertEquals(2, bookRecord.getSize());
			
		
		Record gameRecord = new Record("videogame");
		gameRecord.addField(new Field<String>("name", "Stardew Valley"));
		gameRecord.addField(new Field<String>("maker", "Chucklefish"));
		
			//Checking type methods for fields and records. Name records are same field type.
			assertFalse(bookRecord.sameTypeAs(gameRecord));
			assertFalse(bookRecord.hashCode() == gameRecord.hashCode());
			assertTrue(bookRecord.getFieldAt(0).sameTypeAs(gameRecord.getFieldAt(0)));
			assertTrue(!bookRecord.getFieldAt(0).sameTypeAs(gameRecord.getFieldAt(1)));
			
		//Checking duplicate field entries.	Should return false and not alter.
		gameRecord.addField(new Field<String>("name", "Starbound"));
			assertEquals(2, gameRecord.getSize());
			assertEquals(null, gameRecord.getFieldAt(2));
			
		//Check .getType and adding new field.
		gameRecord.addField(new Field<Integer>("hours played", 419));	
			assertEquals(3, gameRecord.getSize());
			assertEquals(Integer.class, gameRecord.getFieldAt(2).getType());
			assertEquals(String.class, gameRecord.getFieldAt(1).getType());
			assertFalse(gameRecord.sameTypeAs(bookRecord));
		
			
		//Lastly, print both of them out.
		System.out.println(gameRecord.toString());
			
			

	}

}
