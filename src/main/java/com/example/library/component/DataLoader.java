package com.example.library.component;

import com.example.library.entity.Role;
import com.example.library.entity.User;
import com.example.library.entity.enums.Permission;
import com.example.library.repository.RoleRepository;
import com.example.library.repository.UserRepository;
import com.example.library.utils.RoleConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.example.library.entity.enums.Permission.*;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final
    RoleRepository roleRepository;
    private final 
    UserRepository userRepository;
    private final
    PasswordEncoder passwordEncoder;

    @Value("${spring.datasource.initalization-mode}")
    public String initializate;

    @Override
    public void run(String... args){
        if (initializate.equals("always")){
            Permission[] permessions = Permission.values();

            Role superAdmin = roleRepository.save(new Role(
                    RoleConstants.SUPERADMIN,
                    "SuperAdmin uchun hamma huquq berilgan",
                    Arrays.asList(permessions)
            ));
            Role admin = roleRepository.save(new Role(
                    RoleConstants.ADMIN,
                    "Admin uchun bazi bir imkoniyatlar cheklangan",
                    Arrays.asList(ADD_USER,VIEW_USER,ADD_BOOK,EDIT_BOOK,VIEW_BOOK)
            ));
            Role user = roleRepository.save(new Role(
                    RoleConstants.USER,
                    "foydalanuvchi uchun cheklangan imkoniyatlar",
                    Arrays.asList(VIEW_BOOK)
            ));

            userRepository.save(new User(
                    "superAdmin",
                    "superAdmin",
                    "superAdmin@gmail.com",
                    passwordEncoder.encode("superAdmin"),
                    superAdmin
            ));

            userRepository.save(new User(
                    "admin",
                    "admin",
                    "admin@gmail.com",
                    passwordEncoder.encode("admin"),
                    admin
            ));
        }

    }
}




