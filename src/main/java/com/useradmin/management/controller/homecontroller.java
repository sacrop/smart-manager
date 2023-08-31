package com.useradmin.management.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.useradmin.management.helper.Message;
import com.useradmin.management.model.UserDtls;
import com.useradmin.management.repository.UserRepository;
import com.useradmin.management.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class homecontroller {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/")
    public String home(){
        return "index";
    }
    @GetMapping("/signin")
    public String signin(){
        return "login";
    }
    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @Autowired
    private UserRepository userRepo;

    @ModelAttribute
    public void userDtails(Model m,Principal p)
    {
        if(p!=null){
            String email=p.getName();
        UserDtls user=userRepo.findByEmail(email);
        m.addAttribute("user", user);
        }

    }


    @PostMapping("/createUser")
    public String createUser(@ModelAttribute UserDtls user,@RequestParam(value="agreement",defaultValue = "false") boolean agreement,HttpSession session)
    {

        Boolean check=userService.checkbyemail(user.getEmail());
        try {
            if(!agreement){
                
                throw new Exception("you have not agreed terms and conditions");
            }
            if(check){
            
                 session.setAttribute("message", new Message("email id already exist!!", "alert-danger"));
                }
             else{
                UserDtls userdtls=userService.createUser(user);
                if(userdtls!=null && agreement){
                    // System.out.println("Registration successfull!");
                    session.setAttribute("message", new Message("successfull registration !!", "alert-success"));
                }
            }
            
        return "redirect:/register";
        } 
            catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("message", new Message("something went wrong !!"+e.getMessage(), "alert-danger"));
            
        return "redirect:/register";
        }
        
        
        
    }
}
