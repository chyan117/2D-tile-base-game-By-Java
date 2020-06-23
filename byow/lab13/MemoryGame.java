package byow.lab13;

import edu.princeton.cs.introcs.StdDraw;
import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private int num;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }
        int seed = Integer.parseInt(args[0]);
        Random RANDOM =new Random(seed);
        MemoryGame game = new MemoryGame(40, 40, RANDOM);
        game.startGame();
    }

    public MemoryGame(int width, int height, Random r) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 40);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        rand = r;
        playerTurn = false;
        //TODO: Initialize random number generator
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        StringBuilder s = new StringBuilder();
        for(int i=0; i<n; i++){
            s = s.append( CHARACTERS[rand.nextInt(CHARACTERS.length)] );
        }
        return s.toString();
    }
    private void basic_pic(){
        StdDraw.text( 3, height-1, "Round" + round);
        StdDraw.line(0, height-2, width, height-2);
        num = rand.nextInt(6);
        StdDraw.text( width-5, height-1, ENCOURAGEMENT[num]);
    }
    private void basic_pic0(){
        StdDraw.text( 3, height-1, "Round" + round);
        StdDraw.line(0, height-2, width, height-2);
        StdDraw.text( width-5, height-1, ENCOURAGEMENT[num]);
    }
    private void basic_pic_1(String s){
        Font bigFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.clear(Color.BLACK);
        StdDraw.text( width/2, height/2, s);
        StdDraw.setFont(new Font(s, Font.BOLD, 16));
        basic_pic0();
        if(playerTurn){
            StdDraw.text( width/2, height-1, "Type! " );
        }
        else {
            StdDraw.text(width / 2, height - 1, "Watch! ");
        }
        StdDraw.show();
    }
    public void drawFrame(String s) {
        StdDraw.clear(Color.BLACK);
        StdDraw.setFont(new Font(s, Font.BOLD, 30));
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text( width/2, height/2, s);
        StdDraw.clear(Color.BLACK);
        StdDraw.setFont(new Font(s, Font.BOLD, 16));
        StdDraw.setPenColor(Color.WHITE);
        if(playerTurn){
            basic_pic0();
            StdDraw.text( width/2, height-1, "Type! " );
        }
        else{
            basic_pic();
            StdDraw.text( width/2, height-1, "Watch! " );
        }
        StdDraw.show();
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
    }

    public void flashSequence(String letters) {
        for(int i=0; i<letters.length(); i++) {
            basic_pic_1(Character.toString(letters.charAt(i)));
            StdDraw.pause(1000);
            StdDraw.clear(Color.BLACK);
            StdDraw.pause(500);
        }
        basic_pic0();
        StdDraw.text( width/2, height-1, "Type! " );
        StdDraw.show();
        //TODO: Display each character in letters, making sure to blank the screen between letters
    }

    public String solicitNCharsInput(int n) {
        int i =0;
        StringBuilder b = new StringBuilder();
        playerTurn = true;
        while(i<n){
            if(StdDraw.hasNextKeyTyped()) {
                b.append(StdDraw.nextKeyTyped());
                basic_pic_1(b.toString());
                playerTurn = true;
                i = i + 1;
            }
        }
        playerTurn = false;
        //TODO: Read n letters of player input
        return b.toString();
    }

    public void startGame() {
        round=0;
        String s;
        String answer;
        //TODO: Set any relevant variables before the game starts
        do {
            round = round+1;
            Font bigFont = new Font("Monaco", Font.BOLD, 40);
            StdDraw.setFont(bigFont);
            StdDraw.setPenColor(Color.white);
            StdDraw.clear(Color.BLACK);
            StdDraw.text( width/2, height/2,"round"+round);
            StdDraw.show();
            StdDraw.pause(1000);
            drawFrame("Round" + round);
            StdDraw.show();
            s = generateRandomString(round);
            flashSequence(s);
            answer = solicitNCharsInput(round);
            StdDraw.pause(500);
            gameOver = !(answer.equals(s));
        } while( !gameOver );
        StdDraw.clear(Color.BLACK);
        Font bigFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text( width/2, height/2, "Your game is over at " + round);
        StdDraw.show();
    }
}
