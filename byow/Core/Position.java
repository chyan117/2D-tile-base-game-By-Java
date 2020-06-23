package byow.Core;

public class Position {
    private int x;
    private int y;
//    BottomLeft Position of a subspace
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }
    public int X_value(){
        return x;
    }
    public int Y_value(){
        return y;
    }
    @Override
    public boolean equals(Object o){
        if(o==this){
            return true;
        }
        if(this.getClass()!=o.getClass()){
            return false;
        }
        if(o==null){
            return false;
        }
        Position comparator = (Position) o;
        if(comparator.X_value()!=this.x || comparator.Y_value()!=this.y){
            return false;
        }
        if(comparator.hashCode()!=this.hashCode()){
            return false;
        }
        return true;
    }
    @Override
    public int hashCode(){
        return 7*this.x*31+this.y*31*31;
    }
}