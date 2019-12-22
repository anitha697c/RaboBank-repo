package com.rabobank.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.rabobank.model.Record;

/**
 * @author Anitha C
 */

@Service
public class ValidatorServiceImpl implements ValidatorService {

	/**
	 * @return List<Records> to get all duplicate records
	 */
	public List<Record> getDuplicateRecords(List<Record> records) {
		Map<Integer, Record> uniqueRecords = new HashMap<Integer, Record>();
		List<Record> duplicateRecords = new ArrayList<Record>();
		List<Record> finalDuplicateRecords = new ArrayList<Record>();
		
		records.forEach(record -> {
			if (uniqueRecords.containsKey(record.getTransactionRef())) {
				record.setFailureReason("Duplicate Reference");
				duplicateRecords.add(record);
			} else {
				uniqueRecords.put(record.getTransactionRef(), record);
			}
		});		
		
		finalDuplicateRecords.addAll(duplicateRecords);		
		duplicateRecords.forEach(record -> {	
			if (null != uniqueRecords.get(record.getTransactionRef())) {
				uniqueRecords.get(record.getTransactionRef()).setFailureReason("Duplicate Reference");
				finalDuplicateRecords.add(uniqueRecords.get(record.getTransactionRef()));
				
				uniqueRecords.remove(record.getTransactionRef());
			}
		});	
		return finalDuplicateRecords;
	}

	/**
	 * @return List<Records>  to get all records having incorrect end balance will be returned
	 */
	
	public List<Record> getEndBalanceErrorRecords(List<Record> records) {
		List<Record> endBalanceErrorRecords = new ArrayList<Record>();
		
		records.forEach(record -> {			
			if (Math.round(record.getStartBalance() + record.getMutation() - record.getEndBalance()) != 0) {	
				endBalanceErrorRecords.add(record);
				record.setFailureReason( (record.getFailureReason() == null ? "" : (record.getFailureReason() + ", ")) + "Incorrect End Balance");
			}
		});
		return endBalanceErrorRecords;
	}

}
