
package org.example.themovingcompany.exception;

// A simple structure to return errors in JSON format.
// Instead of showing a raw stack trace or a plain error message,
// we return a clean JSON object with:
// - the HTTP status code (like 400 or 404)
// - and a clear error message.
//
// This helps frontend apps, Postman users, or other APIs understand
// exactly what went wrong and how to handle the error.

public class ApiError {
    private int status;
    private String message;

    public ApiError(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}

