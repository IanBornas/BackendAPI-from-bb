package com.gabriel.messms.controller;
import com.gabriel.messms.model.ReplyToMessage;
import com.gabriel.messms.service.ReplyToMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
public class ReplyToMessageController {
	Logger logger = LoggerFactory.getLogger( ReplyToMessageController.class);
	@Autowired
	private ReplyToMessageService replyToMessageService;
	@GetMapping("/api/replyToMessage")
	public ResponseEntity<?> listReplyToMessage()
{
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			ReplyToMessage[] replyToMessage = replyToMessageService.getAll();
			response =  ResponseEntity.ok().headers(headers).body(replyToMessage);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PutMapping("api/replyToMessage")
	public ResponseEntity<?> add(@RequestBody ReplyToMessage replyToMessage){
		logger.info("Input >> " + replyToMessage.toString() );
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			ReplyToMessage newReplyToMessage = replyToMessageService.create(replyToMessage);
			logger.info("created replyToMessage >> " + newReplyToMessage.toString() );
			response = ResponseEntity.ok(newReplyToMessage);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve replyToMessage with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PostMapping("api/replyToMessage")
	public ResponseEntity<?> update(@RequestBody ReplyToMessage replyToMessage){
		logger.info("Update Input >> replyToMessage.toString() ");
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			ReplyToMessage newReplyToMessage = replyToMessageService.update(replyToMessage);
			response = ResponseEntity.ok(replyToMessage);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve replyToMessage with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}

	@GetMapping("api/replyToMessage/{id}")
	public ResponseEntity<?> get(@PathVariable final Integer id){
		logger.info("Input replyToMessage id >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			ReplyToMessage replyToMessage = replyToMessageService.get(id);
			response = ResponseEntity.ok(replyToMessage);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@DeleteMapping("api/replyToMessage/{id}")
	public ResponseEntity<?> delete(@PathVariable final Integer id){
		logger.info("Input >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			replyToMessageService.delete(id);
			response = ResponseEntity.ok(null);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
}
