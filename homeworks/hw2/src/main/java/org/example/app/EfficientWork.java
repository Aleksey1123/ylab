package org.example.app;

import org.example.command.*;
import org.example.entity.Booking;
import org.example.entity.ConferenceHall;
import org.example.entity.User;
import org.example.entity.Workplace;
import org.example.service.BookingService;
import org.example.service.ConferenceHallService;
import org.example.service.UserService;
import org.example.service.WorkplaceService;

import java.util.List;
import java.util.Scanner;

/** The main app class. **/
public class EfficientWork {

    private final Scanner IN = new Scanner(System.in);

    private final UserService userService = new UserService();
    private final BookingService bookingService = new BookingService(userService);
    private final WorkplaceService workplaceService = new WorkplaceService();
    private final ConferenceHallService hallService = new ConferenceHallService();

    private final CommandInvoker invoker = new CommandInvoker();

    public EfficientWork() {

        invoker.registerCommand("?", new PrintRegisteredUsersCommand(this));
        invoker.registerCommand("help", new HelpCommand(this));
        invoker.registerCommand("1", new RegisterUserCommand(this));
        invoker.registerCommand("2", new AuthorizeCommand(this));
        invoker.registerCommand("3", new BookResourceCommand(this));
        invoker.registerCommand("4", new CancelBookingCommand(this));
        invoker.registerCommand("5", new ViewAllBookingsCommand(this));
        invoker.registerCommand("6", new ViewBookingsByResourceCommand(this));
        invoker.registerCommand("7", new ViewAllBookingsByUserCommand(this));
        invoker.registerCommand("8", new ViewAllBookingsByDateCommand(this));
        invoker.registerCommand("9", new LogOutCommand(this));
        invoker.registerCommand("edit", new ShowEditMenuCommand(this));
        invoker.registerCommand("cw", new CreateWorkplaceCommand(this));
        invoker.registerCommand("gw", new GetWorkplaceCommand(this));
        invoker.registerCommand("gaw", new GetAllWorkplacesCommand(this));
        invoker.registerCommand("uw", new UpdateWorkplaceCommand(this));
        invoker.registerCommand("dw", new DeleteWorkplaceCommand(this));
        invoker.registerCommand("ch", new CreateConferenceHallCommand(this));
        invoker.registerCommand("gh", new GetConferenceHallCommand(this));
        invoker.registerCommand("gah", new GetAllConferenceHallsCommand(this));
        invoker.registerCommand("uh", new UpdateConferenceHallCommand(this));
        invoker.registerCommand("dh", new DeleteConferenceHallCommand(this));
    }

    /**
     * Infinity loop that keeps the app running
     */
    public void startApp() {

        help();
        do {
            String decision = IN.nextLine();

            if (decision.equals("quit"))
                break;

            handleBasicInput(decision);
        } while (true);
    }

    /** This method redirects work to individual methods. **/
    public void handleBasicInput(String decision) {

       invoker.executeCommand(decision);
    }

    /**
     * Output with available console commands.
     */
    public void help() {
        System.out.println("------------------EfficientWork------------------");
        System.out.println("Welcome to the EfficientWork co-working service");
        System.out.println("Enter ? - to print all registered users");
        System.out.println("Enter help - to print the context menu");
        System.out.println("Enter 1 - to register");
        System.out.println("Enter 2 - to log in");
        System.out.println("Enter 3 - to book a resource (workplace or conference hall)");
        System.out.println("Enter 4 - to cancel a resource booking (workplace or conference hall)");
        System.out.println("Enter 5 - to view all bookings");
        System.out.println("Enter 6 - to view all bookings for a specific resource");
        System.out.println("Enter 7 - to view all bookings for a specific user");
        System.out.println("Enter 8 - to view all bookings for a specific date");
        System.out.println("Enter 9 - to log out");
        System.out.println("Enter edit - to open EditPanel");
        System.out.println("Enter quit - to exit the program");
        System.out.println("-------------------------------------------------");
    }

    public void printRegisteredUsers() {

        System.out.println(userService.getAllUsers());
    }

    /**
     * This method provide user registration at the appropriate service.
     * Method throws a RuntimeException: if a user with this username already exists,
     * if username is empty, if addUser() returns null, that corresponds to a registration error.
     */
    public void registerUser() {

        System.out.println("Enter a username: ");
        String username = IN.nextLine();

        System.out.println("Enter a password: ");
        String password = IN.nextLine();

        if (userService.addUser(username, password) != null) {
            System.out.println("Successful registration!");
        }
    }

    public void authorize() {

        System.out.println("Enter username: ");
        String username = IN.nextLine();

        System.out.println("Enter password: ");
        String password = IN.nextLine();

        if (userService.authorizeUser(username, password))
            System.out.println("Successfully logged in as: " + username + ".");
    }

