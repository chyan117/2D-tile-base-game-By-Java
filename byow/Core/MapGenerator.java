package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class MapGenerator {
    int x_aix_length;
    int y_aix_length;
    Room Room_template;
    private TETile[][] world;
    private Random RANDOM;
    List<Room> Garbage_Collection;
    List<Room> Room_Collection;
    public MapGenerator(int row, int column, long SEED) {
        this.x_aix_length = row;
        this.y_aix_length = column;
        RANDOM = new Random(SEED);
        TERenderer ter = new TERenderer();
        ter.initialize(row, column);
        world = new TETile[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
        Random_room_Placement(RANDOM);
        //System.out.println(Room_Collection.size());
        for(Room r: Room_Collection){
            Draw_Room(world, r);
        }

//        Connect_TWO(world, Room_Collection.get(2), Room_Collection.get(3));
//        Connect_TWO(world, Room_Collection.get(3), Room_Collection.get(4));
//        Connect_TWO(world, Room_Collection.get(4), Room_Collection.get(5));
//        Connect_TWO(world, Room_Collection.get(5), Room_Collection.get(6));
//        world[Room_Collection.get(5).Center_X()][Room_Collection.get(5).Center_Y()]= Tileset.WATER;
//        world[Room_Collection.get(6).Center_X()][Room_Collection.get(6).Center_Y()]= Tileset.WATER;
        Connect(world, Room_Collection);
        Make_Avatar_Door();
//        System.out.println(Room_Collection.size());
//        for(Room r: Room_Collection){
//            world[r.Center_X()][r.Center_Y()]= Tileset.WATER;
//            world[r.Position_v().X_value()][r.Position_v().Y_value()]= Tileset.SAND;
//        }
    }
    private void Make_Avatar_Door(){
        int x, y;
        do{
            x = RandomUtils.uniform(RANDOM, 2, x_aix_length-2);
            y = RandomUtils.uniform(RANDOM,2, y_aix_length-2);
        }while(!world[x][y].equals(Tileset.FLOOR));
        world[x][y] = Tileset.AVATAR;
        do{
            x = RandomUtils.uniform(RANDOM, 2, x_aix_length-2);
            y = RandomUtils.uniform(RANDOM,2, y_aix_length-2);
        }while(
//                Choose one of the Wall to be Door
                !(world[x][y].equals(Tileset.WALL) ) || ! (
//                At least Reachable && Connect to outside World
                world[x+1][y].equals(Tileset.FLOOR) ||
                world[x-1][y].equals(Tileset.FLOOR) ||
                world[x][y+1].equals(Tileset.FLOOR) ||
                world[x][y-1].equals(Tileset.FLOOR)  ) ||
                        !(
                        world[x+1][y].equals(Tileset.NOTHING) ||
                                world[x-1][y].equals(Tileset.NOTHING) ||
                                world[x][y+1].equals(Tileset.NOTHING) ||
                                world[x][y-1].equals(Tileset.NOTHING)  )
        )   ;
        world[x][y] = Tileset.LOCKED_DOOR;
    }

    public TETile[][] return_map() {
        return world;
    }

    private void Random_room_Placement(Random RANDOM){
//        Produce 2~4 rooms
        Room_Collection = new ArrayList<>();
        int Room_num = RandomUtils.uniform(RANDOM,10, 20);
        do{
            Room_template = Generate_New_Room_Property();
            while(Room_template.CheckOutOfBound(x_aix_length, y_aix_length)) {
                Room_template = Generate_New_Room_Property();
            }
            Garbage_Collection = new ArrayList<>();
            for (Room item : Room_Collection) {
                if(item.overlap(Room_template)||Room_template.overlap(item)) {
                    Garbage_Collection.add(item);
                }
            }
            for(Room item: Garbage_Collection){
                Room_Collection.remove(item);
            }
            Room_Collection.add(Room_template);
        }while(Room_Collection.size()<Room_num);
    }
    private Room Generate_New_Room_Property(){
        int Width = RandomUtils.uniform(RANDOM,3, 12);
        int Height = RandomUtils.uniform(RANDOM,3, 12);
        //Choose Bottom Left Position
        int x = RandomUtils.uniform(RANDOM, 2, x_aix_length);
        int y = RandomUtils.uniform(RANDOM,2, y_aix_length);
        Position p = new Position( x, y);
        Room Room_template = new Room(p , Width, Height);
        return Room_template;
    }
    private void Draw_Room(TETile[][] world, Room Room){
        for(int i=0; i<Room.Width_v(); i++){
            for(int j=0; j<Room.Height_v(); j++){
                if(i==0||i==Room.Width_v()-1||j==0|| j==Room.Height_v()-1){
                    world[i+Room.Position_v().X_value()][j+Room.Position_v().Y_value()] = Tileset.WALL;
                    continue;
                }
                world[i+Room.Position_v().X_value()][j+Room.Position_v().Y_value()] = Tileset.FLOOR;
            }
            //world[Room.Center_X()][Room.Center_Y()] = Tileset.WATER;
        }
    }
    private void Connect(TETile[][] world, List Room_Collection){
        ArrayList<Room> World_Spot = (ArrayList<Room>)Room_Collection;
        Room Match_Standard = World_Spot.remove(0);
        while(World_Spot.size()>0){
            Connect_TWO(world, Match_Standard, World_Spot.get(0));
            Match_Standard = World_Spot.remove(0);
        }
    }
//    Connect two room!
//    Room
    private void Connect_TWO(TETile[][] world, Room first, Room second ){
        int f_x = first.Center_X();
        int f_y = first.Center_Y();
        int s_x = second.Center_X();
        int s_y = second.Center_Y();
//        Connect fist and second's center
        int OffSet_X = Math.abs(f_x - s_x);
        int OffSet_Y = Math.abs(f_y - s_y);
        int x ;
        int y ;
        boolean relation_for_UR_LL= false;
        if(f_x>=s_x&&f_y>=s_y || s_x>=f_x&&s_y>=f_y){
            relation_for_UR_LL= true;
        }
        if(relation_for_UR_LL) {
            if(f_x>=s_x&&f_y>=s_y) {
                x = s_x;
                y = s_y;
            }
            else{
                x = f_x;
                y = f_y;
            }
            for (int i = 0; i <= OffSet_X; i++) {
                world[x + i][y] = Tileset.FLOOR;
                if (world[x + i][y + 1].equals(Tileset.FLOOR)) {
                } else {
                    world[x + i][y + 1] = Tileset.WALL;
                }
                if (world[x + i][y - 1].equals(Tileset.FLOOR)) {
                } else {
                    world[x + i][y - 1] = Tileset.WALL;
                }
            }
            for (int i = 0; i <= OffSet_Y; i++) {
//                System.out.println(OffSet_Y);
//                System.out.println(i);
//                System.out.println("f_x is : "+ f_x);
//                System.out.println("f_y is : "+ f_y);
//                System.out.println("s_x is : "+ s_x);
//                System.out.println("s_y is : "+ s_y);
                world[x+OffSet_X][y + i] = Tileset.FLOOR;
                if (world[x + 1+OffSet_X][y + i].equals(Tileset.FLOOR)) {
                } else {
                    world[x + 1+OffSet_X][y + i] = Tileset.WALL;
                }

                if (world[x - 1+OffSet_X][y + i].equals(Tileset.FLOOR)) {
                } else {
                    world[x - 1+OffSet_X][y + i] = Tileset.WALL;
                }
            }
        }
        else {
            if(f_x>s_x) {
                x = s_x;
                y = s_y;
            }
            else{
                x = f_x;
                y = f_y;
            }
            for (int i = 0; i <= OffSet_X; i++) {
                world[x + i][y] = Tileset.FLOOR;
                if (world[x + i][y + 1].equals(Tileset.FLOOR)) {
                } else {
                    world[x + i][y + 1] = Tileset.WALL;
                }
                if (world[x + i][y - 1].equals(Tileset.FLOOR)) {
                } else {
                    world[x + i][y - 1] = Tileset.WALL;
                }
            }
            if(f_y>s_y) {
                x = s_x;
                y = s_y;
            }
            else{
                x = f_x;
                y = f_y;
            }
            for (int i = 0; i <= OffSet_Y; i++) {
                world[x][y + i] = Tileset.FLOOR;
                if (world[x + 1][y + i].equals(Tileset.FLOOR)) {
                } else {
                    world[x + 1][y + i] = Tileset.WALL;
                }

                if (world[x - 1][y + i].equals(Tileset.FLOOR)) {
                } else {
                    world[x - 1][y + i] = Tileset.WALL;
                }
            }
        }

    }
}