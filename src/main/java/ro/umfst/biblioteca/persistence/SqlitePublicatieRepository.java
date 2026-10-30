package ro.umfst.biblioteca.persistence;

import ro.umfst.biblioteca.model.*;
import java.sql.*;
import java.util.*;

public final class SqlitePublicatieRepository implements Repository<Publicatie> {
    private final Database database;
    public SqlitePublicatieRepository(Database database) { this.database = database; }

    @Override public List<Publicatie> findAll() throws SQLException {
        List<Publicatie> result = new ArrayList<>();
        try (Connection c = database.connect();
             PreparedStatement statement = c.prepareStatement("SELECT * FROM publicatii ORDER BY titlu COLLATE NOCASE");
             ResultSet r = statement.executeQuery()) {
            while (r.next()) {
                if ("Carte".equals(r.getString("tip")))
                    result.add(new Carte(r.getString("id"), r.getString("titlu"), r.getString("autor"),
                        r.getInt("an"), r.getString("detaliu"), r.getBoolean("imprumutata")));
                else
                    result.add(new Revista(r.getString("id"), r.getString("titlu"), r.getString("autor"),
                        r.getInt("an"), Integer.parseInt(r.getString("detaliu")), r.getBoolean("imprumutata")));
            }
        }
        return result;
    }

    @Override public void save(Publicatie p) throws SQLException {
        String sql = """
            INSERT INTO publicatii(id,tip,titlu,autor,an,detaliu,imprumutata) VALUES(?,?,?,?,?,?,?)
            ON CONFLICT(id) DO UPDATE SET tip=excluded.tip,titlu=excluded.titlu,autor=excluded.autor,
            an=excluded.an,detaliu=excluded.detaliu,imprumutata=excluded.imprumutata
            """;
        try (Connection c = database.connect(); PreparedStatement s = c.prepareStatement(sql)) {
            s.setString(1, p.getId()); s.setString(2, p.getTip());
            s.setString(3, p.getTitlu()); s.setString(4, p.getAutor());
            s.setInt(5, p.getAn()); s.setString(6, p.getDetaliu()); s.setBoolean(7, p.isImprumutata());
            s.executeUpdate();
        }
    }

    @Override public void delete(String id) throws SQLException {
        try (Connection c = database.connect();
             PreparedStatement s = c.prepareStatement("DELETE FROM publicatii WHERE id=?")) {
            s.setString(1, id);
            s.executeUpdate();
        }
    }
}