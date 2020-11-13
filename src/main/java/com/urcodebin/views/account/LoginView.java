package com.urcodebin.views.account;

import com.urcodebin.views.main.MainView;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "login", layout = MainView.class)
@PageTitle("Account Login")
public class LoginView extends VerticalLayout {

    private LoginForm userLoginForm = new LoginForm();

    private LoginView() {

        setSizeFull();
        setHorizontalComponentAlignment(Alignment.CENTER, userLoginForm);
        userLoginForm.setForgotPasswordButtonVisible(false);
        userLoginForm.addLoginListener(e -> {
            boolean isAuthenticated = true;
            if (isAuthenticated) {
                successfulLogin();
            } else {
                userLoginForm.setError(true);
            }
        });

        add(userLoginForm);
    }

    private void successfulLogin() {
        String successMessage = "Welcome to Ur-Codebin. Logged in!";
        Notification notification = Notification.show(successMessage);
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }
}
