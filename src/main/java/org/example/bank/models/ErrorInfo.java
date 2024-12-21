package org.example.bank.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorInfo {
    private String url;
    private String message;

    public ErrorInfo(String url, Exception ex) {
        this.url = url;
        this.message = ex.getLocalizedMessage();
    }
}