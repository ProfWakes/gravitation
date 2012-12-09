import javax.swing.*;

public class MainClass {
    public MainClass() {
        JFrame frame = new JFrame();
        frame.add(new Panel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setTitle("Gravity");
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public static void main(String[]args){
        new MainClass();
    }
}

