package com.gabriel.callms.controller;
import com.gabriel.callms.model.CallSignal;
import com.gabriel.callms.service.CallSignalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
public class CallSignalController {
	Logger logger = LoggerFactory.getLogger( CallSignalController.class);
	@Autowired
	private CallSignalService callSignalService;
	@GetMapping("/api/callSignal")
	public ResponseEntity<?> listCallSignal()
{
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			CallSignal[] callSignal = callSignalService.getAll();
			response =  ResponseEntity.ok().headers(headers).body(callSignal);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PutMapping("api/callSignal")
	public ResponseEntity<?> add(@RequestBody CallSignal callSignal){
		logger.info("Input >> " + callSignal.toString() );
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			CallSignal newCallSignal = callSignalService.create(callSignal);
			logger.info("created callSignal >> " + newCallSignal.toString() );
			response = ResponseEntity.ok(newCallSignal);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve callSignal with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PostMapping("api/callSignal")
	public ResponseEntity<?> update(@RequestBody CallSignal callSignal){
		logger.info("Update Input >> callSignal.toString() ");
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			CallSignal newCallSignal = callSignalService.update(callSignal);
			response = ResponseEntity.ok(callSignal);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve callSignal with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}

	@GetMapping("api/callSignal/{id}")
	public ResponseEntity<?> get(@PathVariable final Integer id){
		logger.info("Input callSignal id >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			CallSignal callSignal = callSignalService.get(id);
			response = ResponseEntity.ok(callSignal);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@DeleteMapping("api/callSignal/{id}")
	public ResponseEntity<?> delete(@PathVariable final Integer id){
		logger.info("Input >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			callSignalService.delete(id);
			response = ResponseEntity.ok(null);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
}
