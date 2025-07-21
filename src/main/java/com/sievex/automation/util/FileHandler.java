package com.sievex.automation.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHandler {
    private String saveHtmlToFile(Long jobId, String html) throws IOException {
        Path path = Paths.get("data/pages/" + jobId + ".html");
        Files.createDirectories(path.getParent());
        Files.writeString(path, html, StandardCharsets.UTF_8);
        return path.toString();
    }
}
