package com.roninswdstudio.ronin_app.exceptions;

public class ImageNotFoundException extends Exception{
    public ImageNotFoundException(String message) {
        super(message);
    }
    public ImageNotFoundException() {
        super("Image not found excepton");
    }
 }
