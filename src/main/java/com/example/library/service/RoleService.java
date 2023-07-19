package com.example.library.service;

import com.example.library.entity.Role;
import com.example.library.payload.ApiResponse;
import com.example.library.payload.RoleDto;
import com.example.library.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {


    private final RoleRepository roleRepository;

    public ApiResponse addRole(RoleDto roleDto){
        try {
            if (roleRepository.existsByName(roleDto.getName())) {
                return new ApiResponse("Bunday Role mavjud", false);
            }
            Role role = new Role(
                    roleDto.getName(),
                    roleDto.getDescription(),
                    roleDto.getPermissionList()
            );
            roleRepository.save(role);
            return new ApiResponse("Lavozim yaratildi", true);
        }
        catch (Exception e){
            e.getMessage();
        }
        return new ApiResponse("Enternal Server Error", false);
    }

    public ApiResponse editRole(Long id, RoleDto lavozimDto) {
        try {
            Optional<Role> optionalLavozim = roleRepository.findById(id);
            Role role = optionalLavozim.get();
            role.setName(lavozimDto.getName());
            role.setDescription(lavozimDto.getDescription());
            role.setPermissions(lavozimDto.getPermissionList());
            roleRepository.save(role);
            return new ApiResponse("edited", true);
        }
        catch (Exception e){
            e.getMessage();
        }
        return new ApiResponse("Not Found", false);
    }

    public ApiResponse deleteRole(Long roleId) {
        try {
            roleRepository.deleteById(roleId);
            return new ApiResponse("deleted", true);
        }
        catch (Exception e){
            e.getMessage();
        }
        return new ApiResponse("Not Deleted", false);
    }

    public ResponseEntity<?> getRoles() {
        try {
            List<Role> roles = roleRepository.findAll();
            return ResponseEntity.status(200).body(roles);
        }
        catch (Exception e){
            e.getMessage();
        }
        return ResponseEntity.status(500).body("Enternal Server Error");
    }
}
