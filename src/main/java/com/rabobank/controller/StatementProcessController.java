package com.rabobank.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.rabobank.constant.BankConstants;
import com.rabobank.model.Response;
import com.rabobank.model.Record;
import com.rabobank.service.ExtractorService;
import com.rabobank.service.ValidatorService;

/**
 * 
 * @author Anitha C
 *
 */

@Controller
@RequestMapping("/rabobank")
public class StatementProcessController {

	@Autowired
	private ValidatorService validatorService;

	@Autowired
	private ExtractorService extractorService;

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public @ResponseBody Response test() throws Exception {
		Response response = new Response();
		return response;
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String upload() throws Exception {
		
		return "upload";
	}

	
	@RequestMapping(value = "/processStatement", method = RequestMethod.POST)
	public @ResponseBody Response handleFileUpload(@RequestParam("file") MultipartFile multipart) throws Exception {
		Response response = new Response();
		if (!multipart.isEmpty()) {
			
	
			if (FilenameUtils.getExtension(multipart.getOriginalFilename()).equalsIgnoreCase(BankConstants.TYPE_CSV)) {
				List<Record> errorRecords = new ArrayList<Record>();
				File csvFile = new File(System.getProperty("java.io.tmpdir")+"/"+multipart.getOriginalFilename());
				multipart.transferTo(csvFile);
				
				List<Record> extractedRecords = extractorService.extractStatmentFromCSV(csvFile);
				errorRecords.addAll(validatorService.getDuplicateRecords(extractedRecords));						
				
				errorRecords.addAll(validatorService.getEndBalanceErrorRecords(extractedRecords));
				
				HashSet<Record> recset = new HashSet<>();
				errorRecords = errorRecords.stream().filter(e -> recset.add(e)).collect(Collectors.toList());
				
				
				if (!errorRecords.isEmpty()) {
					response.setResponseCode(BankConstants.HTTP_CODE_SUCCESS);
					response.setResponseMessage(BankConstants.VALIDATION_ERROR);
					response.setRecords(errorRecords);
				} else {
					response.setResponseCode(BankConstants.HTTP_CODE_SUCCESS);
					response.setResponseMessage(BankConstants.VALIDATION_SUCCESS);
				}
			} else if (FilenameUtils.getExtension(multipart.getOriginalFilename()).equalsIgnoreCase(BankConstants.TYPE_XML)) {
				List<Record> errorRecords = new ArrayList<Record>();
				
				//File xmlFile = new File(multipart.getOriginalFilename());
				File xmlFile = new File(System.getProperty("java.io.tmpdir")+"/"+multipart.getOriginalFilename());
				
				multipart.transferTo(xmlFile);
				List<Record> extractedRecords = extractorService.extractStatmentFromXML(xmlFile);
				errorRecords.addAll(validatorService.getDuplicateRecords(extractedRecords));
				errorRecords.addAll(validatorService.getEndBalanceErrorRecords(extractedRecords));
				if (!errorRecords.isEmpty()) {
					response.setResponseCode(BankConstants.HTTP_CODE_SUCCESS);
					response.setResponseMessage(BankConstants.VALIDATION_ERROR);
					response.setRecords(errorRecords);
				} else {
					response.setResponseCode(BankConstants.HTTP_CODE_SUCCESS);
					response.setResponseMessage(BankConstants.VALIDATION_SUCCESS);
				}
			} else {
				response.setResponseCode(BankConstants.HTTP_CODE_INVALID_INPUT);
				response.setResponseMessage(BankConstants.UNSUPORTED_FILE_FORMAT);
			}
		} else {
			response.setResponseCode(BankConstants.HTTP_CODE_INVALID_INPUT);
			response.setResponseMessage(BankConstants.INVALID_INPUT);
		}
		return response;
	}

	@ExceptionHandler(Exception.class)
	public @ResponseBody Response handleException(HttpServletRequest request, Exception ex) {
		Response response = new Response();
		response.setResponseCode(BankConstants.HTTP_CODE_ERROR);
		response.setResponseMessage(BankConstants.UNEXPECTED_SERVER_ERROR);
		ex.printStackTrace();
		return response;
	}

}
