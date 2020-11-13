package com.urcodebin.views.account;

import com.urcodebin.helpers.PageRouter;
import com.urcodebin.views.main.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "login", layout = MainView.class)
@PageTitle("Account Login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private LoginForm userLoginForm = new LoginForm();
    private Button registerButton = new Button("Create An Account");

    private LoginView() {
        setSizeFull();
        setHorizontalComponentAlignment(Alignment.CENTER, userLoginForm, registerButton);
        userLoginForm.setForgotPasswordButtonVisible(false);
        userLoginForm.setAction("login");

        registerButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        registerButton.setMinWidth("400px");
        registerButton.addClickListener(e -> {
            PageRouter.routeToPage(RegisterView.class);
        });
        add(userLoginForm, registerButton);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // inform the user about an authentication error
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            userLoginForm.setError(true);
        }
    }
}
