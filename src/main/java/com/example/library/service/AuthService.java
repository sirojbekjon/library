package com.example.library.service;

import com.example.library.entity.Department;
import com.example.library.entity.Managment;
import com.example.library.entity.User;
import com.example.library.exception.ResourceNotFoundException;
import com.example.library.payload.LoginDto;
import com.example.library.payload.RegisterDto;
import com.example.library.repository.DepartmentRepository;
import com.example.library.repository.ManagmentRepository;
import com.example.library.repository.RoleRepository;
import com.example.library.repository.UserRepository;
import com.example.library.security.JwtProvider;
import com.example.library.utils.RoleConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
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
import java.util.Optional;

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
    private final ManagmentRepository managmentRepository;
    private final DepartmentRepository departmentRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException(username));
    }

    public HttpEntity<?> registerUser(RegisterDto registerDto) {
        try {
            Optional<User> optionalUser = userRepository.findByUsername(registerDto.getUsername());
            if (optionalUser.isPresent()){
                return ResponseEntity.status(404).body("This username has already been created");
            }
            Optional<Managment> managmentRepositoryById = managmentRepository.findById(registerDto.getManagment());
            User user = new User();
            if (registerDto.getDepartment()==null){
                user.setName(registerDto.getName());
                user.setUsername(registerDto.getUsername());
                user.setPhone(registerDto.getPhone());
                user.setManagment(managmentRepositoryById.get());
                user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
                user.setRole(roleRepository.findByName(RoleConstants.USER).orElseThrow(() -> new ResourceNotFoundException("role", "name", RoleConstants.USER)));

            } else if (managmentRepositoryById.isPresent() && registerDto.getDepartment()!=null)
            {
                Optional<Department> departmentRepositoryById = departmentRepository.findById(registerDto.getDepartment());
                user.setName(registerDto.getName());
                user.setUsername(registerDto.getUsername());
                user.setPhone(registerDto.getPhone());
                user.setManagment(managmentRepositoryById.get());
                user.setDepartment(departmentRepositoryById.get());
                user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
                user.setRole(roleRepository.findByName(RoleConstants.USER).orElseThrow(() -> new ResourceNotFoundException("role", "name", RoleConstants.USER)));
            }
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

    public HttpEntity<?> loginUser(LoginDto loginDto) {
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
