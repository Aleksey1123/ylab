package org.example.command;

import org.example.app.EfficientWork;

public class ViewAllBookingsByUserCommand implements Command {

    private final EfficientWork app;

    public ViewAllBookingsByUserCommand(EfficientWork app) {
        this.app = app;
    }

    @Override
    public void execute() {
        app.viewAllBookingsFilteredByUser();
    }
}
