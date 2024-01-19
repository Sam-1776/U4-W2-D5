package samuelesimeone.classi;

import samuelesimeone.superclassi.Biblioteca;

import java.time.LocalDate;

public class Libri extends Biblioteca {
    private String autore;
    private String genere;

    public Libri(Long codice_ISBN, String titolo, LocalDate anno_pubblicazione, Integer nPagine, String autore, String genere) {
        super(codice_ISBN, titolo, anno_pubblicazione, nPagine);
        this.autore = autore;
        this.genere = genere;
    }

    public String getAutore() {
        return autore;
    }

    public String getGenere() {
        return genere;
    }

    @Override
    public String toString() {
        return "Libri{" +
                "autore='" + autore + '\'' +
                ", genere='" + genere + '\'' +
                ", codice_ISBN=" + codice_ISBN +
                ", titolo='" + titolo + '\'' +
                ", anno_pubblicazione=" + anno_pubblicazione +
                ", nPagine=" + nPagine +
                '}';
    }
}
