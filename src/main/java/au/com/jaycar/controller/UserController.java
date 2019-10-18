package au.com.jaycar.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import au.com.jaycar.business.UserBusiness;
import au.com.jaycar.dto.UserDetailsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/users")
public class UserController {
	
	private final UserBusiness userBusiness;

	@GetMapping("/list")
	public String listUsers(Model model) {
		
		List<UserDetailsDto> detailsDtos = userBusiness.listAllUsers();
		
		log.info("retreived users: {}", detailsDtos);
		
		model.addAttribute("users", detailsDtos);

		return "users/list";
	}
	
	
}
