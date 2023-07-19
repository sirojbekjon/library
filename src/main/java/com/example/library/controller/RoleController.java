package com.example.library.controller;


import com.example.library.log.Loggerr;
import com.example.library.payload.ApiResponse;
import com.example.library.payload.RoleDto;
import com.example.library.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/role")
public class RoleController {

     private final RoleService roleService;

    @PreAuthorize(value = "hasAuthority('ADD_ROLE')")
    @PostMapping("/add")
    public ResponseEntity<?> addRole(@Valid @RequestBody RoleDto roleDto){
        Loggerr.log();
        ApiResponse apiResponse = roleService.addRole(roleDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


    @PreAuthorize(value = "hasAuthority('EDIT_ROLE')")
    @PutMapping("/edit/{id}")
    public ResponseEntity<?>editRole(@PathVariable Long id,@Valid @RequestBody RoleDto roleDto){
        Loggerr.log();
        ApiResponse apiResponse = roleService.editRole(id,roleDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAuthority('DELETE_ROLE')")
    @DeleteMapping("/delete/{roleId}")
    public ResponseEntity<?> deleteRole(@PathVariable Long roleId){
        Loggerr.log();
        ApiResponse apiResponse = roleService.deleteRole(roleId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    @PreAuthorize(value = "hasAuthority('VIEW_ROLE')")
    @GetMapping("/view")
    public ResponseEntity<?> getAllRoles(){
        Loggerr.log();
        return roleService.getRoles();
    }


}
