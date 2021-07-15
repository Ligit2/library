package library.services;

import library.DAO;
import library.entities.Author;

import java.sql.SQLException;

public class AuthorService {

    public static void write(Author author) throws SQLException {
        DAO.writeAuthor(author);
    }

    public static void deleteAuthorFromTable(String name) throws SQLException {
        DAO.deleteAuthor(name);
    }
}
