package com.techie.user.profile.exception;

public class UserNotFoundException extends RuntimeException {
      public UserNotFoundException(String message) {
        super(message);
    }
}