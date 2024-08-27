package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.UserWithAvatarFileDto;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.errors.UserNotFoundException;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final ResumeService resumeService;
    private final VacancyService vacancyService;

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDto", new UserWithAvatarFileDto());
        return "auth/register";
    }

    @PostMapping("register")
    public String registerUser(@Valid @ModelAttribute("userDto") UserWithAvatarFileDto userDto,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            log.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
            model.addAttribute("bindingResult", bindingResult);
            model.addAttribute("userDto", userDto);
            return "auth/register";
        }

        try {
            MultipartFile avatar = userDto.getAvatar();
            if (avatar != null) {
                log.info("Received avatar: name={}, size={}, originalFilename={}",
                        avatar.getName(), avatar.getSize(), avatar.getOriginalFilename());
                if (avatar.getOriginalFilename() == null || avatar.getOriginalFilename().isEmpty()) {
                    log.info("Received empty avatar, default avatar will be used");
                }
            } else {
                log.warn("Avatar file is null!");
            }
            userDto.setEnabled(true);
            userService.addUserWithAvatar(userDto);

            log.info("User authenticated successfully: {}", userDto.getEmail());
        } catch (IOException | UserNotFoundException e) {
            model.addAttribute("errorMessage", "Error uploading avatar. Please try again.");
            log.error(e.getMessage());
            return "auth/register";
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage());
            bindingResult.rejectValue("email", "error.userDto", String.format("User with email %s already exists.", userDto.getEmail()));
            model.addAttribute("bindingResult", bindingResult);
            model.addAttribute("userDto", userDto);


            return "auth/register";
        } catch (Exception e) {
            log.error(e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
            return "auth/register";
        }

        model.addAttribute("successMessage", "Registration successful! Redirecting to the main page...");
        return "redirect:/";
    }

    @GetMapping("profile")
    public String profile(Model model, Principal principal, Authentication authentication) throws IOException, UserNotFoundException {
        Boolean ifEntityUpdated = (Boolean) model.asMap().get("ifEntityUpdated");
        log.info("ifEntityUpdated: {}", ifEntityUpdated);
        if (ifEntityUpdated != null) {
            String entityTitle = (String) model.asMap().get("entityTitle");
            String entityName = (String) model.asMap().get("entityName");

            model.addAttribute("entityUpdated", ifEntityUpdated);
            model.addAttribute("entityTitle", entityTitle);
            model.addAttribute("entityName", entityName);
        }

        if (principal == null) {
            log.error("Principal is null. User is not authenticated.");
            return "redirect:/auth/login";
        }
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        model.addAttribute("isAuthenticated", isAuthenticated);
        if (isAuthenticated && authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            model.addAttribute("username", username);
        }
        log.info("isAuthenticated: {}", isAuthenticated);
        log.info("Fetching profile for user: {}", principal.getName());

        UserDto userDto = userService.getUserByEmail(principal.getName());
        List<ResumeDto> resumes = resumeService.getResumeByUserId(userDto.getId());
        List<VacancyDto> vacancies = vacancyService.getVacancyByAuthorId(userDto.getId());
        model.addAttribute("userVacancies", vacancies);
        model.addAttribute("userResumes", resumes);
        model.addAttribute("userDto", userDto);
        return "auth/profile";
    }

}