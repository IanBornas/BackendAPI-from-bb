package com.gabriel.messms.service;
import com.gabriel.messms.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MessageService {
	Logger logger = LoggerFactory.getLogger(MessageService.class);
	@Value("${service.api.endpoint}")
	private String endpointUrl = "http://localhost:8080/api/message";

	protected static MessageService service= null;
	public static MessageService getService(){
		if(service == null){
			service = new MessageService();
		}
		return service;
	}

	RestTemplate restTemplate = null;
	public RestTemplate getRestTemplate() {
		if(restTemplate == null) {
		restTemplate = new RestTemplate();
			List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
			MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
			converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
			messageConverters.add(converter);
			restTemplate.setMessageConverters(messageConverters);
		}
		return restTemplate;
	}

	public Message get(Integer id) {
		String url = endpointUrl + "/" + Integer.toString(id);
		logger.info("get: "  + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<?> request = new HttpEntity<>(null, headers);
		final ResponseEntity<Message> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, Message.class);
		return response.getBody();
	}

	public Message[] getAll() {
		String url = endpointUrl;
		logger.info("getMessages: " + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<?> request = new HttpEntity<>(null, headers);
		final ResponseEntity<Message[]> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, Message[].class);
		Message[] messages = response.getBody();
		return messages;
	}

	public Message create(Message message) {
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Message> request = new HttpEntity<>(message, headers);
		final ResponseEntity<Message> response =
		getRestTemplate().exchange(url, HttpMethod.PUT, request, Message.class);
		return response.getBody();
	}
	public Message update(Message message) {
		logger.info("update: " + message.toString());
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Message> request = new HttpEntity<>(message, headers);
		final ResponseEntity<Message> response =
		getRestTemplate().exchange(url, HttpMethod.POST, request, Message.class);
		return response.getBody();
	}

	public void delete(Integer id){
		logger.info("delete: " + Integer.toString(id));
		String url = endpointUrl + " / " + Integer.toString(id);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Message> request = new HttpEntity<>(null, headers);
		getRestTemplate().exchange(url, HttpMethod.DELETE, request, Message.class);
	}
}
