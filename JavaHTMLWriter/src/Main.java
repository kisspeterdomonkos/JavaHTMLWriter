import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        if (args.length >= 1) {
            String name = "";
            for (int i = 0; i < args.length - 1; i++) {
                name += args[i] + " ";
            }
            String email = args[args.length - 1];
            generateHTMLFile(name.trim(), email);
        } else {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Nincsenek megadva argumentumok");
            System.out.println("Név:");
            String name = scanner.nextLine();
            System.out.println("E-mail cím:");
            String email = scanner.nextLine();
            generateHTMLFile(name, email);
            scanner.close();
        }
    }

    public static void generateHTMLFile(String name, String email) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Adja meg azokat a HTML elemeket, amelyeket nem szeretne megjeleníteni (pl.: h1, p, tr, td, a):");
        String elementsToHide = scanner.nextLine();

        String url = "https://github.com/kisspeterdomonkos/JavaHTMLWriter";
        String htmlContent = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<title>"+ name +" - Honlap</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>Teszt feladat</h1>\n" +
                "<p><a href=\""+ url +"\">Github</a></p>\n" +
                "<p>A weboldal elkészítőjének adatai</p>\n" +
                "<table border=\"1px solid black\">\n" +
                "<tr>\n" +
                "<td> Név </td>\n" +
                "<td>"+ name +"</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td> Elérhetőség </td>\n" +
                "<td>"+ email +"</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "</body>\n" +
                "</html>";

        String[] elements = elementsToHide.split(",");
        for (String element : elements) {
            if (element.trim().equals("a")) {
                htmlContent = htmlContent.replaceAll("<a\\s+(?:[^>]*?\\s+)?href=\"[^\"]*?\"[^>]*?>", "");
                htmlContent = htmlContent.replaceAll("</a>", "");
            } else {
                htmlContent = htmlContent.replaceAll("<" + element.trim() + ">", "");
                htmlContent = htmlContent.replaceAll("</" + element.trim() + ">", "");
            }
        }

        System.out.println("Adja meg az elérési útvonalat, hogy hova szeretné letölteni a HTML fájl-t:");
        String directoryPath = scanner.nextLine();

        System.out.println("Adjon nevet a HTML fájl-nak:");
        String fileName = scanner.nextLine() + ".html";
        scanner.close();

        String filePath = directoryPath + "/" + fileName;
        saveHTMLToFile(htmlContent, filePath);
    }

    public static void saveHTMLToFile(String htmlContent, String filePath) {
        try {
            Path path = Paths.get(filePath);
            Files.createDirectories(path.getParent());
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write(htmlContent);
                System.out.println("Sikeres mentés!");
            } catch (IOException e) {
                System.err.println("Hiba történt a HTML fájl írása közben: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Hiba történt a könyvtár létrehozása közben: " + e.getMessage());
        }
    }
}
