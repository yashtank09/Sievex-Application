package com.oauthapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataApiResponse<T> {
    @JsonProperty("status")
    private String status;
    @JsonProperty("status_code")
    private int statusCode;
    @JsonProperty("status_message")
    private String statusMessage;
    @JsonProperty("data")
    private T data;

    public DataApiResponse(String status, int statusCode, String statusMessage) {
        this.status = status;
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }
}
