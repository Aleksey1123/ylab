package org.example.app;

import org.example.entity.Booking;
import org.example.entity.ConferenceHall;
import org.example.entity.Workplace;
import org.example.service.BookingService;
import org.example.service.ResourceService;
import org.example.service.UserService;
import org.example.entity.User;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.*;

/** Главный класс приложения по сути можно назвать котроллером. **/
public class EfficientWork {

    private final Scanner IN = new Scanner(System.in);
    private User authorisedUser;
    private boolean editPanelNotShown = false;

    private final UserService userService = new UserService();
    private final BookingService bookingService = new BookingService();
    private final ResourceService resourceService = new ResourceService();

    /**
     * Бесконечный цикл, который обеспечивает работу консольного приложения.
     */
    public void startApp() {

        boolean flag;

        do {

            if (!editPanelNotShown)
                help();
            editPanelNotShown = false;
            String decision = IN.nextLine();
            flag = handleBasicInput(decision);
        } while (flag);
    }

    /** Switch выражение, которое перенаправляет работу на отдельные методы. **/
    public boolean handleBasicInput(String decision) {

        switch (decision) {
            case "?":
                printRegisteredUsers();
                break;
            case "help":
                help();
                break;
            case "1":
                registerUser();
                break;
            case "2":
                authorize();
                break;
            case "3":
                bookResource();
                break;
            case "4":
                cancelBooking();
                break;
            case "5":
                viewAllBookings();
                break;
            case "6":
                viewAllBookingsFilteredByResource();
                break;
            case "7":
                viewAllBookingsFilteredByUser();
                break;
            case "8":
                viewAllBookingsFilteredByDate();
                break;
            case "9":
                logOut();
                break;
            case "edit":
                showEditMenu();
                editPanelNotShown = true;
                break;
            case "cw":
                createWorkplace();
                break;
            case "gw":
                getWorkplace();
                break;
            case "gaw":
                getAllWorkPlaces();
                break;
            case "uw":
                updateWorkplace();
                break;
            case "dw":
                deleteWorkplace();
                break;
            case "ch":
                createConferenceHall();
                break;
            case "gh":
                getConferenceHall();
                break;
            case "gah":
                getAllConferenceHalls();
                break;
            case "uh":
                updateConferenceHall();
                break;
            case "dh":
                deleteConferenceHall();
                break;
            case "quit":
                return false;
            default:
                System.out.println("Неверный аргумент, попробуйте снова!");
        }

        return true;
    }

    /** Вывод панели с основными командами на экран. **/
    public void help() {

        System.out.println("------------------EfficientWork------------------");
        System.out.println("Добро пожаловать в коворкинг-сервис EfficientWork");
        System.out.println("Введите ? - вывести всех зарегистрированных пользователей");
        System.out.println("Введите help - вывести контекстное меню");

        System.out.println("Введите 1 - зарегистрироваться");
        System.out.println("Введите 2 - войти");
        System.out.println("Введите 3 - забронировать ресурс (рабочее место или конференц-зал)");
        System.out.println("Введите 4 - отменить бронирование ресурса (рабочее место или конференц-зал)");
        System.out.println("Введите 5 - просмотреть все бронирования");
        System.out.println("Введите 6 - просмотреть все бронирования для конкретного ресурса");
        System.out.println("Введите 7 - просмотреть все бронирования для конкретного пользователя");
        System.out.println("Введите 8 - просмотреть все бронирования для конкретной даты");
        System.out.println("Введите 9 - выйти");

        System.out.println("Введите edit - открыть EditPanel");
        System.out.println("Введите quit - выйти из программы");
        System.out.println("-------------------------------------------------");
    }

    /** Вывод всех зарегистрированных пользователей с помощью вызова getAllUsers() у соответствующего сервиса. **/
    public void printRegisteredUsers() {

        System.out.println(userService.getAllUsers());
    }

