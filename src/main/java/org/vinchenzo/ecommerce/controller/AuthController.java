package org.vinchenzo.ecommerce.controller;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.vinchenzo.ecommerce.exception.NoActiveUserException;
import org.vinchenzo.ecommerce.model.AppRole;
import org.vinchenzo.ecommerce.model.Role;
import org.vinchenzo.ecommerce.model.User;
import org.vinchenzo.ecommerce.repository.RoleRepository;
import org.vinchenzo.ecommerce.repository.UserRepository;
import org.vinchenzo.ecommerce.security.jwt.JwtUtils;
import org.vinchenzo.ecommerce.security.request.LoginRequest;
import org.vinchenzo.ecommerce.security.request.SignupRequest;
import org.vinchenzo.ecommerce.security.response.MessageResponse;
import org.vinchenzo.ecommerce.security.response.UserInfoResponse;
import org.vinchenzo.ecommerce.security.services.UserDetailsImpl;
import java.util.*;
import java.util.stream.Collectors;


@SuppressWarnings("ALL")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    JwtUtils jwtUtils;

    AuthenticationManager authenticationManager;

    UserRepository userRepository;

    RoleRepository roleRepository;

    PasswordEncoder encoder;

    public AuthController(JwtUtils jwtUtils, AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (AuthenticationException exception) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        UserInfoResponse response = new UserInfoResponse(userDetails.getUsername(),
                roles, userDetails.getId(),jwtCookie.getValue());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByUserName(signupRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signupRequest.getUsername(),
                signupRequest.getEmail(),
                encoder.encode(signupRequest.getPassword()));

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "seller":
                        Role modRole = roleRepository.findByRoleName(AppRole.ROLE_SELLER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

//    @GetMapping("/username")
//    public String currentUser(Authentication authentication) {
//        if(authentication!=null){
//            return authentication.getName();
//        }
//        else return "NULL";
//    }
    @GetMapping("/username")
    public ResponseEntity<?> currentUsername(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            throw new NoActiveUserException("No active user found. Please log in.");
        }
        return ResponseEntity.ok(authentication.getName());
    }

    @GetMapping("/user")
    public ResponseEntity<?> currentUser(Authentication authentication) {
        if (authentication==null ||!authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return AuthStatusResponse();
        }

        else {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            UserInfoResponse response = new UserInfoResponse(userDetails.getUsername(),
                    roles, userDetails.getId(),"hidden");
            return ResponseEntity.ok().body(response);
        }
    }

    @PostMapping("signout")
    public ResponseEntity<?> signoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(new MessageResponse("You have been signed out!"));
    }

    private ResponseEntity<?> AuthStatusResponse() {
        Map<String, Object> response2 = new HashMap<>();
        response2.put("status", HttpStatus.UNAUTHORIZED.value());
        response2.put("error", "Unauthorized");
        response2.put("message", "No active user found");
        return new ResponseEntity<>(response2, HttpStatus.UNAUTHORIZED);
    }


}
