package com.oracle.note;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "path-provide-service", url = "${PATH_PROVIDE_URI:http://localhost}:8900")//    // for version 0.0.2
//@FeignClient(name = "path-provide-service", url = "${PATH_PROVIDE_SERVICE_HOST:http://localhost}:8900")  // for version 0.0.1
//@FeignClient(name = "currency-exchange-service")//Kubernetes Service Name
public interface PathServiceProxy {
	
	@GetMapping("/get/path")
	public String getLatestPath();
}

//Docker command
//docker run -p 8901:8901 -e PATH_PROVIDE_SERVICE_HOST=27544ee662d7 --link=27544ee662d7 pratik502/write-note:0.0.1
//docker run -p 8900:8900 pratik502/path-provide:0.0.1