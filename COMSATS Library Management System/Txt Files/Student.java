/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objectmodelpackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

// Inheritance and encapsulation used
public class Student extends Institute {

    private String studentName;
    private String studentId;
    private String programEnrolledIn;

    private int fine;

    private ArrayList<Book> issuedBooks;

    private boolean cmsatsLibraryCard;

    public Student() {
    }
    public Student(String studentName, String studentId, String programEnrolledIn) {
        super("COMSATS");
        this.studentName = studentName;
        this.studentId = studentId;
        this.programEnrolledIn = programEnrolledIn;
        issuedBooks=new ArrayList<>();
        
    }

    public Student(String instituteName, String studentName, String studentId, String programEnrolledIn) {
        super("COMSATS");
        this.studentName = studentName;
        this.studentId = studentId;
        this.programEnrolledIn = programEnrolledIn;

        this.cmsatsLibraryCard = false;

        //issueCard("COMSATS");

        issuedBooks = new ArrayList<>();
    }

    public int issueCard(String instituteName) {
        if (instituteName.equals("COMSATS")) {
            if (cmsatsLibraryCard) {
                return 2;
            }
            cmsatsLibraryCard = true;
            return 1;
        }

        return 0;
    }

    public boolean hasLibraryAccess(String instituteName) {
        if (instituteName.equals("COMSATS")) {
            return cmsatsLibraryCard;
        }
        return false;
    }

    public void addBookToIssueList(Book book) {
        this.issuedBooks.add(book);
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getProgramEnrolledIn() {
        return programEnrolledIn;
    }

    public ArrayList<Book> getIssuedBooks() {
        return issuedBooks;
    }

    public int getFine() {
        return fine;
    }

    public void setFine(int fine) {
        this.fine = fine;
    }
//File handling is used below
    public void addStudent() {
        FileWriter myWriter = null;
        try {
            myWriter = new FileWriter("Students.txt", true);
        } catch (IOException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        String dataToWrite = studentName + "," + studentId + "," + programEnrolledIn;

        try {

            myWriter.append(dataToWrite + "\n");
            System.out.println("Successfully wrote to the file.\tNow You are recomended to issue Library Card So that you have access to COMSATS Library.");

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
    public ArrayList<Student> displayStudents() {
        ArrayList<Student> students = new ArrayList<Student>();
        FileInputStream file = null;
        String[] data = null;
        try {
            file = new FileInputStream("Students.txt");
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                data = scan.nextLine().split(",");
                students.add(new Student(data[0], data[1], data[2]));
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
        return students;

    }
}