    /** Регистрация пользователя с помощью вызова addUser() у соответствующего сервиса.
     * Метод пробрасывает RuntimeException в случаях: если пользователь под таким именем уже существует,
     * если addUser() возвращает null, что соответствует ошибке регистрации.
     **/
    public void registerUser() {

        try {
            System.out.println("Введите имя пользователя: ");
            String username = IN.nextLine();

            if (userService.getAllUsers().containsKey(username)) {
                throw new RuntimeException("Такой пользователь уже существует.\n" +
                        "Придумайте новое имя пользователя.");
            }

            System.out.println("Введите пароль: ");
            String password = IN.nextLine();

            if (userService.addUser(username, password) != null) {
                System.out.println("Вы успешно зарегестрировались!");
            }
            else throw new RuntimeException("Произошла ошибка, попробуйте снова!");
        }
        catch (RuntimeException exception) {
            System.out.println(exception.getMessage());
        }
    }

    /** Авторизация пользователя с помощью вызова authorizeUser() у соответствующего сервиса.
     * Метод пробрасывает RuntimeException в случаях: если пользователь уже вошел в систему,
     * если authorizeUser() возвращает false, что соответствует неверному имени пользователя или паролю.
     **/
    public void authorize() {

        try {
            if (authorisedUser != null)
                throw new RuntimeException("Вы уже вошли в систему под пользователем: " +
                        authorisedUser.getUsername() + ".");

            System.out.println("Введите имя пользователя: ");
            String username = IN.nextLine();

            System.out.println("Введите пароль: ");
            String password = IN.nextLine();

            if (!userService.authorizeUser(username, password)) {
                throw new RuntimeException("Неверное имя пользователя или пароль, попробуйте ещё раз!");
            }

            authorisedUser = userService.getUserByUsername(username);
            System.out.println("Вы успешно вошли в систему под пользователем: " +
                    username + ".");
        }
        catch (RuntimeException exception) {
            System.out.println(exception.getMessage());
        }
    }

    /** Выход из системы с помощью изменения переменной authorisedUser.
     * Метод пробрасывает RuntimeException в случае, если пользователь еще не вошел в систему.
     **/
    public void logOut() {

        try {
            if (authorisedUser == null)
                throw new RuntimeException("Вы еще не вошли в систему!");

            authorisedUser = null;
            System.out.println("Вы успешно вышли из системы.");
        }
        catch (RuntimeException exception) {
            System.out.println(exception.getMessage());
        }
    }

    /** Бронирование ресурса с помощью вызова makeBooking() у сервиса bookingService.
     * Метод пробрасывает DateTimeException в случае, если некорректно введена дата.
     * Метод пробрасывает RuntimeException в случаях: если пользователь еще не вошел в систему,
     * если произошел конфликт бронирования, в случае конфликта makeBooking() возвращает пустой список.
     **/
    public void bookResource() {
        try {
            if (authorisedUser == null) {
                throw new RuntimeException("Для бронирования необходимо сначала войти в систему.");
            }

            System.out.print("Введите ID ресурса (рабочего места или конференц-зала): ");
            String resourceId = IN.nextLine();

            System.out.print("Введите дату и время начала бронирования (ГГГГ-ММ-ДДTЧЧ:ММ:CC): ");
            String startDateTimeString = IN.nextLine();
            LocalDateTime startTime = LocalDateTime.parse(startDateTimeString);

            System.out.print("Введите дату и время окончания бронирования (ГГГГ-ММ-ДДTЧЧ:ММ:CC): ");
            String endDateTimeString = IN.nextLine();
            LocalDateTime endTime = LocalDateTime.parse(endDateTimeString);

            List<Booking> conflicts = bookingService.makeBooking(authorisedUser, resourceId, startTime, endTime);
            if (!conflicts.isEmpty()) {
                throw new RuntimeException("Конфликт бронирования с " + conflicts + ".");
            }

            System.out.println("Вы успешно забронировали место!");
        }
        catch (DateTimeException exception) {
            System.out.println("Некорректно введена дата.");
        }
        catch (RuntimeException exception) {
            System.out.println(exception.getMessage());
        }
    }

