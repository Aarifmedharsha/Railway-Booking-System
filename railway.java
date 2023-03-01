import java.lang.reflect.Array;
import java.util.*;

public class railway {
    private static Scanner sc;
    private static final String train_name = "Aarif's Railway -- Coimbatore to Chennai Express";
    private static final String train_number = "ABC1234LJK";
    private static final String train_stops[] = { "Coimbatore", "Tiruppur", "Erode", "Salem", "Kancheepuram",
            "Chennai" };
    private static ArrayList<Users> users = new ArrayList<>();
    private static ArrayList<Wait> wait_list = new ArrayList<>();
    private static ArrayList<Wait> book_list = new ArrayList<>();
    private static Set<Integer> cancelled =new HashSet<>();
    private static int seats[][] = new int[10][6];
    private static int seats_fill[][] = new int[10][6];
    static int userr_id = 100, index = 0, ticket_no = 50, cancel_index = 0;
    public static void start() {
        System.out.println("--------Welocome to Aarif's Railway Ticket Booking System--------");
        System.out.println("1.Admin Login");
        System.out.println("2.User Login");
        System.out.println("3.Exit");
        System.out.println("Choose any of the options..");
        int ch = sc.nextInt();
        switch (ch) {
            case 1:
                admin();
                break;
            case 2:
                user();
                break;
            case 3:
                System.out.println("Thank you for visiting Aarif Railways!!Visit us again...");
                System.exit(0);
            default:
                System.out.print("\033[H\033[2J");
                System.out.flush();
                start();
                break;
        }
    }