    public void logOut() {

        if (userService.logOut())
            System.out.println("Successfully logged out.");
    }

    /** Booking a resource by calling makeBooking() on the bookingService. **/
    public void bookResource() {

        System.out.print("Enter resource ID (workplace or conference hall): ");
        String resourceId = IN.nextLine();

        System.out.print("Enter resource type (W or H): ");
        String resourceType = IN.nextLine();

        System.out.print("Enter booking start date and time (YYYY-MM-DDTHH:MM:SS): ");
        String startDateTimeString = IN.nextLine();

        System.out.print("Enter booking end date and time (YYYY-MM-DDTHH:MM:SS): ");
        String endDateTimeString = IN.nextLine();

        if (bookingService.makeBooking(resourceId,
                resourceType, startDateTimeString, endDateTimeString))
            System.out.println("Successfully booked resource: " + resourceId + ".");
        else System.out.println("There is a conflict with an existing booking at this resource.");
    }

    /**
     * Cancels booking by calling cancelBooking() on the bookingService.
     */
    public void cancelBooking() {

        System.out.print("Enter booking ID to cancel: ");
        String bookingId = IN.nextLine();

        if (!bookingService.cancelBooking(bookingId))
            System.out.println("Successfully cancelled booking with ID: " + bookingId);
    }

    /**
     * Display all existing bookings on the screen by calling printBookings().
     */
    public void viewAllBookings() {

        List<Booking> bookings = bookingService.getAllBookings();
        System.out.println("----------------List of all bookings----------------");

        if (bookings.isEmpty())
            System.out.println("There are no bookings yet.");

        printBookings(bookings);
    }

    /**
     * Display all existing bookings filtered by user on the screen by calling printBookings()
     * and getAllBookingsByUser() on the bookingService.
     */
    public void viewAllBookingsFilteredByUser() {

        System.out.println("Enter username to filter: ");
        String username = IN.nextLine();

        List<Booking> bookings = bookingService.getAllBookingsByUser(username);
        System.out.println("----------------List of all bookings for this user----------------");

        if (bookings.isEmpty())
            System.out.println("There are no bookings for this user yet.");

        printBookings(bookings);
    }

    /**
     * Display all existing bookings filtered by resource on the screen by calling printBookings()
     * and getAllBookingsByResource() on the bookingService.
     */
    public void viewAllBookingsFilteredByResource() {

        System.out.println("Enter resource ID to filter: ");
        String resourceId = IN.nextLine();

        List<Booking> bookings = bookingService.getAllBookingsByResource(resourceId);
        System.out.println("----------------List of all bookings for this resource----------------");

        if (bookings.isEmpty())
            System.out.println("There are no bookings for this resource yet.");

        printBookings(bookings);
    }

    /**
     * Display all existing bookings filtered by date on the screen by calling printBookings()
     * and getAllBookingsByDate() on the bookingService.
     */
    public void viewAllBookingsFilteredByDate() {

        System.out.print("Enter date and time to filter (YYYY-MM-DDTHH:MM:SS): ");
        String startDateTimeString = IN.nextLine();

        List<Booking> bookings = bookingService.getAllBookingsByDate(startDateTimeString);
        System.out.println("----------------List of all bookings for this date----------------");

        if (bookings.isEmpty())
            System.out.println("There are no bookings for this date and time yet.");

        printBookings(bookings);
    }

    /**
     * Display all existing bookings on the screen. The function accepts one parameter
     * - list of all bookings.
     */
    public void printBookings(List<Booking> bookings) {

        for (var booking : bookings) {
            var resourceId = (booking.getHallId() != null) ? booking.getHallId() : booking.getWorkplaceId();
            System.out.println("ID: " + booking.getId() + ",\n" +
                    "Resource ID: " + resourceId + ",\n" +
                    "Booking start time - end time: " + booking.getStartTime() + " - " + booking.getEndTime() + ",\n" +
                    "Username: " + booking.getUser().getUsername() + "\n");
            System.out.println("--------------------------------------------------------------------------------------");
        }
    }

    /** Displays the panel with resource editing commands on the screen. **/
    public void showEditMenu() {

        System.out.println("------------------EditPanel------------------");
        System.out.println("Enter help - display the context menu");

        System.out.println("Enter cw - create a new workplace");
        System.out.println("Enter gw - display an existing workplace");
        System.out.println("Enter gaw - display all existing workplaces");
        System.out.println("Enter uw - update an existing workplace");
        System.out.println("Enter dw - delete an existing workplace");

        System.out.println("Enter ch - create a new conference hall");
        System.out.println("Enter gh - display an existing conference hall");
        System.out.println("Enter gah - display all existing conference halls");
        System.out.println("Enter uh - update an existing conference hall");
        System.out.println("Enter dh - delete an existing conference hall");

        System.out.println("Enter quit - exit the program");
        System.out.println("---------------------------------------------");
    }