    /** Отмена бронирования ресурса с помощью вызова cancelBooking() у сервиса bookingService.
     * Метод пробрасывает RuntimeException в случае, если неверно введено ID бронирования.
     **/
    public void cancelBooking() {

        try {
            System.out.print("Введите ID бронирования для отмены: ");
            String bookingId = IN.nextLine();

            if (!bookingService.cancelBooking(bookingId)) {
                throw new RuntimeException("Неверное ID бронирования, попробуйте снова.");
            }

            System.out.println("Успешная отмена бронирования под ID: " + bookingId);
        }
        catch (RuntimeException exception) {
            System.out.println(exception.getMessage());
        }
    }

    /** Вывод всех существующих бронирований на экран с помощью вызова printBookings(). **/
    public void viewAllBookings() {

        List<Booking> bookings = bookingService.getAllBookings();
        System.out.println("----------------Список всех бронирований----------------");

        if (bookings.isEmpty())
            System.out.println("Пока ещё нет ни единого бронирования.");

        printBookings(bookings);
    }

    /** Вывод всех существующих бронирований отфильтрованных по пользователю на экран с помощью вызова printBookings()
     *  и вызова getAllBookingsByUser() у сервиса bookingService.
     **/
    public void viewAllBookingsFilteredByUser() {

        System.out.println("Введите имя пользователя для фильтрации: ");
        String username = IN.nextLine();

        List<Booking> bookings = bookingService.getAllBookingsByUser(username);
        System.out.println("----------------Список всех бронирований по данному пользователю----------------");

        if (bookings.isEmpty())
            System.out.println("Для данного пользователя пока ещё нет ни единого бронирования.");

        printBookings(bookings);
    }

    /** Вывод всех существующих бронирований отфильтрованных по ресурсу на экран с помощью вызова printBookings()
     *  и вызова getAllBookingsByResource() у сервиса bookingService.
     **/
    public void viewAllBookingsFilteredByResource() {

        System.out.println("Введите ID ресурса для фильтрации: ");
        String resourceId = IN.nextLine();

        List<Booking> bookings = bookingService.getAllBookingsByResource(resourceId);
        System.out.println("----------------Список всех бронирований по данному ресурсу----------------");

        if (bookings.isEmpty())
            System.out.println("Для данного ресурса пока ещё нет ни единого бронирования.");

        printBookings(bookings);
    }

    /** Вывод всех существующих бронирований отфильтрованных по дате на экран с помощью вызова printBookings()
     *  и вызова getAllBookingsByDate() у сервиса bookingService.
     **/
    public void viewAllBookingsFilteredByDate() {

        System.out.print("Введите дату и время для фильтрации (ГГГГ-ММ-ДДTЧЧ:ММ:CC): ");
        String startDateTimeString = IN.nextLine();
        LocalDateTime date = LocalDateTime.parse(startDateTimeString);

        List<Booking> bookings = bookingService.getAllBookingsByDate(date);
        System.out.println("----------------Список всех бронирований по данному ресурсу----------------");

        if (bookings.isEmpty())
            System.out.println("Для данной даты и времени нет еще ни одного бронирования.");

        printBookings(bookings);
    }

    /** Вывод всех существующих бронирований на экран. Функция принимает один параметр
     *  - список всех бпронирований.
     **/
    public void printBookings(List<Booking> bookings) {

        for (var booking : bookings) {
            System.out.println("ID: " + booking.getId() + ",\n" +
                    "ID ресурса: " + booking.getResourceId() + ",\n" +
                    "Время: " + booking.getStartTime() + " - " + booking.getEndTime() + ",\n" +
                    "Пользователь: " + booking.getUser().getUsername() + "\n");
            System.out.println("--------------------------------------------------------------------------------------");
        }
    }

