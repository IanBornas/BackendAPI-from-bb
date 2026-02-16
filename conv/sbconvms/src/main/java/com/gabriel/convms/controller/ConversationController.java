package com.gabriel.convms.controller;
import com.gabriel.convms.model.Conversation;
import com.gabriel.convms.service.ConversationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
public class ConversationController {
	Logger logger = LoggerFactory.getLogger( ConversationController.class);
	@Autowired
	private ConversationService conversationService;
	@GetMapping("/api/conversation")
	public ResponseEntity<?> listConversation()
{
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Conversation[] conversation = conversationService.getAll();
			response =  ResponseEntity.ok().headers(headers).body(conversation);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PutMapping("api/conversation")
	public ResponseEntity<?> add(@RequestBody Conversation conversation){
		logger.info("Input >> " + conversation.toString() );
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Conversation newConversation = conversationService.create(conversation);
			logger.info("created conversation >> " + newConversation.toString() );
			response = ResponseEntity.ok(newConversation);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve conversation with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PostMapping("api/conversation")
	public ResponseEntity<?> update(@RequestBody Conversation conversation){
		logger.info("Update Input >> conversation.toString() ");
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Conversation newConversation = conversationService.update(conversation);
			response = ResponseEntity.ok(conversation);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve conversation with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}

	@GetMapping("api/conversation/{id}")
	public ResponseEntity<?> get(@PathVariable final Integer id){
		logger.info("Input conversation id >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Conversation conversation = conversationService.get(id);
			response = ResponseEntity.ok(conversation);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@DeleteMapping("api/conversation/{id}")
	public ResponseEntity<?> delete(@PathVariable final Integer id){
		logger.info("Input >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			conversationService.delete(id);
			response = ResponseEntity.ok(null);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
}
