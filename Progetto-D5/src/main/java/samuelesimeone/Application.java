package samuelesimeone;

import com.github.javafaker.Faker;
import org.apache.commons.io.FileUtils;
import samuelesimeone.classi.Libri;
import samuelesimeone.classi.Riviste;
import samuelesimeone.enumeratori.Periodo;
import samuelesimeone.superclassi.Biblioteca;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Application {

    static Faker faker = new Faker(Locale.ITALY);
    static Random rdm = new Random();
    static Scanner input = new Scanner(System.in);

    static File file = new File("src/main/java/samuelesimeone/dbCatalogo.txt");

    static Supplier<Libri> genrateBook = () ->{
      return new Libri(rdm.nextLong(1000000000), faker.book().title(), generateData(), rdm.nextInt(1000), faker.book().author(), faker.book().genre() );
    };

    static Supplier<Riviste> generateMagazine = () ->{

        Integer x = rdm.nextInt(3);
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


        for (int i = 0; i < 100; i++) {
            generateCatalogo(bibliotecaList);
        }

        System.out.println("********************************** Benvenuto ************************************");
            Integer cmd = 10;
            System.out.println("Che azioni vuoi fare nel catalogo? (1-Aggiungi, 2-Cancellazione, 3-Ricerca ISBN, 4-Ricerca Data, 5-Ricerca Autore, 6-Salva, 7-Scrivi da DB, 8-Cancella DB, 9-Vedi Catalogo, 0-Esci)");
            cmd = input.nextInt();
            switch (cmd){
                case 1: {
                    newElement(bibliotecaList);
                    break;
                }
                case 2: {
                    removeElement(bibliotecaList);
                    break;
                }
                case 3: {
                    searchElementByISBN(bibliotecaList);
                    break;
                }
                case 4:{
                    searchElementByDate(bibliotecaList);
                    break;
                }
                case 5:{
                    searchElementByAuthor(bibliotecaList);
                    break;
                }
                case 6:{
                    saveOnDisk(bibliotecaList);
                    break;
                }
                case 7:{
                    bibliotecaList.addAll(writeFromDB(file));
                    printCatalogo(bibliotecaList);
                    break;
                }
                case 8:{
                    removeDB(file);
                    break;
                }
                case 9:{
                    printCatalogo(bibliotecaList);
                    break;
                }
                default:{
                    System.out.println("Arrivederci");
                    break;
                }
            }
            input.close();


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
        System.out.println("Cosa vuoi aggiungere? (libro o rivista)");
        Scanner scanner = new Scanner(System.in);
        str = scanner.nextLine();
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

    public static void searchElementByISBN(List<Biblioteca> x){
        System.out.println("Quale elemento vuoi cercare?");
        Long y;
        y = input.nextLong();
        List<Biblioteca> elemnet = x.stream().filter(j -> j.getCodice_ISBN().equals(y)).collect(Collectors.toList());
        System.out.println("Elemento cercato: " + elemnet.get(0));
    }

    public static void searchElementByDate(List<Biblioteca> x){
        try{;
            System.out.println("Di che anno è l'elemento?");
            Scanner scanner = new Scanner(System.in);
            String test = scanner.nextLine();
            LocalDate g = LocalDate.parse(test);
                List<Biblioteca> element = x.stream().filter(j -> j.getAnno_pubblicazione().equals(g)).collect(Collectors.toList());
                element.forEach(System.out::println);
                scanner.close();
        }catch (DateTimeException e){
            System.out.println(e.getMessage());
        }
    }

    public static void searchElementByAuthor(List<Biblioteca> x){
        try{
            System.out.println("Inserire il nome dell'autore");
            Scanner scanner = new Scanner(System.in);
            String b = scanner.nextLine();
            List<Biblioteca> element = x.stream().filter(j->j instanceof Libri).filter(a -> ((Libri) a).getAutore().equals(b)).collect(Collectors.toList());
            element.forEach(System.out::println);
            scanner.close();
        }catch (Exception e){
            e.getMessage();
        }
    }

    public static void saveOnDisk(List<Biblioteca> x ){
        for (int i = 0; i < x.size(); i++) {
                x.stream().forEach(j -> {
                    if (j instanceof Libri){
                        try {
                            FileUtils.writeStringToFile(file, ((Libri) j).getAutore() + "@" + ((Libri) j).getGenere() + "@" +  j.getCodice_ISBN() + "@" + j.getTitolo() + "@" + j.getAnno_pubblicazione() + "@" + j.getnPagine() + "#" , StandardCharsets.UTF_8, true);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }else {
                        try {
                            FileUtils.writeStringToFile(file, j.getTitolo() + "@" + j.getCodice_ISBN() + "@" + ((Riviste) j).getPeriodicità() + "@" + j.getAnno_pubblicazione() + "@" + j.getnPagine() + "#" , StandardCharsets.UTF_8, true);
                        }catch (IOException e){
                            System.out.println(e.getMessage());
                        }

                    }
                });
        }

    }

    public static List<Biblioteca> writeFromDB(File x){
        List<Biblioteca> y = new ArrayList<>();
        try{
            String contenuto = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

            List<String> splitElementiString = Arrays.asList(contenuto.split("#"));

            splitElementiString.stream().forEach(str -> {
                if (str.contains(Periodo.Semestrale) || str.contains(Periodo.Mensile) || str.contains(Periodo.Settimanale)) {
                    if (str.contains(Periodo.Semestrale)) {
                        y.add(new Riviste(Long.parseLong(str.split("@")[1]), str.split("@")[0], LocalDate.parse(str.split("@")[3]), Integer.parseInt(str.split("@")[4]), Periodo.Semestrale));
                    } else if (str.contains(Periodo.Mensile)) {
                        y.add(new Riviste(Long.parseLong(str.split("@")[1]), str.split("@")[0], LocalDate.parse(str.split("@")[3]), Integer.parseInt(str.split("@")[4]), Periodo.Mensile));
                    }else {
                        y.add(new Riviste(Long.parseLong(str.split("@")[1]), str.split("@")[0], LocalDate.parse(str.split("@")[3]), Integer.parseInt(str.split("@")[4]), Periodo.Settimanale));
                    }

                } else {
                    y.add(new Libri(Long.parseLong(str.split("@")[2]),str.split("@")[3], LocalDate.parse(str.split("@")[4]), Integer.parseInt(str.split("@")[5]),str.split("@")[0], str.split("@")[1]));
                }
            });

            }catch (IOException e){
                System.out.println(e.getMessage());
            }

        return y;
    }

    public static void printCatalogo(List<Biblioteca> x){
        System.out.println("********************* Catalogo Completo ********************");
        x.stream().forEach(System.out::println);
    }

    public static void removeDB (File x){
        FileUtils.deleteQuietly(x);
    }
}
