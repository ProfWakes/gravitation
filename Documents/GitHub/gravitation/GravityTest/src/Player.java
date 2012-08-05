import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.event.KeyEvent;

public class Player {
    private int dRotation,rotation,dx,dy,x,y,dHyp,health,dims,mass;
    private double diagonal;
    private Image image;
    private boolean CW,ACW;
    public int pullX,pullY;

    public Player(int x,int y){
        dRotation = 0;
        dx = 0;
        dy = 0;
        dHyp = 0;
        this.x = x;
        this.y = y;
        health = 10;
        mass = 3;
        dims = 16 * mass;
        diagonal = (Math.pow(2,0.5)) * dims;
        rotation = 0;
        CW = false;
        ACW = false;
        pullX = 0;
        pullY = 0;
        ImageIcon ii = new ImageIcon(this.getClass().getResource("Space_ship.png"));
        image = ii.getImage();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Image getImage() {
        return image;
    }
    
    public int getDims() {
        return dims;
    }
    
    public int getMass() {
        return mass;
    }

    public void drawPlayer(Graphics2D g2d, ImageObserver imageObserver) {
        g2d.translate(500-Math.round(Math.sin(Math.toRadians(45-rotation))*diagonal/2),350-Math.round(Math.cos(Math.toRadians(45-rotation))*diagonal/2));
        g2d.rotate(Math.toRadians(rotation));
        g2d.drawImage(getImage(), 0, 0, getDims(), getDims(), imageObserver);
    }

    public void move() {
        if (ACW && dRotation > -10 || !CW && dRotation > 0){
            dRotation -= 1;
        }
        if (CW && dRotation < 10 || !ACW && dRotation < 0){
            dRotation += 1;
        }
        if (x < -3533 && dx < 0){
            dx = 0;
        }
        if (y < - 2042 && dy < 0){
            dy = 0;
        }
        if (x > 2533 && dx > 0){
            dx = 0;
        }
        if (y > 1342 && dy > 0){
            dy = 0;
        }
        dx += pullX;
        dy += pullY;
        rotation += dRotation;
        x += dx;
        y += dy;
    }
    public void press (KeyEvent e){
        int key = e.getKeyCode();
        int ddx = (int) Math.round(Math.sin(Math.toRadians(rotation))*2);
        int ddy = (int) -(Math.round(Math.cos(Math.toRadians(rotation))*2));
        if (key == KeyEvent.VK_UP && (dHyp < 10 || dHyp > Math.pow((Math.pow(dx + ddx,2) + Math.pow(dy + ddy,2)),0.5))){
            dx += ddx;
            dy += ddy;
            dHyp = (int) Math.round(Math.pow((Math.pow(dx,2)+Math.pow(dy,2)),0.5));
        }
        if (key == KeyEvent.VK_DOWN){
            dx -= dx/2;
            dy -= dy/2;
            dHyp = (int) Math.round(Math.pow((Math.pow(dx,2)+Math.pow(dy,2)),0.5));
        }
        if (key == KeyEvent.VK_LEFT) {
            ACW = true;
        }
        if (key == KeyEvent.VK_RIGHT) {
            CW = true;
        }
    }
    
    public void release(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            ACW = false;
        }
        if (key == KeyEvent.VK_RIGHT) {
            CW = false;
        }
    }
}