    /** Вывод панели с командами редактирования ресурсов на экран. **/
    public void showEditMenu() {

        System.out.println("------------------EditPanel------------------");
        System.out.println("Введите help - вывести контекстное меню");

        System.out.println("Введите cw - создать новое рабочее место");
        System.out.println("Введите gw - вывести существующее рабочее место");
        System.out.println("Введите gaw - вывести все существующие рабочие места");
        System.out.println("Введите uw - изменить существующее рабочее место");
        System.out.println("Введите dw - удалить существующее рабочее место");

        System.out.println("Введите ch - создать новый конференц-зал");
        System.out.println("Введите gh - вывести существующий конференц-зал");
        System.out.println("Введите gah - вывести все существующие конференц-залы");
        System.out.println("Введите uh - изменить существующий конференц-зал");
        System.out.println("Введите dh - удалить существующий конференц-зал");

        System.out.println("Введите quit - выйти из программы");
        System.out.println("---------------------------------------------");
    }

    /** Создание рабочего места с помощью вызова createWorkplace() у сервиса resourceService.
     * Все рабочие места хранятся в HashMap workplaces внутри сервиса resourceService.
     **/
    public void createWorkplace() {

        System.out.println("Введите описание рабочего места: ");
        String description = IN.nextLine();

        resourceService.createWorkplace(description);
        System.out.println("Рабочее место успешно добавлено!");
    }

    /** Вывод рабочего места на экран с помощью вызова getWorkplaceById(id) у сервиса resourceService.
     * Метод пробрасывает RuntimeException в случае, если getWorkplaceById(id) возвращает null.
     **/
    public void getWorkplace() {

        try {
            System.out.println("Введите ID рабочего места: ");
            String id = IN.nextLine();

            Workplace workplace = resourceService.getWorkplaceById(id);

            if (workplace == null)
                throw new RuntimeException("Такого рабочего места не существует, попробуйте снова!");

            System.out.println("Найденное рабочее место: " + workplace);
        }
        catch (RuntimeException exception) {
            System.out.println(exception.getMessage());
        }
    }

    /** Вывод всех рабочих мест на экран с помощью вызова getAllWorkplaces() у сервиса resourceService. **/
    public void getAllWorkPlaces() {

        System.out.println("Доступные рабочие места: \n");

        List<Workplace> workplaces = resourceService.getAllWorkplaces();
        for (Workplace workplace : workplaces) {
            System.out.println(workplace + "\n");
        }
    }

    /** Изменение рабочего места с помощью вызова updateWorkplace(id,description) у сервиса resourceService.
     * Метод пробрасывает RuntimeException в случае, если updateWorkplace(id,description) возвращает null.
     **/
    public void updateWorkplace() {

        try {
            System.out.println("Введите ID рабочего места, которое хотите изменить: ");
            String id = IN.nextLine();

            System.out.println("Введите новое описание рабочего места: ");
            String description = IN.nextLine();

            if (resourceService.updateWorkplace(id, description) == null) {
                throw new RuntimeException("Рабочего места с ID: " + id +
                        " не существует.");
            }

            System.out.println("Рабочее место с ID: " + id + " успешно изменено");
        }
        catch (RuntimeException exception) {
            System.out.println(exception.getMessage());
        }
    }

    /** Удаоение рабочего места из HashMap в resourceService с помощью
     * вызова deleteWorkplace(id) у сервиса resourceService.
     * Метод пробрасывает RuntimeException в случае, если deleteWorkplace(id) возвращает null.
     **/
    public void deleteWorkplace() {

        try {
            System.out.println("Введите ID рабочего места, которое хотите удалить: ");
            String id = IN.nextLine();

            if (resourceService.deleteWorkplace(id) == null)
                throw new RuntimeException("Рабочего места с ID: " + id +
                        " не существует.");

            System.out.println("Рабочее место с ID: " + id + " успешно удалено");
        }
        catch (RuntimeException exception) {
            System.out.println(exception.getMessage());
        }
    }

