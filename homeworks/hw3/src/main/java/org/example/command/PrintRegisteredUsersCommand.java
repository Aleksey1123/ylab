package org.example.command;

import org.example.app.EfficientWork;

public class PrintRegisteredUsersCommand implements Command {

    private final EfficientWork app;

    public PrintRegisteredUsersCommand(EfficientWork app) {
        this.app = app;
    }

    @Override
    public void execute() {
        app.printRegisteredUsers();
    }
}
