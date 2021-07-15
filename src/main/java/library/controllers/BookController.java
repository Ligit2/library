package library.controllers;

import library.services.BookService;
import library.utils.Genre;
import library.entities.Book;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookController {

    public static void getBookFromConsole() {
        List<Integer> ides = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter name of the book");
        String bookName = scanner.nextLine();
        Genre bookGenre = getGenre(scanner);
        Book book = new Book(bookName, bookGenre);
        try {
            String s = BookService.showAuthors();
            if (s == null) {
                System.out.println("Enter number of Authors");
                int count = scanner.nextInt();
                while (count-- > 0) {
                    System.out.println("Enter Author's Id");
                    int id = scanner.nextInt();
                    ides.add(id);
                }
                BookService.writeBook(book);
                BookService.writeBookAndAuthor(book.getId(), ides);
            } else {
                System.out.println("There is no Authors , first add Authors in db");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static Genre getGenre(Scanner scanner) {
        System.out.println("Enter genre");
        System.out.println("drama :1");
        System.out.println("detective :2");
        System.out.println("science fiction :3");
        System.out.println("horror :4");
        System.out.println("thriller :5");
        int genre = scanner.nextInt();
        if (genre == 1) return Genre.DRAMA;
        else if (genre == 2) return Genre.DETECTIVE;
        else if (genre == 3) return Genre.SCIENCE_FICTION;
        else if (genre == 4) return Genre.HORROR;
        else return Genre.THRILLER;
    }

    public static void searchByTitle() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the string to search by title");
        String search = scanner.nextLine();
        try {
            BookService.showBooksWithStringInTitle(search);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void deleteBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter title of the book to delete");
        String title = scanner.nextLine();
        try {
            BookService.deleteBookFromTable(title);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void showBooks() {
        try {
            BookService.showBooksWithStringInTitle(null);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void updateBook() {
        List<Integer> ides = new ArrayList<>();
        try {
            showBooks();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the id of the book");
            int bookId = scanner.nextInt();
            System.out.println("Update name :1");
            System.out.println("update genre :2");
            int choice = scanner.nextInt();
            if(choice==1){
                System.out.println("Enter new name");
                String newTitle = scanner.nextLine();
                BookService.updateTitle(bookId,newTitle);
            }
            else
            {
                Genre newGenre = getGenre(scanner);
                BookService.updateGenre(bookId,newGenre);
            }
            showBooks();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
