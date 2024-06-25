import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private final String url = "jdbc:postgresql://database-one.cwacmjswcclu.us-east-1.rds.amazonaws.com:5432/Sistema de gestion hospital";
    private final String user = "Notch_maxi";  // Cambia esto a tu nombre de usuario de PostgreSQL
    private final String password = "21032005";  // Cambia esto a tu contraseña de PostgreSQL

    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
        return conn;
    }
}