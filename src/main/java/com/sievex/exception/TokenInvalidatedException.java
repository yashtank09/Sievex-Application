package com.sievex.exception;

import javax.security.sasl.AuthenticationException;

public class TokenInvalidatedException extends AuthenticationException {
    public TokenInvalidatedException(String message) {
        super(message);
    }
}
