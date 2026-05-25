/*
 * Disciplina: CBTSWE1 - ADS 571
 * Professor: Wellington Tuler Moraes
 * Trabalho Pratico 02 - CRUD de Livros
 * Dupla: Matheus Correia de Franca e Davi Leite Coelho
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookDao {
    private static final String DEFAULT_JDBC_URL = "jdbc:mysql://localhost:3306/java?useSSL=false&allowPublicKeyRetrieval=true&useTimezone=true&serverTimezone=UTC";
    private static final String DEFAULT_JDBC_USERNAME = "laravel";
    private static final String DEFAULT_JDBC_PASSWORD = "laravel";

    private BookDao() {
    }

    public static int save(Book book) throws SQLException {
        String sql = "INSERT INTO book (title, author, price) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setDouble(3, book.getPrice());
            return statement.executeUpdate();
        }
    }

    public static int update(Book book) throws SQLException {
        String sql = "UPDATE book SET title = ?, author = ?, price = ? WHERE book_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setDouble(3, book.getPrice());
            statement.setInt(4, book.getId());
            return statement.executeUpdate();
        }
    }

    public static int delete(int id) throws SQLException {
        String sql = "DELETE FROM book WHERE book_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate();
        }
    }

    public static Book findById(int id) throws SQLException {
        String sql = "SELECT book_id, title, author, price FROM book WHERE book_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapBook(resultSet);
                }
            }
        }
        return null;
    }

    public static List<Book> findAll() throws SQLException {
        List<Book> books = new ArrayList<Book>();
        String sql = "SELECT book_id, title, author, price FROM book ORDER BY book_id";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                books.add(mapBook(resultSet));
            }
        }
        return books;
    }

    private static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException exception) {
            throw new SQLException("Driver JDBC do MySQL nao encontrado.", exception);
        }

        return DriverManager.getConnection(getConfig("BOOKSTORE_JDBC_URL", DEFAULT_JDBC_URL),
                getConfig("BOOKSTORE_JDBC_USERNAME", DEFAULT_JDBC_USERNAME),
                getConfig("BOOKSTORE_JDBC_PASSWORD", DEFAULT_JDBC_PASSWORD));
    }

    private static String getConfig(String name, String fallback) {
        String value = System.getenv(name);
        if (value == null || value.trim().isEmpty()) {
            return fallback;
        }
        return value.trim();
    }

    private static Book mapBook(ResultSet resultSet) throws SQLException {
        return new Book(
                resultSet.getInt("book_id"),
                resultSet.getString("title"),
                resultSet.getString("author"),
                resultSet.getDouble("price"));
    }
}
