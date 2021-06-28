/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objectmodelpackage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Book {
    private String bookName;
    private String bookAuthor;
    private String borrowerId;
    private String borrowDate;
    private String returnDate;
    private boolean pendingReturn;
    //Constructor is used below
    public Book(Book book) {
        this.bookName = book.getBookName();
        this.bookAuthor = book.getBookAuthor();
    }

    public Book(String bookName, String bookAuthor) {
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
    }

    Book() {
       
    }

    public String getBookName() {
        return bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public String getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(String borrowerId) {
        this.borrowerId = borrowerId;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isPendingReturn() {
        return pendingReturn;
    }

    public void setPendingReturn(boolean pendingReturn) {
        this.pendingReturn = pendingReturn;
    }
    //File Handling is used here 
     public void addBook() {
        FileWriter myWriter = null;
        try {
            myWriter = new FileWriter("Books.txt", true);
        } catch (IOException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        String dataToWrite = bookName + "," + bookAuthor ;

        try {

            myWriter.append(dataToWrite + "\n");
            System.out.println("Successfully wrote to the file.");

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } finally {
            try {
                myWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
//File handling is used below
    public ArrayList<Book> displayBooks() {
        ArrayList<Book> books = new ArrayList<Book>();
        FileInputStream file = null;
        String[] data = null;
        try {
            file = new FileInputStream("Books.txt");
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                data = scan.nextLine().split(",");
                books.add(new Book(data[0], data[1]));
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                file.close();
            } catch (IOException ex) {
                Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return books;

    }
}