package uia.utils.http;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpClient {

    private int retryCount;

    private String url;

    private CloseableHttpClient client;

    public HttpClient(String url) {
        this.retryCount = 3;
        this.url = url;
        HttpClientBuilder builder = HttpClientBuilder.create();
        this.client = builder.build();
    }

    public HttpClient(String url, HttpClientBuilder builder) {
        this.url = url;
        this.client = builder.build();
    }

    public void shutdown() throws IOException {
        this.client.close();
    }

    public int getRetryCount() {
        return this.retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public HttpClientResponse get(String command) throws IOException {
        HttpGet getMethod = new HttpGet(this.url + command);
        return execute(getMethod);
    }

    public HttpClientResponse postJson(String command, String json) throws IOException {
        StringEntity requestEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
        HttpPost postMethod = new HttpPost(this.url + command);
        postMethod.setEntity(requestEntity);

        return execute(postMethod);
    }

    public HttpClientResponse postXml(String command, String json) throws IOException {
        StringEntity requestEntity = new StringEntity(json, ContentType.APPLICATION_XML);
        HttpPost postMethod = new HttpPost(this.url + command);
        postMethod.setEntity(requestEntity);

        return execute(postMethod);
    }

    public HttpClientResponse delete(String command) throws IOException {
        HttpDelete deleteMethod = new HttpDelete(this.url + command);
        return execute(deleteMethod);
    }

    private HttpClientResponse execute(HttpUriRequest request) throws IOException {
        HttpResponse response;

        int rc = this.retryCount;
        while (true) {
            rc--;
            try {
                response = this.client.execute(request);
                break;
            }
            catch (IOException ex) {
                if (rc < 1) {
                    throw ex;
                }
            }
        }

        return new HttpClientResponse(response);
    }
}
