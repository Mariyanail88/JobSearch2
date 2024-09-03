package kg.attractor.jobsearch.errors.handler;

import jakarta.servlet.http.HttpServletRequest;
import kg.attractor.jobsearch.errors.CategoryNotFoundException;
import kg.attractor.jobsearch.errors.ErrorResponseBody;
import kg.attractor.jobsearch.errors.UserNotFoundException;
import kg.attractor.jobsearch.errors.VacancyNotFoundException;
import kg.attractor.jobsearch.service.ErrorService;
import kg.attractor.jobsearch.util.ConsoleColors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.NoSuchElementException;
@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalMvcControllerAdvice {

    private final ErrorService errorService;
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletRequest request, Exception ex, Model model) {
        logging(request.getRequestURL().toString(), ex.getMessage());

        ErrorResponseBody errorResponse = errorService.makeResponse(ex);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("errorMessage", errorResponse.getError());
        modelAndView.addObject("reasons", errorResponse.getReasons());
        modelAndView.setViewName("errors/error");

        return modelAndView;
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String notFound(HttpServletRequest request, Model model) {
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reason", HttpStatus.NOT_FOUND.getReasonPhrase());
        model.addAttribute("details", request);
        return "errors/error";
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFound(HttpServletRequest request, Model model, UserNotFoundException ex) {
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reason", "User Not Found");
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("details", request);
        return "errors/error";
    }

    @ExceptionHandler(VacancyNotFoundException.class)
    public String handleVacancyNotFound(HttpServletRequest request, Model model, VacancyNotFoundException ex) {
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reason", "Vacancy Not Found");
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("details", request);
        return "errors/error";
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public String handleCategoryNotFound(HttpServletRequest request, Model model, CategoryNotFoundException ex) {
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reason", "Category Not Found");
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("details", request);
        return "errors/error";
    }private static void logging(String requestUrl, String exceptionMessage) {
        log.error(
                ConsoleColors.RED +
                        "Request: {} raised error(s) {}", requestUrl, exceptionMessage +
                        ConsoleColors.RESET
        );
    }
}