package com.urcodebin.helpers;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.HasUrlParameter;


public class PageRouter {

    public static <T, C extends Component & HasUrlParameter<T>> void routeToPage(Class<? extends C> page, T param) {
        UI currentUI = UI.getCurrent();
        currentUI.access(() -> {
            currentUI.getUI().ifPresent(ui -> ui.navigate(page, param));
        });
    }

    public static void routeToPage(Class<? extends Component> page) {
        UI currentUI = UI.getCurrent();
        currentUI.access(() -> {
            currentUI.getUI().ifPresent(ui -> ui.navigate(page));
        });
    }
}
