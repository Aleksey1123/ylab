package org.example.command;

import org.example.app.EfficientWork;

public class AuthorizeCommand implements Command {

    private final EfficientWork app;

    public AuthorizeCommand(EfficientWork app) {
        this.app = app;
    }

    @Override
    public void execute() {
        app.authorize();
    }
}
