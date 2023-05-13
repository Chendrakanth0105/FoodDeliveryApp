import java.util.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.security.DrbgParameters.Reseed;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class App {

    public static final String RESET = "\033[0m";
    public static final String BLACK = "\033[30m";
    public static final String RED = "\033[31m";
    public static final String GREEN = "\033[32m";
    public static final String YELLOW = "\033[33m";
    public static final String BLUE = "\033[34m";
    public static final String MAGENTA = "\033[35m";
    public static final String CYAN = "\033[36m";
    public static final String WHITE = "\033[37m";
    public static final String BOLD = "\033[1m";
    public static final String UNDERLINE = "\033[4m";
    public static final String BACKGROUND_BLACK = "\033[40m";
    public static final String BACKGROUND_RED = "\033[41m";
    public static final String BACKGROUND_GREEN = "\033[42m";
    public static final String BACKGROUND_YELLOW = "\033[43m";
    public static final String BACKGROUND_BLUE = "\033[44m";
    public static final String BACKGROUND_MAGENTA = "\033[45m";
    public static final String BACKGROUND_CYAN = "\033[46m";
    public static final String BACKGROUND_WHITE = "\033[47m";

    public static void main(String[] args) throws Exception {
        // Print a welcome message
        print();
        Header("Welcome ");
        Scanner input = new Scanner(System.in);
        System.out.println(RED+BOLD + "Choose your Interface:" + RESET);
        System.out.println(YELLOW + "a) Customer" + "\t" + "b) Delivery Agent" + "\t" + "c) Admin" + RESET);
        System.out.println(
                GREEN + "Hint: Choose 'a' for Customer, 'b' for Delivery Agent, 'c' for Admin" + RESET);
        char mode = input.nextLine().charAt(0);
        int choice;
        String[] Creds;
        switch (mode) {
            case 'a':
                char exit;
                while (true) {
                    Header("Customer Interface");
                    System.out.println(YELLOW + "1. Login" + RESET);
                    System.out.println(YELLOW + "2. Sign Up" + RESET);
                    System.out.println(YELLOW + "3. Exit" + RESET);
                    choice = Integer.parseInt(input.nextLine());
                    if (choice == 1) {
                        Header("Customer Login");
                        System.out.println();
                        System.out.println(RED + "Login:" + RESET);
                        System.out.println(BLUE + "-----" + RESET);
                        String Credentials = login();
                        Creds = Credentials.split(",");
                        if (Authenticate(Creds[0], Creds[1], "Customers")) {
                            System.out.println(BLUE+BOLD + "Login Success!!" + RESET);
                            System.out.println("");
                            System.out.println(YELLOW + "1. Change your password" + RESET);
                            System.out.println(YELLOW + "2. Order food" + RESET);
                            choice = Integer.parseInt(input.nextLine());
                            if (choice == 1) {
                                Header("Change Password");
                                System.out.println(YELLOW + "Please enter a new password:" + RESET);
                                String pass = input.nextLine();
                                if (pass.equals(Creds[1])) {
                                    System.out.println(
                                            RED+BOLD + "Sorry... you are trying to use the previous password." + RESET);
                                } else {
                                    if (updatePassword("Customers", Creds[0], pass)) {
                                        System.out.println(BLUE+BOLD + "Updated Successfully.." + RESET);
                                        System.out.println();
                                        System.out.println(YELLOW + "Do you want to exit (Y/N):" + RESET);
                                        exit = input.nextLine().charAt(0);
                                        if (exit == 'Y' || exit == 'y') {
                                            break;
                                        }
                                    }
                                }
                            }
                            if (choice == 2) {
                                Header("Menu");
                                System.out.println("");
                                displayMenu();
                                Header("Order For Your Tummyy!!!");
                                System.out.println(" ");
                                smallHeader("Order Please!!");
                                System.out.println(" ");
                                String[][] orders = placeOrder(input);
                                confirmOrder(Creds[0], orders, input);
                                System.out.println();
                                System.out.println(YELLOW+"Do you want to exit (Y/N):"+RESET);
                                exit = input.nextLine().charAt(0);
                                if (exit == 'Y' || exit == 'y') {
                                    break;
                                }
                            } else {
                                System.out.println(RED+BOLD+"Wrong choice..."+RESET);
                            }

                        } else {
                            System.out.println(RED+BOLD+"Invalid Credentials!!"+RESET);
                        }

                    } else if (choice == 2) {
                        String details;
                        Header("Customer Registration");
                        System.out.println(YELLOW+"Registration:"+RESET);
                        System.out.println(BLUE+"------------"+RESET);
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
                                System.out.println(BLUE+BOLD+"Registered Successfully!!"+RESET);
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        System.out.println(YELLOW+"Do you want to exit (Y/N):"+RESET);
                        exit = input.nextLine().charAt(0);
                        if (exit == 'Y' || exit == 'y') {
                            break;
                        }
                    } else if (choice == 3) {
                        break;
                    } else {
                        System.out.println(RED+BOLD+"Invalid Choice!!!"+RESET);
                    }
                }
                break;
            case 'b':
                while (true) {
                    Header("Agent Interface");
                    System.out.println(YELLOW+"1. Login"+RESET);
                    System.out.println(YELLOW+"2. Exit. "+RESET);
                    int opt = Integer.parseInt(input.nextLine());
                    if (opt == 1) {
                        Header("Agent Login");
                        System.out.println(YELLOW+"Login:"+RESET);
                        System.out.println(BLUE+"-----"+RESET);
                        String Credentials = login();
                        Creds = Credentials.split(",");
                        if (Authenticate(Creds[0], Creds[1], "DeliveryAgents")) {
                            System.out.println("");
                            System.out.println(BLUE+BOLD+"Login Success!!"+RESET);
                            System.out.println("");
                            System.out.println();
                            System.out.println(YELLOW+"1. Change Password"+RESET);
                            System.out.println(YELLOW+"2. Your Dashboard"+RESET);
                            choice = Integer.parseInt(input.nextLine());
                            if (choice == 1) {
                                Header("Change Password");
                                System.out.println(YELLOW+"Please enter a new password:"+RESET);
                                String pass = input.nextLine();
                                if (pass.equals(Creds[1])) {
                                    System.out.println(RED+BOLD+"Sorry... you are trying to use the previous password."+RESET);
                                } else {
                                    if (updatePassword("DeliveryAgents", Creds[0], pass)) {
                                        System.out.println(BLUE+BOLD+"Updated Successfully.."+RESET);
                                        System.out.println();
                                        System.out.println(YELLOW+"Do you want to exit (Y/N):"+RESET);
                                        exit = input.nextLine().charAt(0);
                                        if (exit == 'Y' || exit == 'y') {
                                            break;
                                        }
                                    }
                                }
                            }
                            if (choice == 2) {
                                Header("Your Deliveries");
                                int id = fetchID(Creds[0]);
                                displayDeliveries(id);
                                System.out.println();
                                System.out.println();
                                Header("Over all Customer Ratings");
                                performance_DA(id);
                                System.out.println();
                                System.out.println(YELLOW+"Do you want to exit (Y/N)"+RESET);
                                exit = input.nextLine().charAt(0);
                                if (exit == 'Y' || exit == 'y') {
                                    break;
                                }
                            } else {
                                System.out.println(RED+BOLD+"Wrong choice..."+RESET);
                            }

                        } else {
                            System.out.println(RED+BOLD+"Invalid Credentials!!"+RESET);
                        }
                    } else if (opt == 2) {
                        break;
                    } else {
                        System.out.println(RED+BOLD+"Invalid Choice!!!"+RESET);
                    }

                }

                break;
            case 'c':
                Header("Admin Interface");
                System.out.println(YELLOW+"1. Login"+RESET);
                System.out.println(YELLOW+"2. Exit"+RESET);
                int option = Integer.parseInt(input.nextLine());
                if (option == 1) {
                    Header("Admin Login");
                    System.out.println(YELLOW+"Login:"+RESET);
                    System.out.println(BLUE+"-----"+RESET);
                    String Credentials = login();
                    Creds = Credentials.split(",");
                    if (Authenticate(Creds[0], Creds[1], "Admin")) {
                        System.out.println("");
                        System.out.println(BLUE+BOLD+"Login Success!!"+RESET);
                        System.out.println("");

                        Header("Welcome " + Creds[0]);
                        Boolean loop = true;
                        while (loop == true) {
                            System.out.println(YELLOW+"Quick Actions: "+RESET);
                            System.out.println(BLUE+"-------------"+RESET);
                            System.out.println("");
                            smallHeader("Edit Operations");
                            System.out.println(RED+"ADD: "+RESET);
                            System.out.println(YELLOW+"1. Add an Admin "+RESET);
                            System.out.println(YELLOW+"2. Hire a Delivery Agent"+RESET);
                            System.out.println(YELLOW+"3. Add a new dish"+RESET);
                            System.out.println("");
                            System.out.println(RED+"Delete:"+RESET);
                            System.out.println(YELLOW+"4. Remove an Admin"+RESET);
                            System.out.println(YELLOW+"5. Fire a Delivery Agent"+RESET);
                            System.out.println(YELLOW+"6. Remove a dish"+RESET);
                            System.out.println("");
                            smallHeader("Other Operations");
                            System.out.println(YELLOW+"7. Display Dashboard"+RESET);
                            System.out.println(YELLOW+"8. Exit"+RESET);
                            System.out.println(BLUE+"Hint: Choose 1, 2, 3, 4, 5, 6, 7 or 8..."+RESET);
                            option = Integer.parseInt(input.nextLine());
                            String UserName, Password, EMail, MobileNumber, query, item, description;
                            int price;

                            switch (option) {
                                case 1:
                                    Header("Adding Admin");
                                    System.out.println(YELLOW+"Registration"+RESET);
                                    System.out.println(BLUE+"------------"+RESET);
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
                                            System.out.println(BLUE+BOLD+"Registration Successfull!!"+RESET);
                                            System.out.println("");
                                        }
                                    } catch (Exception e) {
                                        System.out.println(RED+BOLD+e+RESET);
                                    }
                                    break;
                                case 2:
                                    Header("Hiring an Employee");
                                    System.out.println(YELLOW+"Registration"+RESET);
                                    System.out.println(BLUE+"------------"+RESET);
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
                                            System.out.println(BLUE+BOLD+"Registration Successfull!!"+RESET);
                                            System.out.println("");
                                        }
                                    } catch (Exception e) {
                                        System.out.println(RED+BOLD+e+RESET);
                                    }
                                    query = "INSERT INTO AgentPerformance(Agent_Id,Overall_Rating) values('" + Agent_Id
                                            + "',NULL)";
                                    try {
                                        execute(query);
                                    } catch (Exception e) {
                                        System.out.println(RED+BOLD+e+RESET);
                                    }
                                    break;
                                case 3:
                                    Header("Adding a New Dish!!");
                                    System.out.println("");
                                    values = addMenu(input);
                                    String[] c = values.split(",");
                                    item = c[0];
                                    description = c[1];
                                    price = Integer.parseInt(c[2]);
                                    query = "INSERT INTO Menu (Item, Description, Price, Rating) VALUES ('"
                                            + item + "','" + description + "','" + price + "','')";
                                    try {
                                        Boolean success = execute(query);
                                        if (success == true) {
                                            System.out.println("");
                                            System.out.println(BLUE+BOLD+"Added Successfull!!"+RESET);
                                            System.out.println("");
                                        }
                                    } catch (Exception e) {
                                        System.out.println(RED+BOLD+e+RESET);
                                    }
                                    break;
                                case 4:
                                    Header(" Remove an Admin");
                                    System.out.println(RED+"Removing Admin Access:"+RESET);
                                    System.out.println(BLUE+"---------------------"+RESET);
                                    UserName = remove(input);
                                    query = "DELETE FROM Admin WHERE UserName = '" + UserName + "'";
                                    try {
                                        Boolean success = execute(query);
                                        if (success == true) {
                                            System.out.println("");
                                            System.out.println(BLUE+BOLD+"Deactivated Admin Successfull!!"+RESET);
                                            System.out.println("");
                                        }
                                    } catch (Exception e) {
                                        System.out.println(RED+BOLD+e+RESET);
                                    }

                                    break;
                                case 5:
                                    Header("Fire a Delivery Agent");
                                    System.out.println(RED+"Agent Details:"+RESET);
                                    System.out.println(BLUE+"-------------"+RESET);
                                    UserName = remove(input);
                                    int id = 0;
                                    query = "SELECT Agent_Id FROM DeliveryAgents WHERE UserName = '"+UserName+"'";
                                    int r = 0;
                                    try {
                                        ResultSet resultset = executeQuery(query);
                                        while (resultset.next()) {
                                            id = resultset.getInt(1);
                                            r = r + 1;
                                        }
                                        resultset.close();
                                    } catch (Exception e) {
                                        System.out.println(RED+BOLD+e+RESET);
                                    }
                                    query = "DELETE FROM AgentPerformance WHERE Agent_Id = '" + id + "'";
                                    try {
                                        execute(query);
                                    } catch (Exception e) {
                                        System.out.println(RED+BOLD+e+RESET);
                                    }
                                    query = "DELETE FROM DeliveryAgents WHERE UserName = '" + UserName + "'";
                                    try {
                                        Boolean success = execute(query);
                                        if (success == true) {
                                            System.out.println("");
                                            System.out.println(BLUE+BOLD+"Fired " + UserName + " Successfull!!"+RESET);
                                            System.out.println("");
                                        }
                                    } catch (Exception e) {
                                        System.out.println(RED+BOLD+e+RESET);
                                    }
                                    break;

                                case 6:
                                    Header("Remove a Dish");
                                    System.out.println(RED+"Agent Details:"+RESET);
                                    System.out.println(BLUE+"-------------"+RESET);
                                    item = removeMenu(input);
                                    query = "DELETE FROM Menu WHERE Item = '" + item + "'";
                                    try {
                                        Boolean success = execute(query);
                                        if (success == true) {
                                            System.out.println("");
                                            System.out.println(BLUE+BOLD+"Deleted " + item + " Successfull!!"+RESET);
                                            System.out.println("");
                                        }
                                    } catch (Exception e) {
                                        System.out.println(RED+BOLD+e+RESET);
                                    }
                                    break;
                                case 7:
                                    Header("Dashboard");
                                    System.out.println();
                                    System.out.println(YELLOW+"1. Detailed Information"+RESET);
                                    System.out.println(YELLOW+"2. Analytics"+RESET);
                                    option = Integer.parseInt(input.nextLine());
                                    if (option == 1) {
                                        smallHeader("Delivery Agent Details");
                                        displayDA();
                                        smallHeader("Performance Metrics");
                                        AllDAPerformances();
                                        smallHeader("Orders Placed");
                                        displayOrders();
                                    }
                                    else if(option == 2){
                                        smallHeader("ANALYTICS");
                                        analytics(input);
                                    }
                                    break;
                                case 8:
                                    loop = false;
                                    break;
                            }
                        }
                    } else {
                        System.out.println(RED+BOLD+"Invalid Credentials!!"+RESET);
                    }

                } else if (option == 2) {
                    break;
                } else {
                    System.out.println(RED+BOLD+"Invalid Choice!!!"+RESET);
                }
                break;
            default:
                System.out.println(RED+BOLD+"Invalid Choice!!"+RESET);
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
            System.out.println(RED+BOLD+e+RESET);
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
            System.out.println(RED+BOLD+e+RESET);
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
            System.out.println(RED+BOLD+e+RESET);
        }

        return null;
    }

    // -----------------------------------------------------------------------PRINT--------------------------------------------------------------------------------------
    static void print() {
        System.out.println(" ");
        System.out.println(BLUE+BACKGROUND_WHITE+"+----------------------------------------------------------------------------------------------------------------------------------------------------+"+RESET+"");
        System.out.println(BLUE+BACKGROUND_WHITE+"+----------------------------------------------------------------------------------------------------------------------------------------------------+"+RESET+"");
        System.out.println(BLUE+BACKGROUND_WHITE+"||"+"                                                                  "+BACKGROUND_BLACK+WHITE+"HealthyDOOR"+YELLOW+":)"+BACKGROUND_WHITE+""+"                                                                   ||"+BLUE+RESET);
        System.out.println(BLUE+BACKGROUND_WHITE+"+----------------------------------------------------------------------------------------------------------------------------------------------------+"+RESET+"");
        System.out.println(BLUE+BACKGROUND_WHITE+"+----------------------------------------------------------------------------------------------------------------------------------------------------+"+RESET+"\n");
        System.out.println("");
    }

    static void Header(String value) {
        System.out.println(
                BLUE + "+-------------------------------------------------------------------------------------------------------------------------+"
                        + RESET);
        System.out.print("                                                           ");
        System.out.printf("%2s", RED+BOLD + value + RESET);
        System.out.print("                                                          \n");
        System.out.println(
                BLUE + "+-------------------------------------------------------------------------------------------------------------------------+\n"
                        + RESET);

    }

    static void smallHeader(String value) {
        System.out.println(
                BLUE + "+-------------------------------------------------------------+" + RESET);
        System.out.print("|                     ");
        System.out.printf("%1s", RED + value + RESET);
        System.out.print("                                                          \n");
        System.out.println(
                BLUE + "+-------------------------------------------------------------+\n" + RESET);

    }

    static void displayMenu() {
        String query = "SELECT * FROM Menu";
        try {
            System.out.println(" ");
            ResultSet resultset = executeQuery(query);
            if (resultset.next()) {
                System.out.printf(BLUE+"|%-20s| %-80s| %8s| %7s|\n", "Item", "Description", "Price", "Rating"+RESET);
                System.out.println(
                        BLUE + "+-------------------------------------------------------------------------------------------------------------------------+"
                                + RESET);
                do {
                    System.out.printf(BLUE+"|%-20s| %-80s| %8s| %7s|\n", resultset.getString(1), resultset.getString(2),
                            resultset.getString(3), resultset.getString(4)+RESET);
                } while (resultset.next());
            } else {
                System.out.println(BLUE+BOLD+"Restaurant Is Closed!!"+RESET);
            }
            System.out.println(" ");
            resultset.close();
        } catch (Exception e) {
            System.out.println(RED+BOLD+e+RESET);
        }
    }

    static void displayDA() {
        String query = "SELECT * FROM DeliveryAgents";
        try {
            ResultSet resultset = executeQuery(query);
            if (resultset.next()) {
                System.out.println(
                        "+-------------------------------------------------------------------------------------------------------------------------+");

                System.out.println("");
                System.out.printf(RED+"|%-10s| %-15s| %-15s| %-20s| %-15s| %-10s| %10s|\n", "UserName", "Password",
                        "MobileNumber",
                        "EMail", "DateOfJoining", "Salary", "Agent_Id"+RESET);
                System.out.println(
                        BLUE+"+-------------------------------------------------------------------------------------------------------------------------+"+RESET);
                do {
                    System.out.printf(GREEN+"|%-10s| %-15s| %-15s| %-20s| %-15s| %-10s| %10s|\n", resultset.getString(1),
                            resultset.getString(2),
                            resultset.getString(3), resultset.getString(4), resultset.getString(5),
                            resultset.getString(6), resultset.getString(7)+RESET);
                } while (resultset.next());
                System.out.println(
                        BLUE+"+-------------------------------------------------------------------------------------------------------------------------+"+RESET);

            } else {
                System.out.println(RED+BOLD+"No Employees"+RESET);
            }
            System.out.println(" ");
            resultset.close();
        } catch (Exception e) {
            System.out.println(RED+BOLD+e+RESET);
        }
    }

    static void analytics(Scanner input){
        String query = "SELECT COUNT(*) AS total_rows FROM orders";
        int total_orders = 0;
        int i = 0;
        String[] items = new String[100];
        String[] count = new String[100];
        try{
            ResultSet resultset = executeQuery(query);
            while(resultset.next()){
                total_orders = resultset.getInt(1);
            }
        }catch(Exception e){
            System.out.println(RED+BOLD+e+RESET);
        }

        System.out.println("");
        System.out.println(YELLOW+"Total number of orders recieved are : "+RESET);
        System.out.println(total_orders);
        System.out.println();

        query = "SELECT AMOUNT FROM Orders";
        int total_Amount = 0;
        try{
            ResultSet resultset = executeQuery(query);
            while(resultset.next()){
                total_Amount += resultset.getInt(1);
            }
        }catch(Exception e){
            System.out.println(RED+BOLD+e+RESET);
        }

        System.out.println("");
        System.out.println(YELLOW+"Total Revenue generated : "+RESET);
        System.out.println(total_Amount);
        System.out.println();

        query = "SELECT item, COUNT(*) AS item_count FROM (SELECT TRIM(SUBSTRING_INDEX(SUBSTRING_INDEX(Ordered_Items, ',', numbers.n), ',', -1)) AS item FROM  (SELECT 1 + a.N + b.N * 10 AS n  FROM  (SELECT 0 AS N UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL  SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS a         CROSS JOIN         (SELECT 0 AS N UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL         SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS b     ) numbers     INNER JOIN `orders` ON CHAR_LENGTH(Ordered_Items) - CHAR_LENGTH(REPLACE(Ordered_Items, ',', '')) >= numbers.n - 1 ) items GROUP BY item;";
        try{
            ResultSet resultset = executeQuery(query);
            while(resultset.next()){
                 items[i] = resultset.getString(1);
                 count[i] = resultset.getString(2);
                 i = i+1;
            }
        }catch(Exception e){
            System.out.println(RED+BOLD+e+RESET);
        }
        System.out.println("");
        System.out.println("Count of Items ordered : ");
        System.out.println();
        System.out.println("+------------------------+");
        System.out.printf(BLUE+"|%-15s| %-5s\n","Items","Count"+RESET);
        System.out.println("+------------------------+");
        for(int j = 0; j<i; j++){
            System.out.printf(BLUE+"|%-15s| %-5s\n",items[j],count[j]+RESET);
        }
        System.out.println("+------------------------+");
    }

    static void displayOrders() {
        String query = "SELECT * FROM Orders";
        try {
            System.out.println(" ");
            ResultSet resultset = executeQuery(query);
            if (resultset.next()) {
                System.out.println(
                        BLUE+"+-------------------------------------------------------------------------------------------------------------------------------------------+"+RESET);

                System.out.printf(RED+"|%-20s| %-20s| %-30s| %-20s| %-20s| %-20s|\n", "Order_Id", "Customer",
                        "Ordered_Items", "Amount", "Agent_Id", "DeliveryStatus"+RESET);
                System.out.println(
                        BLUE+"+-------------------------------------------------------------------------------------------------------------------------------------------+"+RESET);
                do {
                    System.out.printf(GREEN+"|%-20s| %-20s| %-30s| %-20s| %-20s| %-20s|\n", resultset.getString(1),
                            resultset.getString(2),
                            resultset.getString(3),
                            resultset.getString(4),
                            resultset.getString(5),
                            resultset.getString(6)+RESET);
                } while (resultset.next());
                System.out.println(
                        BLUE+"+-------------------------------------------------------------------------------------------------------------------------------------------+"+RESET);

            } else {
                System.out.println(RED+BOLD+"No Orders Placed!!"+RESET);
            }
            System.out.println(" ");
            resultset.close();
        } catch (Exception e) {
            System.out.println(RED+BOLD+e+RESET);
        }
    }

    static void displayDeliveries(int agentid) {
        String query = "SELECT * FROM Orders WHERE Agent_Id = '" + agentid + "'";
        try {
            ResultSet resultset = executeQuery(query);
            if (resultset.next()) {
                System.out.println(
                        BLUE+"+-----------------------------------------------------------------------------------------------------------------------------+"+RESET);

                System.out.printf(RED+"|%-20s| %-20s| %-40s| %-10s| %-10s| %-20s|\n", "Order_Id", "Customer",
                        "Ordered_Items", "Amount", "Agent_Id", "DeliveryStatus"+RESET);
                System.out.println(
                        BLUE+"+-----------------------------------------------------------------------------------------------------------------------------+"+RESET);
                do {
                    System.out.printf(GREEN+"|%-20s| %-20s| %-40s| %-10s| %-10s| %-20s|\n", resultset.getString(1),
                            resultset.getString(2),
                            resultset.getString(3), resultset.getString(4), resultset.getString(5),
                            resultset.getString(6)+RESET);
                } while (resultset.next());
                System.out.println(
                        BLUE+"+-----------------------------------------------------------------------------------------------------------------------------+"+RESET);

            }
        } catch (Exception e) {
            System.out.println(RED+BOLD+e+RESET);
        }
    }

    static void performance_DA(int id) {
        System.out.println();
        String query = "SELECT * FROM AgentPerformance WHERE Agent_Id = '" + id + "'";
        try {
            ResultSet resultset = executeQuery(query);
            if (resultset.next()) {
                System.out.println(
                        BLUE+"+-----------------------------------------------+"+RESET);
                System.out.printf(RED+"|%-20s| %-20s|\n", "Agent_Id", "Overall_Rating"+RESET);
                System.out.println(
                        BLUE+"+-----------------------------------------------+"+RESET);
                do {
                    System.out.printf(GREEN+"|%-20s| %-20s|\n", resultset.getString(1), resultset.getString(2)+RESET);
                } while (resultset.next());
                System.out.println(
                        BLUE+"+-----------------------------------------------+"+RESET);
            }
        } catch (Exception e) {
            System.out.println(RED+BOLD+e+RESET);
        }
    }

    static void AllDAPerformances() {
        String query = "SELECT da.Agent_Id, da.UserName, ap.Overall_Rating FROM DeliveryAgents da JOIN AgentPerformance ap ON da.Agent_Id = ap.Agent_Id;";
        try {
            ResultSet resultset = executeQuery(query);
            if (resultset.next()) {
                System.out.println(
                        BLUE+"+-----------------------------------------------------------------------+"+RESET);
                System.out.printf(RED+"|%-20s| %-20s| %-30s|\n", "Agent_Id", "Agent Name", "Overall_Rating"+RESET);
                System.out.println(
                        BLUE+"+-----------------------------------------------------------------------+"+RESET);
                do {
                    System.out.printf(GREEN+"|%-20s| %-20s| %-30s|\n", resultset.getString(1), resultset.getString(2),
                            resultset.getString(3)+RESET);
                } while (resultset.next());
                System.out.println(
                        BLUE+"+-----------------------------------------------------------------------+"+RESET);
            }
        } catch (Exception e) {
            System.out.println(RED+BOLD+e+RESET);
        }
    }

    // ---------------------------------------------------------------LOGIN_&_AUTHENICATION-------------------------------------------------------------------------
    static String login() {
        System.out.println();
        Scanner input = new Scanner(System.in);
        System.out.print(YELLOW+"Username: "+RESET);
        String username = input.nextLine();
        System.out.print(YELLOW+"Password: "+RESET);
        String password = input.nextLine();
        System.out.println();
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
            System.out.println(RED+BOLD + e.getMessage() + RESET);
        }
        return login;
    }
    // --------------------------------------------------------------REGISTRATION------------------------------------------------------------------------------------

    static String register_customer(Scanner input) {
        String username, password, email, mobilenumber;
        System.out.println(YELLOW+"Choose your username"+RESET);
        username = input.nextLine();
        System.out.println(YELLOW+"Enter password: "+RESET);
        password = input.nextLine();
        System.out.println(YELLOW+"Enter your mobilenumber: "+RESET);
        mobilenumber = input.nextLine();
        System.out.println(YELLOW+"Enter Email id:"+RESET);
        email = input.nextLine();
        return username + "," + password + "," + mobilenumber + "," + email;
    }

    static String register_DA(Scanner input) {
        String username, password, email, mobilenumber;
        String DateOfJoining;
        int Salary, Agent_Id;
        System.out.println(YELLOW+"Choose your username"+RESET);
        username = input.nextLine();
        System.out.println(YELLOW+"Generate password: "+RESET);
        password = input.nextLine();
        System.out.println(YELLOW+"Enter his mobilenumber: "+RESET);
        mobilenumber = input.nextLine();
        System.out.println(YELLOW+"Enter Email id:"+RESET);
        email = input.nextLine();
        DateOfJoining = CurrentDate();
        System.out.println(YELLOW+"Enter the Salary:  "+RESET);
        Salary = Integer.parseInt(input.nextLine());
        System.out.println(YELLOW+"Generate an Agent_id:"+RESET);
        Agent_Id = Integer.parseInt(input.nextLine());
        return username + "," + password + "," + mobilenumber + "," + email + "," + DateOfJoining + "," + Salary + ","
                + Agent_Id;
    }

    static String adminReg(Scanner input) {
        String username, password;
        System.out.println(YELLOW+"Choose your username"+RESET);
        username = input.nextLine();
        System.out.println(YELLOW+"Enter password: "+RESET);
        password = input.nextLine();
        return username + "," + password;

    }

    static Boolean updatePassword(String tablename, String username, String newpassword) {
        String query = "update " + tablename + " set Password = '" + newpassword + "' where UserName = '" + username
                + "'";
        Boolean success = false;
        try {
            success = execute(query);
        } catch (Exception e) {
            System.out.println(RED+BOLD + e + RESET);
        }
        return success;
    }

    static String addMenu(Scanner input) {
        String item, description;
        int price;
        System.out.println(YELLOW+"Add the dish name: "+RESET);
        item = input.nextLine();
        System.out.println(YELLOW+"Describe the item: "+RESET);
        description = input.nextLine();
        System.out.println(YELLOW+"What is the price of the dish: "+RESET);
        price = Integer.parseInt(input.nextLine());
        return item + "," + description + "," + price;

    }

    public static String[][] placeOrder(Scanner input) {
        String item_name = "";
        String order_choice;
        String[][] Order_Details_dup = new String[15][3];
        Boolean wantToOrder = true;
        int quantity, price;
        System.out.println(YELLOW+"What do you want to order sir: "+RESET);
        int i = 0;
        while (wantToOrder == true) {
            boolean item = true;
            while (item) {
                System.out.println(YELLOW+"Dish: "+RESET);
                item_name = input.nextLine();
                String query1 = "SELECT * FROM Menu WHERE Item='" + item_name + "'";
                try {
                    ResultSet resultset1 = executeQuery(query1);
                    if (resultset1.next()) {
                        Order_Details_dup[i][0] = item_name;
                        item = false;
                    } else {
                        System.out.println(RED+BOLD+"Sorry sir, We dont serve this item!!"+RESET);
                    }
                } catch (Exception e) {
                    System.out.println(RED+BOLD+e+RESET);
                }
            }
            String query2 = "SELECT Price FROM Menu WHERE Item = '" + item_name + "'";
            try {
                ResultSet resultset2 = executeQuery(query2);
                if (resultset2.next()) {
                    price = resultset2.getInt(1);
                    System.out.println(YELLOW+"price: " + price+RESET);
                    Order_Details_dup[i][2] = String.valueOf(price);
                }
                resultset2.close();
            } catch (Exception e) {
                System.out.println(RED+BOLD+e+RESET);
            }
            System.out.println(YELLOW+"Quantity: "+RESET);
            quantity = Integer.parseInt(input.nextLine());
            Order_Details_dup[i][1] = String.valueOf(quantity);
            while (true) {
                System.out.println(YELLOW+"Anything else sir?...(yes/no)"+RESET);
                order_choice = input.nextLine();
                if (order_choice.equals("yes") || order_choice.equals("Yes") || order_choice.equals("YES")) {
                    i = i + 1;
                    break;
                } else if (order_choice.equals("no") || order_choice.equals("No") || order_choice.equals("NO")) {
                    System.out.println(BLUE+BOLD+"Thank you for Ordering sir!!!"+RESET);
                    wantToOrder = false;
                    break;
                } else {
                    System.out.println(RED+BOLD+"Sorry sir!! We couldnt Understand that"+RESET);
                }
            }
        }
        String[][] Order_Details = new String[i + 1][3];
        for (int j = 0; j <= i; j++) {
            for (int k = 0; k < 3; k++) {
                Order_Details[j][k] = Order_Details_dup[j][k];
            }
        }
        return Order_Details;
    }

    static void confirmOrder(String customer, String[][] orders, Scanner input) {
        int orderid = generateOrderId();
        String items = "";
        String[] BillDetails = new String[4];
        String[] UName = new String[100];
        int amount = 0, agentid = 0;
        for (int i = 0; i < orders.length; i++) {
            items += orders[i][0];
            if (i < orders.length - 1) {
                items += ","; // Add a comma separator if it's not the last element
            }
        }
        String[] no_of_items = items.split(",");
        int[] Rating = new int[no_of_items.length];
        for (int i = 0; i < orders.length; i++) {
            amount += (Integer.parseInt(orders[i][1])) * (Integer.parseInt(orders[i][2]));
        }

        // Fetching Agent Names:
        String query = "SELECT UserName, Agent_Id FROM DeliveryAgents";
        int r = 0;
        try {
            ResultSet resultset = executeQuery(query);
            while (resultset.next()) {
                UName[r] = resultset.getString(1);
                r = r + 1;
            }
            resultset.close();
        } catch (Exception e) {
            System.out.println(RED+BOLD+e+RESET);
        }

        // Assigning the Agent_Id And Username with its exact length array
        String[] UserName = new String[r];
        for (int k = 0; k < r; k++) {
            UserName[k] = UName[k];
        }

        // Assign an Agent to the Order
        String assignedAgent = AssignDA(UserName);

        // Find the assigned agent Id number
        query = "SELECT Agent_Id FROM DeliveryAgents WHERE UserName = '" + assignedAgent + "'";
        try {
            ResultSet resultset1 = executeQuery(query);
            while (resultset1.next()) {
                agentid = resultset1.getInt(1);
            }
            resultset1.close();
        } catch (Exception e) {
            System.out.println(RED+BOLD+e+RESET);
        }

        // Insert into orders table!
        query = "INSERT INTO Orders (Order_Id, Customer, Ordered_Items, Amount, Agent_Id, DeliveryStatus) VALUES ('"
                + orderid + "','" + customer + "','" + items + "','" + amount + "','" + agentid
                + "','Food is Being Prepared')";
        try {
            execute(query);
            System.out.println(BLUE+BOLD+"Order Placed Successfully!!"+RESET);
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("");
        System.out.println(GREEN+"Food is being prepared....."+RESET);
        sleep(15);
        System.out.println(" ");
        System.out.println(GREEN+"Wohooo!!!"+RESET);
        System.out.println(GREEN+"Food Is Prepared!!! "+RESET);
        System.out.println();
        System.out.println(GREEN+assignedAgent + " has Picked your Order..."+RESET);
        System.out.println("");
        System.out.println(GREEN+"\nHold On.........."+RESET);

        sleep(30);
        System.out.println("");
        System.out.println(BLUE+BOLD+"Your Order has been successfully delivered..!!"+RESET);
        System.out.println(YELLOW+"Thank you for Ordering!!"+RESET);
        System.out.println(" ");

        query = "UPDATE Orders SET DeliveryStatus = 'Delivered' WHERE Order_Id = '" + orderid + "'";
        try {
            execute(query);
        } catch (Exception e) {
            System.out.println(RED+BOLD+e+RESET);
        }

        // Print the Bill
        System.out.println(YELLOW+"Please pay your Bill!!"+RESET);
        System.out.println("");
        smallHeader("BILL");
        BillDetails[0] = CurrentDate();
        BillDetails[1] = String.valueOf(orderid);
        BillDetails[2] = items;
        BillDetails[3] = String.valueOf(amount);
        generateBill(BillDetails);
        System.out.println("");
        sleep(5);
        Boolean status = payment(input);
        if (status == true) {
            System.out.println(BLUE+BOLD+"Done!"+RESET);
        }
        System.out.println();
        assignRating(no_of_items, Rating, input, assignedAgent);

    }

    // --------------------------------------------------------------FIRING_AGENTS_AND_ADMIN------------------------------------------------------------------------------

    static String remove(Scanner input) {
        String username;
        System.out.print(YELLOW+"Enter the Username: "+RESET);
        username = input.nextLine();
        return username;
    }

    static String removeMenu(Scanner input) {
        String item;
        System.out.print(YELLOW+"Enter the dish name you want to remove: "+RESET);
        item = input.nextLine();
        return item;
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
            System.out.println(RED+BOLD+e+RESET);
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
            System.out.println(RED+BOLD+e+RESET);
        }
        return null;
    }

    // ------------------------------------------------------------GENERATING_FUNCTIONS-----------------------------------------------------------------------------------------

    public static String CurrentDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);
        return formattedDate;
    }

    public static int generateOrderId() {
        Random random = new Random();
        int orderId = random.nextInt(1000000); // Change the limit as per your requirement
        return orderId;
    }

    public static Boolean payment(Scanner input) {
        String pay = "not done";
        Boolean Status = false;
        while (pay.equals("not done")) {
            System.out.println();
            smallHeader("Payment Options");
            System.out.println(YELLOW+"1. Cash"+RESET);
            System.out.println(YELLOW+"2. Gpay/Phonepe"+RESET);
            System.out.println(YELLOW+"3. Card (Currently Unavailable!)"+RESET);
            System.out.println();
            System.out.println(YELLOW+"Enter your option"+RESET);
            int choice = Integer.parseInt(input.nextLine());
            if (choice == 1) {
                System.out.println();
                System.out.println(YELLOW+"Please pay the cash: "+RESET);
                sleep(5);
                pay = "done";
                Status = true;
            } else if (choice == 2) {
                System.out.println();
                System.out.println(YELLOW+"Please pay to the number '7978898712' "+RESET);
                sleep(5);
                pay = "done";
                Status = true;
            } else if (choice == 3) {
                sleep(5);
                System.out.println(RED+BOLD+"Sorry!"+RESET);
                System.out.println(RED+BOLD+"This option is unavailable at the moment...."+RESET);
                System.out.println(RED+BOLD+"Kindly choose other payment option..."+RESET);
            }
        }
        return Status;
    }

    public static void generateBill(String[] data) {
        String[] headers = { "Current Date", "Order ID", "Items", "Amount" };
        int[] columnWidths = { 15, 10, 50, 10 };

        // Print headers
        printSeparatorLine(columnWidths);
        for (int i = 0; i < headers.length; i++) {
            System.out.printf(BLUE+"| %-" + columnWidths[i] + "s ", centerAlign(headers[i], columnWidths[i])+RESET);
        }
        System.out.println(BLUE+"|"+RESET);

        printSeparatorLine(columnWidths);

        // Print data row
        for (int i = 0; i < data.length; i++) {
            System.out.printf(BLUE+"| %-" + columnWidths[i] + "s ", centerAlign(data[i], columnWidths[i])+RESET);
        }
        System.out.println(BLUE+"|"+RESET);

        printSeparatorLine(columnWidths);
    }

    private static String centerAlign(String text, int width) {
        int padding = width - text.length();
        int leftPadding = padding / 2;
        int rightPadding = padding - leftPadding;
        return " ".repeat(leftPadding) + text + " ".repeat(rightPadding);
    }

    private static void printSeparatorLine(int[] columnWidths) {
        for (int width : columnWidths) {
            System.out.print(BLUE+"+" + "-".repeat(width + 2)+RESET);
        }
        System.out.println(BLUE+"+"+RESET);
    }

    public static String AssignDA(String[] agents) {
        Random random = new Random();
        int randomIndex = random.nextInt(agents.length);
        String randomAgent = agents[randomIndex];
        return randomAgent;
    }

    static void assignRating(String[] numberOfItems, int[] Rating, Scanner input, String agent) {
        System.out.println();
        String query;
        System.out.println(
                YELLOW+"We want one minute of your precious time for rating the food... Which will help us to serve you better!!"+RESET);
        for (int i = 0; i < numberOfItems.length; i++) {
            System.out.println(YELLOW+"How was " + numberOfItems[i] + " out of 5:"+RESET);
            Rating[i] = Integer.parseInt(input.nextLine());
        }
        System.out.println(YELLOW+"How do you rate our Agent " + agent+RESET);
        int agentRating = Integer.parseInt(input.nextLine());
        sleep(2);
        System.out.println(BLUE+BOLD+"Thank you for Ordering...."+RESET);
        for (int i = 0; i < numberOfItems.length; i++) {
            query = "UPDATE Menu SET Rating = " + String.valueOf(Rating[i]) + " WHERE Item = '" + numberOfItems[i]
                    + "'";
            try {
                execute(query);
            } catch (Exception e) {
                System.out.println(RED+BOLD+e+RESET);
            }
        }
        int agentid = fetchID(agent);
        query = "UPDATE AgentPerformance SET Overall_Rating = " + agentRating + " WHERE Agent_Id = '" + agentid + "'";
        try {
            execute(query);
        } catch (Exception e) {
            System.out.println(RED+BOLD+e+RESET);
        }
    }
    // -----------------------------------------------------------------SLEEP---------------------------------------------------------------------------------------

    public static void sleep(int sleepDuration) {
        sleepDuration = sleepDuration * 1000;
        int loadingInterval = 500; // 0.5 seconds
        int numDots = sleepDuration / loadingInterval;

        System.out.println( GREEN+"Loading..."+RESET);

        for (int i = 0; i < numDots; i++) {
            System.out.print(BLUE+"."+RESET);
            System.out.flush(); // Flush the output to ensure immediate printing
            try {
                Thread.sleep(loadingInterval);
            } catch (InterruptedException e) {
                System.out.println(RED+BOLD+e+RESET);
            }
        }

    }

    // -----------------------------------------------------------------Fetch_Agent_Id-----------------------------------------------------------------------------

    static int fetchID(String UserName) {
        int id = 0;
        String query = "select Agent_Id from DeliveryAgents where UserName = '" + UserName + "'";
        try {
            ResultSet resultset = executeQuery(query);
            if (resultset.next()) {
                id = resultset.getInt(1);
            }
            resultset.close();
            return id;
        } catch (Exception e) {
            System.out.println(RED+BOLD+e+RESET);
        }
        return 0;
    }

} // ---------------------------------------------------------------------------------------------------------------------------------------------------
