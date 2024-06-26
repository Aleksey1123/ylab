package org.example.command;

import org.example.app.EfficientWork;

public class ViewAllBookingsCommand implements Command {

    private final EfficientWork app;

    public ViewAllBookingsCommand(EfficientWork app) {
        this.app = app;
    }

    @Override
    public void execute() {
        app.viewAllBookings();
    }
}
