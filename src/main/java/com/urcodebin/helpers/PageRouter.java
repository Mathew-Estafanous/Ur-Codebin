package com.urcodebin.helpers;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PageRouter {

    private static final Logger LOGGER = LoggerFactory.getLogger(PageRouter.class);

    public static <T, C extends Component & HasUrlParameter<T>> void routeToPage(Class<? extends C> page, T param) {
        LOGGER.debug("Routing to {} with a parameter of {}.", page, param);
        UI currentUI = UI.getCurrent();
        currentUI.access(() -> {
            currentUI.getUI().ifPresent(ui -> ui.navigate(page, param));
        });
    }

    public static void routeToPage(Class<? extends Component> page) {
        LOGGER.debug("Routing to {} with no parameters", page);
        UI currentUI = UI.getCurrent();
        currentUI.access(() -> {
            currentUI.getUI().ifPresent(ui -> ui.navigate(page));
        });
    }

    public static <C extends Component> String getRouteToPage(Class<? extends C> pagePath, String parameter) {
        String startPath = ((VaadinServletRequest) VaadinService.getCurrentRequest()).getHttpServletRequest()
                .getRequestURL().toString();
        String pageViewUrl = RouteConfiguration.forSessionScope().getUrl(pagePath);
        return startPath.concat(pageViewUrl).concat("/").concat(parameter);
    }
}
