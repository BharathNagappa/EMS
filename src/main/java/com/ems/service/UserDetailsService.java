package com.ems.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ems.dataobject.RestResponse;
import com.ems.dataobject.User;
import com.ems.service.client.HttpRestClient;


@Service
@PropertySource("classpath:application.properties")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	HttpRestClient<User> httpRestClient;
	
	@Value("${rest.baseUrl.userService}")
	String baseUrl;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ParameterizedTypeReference<RestResponse<List<User>>> typeReference = new ParameterizedTypeReference<RestResponse<List<User>>>() {};
		List<User> userDetails = null;
		try {
			URI url = new URI(baseUrl+"getuser/"+username);
			userDetails = (List<User>) httpRestClient.getFromRest(url,typeReference);
			/*
			 * List<User> users = Optional.ofNullable(userDetails).
			 * orElseGet(()->Arrays.asList(new User())) .stream(). map(userDetail->new
			 * User(userDetail.getUsername(),userDetail.getPassword()))
			 * .collect(Collectors.toList());
			 */

			return userDetails.get(0);
		} catch(IndexOutOfBoundsException exp)
		{
			throw new UsernameNotFoundException("User not found");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			System.out.println("Invalid url syntax for UserService");
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public List<User> getAllActiveUsers(){

		ParameterizedTypeReference<RestResponse<List<User>>> typeReference = new ParameterizedTypeReference<RestResponse<List<User>>>() {};
		List<User> userDetails = null;
		try {
			URI url = new URI(baseUrl+"users");
			userDetails = (List<User>) httpRestClient.getFromRest(url,typeReference);
			return userDetails;
		} catch(IndexOutOfBoundsException exp)
		{
			throw new UsernameNotFoundException("User not found");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			System.out.println("Invalid url syntax for UserService");
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;



	}

}
