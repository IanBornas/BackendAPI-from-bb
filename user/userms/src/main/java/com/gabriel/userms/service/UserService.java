package com.gabriel.userms.service;
import com.gabriel.userms.model.User;
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
public class UserService {
	Logger logger = LoggerFactory.getLogger(UserService.class);
	@Value("${service.api.endpoint}")
	private String endpointUrl = "http://localhost:8080/api/user";

	protected static UserService service= null;
	public static UserService getService(){
		if(service == null){
			service = new UserService();
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

	public User get(Integer id) {
		String url = endpointUrl + "/" + Integer.toString(id);
		logger.info("get: "  + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<?> request = new HttpEntity<>(null, headers);
		final ResponseEntity<User> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, User.class);
		return response.getBody();
	}

	public User[] getAll() {
		String url = endpointUrl;
		logger.info("getUsers: " + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<?> request = new HttpEntity<>(null, headers);
		final ResponseEntity<User[]> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, User[].class);
		User[] users = response.getBody();
		return users;
	}

	public User create(User user) {
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<User> request = new HttpEntity<>(user, headers);
		final ResponseEntity<User> response =
		getRestTemplate().exchange(url, HttpMethod.PUT, request, User.class);
		return response.getBody();
	}
	public User update(User user) {
		logger.info("update: " + user.toString());
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<User> request = new HttpEntity<>(user, headers);
		final ResponseEntity<User> response =
		getRestTemplate().exchange(url, HttpMethod.POST, request, User.class);
		return response.getBody();
	}

	public void delete(Integer id){
		logger.info("delete: " + Integer.toString(id));
		String url = endpointUrl + " / " + Integer.toString(id);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<User> request = new HttpEntity<>(null, headers);
		getRestTemplate().exchange(url, HttpMethod.DELETE, request, User.class);
	}
}
