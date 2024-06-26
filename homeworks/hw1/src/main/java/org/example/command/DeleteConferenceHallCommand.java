package org.example.command;

import org.example.app.EfficientWork;

public class DeleteConferenceHallCommand implements Command {

    private final EfficientWork app;

    public DeleteConferenceHallCommand(EfficientWork app) {
        this.app = app;
    }

    @Override
    public void execute() {
        app.deleteConferenceHall();
    }
}
