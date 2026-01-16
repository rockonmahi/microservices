package com.escola.userservice.controller;


import com.escola.userservice.dto.UserLoginDto;
import com.escola.userservice.form.UserLoginForm;
import com.escola.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public UserLoginDto userDetails(@RequestBody UserLoginForm userLogin) {
        Source schemaFile = new StreamSource(getClass().getResourceAsStream("schema/order_impex.xsd"));

        return userService.getUserDetails(userLogin);
    }
}
