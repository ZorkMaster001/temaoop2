import ro.umfst.biblioteca.model.*;
import ro.umfst.biblioteca.persistence.*;
import ro.umfst.biblioteca.ui.MainFrame;
import javax.swing.*;
import java.awt.*;
import java.nio.file.*;

/** Verifica persistenta in procese Java separate si pornirea exacta din Main. */
public class MainRunTest {
    public static void main(String[] args) throws Exception {
        if (args.length == 2) {
            SqlitePublicatieRepository repo = new SqlitePublicatieRepository(new Database(Path.of(args[1])));
            if ("write".equals(args[0])) {
                Carte carte = new Carte("test-persistenta", "Carte de verificare", "Autor", 2024, "Roman", false);
                repo.save(carte);
            } else if ("update".equals(args[0])) {
                Publicatie carte = repo.findAll().get(0);
                if (!carte.getTitlu().equals("Carte de verificare")) throw new AssertionError("Titlu pierdut");
                carte.setImprumutata(true);
                repo.save(carte);
            } else {
                Publicatie carte = repo.findAll().get(0);
                if (!carte.isImprumutata()) throw new AssertionError("Stare pierduta dupa restart");
            }
            return;
        }

        Path folder = Files.createTempDirectory("biblioteca-procese-");
        Path file = folder.resolve("baza.db");
        try {
            String java = Path.of(System.getProperty("java.home"), "bin", "java.exe").toString();
            for (String action : new String[]{"write", "update", "read"}) {
                Process process = new ProcessBuilder(java, "-cp", System.getProperty("java.class.path"),
                    "MainRunTest", action, file.toString()).inheritIO().start();
                if (process.waitFor() != 0) throw new AssertionError("Persistenta: " + action);
            }
        } finally {
            Files.deleteIfExists(file);
            Files.deleteIfExists(folder);
        }

        Main.main(new String[0]);
        // Lansarea ferestrei este programata inaintea acestei verificari, pe acelasi EDT.
        SwingUtilities.invokeAndWait(() -> {
            boolean opened = false;
            try {
                for (Window window : Window.getWindows()) {
                    if (window instanceof MainFrame && window.isVisible()) opened = true;
                    if (window instanceof JDialog && window.isVisible())
                        throw new AssertionError("Dialog de eroare la pornire");
                }
                if (!opened) throw new AssertionError("Main nu a deschis aplicatia");
            } finally {
                for (Window window : Window.getWindows()) window.dispose();
            }
        });
        System.out.println("PASS: Main porneste, SQLite pastreaza datele si imprumutul intre 3 procese Java.");
    }
}