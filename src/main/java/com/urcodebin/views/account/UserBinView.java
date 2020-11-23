package com.urcodebin.views.account;

import com.urcodebin.backend.entity.CodePaste;
import com.urcodebin.backend.entity.UserAccount;
import com.urcodebin.backend.interfaces.UserAccountService;
import com.urcodebin.enumerators.PasteVisibility;
import com.urcodebin.enumerators.SyntaxHighlight;
import com.urcodebin.helpers.PageRouter;
import com.urcodebin.security.SecurityUtils;
import com.urcodebin.views.main.MainView;
import com.urcodebin.views.paste.CodeView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.dnd.GridDropMode;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import java.time.LocalDateTime;
import java.util.*;

@Route(value = "myBin", layout = MainView.class)
@PageTitle("My Bin")
@Secured("ROLE_USER")
public class UserBinView extends VerticalLayout {

    private final Grid<CodePaste> profileBinGrid = new Grid<>(CodePaste.class);
    private final FormLayout formLayout = new FormLayout();

    private final UserAccountService userAccountService;

    private CodePaste selectedCodePaste;

    @Autowired
    public UserBinView(@Qualifier("AccountService") UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
        H1 profileUsername = new H1("TwinsRock88's Paste Bin");
        configureGrid();
        configureForm();
        SplitLayout splitLayout = createSplitLayout();
        add(profileUsername, new Hr(), splitLayout);
    }

    private void configureForm() {
        Button copyLinkBtn = createDisabledButton("Get Shareable Link" ,ButtonVariant.LUMO_CONTRAST);
        addListenerForCopyLinkBtn(copyLinkBtn);
        Button goToPasteBtn = createDisabledButton("Go To Paste", ButtonVariant.LUMO_PRIMARY);
        addListenerForGoToBtn(goToPasteBtn);
        Button deletePasteBtn = createDisabledButton("Delete Paste", ButtonVariant.LUMO_ERROR);
        addListenerForDeleteBtn(deletePasteBtn);
        formLayout.add(copyLinkBtn, goToPasteBtn, deletePasteBtn);
    }

    private void addListenerForGoToBtn(Button goToBtn) {
        goToBtn.addClickListener(event -> {
            PageRouter.routeToPage(CodeView.class, selectedCodePaste.getPasteId().toString());
        });
    }

    private void addListenerForCopyLinkBtn(Button copyBtn) {
        copyBtn.addClickListener(event -> {
            System.out.println("COPYING THE LINK FOR THE PAGE");
        });
    }
    private void addListenerForDeleteBtn(Button deleteBtn) {
        deleteBtn.addClickListener(event -> {
            System.out.println("DELETING THE PASTE");
        });
    }

    private Button createDisabledButton(String title, ButtonVariant btnVariant) {
        final Button btn = new Button(title);
        btn.getStyle().set("margin", "3%");
        btn.addThemeVariants(btnVariant);
        btn.setEnabled(false);
        return btn;
    }

    private void configureGrid() {
        profileBinGrid.setColumns("pasteTitle", "pasteId", "syntaxHighlighting", "pasteVisibility");
        profileBinGrid.setDropMode(GridDropMode.ON_TOP_OR_BETWEEN);
        profileBinGrid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        profileBinGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        profileBinGrid.setDataProvider(DataProvider.ofCollection(getAllCodePastesByTheUser()));
        profileBinGrid.getColumns().forEach(col -> col.setAutoWidth(true));

        addGridSelectFunctionality();
    }

    private void addGridSelectFunctionality() {
        profileBinGrid.asSingleSelect().addValueChangeListener(event -> {
           if(event.getValue() != null) {
               Optional<CodePaste> selectedPaste = profileBinGrid.getSelectedItems().stream().findFirst();
               if(selectedPaste.isPresent()) {
                   setButtonsEnabled(true);
                   selectedCodePaste = selectedPaste.get();
               }
           } else
               setButtonsEnabled(false);
        });
    }

    private void setButtonsEnabled(boolean val) {
        formLayout.getChildren()
                .filter(component -> component instanceof Button)
                .forEach(btn -> ((Button) btn).setEnabled(val));
    }

    private SplitLayout createSplitLayout() {
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();
        splitLayout.addToPrimary(profileBinGrid);
        splitLayout.setPrimaryStyle("minWidth", "80%");
        splitLayout.setPrimaryStyle("maxWidth", "80%");

        splitLayout.addToSecondary(formLayout);
        splitLayout.setSecondaryStyle("minWidth", "20%");
        splitLayout.setSecondaryStyle("maxWidth", "20%");
        return splitLayout;
    }

    private List<CodePaste> getAllCodePastesByTheUser() {
        String username;
        try {
            username = SecurityUtils.getUserCredentialsUsername();
        } catch (AuthenticationCredentialsNotFoundException e) {
            return Collections.emptyList();
        }
        Optional<UserAccount> userAccount = userAccountService.findByUsername(username);
        if(!userAccount.isPresent()) {
            return Collections.emptyList();
        }
        return userAccount.get().getCodePastes();
    }
}
