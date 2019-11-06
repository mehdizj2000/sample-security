package au.com.jaycar.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import au.com.jaycar.business.TokenBusiness;
import au.com.jaycar.business.UserBusiness;
import au.com.jaycar.business.impl.ResetPasswordTokenBusinessImpl;
import au.com.jaycar.domain.ResetPasswordToken;
import au.com.jaycar.domain.VerificationToken;
import au.com.jaycar.dto.UserDetailsDto;
import au.com.jaycar.exception.GeneralBusinessException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/users")
public class UserController {

	private UserBusiness userBusiness;

	private TokenBusiness<VerificationToken> verificationTokenBusiness;
	
	private TokenBusiness<ResetPasswordToken> resetPasswordTokenBusiness;

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
		UserDetailsDto detailsDto = getUserBusiness().saveUser(user);

		return "redirect:/users/" + detailsDto.getId();
	}

	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable Long id) {
		getUserBusiness().deleteUser(id);
		return "redirect:/users/list";
	}

	public UserBusiness getUserBusiness() {
		return userBusiness;
	}

	public TokenBusiness<VerificationToken> getVerificationTokenBusiness() {
		return verificationTokenBusiness;
	}

	@GetMapping("/list")
	public String listUsers(Model model) {
		List<UserDetailsDto> detailsDtos = getUserBusiness().listAllUsers();
		log.info("retreived users: {}", detailsDtos);
		model.addAttribute("users", detailsDtos);
		return "users/list";
	}
	
	@GetMapping("/modify/{id}")
	public String modifyUser(@PathVariable Long id, Model model) {
		UserDetailsDto detailsDto = getUserBusiness().findUser(id);
		model.addAttribute("user", detailsDto);
		return "users/update-user";
	}
	
	@PostMapping("/modify/request/{id}")
	public String requestModifyUser(@PathVariable Long id, @RequestParam String email, Model model) {
		UserDetailsDto detailsDto = userBusiness.updateUserReq(id, email);
		model.addAttribute("user", detailsDto);
		
		return "users/update-user";
	}

	@GetMapping("/new")
	public String newUser(@ModelAttribute(value = "user") final UserDetailsDto user) {
		return "users/userform";
	}

	@GetMapping("/registration/confirm/{token}")
	public String register(@PathVariable String token) {
		UserDetailsDto detailsDto = getVerificationTokenBusiness().verifyToken(token);
		return "redirect:/users/" + detailsDto.getId();
	}

	@GetMapping("/save")
	public String save(@ModelAttribute(value = "user") final UserDetailsDto user) {
		return "users/userform";
	}

	@Autowired
	public void setUserBusiness(UserBusiness userBusiness) {
		this.userBusiness = userBusiness;
	}

	@Autowired
	public void setVerificationTokenBusiness(TokenBusiness<VerificationToken> verificationTokenBusiness) {
		this.verificationTokenBusiness = verificationTokenBusiness;
	}

	@GetMapping("/{id}")
	public String userDetails(@PathVariable(name = "id") Long id, Model model) {
		UserDetailsDto detailsDto = getUserBusiness().findUser(id);
		model.addAttribute("user", detailsDto);
		return "users/view";
	}

	public TokenBusiness<ResetPasswordToken> getResetPasswordTokenBusiness() {
		return resetPasswordTokenBusiness;
	}

	@Autowired
	public void setResetPasswordTokenBusiness(TokenBusiness<ResetPasswordToken> resetPasswordTokenBusiness) {
		this.resetPasswordTokenBusiness = resetPasswordTokenBusiness;
	}

}
