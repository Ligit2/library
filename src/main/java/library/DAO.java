package library;

import library.entities.Author;
import library.entities.Book;
import library.utils.DatabaseCredentials;
import library.utils.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAO {
    public static void writeAuthor(Author author) throws SQLException {
        Connection connection = DriverManager.
                getConnection(DatabaseCredentials.getURL(),
                        DatabaseCredentials.getUser(),
                        DatabaseCredentials.getPassword());
        PreparedStatement preparedStatement = connection.prepareStatement("insert into authors(name)" +
                "values (?)");
        preparedStatement.setString(1, author.getName());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public static List<Author> getAuthorsFromTable() throws SQLException {
        List<Author> authors = new ArrayList<>();
        Connection connection = DriverManager.
                getConnection(DatabaseCredentials.getURL(),
                        DatabaseCredentials.getUser(),
                        DatabaseCredentials.getPassword());
        PreparedStatement preparedStatement = connection.prepareStatement("select * from authors");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            authors.add(new Author(resultSet.getInt(1), resultSet.getString(2)));
        }
        return authors;
    }

    public static void writeBook(Book book) throws SQLException {
        Connection connection = DriverManager.
                getConnection(DatabaseCredentials.getURL(),
                        DatabaseCredentials.getUser(),
                        DatabaseCredentials.getPassword());
        PreparedStatement preparedStatement = connection.prepareStatement("insert into books(title,genre)" +
                "values (?,?)");
        preparedStatement.setString(1, book.getTitle());
        preparedStatement.setString(2, String.valueOf(book.getGenre()));
        preparedStatement.executeUpdate();
        preparedStatement.close();
        PreparedStatement preparedStatement1 = connection.prepareStatement("select id from books where title = ?");
        preparedStatement1.setString(1, book.getTitle());
        ResultSet resultSet = preparedStatement1.executeQuery();
        while (resultSet.next()) {
            book.setId(resultSet.getInt(1));
        }
    }

    public static void writeInBooks_AuthorsTable(Integer bookId, Integer authorId) throws SQLException {
        Connection connection = DriverManager.
                getConnection(DatabaseCredentials.getURL(),
                        DatabaseCredentials.getUser(),
                        DatabaseCredentials.getPassword());
        PreparedStatement preparedStatement = connection.prepareStatement("insert into books_authors(book_id,author_id)" +
                "values (?,?)");
        preparedStatement.setInt(1, bookId);
        preparedStatement.setInt(2, authorId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public static ResultSet getBooksWithStringInTitle(String search) throws SQLException {
        Connection connection = DriverManager.
                getConnection(DatabaseCredentials.getURL(),
                        DatabaseCredentials.getUser(),
                        DatabaseCredentials.getPassword());
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select b.id, b.title,b.genre, group_concat(a.name order by a.name separator ', ')\n" +
                        "from books b\n" +
                        "join books_authors  bs on b.id = bs.book_id\n" +
                        "join authors a on a.id = bs.author_id\n" +
                        "where LOCATE(?,b.title)\n" +
                        "group by b.title"
        );
        preparedStatement.setString(1, search);
        ResultSet resultSet = preparedStatement.executeQuery();
        preparedStatement.close();
        return resultSet;
    }

    public static void deleteBook(String title) throws SQLException {
        Connection connection = DriverManager.
                getConnection(DatabaseCredentials.getURL(),
                        DatabaseCredentials.getUser(),
                        DatabaseCredentials.getPassword());
        PreparedStatement preparedStatement = connection.prepareStatement("delete from books where title = ?");
        preparedStatement.setString(1, title);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public static void deleteBookCascade(int id) throws SQLException {
        Connection connection = DriverManager.
                getConnection(DatabaseCredentials.getURL(),
                        DatabaseCredentials.getUser(),
                        DatabaseCredentials.getPassword());
        PreparedStatement preparedStatement = connection.prepareStatement("delete from books_authors\n" +
                "where book_id = ?");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public static ResultSet getAllBooks() throws SQLException {
        Connection connection = DriverManager.
                getConnection(DatabaseCredentials.getURL(),
                        DatabaseCredentials.getUser(),
                        DatabaseCredentials.getPassword());
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select b.id , b.title,b.genre, group_concat(a.name order by a.name separator ', ')\n" +
                        "from books b\n" +
                        "join books_authors  bs on b.id = bs.book_id\n" +
                        "join authors a on a.id = bs.author_id\n" +
                        "group by b.title"
        );
        ResultSet resultSet = preparedStatement.executeQuery();
        preparedStatement.close();
        return resultSet;
    }

    public static void deleteAuthor(String name) throws SQLException {
        Connection connection = DriverManager.
                getConnection(DatabaseCredentials.getURL(),
                        DatabaseCredentials.getUser(),
                        DatabaseCredentials.getPassword());
        PreparedStatement preparedStatement = connection.prepareStatement("delete from authors where name = ?");
        preparedStatement.setString(1, name);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public static ResultSet getBooks() throws SQLException {
        Connection connection = DriverManager.
                getConnection(DatabaseCredentials.getURL(),
                        DatabaseCredentials.getUser(),
                        DatabaseCredentials.getPassword());
        PreparedStatement preparedStatement = connection.prepareStatement("select * from books");
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

    public static void updateBooksByTitle(int bookId, String newTitle) throws SQLException {
        Connection connection = DriverManager.
                getConnection(DatabaseCredentials.getURL(),
                        DatabaseCredentials.getUser(),
                        DatabaseCredentials.getPassword());
        PreparedStatement preparedStatement = connection.prepareStatement("update books set title =? where id=? ");
        preparedStatement.setString(1,newTitle);
        preparedStatement.setInt(2,bookId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public static void updateBookByGenre(int bookId, Genre newGenre) throws SQLException {
        Connection connection = DriverManager.
                getConnection(DatabaseCredentials.getURL(),
                        DatabaseCredentials.getUser(),
                        DatabaseCredentials.getPassword());
        PreparedStatement preparedStatement = connection.prepareStatement("update books set genre =? where id=? ");
        preparedStatement.setString(1, String.valueOf(newGenre));
        preparedStatement.setInt(2,bookId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
}
