package org.example.command;

import org.example.app.EfficientWork;

public class CancelBookingCommand implements Command {

    private final EfficientWork app;

    public CancelBookingCommand(EfficientWork app) {
        this.app = app;
    }

    @Override
    public void execute() {
        app.cancelBooking();
    }
}
