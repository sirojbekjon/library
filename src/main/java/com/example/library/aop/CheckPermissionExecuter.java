package com.example.library.aop;
import com.example.library.entity.User;
import com.example.library.exception.ForbiddenException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CheckPermissionExecuter {

    @Before(value = "@annotation(checkPermission)")
    public void checkUserPermissionMyMethod(CheckPermission checkPermission){
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean exist = false;
        for (GrantedAuthority authority : user.getAuthorities()) {
            if (authority.getAuthority().equals(checkPermission.permission())){
                exist = true;
            break;
        }
        }
        if(!exist){
            throw new ForbiddenException(checkPermission.permission(), "Ruxsat yo'q");
        }
    }
}
