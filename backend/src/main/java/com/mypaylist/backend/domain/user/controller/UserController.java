package com.mypaylist.backend.domain.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mypaylist.backend.domain.user.User;
import com.mypaylist.backend.domain.user.dto.LoginRequestDto;
import com.mypaylist.backend.domain.user.dto.UserRequestDto;
import com.mypaylist.backend.domain.user.dto.UserResponseDto;
import com.mypaylist.backend.domain.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody @Valid UserRequestDto requestDto) {
        UserResponseDto response = userService.signup(requestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequestDto requestDto) {
        String token = userService.login(requestDto);
        return ResponseEntity.ok(token); // 토큰을 응답으로 전달
    }

    @GetMapping("/me")
    public UserResponseDto getMyInfo(@AuthenticationPrincipal User user) {
        return new UserResponseDto(user.getEmail(), user.getNickname());
    }


}
