package org.example.command;

import org.example.app.EfficientWork;

public class GetAllConferenceHallsCommand implements Command {

    private final EfficientWork app;

    public GetAllConferenceHallsCommand(EfficientWork app) {
        this.app = app;
    }

    @Override
    public void execute() {
        app.getAllConferenceHalls();
    }
}
