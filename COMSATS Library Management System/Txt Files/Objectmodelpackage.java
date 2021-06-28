/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objectmodelpackage;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Objectmodelpackage {
 private static final int perDayFine = 10;

    private static ArrayList<Student> students;
    private static Library comsatsLibrary;
    private static ArrayList<Books> booksArrayList;
    static Scanner scan=new Scanner(System.in);

    private static boolean outOfStock = false;

    public static void main(String[] args) {

        students = new ArrayList<>();

        comsatsLibrary = new Library("COMSATS");
        Books books=new Books();
        books.setBooks(new Book().displayBooks());
        comsatsLibrary.addBookToLibrary(books);
        booksArrayList=comsatsLibrary.getBooksArrayList();
                

       students=new Student().displayStudents();

        while(true) {

            Scanner reader = new Scanner(System.in);

            System.out.println("-------------------------------------");
            System.out.println();
            System.out.println("Welcome to COMSATS Library Management System");
            System.out.println("1. Display all students");
            System.out.println("2. Display all books");
            System.out.println("3. Issue Library Card");
            System.out.println("4. Issue book");
            System.out.println("5. Return book");
            System.out.println("6. Add Student");
            System.out.println("7. Add Book");
            System.out.println("8. My Account");            
            System.out.println("9. Exit");

            try {

                boolean nextInt = reader.hasNextInt();

                int option = 0;
                if(nextInt) {
                    option = reader.nextInt();
                }

                switch (option) {
                    case 1:
                        printStudentList(students);
                        break;
                    case 2:
                        displayAllBooks(comsatsLibrary);
                        break;

                    case 3:
                        issueLibraryCard();
                        break;

                    case 4:
                        issueBook(comsatsLibrary);
                        break;

                    case 5:
                        returnBook();
                        break;

                    case 6:
                        addStudents();
                        break;

                    case 7:
                        System.out.println("Enter the name of book: ");
                        String bookName=scan.nextLine();
                        System.out.println("Enter the Author of book: ");
                        String bookAuthor=scan.nextLine();
                        addBooksToLibrary(comsatsLibrary,bookName, bookAuthor);
                        break;
                        
                    case 8:
                        showMyAccount();
                        break;
                    
                    case 9:
                        System.exit(1);
                        break;    

                    default:
                        System.out.println("Please enter a valid option");
                }

            } catch (InputMismatchException e) {
                System.out.println("Enter a valid option");
                reader.close();
            }
        }
    }

    private static void showMyAccount() {
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter your studentId: ");
        try {
            String accountId = reader.nextLine();
            Student student = findStudent(accountId);
            if(student!= null) {
                System.out.println("Welcome "+student.getStudentName());
                System.out.println("Registered Student Id: "+student.getStudentId());
                System.out.println("Institute Name: "+student.getInstituteName());
                System.out.println("Program Enrolled In: "+student.getProgramEnrolledIn());
                ArrayList<Book> bookList = student.getIssuedBooks();
                System.out.println();
                if(bookList.size() > 0) {
                    System.out.println("Your issued Books-");
                    for(Book book: bookList) {
                        calculateFine(student, book.getBorrowDate(), book.getReturnDate());
                        System.out.println("Name: "+book.getBookName() + "   Author: " + book.getBookAuthor()+"   Issued Date: "+book.getBorrowDate()+"   Return Date: "+book.getReturnDate()+"   Fine: "+student.getFine());
                    }
                }
                else {
                    System.out.println("Your cart is empty");
                }
                System.out.println("Total fine pending: "+student.getFine());
            }
            else {
                System.out.println("Student not found");
            }
        } catch (Exception e) {
            System.out.println("Enter a valid studentId.");
        }
    }

    private static void calculateFine(Student student, String borrowDate, String returnDate) {

        String todaysDate = "26/06/2021";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate borDate = LocalDate.parse(returnDate, formatter);
            LocalDate retDate = LocalDate.parse(todaysDate, formatter);
            long days = ChronoUnit.DAYS.between(retDate, borDate);

            if (days < 0) {
                int numOfDays = (int) Math.abs(days);
                System.out.println("numOfDays: "+numOfDays);
                int totalFine = student.getFine() + numOfDays*perDayFine;
                student.setFine(totalFine);
            }
        } catch (Exception e) {
            System.out.println("Error parsing dates"+e.getMessage()+"\n"+e);
        }
    }

    private static void returnBook() {
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter your studentId: ");
        try {
            String accountId = reader.nextLine();
            Student student = findStudent(accountId);
            if(student!= null) {
                ArrayList<Book> bookList = student.getIssuedBooks();
                System.out.println();
                System.out.println("Welcome "+student.getStudentName());
                if(bookList.size() > 0) {
                    System.out.println("Your issued Books-");
                    for(Book book: bookList) {
                        calculateFine(student, book.getBorrowDate(), book.getReturnDate());
                        System.out.println("Name: "+book.getBookName() + "   Author: " + book.getBookAuthor()+"   Issued Date: "+book.getBorrowDate()+"   Return Date: "+book.getReturnDate()+"   Fine: "+student.getFine());
                    }
                    System.out.println("Enter book name to return-");
                    try {
                        String returnBookName = reader.nextLine();
                        boolean bookExits =false;
                        Book foundBook = null;
                        for(Book book: bookList) {
                            if(book.getBookName().equalsIgnoreCase(returnBookName)) {
                                bookExits = true;
                                foundBook = book;
                            }
                        }
                        if(bookExits) {
                            bookList.remove(foundBook);
                            foundBook.setPendingReturn(false);
                            System.out.println("Book returned successfully");
                        }
                        else {
                            System.out.println("Book not found");
                        }

                    } catch (Exception e) {
                        System.out.println("Enter a valid book name.");
                    }
                }
                else {
                    System.out.println("You haven't issued any books");
                }
            }
            else {
                System.out.println("Student not found");
            }
        } catch (Exception e) {
            System.out.println("Enter a valid studentId.");
        }
    }

    private static void issueLibraryCard() {
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter Library Name (COMSATS):");

        try {
            String libraryName = reader.nextLine();
            if(libraryName.equalsIgnoreCase("COMSATS")) {
                System.out.println("Enter your StudentId: ");
                try {
                    String issuee = reader.nextLine();
                    Student student = findStudent(issuee);
                    if(student != null) {
                        int status = student.issueCard(libraryName.toUpperCase());
                        switch (status){
                            case 0:
                                System.out.println("Error issuing library card. Sorry for inconvenience. Try again later");
                                break;
                            case 1:
                                System.out.println("Library Card Issued");
                                break;
                            case 2:
                                System.out.println("Library card already issued earlier");
                                break;
                        }
                    }
                    else
                        System.out.println("Student not found");
                } catch (Exception e) {
                    System.out.println("Enter a valid studentId");
                }
            }
            else {
                System.out.println("Enter a valid library name(COMSATS).");
            }
        } catch (Exception e) {
            System.out.println("Enter a valid library name(COMSATS).");
        }
    }

    private static void issueBook(Library comsatsLibrary) {
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter Book Name: ");
        String getBookName = reader.nextLine();
        String bookLocation;

        Book bookFound = checkBookAvailable(comsatsLibrary, getBookName);
            if(bookFound != null) {
                System.out.println("Book Found in COMSATS library");
                bookLocation = "COMSATS";
            }
            else {
                if(outOfStock) {
                    System.out.println("Book out of stock");
                }
                else {
                    System.out.println("Book not available in the library");
                }
                return;
            }
        System.out.println("Enter your studentId: ");
        String borrowerStudentId = reader.nextLine();
        Student student = findStudent(borrowerStudentId);

        if(student != null) {

            System.out.println("Student studies in "+student.getInstituteName());

            if(student.hasLibraryAccess(bookLocation)) {
                System.out.println("Access granted");
                RulesResultSet resultSet = maxBookStudentCanIssue(bookLocation, student.getInstituteName(), student.getProgramEnrolledIn());
                if(resultSet != null) {
                    if(student.getIssuedBooks().size() < resultSet.getNumOfBooks()) {
                        bookFound.setBorrowerId(student.getStudentId());

                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Calendar c = Calendar.getInstance();
                        c.setTime(new Date());
                        bookFound.setBorrowDate(sdf.format(c.getTime()));
                        c.add(Calendar.DATE, resultSet.getNumOfDays());
                        bookFound.setReturnDate(sdf.format(c.getTime()));

                        bookFound.setPendingReturn(true);

                        student.addBookToIssueList(bookFound);

                        System.out.println("Book Issued");
                    }
                    else {
                        System.out.println("Max number of books issued");
                    }
                }
                else {
                    System.out.println("Cannot issue Book");
                }

            }
            else {
                System.out.println("You do not have access to library. You are requested to First issue Library card.");
            }
        }
        else {
            System.out.println("Student not found");
        }


    }
