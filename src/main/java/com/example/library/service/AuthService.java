package com.example.library.service;

import com.example.library.entity.User;
import com.example.library.exception.ResourceNotFoundException;
import com.example.library.payload.LoginDto;
import com.example.library.payload.RegisterDto;
import com.example.library.repository.RoleRepository;
import com.example.library.repository.UserRepository;
import com.example.library.security.JwtProvider;
import com.example.library.utils.RoleConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException(username));
    }

    public ResponseEntity<?> registerUser(RegisterDto registerDto) {
        try {
            if (!registerDto.getPassword().equals(registerDto.getPrePassword())) {
                return ResponseEntity.status(404).body("parollar mos emas");
            }
            if (userRepository.existsByEmail(registerDto.getEmail())) {
                return ResponseEntity.status(404).body("Bunday email avval ro'yxatdan o'tgan");
            }

            User user = new User(
                    registerDto.getName(),
                    registerDto.getUsername(),
                    registerDto.getEmail(),
                    passwordEncoder.encode(registerDto.getPassword()),
                    roleRepository.findByName(RoleConstants.USER).orElseThrow(() -> new ResourceNotFoundException("role", "name", RoleConstants.USER))
            );
            userRepository.save(user);
            String token = jwtProvider.generateToken(registerDto.getUsername(), user.getRole());
            TokenService tokenService = new TokenService(token);
            return ResponseEntity.status(200).body(tokenService.getToken());
        }
        catch (Exception e){
            e.getMessage();
        }
        return ResponseEntity.status(500).body("Internal Server Error");
    }

    public ResponseEntity<?> loginUser(LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
            User user = (User) authentication.getPrincipal();
            String token = jwtProvider.generateToken(loginDto.getUsername(), user.getRole());
            TokenService tokenService = new TokenService(token);
            return ResponseEntity.ok(tokenService.getToken());
        }
        catch (Exception e){
            e.getMessage();
        }
        return ResponseEntity.status(500).body("Enternal Server Error");
    }



}
