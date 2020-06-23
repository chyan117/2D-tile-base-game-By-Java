package byow.Core;

public class Room {
    int Width;
    int Height;
    Position p;
    public Room(Position p, int Width, int Height){
        this.p = p;
        this.Height = Height;
        this.Width = Width;
    }
    public Position Position_v(){
        return p;
    }

    public int Width_v(){
        return Width;
    }
    public int Height_v(){
        return Height;
    }
    public int Center_X(){
        return p.X_value()+Width/2;
    }
    public int Center_Y(){
        return p.Y_value()+Height/2;
    }
    public boolean overlap(Room others){
        int this_B_L_X = p.X_value();
        int this_B_L_Y = p.Y_value();
        int this_U_R_X = p.X_value()+Width;
        int this_U_R_Y = p.Y_value()+Height;
        if(     (   (this_B_L_X<=others.Position_v().X_value() )
                && (others.Position_v().X_value()<=this_U_R_X)
                && (this_B_L_Y<=others.Position_v().Y_value() )
                && (others.Position_v().Y_value()<=this_U_R_Y) )
                ||
                ((//the U R of others
                this_B_L_X<=others.Position_v().X_value()+others.Width_v() )
                && (others.Position_v().X_value()+others.Width_v()<=this_U_R_X)
                && (this_B_L_Y<=others.Position_v().Y_value()+others.Height )
                && (others.Position_v().Y_value()+others.Height<=this_U_R_Y))
                ||
                ((//the U L of others
                        this_B_L_X<=others.Position_v().X_value() )
                        && (others.Position_v().X_value()<=this_U_R_X)
                        && (this_B_L_Y<=others.Position_v().Y_value()+others.Height )
                        && (others.Position_v().Y_value()+others.Height<=this_U_R_Y))
                ||
                ((//the L R of others
                        this_B_L_X<=others.Position_v().X_value()+others.Width_v() )
                        && (others.Position_v().X_value()+others.Width_v()<=this_U_R_X)
                        && (this_B_L_Y<=others.Position_v().Y_value() )
                        && (others.Position_v().Y_value()<=this_U_R_Y))

                                ||
                ((//Center is inside other or not
                this_B_L_X<=others.Center_X() )
                && (others.Center_X()<=this_U_R_X)
                && (this_B_L_Y<=others.Center_Y() )
                && (others.Center_Y()<=this_U_R_Y))
                )
                {
            return true;
        }
        return false;
    }
    public boolean CheckOutOfBound(int width_bound, int height_bound){
        if((p.X_value()>=width_bound-2 ) || (p.Y_value()>=height_bound-2 )||
                (p.X_value()+Width>=width_bound-2 )
                || (p.Y_value()+Height>=height_bound-2) ){
            return true;
        }
        return false;
    }
}
