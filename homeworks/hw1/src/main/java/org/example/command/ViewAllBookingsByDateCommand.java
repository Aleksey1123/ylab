package org.example.command;

import org.example.app.EfficientWork;

public class ViewAllBookingsByDateCommand implements Command {

    private final EfficientWork app;

    public ViewAllBookingsByDateCommand(EfficientWork app) {
        this.app = app;
    }

    @Override
    public void execute() {
        app.viewAllBookingsFilteredByDate();
    }
}
