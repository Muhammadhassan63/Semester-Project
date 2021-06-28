/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objectmodelpackage;

/**
 *
 * @author abc
 */
public class RulesResultSet {
    private int numOfBooks;
    private int numOfDays;

    public RulesResultSet(int numOfBooks, int numOfDays) {
        this.numOfBooks = numOfBooks;
        this.numOfDays = numOfDays;
    }

    public int getNumOfBooks() {
        return numOfBooks;
    }

    public void setNumOfBooks(int numOfBooks) {
        this.numOfBooks = numOfBooks;
    }

    public int getNumOfDays() {
        return numOfDays;
    }

    public void setNumOfDays(int numOfDays) {
        this.numOfDays = numOfDays;
    }
}