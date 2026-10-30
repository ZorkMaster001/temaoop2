package ro.umfst.biblioteca.ui;

import ro.umfst.biblioteca.model.Publicatie;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public final class StatisticiPanel extends JPanel {
    private final JLabel total = new JLabel();
    private final JLabel disponibile = new JLabel();
    private final JLabel imprumutate = new JLabel();
    public StatisticiPanel() {
        super(new GridLayout(1, 3, 16, 0));
        setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        add(card("PUBLICATII", total)); add(card("DISPONIBILE", disponibile)); add(card("IMPRUMUTATE", imprumutate));
    }
    private JPanel card(String title, JLabel value) {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(210, 218, 225)),
            BorderFactory.createEmptyBorder(16, 20, 16, 20)));
        panel.add(new JLabel(title), BorderLayout.NORTH);
        value.setFont(value.getFont().deriveFont(Font.BOLD, 28f));
        value.setForeground(new Color(28, 88, 94));
        panel.add(value, BorderLayout.CENTER);
        return panel;
    }
    public void update(List<Publicatie> rows) {
        long borrowed = rows.stream().filter(Publicatie::isImprumutata).count();
        total.setText(String.valueOf(rows.size()));
        disponibile.setText(String.valueOf(rows.size() - borrowed));
        imprumutate.setText(String.valueOf(borrowed));
    }
}