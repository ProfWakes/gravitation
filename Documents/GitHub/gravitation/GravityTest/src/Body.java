
public class Body {
    private int x,y,mass;

    public Body(int x,int y,int mass){
        this.x = x;
        this.y = y;
        this.mass = mass;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public int getMass(){
        return mass;
    }
}
