package com.l2i.siteL2I.service.person;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.l2i.siteL2I.model.person.User;
import com.l2i.siteL2I.repository.person.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository repository;
	@Autowired
	// UserService uService;
	StudentService studentService;
	@Autowired
	ProfessorService professorService;

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

	// // Récupère les informations de l'utilisateur actuellement authentifié
	public User getUserContext() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.isAuthenticated()) {
			String username = null;
			String role = null;

			if (authentication.getPrincipal() instanceof UserDetails) {
				UserDetails userDetails = (UserDetails) authentication.getPrincipal();
				username = userDetails.getUsername();
				role = userDetails.getAuthorities().stream()
						.findFirst()
						.map(authority -> authority.getAuthority())
						.orElse(null);
			} else if (authentication.getPrincipal() instanceof String) {
				// Si l'utilisateur est authentifié avec un token JWT contenant juste le
				// nomd'utilisateur
				username = (String) authentication.getPrincipal();
			}

			// return new UserContext(username, role);
			return repository.findByEmail(username).get();
		}

		return null;
	}

}
