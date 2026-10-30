package ro.umfst.biblioteca.persistence;

import java.nio.file.*;
import java.sql.*;

public final class Database {
    private final String url;
    public Database(Path file) throws java.io.IOException, SQLException {
        // Incarcam explicit driverul JDBC care este inclus in folderul lib.
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException | LinkageError ex) {
            throw new SQLException("Driverul SQLite nu este incarcat. Redeschide folderul proiect_2 "
                + "in IntelliJ si ruleaza configuratia Biblioteca sau Porneste.bat.", ex);
        }
        Path absolute = file.toAbsolutePath();
        Files.createDirectories(absolute.getParent());
        url = "jdbc:sqlite:" + absolute;
        try (Connection connection = connect(); Statement statement = connection.createStatement()) {
            statement.executeUpdate("""
                CREATE TABLE IF NOT EXISTS publicatii (
                    id TEXT PRIMARY KEY,
                    tip TEXT NOT NULL CHECK(tip IN ('Carte','Revista')),
                    titlu TEXT NOT NULL CHECK(length(trim(titlu)) > 0),
                    autor TEXT NOT NULL CHECK(length(trim(autor)) > 0),
                    an INTEGER NOT NULL CHECK(an >= 1450),
                    detaliu TEXT NOT NULL CHECK(length(trim(detaliu)) > 0),
                    imprumutata INTEGER NOT NULL DEFAULT 0 CHECK(imprumutata IN (0,1))
                )
                """);
        }
    }
    public Connection connect() throws SQLException {
        Connection connection = DriverManager.getConnection(url);
        try (Statement statement = connection.createStatement()) {
            statement.execute("PRAGMA busy_timeout = 5000");
        } catch (SQLException ex) {
            connection.close();
            throw ex;
        }
        return connection;
    }
}