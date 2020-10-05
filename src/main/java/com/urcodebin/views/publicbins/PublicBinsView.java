package com.urcodebin.views.publicbins;

import java.util.Optional;

import com.urcodebin.backend.entity.CodePaste;
import com.urcodebin.backend.service.PasteService;
import com.urcodebin.enumerators.StringToPasteExpiration;
import com.urcodebin.enumerators.StringToSyntaxHighlight;
import com.urcodebin.helpers.PageRouter;
import com.urcodebin.views.paste.CodeView;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToUuidConverter;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.urcodebin.views.main.MainView;

@Route(value = "bins", layout = MainView.class)
@PageTitle("Public Bins")
@CssImport("./styles/views/publicbins/public-bins-view.css")
public class PublicBinsView extends Div {

    private Grid<CodePaste> grid;
    private final Div editorLayoutDiv = new Div();

    private final TextField pasteId = new TextField();
    private final TextField pasteTitle = new TextField();
    private final TextField syntaxHighlighting = new TextField();
    private final TextField codeExpiration = new TextField();

    private final Button clear = new Button("Clear");
    private final Button view = new Button("View");

    private final Binder<CodePaste> binder;

    private CodePaste paste = new CodePaste();

    private final PasteService pasteService;

    @Autowired
    public PublicBinsView(@Qualifier("PasteService") PasteService pasteService) {
        setId("public-bins-view");
        this.pasteService = pasteService;
        configureGrid();

        populateFormWhenGridSelected();

        binder = new Binder<>(CodePaste.class);
        convertBinderVariablesToUseableStrings();

        binder.bindInstanceFields(this);

        clear.addClickListener(e -> {
            disableForm();
            refreshGrid();
        });

        view.addClickListener(e -> routeToCodeViewForChosenPaste());

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createChosenLayout(splitLayout);

        add(splitLayout);
    }

    private void convertBinderVariablesToUseableStrings() {
        binder.forField(pasteId)
                .withConverter(new StringToUuidConverter("Must Be a UUID"))
                .bind(CodePaste::getPasteId, null);

        binder.forField(syntaxHighlighting)
                .withConverter(new StringToSyntaxHighlight())
                .bind(CodePaste::getSyntaxHighlighting, CodePaste::setSyntaxHighlighting);

        binder.forField(codeExpiration)
                .withConverter(new StringToPasteExpiration())
                .bind(CodePaste::getPasteExpiration, CodePaste::setPasteExpiration);
    }

    private void routeToCodeViewForChosenPaste() {
        PageRouter.routeToPage(CodeView.class, paste.getPasteId().toString());
    }

    private void populateFormWhenGridSelected() {
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Optional<CodePaste> pasteFromBackend = pasteService.findByPasteId(event.getValue().getPasteId());
                if(pasteFromBackend.isPresent()){
                    populateForm(pasteFromBackend.get());
                } else {
                    refreshGrid();
                }
            } else {
                disableForm();
            }
        });
    }

    private void configureGrid() {
        grid = new Grid<>(CodePaste.class);
        grid.setColumns("pasteTitle", "pasteId", "syntaxHighlighting", "pasteExpiration");
        grid.setDataProvider(DataProvider.ofCollection(pasteService.findAllPublicPastes()));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();
    }

    private void createChosenLayout(SplitLayout splitLayout) {
        editorLayoutDiv.setId("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setId("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        makeAllFieldsReadOnly();
        addFormItem(editorDiv, formLayout, pasteTitle, "Paste Title");
        addFormItem(editorDiv, formLayout, pasteId, "Paste ID");
        addFormItem(editorDiv, formLayout, syntaxHighlighting, "Syntax Highlight");
        addFormItem(editorDiv, formLayout, codeExpiration, "Code Expiration");
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void makeAllFieldsReadOnly() {
        pasteTitle.setReadOnly(true);
        syntaxHighlighting.setReadOnly(true);
        codeExpiration.setReadOnly(true);
        pasteId.setReadOnly(true);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setId("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(true);
        clear.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        view.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(view, clear);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void addFormItem(Div wrapper, FormLayout formLayout, AbstractField field, String fieldName) {
        formLayout.addFormItem(field, fieldName);
        wrapper.add(formLayout);
        field.getElement().getClassList().add("full-width");
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void disableForm() {
        editorLayoutDiv.setVisible(false);
    }

    private void populateForm(CodePaste value) {
        this.paste = value;
        binder.readBean(this.paste);
        editorLayoutDiv.setVisible(true);
    }
}
