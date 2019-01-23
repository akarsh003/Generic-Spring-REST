package com.ndg.springdemo.user;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ndg.springdemo.security.IAuthenticationFacade;

@CrossOrigin()
@RestController
@RequestMapping(UserController.URI)
public class UserController {


	@Autowired
	private IAuthenticationFacade authenticationFacade;

	static final String URI = "/user";

	@RequestMapping(value = "/getcurrentuserinfo", method = RequestMethod.GET)
	protected ResponseEntity<CurrentUserModel> get() {
		Authentication authentication = authenticationFacade.getAuthentication();
		CurrentUserModel model = new CurrentUserModel(authentication.getName(),
				authentication.getAuthorities().stream().map(arg0->arg0.getAuthority()).collect(Collectors.toList())
				);
		
		return new ResponseEntity<>(model, HttpStatus.OK);
	}

}
