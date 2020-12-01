package com.urcodebin.views.paste;

import com.urcodebin.backend.entity.CodePaste;
import com.urcodebin.backend.entity.UserAccount;
import com.urcodebin.backend.interfaces.PasteService;
import com.urcodebin.backend.interfaces.UserAccountService;
import com.urcodebin.convertors.PasteExpirationToLocalDateTime;
import com.urcodebin.enumerators.PasteExpiration;
import com.urcodebin.enumerators.PasteSyntax;
import com.urcodebin.enumerators.PasteVisibility;
import com.urcodebin.helpers.PageRouter;
import com.urcodebin.security.SecurityUtils;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.urcodebin.views.main.MainView;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.EnumSet;
import java.util.Optional;

@Route(value = "", layout = MainView.class)
@PageTitle("+ Paste")
@CssImport("./styles/views/paste/paste-view.css")
public class PasteView extends Div {

    private final TextArea sourceCode = new TextArea("Source Code");
    private final TextField pasteTitle = new TextField("Code Title");
    private final ComboBox<PasteSyntax> pasteSyntax = new ComboBox<>("Syntax Highlighting");
    private final ComboBox<PasteExpiration> pasteExpiration = new ComboBox<>("Code Expiration");
    private final ComboBox<PasteVisibility> pasteVisibility = new ComboBox<>("Paste Visibility");
    
    private final Button upload = new Button("Upload");
    private final Button undo = new Button("Undo");

    private final Binder<CodePaste> binder = new Binder<>(CodePaste.class);

    public PasteView(@Qualifier("PasteService") PasteService pasteService,
                     @Qualifier("AccountService") UserAccountService userAccountService) {
        setId("paste-view");

        setupSourceCodeTextArea();
        setupCodeTitleTextField();
        setupSyntaxHighlightDopBox();
        setupCodeExpirationDropBox();
        setupPasteVisibilityDropBox();

        add(createTitle());
        add(new Hr());
        add(createFormLayout());
        add(createButtonLayout());

        binder.forField(pasteExpiration)
                .withConverter(new PasteExpirationToLocalDateTime())
                .bind(CodePaste::getPasteExpiration, CodePaste::setPasteExpiration);
        binder.bindInstanceFields(this);
        clearForm();

        undo.addClickListener(e -> clearForm());
        upload.addClickListener(e -> {
            createNewCodePaste(pasteService, userAccountService);
        });
    }

    private void createNewCodePaste(PasteService pasteService, UserAccountService userAccountService) {
        CodePaste newPaste = binder.getBean();
        setUserAccountIfLoggedIn(userAccountService, newPaste);
        CodePaste codePaste = pasteService.createNewPaste(newPaste);
        navigateToCodeView(codePaste);
    }

    private void setUserAccountIfLoggedIn(UserAccountService userAccountService, CodePaste newPaste) {
        if(SecurityUtils.isUserLoggedIn()) {
            String username = SecurityUtils.getUserCredentialsUsername();
            Optional<UserAccount> userAccount = userAccountService.findByUsername(username);
            userAccount.ifPresent(newPaste::setUserAccount);
        }
    }

    private void navigateToCodeView(CodePaste codePaste) {
        PageRouter.routeToPage(CodeView.class, codePaste.getPasteId().toString());
    }

    private void setupCodeTitleTextField() {
        pasteTitle.setMaxLength(60);
    }

    private void setupPasteVisibilityDropBox() {
        pasteVisibility.setItemLabelGenerator(PasteVisibility::getValue);
        pasteVisibility.setItems(EnumSet.allOf(PasteVisibility.class));
        pasteVisibility.setRequired(true);
        pasteVisibility.setAllowCustomValue(false);
    }

    private void setupCodeExpirationDropBox() {
        pasteExpiration.setItemLabelGenerator(PasteExpiration::getValue);
        pasteExpiration.setItems(EnumSet.allOf(PasteExpiration.class));
        pasteExpiration.setRequired(true);
        pasteExpiration.setAllowCustomValue(false);
    }

    private void setupSyntaxHighlightDopBox() {
        pasteSyntax.setItemLabelGenerator(PasteSyntax::getValue);
        pasteSyntax.setItems(EnumSet.allOf(PasteSyntax.class));
        pasteSyntax.setRequired(true);
        pasteSyntax.setAllowCustomValue(false);
    }

    private void setupSourceCodeTextArea() {
        sourceCode.setRequired(true);
        sourceCode.setMinHeight("500px");
        sourceCode.setAutocorrect(false);
        sourceCode.setPlaceholder("//Place your code here!");
    }

    private void clearForm() {
        binder.setBean(new CodePaste());
        setDefaultSettings();
    }

    private void setDefaultSettings() {
        pasteTitle.setValue("Untitled Code");
        pasteExpiration.setValue(PasteExpiration.TENMINUTES);
        pasteSyntax.setValue(PasteSyntax.NONE);
        pasteVisibility.setValue(PasteVisibility.PRIVATE);
    }

    private Component createTitle() {
        return new H3("New Paste");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0px", 1),
                new FormLayout.ResponsiveStep("500px", 2)
        );
        formLayout.setColspan(sourceCode, 2);
        formLayout.add(sourceCode, pasteSyntax, pasteExpiration, pasteTitle, pasteVisibility);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        upload.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(upload);
        buttonLayout.add(undo);
        return buttonLayout;
    }

}
