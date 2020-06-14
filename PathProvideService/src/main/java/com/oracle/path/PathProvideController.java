package com.oracle.path;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PathProvideController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PathProvideController.class);


	@Autowired
	private InstanceInformationService instanceInformationService;

	@GetMapping("/")
	public String imHealthy() {
		return "{healthy:true}";
	}

	//http://localhost:8900/get/path
	@GetMapping("/get/path")
	public String retrieveExchangeValue(@RequestHeader Map<String, String> headers) {
		Date timestamp=new Date();
		printAllHeaders(headers);
		return timestamp.toString().replace(" ","_")+"_"+instanceInformationService.retrieveInstanceInfo().replace(" ","_");
	}

	private void printAllHeaders(Map<String, String> headers) {
		headers.forEach((key, value) -> {
			LOGGER.info(String.format("Header '%s' = %s", key, value));
		});
	}
	
	public static void main(String[] args) {
		Date timestamp=new Date();
		System.out.println("Current timestamp "+timestamp.toString().replace(" ","_"));
	}
}
