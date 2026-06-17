package main;

import javax.swing.JFrame;

public class Main {
	public static void main(String []args) {
		
		JFrame Window = new JFrame("Simple Chess");
		
		Window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
