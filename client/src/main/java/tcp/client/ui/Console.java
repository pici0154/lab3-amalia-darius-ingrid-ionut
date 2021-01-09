package tcp.client.ui;
import tcp.common.ApartmentService;
import tcp.common.BookService;
import tcp.common.ClientService;
import tcp.common.domain.Apartment;
import tcp.common.domain.Book;
import tcp.common.domain.Client;
import tcp.common.domain.validators.ValidatorException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Console {
    private BookService bookService;
    private ClientService clientService;
    private ApartmentService apartmentService;
    private Scanner scanner;

    public Console(BookService bookService, ClientService clientService,ApartmentService apartmentService) {
        this.bookService = bookService;
        this.clientService = clientService;
        this.apartmentService = apartmentService;
        this.scanner = new Scanner(System.in);
    }

    private void showMenu() {
        System.out.println("1. Book CRUD");
        System.out.println("2. Client CRUD");
        System.out.println("3. Apartment CRUD");

        System.out.println("x. Exit");
    }

    public void runConsole() throws IOException {
        showMenu();
        while (true) {
            String option = scanner.next();
            switch (option) {
                case "1":
                    runBookCRUD();
                    break;
                case "2":
                    runClientCRUD();
                    break;
                case "3":
                    runApartmentCRUD();
                    break;
                case "x":
                    return;
                default:
                    System.out.println("Invalid option!");
                    break;
            }
        }
    }

    private void runBookCRUD() throws IOException {
        while (true) {
            System.out.println();
            System.out.println("1. Add a book");
            System.out.println("2. View all books");
            System.out.println("3. Delete a book by a given id");
            System.out.println("4. Update a book");
            System.out.println("5. Back");

            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            String option = bufferRead.readLine();
            switch (option) {
                case "1":
                    addBook();
                    break;
                case "2":
                    printAllBooks();
                    break;
                case "3":
                    deleteBookById();
                    break;
                case "4":
                    updateBook();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Invalid option!");
                    break;
            }
        }
    }

    private void addBook() throws IOException{
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter id: ");
        int id = Integer.parseInt(bufferRead.readLine());
        System.out.println("Enter title: ");
        String name = bufferRead.readLine();
        System.out.println("Enter author: ");
        String author = bufferRead.readLine();
        System.out.println("Enter publisher: ");
        String publisher = bufferRead.readLine();
        System.out.println("Enter year of publication: ");
        String yearOfPublication = bufferRead.readLine();
        System.out.println("Enter price: ");
        double price = Double.parseDouble(bufferRead.readLine());

        Book book = new Book(id, name, author, publisher, yearOfPublication, price);
        book.setId(id);

        bookService.addBook(book);
    }

    private void printAllBooks() {
        Future<String> result = bookService.findAllBooks();

        try {
            String[] bookList = result.get().substring(1, result.get().length() - 1).split(", Book");

            System.out.println("Client: received result - List of books: ");

            for (String s : bookList) {
                System.out.println(s);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void deleteBookById() {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Enter id: ");
            int id = Integer.parseInt(bufferRead.readLine());

            bookService.delete(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateBook() {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.println("Enter id: ");
            int id = Integer.parseInt(bufferRead.readLine());
            System.out.println("Enter title: ");
            String name = bufferRead.readLine();
            System.out.println("Enter author: ");
            String author = bufferRead.readLine();
            System.out.println("Enter publisher: ");
            String publisher = bufferRead.readLine();
            System.out.println("Enter year of publication: ");
            String yearOfPublication = bufferRead.readLine();
            System.out.println("Enter price: ");
            double price = Double.parseDouble(bufferRead.readLine());

            Book book = new Book(id, name, author, publisher, yearOfPublication, price);
            book.setId(id);

            bookService.update(book);

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ValidatorException e) {
            e.printStackTrace();
        }
    }

    private void runClientCRUD() throws IOException {
        while (true) {
            System.out.println();
            System.out.println("1. Add a client");
            System.out.println("2. View all clients");
            System.out.println("3. Delete a client by a given id");
            System.out.println("4. Update a client");
            System.out.println("5. Back");

            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            String option = bufferRead.readLine();
            switch (option) {
                case "1":
                    addClient();
                    break;
                case "2":
                    printAllCllients();
                    break;
                case "3":
                    deleteClientById();
                    break;
                case "4":
                    updateClient();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Invalid option!");
                    break;
            }
        }
    }

    private void addClient() throws IOException {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Enter id: ");
            int id = Integer.parseInt(bufferRead.readLine());
            System.out.println("Enter name: ");
            String name = bufferRead.readLine();
            System.out.println("Enter date of registration: ");
            String dateOfRegistration = bufferRead.readLine();

            Client client = new Client(id, name, dateOfRegistration);
            client.setId(id);

            clientService.addClient(client);

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ValidatorException ex) {
            ex.printStackTrace();
        }
    }

    private void printAllCllients() {
        Future<String> result = clientService.findAllClients();

        try {
            String[] clientList = result.get().substring(1, result.get().length() - 1).split(", Client");

            System.out.println("Client: received result - List of books: ");

            for (String c : clientList) {
                System.out.println(c);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void deleteClientById() {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Enter id: ");
            int id = Integer.parseInt(bufferRead.readLine());

            clientService.delete(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("The client was deleted!");
    }

    private void updateClient() {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.println("Enter id: ");
            int id = Integer.parseInt(bufferRead.readLine());
            System.out.println("Enter name: ");
            String name = bufferRead.readLine();
            System.out.println("Enter date of registration: ");
            String dateOfRegistration = bufferRead.readLine();

            Client client = new Client(id, name, dateOfRegistration);
            client.setId(id);

            clientService.update(client);

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ValidatorException e) {
            e.printStackTrace();
        }
        System.out.println("The client was updated!");
    }
    private void runApartmentCRUD() throws IOException {
        while (true) {
            System.out.println();
            System.out.println("1. Add a Apartment");
            System.out.println("2. View all Apartments");
            System.out.println("3. Delete a Apartment by a given id");
            System.out.println("4. Update a Apartment");
            System.out.println("5. Back");

            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            String option = bufferRead.readLine();
            switch (option) {
                case "1":
                    addApartment();
                    break;
                case "2":
                    printAllApartments();
                    break;
                case "3":
                    deleteApartmentById();
                    break;
                case "4":
                    updateApartment();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Invalid option!");
                    break;
            }
        }
    }


    private void addApartment() throws IOException{
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter id: ");
        int id = Integer.parseInt(bufferRead.readLine());
        System.out.println("Enter Address: ");
        String address = bufferRead.readLine();
        System.out.println("Enter Category: ");
        String category = bufferRead.readLine();
        System.out.println("Enter Description: ");
        String description = bufferRead.readLine();
        System.out.println("Enter price: ");
        double price = Double.parseDouble(bufferRead.readLine());
        System.out.println("Enter year of construction: ");
        String yearOfconstruction = bufferRead.readLine();

        Apartment ap = new Apartment(id, address, category, description, yearOfconstruction, price);
       ap.setId(id);

        apartmentService.addApartment(ap);
    }

    private void printAllApartments() {
        Future<String> result = apartmentService.findAllApartments();

        try {
            String[] apartmentList = result.get().substring(1, result.get().length() - 1).split(", Apartment");

            System.out.println("Client: received result - List of apartments: ");

            for (String s : apartmentList) {
                System.out.println(s);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void deleteApartmentById() {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Enter id: ");
            int id = Integer.parseInt(bufferRead.readLine());

            apartmentService.deleteApartment(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateApartment() {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.println("Enter id: ");
            int id = Integer.parseInt(bufferRead.readLine());
            System.out.println("Enter Address: ");
            String address = bufferRead.readLine();
            System.out.println("Enter Category: ");
            String category = bufferRead.readLine();
            System.out.println("Enter Description: ");
            String description = bufferRead.readLine();
            System.out.println("Enter price: ");
            double price = Double.parseDouble(bufferRead.readLine());
            System.out.println("Enter year of construction: ");
            String yearOfconstruction = bufferRead.readLine();

            Apartment ap = new Apartment(id, address, category, description, yearOfconstruction, price);
            ap.setId(id);

           apartmentService.updateApartment(ap);

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ValidatorException e) {
            e.printStackTrace();
        }
    }


}