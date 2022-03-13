package com.ems.service.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.ems.config.EMSConfiguration;
import com.ems.dataobject.RestResponse;
import com.ems.dataobject.User;

@Component
@PropertySource("classpath:application.properties")
public class HttpRestClient<T> {
	
	/*
	 * @Autowired WebClient webClient;
	 */
	@Autowired
	EMSConfiguration configuration;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	private HttpServletRequest request;
	
	@Value("${rest.baseUrl.userService}")
	private String baseUrl;
	
	public HttpRestClient() {}
	
	
	public List<T> getFromRest(URI url,ParameterizedTypeReference<RestResponse<List<T>>> typeReference)
	{	
		/*
		 * RestResponse<List<T>> response = Optional.ofNullable( webClient.get()
		 * .uri("getuser/"+userName) .retrieve() .bodyToMono(typeReference) .block() ).
		 * orElseGet(()->new RestResponse<List<T>>(HttpStatus.INTERNAL_SERVER_ERROR,
		 * "Rest Client Error"));
		 */
		
		/*
		 * ResponseEntity<RestResponse<List<T>>> response; String baseUrl =
		 * configuration.getBaseUrl(); response =
		 * restTemplate.exchange(baseUrl+"getuser/"+userName, HttpMethod.GET, null,
		 * typeReference); return ((RestResponse<List<T>>)
		 * response.getBody()).getData();
		 */
		

		
		List<T> data = null;
		try
		{
			ResponseEntity<RestResponse<List<T>>> response;
			User user = null;
			if(SecurityContextHolder.getContext().getAuthentication()!=null)
			{
				user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			}
			RequestEntity<Void> requestEntity = getRequestEntity(url,user);

			//response = restTemplate.getForObject(baseUrl+userName, RestResponse.class);
			response = restTemplate.exchange(requestEntity, typeReference);
			if(response.hasBody())
			{
				data = response.getBody().getData();
				System.out.println(data);
				return (List<T>) data;
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			data = new ArrayList<T>();
		}
		
		return data;
	
	}
	
	
	public T postForRest(String url,T t,ParameterizedTypeReference<RestResponse<T>> typeReference) throws URISyntaxException
	{ 
		String accessToken = getAccessToken(); 
		User user = null;
		if(SecurityContextHolder.getContext().getAuthentication()!=null)
		{
			user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		HttpHeaders headers = new HttpHeaders(); 
		headers.add("Authorization", "Bearer "+accessToken);
		if(user!=null) { 
			headers.add("username", user.getUserId());
			headers.add("password", user.getPassword()); 
		} 
				RequestEntity<T> request = RequestEntity
				     .post(url)
				     .accept(MediaType.APPLICATION_JSON)
				     .headers(headers)
				     .body(t);
				 ResponseEntity<RestResponse<T>> response = restTemplate.exchange(request, typeReference);
				return (T) response.getBody().getData();
		
		


	}
	 

	private RequestEntity<Void> getRequestEntity(URI url,User user) throws URISyntaxException {
		String accessToken = getAccessToken();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer "+accessToken);
		if(user!=null)
		{
			headers.add("username", user.getUserId());
			headers.add("password", user.getPassword());
		}

		//URI url = new URI(baseUrl+"getuser/"+userName);
		RequestEntity<Void> requestEntity = RequestEntity
			     .get(url)
			     .headers(headers)
			     .build();
		return requestEntity;
	}


	
	
	public String getAccessToken() {
		User user;
		if(SecurityContextHolder.getContext().getAuthentication()!=null)
		{
			user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		else
		{
			String un = request.getParameter("username");
			String pwd = request.getParameter("password");
			System.out.println(un+"->"+pwd);
			user = new User();
			user.setUserId(un);
			user.setPassword(pwd);
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", user.getUserId());
		map.put("password", user.getPassword());
		RestResponse<String> restResponse = restTemplate.postForObject(baseUrl+"/authenticate",map, RestResponse.class);
		return restResponse.getData();
		
	}
	
}
