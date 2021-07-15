package library;

import library.controllers.AuthorController;
import library.controllers.BookController;

import java.util.Scanner;

public class InputController {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int choice = getChoice(scanner);
            if (choice == 0) break;
            else if (choice == 1) {
                AuthorController.getAuthorFromConsole();
            } else if (choice == 2) {
                BookController.getBookFromConsole();
            } else if (choice == 3) {
                BookController.searchByTitle();
            } else if (choice == 4) {
                BookController.deleteBook();
            } else if (choice == 5) {
                AuthorController.deleteAuthor();
            } else if(choice == 6) {
                BookController.showBooks();
            }
            else {
                BookController.updateBook();
            }
        }
    }

    private static int getChoice(Scanner scanner) {
        System.out.println("Enter 1 for adding Author");
        System.out.println("Enter 2 for adding book");
        System.out.println("Enter 3 for searching by title");
        System.out.println("Enter 4 to delete book");
        System.out.println("Enter 5 to delete Author");
        System.out.println("Enter 6 for showing books");
        System.out.println("Enter 7 to update library");
        System.out.println("Enter 0 to finish");
        int choice = scanner.nextInt();
        return choice;
    }
}
