import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Panel extends JPanel implements ActionListener {

    public Player player;
    public Planet planetOne;
    private Image space;
    private int bkgWidth,bkgHeight;

    public Panel(){
        ImageIcon ii = new ImageIcon(this.getClass().getResource("The_Final_Frontier.png"));
        space = ii.getImage();
        player = new Player(0,0);
        planetOne = new Planet(500,100,10);
        bkgHeight = 1692;
        bkgWidth = 3033;
        addKeyListener(new adapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        Timer timer = new Timer(25,this);
        timer.start();
    }

    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(space, -player.getX(), -player.getY(), bkgWidth-player.getX(), bkgHeight-player.getY(), 0, 0, 1011, 564, this);
        g2d.drawImage(space, -bkgWidth-player.getX(), -player.getY(), -player.getX(), bkgHeight-player.getY(), 0, 0, 1011, 564, this);
        g2d.drawImage(space, -player.getX(), -bkgHeight-player.getY(), bkgWidth-player.getX(),-player.getY(), 0, 0, 1011, 564, this);
        g2d.drawImage(space, -bkgWidth-player.getX(), -bkgHeight-player.getY(), -player.getX(), -player.getY(), 0, 0, 1011, 564, this);
        planetOne.drawPlanet(g2d,planetOne.getX()-player.getX(),planetOne.getY()-player.getY(),this);
        player.drawPlayer(g2d,this);
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    public void actionPerformed(ActionEvent e) {
        player.pullX = 0;
        player.pullY = 0;
        planetOne.pullX = 0;
        planetOne.pullY = 0;
        Body ship = new Body(player.getX(),player.getY(),player.getMass());
        Body bodyOne = new Body(planetOne.getX(),planetOne.getY(),planetOne.getMass());
        int force[] = attraction(ship,bodyOne);
        player.pullX += force[0];
        player.pullY += force[1];
        planetOne.pullX += force[2];
        planetOne.pullY += force[3];
        int bounce[] = bounce(ship,bodyOne);
        player.pullX += bounce[0];
        player.pullY += bounce[1];
        planetOne.pullX += bounce[2];
        planetOne.pullY += bounce[3];
        player.move();
        planetOne.move();
        repaint();
    }

    public int[] attraction (Body a, Body b){
        int changeX = a.getX() - b.getX();
        int changeY = a.getY() - b.getY();
        double accelerationAX;
        double accelerationAY;
        double accelerationBX;
        double accelerationBY;
        if (changeY > 0){
            accelerationBY = (a.getMass()*5000/(Math.pow(changeX,2)+ Math.pow(changeY,2)))/(Math.pow(Math.pow(changeX/changeY,2)+1,0.5));
            accelerationBX = (changeX/changeY) * accelerationBY;
            accelerationAY = -(b.getMass()*5000/(Math.pow(changeX,2)+ Math.pow(changeY,2)))/(Math.pow(Math.pow(changeX/changeY,2)+1,0.5));
            accelerationAX = (changeX/changeY) * accelerationAY;
        }else if(changeY < 0){
            accelerationBY = -(a.getMass()*5000/(Math.pow(changeX,2)+ Math.pow(changeY,2)))/(Math.pow(Math.pow(changeX/changeY,2)+1,0.5));
            accelerationBX = (changeX/changeY) * accelerationBY;
            accelerationAY = (b.getMass()*5000/(Math.pow(changeX,2)+ Math.pow(changeY,2)))/(Math.pow(Math.pow(changeX/changeY,2)+1,0.5));
            accelerationAX = (changeX/changeY) * accelerationAY;    
        }else{
            accelerationBY = 0;
            accelerationBX = a.getMass()*5000/(Math.pow(changeX,2)*changeX/Math.abs(changeX));
            accelerationAY = 0;
            accelerationAX = -b.getMass()*5000/(Math.pow(changeX,2)*changeX/Math.abs(changeX));
        }
        return new int[] {(int) Math.round(accelerationAX),(int) Math.round(accelerationAY),(int) Math.round(accelerationBX),(int) Math.round(accelerationBY)};
    }

    public int[] bounce(Body a,Body b){
        int changeX = a.getX() - b.getX();
        int changeY = a.getY() - b.getY();
        double accelerationAX = 0;
        double accelerationAY = 0;
        double accelerationBX = 0;
        double accelerationBY = 0;
        if(Math.pow(Math.pow(changeX, 2) + Math.pow(changeY, 2), 0.5) < (((15 * b.getMass()) / 2) + ((15 * a.getMass()) / 2))){
            if (changeY > 0){
                accelerationBY = -(a.getMass()*6000/(Math.pow(changeX,2)+ Math.pow(changeY,2)))/Math.pow(Math.pow(changeX/changeY,2)+1,0.5);
                accelerationBX = (changeX/changeY) * accelerationBY;
                accelerationAY = (b.getMass()*6000/(Math.pow(changeX,2)+ Math.pow(changeY,2)))/Math.pow(Math.pow(changeX/changeY,2)+1,0.5);
                accelerationAX = (changeX/changeY) * accelerationAY;
            }else if(changeY < 0){
                accelerationBY = (a.getMass()*6000/(Math.pow(changeX,2)+ Math.pow(changeY,2)))/Math.pow(Math.pow(changeX/changeY,2)+1,0.5);
                accelerationBX = (changeX/changeY) * accelerationBY;
                accelerationAY = -(b.getMass()*6000/(Math.pow(changeX,2)+ Math.pow(changeY,2)))/Math.pow(Math.pow(changeX/changeY,2)+1,0.5);
                accelerationAX = (changeX/changeY) * accelerationAY;
            }else{
                accelerationBY = 0;
                accelerationBX = -a.getMass()*6000/Math.pow(changeX,2);
                accelerationAY = 0;
                accelerationAX = -b.getMass()*6000/Math.pow(changeX,2);
            }
        }
        return new int[] {(int) Math.round(accelerationAX) ,(int) Math.round(accelerationAY),(int) Math.round(accelerationBX),(int) Math.round(accelerationBY)};
    }

    private class adapter extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            player.press(e);
        }

        public void keyReleased(KeyEvent e) {
            player.release(e);
        }
    }

}