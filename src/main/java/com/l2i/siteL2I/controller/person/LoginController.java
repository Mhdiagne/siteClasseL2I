package com.l2i.siteL2I.controller.person;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.l2i.siteL2I.dto.person.AccountCredentials;
import com.l2i.siteL2I.model.person.Professor;
import com.l2i.siteL2I.model.person.User;
import com.l2i.siteL2I.service.person.ProfessorService;
import com.l2i.siteL2I.service.person.StudentService;;

@RestController
public class LoginController {
	@Autowired
	private JwtService jwtService;
	@Autowired
	// UserService uService;
	StudentService studentService;
	@Autowired
	ProfessorService professorService;
	@Autowired
	AuthenticationManager authenticationManager;

	@PostMapping("/login")
	public ResponseEntity<?> getToken(@RequestBody AccountCredentials credentials) {
		UsernamePasswordAuthenticationToken creds = new UsernamePasswordAuthenticationToken(
				credentials.getUsername(),
				credentials.getPassword());

		Authentication auth = authenticationManager.authenticate(creds);

		// Get user role
		// User user = uService.findByUsername(auth.getName());

		// Professor estProfConnected = professorService.getProfByMail(auth.getName());
		// String role = "professor";
		// if (estProfConnected==null) {
		// Student estStudentConnected =
		// studentService.getStudentByMail(auth.getName());
		// role = "student";
		// }
		User estProfConnected = null;
		Optional<Professor> whoIs = professorService.getProfByMail(auth.getName());
		if (whoIs.isPresent()) {
			estProfConnected = whoIs.get();
		} else {
			estProfConnected = studentService.getStudentByMail(auth.getName());
			// role = "student";
		}

		String role = estProfConnected.getRole();

		// Generate token
		String jwts = jwtService.getToken(auth.getName(), role);

		// Crée une map pour contenir le jeton JWT et d'autres informations utilisateur
		Map<String, Object> response = new HashMap<>();
		response.put("token", "Bearer " + jwts); // Ajoute le jeton JWT
		// response.put("id", estProfConnected.getId()); // Ajoute l'ID de l'utilisateur
		// response.put("prenom", estProfConnected.getFirstName()); // Ajoute le prénom
		// de l'utilisateur
		// response.put("nom", estProfConnected.getName()); // Ajoute le nom de
		// l'utilisateur
		response.put("user", estProfConnected); // Ajoute des infos de l'utilisateur
		// Ajoute les en-têtes HTTP
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwts); // Ajoute le jeton JWT dans les en-têtes
		headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization"); // Expose le jeton JWT dans les
																					// en-têtes

		// Build response with the generated token

		// Renvoie une réponse HTTP contenant le jeton JWT et d'autres informations
		// utilisateur
		return ResponseEntity.ok()
				.headers(headers)
				.body(response);
	}

}