    /** Создание конференц-зала с помощью вызова createConferenceHall() у сервиса resourceService.
     * Все конференц-залы хранятся в HashMap conferenceHalls внутри сервиса resourceService.
     **/
    public void createConferenceHall() {

        try {
            System.out.println("Введите описание конференц-зала: ");
            String description = IN.nextLine();

            System.out.println("Введите размер конференц-зала: ");
            int size = Integer.parseInt(IN.nextLine());

            resourceService.createConferenceHall(description, size);
            System.out.println("Конференц-зал успешно добавлен!");
        }
        catch (NumberFormatException exception) {
            System.out.println("Размер конференц-зала должен быть целым числом!");
        }
    }

    /** Вывод конференц-зала на экран с помощью вызова getConferenceHallById(id) у сервиса resourceService.
     * Метод пробрасывает RuntimeException в случае, если getConferenceHallById(id) возвращает null.
     **/
    public void getConferenceHall() {

        try {
            System.out.println("Введите ID конференц-зала: ");
            String id = IN.nextLine();

            ConferenceHall conferenceHall = resourceService.getConferenceHallById(id);

            if (conferenceHall == null)
                throw new RuntimeException("Такого конференц-зала не существует, попробуйте снова!");

            System.out.println("Найденный конференц-зал: " + conferenceHall);
        }
        catch (RuntimeException exception) {
            System.out.println(exception.getMessage());
        }
    }

    /** Вывод всех конференц-залов на экран с помощью вызова getAllConferenceHalls() у сервиса resourceService. **/
    public void getAllConferenceHalls() {

        System.out.println("Доступные конференц-залы: \n");

        List<ConferenceHall> conferenceHalls = resourceService.getAllConferenceHalls();
        for (ConferenceHall hall : conferenceHalls) {
            System.out.println(hall + "\n");
        }
    }

    /** Изменение конференц-зала с помощью вызова updateConferenceHall(id,description,size) у сервиса resourceService.
     * Метод пробрасывает NumberFormatException в случае, если некорректно введен размер зала.
     * Метод пробрасывает RuntimeException в случае, если updateConferenceHall(id,description,size) возвращает null.
     **/
    public void updateConferenceHall() {

        try {
            System.out.println("Введите ID конференц-зала, который хотите изменить: ");
            String id = IN.nextLine();

            System.out.println("Введите новое описание конференц-зала: ");
            String description = IN.nextLine();

            System.out.println("Введите размер конференц-зала: ");
            int size = Integer.parseInt(IN.nextLine());

            if (resourceService.updateConferenceHall(id, description, size) == null) {
                throw new RuntimeException("Конференц-зал с ID: " + id +
                        " не существует.");
            }

            System.out.println("Конференц-зал с ID: " + id + " успешно изменен");
        }
        catch (NumberFormatException exception) {
            System.out.println("Размер конференц-зала должен быть целым числом!");
        }
        catch (RuntimeException exception) {
            System.out.println(exception.getMessage());
        }
    }

    /** Удаоение конференц-зала из HashMap conferenceHalls в resourceService с помощью
     * вызова deleteConferenceHall(id) у сервиса resourceService.
     * Метод пробрасывает RuntimeException в случае, если deleteConferenceHall(id) возвращает null.
     **/
    public void deleteConferenceHall() {

        try {
            System.out.println("Введите ID конференц-зала, который хотите удалить: ");
            String id = IN.nextLine();

            if (resourceService.deleteConferenceHall(id) == null)
                throw new RuntimeException("Конференц-зал с ID: " + id +
                        " не существует.");

            System.out.println("Конференц-зал с ID: " + id + " успешно удален");
        }
        catch (RuntimeException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
