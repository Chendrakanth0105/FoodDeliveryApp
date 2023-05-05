import java.util.*;
import java.sql.Date;
import java.sql.*;

public class App {
    static Connection sqlconnect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/HealthyDOOR", "root",
                    "Chendrakanth@0105");
            return connection;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    static void print() {
        System.out.println(" ");
        System.out.println("+------------------------------------------+\n");
        System.out.println("|              HealthyDOOR :)              |\n");
        System.out.println("+------------------------------------------+\n");
        System.out.println("");
    }

    static void Header(String value) {
        System.out.println(
                "+-------------------------------------------------------------------------------------------------------------------------+");
        System.out.print("                                                           ");
        System.out.printf("%2s", value);
        System.out.print("                                                          \n");
        System.out.println(
                "+-------------------------------------------------------------------------------------------------------------------------+\n");

    }

    static String login() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter Username: ");
        String username = input.nextLine();
        System.out.println("Enter your password to login: ");
        String password = input.nextLine();
        return username + "," + password;
    }

    static String register_DA() {
        String username, password, email, mobilenumber;
        Date DateOfJoining;
        int Salary, Agent_Id;
    
        Scanner input = new Scanner(System.in);
        System.out.println("Choose your username");
        username = input.nextLine();
        System.out.println("Enter password: ");
        password = input.nextLine();
        System.out.println("Enter your mobilenumber: ");
        mobilenumber = input.nextLine();
        System.out.println("Enter Email id:");
        email = input.nextLine();
        return username + "," + password + "," + mobilenumber + "," + email;
    }

    static void displayMenu() {
        try {
            Connection connection = sqlconnect();
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery("SELECT * FROM Menu");
            while (resultset.next()) {
                System.out.printf("|%-20s| %-80s| %8s| %7s|\n", resultset.getString(1), resultset.getString(2),
                        resultset.getString(3), resultset.getString(4));
            }
            resultset.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static int countColumns(String value) {
        int length = 0;
        String tableName = value;
        String query = "SELECT COUNT(*) FROM information_schema.columns WHERE table_name = '" + tableName + "'";
        try {
            Connection connection = sqlconnect();
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(query);
            resultset.next(); // move the cursor to the first row
            length = resultset.getInt(1);

            connection.close();
            statement.close();
            resultset.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return length;
    }

    static boolean Authenticate(String enteredUsername, String enteredPassword, String tablename) {
        Boolean login = false;
        try {
            Connection connection = sqlconnect();
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery("SELECT * FROM " + tablename + " WHERE username='"
                    + enteredUsername + "' AND password='" + enteredPassword + "'");
            if (resultset.next()) {
                login = true;
            } else {
                login = false;
            }
            connection.close();
            statement.close();
            resultset.close();
            return login;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return login;
    }

    static String[] columnNames(String tablename) {
        int len = countColumns(tablename);
        String[][] names = new String[len][1];
        String[] colNames = new String[len];
        try {
            Connection connection = sqlconnect();
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(
                    "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '" + tablename + "'");
            int i = 0;
            while (resultset.next()) {
                names[i][0] = resultset.getString(1);
                i++;
            }
            connection.close();
            statement.close();
            resultset.close();
            for(int j=0; j<len; j++){
                colNames[j]=names[j][0];
            }
            return colNames;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // static void updateDatabase(String details) {
    // // int count;
    // // count = countColumns(details);
    // try {
    // Connection connection = sqlconnect();
    // Statement statement = connection.createStatement();
    // ResultSet resultset = statement.executeQuery(
    // "INSERT INTO " + tablename + " VALUES('" + enteredUsername + "" +
    // enteredPassword + "'");
    // } catch (Exception e) {
    // System.out.println(e);
    // }
    // }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------
    public static void main(String[] args) throws Exception {
        // Print a welcome message
        print();
        Header("Welcome ");
        Scanner input = new Scanner(System.in);
        System.out.println("Choose your Interface:");
        System.out.println("a) Customer" + "\t" + "b) Delivery Agent" + "\t" + "c) Admin");
        System.out.println(
                "Hint: Choose 'a' for Customer, 'b' for Delivery Agent, 'c' for Admin");
        char mode = input.nextLine().charAt(0);
        switch (mode) {
            case 'a':
                Header("Customer Interface");
                System.out.println("1. Login");
                System.out.println("2. Sign Up");
                System.out.println("3. Exit");
                int choice = Integer.parseInt(input.nextLine());
                if (choice == 1) {
                    System.out.println("");
                    System.out.println("-----------------------Customer Login-----------------------");
                    System.out.println("");
                    System.out.println("Customer Login:");
                    System.out.println("**************");
                    String Credentials = login();
                    String[] Creds = Credentials.split(",");
                    int length = countColumns("Customers");
                    if (Authenticate(Creds[0], Creds[1], "Customers")) {
                        System.out.println("Login Success!!");
                        System.out.println("");
                        Header("Menu");
                        System.out.println("");
                        System.out.printf("|%-20s| %-80s| %8s| %7s|\n", "Item", "Description", "Price", "Rating");
                        System.out.println("");
                        displayMenu();
                        Header("Order For Your Tummyy!!!");

                    } else {
                        System.out.println("Invalid Credentials!!");
                    }

                } else if (choice == 2) {
                    String details;
                    System.out.println("");
                    System.out.println("-----------------------Customer Registration-----------------------");
                    System.out.println("");
                    System.out.println("Customer Registration:");
                    System.out.println("*********************");
                    details = register();

                } else if (choice == 3) {
                    break;
                } else {
                    System.out.println("Invalid Choice!!!");
                }
                break;
            case 'b':
                Header("Agent Interface");
                System.out.println("1. Login");
                System.out.println("2. Exit. ");
                int opt = Integer.parseInt(input.nextLine());
                if (opt == 1) {
                    System.out.println("");
                    System.out.println("-----------------------Delivery Agent Login-----------------------");
                    System.out.println("");
                    System.out.println("Agent Login:");
                    System.out.println("***********");
                    String Credentials = login();
                    String[] Creds = Credentials.split(",");
                    if (Authenticate(Creds[0], Creds[1], "DeliveryAgents")) {
                        System.out.println("Login Success!!");
                        System.out.println("");
                    } else {
                        System.out.println("Invalid Credentials!!");
                    }
                } else if (opt == 2) {
                    break;
                } else {
                    System.out.println("Invalid Choice!!!");
                }
                break;
            case 'c':
                Header("Admin Interface");
                System.out.println("1. Login");
                System.out.println("2. Exit");
                int ch = Integer.parseInt(input.nextLine());
                if (ch == 1) {
                    System.out.println("");
                    System.out.println("-----------------------Admin Login-----------------------");
                    System.out.println("");
                    System.out.println("Admin Login:");
                    System.out.println("***********");
                    String Credentials = login();
                    String[] Creds = Credentials.split(",");
                    int length = countColumns("Admin");
                    if (Authenticate(Creds[0], Creds[1], "Admin")) {
                        System.out.println("Login Success!!");
                        System.out.println("");
                    } else {
                        System.out.println("Invalid Credentials!!");
                    }

                } else if (ch == 2) {
                    break;
                } else {
                    System.out.println("Invalid Choice!!!");
                }
                break;
            default:
                System.out.println("Invalid Choice!!");
        }

    }
}
