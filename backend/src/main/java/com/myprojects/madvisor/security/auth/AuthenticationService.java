package com.myprojects.madvisor.security.auth;

import com.myprojects.madvisor.security.user.CustomUserDetails;
import com.myprojects.madvisor.security.user.Role;
import com.myprojects.madvisor.security.user.User;
import com.myprojects.madvisor.security.user.UserRepository;
import com.myprojects.madvisor.security.config.JwtService;
import com.myprojects.madvisor.transactions.model.Category;
import com.myprojects.madvisor.transactions.repository.CategoryRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final CategoryRepository categoryRepository;

    public AuthenticationService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, CategoryRepository categoryRepository) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.categoryRepository = categoryRepository;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();

        var customUserDetails = new CustomUserDetails(user);

        var jwtToken = jwtService.generateToken(customUserDetails);

        return new AuthenticationResponse(jwtToken);
    }


    public AuthenticationResponse register(RegisterRequest request) {

        var user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        User savedUser = repository.save(user);

        var customUserDetails = new CustomUserDetails(user);

        var jwtToken = jwtService.generateToken(customUserDetails);

        initializeDefaultCategories(savedUser);

        return new AuthenticationResponse(jwtToken);
    }

    private void initializeDefaultCategories(User user) {
        List<Category> defaultCategories = Arrays.asList(
                new Category("Work", "Briefcase-business", user),
                new Category("Scholarship", "Graduation-cap", user),
                new Category("Food", "Pizza", user),
                new Category("Gas", "Fuel", user),
                new Category("Transportation", "Car", user),
                new Category("Entertainment", "Party-popper", user)
        );

        categoryRepository.saveAll(defaultCategories);
    }

}
