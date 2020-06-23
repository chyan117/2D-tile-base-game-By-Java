package byow.Core;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
//UI Class
public class Draw_Menu {
    int WIDTH, HEIGHT;
    public Draw_Menu(int WIDTH, int HEIGHT){
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);
        Font bigFont = new Font("Monaco", Font.BOLD, 40);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text( WIDTH/2, HEIGHT-4, "CS61B: The Game");
        Font Fontsmall = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(Fontsmall);
        StdDraw.text( WIDTH/2, HEIGHT/2, "New Game (N)");
        StdDraw.text( WIDTH/2, HEIGHT/2-2, "Load Game (L)");
        StdDraw.text( WIDTH/2, HEIGHT/2-4, "Quit (:Q)");
        StdDraw.enableDoubleBuffering();
        StdDraw.show();
    }
    void UI_Instruction(){
        StdDraw.clear(Color.BLACK);
        Font bigFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text( WIDTH/2, HEIGHT/2+2, "Please Enter A Random Seed! ");
        Font smallFont = new Font("Monaco", Font.CENTER_BASELINE, 15);
        StdDraw.setFont(smallFont);
        StdDraw.text( WIDTH/2, HEIGHT/2-4, "Note: Seed Must be less than: 9,223,372,036,854,775,807 ");
        StdDraw.text( WIDTH/2, HEIGHT/2-6, " (Once you Finish, Enter 'S' for Watch your World) ");
        StdDraw.enableDoubleBuffering();
        StdDraw.show();
    }
    void Escape_Success(){
        StdDraw.clear(Color.BLACK);
        Font bigFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text( WIDTH/2, HEIGHT/2, "Yeeee! You escape Successfully!!!");
        Font SmallFont = new Font("Monaco", Font.BOLD, 25);
        StdDraw.setFont(SmallFont);
        StdDraw.setPenColor(Color.yellow);
        StdDraw.text( WIDTH/2, HEIGHT/2-4, " Press any Button to Leave .....");
        StdDraw.enableDoubleBuffering();
        StdDraw.show();
    }

}
