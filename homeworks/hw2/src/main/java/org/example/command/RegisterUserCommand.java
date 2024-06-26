package org.example.command;

import org.example.app.EfficientWork;

public class RegisterUserCommand implements Command {

    private final EfficientWork app;

    public RegisterUserCommand(EfficientWork app) {
        this.app = app;
    }

    @Override
    public void execute() {
        app.registerUser();
    }
}
