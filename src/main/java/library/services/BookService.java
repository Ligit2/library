package library.services;

import library.DAO;
import library.entities.Author;
import library.entities.Book;
import library.utils.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BookService {

    public static String showAuthors() throws SQLException {
        List<Author> authorsFromTable = DAO.getAuthorsFromTable();
        if(authorsFromTable.isEmpty()){
            return "No Authors in db";
        }
        else
        {
            authorsFromTable.forEach(author -> System.out.println(author));
            return null;
        }
    }

    public static void writeBook(Book book) throws SQLException {
        DAO.writeBook(book);
    }

    public static void writeBookAndAuthor(int bookId, List<Integer> ides) {
        ides.forEach(id -> {
            try {
                DAO.writeInBooks_AuthorsTable(bookId, id);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }
    public static void deleteBookById(int id) throws SQLException {
        DAO.deleteBookCascade(id);
    }

    public static void deleteBookFromTable(String title) throws SQLException {
        DAO.deleteBook(title);
    }

    public static void showBooksWithStringInTitle(String search) throws SQLException {
        ResultSet result;
        if (search == null) {
            result = DAO.getAllBooks();
        } else {
            result = DAO.getBooksWithStringInTitle(search);
        }
        boolean check = false;
        while (result.next()) {
            check = true;
            System.out.println("Book id : " + result.getInt(1));
            System.out.println("Book name : " + result.getString(2));
            System.out.println("Book genre : " + result.getString(3));
            System.out.println("Authors : " + result.getString(4));
            System.out.println();
        }
        if (!check) System.out.println("No such books");
    }

    public static void showBooks() throws SQLException {
        ResultSet books = DAO.getBooks();
        while(books.next()){
            System.out.println("Book id : " + books.getInt(1) + "  "
            +"Book name : "+ books.getString(2));
        }
    }

    public static void updateTitle(int bookId, String newTitle) throws SQLException {
        DAO.updateBooksByTitle(bookId,newTitle);
    }

    public static void updateGenre(int bookId, Genre newGenre) throws SQLException {
        DAO.updateBookByGenre(bookId,newGenre);
    }
}
