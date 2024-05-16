package goit.database;

import goit.prefs.Prefs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DatabaseInitService {

    Connection connection;

    public DatabaseInitService(Connection connection) {
        this.connection = connection;
    }

    public void initialize() {
        try {
            String fileName = new Prefs().getValue(Prefs.INIT_DB_FILE);
            String sqlUpdate = String.join("\n", Files.readAllLines(Paths.get(fileName)));
            PreparedStatement preparedSt = connection.prepareStatement(sqlUpdate);
            preparedSt.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Connection connection = Database.getConnection();
        DatabaseInitService dbInitService = new DatabaseInitService(connection);
        dbInitService.initialize();
    }
}
