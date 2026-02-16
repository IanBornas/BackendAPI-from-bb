package com.gabriel.callms.service;
import com.gabriel.callms.model.CallSignal;
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
public class CallSignalService {
	Logger logger = LoggerFactory.getLogger(CallSignalService.class);
	@Value("${service.api.endpoint}")
	private String endpointUrl = "http://localhost:8080/api/callSignal";

	protected static CallSignalService service= null;
	public static CallSignalService getService(){
		if(service == null){
			service = new CallSignalService();
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

	public CallSignal get(Integer id) {
		String url = endpointUrl + "/" + Integer.toString(id);
		logger.info("get: "  + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity request = new HttpEntity<>(null, headers);
		final ResponseEntity<CallSignal> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, CallSignal.class);
		return response.getBody();
	}

	public CallSignal[] getAll() {
		String url = endpointUrl;
		logger.info("getCallSignals: " + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity request = new HttpEntity<>(null, headers);
		final ResponseEntity<CallSignal[]> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, CallSignal[].class);
		CallSignal[] callSignals = response.getBody();
		return callSignals;
	}

	public CallSignal create(CallSignal callSignal) {
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<CallSignal> request = new HttpEntity<>(callSignal, headers);
		final ResponseEntity<CallSignal> response =
		getRestTemplate().exchange(url, HttpMethod.PUT, request, CallSignal.class);
		return response.getBody();
	}
	public CallSignal update(CallSignal callSignal) {
		logger.info("update: " + callSignal.toString());
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<CallSignal> request = new HttpEntity<>(callSignal, headers);
		final ResponseEntity<CallSignal> response =
		getRestTemplate().exchange(url, HttpMethod.POST, request, CallSignal.class);
		return response.getBody();
	}

	public void delete(Integer id){
		logger.info("delete: " + Integer.toString(id));
		String url = endpointUrl + " / " + Integer.toString(id);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<CallSignal> request = new HttpEntity<>(null, headers);
		final ResponseEntity<CallSignal> response =
		getRestTemplate().exchange(url, HttpMethod.DELETE, request, CallSignal.class);
	}
}
