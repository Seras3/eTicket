package graphic;

import dto.*;
import service.API;
import service.AuthService;
import util.Command;
import util.Converter;

import java.time.LocalDateTime;
import java.util.*;

public class GUI {
    private static Scanner scan = new Scanner(System.in);
    private static int command_align = 20;

    public static void sceneTitle(String title) {
        System.out.println("~~~~~ " + title + " ~~~~~");
    }

    public static void intro() {
        System.out.println("Welcome to eTicket !\n");
        System.out.println("We're glad to have you here.");
    }

    public static void authentication() {
        System.out.println("Get comfortable and create an account or just log in.");
    }

    public static void enterCommand() {
        System.out.println("Enter command:");
    }

    public static void help(Map<String, Command> commands) {
        List<Command> display_list = new ArrayList(commands.values());
        Collections.sort(display_list);
        display_list.forEach(obj -> System.out.println(GUI.commandToString(obj)));
    }

    public static String commandToString(Command command) {
        int align_offset = GUI.command_align - command.getName().length();
        return command.getName() + " ".repeat(align_offset) + " - " + command.getDescription();
    }

    public static void exit() {
        System.out.println("See you soon! :D");
    }

    public static void invalidCommand() {
        System.out.println("Command is invalid, please try again.");
    }

    public static void invalidCommandParameter(String parameter) {
        System.out.println("Parameter " + parameter + " is invalid.");
    }

    public static void exception(Exception e) {
        System.out.println(e.getMessage());
    }

    public static void allParamsRequired() {
        System.out.println("All parameters are required.");
    }

    public static void authResponse(AuthService.Result auth_result) {
        switch(auth_result) {
            case REGISTER_SUCCESS:
                System.out.println("Your account has been created.");
                break;
            case LOGIN_SUCCESS:
                System.out.println("Successfully login.");
                break;
            case NOT_FOUND:
                System.out.println("E-mail or password incorrect.");
                break;
            case USER_EXISTS:
                System.out.println("You already have an account linked with this e-mail.");
                break;
        }
    }

    public static void apiResponse(API.Result api_result) {
        switch(api_result) {
            case OK:
                System.out.println("Command successfully executed.");
                break;
            case NOT_FOUND:
                System.out.println("Resource not found.");
                break;
            case FAIL:
                System.out.println("Command failed.");
                break;
        }
    }

    public static void welcome(String user) {
        System.out.println(String.format("Hello, %s! :D", user));
    }

    public static void ticketsList(List<TicketRowDTO> tickets) {
        for(TicketRowDTO ticket : tickets) {
            System.out.println(ticket.getName() + " ( " + ticket.getPrice() +" RON )" + " - " + ticket.getCount() + " tickets");

            if(ticket.isSeat()) {
                System.out.println("(Pick seat)");
            }

            if(ticket.getDescription() != null)  {
                System.out.println(ticket.getDescription());
            }

            System.out.println();
        }
    }

    public static void displayDate(LocalDateTime date) {
        System.out.print(date.getDayOfMonth() + " " + date.getMonth() +" "+ date.getYear() +
                " ( "+ (date.getHour() < 9 ? "0" + date.getHour() : date.getHour()) +
                " : " + (date.getMinute() < 9 ? "0" + date.getMinute() : date.getMinute()) +" )");
    }

    public static void eventRowList(List<EventRowDTO> events) {
        for(EventRowDTO event : events) {

            System.out.println("~~~~~~~~~~~~~~~~~ ("+ event.getId() +") " + event.getName() + " ~~~~~~~~~~~~~~~~~");

            System.out.println("Location: " + event.getCountry() + ", " + event.getCity());

            System.out.print("When ? : ");
            displayDate(event.getStartDate());
            System.out.print(" - ");
            displayDate(event.getEndDate());
            System.out.println();
            System.out.println();

            System.out.println(event.getDescription());

            System.out.println();
        }
    }

