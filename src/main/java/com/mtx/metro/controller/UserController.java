package com.mtx.metro.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mtx.metro.constants.CodeConstants;
import com.mtx.metro.controller.dto.RegisterDto;
import com.mtx.metro.controller.dto.UpdateDto;
import com.mtx.metro.service.impl.UserEmailServiceImpl;
import com.mtx.metro.utils.Result;
import com.mtx.metro.controller.dto.LoginDto;
import com.mtx.metro.exception.ServiceException;
import com.mtx.metro.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@RestController
@Validated
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserEmailServiceImpl userEmailService;

    //登录用户
    @PostMapping("/login")
    public Result login(@RequestBody @Valid LoginDto LoginDto){
        return userService.login(LoginDto);
    }

    //登出用户
    @PostMapping("/logout")
    public Result logout(){
        return userService.logout();
    }

    //注册用户
    @PostMapping("/register")
    public Result userRegister(@RequestBody @Valid RegisterDto registerDto){
        return userService.register(registerDto);
    }

    //邮箱验证
    @GetMapping("/email")
    public Result sendEmailCode(@RequestParam
                                    @Valid @NotBlank(message = "邮箱不能为空")
                                    @Email(message = "邮箱格式不正确")
                                            String email){
        return Result.success(userEmailService.sendEmailCode(email));
    }

    //查询所有用户信息!!
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Result getAllUserInfo(){
        return Result.success(userService.getAllUserInfo());
    }

    //根据id查询用户信息!!
    @GetMapping("/info")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Result getStuByID(@RequestParam
                                         String uid){
        return Result.success(userService.getUserByID(uid));
    }

    //删除用户信息!!
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Result deleteUserById(@RequestParam String uid){
        return Result.success(userService.deleteUserById(uid));
    }

    //更新用户数据
    @PostMapping("/update")
    @PreAuthorize("hasAnyAuthority('ROLE_NORMAL','ROLE_COMPANY','ROLE_ADMIN')")
    public Result updateUserInfo(@RequestBody
                                 @Valid UpdateDto ud){
        return Result.success(userService.updateUserInfo(ud));
    }

    //更改用户密码!!
    @PostMapping("/update/password") //改变数据库数据就用post
    @PreAuthorize("hasAnyAuthority('ROLE_NORMAL','ROLE_COMPANY','ROLE_ADMIN')")
    public Result updateUserPwd(@RequestParam
                                            String id,
                                @RequestParam
                                @Valid @NotBlank(message = "原密码不能为空")
                                        String oldpwd,
                                @RequestParam
                                    @Valid @NotBlank(message = "新密码不能为空")
                                            String newpwd){
        return Result.success(userService.updateUserPwd(id,oldpwd,newpwd));
    }
}
