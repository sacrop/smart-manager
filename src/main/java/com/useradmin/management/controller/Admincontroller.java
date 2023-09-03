package com.useradmin.management.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.useradmin.management.helper.Message;
import com.useradmin.management.model.UserDtls;
import com.useradmin.management.repository.UserRepository;
import com.useradmin.management.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class Admincontroller {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ModelAttribute
    public void userDtails(Model m,Principal p)
    {
        String email=p.getName();
        UserDtls user=userRepo.findByEmail(email);
        m.addAttribute("user", user);

    }
    @GetMapping("/")
    public String home(){
        return "/admin/home";
    }

    @GetMapping("/search")
    public String searching(@RequestParam("searchQuery") String name,Model model){
        
        System.out.println("name"+name);
        List<UserDtls> userdetail=new ArrayList<>(userService.getUserByName(name));
        model.addAttribute("tuser",userdetail);
        return "/admin/userman";
    }

    @GetMapping("/user")
    public String manageuser(Model model){
        List<UserDtls> tbuser=new ArrayList<>(userService.getAllUser());
        model.addAttribute("tuser", tbuser);

        return "/admin/userman";
    }
    @GetMapping("/delete")
    public String deleteUser(@RequestParam("userId") int id){
       userService.deleteUserById(id);

        return "redirect:/admin/user";

    }

    @GetMapping("/updateadmin")
    public String changeuserrole(@RequestParam("userId") int id){
        userService.changeUserRole(id, "ROLE_ADMIN");

        return "redirect:/admin/user";
    }
    @GetMapping("/updateuser")
    public String changeadminrole(@RequestParam("userId") int id){
        userService.changeUserRole(id, "ROLE_USER");
        return "redirect:/admin/user";
    }

    @GetMapping("/change")
    public String loadpass(){
        return "/admin/change_password";
    }


    

     @PostMapping("/updatepassword")
    public String changepass(Principal p, @RequestParam("old") String oldpass, @RequestParam("newpass") String newpass,HttpSession session)
    {
        String email=p.getName();

        UserDtls loginuser=userRepo.findByEmail(email);
           Boolean succ= passwordEncoder.matches(oldpass, loginuser.getPassword());

           if(succ)
           {
              loginuser.setPassword(passwordEncoder.encode(newpass));
              UserDtls login=userRepo.save(loginuser);
                if(login!=null){
                    session.setAttribute("msgp", new Message("password updated successful","alert-success"));
                }
                else{
                        session.setAttribute("msgp", new Message("something wrong on server", "alert-danger"));
                }
           }
           else
           {
            session.setAttribute("msgp", new Message("invalid old password", "alert-danger"));
           }



        return"redirect:/admin/change";
    }

}
