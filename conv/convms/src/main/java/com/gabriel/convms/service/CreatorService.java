package com.gabriel.convms.service;
import com.gabriel.convms.model.Creator;
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
public class CreatorService {
	Logger logger = LoggerFactory.getLogger(CreatorService.class);
	@Value("${service.api.endpoint}")
	private String endpointUrl = "http://localhost:8080/api/creator";

	protected static CreatorService service= null;
	public static CreatorService getService(){
		if(service == null){
			service = new CreatorService();
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

	public Creator get(Integer id) {
		String url = endpointUrl + "/" + Integer.toString(id);
		logger.info("get: "  + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<?> request = new HttpEntity<>(null, headers);
		final ResponseEntity<Creator> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, Creator.class);
		return response.getBody();
	}

	public Creator[] getAll() {
		String url = endpointUrl;
		logger.info("getCreators: " + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<?> request = new HttpEntity<>(null, headers);
		final ResponseEntity<Creator[]> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, Creator[].class);
		Creator[] creators = response.getBody();
		return creators;
	}

	public Creator create(Creator creator) {
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Creator> request = new HttpEntity<>(creator, headers);
		final ResponseEntity<Creator> response =
		getRestTemplate().exchange(url, HttpMethod.PUT, request, Creator.class);
		return response.getBody();
	}
	public Creator update(Creator creator) {
		logger.info("update: " + creator.toString());
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Creator> request = new HttpEntity<>(creator, headers);
		final ResponseEntity<Creator> response =
		getRestTemplate().exchange(url, HttpMethod.POST, request, Creator.class);
		return response.getBody();
	}

	public void delete(Integer id){
		logger.info("delete: " + Integer.toString(id));
		String url = endpointUrl + " / " + Integer.toString(id);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Creator> request = new HttpEntity<>(null, headers);
		getRestTemplate().exchange(url, HttpMethod.DELETE, request, Creator.class);
	}
}
