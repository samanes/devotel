package com.devotel.profileservice.service;

import com.devotel.profileservice.dto.ProfileCreateRequest;
import com.devotel.profileservice.dto.ProfileResponse;
import com.devotel.profileservice.entity.Profile;
import com.devotel.profileservice.repository.ProfileRepository;
import com.devotel.shared.exception.ExternalServiceException;
import com.devotel.shared.exception.ResourceNotFoundException;
import com.devotel.userservice.soap.gen.GetUserByIdRequest;
import com.devotel.userservice.soap.gen.GetUserByIdResponse;
import com.devotel.userservice.soap.gen.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.WebServiceTransportException;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository repo;
    private final WebServiceTemplate ws;

    @Override
    public ProfileResponse create(ProfileCreateRequest dto) {
        GetUserByIdRequest req = new GetUserByIdRequest();
        req.setId(dto.userId());
        GetUserByIdResponse res;
        try {
            res = (GetUserByIdResponse) ws.marshalSendAndReceive(req);
        } catch (SoapFaultClientException ex) {
            String faultCode = ex.getFaultCode().getLocalPart();
            String reason    = ex.getFaultStringOrReason();
            if ("UserNotFound".equals(faultCode) || reason.contains("User not found")) {
                throw new ResourceNotFoundException("User with id=" + dto.userId() + " not found");
            }
            throw new ExternalServiceException(
                    "SOAP Fault [" + faultCode + "] " + reason, ex
            );
        } catch (WebServiceTransportException ex) {
            throw new ExternalServiceException(
                    "Cannot reach User SOAP service at configured endpoint", ex
            );
        }

        User soapUser = res.getUser();
        Profile saved = repo.save(
                new Profile(null,
                        dto.bio(),
                        dto.location(),
                        dto.age(),
                        soapUser.getId()
                )
        );
    return new ProfileResponse(
        saved.getId(),
        saved.getBio(),
        saved.getLocation(),
        saved.getAge(),
        new com.devotel.profileservice.dto.UserDTO(
            soapUser.getId(), soapUser.getName(), soapUser.getEmail()));
    }

    @Override
    public ProfileResponse get(Long id) {
        Profile p = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profile with id=" + id + " not found"));

        GetUserByIdRequest req = new GetUserByIdRequest();
        req.setId(p.getUserId());
        GetUserByIdResponse res;
        try {
            res = (GetUserByIdResponse) ws.marshalSendAndReceive(req);
        } catch (SoapFaultClientException ex) {
            String faultCode = ex.getFaultCode().getLocalPart();
            String reason    = ex.getFaultStringOrReason();
            if ("UserNotFound".equals(faultCode) || reason.contains("User not found")) {
                throw new ResourceNotFoundException("User with id=" + p.getUserId() + " not found");
            }
            throw new ExternalServiceException(
                    "SOAP Fault [" + faultCode + "] " + reason, ex
            );
        } catch (WebServiceTransportException ex) {
            throw new ExternalServiceException(
                    "Cannot reach User SOAP service at configured endpoint", ex
            );
        }

        User soapUser = res.getUser();
    return new ProfileResponse(
        p.getId(),
        p.getBio(),
        p.getLocation(),
        p.getAge(),
        new com.devotel.profileservice.dto.UserDTO(
            soapUser.getId(), soapUser.getName(), soapUser.getEmail()));
    }
}