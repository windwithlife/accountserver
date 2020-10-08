package com.simple.account.client;

import com.simple.account.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.simple.account.AccountConstant;
import com.simple.common.api.BaseResponse;
import com.simple.common.auth.AuthConstant;

import javax.validation.Valid;

@FeignClient(name = AccountConstant.SERVICE_NAME, path = "/v1/example", url = "${simple.example-service-endpoint}")
// TODO Client side validation can be enabled as needed
// @Validated
public interface ExampleClient {

    @PostMapping(path = "/create")
    GenericAccountResponse createAccount(@RequestHeader(AuthConstant.AUTHORIZATION_HEADER) String authz, @RequestBody @Valid CreateAccountRequest request);
    // used after a user clicks a confirmation link in their email.
    @GetMapping(path = "/test")
    BaseResponse changeEmail(@RequestHeader(AuthConstant.AUTHORIZATION_HEADER) String authz, @RequestParam @Valid String request);
}
