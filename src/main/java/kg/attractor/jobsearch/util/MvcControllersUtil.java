package kg.attractor.jobsearch.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@Slf4j
public class MvcControllersUtil {
    private MvcControllersUtil() {
    }

    public static void authCheckAndAddAttributes(
            Model model,
            Authentication authentication,
            List<?> collection,
            String placeHolder) {
        model.addAttribute(placeHolder, collection);
        authCheck(model, authentication);
    }

    public static <T> void authCheckAndAddAttributes(
            Model model,
            Authentication authentication,
            T dto,
            String placeHolder
    ) {
        model.addAttribute(placeHolder, dto);
        authCheck(model, authentication);
    }

    public static void authCheck(Model model, Authentication authentication) {
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        model.addAttribute("isAuthenticated", isAuthenticated);
        if (isAuthenticated && authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            model.addAttribute("username", username);
        }
    }
    // todo - add to context?
    public static ResourceBundle getResourceBundleSetLocaleSetProperties(Model model, Locale locale) {


        if (locale == null) {
            locale = Locale.ENGLISH;
        }

        ResourceBundle bundle = ResourceBundle.getBundle("resource", locale);

        MvcControllersUtil.setPropertiesForLayout(model, bundle);
        return bundle;
    }

    public static void setPropertiesForLayout(Model model, ResourceBundle bundle) {
        model.addAttribute("home", bundle.getString("button.home"));
        model.addAttribute("create", bundle.getString("layout.create"));
        model.addAttribute("profile", bundle.getString("layout.profile"));
        model.addAttribute("logout", bundle.getString("layout.logout"));
        model.addAttribute("login", bundle.getString("layout.login"));
        model.addAttribute("register", bundle.getString("layout.register"));
        model.addAttribute("loggedInMessage", bundle.getString("layout.logged.in"));
        model.addAttribute("roleMessage", bundle.getString("layout.roles"));
        model.addAttribute("notLoggedInMessage", bundle.getString("layout.not.logged.in"));
        model.addAttribute("availableActions", bundle.getString("layout.actions.available"));
        model.addAttribute("title", bundle.getString("layout.title"));
    }

}
