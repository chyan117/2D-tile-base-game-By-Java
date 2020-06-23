package byow.Core;

import byow.TileEngine.TERenderer;

public class MapVisualTest {

    public static void main(String[] args) {
        final int x_aix_length = 80;
        final int y_aix_length = 30;
        TERenderer ter = new TERenderer();
        ter.initialize(x_aix_length, y_aix_length);
        MapGenerator M = new MapGenerator(x_aix_length, y_aix_length, 78080);
        ter.renderFrame(M.return_map());
    }
}
