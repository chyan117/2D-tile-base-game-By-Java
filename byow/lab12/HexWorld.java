package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.awt.*;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static Random RANDOM = new Random();
    private static TETile selectSET(){
        int tileNum = RANDOM.nextInt(6);
            switch (tileNum) {
                case 0:
                    return Tileset.WALL;
                case 1:
                    return Tileset.FLOWER;
                case 2:
                    return Tileset.GRASS;
                case 3:
                    return Tileset.AVATAR;
                case 4:
                    return Tileset.MOUNTAIN;
                case 5:
                    return Tileset.SAND;
                default:
                    return Tileset.NOTHING;
            }
    }
    private static void addHexagon(TETile[][] world, Position P, int size){
        //the LeftUp
        TETile te = selectSET();
        if(size<2){
            throw new IllegalArgumentException("the size must bigger than 2");
        }
        int OffSet = size;
        int Starting_x = P.get_X();
        int Starting_y = P.get_Y();
        int OffSet_inner = 0;
        //Fill in with two parts
        for(int i=size; i>=1 ; i--){
            for(int j=0; j<i*2; j++) {
                world[Starting_x-OffSet_inner][Starting_y - j- OffSet_inner] = te;
                world[Starting_x+OffSet_inner+(OffSet-1) ][Starting_y - j- OffSet_inner] = te;
            }
            OffSet_inner = OffSet_inner+1;
        }
        //Fill in the Middle
        for(int i=1; i<size ; i++){
            for(int j=0; j<size*2; j++)
            world[Starting_x+i][Starting_y-j] = te;
        }
    }
    private static void add_row_Hexagon(TETile[][] world, Position P, int size, int row_num){
        for(int i=0; i<row_num; i++) {
            Position p_now = new Position(P.get_X(), P.get_Y()-size*2*i);
            addHexagon(world, p_now, size);
        }
    }
    private static void Fillin_Left_of_graph(int x, int y, TETile[][] world, int size){
        for(int i=0; i<3; i++){
            Position P = new Position(x, y);
            TETile t = new TETile('@', Color.black, Color.white, "test");
            HexWorld.add_row_Hexagon(world, P, size, 3+i);
            x = x + (size * 2 - 1);
            y = y + size;
        }
    }
    private static void Fillin_Right_of_grapgh(int x, int y, TETile[][] world, int size){
        for(int i=0; i<2; i++){
            Position P3 = new Position(x, y);
            TETile t3 = new TETile('@', Color.black, Color.white, "test");
            HexWorld.add_row_Hexagon(world, P3, size, 4-i);
            x = x + (size * 2 - 1);
            y = y - size;
        }
    }
    private static void Straw_Whole_Plot(int x, int y, TETile[][] world, int size) {
        Fillin_Left_of_graph(x, y, world, size);
        //After Finishing the left part: it's x, y's position
        x = x +3*(size * 2 - 1);
        y = y + size;
        Fillin_Right_of_grapgh(x, y, world, size);
    }

    public static void Draw(int WIDTH, int HEIGHT, TETile[][] world, int size){
        Straw_Whole_Plot(5, HEIGHT-size*3, world, size);
    }



}
