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

    @GetMapping("/editprof")
    public String updateprofile()
    {
        return "/admin/editprofile";
    }
    @PostMapping("/editprofile")
    public String updatedprofile(Principal p,@RequestParam("qualification") String qualification,@RequestParam("name") String name,@RequestParam("email") String email,@RequestParam("address") String address)
    {
        UserDtls userdtl=userService.getUserByEmail(p.getName());
        userdtl.setEmail(email);
        userdtl.setAddress(address);
        userdtl.setName(name);
        userdtl.setQualification(qualification);
        userRepo.save(userdtl);

        return "redirect:/admin/editprof";
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

    @GetMapping("/register")
    public String register(){
        return "/admin/usercreation";
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
                   
                    session.setAttribute("message", new Message("successfull registration !!", "alert-success"));
                }
            }
            
        return "redirect:/admin/register";
        } 
            catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("message", new Message("something went wrong !!"+e.getMessage(), "alert-danger"));
            
        return "redirect:/admin/register";
        }
        
        
    }

    @GetMapping("/updateuserdetail")
    public String updateuser(@RequestParam("userId") int id,Model model)
    {
        UserDtls user= userService.getUserById(id);
        model.addAttribute("user", user);
        return "/admin/updateuser";
    }

    @PostMapping("/updateuserdetails")
    public String updateduser(@RequestParam("userId") int id,@RequestParam("qualification") String qualification,@RequestParam("name") String name,@RequestParam("email") String email,@RequestParam("address") String address)
    {
        UserDtls user1=userService.getUserById(id);
        user1.setName(name);
        user1.setEmail(email);
        user1.setAddress(address);
        user1.setQualification(qualification);
        userRepo.save(user1);
        return "redirect:/admin/user";

    }
}
