package com.barneyb.jpixelclient.raw;

import com.barneyb.jpixelclient.json.JsonView;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;

/**
 * @author bboisvert
 */
public class SceneView {

    private final JsonView jsonView;
    private final ObjectMapper mapper;

    public SceneView() {
        jsonView = new JsonView();
        mapper = buildMapper();
    }

    public SceneView(String host, int port) {
        jsonView = new JsonView(host, port);
        mapper = buildMapper();
    }

    public SceneView(URL url) {
        jsonView = new JsonView(url);
        mapper = buildMapper();
    }

    private ObjectMapper buildMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
        mapper.setVisibility(mapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE));
        return mapper;
    }

    public void view(Scene scene) {
        view(scene, true);
    }

    private void view(Scene scene, boolean disconnect) {
        jsonView.stream(out -> {
            try {
                mapper.writeValue(out, scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, disconnect);
    }

    public void animate(Scene scene) {
        animate(scene, 100);
    }

    public void animate(Scene scene, int msPerFrame) {
        for (Frame f : scene) {
            Scene s = new Scene(scene.getBase());
            s.addFrame(f);
            view(s, false);
            try {
                Thread.sleep(msPerFrame);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        Scene scene = new Scene();
        scene.setBase(new Frame(
                Region.rect(50, 50, 500, 51)
        ));
        scene.addFrame(new Frame(
                Region.rect(100, 200, 300, 400)
        ));
        scene.addFrame(new Frame(
                Command.color(1, 0, 0),
                Region.rect(150, 250, 350, 450)
        ));
        scene.addFrame(new Frame(
                Command.thickness(3),
                Region.rect(125, 225, 325, 425)
        ));
        scene.addFrame(new Frame(
                Command.ellipse(),
                Region.ellipse(125, 225, 75, 125)
        ));
        new SceneView().view(scene);
    }

}
