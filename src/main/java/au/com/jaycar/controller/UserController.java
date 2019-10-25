package au.com.jaycar.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String newUser(@ModelAttribute(value = "user") final UserDetailsDto user) {
		return "users/userform";
	}
	
	@GetMapping("/save")
	public String save(@ModelAttribute(value = "user") final UserDetailsDto user) {
		return "users/userform";
	}

	@PostMapping("/save")
	public String createNewUser(@ModelAttribute(value = "user") @Valid UserDetailsDto user, BindingResult result,
			RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			return "users/userform";
		}

		if (user.getId() == null) {
			redirectAttributes.addFlashAttribute("globalMessage", "Successfully created a new user");
		} else {
			redirectAttributes.addFlashAttribute("globalMessage", "Successfully updated a new user");
		}
		UserDetailsDto detailsDto = userBusiness.saveUser(user);

		return "redirect:/users/" + detailsDto.getId();
	}

}
