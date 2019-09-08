package com.mahhaus.zeronota.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author josias.soares
 * Create 01/09/2019
 */
@RestController
@RequestMapping("/")
public class IndexController {

    @GetMapping
    String get() {
        return "GET Spring Boot";
    }
//
//    @GetMapping("/userInfo")
//    public UserDetails userInfo(@AuthenticationPrincipal UserDetails userDetails){
//        return userDetails;
//    }
}
