package byow.Core;

import byow.SaveDemo.Editor;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.io.*;
import java.util.ArrayList;
//All are fine except the movement is not record.....
public class Engine {
    boolean Escape_Success;
    boolean quit_save;
    boolean Load_Mode;
    TERenderer ter = new TERenderer();
    IO_Process IO = new IO_Process();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    TETile[][] finalWorldFrame;
    String value;
    boolean Accumulate_path;
    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        Load_Mode =false;
        Draw_Menu D = new Draw_Menu(WIDTH, HEIGHT);
        StringBuilder b =new StringBuilder();;
        char s;
        StringBuilder w =new StringBuilder();
        while(true) {
            if(StdDraw.hasNextKeyTyped()){
                s = StdDraw.nextKeyTyped();
                if(s=='N'||s=='n'){
                    //Change Menu
                    D.UI_Instruction();
                    b = new StringBuilder();
                    b.append(s);
                }
                else if(s=='S'||s=='s'){
                    b.append(s);
                    System.out.print(s);
                    break;
                }
//                Load The File
                else if(s=='L'||s=='l'){
                    b.append(s);
                    break;
                }
                else{
                    b.append(s);
                }
                System.out.print(s);
            }
        }
        value = b.toString();
        interactWithInputString(value);
//        After World Generated....

