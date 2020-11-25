package com.urcodebin.views.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.urcodebin.security.SecurityUtils;
import com.urcodebin.views.account.LoginView;
import com.urcodebin.views.account.UserBinView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinServlet;
import com.vaadin.flow.theme.Theme;
import com.urcodebin.views.paste.PasteView;
import com.urcodebin.views.publicbins.PublicBinsView;
import com.vaadin.flow.theme.lumo.Lumo;

/**
 * The main view is a top-level placeholder for other views.
 */
@JsModule("./styles/shared-styles.js")
@CssImport(value = "./styles/views/main/main-view.css", themeFor = "vaadin-app-layout")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@CssImport("./styles/views/main/main-view.css")
@PWA(name = "Ur CodeBin", shortName = "Ur CodeBin", enableInstallPrompt = false)
public class MainView extends AppLayout {

    private final Tabs menu;

    private final static String PASTE_TITLE = "+ Paste";
    private final static String PUBLIC_BIN_TITLE = "Public Bins";
    private final static String PROFILE_TITLE = "My Paste Bin";
    private final static String LOGOUT_TITLE = "Logout";
    private final static String LOGIN_TITLE = "Login";

    private final static String LOGOUT_URL = "/logout";

    public MainView() {
        HorizontalLayout header = createHeader();
        menu = createMenuTabs();
        addToNavbar(createTopBar(header, menu));
    }

    private VerticalLayout createTopBar(HorizontalLayout header, Tabs menu) {
        VerticalLayout layout = new VerticalLayout();
        layout.getThemeList().add("dark");
        layout.setWidthFull();
        layout.setSpacing(false);
        layout.setPadding(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.add(header, menu);
        return layout;
    }

    private HorizontalLayout createHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.setPadding(false);
        header.setSpacing(false);
        header.setWidthFull();
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setId("header");
        Image logo = new Image("images/codebin.png", "Ur CodeBin logo");
        logo.setId("logo");
        header.add(logo);
        header.add(new H1("Ur CodeBin"));
        return header;
    }

    private static Tabs createMenuTabs() {
        final Tabs tabs = new Tabs();
        tabs.getStyle().set("max-width", "100%");
        tabs.add(getAvailableTabs());
        return tabs;
    }

    private static Tab[] getAvailableTabs() {
        final List<Tab> availableTabs = new ArrayList<>();
        availableTabs.add(createTab(PASTE_TITLE, VaadinIcon.EDIT, PasteView.class));
        availableTabs.add(createTab(PUBLIC_BIN_TITLE, VaadinIcon.BROWSER, PublicBinsView.class));

        if(SecurityUtils.isAccessGranted(UserBinView.class)) {
            availableTabs.add(createTab(PROFILE_TITLE, VaadinIcon.USER, UserBinView.class));

            final String contextPath = VaadinServlet.getCurrent().getServletContext().getContextPath();
            availableTabs.add(createTab(createLogoutLink(contextPath)));
        } else {
            availableTabs.add(createTab(LOGIN_TITLE, VaadinIcon.ARROW_DOWN, LoginView.class));
        }
        return availableTabs.toArray(new Tab[0]);
    }

    private static Tab createTab(Component content) {
        final Tab tab = new Tab();
        tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        tab.add(content);
        return tab;
    }

    private static Tab createTab(String text, VaadinIcon icon ,Class<? extends Component> navigationTarget) {
        final RouterLink pageLink = populateLink(new RouterLink(null, navigationTarget), icon, text);
        final Tab tab = createTab(pageLink);
        ComponentUtil.setData(tab, Class.class, navigationTarget);
        return tab;
    }

    private static Anchor createLogoutLink(String contextPath) {
        final Anchor anchor = populateLink(new Anchor(), VaadinIcon.ARROW_RIGHT, LOGOUT_TITLE);
        anchor.setHref(contextPath + LOGOUT_URL);
        return anchor;
    }

    private static <T extends HasComponents> T populateLink(T a, VaadinIcon icon, String title) {
        a.add(icon.create());
        a.add(title);
        return a;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        getTabForComponent(getContent()).ifPresent(menu::setSelectedTab);
    }

    private Optional<Tab> getTabForComponent(Component component) {
        try {
            return menu.getChildren()
                    .filter(tab -> ComponentUtil.getData(tab, Class.class)
                            .equals(component.getClass()))
                    .findFirst().map(Tab.class::cast);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
