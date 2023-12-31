package com.diazrony.authcourse.controller;

import com.diazrony.authcourse.dto.AuthRequest;
import com.diazrony.authcourse.dto.AuthResponse;
import com.diazrony.authcourse.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1")
public class Auth {

    AuthService authService;

    public Auth(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(path = "/oauth/access_token")
    public AuthResponse login(@RequestBody AuthRequest request) throws Exception {
        return authService.login(request);
    }

}
