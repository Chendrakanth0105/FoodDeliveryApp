import java.util.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class App {

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
                    Header("Custommer Login");
                    System.out.println("Login:");
                    System.out.println("-----");
                    String Credentials = login();
                    String[] Creds = Credentials.split(",");
                    System.out.println(" ");
                    if (Authenticate(Creds[0], Creds[1], "Customers")) {
                        System.out.println("Login Success!!");
                        System.out.println("");
                        Header("Menu");
                        System.out.printf("|%-20s| %-80s| %8s| %7s|\n", "Item", "Description", "Price", "Rating");
                        System.out.println(
                                "+-------------------------------------------------------------------------------------------------------------------------+");
                        System.out.println("");
                        displayMenu();
                        Header("Order For Your Tummyy!!!");

                    } else {
                        System.out.println("Invalid Credentials!!");
                    }

                } else if (choice == 2) {
                    String details;
                    Header("Customer Registration");
                    System.out.println("Registration:");
                    System.out.println("------------");
                    details = register_customer(input);
                    String[] d = details.split(",");
                    String UserName = d[0];
                    String Password = d[1];
                    String MobileNumber = d[2];
                    String EMail = d[3];
                    String query = "INSERT INTO Customers (UserName, Password, MobileNumber, EMail) VALUES ('"
                            + UserName + "','" + Password + "','" + MobileNumber + "','" + EMail + "')";
                    try {
                        Boolean register = execute(query);
                        if (register == true) {
                            System.out.println("");
                            System.out.println("Registered Successfully!!");
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }

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
                    Header("Agent Login");
                    System.out.println("Login:");
                    System.out.println("-----");
                    String Credentials = login();
                    String[] Creds = Credentials.split(",");
                    if (Authenticate(Creds[0], Creds[1], "DeliveryAgents")) {
                        System.out.println("");
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
                int option = Integer.parseInt(input.nextLine());
                if (option == 1) {
                    Header("Admin Login");
                    System.out.println("Login:");
                    System.out.println("-----");
                    String Credentials = login();
                    String[] Creds = Credentials.split(",");
                    int length = countColumns("Admin");
                    if (Authenticate(Creds[0], Creds[1], "Admin")) {
                        System.out.println("");
                        System.out.println("Login Success!!");
                        System.out.println("");

                        Header("Welcome " + Creds[0]);
                        Boolean loop = true;
                        while (loop == true) {
                            System.out.println("Quick Actions: ");
                            System.out.println("-------------");
                            System.out.println("1. Add an Admin ");
                            System.out.println("2. Hire a Delivery Agent");
                            System.out.println("3. Remove an Admin");
                            System.out.println("4. Fire a Delivery Agent");
                            System.out.println("5. Display Dashboard");
                            System.out.println("6. Exit");
                            System.out.println("Hint: Choose 1, 2, 3, 4, 5 or 6...");
                            option = Integer.parseInt(input.nextLine());
                            String UserName, Password, EMail, MobileNumber, query;
                            switch (option) {
                                case 1:
                                    Header("Adding Admin");
                                    System.out.println("Registration");
                                    System.out.println("------------");
                                    String values = adminReg(input);
                                    String[] a = values.split(",");
                                    UserName = a[0];
                                    Password = a[1];
                                    query = "INSERT INTO Admin (UserName, Password) VALUES ('"
                                            + UserName + "','" + Password + "')";
                                    try {
                                        Boolean success = execute(query);
                                        if (success == true) {
                                            System.out.println("");
                                            System.out.println("Registration Successfull!!");
                                            System.out.println("");
                                        }
                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }
                                    break;
                                case 2:
                                    Header("Hiring an Agent");
                                    System.out.println("Registration");
                                    System.out.println("------------");
                                    String details = register_DA(input);
                                    String[] b = details.split(",");
                                    Date DateOfJoining;
                                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    int Salary, Agent_Id;
                                    UserName = b[0];
                                    Password = b[1];
                                    MobileNumber = b[2];
                                    EMail = b[3];
                                    java.util.Date utilDate = dateFormat.parse(b[4]);
                                    DateOfJoining = new Date(utilDate.getTime());
                                    Salary = Integer.parseInt(b[5]);
                                    Agent_Id = Integer.parseInt(b[6]);
                                    query = "INSERT INTO DeliveryAgents (UserName, Password, MobileNumber, EMail, DateOfJoining, Salary, Agent_Id) VALUES ('"
                                            + UserName + "','" + Password + "','" + MobileNumber + "','" + EMail + "','"
                                            + DateOfJoining + "','" + Salary + "','" + Agent_Id + "')";
                                    try {
                                        Boolean success = execute(query);
                                        if (success == true) {
                                            System.out.println("");
                                            System.out.println("Registration Successfull!!");
                                            System.out.println("");
                                        }
                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }
                                    break;
                                case 3:
                                    Header(" Remove an Admin");
                                    System.out.println("Removing Admin Access:");
                                    System.out.println("---------------------");
                                    UserName = removeAdmin(input);
                                    query = "DELETE FROM Admin WHERE UserName = '" + UserName + "'";
                                    try {
                                        Boolean success = execute(query);
                                        if (success == true) {
                                            System.out.println("");
                                            System.out.println("Deactivated Admin Successfull!!");
                                            System.out.println("");
                                        }
                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }

                                    break;
                                case 4:
                                    Header("Fire a Delivery Agent");
                                    System.out.println("Agent Details:");
                                    System.out.println("-------------");
                                    UserName = removeAdmin(input);
                                    query = "DELETE FROM DeliveryAgents WHERE UserName = '" + UserName + "'";
                                    try {
                                        Boolean success = execute(query);
                                        if (success == true) {
                                            System.out.println("");
                                            System.out.println("Fired "+UserName+" Successfull!!");
                                            System.out.println("");
                                        }
                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }

                                    break;
                                case 5:
                                    Header("Dashboard");
                                    System.out.println("1. List the Information");
                                    System.out.println("2. List the Total Orders");
                                    option = Integer.parseInt(input.nextLine());
                                    if(option == 1){
                                        
                                    }
                                    
                                    break;
                                case 6:
                                    loop = false;
                                    break;
                            }
                        }
                    } else {
                        System.out.println("Invalid Credentials!!");
                    }

                } else if (option == 2) {
                    break;
                } else {
                    System.out.println("Invalid Choice!!!");
                }
                break;
            default:
                System.out.println("Invalid Choice!!");
        }

    }

    // -------------------------------------------------------------------CONNECTION-----------------------------------------------------------------------------------------
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

    static Boolean execute(String query) {
        try {
            Connection connection = sqlconnect();
            Statement statement = connection.createStatement();
            statement.execute(query);
            connection.close();
            statement.close();
        } catch (Exception e) {
            System.out.println(e);
            ;
        }

        return true;
    }

    static ResultSet executeQuery(String query) {
        try {
            Connection connection = sqlconnect();
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(query);
            return resultset;
        } catch (Exception e) {
            System.out.println(e);
            ;
        }

        return null;
    }

    // -----------------------------------------------------------------------PRINT--------------------------------------------------------------------------------------
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

    static void smallHeader(String value) {
        System.out.println(
                "+-------------------------------------------------------------+");
        System.out.print("                     ");
        System.out.printf("%1s", value);
        System.out.print("                                                          \n");
        System.out.println(
                "+-------------------------------------------------------------+\n");

    }

    static void displayMenu() {
        String query = "SELECT * FROM Menu";
        try {
            ResultSet resultset = executeQuery(query);
            while (resultset.next()) {
                System.out.printf("|%-20s| %-80s| %8s| %7s|\n", resultset.getString(1), resultset.getString(2),
                        resultset.getString(3), resultset.getString(4));
            }
            resultset.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // ---------------------------------------------------------------LOGIN_&_AUTHENICATION-------------------------------------------------------------------------
    static String login() {
        Scanner input = new Scanner(System.in);
        System.out.print("Username: ");
        String username = input.nextLine();
        System.out.print("Password: ");
        String password = input.nextLine();
        return username + "," + password;
    }

    static boolean Authenticate(String enteredUsername, String enteredPassword, String tablename) {
        Boolean login = false;
        String query = "SELECT * FROM " + tablename + " WHERE username='"
                + enteredUsername + "' AND password='" + enteredPassword + "'";
        try {
            ResultSet resultset = executeQuery(query);
            if (resultset.next()) {
                login = true;

            } else {
                login = false;
            }
            return login;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return login;
    }
    // --------------------------------------------------------------REGISTRATION------------------------------------------------------------------------------------

    static String register_customer(Scanner input) {
        String username, password, email, mobilenumber;
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

    static String register_DA(Scanner input) {
        String username, password, email, mobilenumber;
        String DateOfJoining;
        int Salary, Agent_Id;
        System.out.println("Choose your username");
        username = input.nextLine();
        System.out.println("Enter password: ");
        password = input.nextLine();
        System.out.println("Enter your mobilenumber: ");
        mobilenumber = input.nextLine();
        System.out.println("Enter Email id:");
        email = input.nextLine();
        DateOfJoining = CurrentDate();
        System.out.println("Enter the Salary:  ");
        Salary = Integer.parseInt(input.nextLine());
        System.out.println("Generate an Agent_id:");
        Agent_Id = Integer.parseInt(input.nextLine());
        return username + "," + password + "," + mobilenumber + "," + email + "," + DateOfJoining + "," + Salary + ","
                + Agent_Id;
    }

    static String adminReg(Scanner input) {
        String username, password;
        System.out.println("Choose your username");
        username = input.nextLine();
        System.out.println("Enter password: ");
        password = input.nextLine();
        return username + "," + password;

    }

    // --------------------------------------------------------------FIRING_AGENTS_AND_ADMIN------------------------------------------------------------------------------

    static String removeAdmin(Scanner input) {
        String username;
        System.out.print("Enter the admin Username: ");
        username = input.nextLine();
        return username;
    }

    // --------------------------------------------------------------COUNT_COLUMNS------------------------------------------------------------------------------

    static int countColumns(String value) {
        int length = 0;
        String tableName = value;
        String query = "SELECT COUNT(*) FROM information_schema.columns WHERE table_name = '" + tableName + "'";
        try {
            ResultSet resultset = executeQuery(query);
            resultset.next(); // move the cursor to the first row
            length = resultset.getInt(1);
            resultset.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return length;
    }

    static String[] columnNames(String tablename) {
        int len = countColumns(tablename);
        String[][] names = new String[len][1];
        String[] colNames = new String[len];
        String query = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '" + tablename + "'";
        try {
            ResultSet resultset = executeQuery(query);
            int i = 0;
            while (resultset.next()) {
                names[i][0] = resultset.getString(1);
                i++;
            }
            resultset.close();
            for (int j = 0; j < len; j++) {
                colNames[j] = names[j][0];
            }
            return colNames;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // -------------------------------------------------------------------DATE-----------------------------------------------------------------------------------------
    public static String CurrentDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);
        return formattedDate;
    }
    // ----------------------------------------------------------------------------------------------------------------------------------------------------------------

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

}
