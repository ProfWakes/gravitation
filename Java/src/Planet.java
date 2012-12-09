import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class Planet {
    private int x,y,dx,dy,width,height,mass;
    public int pullX,pullY;
    private Image image;
    
    public Planet(int x,int y, int mass) {
        this.x = x;
        this.y = y;
        dx = 0;
        dy = 0;
        this.mass = mass;
        width = 15;
        height = 15;
        pullX = 0;
        pullY = 0;
        ImageIcon ii = new ImageIcon(this.getClass().getResource("Planet_Ceres.png"));
        image = ii.getImage();
    }
    
    public Image getImage(){
        return image;
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
    
    public int getWidth(){
        return width;
    }
    
    public int getHeight(){
        return height;
    }
    
    public void drawPlanet(Graphics2D g2d, int displayX, int displayY, ImageObserver imageObserver){
        int CornerX = 500 + displayX - Math.round(width * mass/2);
        int CornerY = 350 + displayY - Math.round(height * mass/2);
        g2d.drawImage(getImage(),CornerX ,CornerY,CornerX + getWidth()*getMass(),CornerY + getHeight() * getMass(), 0, 0, getWidth(), getHeight(), imageObserver);
    }

    public void move(){
        dx += pullX;
        dy += pullY;
        x += dx;
        y += dy;
    }
}
