package com.codfish.bikeSalesAndService.api.controller;

import com.codfish.bikeSalesAndService.infrastructure.security.UserEntity;
import com.codfish.bikeSalesAndService.infrastructure.security.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserJpaRepository userJpaRepository;

    @GetMapping("/user/info")
    public String userInfo(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            addUserDetailsToModel(authentication, model);
        } else {
            addDefaultValuesToModel(model);
        }
        return "/info/user_info";
    }

    private void addUserDetailsToModel(Authentication authentication, Model model) {
        String username = authentication.getName();
        model.addAttribute("username", username);

        UserEntity userEntity = userJpaRepository.findByUserName(username);
        if (userEntity != null) {
            model.addAttribute("email", userEntity.getEmail());
            model.addAttribute("lastLogin", userEntity.getLastLogin());
        }

        if (authentication.getPrincipal() instanceof UserDetails userDetails) {
            addUserDetailsAttributesToModel(userDetails, model);
        }
    }

    private void addUserDetailsAttributesToModel(UserDetails userDetails, Model model) {
        model.addAttribute("authorities", userDetails.getAuthorities());
        model.addAttribute("accountNonExpired", userDetails.isAccountNonExpired());
        model.addAttribute("accountNonLocked", userDetails.isAccountNonLocked());
        model.addAttribute("credentialsNonExpired", userDetails.isCredentialsNonExpired());
        model.addAttribute("enabled", userDetails.isEnabled());
    }

    private void addDefaultValuesToModel(Model model) {
        model.addAttribute("username", "Admin");
    }
}
