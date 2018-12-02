package com.accenture.interviewproj.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.accenture.interviewproj.security.AuthenticationToken;

@RestController
@RequestMapping("/api/hello")
public class HelloController {

	@GetMapping
	public ResponseEntity<?> getName() {
		return ResponseEntity.ok(new AuthenticationToken("Heelo"));
	}

	/**
	 * This method is executed when a GET request is
	 * made on localhost:8082/getExample
	 *//*
	@GetMapping("/getExample")
	public ResponseEntity<?> getExample() {
		try {
			return ResponseEntity
					.ok("Send any response with status Okay (200)");
		} catch (Exception ex) {
			// send any error status depending on Exception
			return ResponseEntity
					.status(HttpStatus.CONFLICT)
					.body(ex.getMessage());
		}
	}
	
	
	*//**
	 * This method is executed when a POST request is
	 * made on localhost:8082/postExample
	 * The RequestObject can be any object that depending
	 * on the process required
	 *//*
	@PostMapping("/postExample")
	public ResponseEntity<?> postExample(@RequestBody RequestObject requestObject) {
		try {
			//Process requestObject
			return ResponseEntity
					.ok("Send any response with status Okay (200)");
		} catch (Exception ex) {
			// send any error status depending on Exception
			return ResponseEntity
					.status(HttpStatus.CONFLICT)
					.body(ex.getMessage());
		}
	}
	
	*//**
	 * This method is executed when a PUT request is
	 * made on localhost:8082/putExample
	 * The RequestObject can be any object that depending
	 * on the process required
	 *//*
	@PutMapping("/putExample")
	public ResponseEntity<?> putExample(@RequestBody RequestObject requestObject) {
		try {
			//Process requestObject
			return ResponseEntity
					.ok("Send any response with status Okay (200)");
		} catch (Exception ex) {
			// send any error status depending on Exception
			return ResponseEntity
					.status(HttpStatus.CONFLICT)
					.body(ex.getMessage());
		}
	}
	
	*//**
	 * This method is executed when a DELETE request is
	 * made on localhost:8082/deleteExample
	 * The RequestObject can be any object that depending
	 * on the process required
	 *//*
	@PutMapping("/deleteExample")
	public ResponseEntity<?> deleteExample(@RequestBody RequestObject requestObject) {
		try {
			//Process requestObject
			return ResponseEntity
					.ok("Send any response with status Okay (200)");
		} catch (Exception ex) {
			// send any error status depending on Exception
			return ResponseEntity
					.status(HttpStatus.CONFLICT)
					.body(ex.getMessage());
		}
	}*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
