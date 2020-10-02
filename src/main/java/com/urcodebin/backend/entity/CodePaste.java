package com.urcodebin.backend.entity;

import com.urcodebin.enumerators.PasteExpiration;
import com.urcodebin.enumerators.SyntaxtHighlight;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "code_paste")
@SecondaryTable(name = "source_table",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "source_id", referencedColumnName = "ID"))
public class CodePaste {

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false, unique = true)
    private Integer id;

    @Column(name = "paste_id", unique = true, nullable = false, length = 16)
    private final UUID pasteId = UUID.randomUUID();

    @NotNull
    @Lob
    @Column(name = "source_code", table = "source_table")
    private String sourceCode;

    @Column(name = "paste_title")
    private String pasteTitle;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "syntax_highlighting")
    private SyntaxtHighlight syntaxHighlighting;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "paste_Expiration")
    private PasteExpiration pasteExpiration;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CodePaste)) return false;
        CodePaste codePaste = (CodePaste) o;
        return pasteId.equals(codePaste.pasteId) &&
                sourceCode.equals(codePaste.sourceCode) &&
                Objects.equals(pasteTitle, codePaste.pasteTitle) &&
                syntaxHighlighting.equals(codePaste.syntaxHighlighting) &&
                pasteExpiration.equals(codePaste.pasteExpiration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pasteId, sourceCode, pasteTitle, syntaxHighlighting, pasteExpiration);
    }

    public Integer getId() {
        return id;
    }

    public UUID getPasteId() {
        return pasteId;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getPasteTitle() {
        return pasteTitle;
    }

    public void setPasteTitle(String pasteTitle) {
        this.pasteTitle = pasteTitle;
    }

    public SyntaxtHighlight getSyntaxHighlighting() {
        return syntaxHighlighting;
    }

    public void setSyntaxHighlighting(SyntaxtHighlight syntaxHighlighting) {
        this.syntaxHighlighting = syntaxHighlighting;
    }

    public PasteExpiration getPasteExpiration() {
        return pasteExpiration;
    }

    public void setPasteExpiration(PasteExpiration pasteExpiration) {
        this.pasteExpiration = pasteExpiration;
    }
}
