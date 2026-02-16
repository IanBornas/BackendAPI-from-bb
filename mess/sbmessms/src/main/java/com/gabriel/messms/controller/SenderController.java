package com.gabriel.messms.controller;
import com.gabriel.messms.model.Sender;
import com.gabriel.messms.service.SenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
public class SenderController {
	Logger logger = LoggerFactory.getLogger( SenderController.class);
	@Autowired
	private SenderService senderService;
	@GetMapping("/api/sender")
	public ResponseEntity<?> listSender()
{
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Sender[] sender = senderService.getAll();
			response =  ResponseEntity.ok().headers(headers).body(sender);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PutMapping("api/sender")
	public ResponseEntity<?> add(@RequestBody Sender sender){
		logger.info("Input >> " + sender.toString() );
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Sender newSender = senderService.create(sender);
			logger.info("created sender >> " + newSender.toString() );
			response = ResponseEntity.ok(newSender);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve sender with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PostMapping("api/sender")
	public ResponseEntity<?> update(@RequestBody Sender sender){
		logger.info("Update Input >> sender.toString() ");
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Sender newSender = senderService.update(sender);
			response = ResponseEntity.ok(sender);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve sender with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}

	@GetMapping("api/sender/{id}")
	public ResponseEntity<?> get(@PathVariable final Integer id){
		logger.info("Input sender id >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Sender sender = senderService.get(id);
			response = ResponseEntity.ok(sender);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@DeleteMapping("api/sender/{id}")
	public ResponseEntity<?> delete(@PathVariable final Integer id){
		logger.info("Input >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			senderService.delete(id);
			response = ResponseEntity.ok(null);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
}
