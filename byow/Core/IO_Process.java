package byow.Core;
import byow.SaveDemo.Editor;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.io.*;

public class IO_Process {

    TETile[][] loadEditor() {
        File f = new File("./save_data.txt");
        TETile[][] world;
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                TETile[][] s = (TETile[][]) os.readObject();
                for(int i=0; i<s.length; i++){
                    for(int j=0; j<s[0].length; j++){
                        if(s[i][j].equals(Tileset.WALL)){
                            s[i][j] = Tileset.WALL;
                        }
                        if(s[i][j].equals(Tileset.NOTHING)){
                            s[i][j] = Tileset.NOTHING;
                        }
                        if(s[i][j].equals(Tileset.FLOOR)){
                            s[i][j] = Tileset.FLOOR;
                        }
                        if(s[i][j].equals(Tileset.LOCKED_DOOR)){
                            s[i][j] = Tileset.LOCKED_DOOR;
                        }
                        if(s[i][j].equals(Tileset.UNLOCKED_DOOR)){
                            s[i][j] = Tileset.UNLOCKED_DOOR;
                        }
                        if(s[i][j].equals(Tileset.AVATAR)){
                            s[i][j] = Tileset.AVATAR;
                        }
                    }
                }
                return s;
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }

//         In the case no Editor has been saved yet, we return a new one.
        return null;
    }




    public void saveEditor(String world) {
        File f = new File("./save_data.txt");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(world);
        }  catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }
}
