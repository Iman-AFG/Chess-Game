package main;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main {
	public static void main(String []args) {
		
		ImageIcon image = new ImageIcon(Main.class.getResource("chess_icon.png"));
		JFrame Window = new JFrame("Simple Chess");
		
		Window.setIconImage(image.getImage());
		Window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Window.setBackground(Color.BLACK);
		Window.setResizable(false);

		// Add Game Panel to the window
        GamePanel gp = new GamePanel();
        Window.add(gp);
        Window.pack();
        
        Window.setLocationRelativeTo(null);
        Window.setVisible(true);
        
        gp.launchGame();
	}
}
