package ro.umfst.biblioteca;

import ro.umfst.biblioteca.persistence.*;
import ro.umfst.biblioteca.ui.MainFrame;
import javax.swing.*;
import java.nio.file.Path;

public final class App {
    private App() { }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                var repository = new SqlitePublicatieRepository(new Database(Path.of("data", "biblioteca.db")));
                new MainFrame(repository).setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Aplicatia nu poate porni:\n" + ex.getMessage(),
                    "Eroare", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}