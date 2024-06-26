package org.example.command;

import org.example.app.EfficientWork;

public class LogOutCommand implements Command {

    private final EfficientWork app;

    public LogOutCommand(EfficientWork app) {
        this.app = app;
    }

    @Override
    public void execute() {
        app.logOut();
    }
}
