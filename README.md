# jpixelclient
Java bindings for http://github.com/frankamp/go-pixel-server in multiple layers.

In order to use it, you'll need a binary of the pixel server for your platform.
Start it up, and you can then use these bindings to throw scenes at it.

## Raw JSON

The simplest binding is in the `json` module, and simply POSTs a JSON string to
the pixel server:

    new JsonView().view("{ ... scene ... }");

