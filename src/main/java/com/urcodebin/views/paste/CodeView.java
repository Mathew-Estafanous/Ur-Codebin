package com.urcodebin.views.paste;

import com.urcodebin.backend.entity.CodePaste;
import com.urcodebin.backend.interfaces.PasteService;
import com.urcodebin.helpers.NotificationUtil;
import com.urcodebin.helpers.PageRouter;
import com.urcodebin.views.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Optional;
import java.util.UUID;

@Route(value = "view", layout = MainView.class)
@PageTitle("Code View")
@CssImport("./styles/views/paste/code-view.css")
@JsModule("./styles/copytoclipboard.js")
public class CodeView extends Div implements HasUrlParameter<String> {

    private final TextArea sourceCode = new TextArea("Source Code");
    private final H2 pasteTitle = new H2("Paste Title");

    private final Binder<CodePaste> binder = new Binder<>(CodePaste.class);
    private final PasteService pasteService;

    @Autowired
    public CodeView(@Qualifier("PasteService") PasteService pasteService) {
        setId("code-view");
        this.pasteService = pasteService;

        add(setupTitleView());
        add(new Hr());
        add(createButtonViews());
        add(createCodeView());

        binder.bindInstanceFields(this);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent,
                             @OptionalParameter String pasteId) {
        if(pasteId == null) {
            routeBackToMainPageAndNotifyUser();
        } else {
            usePasteIdToFindAndDisplay(pasteId);
        }
    }

    private void usePasteIdToFindAndDisplay(String pasteId) {
        UUID pasteUUID;
        try {
            pasteUUID = UUID.fromString(pasteId);
        } catch (IllegalArgumentException e) {
            routeBackToMainPageAndNotifyUser();
            return;
        }
        Optional<CodePaste> foundCodePaste = pasteService.findByPasteId(pasteUUID);
        if(!foundCodePaste.isPresent())
            routeBackToMainPageAndNotifyUser();
        else
            updatePageWithInformation(foundCodePaste.get());
    }

    private void updatePageWithInformation(CodePaste foundCodePaste) {
        pasteTitle.setText(foundCodePaste.getPasteTitle());
        binder.setBean(foundCodePaste);
    }

    private void routeBackToMainPageAndNotifyUser() {
        PageRouter.routeToPage(UploadPasteView.class);
        NotificationUtil.showNotification("We received an invalid ID and re-routed you back to the home page. " +
                "Please retry with a valid ID.");
    }

    private Component createButtonViews() {
        Button copyCodeBtn = createCopyCodeButton();
        Button getShareLinkBtn = createShareLinkButton();
        return new HorizontalLayout(copyCodeBtn, getShareLinkBtn);
    }

    private Button createShareLinkButton() {
        Button getShareLinkBtn = new Button("Get Share Link");
        getShareLinkBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        getShareLinkBtn.addClickListener(e -> {
            String pasteId = binder.getBean().getPasteId().toString();
            String pathToCurrentUrl = PageRouter.getRouteToPage(CodeView.class, pasteId);
            copyToClipboard(pathToCurrentUrl, "Shareable Link Copied To Clipboard!");
        });
        return getShareLinkBtn;
    }

    private Button createCopyCodeButton() {
        Button copyCodeBtn = new Button("Copy Raw Code");
        copyCodeBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        copyCodeBtn.addClickListener(e -> {
            String sourceCode = binder.getBean().getSourceCode();
            copyToClipboard(sourceCode, "Source Code Copied To Clipboard!");
        });
        return copyCodeBtn;
    }

    private void copyToClipboard(String toCopy, String successMsg) {
        UI.getCurrent().getPage().executeJs("window.copyToClipboard($0)", toCopy);
        NotificationUtil.showNotification(successMsg, NotificationVariant.LUMO_SUCCESS);
    }

    private Component createCodeView() {
        VerticalLayout sourceCodeLayout = new VerticalLayout();
        sourceCodeLayout.setClassName("source-code-layout");
        TextArea sourceCode = setupSourceCodeTextArea();
        sourceCodeLayout.add(sourceCode);
        return sourceCodeLayout;
    }

    private TextArea setupSourceCodeTextArea() {
        sourceCode.setWidth("100%");
        sourceCode.setMinHeight("500px");
        sourceCode.setReadOnly(true);
        return sourceCode;
    }

    private Component setupTitleView() {
        Div headerDiv= new Div();
        headerDiv.add(pasteTitle);
        return headerDiv;
    }
}
