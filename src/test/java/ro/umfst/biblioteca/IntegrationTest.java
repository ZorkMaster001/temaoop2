package ro.umfst.biblioteca;

import ro.umfst.biblioteca.model.*;
import ro.umfst.biblioteca.persistence.*;
import ro.umfst.biblioteca.ui.*;
import java.nio.file.*;
import javax.swing.*;

public final class IntegrationTest {
    private static void check(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
    public static void main(String[] args) throws Exception {
        Path dir = Files.createTempDirectory("biblioteca-test-");
        Path file = dir.resolve("test.db");
        try {
            var repo = new SqlitePublicatieRepository(new Database(file));
            Publicatie book = new Carte("Ion ' SQL", "Liviu Rebreanu", 1920, "Roman");
            Publicatie magazine = new Revista("Stiinta", "Redactia", 2024, 12);
            repo.save(book); repo.save(magazine);
            check(repo.findAll().size() == 2, "Inserare");
            book.setTitlu("Ion revizuit");
            book.setImprumutata(true); repo.save(book);
            var reopened = new SqlitePublicatieRepository(new Database(file));
            Publicatie saved = reopened.findAll().stream().filter(book::equals).findFirst().orElseThrow();
            check(saved.isImprumutata() && saved.getTitlu().equals("Ion revizuit"), "Persistenta si update");
            check(saved instanceof Carte && saved.getDurataImprumut() == 21, "Polimorfism Carte");
            check(magazine.getDurataImprumut() == 7, "Polimorfism Revista");
            check(saved.equals(book) && saved.hashCode() == book.hashCode(), "Identitate");
            check(saved.toString().contains("Carte: Roman"), "super.toString()");
            saved.setImprumutata(false); reopened.save(saved);
            check(!reopened.findAll().stream().filter(book::equals).findFirst().orElseThrow().isImprumutata(), "Returnare");
            reopened.delete(book.getId()); check(reopened.findAll().size() == 1, "Stergere");
            try { new Revista("Test", "Editor", 2024, 0); throw new AssertionError("Validare"); }
            catch (IllegalArgumentException expected) { }
            try { book.setTitlu(" "); throw new AssertionError("Titlu gol"); }
            catch (IllegalArgumentException expected) { }
            SwingUtilities.invokeAndWait(() -> {
                var model = new PublicatieTableModel();
                model.setRows(java.util.List.of(magazine));
                check(model.getRowCount() == 1 && model.getValueAt(0, 6).equals(7), "Tabel Swing");
                var frame = new MainFrame(reopened);
                check(frame.getTitle().contains("Biblioteca"), "Fereastra principala");
                var dialog = new PublicatieDialog(frame, magazine);
                check(dialog.isModal(), "Editor modal");
                dialog.dispose(); frame.dispose();
            });
            System.out.println("PASS: CRUD SQLite, persistenta, validare, OOP, tabel si ferestre Swing.");
        } finally {
            Files.deleteIfExists(file);
            Files.deleteIfExists(dir);
        }
    }
}