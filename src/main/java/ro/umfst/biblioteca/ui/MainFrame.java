package ro.umfst.biblioteca.ui;

import ro.umfst.biblioteca.model.Publicatie;
import ro.umfst.biblioteca.persistence.Repository;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.SQLException;

public final class MainFrame extends JFrame {
    private final Repository<Publicatie> repository;
    private final PublicatieTableModel model = new PublicatieTableModel();
    private final JTable table = new JTable(model);
    private final StatisticiPanel stats = new StatisticiPanel();
    private final JTextField search = new JTextField(24);
    private final JComboBox<String> status = new JComboBox<>(new String[]{"Toate", "Disponibile", "Imprumutate"});
    private final TableRowSorter<PublicatieTableModel> sorter = new TableRowSorter<>(model);
    private final JLabel footer = new JLabel(" ");

    public MainFrame(Repository<Publicatie> repository) {
        super("Biblioteca | Proiect OOP");
        this.repository = repository;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel content = new JPanel(new BorderLayout(0, 18));
        content.setBorder(BorderFactory.createEmptyBorder(24, 28, 20, 28));
        JPanel heading = new JPanel(new BorderLayout(0, 18));
        JLabel title = new JLabel("Biblioteca");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 32f));
        JPanel titles = new JPanel(new GridLayout(0, 1, 0, 5));
        titles.add(title); titles.add(new JLabel("Catalogul tau de carti si reviste"));
        heading.add(titles, BorderLayout.NORTH); heading.add(stats, BorderLayout.CENTER);
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        toolbar.add(new JLabel("Cauta:")); toolbar.add(search); toolbar.add(status);
        JButton add = new JButton("+ Adauga"); add.addActionListener(e -> edit(null)); toolbar.add(add);
        heading.add(toolbar, BorderLayout.SOUTH);
        content.add(heading, BorderLayout.NORTH);
        table.setRowSorter(sorter);
        table.setRowHeight(32);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);
        content.add(new JScrollPane(table), BorderLayout.CENTER);
        JPanel bottom = new JPanel(new BorderLayout());
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        JButton edit = new JButton("Editeaza");
        edit.addActionListener(e -> { Publicatie p = selected(); if (p != null) edit(p); });
        JButton loan = new JButton("Imprumuta / Returneaza");
        loan.addActionListener(e -> toggleLoan());
        JButton delete = new JButton("Sterge");
        delete.addActionListener(e -> delete());
        JButton about = new JButton("Despre");
        about.addActionListener(e -> JOptionPane.showMessageDialog(this,
            "Biblioteca - Proiect II OOP\nJava Swing + SQLite\nCarti: 21 zile | Reviste: 7 zile\n"
            + "Termenele sunt reguli demonstrative; aplicatia retine starea imprumutului.",
            "Despre aplicatie", JOptionPane.INFORMATION_MESSAGE));
        actions.add(edit); actions.add(loan); actions.add(delete); actions.add(about);
        bottom.add(actions, BorderLayout.NORTH);
        footer.setBorder(BorderFactory.createEmptyBorder(14, 10, 0, 0));
        bottom.add(footer, BorderLayout.SOUTH);
        content.add(bottom, BorderLayout.SOUTH); setContentPane(content);
        search.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { filter(); }
            public void removeUpdate(DocumentEvent e) { filter(); }
            public void changedUpdate(DocumentEvent e) { filter(); }
        });
        status.addActionListener(e -> filter());
        setSize(1100, 680); setMinimumSize(new Dimension(960, 580));
        setLocationRelativeTo(null); refresh();
    }

    private void filter() {
        String query = search.getText().trim().toLowerCase(java.util.Locale.ROOT);
        sorter.setRowFilter(new RowFilter<PublicatieTableModel, Integer>() {
            @Override public boolean include(Entry<? extends PublicatieTableModel, ? extends Integer> entry) {
                Publicatie p = model.getRow(entry.getIdentifier());
                boolean matches = (p.getTitlu() + " " + p.getAutor() + " " + p.getDetaliu() + " " + p.getTip())
                    .toLowerCase(java.util.Locale.ROOT).contains(query);
                return matches && (status.getSelectedIndex() == 0
                    || (status.getSelectedIndex() == 1 && !p.isImprumutata())
                    || (status.getSelectedIndex() == 2 && p.isImprumutata()));
            }
        });
        footer.setText(table.getRowCount() + " rezultate | Date salvate local in SQLite");
    }

    private Publicatie selected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selecteaza o publicatie din tabel.");
            return null;
        }
        return model.getRow(table.convertRowIndexToModel(row));
    }
    private void edit(Publicatie p) {
        PublicatieDialog dialog = p == null ? new PublicatieDialog(this) : new PublicatieDialog(this, p);
        dialog.setVisible(true);
        if (dialog.getResult() != null) {
            try { repository.save(dialog.getResult()); refresh(); }
            catch (SQLException ex) { error(ex); }
        }
    }
    private void toggleLoan() {
        Publicatie p = selected();
        if (p == null) return;
        String action = p.isImprumutata() ? "Confirmi returnarea?" :
            "Confirmi imprumutul pentru " + p.getDurataImprumut() + " zile?";
        if (JOptionPane.showConfirmDialog(this, action, p.getTitlu(),
                JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        boolean previous = p.isImprumutata();
        p.setImprumutata(!previous);
        try { repository.save(p); refresh(); }
        catch (SQLException ex) { p.setImprumutata(previous); error(ex); }
    }
    private void delete() {
        Publicatie p = selected();
        if (p == null) return;
        if (p.isImprumutata()) {
            JOptionPane.showMessageDialog(this, "Returneaza publicatia inainte de stergere.");
            return;
        }
        if (JOptionPane.showConfirmDialog(this, "Stergi publicatia „" + p.getTitlu() + "“?",
                "Confirma stergerea", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION)
            try { repository.delete(p.getId()); refresh(); } catch (SQLException ex) { error(ex); }
    }
    private void refresh() {
        try {
            var rows = repository.findAll();
            model.setRows(rows); stats.update(rows); filter();
        } catch (SQLException ex) { error(ex); }
    }
    private void error(Exception ex) {
        JOptionPane.showMessageDialog(this, "Operatia nu a reusit: " + ex.getMessage(),
            "Eroare baza de date", JOptionPane.ERROR_MESSAGE);
    }
}