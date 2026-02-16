package com.gabriel.messms.controller;
import com.gabriel.messms.model.Message;
import com.gabriel.messms.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
public class MessageController {
	Logger logger = LoggerFactory.getLogger( MessageController.class);
	@Autowired
	private MessageService messageService;
	@GetMapping("/api/message")
	public ResponseEntity<?> listMessage()
{
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Message[] message = messageService.getAll();
			response =  ResponseEntity.ok().headers(headers).body(message);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PutMapping("api/message")
	public ResponseEntity<?> add(@RequestBody Message message){
		logger.info("Input >> " + message.toString() );
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Message newMessage = messageService.create(message);
			logger.info("created message >> " + newMessage.toString() );
			response = ResponseEntity.ok(newMessage);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve message with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PostMapping("api/message")
	public ResponseEntity<?> update(@RequestBody Message message){
		logger.info("Update Input >> message.toString() ");
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Message newMessage = messageService.update(message);
			response = ResponseEntity.ok(message);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve message with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}

	@GetMapping("api/message/{id}")
	public ResponseEntity<?> get(@PathVariable final Integer id){
		logger.info("Input message id >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Message message = messageService.get(id);
			response = ResponseEntity.ok(message);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@DeleteMapping("api/message/{id}")
	public ResponseEntity<?> delete(@PathVariable final Integer id){
		logger.info("Input >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			messageService.delete(id);
			response = ResponseEntity.ok(null);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
}
