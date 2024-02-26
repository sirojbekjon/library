package com.example.library.service;

import com.example.library.entity.Department;
import com.example.library.entity.User;
import com.example.library.payload.ApiResponse;
import com.example.library.payload.UserDto;
import com.example.library.repository.DepartmentRepository;
import com.example.library.repository.ManagmentRepository;
import com.example.library.repository.RoleRepository;
import com.example.library.repository.UserRepository;
import com.example.library.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final RoleRepository roleRepository;
  private final JwtProvider jwtProvider;
  private final ManagmentRepository managmentRepository;
  private final DepartmentRepository departmentRepository;

    public ApiResponse deleteUser(Long userId) {
        try {
            userRepository.deleteById(userId);
            return new ApiResponse("user deleted", true);
        }
        catch (Exception e){
            e.getMessage();
        }
        return new ApiResponse("user has not deleted", false);
    }

    public ApiResponse editUser(UserDto userDto) {
        try {
            if (!userDto.getPassword().equals(userDto.getPrePassword())) {
                return new ApiResponse("parollar mos emas", false);
            }
            User user = new User(
                    userDto.getName(),
                    userDto.getUsername(),
                    userDto.getPhone(),
                    passwordEncoder.encode(userDto.getPassword()),
                    managmentRepository.findById(userDto.getManagment()).get(),
                    departmentRepository.findById(userDto.getDepartment()).get()
            );
            userRepository.save(user);
            String token = jwtProvider.generateToken(userDto.getUsername(), user.getRole());
            TokenService tokenService = new TokenService(token);
            return new ApiResponse(tokenService.getToken(), true);
        }
        catch (Exception e){
            e.getMessage();
        }
        return new ApiResponse("user hgas not edited", false);
    }

    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            return ResponseEntity.status(!users.isEmpty() ? 200 : 409).body(users);
        }
        catch (Exception e){
            e.getMessage();
        }
        return ResponseEntity.status(500).body("user has not found");
    }

    public ResponseEntity<?> getUser(Long userId) {
        try {
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                return ResponseEntity.status(202).body(user);
            }
            return ResponseEntity.status(404).body("Not found");
        }
        catch (Exception e){
            e.getMessage();
        }
        return ResponseEntity.status(500).body("Enternal Server Error");
    }
}
