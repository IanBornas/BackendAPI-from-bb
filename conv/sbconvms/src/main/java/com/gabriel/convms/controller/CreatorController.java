package com.gabriel.convms.controller;
import com.gabriel.convms.model.Creator;
import com.gabriel.convms.service.CreatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
public class CreatorController {
	Logger logger = LoggerFactory.getLogger( CreatorController.class);
	@Autowired
	private CreatorService creatorService;
	@GetMapping("/api/creator")
	public ResponseEntity<?> listCreator()
{
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Creator[] creator = creatorService.getAll();
			response =  ResponseEntity.ok().headers(headers).body(creator);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PutMapping("api/creator")
	public ResponseEntity<?> add(@RequestBody Creator creator){
		logger.info("Input >> " + creator.toString() );
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Creator newCreator = creatorService.create(creator);
			logger.info("created creator >> " + newCreator.toString() );
			response = ResponseEntity.ok(newCreator);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve creator with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PostMapping("api/creator")
	public ResponseEntity<?> update(@RequestBody Creator creator){
		logger.info("Update Input >> creator.toString() ");
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Creator newCreator = creatorService.update(creator);
			response = ResponseEntity.ok(creator);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve creator with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}

	@GetMapping("api/creator/{id}")
	public ResponseEntity<?> get(@PathVariable final Integer id){
		logger.info("Input creator id >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Creator creator = creatorService.get(id);
			response = ResponseEntity.ok(creator);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@DeleteMapping("api/creator/{id}")
	public ResponseEntity<?> delete(@PathVariable final Integer id){
		logger.info("Input >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			creatorService.delete(id);
			response = ResponseEntity.ok(null);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
}
