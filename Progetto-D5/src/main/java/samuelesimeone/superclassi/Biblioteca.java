package samuelesimeone.superclassi;

import java.time.LocalDate;

public class Biblioteca {
    protected Long codice_ISBN;
    protected String titolo;
    protected LocalDate anno_pubblicazione;
    protected Integer nPagine;

    public Biblioteca(Long codice_ISBN, String titolo, LocalDate anno_pubblicazione, Integer nPagine) {
        this.codice_ISBN = codice_ISBN;
        this.titolo = titolo;
        this.anno_pubblicazione = anno_pubblicazione;
        this.nPagine = nPagine;
    }

    public Long getCodice_ISBN() {
        return codice_ISBN;
    }

    public String getTitolo() {
        return titolo;
    }

    public LocalDate getAnno_pubblicazione() {
        return anno_pubblicazione;
    }

    public Integer getnPagine() {
        return nPagine;
    }
}
