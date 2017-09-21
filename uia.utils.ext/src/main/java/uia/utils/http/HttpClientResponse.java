package uia.utils.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;

public class HttpClientResponse {

    private final HttpResponse heepResponse;

    HttpClientResponse(HttpResponse httpResponse) {
        this.heepResponse = httpResponse;
    }

    public int getStatusCode() {
        return this.heepResponse.getStatusLine().getStatusCode();
    }

    public String getContent(String charsetName) throws IOException {
        if (this.heepResponse.getEntity() == null) {
            return null;
        }

        InputStream inputStream = this.heepResponse.getEntity().getContent();

        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        // StandardCharsets.UTF_8.name() > JDK 7
        return result.toString(charsetName);
    }

    public InputStream getContentStream() throws IOException {
        if (this.heepResponse.getEntity() == null) {
            return null;
        }

        return this.heepResponse.getEntity().getContent();
    }
}
