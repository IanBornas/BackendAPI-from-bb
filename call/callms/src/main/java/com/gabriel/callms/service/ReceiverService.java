package com.gabriel.callms.service;
import com.gabriel.callms.model.Receiver;
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
public class ReceiverService {
	Logger logger = LoggerFactory.getLogger(ReceiverService.class);
	@Value("${service.api.endpoint}")
	private String endpointUrl = "http://localhost:8080/api/receiver";

	protected static ReceiverService service= null;
	public static ReceiverService getService(){
		if(service == null){
			service = new ReceiverService();
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

	public Receiver get(Integer id) {
		String url = endpointUrl + "/" + Integer.toString(id);
		logger.info("get: "  + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity request = new HttpEntity<>(null, headers);
		final ResponseEntity<Receiver> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, Receiver.class);
		return response.getBody();
	}

	public Receiver[] getAll() {
		String url = endpointUrl;
		logger.info("getReceivers: " + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity request = new HttpEntity<>(null, headers);
		final ResponseEntity<Receiver[]> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, Receiver[].class);
		Receiver[] receivers = response.getBody();
		return receivers;
	}

	public Receiver create(Receiver receiver) {
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Receiver> request = new HttpEntity<>(receiver, headers);
		final ResponseEntity<Receiver> response =
		getRestTemplate().exchange(url, HttpMethod.PUT, request, Receiver.class);
		return response.getBody();
	}
	public Receiver update(Receiver receiver) {
		logger.info("update: " + receiver.toString());
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Receiver> request = new HttpEntity<>(receiver, headers);
		final ResponseEntity<Receiver> response =
		getRestTemplate().exchange(url, HttpMethod.POST, request, Receiver.class);
		return response.getBody();
	}

	public void delete(Integer id){
		logger.info("delete: " + Integer.toString(id));
		String url = endpointUrl + " / " + Integer.toString(id);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Receiver> request = new HttpEntity<>(null, headers);
		final ResponseEntity<Receiver> response =
		getRestTemplate().exchange(url, HttpMethod.DELETE, request, Receiver.class);
	}
}
