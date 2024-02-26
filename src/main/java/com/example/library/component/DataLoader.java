package com.example.library.component;
import com.example.library.entity.Role;
import com.example.library.entity.User;
import com.example.library.entity.enums.Permission;
import com.example.library.repository.RoleRepository;
import com.example.library.repository.UserRepository;
import com.example.library.utils.RoleConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Arrays;

import static com.example.library.entity.enums.Permission.*;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${spring.datasource.initalization-mode}")
    private String initialModeType;



    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (initialModeType.equals("always")){
            Permission[] permessions = Permission.values();

            Role superAdmin = roleRepository.save(new Role(
                    RoleConstants.SUPERADMIN,
                    "SuperAdmin uchun hamma huquq berilgan",
                    Arrays.asList(permessions)
            ));
            Role admin = roleRepository.save(new Role(
                    RoleConstants.ADMIN,
                    "Admin uchun bazi bir imkoniyatlar cheklangan",
                    Arrays.asList(ADD_USER,VIEW_USER,ADD_DOC)
            ));
            Role user = roleRepository.save(new Role(
                    RoleConstants.USER,
                    "User uchun bazi bir imkoniyatlar cheklangan",
                    Arrays.asList(
                            ADD_DOC,
                            VIEW_DOC,
                            DELETE_DOC,
                            EDIT_DOC)
            ));
            userRepository.save(new User(
                    "superAdmin",
                    "superAdmin",
                    "5361",
                    passwordEncoder.encode("superAdmin"),
                    superAdmin
            ));

            userRepository.save(new User(
                    "admin",
                    "admin",
                    "5362",
                    passwordEncoder.encode("admin"),
                    admin
            ));
        }
    }
}




