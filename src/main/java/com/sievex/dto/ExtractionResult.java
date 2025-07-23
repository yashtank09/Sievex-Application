package com.sievex.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExtractionResult {

    // Fields
    private boolean success = false;
    private String message;
    private String dataFilePath;
    private Instant crawlTime;

}
