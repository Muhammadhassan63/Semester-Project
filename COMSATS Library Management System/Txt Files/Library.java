/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objectmodelpackage;

import java.util.ArrayList;



// Inheritance and encapsulation used

public class Library extends Institute{
    private ArrayList<Books> booksArrayList;

    private int forUG;
    private int forPG;
    private int forPHD;
    private int maxDays;

    public Library(String instituteName) {
        super(instituteName);
        booksArrayList = new ArrayList<>();

        if(instituteName.equals("COMSATS")) {
            forUG = 5;
            forPG = 3;
            forPHD = 2;
            maxDays = 12;
        }
    }

   

    public ArrayList<Books> getBooksArrayList() {
        return booksArrayList;
    }

    public void addBookToLibrary(Books books) {
        booksArrayList.add(books);
    }
//non primitive type return from funtion below
    public Books findBooksCollection(String findThisBook) {
        for(Books books : booksArrayList) {
            for(Book book : books.getBooks()) {
                if(book.getBookName().equals(findThisBook)) {
                    return books;
                }
            }
        }
        return null;
    }
//
    public RulesResultSet comsatsRules(String instituteName, String programEnrolledIn) {
        if(instituteName.equals("COMSATS")) {
            switch (programEnrolledIn) {
                case "UG":
                    return new RulesResultSet(forUG, maxDays);
                case "PG":
                    return new RulesResultSet(forPG, maxDays);
                case "PHD":
                    return new RulesResultSet(forPHD, maxDays);
            }
        }
        return null;
    }

   

}