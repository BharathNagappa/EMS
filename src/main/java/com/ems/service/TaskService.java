package com.ems.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ems.dataobject.RestResponse;
import com.ems.dataobject.Task;
import com.ems.dataobject.User;
import com.ems.service.client.HttpRestClient;

@Service
@PropertySource("classpath:application.properties")
public class TaskService implements IEntityService<Task>{

	
	@Autowired
	HttpRestClient<Task> httpRestClient;
	
	@Value("${rest.baseUrl.taskService}")
	String baseUrl;
	
	@Override
	public Iterable<Task> getAllEntities() {
		// TODO Auto-generated method stub
		ParameterizedTypeReference<RestResponse<List<Task>>> typeReference = new ParameterizedTypeReference<RestResponse<List<Task>>>() {};		
		List<Task> tasks = null;
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			URI url = new URI(baseUrl+"/tasks/"+user.getId());
			tasks  = httpRestClient.getFromRest(url, typeReference);
		} catch (URISyntaxException e) {
			System.out.println("Invalid url Syntax for taskService");
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return tasks;
		
	}

	public Task getTaskDetail(Long id) {
		// TODO Auto-generated method stub
		ParameterizedTypeReference<RestResponse<List<Task>>> typeReference = new ParameterizedTypeReference<RestResponse<List<Task>>>() {};		
		Task task = null;
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			URI url = new URI(baseUrl+"/task/"+id);
			List<Task> tasks  = httpRestClient.getFromRest(url, typeReference);
			if(tasks.size()>0)
			{
				task = tasks.get(0);
			}
		} catch (URISyntaxException e) {
			System.out.println("Invalid url Syntax for taskService");
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return task;
		
	}

	public Map<String,String> getCountByStatus(){
		ParameterizedTypeReference<RestResponse<List<Task>>> typeReference = new ParameterizedTypeReference<RestResponse<List<Task>>>() {};		
		Map<String,String> map = null;
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			URI url = new URI(baseUrl+"/countByStatus/"+user.getId());
			List<Task> tasks = httpRestClient.getFromRest(url, typeReference);
			if(tasks.size()>0)
			{
				Task task = tasks.get(0);
				map = task.getAdditionalProperties();
			}
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	@Override
	public Task saveEntity(Task entity) {
		// TODO Auto-generated method stub
		try {
			ParameterizedTypeReference<RestResponse<Task>> typeReference = new ParameterizedTypeReference<RestResponse<Task>>() {};	
			return httpRestClient.postForRest(baseUrl+"/task", entity,typeReference);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entity;
	}

	

}
