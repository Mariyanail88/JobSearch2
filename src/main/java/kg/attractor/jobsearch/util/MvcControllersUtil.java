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

    public static ResourceBundle getResourceBundleSetLocaleSetProperties(Model model, Locale locale) {
        if (locale == null) {
            locale = Locale.ENGLISH;
        }

        ResourceBundle bundle = ResourceBundle.getBundle("resource", locale);
        setPropertiesForLayout(model, bundle);
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

        // Adding additional properties from resource_ru.properties
        model.addAttribute("greeting", bundle.getString("greeting"));
        model.addAttribute("indexGreeting", bundle.getString("index.greeting"));
        model.addAttribute("indexVacancies", bundle.getString("index.vacancies"));
        model.addAttribute("indexResumes", bundle.getString("index.resumes"));
        model.addAttribute("indexVacanciesAndResponses", bundle.getString("index.vacancies.and.responses"));

        model.addAttribute("registerTitle", bundle.getString("register.title"));
        model.addAttribute("registerName", bundle.getString("register.name"));
        model.addAttribute("registerAge", bundle.getString("register.age"));
        model.addAttribute("registerEmail", bundle.getString("register.email"));
        model.addAttribute("registerPassword", bundle.getString("register.password"));
        model.addAttribute("registerPhoneNumber", bundle.getString("register.phoneNumber"));
        model.addAttribute("registerAccountType", bundle.getString("register.accountType"));
        model.addAttribute("registerAccountTypeApplicant", bundle.getString("register.accountType.applicant"));
        model.addAttribute("registerAccountTypeEmployer", bundle.getString("register.accountType.employer"));
        model.addAttribute("registerAvatar", bundle.getString("register.avatar"));
        model.addAttribute("registerSubmit", bundle.getString("register.submit"));
        model.addAttribute("registerErrorMessage", bundle.getString("register.errorMessage"));

        model.addAttribute("editProfileTitle", bundle.getString("editProfile.title"));
        model.addAttribute("editProfileName", bundle.getString("editProfile.name"));
        model.addAttribute("editProfileAge", bundle.getString("editProfile.age"));
        model.addAttribute("editProfileEmail", bundle.getString("editProfile.email"));
        model.addAttribute("editProfilePassword", bundle.getString("editProfile.password"));
        model.addAttribute("editProfilePhone", bundle.getString("editProfile.phone"));
        model.addAttribute("editProfileAvatar", bundle.getString("editProfile.avatar"));
        model.addAttribute("editProfileAccountType", bundle.getString("editProfile.accountType"));
        model.addAttribute("editProfileAccountTypeEmployer", bundle.getString("editProfile.accountType.employer"));
        model.addAttribute("editProfileAccountTypeApplicant", bundle.getString("editProfile.accountType.applicant"));
        model.addAttribute("editProfileSubmit", bundle.getString("editProfile.submit"));

        model.addAttribute("forgotPasswordTitle", bundle.getString("forgotPassword.title"));
        model.addAttribute("forgotPasswordInstruction", bundle.getString("forgotPassword.instruction"));
        model.addAttribute("forgotPasswordEmailPlaceholder", bundle.getString("forgotPassword.emailPlaceholder"));
        model.addAttribute("forgotPasswordSubmit", bundle.getString("forgotPassword.submit"));

        model.addAttribute("profileUpdated", bundle.getString("profile.updated"));
        model.addAttribute("profileYes", bundle.getString("profile.yes"));
        model.addAttribute("profileNo", bundle.getString("profile.no"));
        model.addAttribute("profileCompanyName", bundle.getString("profile.companyName"));
        model.addAttribute("profileEdit", bundle.getString("profile.edit"));
        model.addAttribute("profileAge", bundle.getString("profile.age"));
        model.addAttribute("profileCreateVacancy", bundle.getString("profile.createVacancy"));
        model.addAttribute("profileCreateResume", bundle.getString("profile.createResume"));
        model.addAttribute("profileResponsesToVacancies", bundle.getString("profile.responsesToVacancies"));
        model.addAttribute("profileVacancies", bundle.getString("profile.vacancies"));
        model.addAttribute("profileResumes", bundle.getString("profile.resumes"));
        model.addAttribute("profileNoVacancies", bundle.getString("profile.noVacancies"));
        model.addAttribute("profileNoResumes", bundle.getString("profile.noResumes"));
        model.addAttribute("profileUpdatedDate", bundle.getString("profile.updatedDate"));
        model.addAttribute("profileUpdate", bundle.getString("profile.update"));
        model.addAttribute("profileHome", bundle.getString("profile.home"));
        model.addAttribute("profileEmployerDashboard", bundle.getString("profile.employerDashboard"));
        model.addAttribute("profileApplicantDashboard", bundle.getString("profile.applicantDashboard"));
        model.addAttribute("profileSuccessfullyUpdated", bundle.getString("profile.successfullyUpdated"));

        model.addAttribute("profileEditProfile", bundle.getString("profile.editProfile"));
        model.addAttribute("profileFixErrors", bundle.getString("profile.fixErrors"));
        model.addAttribute("profileFieldName", bundle.getString("profile.fieldName"));
        model.addAttribute("profileErrorDescription", bundle.getString("profile.errorDescription"));
        model.addAttribute("profileName", bundle.getString("profile.name"));
        model.addAttribute("profileEmail", bundle.getString("profile.email"));
        model.addAttribute("profilePhoneNumber", bundle.getString("profile.phoneNumber"));
        model.addAttribute("profileAccountType", bundle.getString("profile.accountType"));
        model.addAttribute("profileApplicant", bundle.getString("profile.applicant"));
        model.addAttribute("profileEmployer", bundle.getString("profile.employer"));
        model.addAttribute("profileAvatar", bundle.getString("profile.avatar"));
        model.addAttribute("profileSaveChanges", bundle.getString("profile.saveChanges"));

        model.addAttribute("resumeCreateTitle", bundle.getString("resume.createTitle"));
        model.addAttribute("resumeName", bundle.getString("resume.name"));
        model.addAttribute("resumeCategory", bundle.getString("resume.category"));
        model.addAttribute("resumeSalary", bundle.getString("resume.salary"));
        model.addAttribute("resumeIsActive", bundle.getString("resume.isActive"));
        model.addAttribute("resumeActiveYes", bundle.getString("resume.activeYes"));
        model.addAttribute("resumeActiveNo", bundle.getString("resume.activeNo"));
        model.addAttribute("resumeCreateButton", bundle.getString("resume.createButton"));

        model.addAttribute("resumeEditTitle", bundle.getString("resume.editTitle"));
        model.addAttribute("resumeApplicantId", bundle.getString("resume.applicantId"));
        model.addAttribute("resumeSaveButton", bundle.getString("resume.saveButton"));

        model.addAttribute("resumeAuthor", bundle.getString("resume.author"));
        model.addAttribute("resumeCreatedDate", bundle.getString("resume.createdDate"));
        model.addAttribute("resumeUpdatedDate", bundle.getString("resume.updatedDate"));

        model.addAttribute("resumeTitle", bundle.getString("resume.title"));
        model.addAttribute("resumeCount", bundle.getString("resume.count"));
        model.addAttribute("resumeLoggedInAs", bundle.getString("resume.loggedInAs"));
        model.addAttribute("resumeNotLoggedIn", bundle.getString("resume.notLoggedIn"));
        model.addAttribute("resumeDetails", bundle.getString("resume.details"));

        model.addAttribute("companyInfoTitle", bundle.getString("company.info.title"));
        model.addAttribute("companyRepresentative", bundle.getString("company.representative"));
        model.addAttribute("companyContactPhone", bundle.getString("company.contactPhone"));
        model.addAttribute("companyEmail", bundle.getString("company.email"));
        model.addAttribute("companyActiveVacancies", bundle.getString("company.activeVacancies"));
        model.addAttribute("companyNoVacancies", bundle.getString("company.noVacancies"));
        model.addAttribute("companyAllVacancies", bundle.getString("company.allVacancies"));

        model.addAttribute("vacancyDescription", bundle.getString("vacancy.description"));
        model.addAttribute("vacancyCategory", bundle.getString("vacancy.category"));
        model.addAttribute("vacancySalary", bundle.getString("vacancy.salary"));
        model.addAttribute("vacancyExperience", bundle.getString("vacancy.experience"));
        model.addAttribute("vacancyExperienceFromTo", bundle.getString("vacancy.experience.fromTo"));
        model.addAttribute("vacancyIsActive", bundle.getString("vacancy.isActive"));
        model.addAttribute("vacancyActiveYes", bundle.getString("vacancy.activeYes"));
        model.addAttribute("vacancyActiveNo", bundle.getString("vacancy.activeNo"));
        model.addAttribute("vacancyCreatedDate", bundle.getString("vacancy.createdDate"));
        model.addAttribute("vacancyUpdatedDate", bundle.getString("vacancy.updatedDate"));
        model.addAttribute("vacancyDetails", bundle.getString("vacancy.details"));

        model.addAttribute("employerCreateTitle", bundle.getString("employer.create.title"));
        model.addAttribute("employerCreateCompanyName", bundle.getString("employer.create.companyName"));
        model.addAttribute("employerCreateContactPerson", bundle.getString("employer.create.contactPerson"));
        model.addAttribute("employerCreateEmail", bundle.getString("employer.create.email"));
        model.addAttribute("employerCreatePhone", bundle.getString("employer.create.phone"));
        model.addAttribute("employerCreateIsActive", bundle.getString("employer.create.isActive"));
        model.addAttribute("employerCreateActiveYes", bundle.getString("employer.create.activeYes"));
        model.addAttribute("employerCreateActiveNo", bundle.getString("employer.create.activeNo"));
        model.addAttribute("employerCreateSubmit", bundle.getString("employer.create.submit"));

        model.addAttribute("vacancyEditTitle", bundle.getString("vacancy.edit.title"));
        model.addAttribute("vacancyEditName", bundle.getString("vacancy.edit.name"));
        model.addAttribute("vacancyEditDescription", bundle.getString("vacancy.edit.description"));
        model.addAttribute("vacancyEditCategoryId", bundle.getString("vacancy.edit.categoryId"));
        model.addAttribute("vacancyEditSalary", bundle.getString("vacancy.edit.salary"));
        model.addAttribute("vacancyEditExpFrom", bundle.getString("vacancy.edit.expFrom"));
        model.addAttribute("vacancyEditExpTo", bundle.getString("vacancy.edit.expTo"));
        model.addAttribute("vacancyEditIsActive", bundle.getString("vacancy.edit.isActive"));
        model.addAttribute("vacancyEditActiveYes", bundle.getString("vacancy.edit.activeYes"));
        model.addAttribute("vacancyEditActiveNo", bundle.getString("vacancy.edit.activeNo"));
        model.addAttribute("vacancyEditAuthorId", bundle.getString("vacancy.edit.authorId"));
        model.addAttribute("vacancyEditSubmit", bundle.getString("vacancy.edit.submit"));

        model.addAttribute("vacanciesTitle", bundle.getString("vacancies.title"));

        model.addAttribute("buttonClose", bundle.getString("button.close"));
    }
}