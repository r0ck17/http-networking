package by.javaguru.server;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpServer {
    private final int PORT;

    public HttpServer(int PORT) {
        System.out.println("[Server] init.");
        this.PORT = PORT;
    }

    public void run() {
        System.out.println("[Server] is running");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            Socket socket = serverSocket.accept();
            System.out.println("[Server] accepted new connection");
            processSocket(socket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void processSocket(Socket socket) {
        System.out.println("[Server] start processing the request");
        try (socket;
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             BufferedInputStream is = new BufferedInputStream(socket.getInputStream())) {
            String jsonContent = getHttpRequestBodyFromStream(is);
            ObjectMapper objectMapper = new ObjectMapper();
            EmployeesSalary employeesSalary = objectMapper.readValue(jsonContent,
                    EmployeesSalary.class);
            System.out.println("\n[Server] all bytes of the request have been read");
            System.out.println(employeesSalary);

            String httpBody = getHtmlPageContentToEmployeesSalary(employeesSalary);
            String headers = """
                    HTTP/1.1 200 OK
                    content-type: text/html
                    content-length: %d
                    """.formatted(httpBody.length());

            out.write(headers.getBytes());
            out.write(System.lineSeparator().getBytes());
            out.write(httpBody.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("[Server] end processing the request");
    }

    private String getHtmlPageContentToEmployeesSalary(EmployeesSalary employees) {
        return """
                <!DOCTYPE html>
                <html lang="en">
                    <head>
                       <title>Salary</title>
                    </head>
                    <body>
                    <table>
                       <tr>
                           <th>Total income</th>
                           <th>Total tax</th>
                           <th>Total profit</th>
                       </tr>
                       <tr>
                           <td>%d</td>
                           <td>%d</td>
                           <td>%d</td>
                       </tr>
                    </table>
                    </body>
                </html>
                """.formatted(
                employees.getTotalIncome(),
                employees.getTotalTax(),
                employees.getTotalProfit());
    }

    private String getHttpRequestBodyFromStream(BufferedInputStream is) throws IOException {
        StringBuilder headersBuilder = new StringBuilder();
        int currChar;
        int lastCh = -1;
        System.out.println("[Server ] reading request\n");

        while ((currChar = is.read()) != -1) {
            if (currChar == '\r' && lastCh == '\n') { // empty line, works in windows
                break;
            }
            System.out.print((char) currChar);
            headersBuilder.append((char) currChar);
            lastCh = currChar;
        }

        System.out.println("\n[Server ] found end line of headers");
        String headersContent = headersBuilder.toString();
        Pattern pattern = Pattern.compile("(?<=Content-Length:\\s)[0-9]+\\b");
        Matcher matcher = pattern.matcher(headersContent);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        if (matcher.find()) {
            int contentLength = Integer.parseInt(matcher.group());
            for (int i = 0; i <= contentLength; i++) {
                byteArrayOutputStream.write(is.read());
            }
            return byteArrayOutputStream.toString(StandardCharsets.UTF_8);
        } else {
            return "";
        }
    }
}
