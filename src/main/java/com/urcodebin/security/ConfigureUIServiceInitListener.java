package com.urcodebin.security;

import com.urcodebin.views.account.LoginView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.security.access.AccessDeniedException;

@SpringComponent
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {

    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().addUIInitListener(uiEvent -> {
            final UI ui = uiEvent.getUI();
            ui.addBeforeEnterListener(this::beforeEnter);
        });
    }

    /**
     * Reroutes the user if she is not authorized to access the view.
     *
     * @param event before navigation event with event details
     */
    private void beforeEnter(BeforeEnterEvent event) {
        final boolean accessGranted = SecurityUtils.isAccessGranted(event.getNavigationTarget());
        if(accessGranted) return;

        if (SecurityUtils.isUserLoggedIn()) {
            event.rerouteToError(AccessDeniedException.class);
            return;
        }
        event.rerouteTo(LoginView.class);
    }
}
