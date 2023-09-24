package by.javaguru.client;

import util.PropertiesUtil;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.*;

public class HttpClientRunner {
    private static final String URL = "http://localhost:" + PropertiesUtil.get("port");
    private static final Path PATH_TO_JSON = Path.of("src", "main",
            "resources", "client-data.json");

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("[Client] init");
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .POST(HttpRequest.BodyPublishers.ofFile(PATH_TO_JSON))
                .setHeader("content-type", "application/json")
                .build();

        System.out.println("[Client] sending a request");
        HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
        System.out.println("[Client] response received");

        System.out.println("\n[Client] response readers:");
        System.out.println(response.headers());
        System.out.println("\n[Client] response body:");
        String htmlContent = response.body();
        System.out.println(htmlContent);

        Path file = Files.createFile(Path.of(getFileNameFromCurrentTime()));
        Files.writeString(file, htmlContent);
    }

    private static String getFileNameFromCurrentTime() {
        return String.format("[%s]%s.html",
                LocalDate.now().format(ofPattern("dd.MM.yyyy")),
                LocalTime.now().format(ofPattern("HH-mm-ss")));
    }
}
