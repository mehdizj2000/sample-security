package au.com.jaycar.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

	@GetMapping("/{id}")
	public String userDetails(@PathVariable(name = "id") Long id, Model model) {
		UserDetailsDto detailsDto = userBusiness.findUser(id);
		model.addAttribute("user", detailsDto);
		return "users/view";
	}

	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable Long id) {
		userBusiness.deleteUser(id);
		return "redirect:/users/list";
	}

	@GetMapping("/modify/{id}")
	public String modifyUser(@PathVariable Long id, Model model) {
		UserDetailsDto detailsDto = userBusiness.findUser(id);
		model.addAttribute("user", detailsDto);
		return "users/userform";
	}

	@GetMapping("/new")
	public String newUser(Model model) {
		UserDetailsDto detailsDto = new UserDetailsDto();
		detailsDto.setId(-1l);
		model.addAttribute("user", detailsDto);
		return "users/userform";
	}

	@PostMapping("/save/{id}")
	public String createNewUser(@PathVariable Long id, UserDetailsDto user) {
		if (id != -1) {
			userBusiness.updateUser(id, user);
		} else {
			user.setId(null);
			userBusiness.saveNewUser(user);
		}
		return "redirect:/users/list";
	}

}
