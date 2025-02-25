package org.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.example.dto.ApiResponse;
import org.example.dto.AuthRequest;
import org.example.dto.UserDTO;
import org.example.model.User;
import org.example.service.AuthenticationService;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "*")  // Allows requests from any origin
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationService authenticationService;
    
    //This method will be removed later once the admin user will be added into the application at realtime.
    @PostMapping("/register/admin")
    public ResponseEntity<ApiResponse> register() {
    	User user = new User();
    	user.setUsername("admin");
    	user.setPassword("admin");
    	user.setRole("ADMIN");
    	user.setActive("Y");
    	try {
    		UserDTO userDTO = authenticationService.register(user);
    		return ResponseEntity.ok(new ApiResponse(user.getUsername() + " is created successfully",userDTO));
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponse(e.getMessage(), null));
    	}
    }
    
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody User user) {
    	try {
    		UserDTO userDTO = authenticationService.register(user);
    		return ResponseEntity.ok(new ApiResponse(user.getUsername() + " is created successfully",userDTO));
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponse(e.getMessage(), null));
    	}
    }
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody AuthRequest request) {
        try {
        	 UserDTO user = authenticationService.authenticate(request.getUsername(), request.getPassword());
        	 if (user != null && user.getToken() != null) {
                 return ResponseEntity.ok(new ApiResponse("Login Successful", user));
             } else {
                 return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                         .body(new ApiResponse("Invalid credentials", null));
             }
        } catch(Exception e) {
        	return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(e.getMessage(), null));
        }
       
    }

    
    @GetMapping("/allUsers")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> getAllUsers() {       
        return ResponseEntity.ok(new ApiResponse("All Users Retrieval Success", authenticationService.getAllUsers()));
    }
    
    @GetMapping("/getUserInfo")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> getUserInfo(@RequestParam String userName) {    
    	try {
    		return ResponseEntity.ok(new ApiResponse("Retrieve User Information Success", authenticationService.getUserInfo(userName)));
    	} catch(Exception e) {
    		return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponse(e.getMessage(), null));
    	}
        
    }
    
    @PutMapping("/updateUser")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody User user) {
    	try {
    		return ResponseEntity.ok(new ApiResponse("User updated successfully", authenticationService.updateUser(user)));
    	} catch(Exception e) {
    		return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponse(e.getMessage(), null));
    	}
    }
    
    @DeleteMapping("/deleteUser")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> deleteUser(@RequestParam String userName) {
    	try {
    		System.out.println("delete user:: "+userName);
    		authenticationService.deleteUser(userName);
    		return ResponseEntity.ok(new ApiResponse("User deleted successfully", null));
    	} catch(Exception e) {
    		return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponse(e.getMessage(), null));
    	}
    }
    
    @GetMapping("/checkUserAvailable/{userName}")
    public ResponseEntity<ApiResponse> checkUserAvailable(@PathVariable String userName) {
    	return ResponseEntity.ok(new ApiResponse(authenticationService.checkUserNameAvailable(userName), null));
    }
    
    @GetMapping("/testJWTAuth")
    public ResponseEntity<ApiResponse> testJWTAuth() {
    	return ResponseEntity.ok(new ApiResponse("Successfully authenticate the JWT token.", null));
    }
    
}
