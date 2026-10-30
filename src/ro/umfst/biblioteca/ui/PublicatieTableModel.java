package ro.umfst.biblioteca.ui;

import ro.umfst.biblioteca.model.Publicatie;
import javax.swing.table.AbstractTableModel;
import java.util.*;

public final class PublicatieTableModel extends AbstractTableModel {
    private List<Publicatie> rows = new ArrayList<>();
    private final String[] columns = {"Tip", "Titlu", "Autor / Editor", "An", "Gen / Numar", "Stare", "Termen (zile)"};
    public void setRows(List<Publicatie> rows) { this.rows = new ArrayList<>(rows); fireTableDataChanged(); }
    public Publicatie getRow(int index) { return rows.get(index); }
    @Override public int getRowCount() { return rows.size(); }
    @Override public int getColumnCount() { return columns.length; }
    @Override public String getColumnName(int column) { return columns[column]; }
    @Override public Class<?> getColumnClass(int column) {
        return column == 3 || column == 6 ? Integer.class : String.class;
    }
    @Override public Object getValueAt(int row, int column) {
        Publicatie p = rows.get(row);
        return switch (column) {
            case 0 -> p.getTip(); case 1 -> p.getTitlu(); case 2 -> p.getAutor();
            case 3 -> p.getAn(); case 4 -> p.getDetaliu();
            case 5 -> p.isImprumutata() ? "Imprumutata" : "Disponibila";
            case 6 -> p.getDurataImprumut(); default -> "";
        };
    }
}