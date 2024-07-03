package org.example.command;

import org.example.app.EfficientWork;

import java.util.HashMap;
import java.util.Map;

public class CommandInvoker {

    private final Map<String, Command> commands = new HashMap<>();
    
    public CommandInvoker(EfficientWork app) {
        
        commands.put("?", new PrintRegisteredUsersCommand(app));
        commands.put("help", new HelpCommand(app));
        commands.put("1", new RegisterUserCommand(app));
        commands.put("2", new AuthorizeCommand(app));
        commands.put("3", new BookResourceCommand(app));
        commands.put("4", new CancelBookingCommand(app));
        commands.put("5", new ViewAllBookingsCommand(app));
        commands.put("6", new ViewBookingsByResourceCommand(app));
        commands.put("7", new ViewAllBookingsByUserCommand(app));
        commands.put("8", new ViewAllBookingsByDateCommand(app));
        commands.put("9", new LogOutCommand(app));
        commands.put("edit", new ShowEditMenuCommand(app));
        commands.put("cw", new CreateWorkplaceCommand(app));
        commands.put("gw", new GetWorkplaceCommand(app));
        commands.put("gaw", new GetAllWorkplacesCommand(app));
        commands.put("uw", new UpdateWorkplaceCommand(app));
        commands.put("dw", new DeleteWorkplaceCommand(app));
        commands.put("ch", new CreateConferenceHallCommand(app));
        commands.put("gh", new GetConferenceHallCommand(app));
        commands.put("gah", new GetAllConferenceHallsCommand(app));
        commands.put("uh", new UpdateConferenceHallCommand(app));
        commands.put("dh", new DeleteConferenceHallCommand(app));
    }

    public void registerCommand(String key, Command command) {
        commands.put(key, command);
    }

    public void executeCommand(String key) {
        Command command = commands.get(key);
        if (command != null) {
            command.execute();
        } else {
            System.out.println("Неверный аргумент, попробуйте снова!");
        }
    }
}
