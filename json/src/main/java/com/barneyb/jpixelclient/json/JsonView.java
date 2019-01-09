package com.barneyb.jpixelclient.json;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;

/**
 * @author bboisvert
 */
public class JsonView {

    private final URL url;

    public JsonView() {
        this("localhost", 8080);
    }

    public JsonView(String host, int port) {
        try {
            this.url = new URL("http", host, port, "/scene");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public JsonView(URL url) {
        this.url = url;
    }

    public void view(String json) {
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            byte[] bytes = json.getBytes();
            conn.setFixedLengthStreamingMode(bytes.length);
            conn.connect();
            try (OutputStream o = conn.getOutputStream()){
                o.write(bytes);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        File f = new File("json/scene.json");
        if (! f.exists()) {
            throw new IllegalArgumentException("File not found '" + f.getAbsolutePath() + '\'');
        }
        String scene = new String(Files.readAllBytes(f.toPath()));
        new JsonView().view(scene);
    }

}
