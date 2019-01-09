# jpixelclient
Java bindings for http://github.com/frankamp/go-pixel-server in multiple layers.

In order to use it, you'll need a binary of the pixel server for your platform.
Start it up, and you can then use these bindings to throw scenes at it. All the
layers have an `XxxView` type with three constructors:

1.  `XxxView()` connects to `localhost:8080` (the default)
1.  `XxxView(String,int)` connects to the host and port specified
1.  `XxxView(URL)` connects to the `URL` specified.

Regardless of how a viewer is constructed, they all behave the same: a `view`
method which accepts the scene you want to view in a specific format, and sends
it over to the pixel server.

## Raw JSON Layer

The simplest binding is in the `json` module, and simply POSTs a JSON string to
the pixel server:

    new JsonView().view((
            "{'b': {'e': [{'r': [50,50,500,51]}]}," +
             "'f': [{'e': [{'c': {'n': 'shape','v': 'circle'}}," +
                          "{'r': [125,225,75,125]}]}]}"
    ).replace('\'', '"'));

It's very unlikely you want this layer; it's intended purpose is to take care of
the network underthings for the higher-level layer below. Using a `String` here
is undesireable for various reasons, and a streaming variant will become
available at some point.

## Scene Layer

The next layer is a port of the pixel server's domain model to Java objects, and
Jackson 2 bindings to allow building scenes in memory. Prepare a `Scene` and
send it over:


    Scene scene = new Scene();
    scene.setBase(new Frame(
            Region.rect(50, 50, 500, 51)
    ));
    scene.addFrame(new Frame(
            Command.ellipse(),
            Region.ellipse(125, 225, 75, 125)
    ));
    new SceneView().view(scene);

This is the layer you probably want. Behind the scenes, the object tree will be
serialized to JSON and passed to the raw layer described above.
