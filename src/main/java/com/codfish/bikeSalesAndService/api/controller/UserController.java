package com.codfish.bikeSalesAndService.api.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping("/user/info")
    public String userInfo(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {

            String username = authentication.getName();
            model.addAttribute("username", username);

            if (authentication.getPrincipal() instanceof UserDetails userDetails) {
                model.addAttribute("authorities", userDetails.getAuthorities());
                model.addAttribute("accountNonExpired", userDetails.isAccountNonExpired());
                model.addAttribute("accountNonLocked", userDetails.isAccountNonLocked());
                model.addAttribute("credentialsNonExpired", userDetails.isCredentialsNonExpired());
                model.addAttribute("enabled", userDetails.isEnabled());
            }
        } else {
            model.addAttribute("username", "Admin");
        }
        return "/info/user_info";
    }
}



