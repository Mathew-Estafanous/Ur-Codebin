package com.urcodebin.views.account;

import com.urcodebin.views.main.MainView;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;

@Route(value = "profile", layout = MainView.class)
@PageTitle("Your Profile")
@Secured("ROLE_USER")
public class ProfileView extends VerticalLayout {

    public ProfileView() {
        add(new H1("WELCOME TO YOUR PROFILE"));
    }
}
