package com.smartcontactmanager.smartcontactmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smartcontactmanager.smartcontactmanager.UserRepository;
import com.smartcontactmanager.smartcontactmanager.entities.User;
import com.smartcontactmanager.smartcontactmanager.helper.Message;

import jakarta.servlet.http.HttpSession;

@Controller
public class mycontroller {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/")
    public String Home()
    {
        return "home";
    }
    @RequestMapping("/about")
    public String about(Model m)
    {
        m.addAttribute("title", "About page ");
        return "about";
    }
     @RequestMapping("/signup")
    public String signup(Model m)
    {
        m.addAttribute("title", "Register- Smart Contact Manager");
        m.addAttribute("user", new User());
        return "signup";
    }
    @PostMapping("/do_register")
    public String registerUser(@ModelAttribute("user") User user,@RequestParam(value="agreement",defaultValue = "false") Boolean agreement,Model m,HttpSession session)
    {
       try {
        if(!agreement)
        {
            System.out.println("You Have not agreed the terms and conditions");
            throw new Exception("You Have not agreed the terms and conditions");
        }
         user.setRole("Role_User");
        user.setEnabled(true);
        User result= this.userRepository.save(user);
        
        m.addAttribute("user", new User());
        session.setAttribute("message",new Message("Successfully Registered","alert-success"));
        return "signup";
       } catch (Exception e) {
        // TODO: handle 
        e.printStackTrace();
        m.addAttribute("user", user);
        session.setAttribute("message",new Message("Something Went Wrong!"+e.getMessage(),"alert-danger"));
        return "signup";
       }
        
    }
}
