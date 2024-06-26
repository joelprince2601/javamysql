import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/form_db";
        String jdbcUsername = "root";
        String jdbcPassword = "root";

        String filePath = "data/users.txt";
        String line;
        String sql = "INSERT INTO form (username, password) VALUES (?, ?)";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath));
                Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String username = parts[0];
                    String password = parts[1];

                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);
                    preparedStatement.executeUpdate();
                }
            }

            System.out.println("Data has been inserted successfully.");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
