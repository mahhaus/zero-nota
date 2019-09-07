package com.mahhaus.zeronota.api;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * @author josias.soares
 * Create 01/09/2019
 */
@RestController
@RequestMapping("/")
public class IndexController {

    @GetMapping
    String get(){
        return "GET Spring Boot";
    }

    @GetMapping("/userInfo")
    public UserDetails userInfo(@AuthenticationPrincipal UserDetails userDetails){
        return userDetails;
    }
}
