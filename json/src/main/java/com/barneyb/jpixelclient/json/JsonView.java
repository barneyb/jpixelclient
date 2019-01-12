package com.barneyb.jpixelclient.json;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.function.Consumer;

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
        stream(out -> {
            try {
                out.write(json);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void stream(Consumer<Writer> work) {
        stream(work, true);
    }

    public void stream(Consumer<Writer> work, boolean disconnect) {
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.connect();
            try (OutputStream o = conn.getOutputStream()) {
                work.accept(new BufferedWriter(new OutputStreamWriter(o)));
            }
            //noinspection ResultOfMethodCallIgnored
            conn.getInputStream().read(); // have to read something
            if (disconnect) conn.disconnect();
        } catch (ConnectException ce) {
            throw new RuntimeException(String.format(
                    "Couldn't connect to pixel server at %s",
                    url
            ), ce);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
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
