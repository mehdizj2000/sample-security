package au.com.jaycar.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

	@GetMapping("/{email}")
	public String userDetails(@PathVariable(name = "email") String email, Model model) {
		UserDetailsDto detailsDto = userBusiness.findUser(email);

		model.addAttribute("user", detailsDto);

		return "users/view";

	}

	@GetMapping("/delete/{email}")
	public String deleteUser(@PathVariable String email) {

		userBusiness.deleteUser(email);

		return "redirect:/users/list";
	}

	@GetMapping("/modify/{email}")
	public String modifyUser(@PathVariable String email, Model model) {
		UserDetailsDto detailsDto = userBusiness.findUser(email);
		
		model.addAttribute("user", detailsDto);
		return "users/update";
	}

	@PostMapping("/modify/{email}")
	public String createNewUser(@PathVariable String email, UserDetailsDto user) {
		userBusiness.updateUser(email, user);
		return "redirect:/users/list";
	}

}
