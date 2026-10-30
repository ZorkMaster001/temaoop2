package ro.umfst.biblioteca.ui;

import ro.umfst.biblioteca.model.*;
import javax.swing.*;
import java.awt.*;
import java.util.UUID;

public final class PublicatieDialog extends JDialog {
    private final JComboBox<String> tip = new JComboBox<>(new String[]{"Carte", "Revista"});
    private final JTextField titlu = new JTextField(25);
    private final JTextField autor = new JTextField(25);
    private final JTextField an = new JTextField(String.valueOf(java.time.Year.now().getValue()));
    private final JTextField detaliu = new JTextField(25);
    private final JLabel detaliuLabel = new JLabel("Gen literar");
    private Publicatie result;
    private final Publicatie original;

    public PublicatieDialog(JFrame owner) { this(owner, null); }
    public PublicatieDialog(JFrame owner, Publicatie original) {
        super(owner, original == null ? "Adauga publicatie" : "Editeaza publicatie", true);
        this.original = original;
        JPanel form = new JPanel(new GridLayout(0, 2, 12, 12));
        form.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        form.add(new JLabel("Tip")); form.add(tip);
        form.add(new JLabel("Titlu")); form.add(titlu);
        form.add(new JLabel("Autor / Editor")); form.add(autor);
        form.add(new JLabel("An aparitie")); form.add(an);
        form.add(detaliuLabel); form.add(detaliu);
        tip.addActionListener(e -> updateLabel());
        if (original != null) {
            tip.setSelectedItem(original.getTip());
            tip.setEnabled(false);
            titlu.setText(original.getTitlu()); autor.setText(original.getAutor());
            an.setText(String.valueOf(original.getAn())); detaliu.setText(original.getDetaliu());
        }
        updateLabel();
        JButton save = new JButton("Salveaza");
        save.addActionListener(e -> save());
        JButton cancel = new JButton("Anuleaza");
        cancel.addActionListener(e -> dispose());
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actions.add(cancel); actions.add(save);
        add(form, BorderLayout.CENTER); add(actions, BorderLayout.SOUTH);
        getRootPane().setDefaultButton(save);
        getRootPane().registerKeyboardAction(e -> dispose(), KeyStroke.getKeyStroke("ESCAPE"),
            JComponent.WHEN_IN_FOCUSED_WINDOW);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack(); setResizable(false); setLocationRelativeTo(owner);
    }
    private void updateLabel() {
        detaliuLabel.setText(tip.getSelectedIndex() == 0 ? "Gen literar" : "Numar revista");
    }
    private void save() {
        try {
            String id = original == null ? UUID.randomUUID().toString() : original.getId();
            boolean borrowed = original != null && original.isImprumutata();
            int year = Integer.parseInt(an.getText().trim());
            result = tip.getSelectedIndex() == 0
                ? new Carte(id, titlu.getText(), autor.getText(), year, detaliu.getText(), borrowed)
                : new Revista(id, titlu.getText(), autor.getText(), year,
                    Integer.parseInt(detaliu.getText().trim()), borrowed);
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Anul si numarul revistei trebuie sa fie numere intregi.",
                "Date invalide", JOptionPane.WARNING_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Date invalide", JOptionPane.WARNING_MESSAGE);
        }
    }
    public Publicatie getResult() { return result; }
}