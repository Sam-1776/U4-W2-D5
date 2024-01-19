package samuelesimeone.classi;

import samuelesimeone.enumeratori.Periodo;
import samuelesimeone.superclassi.Biblioteca;

import java.time.LocalDate;

public class Riviste extends Biblioteca {
    private Periodo periodicità;

    public Riviste(Long codice_ISBN, String titolo, LocalDate anno_pubblicazione, Integer nPagine, Periodo periodicità) {
        super(codice_ISBN, titolo, anno_pubblicazione, nPagine);
        this.periodicità = periodicità;
    }

    public Periodo getPeriodicità() {
        return periodicità;
    }

    @Override
    public String toString() {
        return "Riviste{" +
                "titolo='" + titolo + '\'' +
                ", codice_ISBN=" + codice_ISBN +
                ", periodicità=" + periodicità +
                ", anno_pubblicazione=" + anno_pubblicazione +
                ", nPagine=" + nPagine +
                '}';
    }
}
