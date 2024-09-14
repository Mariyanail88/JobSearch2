package kg.attractor.jobsearch.controller;


import kg.attractor.jobsearch.errors.ErrorResponseBody;
import kg.attractor.jobsearch.service.ErrorService;
import kg.attractor.jobsearch.util.MvcControllersUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Locale;
import java.util.ResourceBundle;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CustomErrorController implements ErrorController {
    private final ErrorService errorService;
    @ModelAttribute
    public void addAttributes(Model model,
                              CsrfToken csrfToken,
                              @SessionAttribute(name = "currentLocale", required = false) Locale locale
    ) {
//        model.addAttribute("_csrf", csrfToken);

        ResourceBundle bundle = MvcControllersUtil.getResourceBundleSetLocaleSetProperties(model, locale);
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        String errorMessage = (String) request.getAttribute("javax.servlet.error.message");

        if (statusCode == null) {
            statusCode = 500;
        }

        if (errorMessage == null) {
            errorMessage = "An unexpected error occurred";
        }

        log.error("Error with status code {} and message {}", statusCode, errorMessage);

        ErrorResponseBody errorResponse = errorService.makeResponse(new Exception(errorMessage));

        model.addAttribute("statusCode", statusCode);
        model.addAttribute("errorMessage", errorResponse.getError());
        model.addAttribute("reasons", errorResponse.getReasons());

        return "errors/error";
    }

}