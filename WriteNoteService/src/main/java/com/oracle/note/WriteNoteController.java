package com.oracle.note;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WriteNoteController {

	private static final Logger LOGGER = LoggerFactory.getLogger(WriteNoteController.class);

	@Autowired
	private InstanceInformationService instanceInformationService;

	@Autowired
	private PathServiceProxy proxy;
	
	private static String noteString;
	private static String timestamp;

	@GetMapping("/")
	public String imHealthy() {
		return "{healthy:true}";
	}
	
	@SuppressWarnings("resource")
	@PostMapping("/write/note")
	public String writeNote(@RequestBody String note) throws IOException {
		String response = proxy.getLatestPath();
		String thisInstanceInfo = instanceInformationService.retrieveInstanceInfo();
		noteString=note;
		timestamp=response;
		return "Note Written "+thisInstanceInfo+" REMOTE SERVICE INFO : "+response;
	}
	

	@SuppressWarnings("resource")
	@GetMapping("/get/note")
	public ResponseEntity<Object> writeNote() throws IOException {

		
		String thisInstanceInfo = instanceInformationService.retrieveInstanceInfo();
		LOGGER.info((timestamp + thisInstanceInfo).replace(" ", "_"));
		timestamp=timestamp.replace(":","_");
		//String filename = "C://Notes1//"+timestamp.substring(0,21)+".txt";
		String filename = "/tmp"+timestamp.substring(0,21)+".txt";
		File file = new File(filename);
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(noteString);
		fileWriter.append((timestamp + thisInstanceInfo).replace(" ", "_"));
		fileWriter.close();
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		HttpHeaders headers = new HttpHeaders();

		headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");

		ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/txt")).body(resource);

		return responseEntity;

		
	}

}