package com.urcodebin.views.paste;

import com.urcodebin.backend.entity.CodePaste;
import com.urcodebin.backend.service.PasteService;
import com.urcodebin.views.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
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
    private final TextArea rawCode = new TextArea("Raw Paste Code");
    private final Label syntaxLabel = new Label("Syntax Highlighting:");
    private final TextField syntaxHighlight = new TextField();
    private final Label expirationLabel = new Label("Code Expiration:");
    private final TextField codeExpiration = new TextField();
    private final H2 codeTitle = new H2("Fake Title");

    private final PasteService pasteService;

    @Autowired
    public CodeView(@Qualifier("PasteService") PasteService pasteService) {
        setId("code-view");
        this.pasteService = pasteService;

        add(setupTitleView());
        add(new Hr());
        add(createChosenOptionsView());
        add(createCodeView());
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
            displayViewWithInformation(foundCodePaste.get());
    }

    private void routeBackToMainPageAndNotifyUser() {
        UI currentUI = UI.getCurrent();
        currentUI.access(() -> {
            currentUI.getUI().ifPresent(ui -> ui.navigate(PasteView.class));
        });
        Notification.show("We received an invalid ID and re-routed you back to the home page. " +
                "Please retry with a valid ID.");
    }

    private void displayViewWithInformation(CodePaste codePaste) {
        sourceCode.setValue(codePaste.getSourceCode());
        rawCode.setValue(codePaste.getSourceCode());
        syntaxHighlight.setValue(codePaste.getSyntaxHighlighting().toString());
        codeExpiration.setValue(codePaste.getPasteExpiration().toString());
        codeTitle.setText(codePaste.getPasteTitle());
    }

    private Component createChosenOptionsView() {
        return new HorizontalLayout(
                syntaxLabel, syntaxHighlight,
                expirationLabel, codeExpiration
        );
    }

    private Component createCodeView() {
        VerticalLayout sourceCodeLayout = new VerticalLayout();
        sourceCodeLayout.setClassName("source-code-layout");
        TextArea sourceCode = setupSourceCodeTextArea();
        TextArea rawCode = setupRawCodeTextArea();
        sourceCodeLayout.add(sourceCode, rawCode);
        return sourceCodeLayout;
    }

    private TextArea setupRawCodeTextArea() {
        rawCode.setWidth("100%");
        rawCode.setMinHeight("400px");
        rawCode.setMaxHeight("400px");
        rawCode.setReadOnly(true);
        return rawCode;
    }

    private TextArea setupSourceCodeTextArea() {
        sourceCode.setWidth("100%");
        sourceCode.setMinHeight("500px");
        sourceCode.setReadOnly(true);
        return sourceCode;
    }

    private Component setupTitleView() {
        syntaxHighlight.setReadOnly(true);
        codeExpiration.setReadOnly(true);
        Div headerDiv= new Div();
        headerDiv.add(codeTitle);
        return headerDiv;
    }
}
