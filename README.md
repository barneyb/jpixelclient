# jpixelclient
Java bindings for http://github.com/frankamp/go-pixel-server in multiple layers.

You'll need Maven and a JDK to build, a JRE to run. Of course. I've used Maven
3.3 and 3.6 along with JDK 1.8 and 11. Others will probably work. To start:

    git clone git@github.com:barneyb/jpixelclient.git
    cd jpixelclient
    mvn clean install

Then add this to your project's POM (or gradle, or whatever):

    <dependency>
        <groupId>com.barneyb.jpixelclient</groupId>
        <artifactId>raw</artifactId>
        <version>0.1.0-SNAPSHOT</version>
    </dependency>

You'll also need a binary of Josh's pixel server for your platform. Start it up,
and you can then use these bindings to throw scenes at it. All the layers have
an `XxxView` type with three constructors:

1.  `XxxView()` connects to `localhost:8080` (the default)
1.  `XxxView(String,int)` connects to the host and port specified
1.  `XxxView(URL)` connects to the `URL` specified.

Regardless of how a viewer is constructed, or what layer it comes from, they all
behave about the same: a `view` method which accepts the scene you want to view
in a specific format, and sends it over to the pixel server.

## JSON Layer (`json`)

The simplest binding is in the `json` module, and simply POSTs a JSON string to
the pixel server:

    new JsonView().view((
            "{'b': {'e': [{'r': [50,50,500,51]}]}," +
             "'f': [{'e': [{'c': {'n': 'shape','v': 'circle'}}," +
                          "{'r': [125,225,75,125]}]}]}"
    ).replace('\'', '"'));

It's very unlikely you want this layer; it's intended purpose is to take care of
the network underthings for the higher-level layer below.

In addition to the `view(String)` method, `stream(Consumer<Writer>)` is also
provided if you wish to dodge the cost of building a potentially-large throwaway
String.

## Scene Layer (`raw`)

The next layer is a port of the pixel server's viewer model to Java, including
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

Note that this is based of the _viewer_, not the _server_, and thus disallows
elements with both a command and a region (coords).
