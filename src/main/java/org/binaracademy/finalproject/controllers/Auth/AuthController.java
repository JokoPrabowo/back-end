package org.binaracademy.finalproject.controllers.Auth;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.binaracademy.finalproject.dto.Request.AuthRequest.LoginRequest;
import org.binaracademy.finalproject.dto.Request.AuthRequest.SignupRequest;
import org.binaracademy.finalproject.dto.Response.JwtResponse;
import org.binaracademy.finalproject.dto.ResponseData;
import org.binaracademy.finalproject.entity.ERole;
import org.binaracademy.finalproject.entity.RoleEntity;
import org.binaracademy.finalproject.entity.UserEntity;
import org.binaracademy.finalproject.helper.utility.ErrorParsingUtility;
import org.binaracademy.finalproject.helper.utility.StatusCode;
import org.binaracademy.finalproject.repositories.RoleRepo;
import org.binaracademy.finalproject.repositories.UserRepo;
import org.binaracademy.finalproject.security.jwt.JwtUtils;
import org.binaracademy.finalproject.security.services.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "Operation signin and signup")
public class AuthController {

    public static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    public static final String ROLE_NOT_FOUND = "Error: Role is not found.";
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepo userRepository;

    @Autowired
    RoleRepo roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<ResponseData<JwtResponse>> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, Errors errors) {

        ResponseData<JwtResponse> responseData = new ResponseData<>();
        if (errors.hasErrors()) {
            responseData.setStatusCode(StatusCode.BAD_REQUEST);
            responseData.setSuccess(false);
            responseData.setMessage(ErrorParsingUtility.parse(errors).toString());
            logger.warn("request invalid :{}", ErrorParsingUtility.parse(errors));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        logger.info("sukses login user : {}", loginRequest.getUsername());
        responseData.setStatusCode(StatusCode.BAD_REQUEST);
        responseData.setSuccess(false);
        responseData.setMessage("sukses");
        responseData.setData(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseData<String>> registerUser(@Valid @RequestBody SignupRequest signUpRequest, Errors errors) {
        ResponseData<String> responseData = new ResponseData<>();
        if (errors.hasErrors()) {
            responseData.setStatusCode(StatusCode.BAD_REQUEST);
            responseData.setSuccess(false);
            responseData.setMessage(ErrorParsingUtility.parse(errors).toString());
            logger.warn("request invalid :{}", ErrorParsingUtility.parse(errors));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        if (Boolean.TRUE.equals(userRepository.existsByUsername(signUpRequest.getUsername()))) {
            logger.warn("Error: Username is already taken!");
            responseData.setStatusCode(StatusCode.BAD_REQUEST);
            responseData.setSuccess(false);
            responseData.setMessage("Error: Username is already taken!");
            return ResponseEntity.badRequest().body(responseData);
        }

        if (Boolean.TRUE.equals(userRepository.existsByEmail(signUpRequest.getEmail()))) {
            logger.warn("Error: Email is already in use!");
            responseData.setStatusCode(StatusCode.BAD_REQUEST);
            responseData.setSuccess(false);
            responseData.setMessage("Error: Email is already in use!");
            return ResponseEntity.badRequest().body(responseData);
        }

        // Create new user's account
        UserEntity user = new UserEntity(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword())
        );

        Set<String> strRoles = signUpRequest.getRole();
        Set<RoleEntity> roles = new HashSet<>();

        if (strRoles == null) {
            RoleEntity userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> {
                        logger.warn(ROLE_NOT_FOUND);
                        return new RuntimeException(ROLE_NOT_FOUND);
                    });
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        RoleEntity adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> {
                                    logger.warn(ROLE_NOT_FOUND);
                                    return new RuntimeException(ROLE_NOT_FOUND);
                                });
                        roles.add(adminRole);

                        break;
                    default:
                        RoleEntity userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> {
                                    logger.warn(ROLE_NOT_FOUND);
                                    return new RuntimeException(ROLE_NOT_FOUND);
                                });
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
        responseData.setStatusCode(StatusCode.OK);
        responseData.setSuccess(true);
        responseData.setMessage("User registered successfully!");
        logger.info("new signup sukses user : {}", signUpRequest.getUsername());
        return ResponseEntity.ok(responseData);
    }
}