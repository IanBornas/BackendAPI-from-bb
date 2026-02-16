package com.gabriel.callms.controller;
import com.gabriel.callms.model.Caller;
import com.gabriel.callms.service.CallerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
public class CallerController {
	Logger logger = LoggerFactory.getLogger( CallerController.class);
	@Autowired
	private CallerService callerService;
	@GetMapping("/api/caller")
	public ResponseEntity<?> listCaller()
{
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Caller[] caller = callerService.getAll();
			response =  ResponseEntity.ok().headers(headers).body(caller);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PutMapping("api/caller")
	public ResponseEntity<?> add(@RequestBody Caller caller){
		logger.info("Input >> " + caller.toString() );
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Caller newCaller = callerService.create(caller);
			logger.info("created caller >> " + newCaller.toString() );
			response = ResponseEntity.ok(newCaller);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve caller with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PostMapping("api/caller")
	public ResponseEntity<?> update(@RequestBody Caller caller){
		logger.info("Update Input >> caller.toString() ");
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Caller newCaller = callerService.update(caller);
			response = ResponseEntity.ok(caller);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve caller with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}

	@GetMapping("api/caller/{id}")
	public ResponseEntity<?> get(@PathVariable final Integer id){
		logger.info("Input caller id >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Caller caller = callerService.get(id);
			response = ResponseEntity.ok(caller);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@DeleteMapping("api/caller/{id}")
	public ResponseEntity<?> delete(@PathVariable final Integer id){
		logger.info("Input >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			callerService.delete(id);
			response = ResponseEntity.ok(null);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
}
