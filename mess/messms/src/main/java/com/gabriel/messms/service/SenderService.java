package com.gabriel.messms.service;
import com.gabriel.messms.model.Sender;
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
public class SenderService {
	Logger logger = LoggerFactory.getLogger(SenderService.class);
	@Value("${service.api.endpoint}")
	private String endpointUrl = "http://localhost:8082/api/sender";

	protected static SenderService service= null;
	public static SenderService getService(){
		if(service == null){
			service = new SenderService();
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

	public Sender get(Integer id) {
		String url = endpointUrl + "/" + Integer.toString(id);
		logger.info("get: "  + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<?> request = new HttpEntity<>(null, headers);
		final ResponseEntity<Sender> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, Sender.class);
		return response.getBody();
	}

	public Sender[] getAll() {
		String url = endpointUrl;
		logger.info("getSenders: " + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<?> request = new HttpEntity<>(null, headers);
		final ResponseEntity<Sender[]> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, Sender[].class);
		Sender[] senders = response.getBody();
		return senders;
	}

	public Sender create(Sender sender) {
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Sender> request = new HttpEntity<>(sender, headers);
		final ResponseEntity<Sender> response =
		getRestTemplate().exchange(url, HttpMethod.PUT, request, Sender.class);
		return response.getBody();
	}
	public Sender update(Sender sender) {
		logger.info("update: " + sender.toString());
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Sender> request = new HttpEntity<>(sender, headers);
		final ResponseEntity<Sender> response =
		getRestTemplate().exchange(url, HttpMethod.POST, request, Sender.class);
		return response.getBody();
	}

	public void delete(Integer id){
		logger.info("delete: " + Integer.toString(id));
		String url = endpointUrl + " / " + Integer.toString(id);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Sender> request = new HttpEntity<>(null, headers);
		getRestTemplate().exchange(url, HttpMethod.DELETE, request, Sender.class);
	}
}
