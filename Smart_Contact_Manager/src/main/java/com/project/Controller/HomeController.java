package com.project.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.Entities.User;
import com.project.Helper.Message;
import com.project.dao.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;

	

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("title" , "Home - SmarContactManager");
		return "home";
	}
	
	@GetMapping("/about")
	public String about(Model model) {
		model.addAttribute("title" , "About - SmarContactManager");
		return "about";
	}
	
	@GetMapping("/signup")
	public String signup(Model model , HttpSession session) {
		session.removeAttribute("message");
		model.addAttribute("title" , "Signup - SmarContactManager");
		model.addAttribute("user", new User());
		return "signup";
	}
	
	//this handler for registering
	@PostMapping("/do_register")
	public String registerUser(@Valid
			@ModelAttribute("user") User user , BindingResult result1 , @RequestParam(value="agreement" , defaultValue="false")boolean agreement , Model model , HttpSession session ) {
		//user will accept the vlaue of email , name , password from form but agreement is not in class user so ww will accept it using @RequestParam
		
		 session.removeAttribute("message");
		
		try {
			if(result1.hasErrors()) {
				model.addAttribute("user" , user);
				return "signup";
			}
			
		if(!agreement) {
		System.out.println("You have ot agreed to terms and conditions");
		 throw new Exception("You have not agreed to terms and conditions");
				
			}
		
		
			
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setImageUrl("default.png");	
			
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			
			System.out.println("Agreement" + agreement);
			System.out.println("USER" + user);
			
			User resultUser =this.userRepository.save(user);
			
			
		  model.addAttribute("user" , new User());
		  
		  session.removeAttribute("message"); //remove message already appearing for past
		  session.setAttribute("message", new Message("Successfully registerd","alert-success"));
		  
		  
			return "signup";

			
			
		} catch (Exception e) {
			
			
			
			e.printStackTrace();
			session.removeAttribute("message"); //remove message already appearing for past
			session.setAttribute("message", new Message("Something Went Wrong!!"+e.toString(),"alert-danger"));
			
			
			return "signup";
		}
		
		}
	
	
	
	//creating the login page
	@GetMapping("/signin")
	public String Signin(Model model) {
		model.addAttribute("title","Login-Smart Contact Manager");
		return "login";
	}
	
}
