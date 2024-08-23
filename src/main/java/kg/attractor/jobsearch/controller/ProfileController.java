package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.UserWithAvatarFileDto;
import kg.attractor.jobsearch.errors.UserNotFoundException;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.util.MvcConrollersUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@Slf4j
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final UserService userService;

    @GetMapping("/edit")
    public String showEditProfileForm(@RequestParam("id") long id, Model model, Authentication authentication) {
        try {

            UserDto userDto = userService.getUserById(id);
            String currentUsername = authentication.getName();
            if (!userDto.getEmail().equals(currentUsername)) {
                return "redirect:/access-denied";
            }
            model.addAttribute("userDto", userDto);
            MvcConrollersUtil.authCheck(model, authentication);
            return "auth/edit_profile";
        } catch (UserNotFoundException e) {
            log.error("User not found", e);
            return "redirect:/profile?error=user-not-found";
        } catch (Exception e) {
            log.error("Unexpected error", e);
            return "redirect:/error";
        }
    }

    @PostMapping("/edit")
    public String editProfile(@ModelAttribute("userDto")
                              UserWithAvatarFileDto userWithAvatarFileDto,
                              Authentication authentication,
                              BindingResult bindingResult,
                              Model model) {
        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("bindingResult", bindingResult);
                return "auth/edit_profile";
            }
            String currentUsername = authentication.getName();
            if (!userWithAvatarFileDto.getEmail().equals(currentUsername)) {
                return "redirect:/access-denied";
            }
            UserDto userDto = userService.getUserByEmail(authentication.getName());
            userWithAvatarFileDto.setAccountType(userDto.getAccountType());
            userService.updateUserProfile(userWithAvatarFileDto);
        } catch (IOException | UserNotFoundException e) {
            log.error("Error updating profile", e);
            return "redirect:/profile/edit?error";
        } catch (Exception e) {
            log.error("Unexpected error", e);
            return "redirect:/error";
        }
        return "redirect:/profile";
    }
}