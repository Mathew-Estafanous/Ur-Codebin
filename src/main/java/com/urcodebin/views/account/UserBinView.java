package com.urcodebin.views.account;

import com.urcodebin.backend.entity.CodePaste;
import com.urcodebin.enumerators.PasteVisibility;
import com.urcodebin.enumerators.SyntaxHighlight;
import com.urcodebin.views.main.MainView;
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
import org.springframework.security.access.annotation.Secured;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Route(value = "myBin", layout = MainView.class)
@PageTitle("Your Profile")
@Secured("ROLE_USER")
public class UserBinView extends VerticalLayout {

    private final Grid<CodePaste> profileBin = new Grid<>(CodePaste.class);
    private final FormLayout formLayout = new FormLayout();

    public UserBinView() {
        H1 profileUsername = new H1("TwinsRock88's Paste Bin");
        configureGrid();
        configureForm();
        SplitLayout splitLayout = createSplitLayout();
        add(profileUsername, new Hr(), splitLayout);
    }

    private void configureForm() {
        Button copyLinkBtn = createDisabledButton("Get Shareable Link" ,ButtonVariant.LUMO_CONTRAST);
        Button goToPasteBtn = createDisabledButton("Go To Paste", ButtonVariant.LUMO_PRIMARY);
        Button deletePasteBtn = createDisabledButton("Delete Paste", ButtonVariant.LUMO_ERROR);

        formLayout.add(copyLinkBtn, goToPasteBtn, deletePasteBtn);
    }

    private Button createDisabledButton(String title, ButtonVariant btnVariant) {
        final Button btn = new Button(title);
        btn.getStyle().set("margin", "2%");
        btn.setWidth("90%");
        btn.addThemeVariants(btnVariant);
        btn.setEnabled(false);
        return btn;
    }

    private void configureGrid() {
        profileBin.setDropMode(GridDropMode.ON_TOP_OR_BETWEEN);
        profileBin.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        profileBin.setSelectionMode(Grid.SelectionMode.SINGLE);
        profileBin.setDataProvider(DataProvider.ofCollection(createTempPastes()));
    }

    private SplitLayout createSplitLayout() {
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();
        splitLayout.addToPrimary(profileBin);
        splitLayout.setPrimaryStyle("minWidth", "75%");
        splitLayout.setPrimaryStyle("maxWidth", "75%");

        splitLayout.addToSecondary(formLayout);
        splitLayout.setSecondaryStyle("minWidth", "25%");
        splitLayout.setSecondaryStyle("maxWidth", "25%");
        return splitLayout;
    }

    private List<CodePaste> createTempPastes() {
        CodePaste first = new CodePaste();
        first.setSourceCode("FAKE");
        first.setSyntaxHighlighting(SyntaxHighlight.JAVA);
        first.setPasteTitle("Fake Title");
        first.setPasteVisibility(PasteVisibility.PUBLIC);
        first.setPasteExpiration(LocalDateTime.now());
        List<CodePaste> tempList = new ArrayList<>();
        tempList.add(first);
        CodePaste second = new CodePaste();
        second.setSourceCode("SECOND");
        second.setSyntaxHighlighting(SyntaxHighlight.CSHARP);
        second.setPasteTitle("Second Title");
        second.setPasteVisibility(PasteVisibility.PUBLIC);
        second.setPasteExpiration(LocalDateTime.now());
        tempList.add(second);
        return tempList;
    }
}
