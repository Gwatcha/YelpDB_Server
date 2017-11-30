import ca.ece.ubc.cpen221.mp5.Field;
import ca.ece.ubc.cpen221.mp5.Record;
import ca.ece.ubc.cpen221.mp5.Table;
import org.junit.Test;

import static org.junit.Assert.*;

//Test table tests Table.java as well as Field.java and Record.java
public class TestTable {

	//Make records of type movie.
	private static Record generateMovieRecord(String name, int year, String director, 
			double rottenTomatoesRating, double imdbRating, String[] genres, boolean onNetflix) {
		Record record = new Record("movie");
		
		Field<String> field1 = new Field<>("name", name);
		Field<Integer> field2 = new Field<Integer>("year", year);
		Field<String> field3 = new Field<String>("director", director);
		Field<Double> field4 = new Field<Double>("rotten tomatoes rating", rottenTomatoesRating);
		Field<Double> field5 = new Field<Double>("imdb rating", imdbRating);
		Field<String[]> field6 = new Field<String[]>("genres", genres);
		Field<Boolean> field7 = new Field<Boolean>("netflix", onNetflix);
		
		record.addField(field1);
		record.addField(field2);
		record.addField(field3);
		record.addField(field4);
		record.addField(field5);
		record.addField(field6);
		record.addField(field7);
		return record;


	}

	@Test
	public void testTable() {
		Table table = new Table("movietable");
		Record movieRecord1 = TestTable.generateMovieRecord
			("Eternal Sunshine of the Spotless Mind", 2004, "Michel Gondry", .93, 8.3, new String[] {"Science fiction film", "Drama"}, false);
			assertTrue(table.addRecord(movieRecord1));
			assertEquals(1, table.getSize());
			assertTrue(table.getName().equals("movietable"));
			table.hashCode();
		//	System.out.println(table.toString());
			
		//Now, try to add record of a different type. .add should return false and not change anything.
		Record bookRecord = new Record("book");
		bookRecord.addField(new Field<String>("name", "East Of Eden"));
		bookRecord.addField(new Field<String>("author", "John Steinbeck"));
			table.addRecord(bookRecord);
			assertTrue(!table.addRecord(bookRecord));
			assertEquals(1, table.getSize());
		//	System.out.println(table.toString());
			
		//Add some more movies.	
		Record movieRecord2 = TestTable.generateMovieRecord
				("The Mirror", 1975, "Andrei Tarkovsky", 1.00, 8.2, new String[] {"Drama", "Biography"}, false);
				assertTrue(table.addRecord(movieRecord2));	
				assertEquals(2, table.getSize());
	    //		System.out.println(table.toString());	
	    		
	    Record movieRecord3 = TestTable.generateMovieRecord
	    		("Stalker", 1979, "Andrei Tarkovsky", 1.00, 8.1, new String[] {"Fantasy", "Mystery"}, false);
	    		assertTrue(table.addRecord(movieRecord3));	
	    		assertEquals(3, table.getSize());
	   // 	    System.out.println(table.toString());		
	    	    
	    Record movieRecord4 = TestTable.generateMovieRecord
	    	    ("Mulholland Drive", 2001, "David Lynch", -1, 8.0, new String[] {"Crime film", "Mystery"}, false);
	    	    assertTrue(table.addRecord(movieRecord4));	
	    	    assertEquals(4, table.getSize());
	    //	    	  System.out.println(table.toString());	    
	    	    	  
	    Record movieRecord5 = TestTable.generateMovieRecord
	    		("Apocalypse Now", 1979 , "Francis Ford Coppola", .98, 8.5, new String[] {"Drama", "Adventure"}, false);
	    		     assertTrue(table.addRecord(movieRecord5));	
	    		     assertEquals(5, table.getSize());
	    		     System.out.println(table.toString());
	    		     
	

	}

}




























