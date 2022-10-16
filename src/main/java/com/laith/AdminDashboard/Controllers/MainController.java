package com.laith.AdminDashboard.Controllers;

import java.security.Principal;
import java.util.Date;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.laith.AdminDashboard.Models.User;
import com.laith.AdminDashboard.Services.UserService;
import com.laith.AdminDashboard.Validator.UserValidator;

@Controller

public class MainController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private UserValidator userValidator;

	@RequestMapping("/registration")
	public String registerForm(@Valid @ModelAttribute("user") User user) {
		return "registrationPage.jsp";
	}
	
	@PostMapping("/registration")
	public String registration(@Valid @ModelAttribute("user") User user, BindingResult result,
								Model model) {
		
        userValidator.validate(user, result);
		if(result.hasErrors()) {
			return "registrationPage.jsp";
		}
		if(userService.findAll().size() > 0) {
			userService.saveWithUserRole(user);
		}else {
			userService.saveUserWithAdminRole(user);
		}
		return "redirect:/";
	}
	
	
    @RequestMapping("/admin")
    public String adminPage(Principal principal, Model model) {
        String username = principal.getName();
        model.addAttribute("currentUser", userService.findByUsername(username));
        model.addAttribute("users",userService.findAll());
        
        return "adminPage.jsp";
    }
    
	@RequestMapping("/admin/{id}")
	public String makeAdmin(@PathVariable("id") Long id, Model model) {
		
		User user = userService.findById(id);
		userService.upgradeUser(user);
		
		model.addAttribute("users", userService.findAll());
		 
		return "redirect:/admin";
	}
    
	@RequestMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") Long id, HttpSession session, Model model) {	
		User user = userService.findById(id);
		userService.deleteUser(user);
		
		model.addAttribute("users", userService.findAll());
		 
		return "redirect:/admin";
	}
	
    @RequestMapping("/login")
    public String login(@RequestParam(value="error", required=false) String error,
    		@RequestParam(value="logout", required=false) String logout, Model model) {
    	
        if(error != null) {
            model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
        }
        if(logout != null) {
            model.addAttribute("logoutMessage", "Logout Successful!");
        }
        return "loginPage.jsp";
    }
	
    @RequestMapping(value = {"/", "/home"})
    public String home(Principal principal, Model model) {
        // 1
        String username = principal.getName();
        User user = userService.findByUsername(username);
        model.addAttribute("currUser", user);
        
		if(user!=null) {
			user.setLastLogin(new Date());
			userService.updateUser(user);
			// If the user is an ADMIN or SUPER_ADMIN they will be redirected to the admin page
			if(user.getRoles().get(0).getName().contains("ROLE_ADMIN")) {
//				model.addAttribute("currentUser", userService.findByEmail(email));
				model.addAttribute("users", userService.findAll());
				return "adminPage.jsp";
			}
			// All other users are redirected to the home page
		}
		
		return "homePage.jsp";
    }
}






