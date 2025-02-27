package com.studycollaboproject.scope.controller;

import com.studycollaboproject.scope.dto.SignupTestDto;
import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.repository.UserRepository;
import com.studycollaboproject.scope.security.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@RestController
public class ApiTestRestController {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/api-test")
    public Map<String,String>  makeApiTestUser(@RequestBody SignupTestDto signupTestDto){
        User user = new User(signupTestDto);
        userRepository.save(user);
        String jwtToken =jwtTokenProvider.createToken(signupTestDto.getSnsId());
        Map<String ,String> token = new HashMap<>();
        token.put("token",jwtToken);
        return token;
    }

    @GetMapping("/api-test")
    public Map<String ,String > loginApiTestUser(@Parameter(description = "닉네임", in = ParameterIn.QUERY)@RequestParam String snsId){
        String jwtToken =jwtTokenProvider.createToken(snsId);
        Map<String ,String> token = new HashMap<>();
        token.put("token",jwtToken);
        return token;
    }
}
