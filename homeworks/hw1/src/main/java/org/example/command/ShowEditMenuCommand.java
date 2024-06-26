package org.example.command;

import org.example.app.EfficientWork;

public class ShowEditMenuCommand implements Command {

    private final EfficientWork app;

    public ShowEditMenuCommand(EfficientWork app) {
        this.app = app;
    }

    @Override
    public void execute() {
        app.showEditMenu();
    }
}
