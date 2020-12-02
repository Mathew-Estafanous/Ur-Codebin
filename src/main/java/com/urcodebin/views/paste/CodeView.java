package com.urcodebin.views.paste;

import com.urcodebin.backend.entity.CodePaste;
import com.urcodebin.backend.interfaces.PasteService;
import com.urcodebin.convertors.StringToLocalDateTime;
import com.urcodebin.convertors.StringToPasteSyntax;
import com.urcodebin.helpers.PageRouter;
import com.urcodebin.views.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Optional;
import java.util.UUID;

@Route(value = "view", layout = MainView.class)
@PageTitle("Code View")
@CssImport("./styles/views/paste/code-view.css")
public class CodeView extends Div implements HasUrlParameter<String> {

    private final TextArea sourceCode = new TextArea("Source Code");
    private final Label syntaxLabel = new Label("Paste Syntax:");
    private final TextField pasteSyntax = new TextField();
    private final Label expirationLabel = new Label("Code Expiration:");
    private final TextField codeExpirationDate = new TextField();
    private final H2 pasteTitle = new H2("Paste Title");

    private final Binder<CodePaste> binder = new Binder<>(CodePaste.class);
    private final PasteService pasteService;

    @Autowired
    public CodeView(@Qualifier("PasteService") PasteService pasteService) {
        setId("code-view");
        this.pasteService = pasteService;

        add(setupTitleView());
        add(new Hr());
        add(createChosenOptionsView());
        add(createCodeView());

        createBinderForFields();
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

    private void createBinderForFields() {
        binder.forField(codeExpirationDate)
                .withConverter(new StringToLocalDateTime())
                .bind(CodePaste::getPasteExpiration, CodePaste::setPasteExpiration);
        binder.forField(pasteSyntax)
                .withConverter(new StringToPasteSyntax())
                .bind(CodePaste::getPasteSyntax, CodePaste::setPasteSyntax);
        binder.bindInstanceFields(this);
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
        binder.readBean(foundCodePaste);
    }

    private void routeBackToMainPageAndNotifyUser() {
        PageRouter.routeToPage(UploadPasteView.class);
        Notification.show("We received an invalid ID and re-routed you back to the home page. " +
                "Please retry with a valid ID.");
    }

    private Component createChosenOptionsView() {
        return new HorizontalLayout(
                syntaxLabel, pasteSyntax,
                expirationLabel, codeExpirationDate
        );
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
        pasteSyntax.setReadOnly(true);
        codeExpirationDate.setReadOnly(true);
        Div headerDiv= new Div();
        headerDiv.add(pasteTitle);
        return headerDiv;
    }
}
