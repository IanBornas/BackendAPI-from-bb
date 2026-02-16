package com.gabriel.callms.controller;
import com.gabriel.callms.model.Receiver;
import com.gabriel.callms.service.ReceiverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
public class ReceiverController {
	Logger logger = LoggerFactory.getLogger( ReceiverController.class);
	@Autowired
	private ReceiverService receiverService;
	@GetMapping("/api/receiver")
	public ResponseEntity<?> listReceiver()
{
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Receiver[] receiver = receiverService.getAll();
			response =  ResponseEntity.ok().headers(headers).body(receiver);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PutMapping("api/receiver")
	public ResponseEntity<?> add(@RequestBody Receiver receiver){
		logger.info("Input >> " + receiver.toString() );
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Receiver newReceiver = receiverService.create(receiver);
			logger.info("created receiver >> " + newReceiver.toString() );
			response = ResponseEntity.ok(newReceiver);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve receiver with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PostMapping("api/receiver")
	public ResponseEntity<?> update(@RequestBody Receiver receiver){
		logger.info("Update Input >> receiver.toString() ");
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Receiver newReceiver = receiverService.update(receiver);
			response = ResponseEntity.ok(receiver);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve receiver with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}

	@GetMapping("api/receiver/{id}")
	public ResponseEntity<?> get(@PathVariable final Integer id){
		logger.info("Input receiver id >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Receiver receiver = receiverService.get(id);
			response = ResponseEntity.ok(receiver);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@DeleteMapping("api/receiver/{id}")
	public ResponseEntity<?> delete(@PathVariable final Integer id){
		logger.info("Input >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			receiverService.delete(id);
			response = ResponseEntity.ok(null);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
}
