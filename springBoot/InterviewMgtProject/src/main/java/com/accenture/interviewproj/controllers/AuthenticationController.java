package com.accenture.interviewproj.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.interviewproj.dtos.EmployeeLoginDTO;
import com.accenture.interviewproj.entities.Employee;
import com.accenture.interviewproj.exceptions.IdNotFoundException;
import com.accenture.interviewproj.security.AuthenticationToken;
import com.accenture.interviewproj.security.JwtService;
import com.accenture.interviewproj.services.EmployeeService;

@CrossOrigin("*")
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
    private JwtService jwtTokenUtil;

    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private BCryptPasswordEncoder bcryptPasswordEncoder;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                		employeeLoginDTO.getEmployeeId(),
                		employeeLoginDTO.getEmployeePassword()
                )
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        return ResponseEntity.ok(new AuthenticationToken(token));
    }
    
    @PostMapping("/token")
    public ResponseEntity<?> getRole(@RequestBody String token){
    	Employee employee;
		try {
			employee = employeeService.findById(jwtTokenUtil.getUsernameFromToken(token));
			return ResponseEntity.ok(employee);
		} catch (IdNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Token");
		}
    	
    }
}
