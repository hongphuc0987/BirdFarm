package com.v2p.swp391.application.controller;

import com.v2p.swp391.application.model.User;
import com.v2p.swp391.security.CurrentUser;
import com.v2p.swp391.security.UserPrincipal;
import com.v2p.swp391.common.api.CoreApiResponse;
import com.v2p.swp391.application.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/v1/me")
public class PersonalController {

    private final UserService userService;
    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public CoreApiResponse<User> getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return CoreApiResponse.success(userService.findById(userPrincipal.getId()));
    }
}
