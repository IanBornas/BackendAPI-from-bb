package com.gabriel.callms.service;
import com.gabriel.callms.model.Call;
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
public class CallService {
	Logger logger = LoggerFactory.getLogger(CallService.class);
	@Value("${service.api.endpoint}")
	private String endpointUrl = "http://localhost:8080/api/call";

	protected static CallService service= null;
	public static CallService getService(){
		if(service == null){
			service = new CallService();
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

	public Call get(Integer id) {
		String url = endpointUrl + "/" + Integer.toString(id);
		logger.info("get: "  + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity request = new HttpEntity<>(null, headers);
		final ResponseEntity<Call> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, Call.class);
		return response.getBody();
	}

	public Call[] getAll() {
		String url = endpointUrl;
		logger.info("getCalls: " + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity request = new HttpEntity<>(null, headers);
		final ResponseEntity<Call[]> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, Call[].class);
		Call[] calls = response.getBody();
		return calls;
	}

	public Call create(Call call) {
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Call> request = new HttpEntity<>(call, headers);
		final ResponseEntity<Call> response =
		getRestTemplate().exchange(url, HttpMethod.PUT, request, Call.class);
		return response.getBody();
	}
	public Call update(Call call) {
		logger.info("update: " + call.toString());
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Call> request = new HttpEntity<>(call, headers);
		final ResponseEntity<Call> response =
		getRestTemplate().exchange(url, HttpMethod.POST, request, Call.class);
		return response.getBody();
	}

	public void delete(Integer id){
		logger.info("delete: " + Integer.toString(id));
		String url = endpointUrl + " / " + Integer.toString(id);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Call> request = new HttpEntity<>(null, headers);
		final ResponseEntity<Call> response =
		getRestTemplate().exchange(url, HttpMethod.DELETE, request, Call.class);
	}
}
