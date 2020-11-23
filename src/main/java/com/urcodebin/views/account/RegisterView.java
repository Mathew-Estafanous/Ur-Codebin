package com.urcodebin.views.account;

import com.urcodebin.backend.entity.UserAccount;
import com.urcodebin.backend.interfaces.UserAccountService;
import com.urcodebin.helpers.PageRouter;
import com.urcodebin.views.main.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Route(value = "register", layout = MainView.class)
@PageTitle("Account Registration")
public class RegisterView extends VerticalLayout {

    private final H3 registrationTitle = new H3("Register For An Account");
    private final Hr divider = new Hr();
    private final TextField username = new TextField("Username");
    private final PasswordField mainPassword = new PasswordField("Password");
    private final PasswordField matchPassword = new PasswordField("Same Password");
    private final EmailField emailField = new EmailField("Email");

    private final Button registerButton;
    private final Span errorMessage;

    private Binder<UserAccount> binder;

    private boolean enablePasswordValidation;

    private final UserAccountService userAccountService;

    @Autowired
    private RegisterView(@Qualifier("AccountService") UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
        emailField.setVisible(true);

        errorMessage = new Span();
        registerButton = new Button("Register");
        registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        FormLayout registerFormLayout = createRegisterFormLayout();
        add(registerFormLayout);

        createBinderForRegisterForm();
        matchPassword.addValueChangeListener(e -> {
            enablePasswordValidation = true;
            binder.validate();
        });

        emailField.addValueChangeListener(e -> binder.validate());

        registerButton.addClickListener(e -> {
            UserAccount acccountRegister = new UserAccount();
            try {
                binder.writeBean(acccountRegister);

                registerUser(acccountRegister);

            } catch (ValidationException validationException) {
                validationException.printStackTrace();
            }
        });
    }

    private void createBinderForRegisterForm() {
        binder = new Binder<>(UserAccount.class);
        binder.forField(username).asRequired()
                .withValidator(this::usernameValidator)
                .bind("username");
        binder.forField(mainPassword).asRequired()
                .withValidator(this::passwordValidator)
                .bind("password");
        binder.forField(emailField).asRequired()
                .withValidator(this::emailValidator)
                .bind("email");
        binder.setStatusLabel(errorMessage);
    }

    private FormLayout createRegisterFormLayout() {
        FormLayout registerLayout = new FormLayout(registrationTitle, divider, username, emailField,
                mainPassword, matchPassword, errorMessage, registerButton);
        registerLayout.setMaxWidth("750px");
        registerLayout.getStyle().set("margin", "0 auto");

        setResponsiveSteps(registerLayout);
        setColumnExceptionsForComponents(registerLayout);

        errorMessage.getStyle().set("color", "var(--lumo-error-text-color)");
        errorMessage.getStyle().set("padding", "15px 0");

        return registerLayout;
    }

    private void setResponsiveSteps(FormLayout registerLayout) {
        registerLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("600px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP));
    }

    private void setColumnExceptionsForComponents(FormLayout registerLayout) {
        registerLayout.setColspan(registrationTitle, 2);
        registerLayout.setColspan(divider, 2);
        registerLayout.setColspan(errorMessage, 2);
        registerLayout.setColspan(registerButton, 2);
    }

    private void registerUser(UserAccount newUser) {
        userAccountService.addUserAccount(newUser);
        successfulRegistration();
    }

    private void successfulRegistration() {
        String successMessage = "Awesome your registration is complete! You are now a member of Ur-Codebin.";
        Notification notification = Notification.show(successMessage);
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        PageRouter.routeToPage(LoginView.class);
    }

    private ValidationResult usernameValidator(String username, ValueContext valueContext) {
        boolean usernameInUse = userAccountService.isUsernameTaken(username);
        if(usernameInUse) {
            return ValidationResult.error("Username is already taken.");
        }
        return ValidationResult.ok();
    }

    private ValidationResult emailValidator(String email, ValueContext valueContext) {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+↵\n" +
                ")*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        Pattern emailPattern = Pattern.compile(regex);

        Matcher matcher = emailPattern.matcher(email);
        if(matcher.matches()) {
            return ValidationResult.ok();
        }
        return ValidationResult.error("Invalid Email Address.");
    }

    private ValidationResult passwordValidator(String pass, ValueContext valueContext) {
        if(pass == null || pass.length() < 8) {
            return ValidationResult.error("Password should be at least 8 characters long");
        }

        if(!enablePasswordValidation) {
            return ValidationResult.ok();
        }

        String matchPass = matchPassword.getValue();
        if(pass.equals(matchPass)) {
            return ValidationResult.ok();
        }
        return ValidationResult.error("Passwords do not match!");
    }
}
