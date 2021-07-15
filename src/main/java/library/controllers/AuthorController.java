package library.controllers;

import library.services.AuthorService;
import library.entities.Author;
import library.services.BookService;

import java.sql.SQLException;
import java.util.Scanner;

public class AuthorController {

    public static void getAuthorFromConsole() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Author's name");
        String name = scanner.nextLine();
        Author author = new Author(name);
        try {
            AuthorService.write(author);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void deleteAuthor() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Author's to delete");
        String name = scanner.nextLine();
        try {
            AuthorService.deleteAuthorFromTable(name);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
