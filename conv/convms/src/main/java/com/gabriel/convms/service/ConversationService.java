package com.gabriel.convms.service;
import com.gabriel.convms.model.Conversation;
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
public class ConversationService {
	Logger logger = LoggerFactory.getLogger(ConversationService.class);
	@Value("${service.api.endpoint}")
	private String endpointUrl = "http://localhost:8080/api/conversation";

	protected static ConversationService service= null;
	public static ConversationService getService(){
		if(service == null){
			service = new ConversationService();
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

	public Conversation get(Integer id) {
		String url = endpointUrl + "/" + Integer.toString(id);
		logger.info("get: "  + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity request = new HttpEntity<>(null, headers);
		final ResponseEntity<Conversation> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, Conversation.class);
		return response.getBody();
	}

	public Conversation[] getAll() {
		String url = endpointUrl;
		logger.info("getConversations: " + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity request = new HttpEntity<>(null, headers);
		final ResponseEntity<Conversation[]> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, Conversation[].class);
		Conversation[] conversations = response.getBody();
		return conversations;
	}

	public Conversation create(Conversation conversation) {
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Conversation> request = new HttpEntity<>(conversation, headers);
		final ResponseEntity<Conversation> response =
		getRestTemplate().exchange(url, HttpMethod.PUT, request, Conversation.class);
		return response.getBody();
	}
	public Conversation update(Conversation conversation) {
		logger.info("update: " + conversation.toString());
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Conversation> request = new HttpEntity<>(conversation, headers);
		final ResponseEntity<Conversation> response =
		getRestTemplate().exchange(url, HttpMethod.POST, request, Conversation.class);
		return response.getBody();
	}

	public void delete(Integer id){
		logger.info("delete: " + Integer.toString(id));
		String url = endpointUrl + " / " + Integer.toString(id);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Conversation> request = new HttpEntity<>(null, headers);
		final ResponseEntity<Conversation> response =
		getRestTemplate().exchange(url, HttpMethod.DELETE, request, Conversation.class);
	}
}
