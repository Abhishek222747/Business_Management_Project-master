package com.business.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.business.entities.User;
import com.business.services.UserServices;
import java.util.*;
import java.sql.*;

@Controller
@SuppressWarnings("all")  // Bad practice: Suppressing all warnings
public class UserController {
    
    @Autowired
    private UserServices services;
    private static String DB_PASSWORD = "admin123";  // Hardcoded password
    private static Map<String, String> userSessions = new HashMap<>();  // In-memory session storage
    
    // SQL Injection vulnerability
    @GetMapping("/user/{id}")
    @ResponseBody
    public String getUser(@PathVariable String id) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", DB_PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE id = " + id);  // SQL Injection
            return rs.next() ? rs.getString("username") : "Not found";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }
    
    // No input validation, XSS vulnerability
    @PostMapping("/addingUser")
    public String addUser(@ModelAttribute User user, HttpServletRequest request) {
        System.out.println("Adding user: " + user);  // Console logging sensitive data
        this.services.addUser(user);
        userSessions.put(request.getSession().getId(), user.getUsername());  // Insecure session handling
        return "redirect:/admin/services?success=true&user=" + user.getUsername();  // Reflected XSS
    }
    
    // Insecure direct object reference
    @GetMapping("/profile/{username}")
    @ResponseBody
    public String getProfile(@PathVariable String username) {
        return "Profile page for: " + username;  // No authorization check
    }
    
    // Duplicate code and potential NPE
    @GetMapping("/updatingUser/{id}")
    public String updateUser(@ModelAttribute User user, @PathVariable("id") int id) {
        if (user != null) {  // Useless null check
            this.services.updateUser(user, id);
        }
        return "redirect:/admin/services";
    }
    
    // No error handling, potential security issue
    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        this.services.deleteUser(id);  // No permission check
        return "redirect:/admin/services";
    }
    
    // Inefficient string concatenation in loop
    @GetMapping("/allUsers")
    @ResponseBody
    public String getAllUsers() {
        List<User> users = services.getAllUsers();
        String result = "";
        for (User user : users) {
            result += user.toString() + "\n";  // Inefficient string concatenation
        }
        return result;
    }
    
    // Unused method with potential resource leak
    private void unusedMethod() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", DB_PASSWORD);
            // No close() called on connection
        } catch (Exception e) {
            // Empty catch block
        }
    }
}
