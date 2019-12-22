package com.rabobank.testcase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;


import com.rabobank.model.Record;
import com.rabobank.service.ExtractorServiceImpl;
import com.rabobank.service.ValidatorServiceImpl;

/**
 * 
 * @author Anitha C 
 * 
 * This test cases covers all service class methods of this
 * application (100% code coverage)
 *
 */

@RunWith(MockitoJUnitRunner.class)
public class RaboBankTestCases {
	
	
	     
	    @InjectMocks
	    ValidatorServiceImpl validatorService;
	     
	    @InjectMocks
	    ExtractorServiceImpl extractorService;

	/**
	 * Type : Positive 
	 * scenario : Duplicate check in given Rabobank Customer
	 * Statement
	 */
	@Test
	public void getDuplicateRecordsTestCaseWithDuplilcate() {
		List<Record> inputList = Arrays.asList(
				new Record(172833, "NL69ABNA0433647324", 66.72, -41.74, "Tickets for Willem Theu�", 24.98),
				new Record(172833, "NL43AEGO0773393871", 16.52, +43.09, "Tickets for Willem Theu�", 59.61));
		
		List<Record> duplicateRecords = validatorService.getDuplicateRecords(inputList);
		assertEquals(inputList.size(), duplicateRecords.size());

	}

	/**
	 * Type : Negative 
	 * scenario : Duplicate check in given Rabobank Customer
	 * Statement
	 */
	@Test
	public void getDuplicateRecordsTestCaseWithOutDuplilcate() {
		List<Record> inputList = Arrays.asList(
				new Record(172823, "NL69ABNA0433647324", 66.72, -41.74, "Tickets for Willem Theu�", 24.98),
				new Record(172833, "NL43AEGO0773393871", 16.52, +43.09, "Tickets for Willem Theu�", 59.61));
		
		List<Record> duplicateRecords = validatorService.getDuplicateRecords(inputList);
		assertEquals(0, duplicateRecords.size());

	}

	/**
	 * Type : Positive 
	 * scenario : EndBalance validation in given Rabobank Customer
	 * Statement
	 */
	@Test
	public void getEndBalanceErrorRecordsTestCaseWithWrongValue() {
		List<Record> inputList = Arrays.asList(
				new Record(172833, "NL69ABNA0433647324", 60.00, -40.00, "Tickets for Willem Theu�", 24.98),
				new Record(172833, "NL43AEGO0773393871", 10.01, +3.02, "Tickets for Willem Theu�", 19.80));
		
		List<Record> endBalanceErrorRecords = validatorService.getEndBalanceErrorRecords(inputList);
		assertEquals(inputList.size(), endBalanceErrorRecords.size());

	}

	/**
	 * Type : Negative 
	 * scenario : EndBalance validation in given Rabobank Customer
	 * Statement
	 */
	@Test
	public void getEndBalanceErrorRecordsTestCaseWithCorrectValue() {
		List<Record> inputList = Arrays.asList(
				new Record(172833, "NL69ABNA0433647324",60.00, -40.00, "Tickets for Willem Theu�",20.00),
				new Record(172833, "NL43AEGO0773393871",10.01, +3.02, "Tickets for Willem Theu�",13.03));
		
		List<Record> endBalanceErrorRecords = validatorService.getEndBalanceErrorRecords(inputList);
		assertEquals(0, endBalanceErrorRecords.size());
	}

	/**
	 * Type : Positive 
	 * scenario : validating extracted records size and number of lines in input file
	 * values as POJO object for validation process
	 */
	@Test
	public void checkRecordsSize() {
		
		File inputFile = new File("records.csv");
		try {
			int totalLineInInputCSV = RaboBankTestCases.getNumberOfLine(inputFile);
			List<Record> extractedRecords = extractorService.extractStatmentFromCSV(inputFile);
			assertEquals(totalLineInInputCSV-1, extractedRecords.size());
		} catch (IOException e) {
		fail("File processing error!!" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Type : Positive 
	 * scenario : Processing the input XML file and extracting
	 * values as POJO object for validation process
	 */
	@Test
	public void extractStatmentFromXMLTestCase() {
		
		File inputFile = new File("records.xml");
		try {
			int totalLineInInputXML = 10; /// let. input XML file has 10 records.
			List<Record> extractedRecords = extractorService.extractStatmentFromXML(inputFile);
			assertEquals(totalLineInInputXML, extractedRecords.size());
		} catch (Exception e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}
	
	//utility methods
	
	public static int getNumberOfLine(File file) throws IOException {
		InputStream is = new BufferedInputStream(new FileInputStream(file));
		try {
			byte[] c = new byte[1024];
			int count = 0;
			int readChars = 0;
			boolean empty = true;
			while ((readChars = is.read(c)) != -1) {
				empty = false;
				for (int i = 0; i < readChars; ++i) {
					if (c[i] == '\n') {
						++count;
					}
				}
			}
			return (count == 0 && !empty) ? 1 : count;
		} finally {
			is.close();
		}
	}
}
