package tgu.Gateway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tgu.Gateway.Utils.JWTGenerate;
import tgu.Gateway.security.conf.PassWordEncoder;
import tgu.Gateway.security.conf.UserDetailsService;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class LoginController {

    private JWTGenerate jwtGenerate;

    private UserDetailsService userDetailsService;

    private PassWordEncoder passWordEncoder;



}
