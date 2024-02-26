package com.example.library.entity;


import com.example.library.entity.enums.Permission;
import com.example.library.entity.template.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends AbstractEntity implements UserDetails {


    private String name;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String phone;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    private FileUpload fileUpload;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Role role;

    @ManyToOne
    private Managment managment;

    @ManyToOne
    private Department department;


    @JsonIgnore
    private boolean isAccountNonLocked = true;
    @JsonIgnore
    private boolean isAccountNonExpired = true;
    @JsonIgnore
    private boolean isCredentialsNonExpired = true;
    @JsonIgnore
    private boolean isEnabled = true;


    public User(String name, String username, String phone, String password, Managment managment, Department department) {
        this.name = name;
        this.username = username;
        this.phone = phone;
        this.password = password;
        this.managment = managment;
        this.department = department;
    }

    public User(String name,String username, String phone, String password, Role role) {
        this.name = name;
        this.username = username;
        this.phone = phone;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<Permission> permissions = this.role.getPermissions();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Permission permission : permissions) {
            grantedAuthorities.add(new SimpleGrantedAuthority(permission.name()));
        }
        return grantedAuthorities;
    }
}