        while(true){
            if(quit_save){
                String storestring = b.toString();
                saveMap(storestring);
                String movement = w.toString();
                StringBuilder p = new StringBuilder();
                for(int i=0; i<w.toString().length()-2; i++){
                    p.append(movement.charAt(i));
                }
                saveEditor(p.toString());
                System.exit(0);
            }
            if(StdDraw.hasNextKeyTyped()){
                if(Escape_Success){
                    System.exit(0);
                }
                char input_movement = StdDraw.nextKeyTyped();
                w.append(input_movement);
                System.out.print(input_movement);

                interactWithInputString(Character.toString(input_movement));
            }
        }
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        //Character.getNumericValue
        ArrayList<Character> Char_List = new ArrayList<>();
        StringBuilder b = new StringBuilder();
        for(int i=0; i<input.length(); i++){
            Char_List.add(i, input.charAt(i));
        }
//        Choose which world to explore
        if(Char_List.get(0).equals('l')||Char_List.get(0).equals('L')){
                Load_Mode = true;
                Char_List.remove(0);
                interactWithInputString(loadMap());
//            interactWithInputString(LoadMove);
                for (int i = 0; i < loadMove().length(); i++) {
                    interactWithInputString(Character.toString(loadMove().charAt(i)));
                }
                ter.renderFrame(finalWorldFrame);
                Load_Mode = false;
        }
        else if(Char_List.get(0).equals('N')||Char_List.get(0).equals('n')){
            Accumulate_path = true;
            Char_List.remove(0);
            while(Char_List.size()>0){
                b.append(Char_List.remove(0));
                if(Char_List.get(0).equals('s')||Char_List.get(0).equals('S')){
                    Char_List.remove(0);//Remove S!
                    break;
                }
            }
            String value = b.toString();
            ter.initialize(WIDTH, HEIGHT);
            MapGenerator M = new MapGenerator(WIDTH, HEIGHT, Long.parseLong(value));
            finalWorldFrame = M.return_map();
        }
//        Left for the figure moving processing (Unrelated to random world anymore! )
        Escape_Success = false;
        quit_save = false;
        //            In order to detect :q
//        $ is dirty bit
        while(!Char_List.isEmpty()) {
            char remove_char = Char_List.remove(0);
            switch (remove_char){
                case 'w': Move(finalWorldFrame, remove_char);
                        save_signal.SetFalse();
                            break;
                case 'W': Move(finalWorldFrame, remove_char);
                        save_signal.SetFalse();
                        break;
                case 'S': Move(finalWorldFrame, remove_char);
                        save_signal.SetFalse();
                        break;
                case 's': Move(finalWorldFrame, remove_char);
                        save_signal.SetFalse();
                        break;
                case 'D': Move(finalWorldFrame, remove_char);
                        save_signal.SetFalse();
                        break;
                case 'd': Move(finalWorldFrame, remove_char);
                        save_signal.SetFalse();
                        break;
                case 'A': Move(finalWorldFrame, remove_char);
                            save_signal.SetFalse();
                        break;
                case 'a': Move(finalWorldFrame, remove_char);
                            save_signal.SetFalse();
                        break;
                case 'q' :if(save_signal.flag()){
//                                IO_Process s= new IO_Process();
//                                s.saveEditor(finalWorldFrame);
                                quit_save = true;
                            }
                            break;
                case 'Q':if(save_signal.flag()){
//                                IO_Process s= new IO_Process();
//                                s.saveEditor(finalWorldFrame);
                                quit_save = true;
                                }
                            break;
                case ':': save_signal.SetTure();
                            break;
                default:
                    save_signal.SetFalse();
                    break;
            }
            if(Escape_Success){
                Draw_Menu end = new Draw_Menu(WIDTH, HEIGHT);
                end.Escape_Success();
                return finalWorldFrame;
            }
            //ter.renderFrame(finalWorldFrame);
        }
        if(Load_Mode){

            return finalWorldFrame;
        }
        ter.renderFrame(finalWorldFrame);
//        //            Only Get into this method when it is interactive mode
//        if(ArgumentArgs.Mode()){
//            return finalWorldFrame;
//        }
        return finalWorldFrame;
    }



    private void Move(TETile[][] world, char s){
//        Find the avatar
        int x=-1;
        int y=-1 ;
        for(int i=0; i<WIDTH; i++){
            if(x!=-1&&y!=-1){
                continue;
            }
            for(int j=0; j<HEIGHT; j++){
                if(world[i][j].equals(Tileset.AVATAR)){
                    x = i;
                    y = j;
                    continue;
                }
//                For Speed Up!!!
                continue;
            }
        }
        if(s=='w'||s=='W'){
            if(world[x][y+1].equals(Tileset.UNLOCKED_DOOR)){
                Escape_Success =true;
                return;
            }
            if(world[x][y+1].equals(Tileset.LOCKED_DOOR)){
                world[x][y+1] = Tileset.UNLOCKED_DOOR;
            }
            if(world[x][y+1].equals(Tileset.FLOOR)){
                world[x][y+1] = Tileset.AVATAR;
                world[x][y] = Tileset.FLOOR;
            }
        }
        else if(s=='a'||s=='A'){
            if(world[x-1][y].equals(Tileset.UNLOCKED_DOOR)){
                Escape_Success =true;
                return;
            }
            if(world[x-1][y].equals(Tileset.LOCKED_DOOR)){
                world[x-1][y] = Tileset.UNLOCKED_DOOR;
            }
            if(world[x-1][y].equals(Tileset.FLOOR)){
                world[x-1][y] = Tileset.AVATAR;
                world[x][y] = Tileset.FLOOR;
            }
        }
        else if(s=='s'||s=='S'){
            if(world[x][y-1].equals(Tileset.UNLOCKED_DOOR)){
                Escape_Success =true;
                return;
            }
            if(world[x][y-1].equals(Tileset.LOCKED_DOOR)){
                world[x][y-1] = Tileset.UNLOCKED_DOOR;
            }
            if(world[x][y-1].equals(Tileset.FLOOR)){
                world[x][y-1] = Tileset.AVATAR;
                world[x][y] = Tileset.FLOOR;
            }
        }
        else if(s=='d'||s=='D'){
            if(world[x+1][y].equals(Tileset.UNLOCKED_DOOR)){
                Escape_Success =true;
                return;
            }
            if(world[x+1][y].equals(Tileset.LOCKED_DOOR)){
                world[x+1][y] =Tileset.UNLOCKED_DOOR;
            }
            if(world[x+1][y].equals(Tileset.FLOOR)){
                world[x+1][y] = Tileset.AVATAR;
                world[x][y] = Tileset.FLOOR;
            }
        }
    }
//    Write Movement
    private void saveEditor(String world) {
        File f = new File("./save_data.txt");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            if(!Accumulate_path){
//                append
                FileOutputStream fs = new FileOutputStream(f, true);
                ObjectOutputStream os = new ObjectOutputStream(fs);
                os.writeObject(world);
            }
            else {
                FileOutputStream fs = new FileOutputStream(f);
                ObjectOutputStream os = new ObjectOutputStream(fs);
                os.writeObject(world);
            }
        }  catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }
    private void saveMap(String world) {
        if(world.length()==0||world.equals("l")||world.equals("L")){
            Accumulate_path = false;
            return;
        }
        File f = new File("./save_map.txt");
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
    private String loadMove() {
        File f = new File("./save_data.txt");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                return (String) os.readObject();
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
        /* In the case no Editor has been saved yet, we return a new one. */
        return " ";
    }
    private static String loadMap() {
        File f = new File("./save_map.txt");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                return (String) os.readObject();
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
        /* In the case no Editor has been saved yet, we return a new one. */
        return " ";
    }
}