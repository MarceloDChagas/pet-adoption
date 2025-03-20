package repository.Database;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnectionTest {
    @Test
    void testDatabaseConnection() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            assertNotNull(connection, "A conexão não deveria ser nula!");
            System.out.println("✅ Conexão com o banco de dados estabelecida com sucesso!");
        } catch (SQLException e) {
            System.out.println("❌ Erro ao conectar ao banco de dados:");
            e.printStackTrace();
        }
    }
}