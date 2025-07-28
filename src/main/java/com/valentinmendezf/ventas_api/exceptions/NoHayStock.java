package com.valentinmendezf.ventas_api.exceptions;

public class NoHayStock extends RuntimeException {
    public NoHayStock(String message) {
        super(message);
    }
}
