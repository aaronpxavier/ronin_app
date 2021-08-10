package com.roninswdstudio.ronin_app.exceptions;

public class ArtistNotFoundException extends Exception {
    public ArtistNotFoundException(String message) {
        super(message);
    }

    public  ArtistNotFoundException() {
        super("artist not found exception");
    }

}
