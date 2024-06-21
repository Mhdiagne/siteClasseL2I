package com.l2i.siteL2I.service.person;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.l2i.siteL2I.model.person.User;
import com.l2i.siteL2I.repository.person.UserRepository;




@Service
public class UserDetailsServiceImpl implements UserDetailsService  {
	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
		Optional<User> user = repository.findByEmail(mail); 

		UserBuilder builder = null;
		if (user.isPresent()) {
			User currentUser = user.get();
			builder = org.springframework.security.core.userdetails.User.withUsername(mail);
			builder.password(currentUser.getPassword());
			builder.roles(currentUser.getRole());
		} else {
			throw new UsernameNotFoundException("User not found.");
		}

		return builder.build();	    
	}
}
