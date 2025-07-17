package com.devotel.userservice.soap;

import com.devotel.userservice.dto.UserResponse;
import org.springframework.ws.server.endpoint.annotation.*;
import lombok.RequiredArgsConstructor;
import com.devotel.userservice.service.UserService;
import com.devotel.userservice.soap.gen.GetUserByIdRequest;
import com.devotel.userservice.soap.gen.GetUserByIdResponse;

@Endpoint
@RequiredArgsConstructor
public class UserEndpoint {
    private static final String NAMESPACE = "http://devotel.com/users";
    private final UserService userService;

    @PayloadRoot(namespace = NAMESPACE, localPart = "GetUserByIdRequest")
    @ResponsePayload
    public GetUserByIdResponse getUserById(@RequestPayload GetUserByIdRequest req) {
        UserResponse dto = userService.getById(req.getId());

        com.devotel.userservice.soap.gen.User soapUser =
                new com.devotel.userservice.soap.gen.User();
        soapUser.setId(dto.id());
        soapUser.setName(dto.name());
        soapUser.setEmail(dto.email());

        GetUserByIdResponse res = new GetUserByIdResponse();
        res.setUser(soapUser);
        return res;
    }
}