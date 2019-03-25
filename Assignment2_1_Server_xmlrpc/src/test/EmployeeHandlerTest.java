package test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import java.io.File;
import java.util.Hashtable;
import java.util.Iterator;

import handler.EmployeeHandler;
import model.Employee;

public class EmployeeHandlerTest {
	EmployeeHandler classUnderTest;
	Hashtable<String, Employee> testData;
	
	@Before
	public void setUp() {
		classUnderTest = new EmployeeHandler();
		testData = EmployeeHandler.createTestData();
	}
	
	@Test
	public void testSerializeToDisk() {
		String filePath = System.getProperty("user.dir") + File.separator + "logs" + File.separator + "ourHashtable.txt";
		boolean status = classUnderTest.serializeToDisk(filePath, testData);
		assertTrue(status);
	}

	@Test
	public void testDeserializeFromDisk() {
		String filePath = System.getProperty("user.dir") + File.separator + "logs" + File.separator + "ourHashtable.txt";
		classUnderTest.serializeToDisk(filePath, testData);
		Hashtable<String, Employee> dataDeserialized = classUnderTest.deserializeFromDisk(filePath);
		
		int sizeExpected = testData.size();
		assertEquals(sizeExpected, dataDeserialized.size());
		
		// Go through each data member and compare
		Iterator<String> itExpected = testData.keySet().iterator();
		String[] keys = new String[sizeExpected];
		for (int i = sizeExpected - 1; i >= 0 && itExpected.hasNext(); --i) {
			keys[i] = itExpected.next();
		}
		for (int i = 0; i < sizeExpected; ++i) {
			assertEquals(testData.get(keys[i]), dataDeserialized.get(keys[i]));
		}
		
	}

}