    /** Creates a workplace using the createWorkplace() method of the resourceService.
     * All workplaces are stored in a HashMap workplaces within the resourceService.
     **/
    public void createWorkplace() {

        System.out.println("Enter workplace description: ");
        String description = IN.nextLine();

        workplaceService.createWorkplace(description);
        System.out.println("Workplace successfully added!");
    }

    /** Displays a workplace on the screen using the getWorkplaceById(id) method of the resourceService.
     * Throws a RuntimeException if getWorkplaceById(id) returns null.
     **/
    public void getWorkplace() {

        System.out.println("Enter workplace ID: ");
        String id = IN.nextLine();

        Workplace workplace = workplaceService.getWorkplaceById(id);
        if (workplace != null)
            System.out.println("Found workplace: " + workplace);
    }

    /** Displays all workplaces on the screen using the getAllWorkplaces() method of the resourceService. **/
    public void getAllWorkPlaces() {

        System.out.println("Available workplaces: \n");

        List<Workplace> workplaces = workplaceService.getAllWorkplaces();
        for (Workplace workplace : workplaces) {
            System.out.println(workplace + "\n");
        }
    }

    /** Updates a workplace using the updateWorkplace(id,description) method of the resourceService.
     * Throws a RuntimeException if updateWorkplace(id,description) returns null.
     **/
    public void updateWorkplace() {

        System.out.println("Enter the ID of the workplace you want to update: ");
        String id = IN.nextLine();

        System.out.println("Enter the new workplace description: ");
        String description = IN.nextLine();

        if (workplaceService.updateWorkplace(id, description) != null)
            System.out.println("Workplace with ID: " + id + " successfully updated");

    }

    /** Deletes a workplace from the HashMap in resourceService using the deleteWorkplace(id) method of the resourceService.
     * Throws a RuntimeException if deleteWorkplace(id) returns null.
     **/
    public void deleteWorkplace() {

        System.out.println("Enter the ID of the workplace you want to delete: ");
        String id = IN.nextLine();

        if (workplaceService.deleteWorkplace(id) != null)
            System.out.println("Workplace with ID: " + id + " successfully deleted");
    }

    /** Creates a conference hall using the createConferenceHall() method of the resourceService.
     * All conference halls are stored in a HashMap conferenceHalls within the resourceService.
     **/
    public void createConferenceHall() {

        System.out.println("Enter conference hall description: ");
        String description = IN.nextLine();

        System.out.println("Enter conference hall size: ");
        String size = IN.nextLine();

        if (hallService.createConferenceHall(description, size) != null)
            System.out.println("Conference hall successfully added!");
    }

    /** Displays a conference hall on the screen using the getConferenceHallById(id) method of the resourceService.
     * Throws a RuntimeException if getConferenceHallById(id) returns null.
     **/
    public void getConferenceHall() {

        System.out.println("Enter conference hall ID: ");
        String id = IN.nextLine();

        ConferenceHall conferenceHall = hallService.getConferenceHallById(id);

        if (conferenceHall != null)
            System.out.println("Found conference hall: " + conferenceHall);
    }

    /** Displays all conference halls on the screen using the getAllConferenceHalls() method of the resourceService. **/
    public void getAllConferenceHalls() {

        System.out.println("Available conference halls: \n");

        List<ConferenceHall> conferenceHalls = hallService.getAllConferenceHalls();
        for (ConferenceHall hall : conferenceHalls) {
            System.out.println(hall + "\n");
        }
    }

    /** Updates a conference hall using the updateConferenceHall(id,description,size) method of the resourceService.
     * Throws a NumberFormatException if the hall size is incorrectly entered.
     * Throws a RuntimeException if updateConferenceHall(id,description,size) returns null.
     **/
    public void updateConferenceHall() {

        System.out.println("Enter the ID of the conference hall you want to update: ");
        String id = IN.nextLine();

        System.out.println("Enter the new conference hall description: ");
        String description = IN.nextLine();

        System.out.println("Enter conference hall size: ");
        String size = IN.nextLine();

        if (hallService.updateConferenceHall(id, description, size) != null)
            System.out.println("Conference hall with ID: " + id + " successfully updated");
    }

    /** Deletes a conference hall from the HashMap conferenceHalls in resourceService using the deleteConferenceHall(id) method of the resourceService.
     * Throws a RuntimeException if deleteConferenceHall(id) returns null.
     **/
    public void deleteConferenceHall() {

        System.out.println("Enter the ID of the conference hall you want to delete: ");
        String id = IN.nextLine();

        if (hallService.deleteConferenceHall(id) != null)
            System.out.println("Conference hall with ID: " + id + " successfully deleted");
    }
}
