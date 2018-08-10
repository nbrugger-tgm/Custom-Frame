import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.niton.frames.CustomFrame;
import com.niton.themes.FlatDarkTheme;
import com.niton.themes.OSXTheme;
import com.niton.themes.OSXTheme.Size;
import com.niton.themes.base.ResizeableTheme;

import java.awt.Color;

/**
 * This is the Test Class
 * @author Nils Brugger
 * @version 2018-08-09
 */
public class Test {
	public static void main(String[] args) {
		testOSXTheme();
		testHeader();
		testContent();
		testFlatTheme();
		testMovingAndResizing();
	}
	
	/**
	 * <b>Description :</b><br>
	 * 
	 * @author Nils Brugger
	 * @version 2018-08-10
	 */
	private static void testOSXTheme() {
		OSXTheme theme = new OSXTheme();
		theme.setSize(Size.NORMAL);
		CustomFrame frame = new CustomFrame(theme);
		frame.setSize(300,300);
		frame.setVisible(true);
		frame.setTitle("A very very very Long JFrame title which is longer than neccessary");
	}

	/**
	 * <b>Description :</b><br>
	 * 
	 * @author Nils Brugger
	 * @version 2018-08-10
	 */
	private static void testHeader() {
		CustomFrame frame = new CustomFrame(new FlatDarkTheme());
		frame.setTitle("I BMMMMMMMMMMMMMMZ");
		frame.setSize(300,300);
		frame.setVisible(true);
	}

	/**
	 * <b>Description :</b><br>
	 * 
	 * @author Nils Brugger
	 * @version 2018-08-09
	 */
	private static void testContent() {
		CustomFrame frame = new CustomFrame(new FlatDarkTheme());
		frame.setSize(300,300);
		frame.setVisible(true);
		JLabel panel = new JLabel("XNXX");
		panel.setBackground(Color.GREEN);
		frame.getContentPane().setLayout(new GridLayout());
		frame.getContentPane().add(panel);
		frame.repaint();
	}

	/**
	 * <b>Description :</b><br>
	 * 
	 * @author Nils Brugger
	 * @version 2018-08-09
	 */
	private static void testFlatTheme() {
		CustomFrame frame = new CustomFrame(new FlatDarkTheme());
		frame.setSize(300,300);
		frame.setVisible(true);
	}

	private static void testMovingAndResizing() {
		ResizeableTheme theme = new ResizeableTheme() {
			
			@Override
			public void paint(Graphics2D g) {
				g.setColor(Color.GRAY);
				g.fillRect(0, 0, getFrame().getWidth(), getFrame().getHeight());
				g.setColor(Color.green);
				g.fillRect(getFrame().getWidth()-65, 0, 20, 20);
				g.setColor(Color.blue);
				g.fillRect(getFrame().getWidth()-45, 0, 20, 20);
				g.setColor(Color.red);
				g.fillRect(getFrame().getWidth()-25, 0, 20, 20);
				g.setColor(Color.DARK_GRAY);
				g.fillRect(5, 20, getFrame().getWidth()-10, getFrame().getHeight()-20);
			}
			
			@Override
			public Rectangle getMinimizeArea() {
				return new Rectangle(getFrame().getWidth()-65, 0, 20, 20);
			}
			
			@Override
			public Rectangle getMaximizeArea() {
				return new Rectangle(getFrame().getWidth()-45, 0, 20, 20);
			}
			
			@Override
			public Rectangle getContentSize() {
				return new Rectangle(5, 20, getFrame().getWidth()-10, getFrame().getHeight()-20);
			}
			
			@Override
			public Rectangle getCloseArea() {
				return new Rectangle(getFrame().getWidth()-25, 0, 20, 20);
			}
			
			@Override
			public int getResizeRadius() {
				return 5;
			}
			
			@Override
			public Rectangle getDragingArea() {
				return new Rectangle(0, 0, getFrame().getWidth(), 20);
			}
			
			@Override
			public boolean currentlyResizeable() {
				return true;
			}
		};
		
		CustomFrame frame = new CustomFrame(theme);
		frame.maximize();
		frame.setVisible(true);
	}
}

