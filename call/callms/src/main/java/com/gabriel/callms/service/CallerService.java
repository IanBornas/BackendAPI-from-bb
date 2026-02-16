package com.gabriel.callms.service;
import com.gabriel.callms.model.Caller;
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
public class CallerService {
	Logger logger = LoggerFactory.getLogger(CallerService.class);
	@Value("${service.api.endpoint}")
	private String endpointUrl = "http://localhost:8080/api/caller";

	protected static CallerService service= null;
	public static CallerService getService(){
		if(service == null){
			service = new CallerService();
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

	public Caller get(Integer id) {
		String url = endpointUrl + "/" + Integer.toString(id);
		logger.info("get: "  + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity request = new HttpEntity<>(null, headers);
		final ResponseEntity<Caller> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, Caller.class);
		return response.getBody();
	}

	public Caller[] getAll() {
		String url = endpointUrl;
		logger.info("getCallers: " + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity request = new HttpEntity<>(null, headers);
		final ResponseEntity<Caller[]> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, Caller[].class);
		Caller[] callers = response.getBody();
		return callers;
	}

	public Caller create(Caller caller) {
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Caller> request = new HttpEntity<>(caller, headers);
		final ResponseEntity<Caller> response =
		getRestTemplate().exchange(url, HttpMethod.PUT, request, Caller.class);
		return response.getBody();
	}
	public Caller update(Caller caller) {
		logger.info("update: " + caller.toString());
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Caller> request = new HttpEntity<>(caller, headers);
		final ResponseEntity<Caller> response =
		getRestTemplate().exchange(url, HttpMethod.POST, request, Caller.class);
		return response.getBody();
	}

	public void delete(Integer id){
		logger.info("delete: " + Integer.toString(id));
		String url = endpointUrl + " / " + Integer.toString(id);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Caller> request = new HttpEntity<>(null, headers);
		final ResponseEntity<Caller> response =
		getRestTemplate().exchange(url, HttpMethod.DELETE, request, Caller.class);
	}
}
