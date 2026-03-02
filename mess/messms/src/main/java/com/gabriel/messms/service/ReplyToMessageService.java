package com.gabriel.messms.service;
import com.gabriel.messms.model.ReplyToMessage;
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
public class ReplyToMessageService {
	Logger logger = LoggerFactory.getLogger(ReplyToMessageService.class);
	@Value("${service.api.endpoint}")
	private String endpointUrl = "http://localhost:8080/api/replyToMessage";

	protected static ReplyToMessageService service= null;
	public static ReplyToMessageService getService(){
		if(service == null){
			service = new ReplyToMessageService();
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

	public ReplyToMessage get(Integer id) {
		String url = endpointUrl + "/" + Integer.toString(id);
		logger.info("get: "  + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<?> request = new HttpEntity<>(null, headers);
		final ResponseEntity<ReplyToMessage> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, ReplyToMessage.class);
		return response.getBody();
	}

	public ReplyToMessage[] getAll() {
		String url = endpointUrl;
		logger.info("getReplyToMessages: " + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<?> request = new HttpEntity<>(null, headers);
		final ResponseEntity<ReplyToMessage[]> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, ReplyToMessage[].class);
		ReplyToMessage[] replyToMessages = response.getBody();
		return replyToMessages;
	}

	public ReplyToMessage create(ReplyToMessage replyToMessage) {
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<ReplyToMessage> request = new HttpEntity<>(replyToMessage, headers);
		final ResponseEntity<ReplyToMessage> response =
		getRestTemplate().exchange(url, HttpMethod.PUT, request, ReplyToMessage.class);
		return response.getBody();
	}
	public ReplyToMessage update(ReplyToMessage replyToMessage) {
		logger.info("update: " + replyToMessage.toString());
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<ReplyToMessage> request = new HttpEntity<>(replyToMessage, headers);
		final ResponseEntity<ReplyToMessage> response =
		getRestTemplate().exchange(url, HttpMethod.POST, request, ReplyToMessage.class);
		return response.getBody();
	}

	public void delete(Integer id){
		logger.info("delete: " + Integer.toString(id));
		String url = endpointUrl + " / " + Integer.toString(id);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<ReplyToMessage> request = new HttpEntity<>(null, headers);
		getRestTemplate().exchange(url, HttpMethod.DELETE, request, ReplyToMessage.class);
	}
}
