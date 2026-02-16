package com.gabriel.callms.controller;
import com.gabriel.callms.model.Call;
import com.gabriel.callms.service.CallService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
public class CallController {
	Logger logger = LoggerFactory.getLogger( CallController.class);
	@Autowired
	private CallService callService;
	@GetMapping("/api/call")
	public ResponseEntity<?> listCall()
{
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Call[] call = callService.getAll();
			response =  ResponseEntity.ok().headers(headers).body(call);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PutMapping("api/call")
	public ResponseEntity<?> add(@RequestBody Call call){
		logger.info("Input >> " + call.toString() );
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Call newCall = callService.create(call);
			logger.info("created call >> " + newCall.toString() );
			response = ResponseEntity.ok(newCall);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve call with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PostMapping("api/call")
	public ResponseEntity<?> update(@RequestBody Call call){
		logger.info("Update Input >> call.toString() ");
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Call newCall = callService.update(call);
			response = ResponseEntity.ok(call);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve call with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}

	@GetMapping("api/call/{id}")
	public ResponseEntity<?> get(@PathVariable final Integer id){
		logger.info("Input call id >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Call call = callService.get(id);
			response = ResponseEntity.ok(call);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@DeleteMapping("api/call/{id}")
	public ResponseEntity<?> delete(@PathVariable final Integer id){
		logger.info("Input >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			callService.delete(id);
			response = ResponseEntity.ok(null);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
}
