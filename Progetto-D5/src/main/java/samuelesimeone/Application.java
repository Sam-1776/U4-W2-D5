package samuelesimeone;

import com.github.javafaker.Faker;
import samuelesimeone.classi.Libri;
import samuelesimeone.classi.Riviste;
import samuelesimeone.enumeratori.Periodo;
import samuelesimeone.superclassi.Biblioteca;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Supplier;

public class Application {

    static Faker faker = new Faker(Locale.ITALY);
    static Random rdm = new Random();
    static Scanner input = new Scanner(System.in);

    static Supplier<Libri> genrateBook = () ->{
      return new Libri(rdm.nextLong(1000000000), faker.book().title(), generateData(), rdm.nextInt(1000), faker.book().author(), faker.book().genre() );
    };

    static Supplier<Riviste> generateMagazine = () ->{

        Integer x = rdm.nextInt();
        Riviste magazine;
        if (x == 1) {
            magazine = new Riviste(rdm.nextLong(1000000000), faker.book().title(), generateData(), rdm.nextInt(12,30), Periodo.Settimanale);
        } else if (x == 2) {
            magazine = new Riviste(rdm.nextLong(1000000000), faker.book().title(), generateData(), rdm.nextInt(12,30), Periodo.Mensile);
        }else{
            magazine = new Riviste(rdm.nextLong(1000000000), faker.book().title(), generateData(), rdm.nextInt(12,30), Periodo.Semestrale);
        }

        return magazine;
    };

    public static void main(String[] args) {

        System.out.println("Progetto W2");

        List<Biblioteca> bibliotecaList = new ArrayList<>();
        String str = "";

        for (int i = 0; i < 100; i++) {
            generateCatalogo(bibliotecaList);
        }

            System.out.println("Vuoi inserire un elemento in catalogo?");
            str = input.nextLine();
            if (str.equals("si")){
                newElement(bibliotecaList);
            }

        System.out.println("Catalogo in stampa");
        printCatalogo(bibliotecaList);

        System.out.println("Vuoi rimuovere un elemento in catalogo?");
        str = input.nextLine();
        if (str.equals("si")){
            removeElement(bibliotecaList);
        }


    }

    public static List<Biblioteca> generateCatalogo(List<Biblioteca> x){
        Integer y = rdm.nextInt(2);
        if (y == 1) {
            x.add(genrateBook.get());
        }else {
            x.add(generateMagazine.get());
        }
        return x;
    }

    public static LocalDate generateData (){

        int year = rdm.nextInt(1600,2023);
        int month = rdm.nextInt(12) + 1;
        int maxDay = LocalDate.of(year, month, 1).lengthOfMonth();
        int day = rdm.nextInt(maxDay) + 1;

        LocalDate randomDate = LocalDate.of(year, month, day);

        return randomDate;

    }

    public static List<Biblioteca> newElement(List<Biblioteca> x){
        String str = "";
        System.out.println("Cosa vuoi aggiungere?");
        str = input.nextLine();
        if (str.equals("libro")){
            Libri newBook = genrateBook.get();
            System.out.println("Libro aggiunto: " + newBook.toString());
            x.add(newBook);
        }else if (str.equals("rivista")){
            Riviste newMegazine = generateMagazine.get();
            System.out.println("Rivista aggiunta: " + newMegazine.toString());
            x.add(newMegazine);
        }else {
            System.out.println("Non è in catalogo questo elemento");
        }

        return x;
    }

    public static List<Biblioteca> removeElement(List<Biblioteca> x){
        Long y = null;
        System.out.println("Quale elemento vuoi eliminare?");
        y = input.nextLong();
        ListIterator<Biblioteca> iter = x.listIterator();
        while (iter.hasNext()){
            if (iter.next().getCodice_ISBN().equals(y)){
                System.out.println("Elemento con successo rimosso");
                iter.remove();
            }
        }
        printCatalogo(x);
        return x;
    }

    public static void printCatalogo(List<Biblioteca> x){
        System.out.println("********************* Catalogo Completo ********************");
        x.stream().forEach(System.out::println);
    }
}
