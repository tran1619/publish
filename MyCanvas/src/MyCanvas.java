import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JFrame;

public class MyCanvas extends Canvas {
	public static void main(String[] args) {
		// make the frame
		javax.swing.JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		
		// add the canvas
		Canvas canvas = new MyCanvas();
		canvas.setSize(400, 400);
		frame.getContentPane().add(canvas);
		
		// show the frame
		frame.pack();
		frame.setVisible(true);
	}
	
	public void paint(Graphics g) {
		// draw mickey
		g.setColor(Color.RED);
		Rectangle r = new Rectangle(100,100,200,200);
		mickey(g, r);
			}
	
	// take a rectangle and invokes fillOval.
		public void boxOval(Graphics g, Rectangle bb) {
		g.fillOval(bb.x, bb.y, bb.width, bb.height);
		}
	
	public void mickey(Graphics g, Rectangle bb) {
		// base case when the smallest ears are only 3 pixels wide
		if (bb.width < 3) {
				return;
		}
		boxOval(g, bb);
		
		int dx = bb.width/2;
		int dy = bb.height/2;
		
		Rectangle half = new Rectangle(bb.x, bb.y, dx, dy);
		half.translate(-dx/2, -dy/2);
		boxOval(g, half);
		mickey(g, half);
		half.translate(dx*2, 0);
		boxOval(g, half);
		mickey(g, half);
		}
}