    private static void admin() {
        System.out.println("Enter Admin Username");
        String a_name = sc.next();
        System.out.println("Enter Admin Password");
        String a_pass = sc.next();
        if (a_name.equals("Aarif") && a_pass.equals("1234")) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("Welcome Admin " + a_name);
            System.out.println("Train Name : " + train_name);
            System.out.println("Train Number : " + train_number);
            System.out.println();
            System.out.println("--------BOOKING LIST--------");
            System.out.printf("%-10s %-15s %-15s %-10s\n", "User_ID", "From", "To", "Ticket_no");
            for (int k = 0; k < book_list.size(); k++) {
                System.out.printf("%-10s %-15s %-15s %-10s\n", book_list.get(k).userr_id,
                        train_stops[book_list.get(k).dep_index],
                        train_stops[book_list.get(k).arr_index], book_list.get(k).ticket_no);
            }
            System.out.println();
            System.out.println("--------Train travelling cities :--------");
            for (int i = 0; i < 6; i++) {
                System.out.println(train_stops[i]);
            }
            System.out.println();
            System.out.println("--------Seat Filling--------");
            for (int l = 0; l < 10; l++) {
                for (int h = 0; h < 6; h++) {
                    System.out.printf("%-7s", seats[l][h]);
                }
                System.out.println();
            }
            System.out.println();
            System.out.println("--------WAIT LIST--------");
            System.out.printf("%-10s %-15s %-15s %-10s\n", "User_ID", "From", "To", "Ticket_no");
            for (int n = 0; n < wait_list.size(); n++) {
                System.out.printf("%-10s %-15s %-15s %-10s\n", wait_list.get(n).userr_id,
                        train_stops[wait_list.get(n).dep_index],
                        train_stops[wait_list.get(n).arr_index], wait_list.get(n).ticket_no);
            }
            System.out.println();
            System.out.println("--------Tickets that are cancelled-----------");
            if(cancelled.size()==0){
                System.out.println("No tickets were cancelled!!");
                admin();
            }
            else{
            System.out.println("Ticket Numbers");
            int c []=new int[cancelled.size()];
            int te=0;
            for(int x:cancelled){
                c[te++]=x;
            }
            for(int i=0;i<c.length;i++){
                System.out.println(c[i]);
            }
            }

        } else {
            System.out.println("Admin Name or Password incorrect");
        }
        sc.nextLine();
        System.out.flush();
        start();
    }

    private static void user() {
        System.out.println("--------Welcome Users--------");
        System.out.println("1.Log In");
        System.out.println("2.Sign up");
        System.out.println("3.Exit");
        System.out.print("Enter your choice:");
        int m = sc.nextInt();
        switch (m) {
            case 1:
                userLogin();
                break;
            case 2:
                userSignup();
                break;
            case 3:
                System.out.print("\033[H\033[2J");
                System.out.flush();
                start();
            default:
                user();
                break;
        }
    }

    private static boolean check(ArrayList<Users> users2, int l_id, String l_pass) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).userr_id == l_id && users.get(i).userr_password.equals(l_pass)) {
                index = i;
                return true;
            }
        }
        return false;
    }

    private static void userLogin() {
        System.out.print("Enter User Id:");
        int l_id = sc.nextInt();
        System.out.print("Enter User Password:");
        String l_pass = sc.next();
        if (check(users, l_id, l_pass)) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            orgUser();
        } else {
            System.out.println("Invalid ");
            System.out.println("Press enter to continue...");
            sc.nextLine();
            System.out.print("\033[H\033[2J");
            System.out.flush();
            userLogin();
        }

    }

    private static void orgUser() {
        System.out.println("Welcome " + users.get(index).userr_name);
        System.out.println("1.Book Ticket");
        System.out.println("2.View or Cancel Ticket");
        System.out.println("3.View Train Details");
        System.out.println("4.Exit");
        System.out.println("Enter Your Choice");
        int opt = sc.nextInt();
        switch (opt) {
            case 1:
                bookTicket();
                break;
            case 2:
                cancelTicket();
                break;
            case 3:
                trainDetails();
                break;
            case 4:
                user();
                break;
            default:
                orgUser();
                break;
        }
    }
    private static boolean userFound(){
        for(int k=0;k<book_list.size();k++){
            if(book_list.get(k).userr_id == users.get(index).userr_id){
                return true;
            }
        }
        return false;
    }
    private static void cancelTicket() {
        if (!userFound()) {
            System.out.println("No tickets booked!! For userID :"+users.get(index).userr_id);
            orgUser();
        } else {
            System.out.println("--------Booking Details--------");
            System.out.printf("%-10s %-15s %-15s %-10s\n", "User_ID", "From", "To", "Ticket_no");
            boolean ab = check(users, cancel_index, train_name);
            for (int k = 0; k < book_list.size(); k++) {
                if (book_list.get(k).userr_id == users.get(index).userr_id) {
                    System.out.printf("%-10s %-15s %-15s %-10s\n", book_list.get(k).userr_id,
                            train_stops[book_list.get(k).dep_index],
                            train_stops[book_list.get(k).arr_index], book_list.get(k).ticket_no);
                }
            }
            System.out.println("--------Train Seating Arrangements-------");
            for (int l = 0; l < 10; l++) {
                for (int h = 0; h < 6; h++) {
                    System.out.printf("%-7s", seats[l][h]);
                }
                System.out.println();
            }
            System.out.println("Press (1) cancel tickets..or any other number to go back");
            int can_t = sc.nextInt();
            int cancel_count = 0;
            if (can_t != 1) {
                orgUser();
            } else if (can_t == 1) {
                ArrayList<Wait> book_list1=book_list;
                System.out.println("Enter the ticket number to be cancelled");
                int t_can = sc.nextInt();
                if(cancelled.contains(t_can)){
                    System.out.println("This ticket is already cancelled!!If it is shown in booking details(It is for our side)Ticket is only booked if the ticket number is found in Seating Arrangements. ");
                    System.out.println("To continue  cancelling press(1)...or any other number to go back ");
                    int moo=sc.nextInt();
                    if(moo==1){
                        cancelTicket();

                    }
                    else{
                        orgUser();
                    }
                }
                else{
                for (int i = 0; i < book_list1.size(); i++) {
                    if (book_list1.get(i).userr_id == users.get(index).userr_id && book_list1.get(i).ticket_no == t_can) {
                        cancel_index = i;
                        cancelProcess(cancel_index);
                        for(Wait s:book_list1){
                            if(s.ticket_no==t_can){
                                book_list1.removeAll((Collection<?>) s);
                            }
                        }
                        cancel_count++;
                    }
                }
                if (cancel_count == 0) {
                    System.out.println("Please Enter Valid Ticket Number");
                    cancelTicket();
                }
                book_list=book_list1;
            }
        }
        }
        
    }

    private static void cancelProcess(int cancel_index) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 6; j++) {
                if (seats[i][j] == book_list.get(cancel_index).ticket_no) {
                    seats[i][j] = 0;
                }
            }
        }
        int rem_index = 0;
        cancelled.add(book_list.get(cancel_index).ticket_no);
        System.out.println("Ticket cancelled Succesfully...");
        if (wait_list.size() == 0) {
            orgUser();
            book_list.remove(cancel_index);
        } else {
            z: for (int w = 0; w < wait_list.size(); w++) {
                if (wait_list.get(w).dep_index >= book_list.get(cancel_index).dep_index
                        && wait_list.get(w).arr_index <= book_list.get(cancel_index).arr_index) {
                    x: for (int i = 0; i < 10; i++) {
                        int sum = 0;
                        y: for (int j = wait_list.get(w).dep_index; j < wait_list.get(w).arr_index; j++) {
                            sum += seats[i][j];
                        }
                        if (sum == 0) {
                            int wait_index = -1;
                            book_list.add(new Wait(wait_list.get(w).userr_id, wait_list.get(w).dep_index,
                                    wait_list.get(w).arr_index, ++ticket_no));
                            for (int m = 0; m < users.size(); m++) {
                                if (users.get(m).userr_id == wait_list.get(w).userr_id) {
                                    wait_index = m;
                                }
                            }
                            for (int k = wait_list.get(w).dep_index; k < wait_list.get(w).arr_index; k++) {
                                seats[i][k] = ticket_no;
                            }
                            rem_index = w;
                            book_list.remove(cancel_index);
                            wait_list.remove(rem_index);
                            break z;
                        }
                    }
                }
            }
            orgUser();
        }

    }

    private static void trainDetails() {
        System.out.println("Train Name : " + train_name);
        System.out.println("Train Number : " + train_number);
        System.out.println("Train travelling cities :");
        for (int i = 0; i < 6; i++) {
            System.out.println("------------->" + train_stops[i]);
        }
        sc.nextLine();

        orgUser();
    }

    private static int checkCity(String city) {
        for (int i = 0; i < train_stops.length; i++) {
            if (train_stops[i].equals(city))
                return i;
        }
        return -1;
    }

    public static void printOther(int city_i) {
        String cities[] = { "Coimbatore", "Tiruppur", "Erode", "Salem", "Kancheepuram", "Chennai" };
        String newCities[] = new String[cities.length - (city_i + 1)];
        System.arraycopy(cities, city_i + 1, newCities, 0, newCities.length);
        for (int i = 0; i < newCities.length; i++) {
            System.out.println(newCities[i]);
        }
    }

    private static void bookTicket() {
        System.out.println("Enter number of booking");
        int n = sc.nextInt();
        for (int s = 0; s < n; s++) {
            System.out.println("--------Seats Available to book!!(1 means booked ..0 means empty)-------");
            for (int l = 0; l < 10; l++) {
                for (int h = 0; h < 6; h++) {
                    System.out.printf("%-7s", seats_fill[l][h]);
                }
                System.out.println();
            }
            System.out.println("------------------------------------------");
            System.out.println("For ticket -->" + (s + 1));
            int s_yes = 0, d_index = -1, a_index = -1;
            System.out.println("Enter Departure station :");
            String city[] = train_stops;
            for (int m = 0; m < city.length; m++) {
                System.out.println(city[m]);
            }
            String d_st = sc.next();
            if (d_st.equals("Chennai")) {
                System.out.println("Chennai is the last stop,You cannot go anywhere from there!!");
                bookTicket();
            } else if (checkCity(d_st) != -1) {
                System.out.println(
                        "From " + d_st + " you can go to the following arrival station ,enter your Arrival station : ");
                printOther(checkCity(d_st));
            } else if (checkCity(d_st) == -1) {
                System.out.println("Enter correct station names for Coimbatore Express");
                bookTicket();
            }
            String a_st = sc.next();
            if (checkCity(a_st) == -1) {
                System.out.println("Enter correct station names for Coimbatore Express");
                bookTicket();
            } else if (d_st.equals(a_st)) {
                System.out.println("Departure station and arrival station should not be the same!!");
                bookTicket();
            } else {
                for (int i = 0; i < train_stops.length; i++) {
                    if (train_stops[i].equals(d_st)) {
                        d_index = i;
                        s_yes++;
                    }
                    if (train_stops[i].equals(a_st)) {
                        a_index = i;
                        s_yes++;
                    }
                }
                if (s_yes == 2) {
                    if(d_index>a_index){
                        System.out.println("Train only can go to the specified stations(Note : It cannot go backward)!!");
                        bookTicket();
                    }
                    else{
                    checkSeat(d_index, a_index);
                    }
                } else {
                    System.out.println("Enter correct station names for Coimbatore Express");
                    bookTicket();
                }
            }

        }
        orgUser();
    }

    private static void checkSeat(int d_index, int a_index) {
        int confirm = 0;
        x: for (int i = 0; i < 10; i++) {
            int sum = 0;
            for (int j = d_index; j < a_index; j++) {
                sum += seats[i][j];
            }
            if (sum == 0) {
                System.out.println("Seat allocated for user_ID " + users.get(index).userr_id + " Seat Number: " + i);
                System.out
                        .println("You can check with the  Seating Arrangement with ur ticket no : " + (ticket_no + 1));
                confirm++;
                book_list.add(new Wait(users.get(index).userr_id, d_index, a_index, ++ticket_no));
                for (int k = d_index; k < a_index; k++) {
                    seats[i][k] += ticket_no;
                    seats_fill[i][k] += 1;
                }
                System.out.println("Press (1) to view Seating Arrangement");
                int maja = sc.nextInt();
                if (maja == 1) {
                    System.out.println("--------Train Seating Arrangements-------");
                    for (int l = 0; l < 10; l++) {
                        for (int h = 0; h < 6; h++) {
                            System.out.printf("%-7s", seats[l][h]);
                        }
                        System.out.println();

                    }
                    System.out.println("---------------------------------------------");
                    break x;
                } else {
                    return;
                }
            }

        }
        if (confirm == 0) {
            if (wait_list.size() > 5) {
                System.out.println("Sorry...Tickets not available");
            } else {
                System.out.println(
                        "Tickets Not available..You will be added to waiting list...Press(1)to continue or any other key to cancel");
                int hoice = sc.nextInt();
                if (hoice == 1) {
                    System.out.println("Successfully added to the waiting list with ticket id : " + (ticket_no + 2));
                    wait_list.add(new Wait(users.get(index).userr_id, d_index, a_index, ++ticket_no));
                } else
                    orgUser();
            }
        }
    }

    private static void userSignup() {
        System.out.print("Enter User Name:");
        String s_name = sc.next();
        System.out.print("Enter User Password:");
        String s_pass = sc.next();
        users.add(new Users(userr_id, s_name, s_pass));
        System.out.println("User Registered with  User Id : " + userr_id);
        System.out.println("Remember User Id you have to enter it while logging in!!!");
        userr_id++;
        System.out.println("\tPress enter to continue...");
        sc.nextLine();
        String s = sc.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
        user();
    }

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        userList();
        start();
    }

    private static void userList() {
        users.add(new Users(99, "Kavi", "1234"));
    }
}

class Users extends railway {
    int userr_id;
    String userr_name, userr_password;

    Users(int userr_id, String userr_name, String u_password) {
        this.userr_id = userr_id;
        this.userr_name = userr_name;
        this.userr_password = u_password;
    }
}

class Wait extends railway {
    int userr_id;
    int dep_index, arr_index, ticket_no;

    Wait(int userr_id, int dep_index, int arr_index, int ticket_no) {
        this.userr_id = userr_id;
        this.dep_index = dep_index;
        this.arr_index = arr_index;
        this.ticket_no = ticket_no;
    }
}