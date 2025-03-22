package mapcreator;

import java.awt.Point;

import javax.swing.JFrame;

public class App 
{
    public static void main( String[] args )
    {
        Point size = new Point(500, 500);
        String[] s = {
            "====================",
            "=                  =",
            "=                  =",
            "=   ┌───────┐      =",
            "=   │       │      =",
            "=   │  ┌────┼────┐ =",
            "=   │  │    │    │ =",
            "=   │  └────┼────┘ =",
            "=   │       │      =",
            "=   └───────┘      =",
            "=                  =",
            "===================="
        };

        createMap map = new createMap(
                        size,
                        s
                        );
        JFrame frame = new JFrame();

                    
        frame.setSize(size.x-25, size.y);
        frame.setTitle("Main");
                    
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
                    
        // Components 
                    
        frame.add(map);
    }

}
