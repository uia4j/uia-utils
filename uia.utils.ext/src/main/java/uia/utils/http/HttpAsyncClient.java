package uia.utils.http;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

public class HttpAsyncClient {

    private int retryCount;

    private String url;

    private CloseableHttpAsyncClient client;

    public HttpAsyncClient(String url) {
        this.retryCount = 3;
        this.url = url;
        this.client = HttpAsyncClients.createDefault();

        this.client.start();
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

    public void get(String command, FutureCallback<HttpClientResponse> callback) throws IOException {
        HttpGet getMethod = new HttpGet(this.url + command);
        getMethod.addHeader("accept", "application/json");

        execute(getMethod, callback);
    }

    public void postJson(String command, String json, FutureCallback<HttpClientResponse> callback) throws IOException {
        StringEntity requestEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
        HttpPost postMethod = new HttpPost(this.url + command);
        postMethod.setEntity(requestEntity);

        execute(postMethod, callback);
    }

    public void postXml(String command, String json, FutureCallback<HttpClientResponse> callback) throws IOException {
        StringEntity requestEntity = new StringEntity(json, ContentType.APPLICATION_XML);
        HttpPost postMethod = new HttpPost(this.url + command);
        postMethod.setEntity(requestEntity);

        execute(postMethod, callback);
    }

    public void delete(String command, FutureCallback<HttpClientResponse> callback) throws IOException {
        HttpDelete deleteMethod = new HttpDelete(this.url + command);
        deleteMethod.addHeader("accept", "application/json");

        execute(deleteMethod, callback);
    }

    private void execute(HttpUriRequest request, final FutureCallback<HttpClientResponse> callback) throws IOException {
        int rc = this.retryCount;
        while (true) {
            rc--;
            try {
                this.client.execute(request, new FutureCallback<HttpResponse>() {

                    @Override
                    public void cancelled() {
                        callback.cancelled();
                    }

                    @Override
                    public void completed(HttpResponse response) {
                        callback.completed(new HttpClientResponse(response));
                    }

                    @Override
                    public void failed(Exception ex) {
                        callback.failed(ex);
                    }

                });
                break;
            }
            catch (Exception ex) {
                if (rc < 1) {
                    throw ex;
                }
            }
        }
    }
}
