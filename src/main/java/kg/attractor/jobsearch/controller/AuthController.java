package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.UserWithAvatarFileDto;
import kg.attractor.jobsearch.errors.UserNotFoundException;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.service.VacancyService;
import kg.attractor.jobsearch.util.MvcControllersUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

@Slf4j
@Controller
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final ResumeService resumeService;
    private final VacancyService vacancyService;
    private final MessageSource messageSource;

    @ModelAttribute
    public void addAttributes(Model model,
                              CsrfToken csrfToken,
                              @SessionAttribute(name = "currentLocale", required = false) Locale locale
    ) {


        ResourceBundle bundle = MvcControllersUtil.getResourceBundleSetLocaleSetProperties(model, locale);
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model, CsrfToken csrfToken) {
        model.addAttribute("userDto", new UserWithAvatarFileDto());
        model.addAttribute("_csrf", csrfToken);
        return "auth/register";
    }

    @PostMapping("register")
    public String registerUser(@Valid @ModelAttribute("userDto") UserWithAvatarFileDto userDto,
                               BindingResult bindingResult,
                               Model model, Locale locale) {
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
            model.addAttribute("successMessage", messageSource.getMessage("registration.success", null, locale));
        } catch (IOException | UserNotFoundException e) {
            model.addAttribute("errorMessage", messageSource.getMessage("error.upload.avatar", null, locale));
            log.error(e.getMessage());
            return "auth/register";
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage());
            bindingResult.rejectValue("email", "error.userDto",
                    messageSource.getMessage("error.userExists", new Object[]{userDto.getEmail()}, locale));
            model.addAttribute("bindingResult", bindingResult);
            model.addAttribute("userDto", userDto);
            return "auth/register";
        } catch (Exception e) {
            log.error(e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
            return "auth/register";
        }

        return "redirect:/";
    }

    @GetMapping("/forgot_password")
    public String showForgotPasswordForm() {
        return "auth/forgot_password_form";
    }

    @PostMapping("/forgot_password")
    public String processForgotPassword(@RequestParam("email") String email, Model model, Locale locale) {
        try {
            userService.sendPasswordResetToken(email);
            model.addAttribute("message", messageSource.getMessage("password.reset.link.sent", null, locale));
        } catch (Exception e) {
            model.addAttribute("error", messageSource.getMessage("error.sending.reset.link", null, locale));
        }
        return "auth/forgot_password_form";
    }

    @GetMapping("/reset_password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);
        return "auth/reset_password_form";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(@RequestParam("token") String token,
                                       @RequestParam("password") String password,
                                       Model model, Locale locale) {
        try {
            userService.resetPassword(token, password);
            model.addAttribute("message", messageSource.getMessage("password.reset.success", null, locale));
        } catch (Exception e) {
            model.addAttribute("error", messageSource.getMessage("error.resetting.password", null, locale));
        }
        return "auth/reset_password_form";
    }
}