//static methods 
    private static RulesResultSet maxBookStudentCanIssue(String bookLocation, String instituteName, String programEnrolledIn) {
        if(bookLocation.equals("COMSATS")) {
            return comsatsLibrary.comsatsRules(instituteName, programEnrolledIn);
        }
        return null;
    }

    private static Student findStudent(String id) {
        for(Student student: students) {
            if(student.getStudentId().equals(id)) {
                return student;
            }
        }
        return null;
    }

    private static Book checkBookAvailable(Library library, String getBookName) {
        outOfStock = false;
        for(Books books : booksArrayList) {
            for(Book book : books.getBooks()) {
                if(book.getBookName().equalsIgnoreCase(getBookName)) {
                    if(!book.isPendingReturn()) {
                        return book;
                    }
                    else {
                        outOfStock = true;
                    }
                }
            }
        }
        return null;
    }

    private static void displayAllBooks(Library comsatsLibrary) {
        System.out.println("\tCOMSATS Library Book Collection");
        for(Books books : booksArrayList) {
            for(Book book : books.getBooks()) {
                System.out.print("Book Name:"+book.getBookName() + "\nAuthor:" + book.getBookAuthor());
                if(book.isPendingReturn()) {
                    System.out.println("\nAvailable: no" + "   Borrower Id:" + book.getBorrowerId() + "   Issue Date:"+book.getBorrowDate() +"   Return Date:"+book.getReturnDate()+"\n");
                }
                else {
                    System.out.println("\nAvailable: yes\n");
                }
            }
            System.out.println();
        }
        System.out.println("---------------------------------------------------------");
        System.out.println();
        

    }

    private static void addBooksToLibrary(Library library, String bookName, String bookAuthor) {
        Book book = new Book(bookName, bookAuthor);
        book.addBook();
        book.setPendingReturn(false);


        Book foundBook = checkBookAvailable(library, bookName);
        if(foundBook != null) {
            Books books = library.findBooksCollection(bookName);
            if(books != null) {
                Book newBook = new Book(foundBook);
                books.addBookToList(newBook);
                books.setNumOfCopies(books.getNumOfCopies()+1);
            }
            else {
                System.out.println("Sorry but there's a bug");
            }
        }
        else {
            Books books = new Books();
            books.setNumOfCopies(1);
            books.addBookToList(book);
            library.addBookToLibrary(books);
        }
    }


    private static void addStudents() {
            System.out.println("Enter the Name of Student: ");
            String name=scan.nextLine();
            System.out.println("Enter the Id of Student : ");
            String id=scan.nextLine();
            System.out.println("Enter the Program of Student [UG,PG,PHD]: ");
            String program=scan.nextLine();
            Student student=new Student("COMSATS",name,id,program);
            student.addStudent();
            students.add(student);   
    }

    private static void printStudentList(ArrayList<Student> students) {
        for(Student student : students) {
            System.out.println("Student Detail\nInstitute: "+student.getInstituteName() + "\tStudent Name: " + student.getStudentName() + "\nStudent ID: " + student.getStudentId() + "\tProgram(PG/UG/PHD): " + student.getProgramEnrolledIn()+"\n");
        }
    }
}