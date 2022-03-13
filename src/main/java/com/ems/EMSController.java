package com.ems;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ems.dataobject.Task;
import com.ems.dataobject.User;
import com.ems.service.TaskService;
import com.ems.service.UserDetailsService;

@Controller
public class EMSController {

	@Autowired
	TaskService taskService;

	@Autowired
	UserDetailsService userService;

	@RequestMapping(method = RequestMethod.GET,path = "/")
	public String home(Model model)
	{
		//User user = new User(getLoggedInUserDetails(),"");
		//model.addAttribute("user",user);
		setLoggedInUserInfo(model);
		Map<String, String> map = taskService.getCountByStatus();
		System.out.println("StatusMap "+map);
		model.addAttribute("StatusMap", map);
		model.addAttribute("pageName", "userdashboard");	
		//System.out.println("In contorlller "+user.getUsername());
		return "index";
	}

	public void setLoggedInUserInfo(Model model)
	{
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//System.out.println(user.getUsername());
		//User user = new User(user.getUsername(),"");
		model.addAttribute("user",user);
	}

	@GetMapping(path = "/tasks")
	public String tasks(Model model)
	{
		/*
		 * User user = new User(getLoggedInUserDetails(),"");
		 * model.addAttribute("user",user);
		 */
		setLoggedInUserInfo(model);
		List<Task> tasks = (List<Task>) taskService.getAllEntities();	
		model.addAttribute("tasks", tasks);
		model.addAttribute("pageName", "taskList");
		return "index";
	}

	@GetMapping(path = "task/{id}")
	public String getTaskDetails(Model model,@PathVariable(name="id") Long taskId)
	{
		setLoggedInUserInfo(model);
		Task task = taskService.getTaskDetail(taskId);
		model.addAttribute("pageName", "taskDetail");	
		model.addAttribute("task", task);
		return "index";
	}


	@GetMapping(path = "/createtask")
	public String createTask(Model model)
	{
		setLoggedInUserInfo(model);
		List<User> users = userService.getAllActiveUsers();
		model.addAttribute("users",users);
		model.addAttribute("task", new Task());
		model.addAttribute("pageName", "createTask");	
		return "index";
	}


	@PostMapping(path = "/createtask") 
	public String createTask(@ModelAttribute Task task,Model model) { 
		setLoggedInUserInfo(model); 
		task.setStatus("TODO");
		task = taskService.saveEntity(task);
		model.addAttribute("task", task);
		model.addAttribute("pageName", "taskDetail"); 
		return "index"; 
	}

	

}
