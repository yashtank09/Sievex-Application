package com.sievex.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataApiResponse<T> {
    @JsonProperty("status")
    private String status;
    @JsonProperty("status_code")
    private int statusCode;
    @JsonProperty("status_message")
    private String statusMessage;

    @JsonProperty("token")
    private String token;

    @JsonProperty("data")
    private T data;

    public DataApiResponse(String status, int statusCode, String statusMessage, T data) {
        this.status = status;
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.data = data;
    }

    public DataApiResponse(String status, int statusCode, String statusMessage) {
        this.status = status;
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public DataApiResponse(String status, int statusCode, String statusMessage, String token, T data) {
        this.status = status;
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.token = token;
        this.data = data;
    }
}
