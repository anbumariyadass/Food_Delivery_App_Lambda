package org.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.example.model.User;
import org.example.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.example.dto.UserDTO;
import org.example.jwt.JwtUtil;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    
    public String encodePassword(String password) {
        return passwordEncoder.encode(password); // Hash the password
    }

    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword); // Verify password
    }
    
    public UserDTO register(User user) {
    	Optional<User> userDetails = userRepository.findByUsername(user.getUsername());
    	if (userDetails.isPresent()) {
    		throw new RuntimeException(user.getUsername() + 
    				" is already present with the system. Please provide something else.");
    	}
        user.setPassword(encodePassword(user.getPassword()));
        userRepository.save(user);
        return new UserDTO(user.getUsername(), user.getRole(), user.getActive());
    }
    
    public UserDTO authenticate(String username, String password) {
        User userDetails = userRepository.findByUsername(username)
        		.orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        UserDTO user = new UserDTO();
        if (verifyPassword(password, userDetails.getPassword())) {
        	user.setUsername(userDetails.getUsername());
        	user.setRole(userDetails.getRole());
        	user.setActive(userDetails.getActive());
        	user.setToken(jwtUtil.generateToken(userDetails.getUsername(), userDetails.getRole()));
        } 
        return user;
    }
    
    public UserDTO updateUser(User user) {
    	User userDetail = userRepository.findByUsername(user.getUsername())
    			.orElseThrow(() -> new RuntimeException("User not found"));
    	
    	if (user.getPassword() != null && encodePassword(user.getPassword()) != userDetail.getPassword()) {
    		userDetail.setPassword(encodePassword(user.getPassword()));
    	}
    	
    	if (user.getRole() != null && user.getRole() != userDetail.getRole()) {
    		userDetail.setRole(user.getRole());
    	}
    	
    	if (user.getActive() != null && user.getActive() != userDetail.getActive()) {
    		userDetail.setActive(user.getActive());
    	}
    	
    	User userSaved = userRepository.save(userDetail);
    	
    	return new UserDTO(userSaved.getUsername(), userSaved.getRole(), userSaved.getActive());
    }
    
    @Transactional  
    public void deleteUser(String userName) {
    	System.out.println("deleteUser "+userName);
    	userRepository.deleteByUsername(userName);
    }
    
    public List<UserDTO> getAllUsers() {
    	List<UserDTO> allUsers = new ArrayList<>();
    	 userRepository.findAll().forEach(user -> allUsers.add(
    			 new UserDTO(user.getUsername(), user.getRole(), user.getActive())));
         return allUsers;
    }
    
    public UserDTO getUserInfo(String userName) {
    	User user = userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException(userName + "not found."));
    	return new UserDTO(user.getUsername(), user.getRole(), user.getActive());
    }
    
    public String checkUserNameAvailable(String userName) {
    	Optional<User> user = userRepository.findByUsername(userName);
        return user.isPresent() ? userName + " is not available for you. Please provide someother user name." : userName + " is available for you.";
   }
    
    public UserDetails getUserDetails(String userName) {
    	return userDetailsService.loadUserByUsername(userName);
    }
    
}
