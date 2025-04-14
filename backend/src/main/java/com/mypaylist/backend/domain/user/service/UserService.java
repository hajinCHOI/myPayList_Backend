package com.mypaylist.backend.domain.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mypaylist.backend.domain.user.User;
import com.mypaylist.backend.domain.user.dto.LoginRequestDto;
import com.mypaylist.backend.domain.user.dto.UserRequestDto;
import com.mypaylist.backend.domain.user.dto.UserResponseDto;
import com.mypaylist.backend.domain.user.repository.UserRepository;
import com.mypaylist.backend.global.jwt.JwtUtil;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public UserResponseDto signup(UserRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }
        if (userRepository.existsByNickname(requestDto.getNickname())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        User user = new User(requestDto.getEmail(), encodedPassword, requestDto.getNickname());
        userRepository.save(user);

        return new UserResponseDto(user.getEmail(), user.getNickname());
    }

    @Transactional(readOnly = true)
    public String login(LoginRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));
    
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    
        return jwtUtil.createToken(user.getEmail());
    }
    
}