    public static void eventCompactShow(EventCompactDTO event) {
        System.out.println("~~~~~~~~ " + event.getEvent().getName() + " ~~~~~~~~");
        System.out.println("Location: " + event.getLocation().getCountry() + ", " + event.getLocation().getCity());
        System.out.println("Address: " + event.getLocation().getDetails());
        System.out.println(event.getEvent().getDescription());
        System.out.println();
    }

    public static void eventList(List<EventDTO> events) {
        for(EventDTO event : events) {
            System.out.print("(" + event.getId() + ") ");
            System.out.println(event.getName());

            System.out.println(event.getDescription());
            System.out.println();
        }
    }

    private static void showCartTicket(TicketDTO ticket) {
        System.out.print("id " + ticket.getId() + ": " + ticket.getName() + " ");
        if(ticket.getSeat() != null) {
            System.out.print("(row: " + ticket.getSeat().getRowOrd() + " ");
            System.out.print("place: " + ticket.getSeat().getPlace() + ") ");
        }
        System.out.println(ticket.getPrice() + " RON");
    }

    public static void showCart(List<CartRowDTO> cartRows) {
        if (cartRows.isEmpty()) {
            System.out.println("YOUR CART IS EMPTY :)");
        } else {
            float total = 0;
            for (CartRowDTO row : cartRows) {
                System.out.print("EVENT: (" + row.getEvent().getId() + ") ");
                System.out.println(row.getEvent().getName());

                for (TicketDTO ticket : row.getTickets()) {
                    GUI.showCartTicket(ticket);
                    total += ticket.getPrice();
                }
                System.out.println();
            }

            System.out.printf("TOTAL : %.2f RON %n", total);
        }
    }

    public static void locationList(List<LocationDTO> locations) {
        for(LocationDTO location : locations) {
            System.out.print("(" + location.getId() + ") ");
            System.out.println(location.getCountry() + ", " + location.getCity());
            System.out.println(location.getDetails());
            System.out.println();
        }
    }

    public static boolean eventDelete(EventDTO event, LocationDTO location) {
        System.out.println("From " + location.getCountry() + ", " + location.getCity() + ".");
        System.out.println("Are you sure you want to delete "+ event.getName() + "? (Y/N)");
        return scan.nextLine().equals("Y");
    }

    public static void NotFound(String msg) {
        System.out.println(msg + " not found.");
    }

    public static EventLocationDTO getEventLocation() {
        EventLocationDTO event = new EventLocationDTO();

        System.out.print("Event id: ");
        event.setEventId(Integer.parseInt(scan.nextLine()));

        System.out.print("Location id: ");
        event.setLocationId(Integer.parseInt(scan.nextLine()));

        System.out.print("Start date (dd-MM-yyyy HH:mm): ");
        event.setStartDate(Converter.stringToLocalDateTime(scan.nextLine()));

        System.out.print("End date (dd-MM-yyyy HH:mm): ");
        event.setEndDate(Converter.stringToLocalDateTime(scan.nextLine()));

        return event;
    }

    public static EventDTO getEvent() {
        EventDTO event = new EventDTO();

        System.out.print("Name: ");
        event.setName(scan.nextLine());

        System.out.print("Description: ");
        event.setDescription(scan.nextLine());

        System.out.print("Category id: ");
        event.setCategoryId(Integer.valueOf(scan.nextLine()));

        return event;
    }

    public static SeatDTO getSeat() {
        SeatDTO input = new SeatDTO();

        System.out.print("row: ");
        input.setRowOrd(Integer.valueOf(scan.nextLine()));

        System.out.print("place: ");
        input.setPlace(Integer.valueOf(scan.nextLine()));

        return input;
    }

    public static String getCommand() {
        enterCommand();
        return scan.nextLine();
    }

    public static AccountAuthDTO getAuth() {
        String email, password;

        System.out.print("e-mail: ");
        email = scan.nextLine();

        System.out.print("password: ");
        password = scan.nextLine();

        return new AccountAuthDTO(email, password);
    }
}
