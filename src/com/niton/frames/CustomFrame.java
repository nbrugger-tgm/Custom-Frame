package com.niton.frames;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;

import javax.swing.JFrame;
import javax.swing.event.MouseInputListener;

import com.niton.listeners.ActionButtonListener;
import com.niton.themes.base.Theme;

/**
 * This is the CustomFrame Class
 * 
 * @author Nils
 * @version 2018-08-08
 */
public class CustomFrame extends JFrame {
	private Rectangle oldSize;
	private static final long serialVersionUID = 1117999892970902406L;
	private Theme theme;

	
	/**
	 * Creates an Instance of CustomFrame.java
	 * @author Nils Brugger
	 * @version 2018-08-09
	 */
	public CustomFrame(Theme t) {
		theme = t;
		theme.setFrame(this);
		setUndecorated(true);
		theme.addListeners();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addMouseListener(new ActionButtonListener(this));
	}
//	
//	/**
//	 * Creates an Instance of CustomFrame.java
//	 * @author Nils Brugger
//	 * @version 2018-08-09
//	 */
//	public CustomFrame() {
//	}
	
	/**
	 * @see java.awt.Window#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean paramBoolean) {
		super.setVisible(paramBoolean);
		if(true)
			oldSize = getBounds();
	}
	
	/**
	 * <b>Description :</b><br>
	 * Checks if the frame fits the normal are exclusive the taskbar.<br>
	 * Maybe not works perfect on any Linux system, because the don't offer a good
	 * task bar implementation
	 * 
	 * @author Nils Brugger
	 * @version 2018-08-09
	 * @return if the frame is as big as the OS allows for normal frames (exclusive
	 *         taskbar)
	 */
	public boolean isMaximized() {
		return getBounds().equals(getMaximizedSize());
	}
	
	/**
	 * <b>Description :</b><br>
	 * Returns the display device the frame is located on
	 * @author Nils Brugger
	 * @version 2018-08-09
	 * @return the device of the frame
	 */
	public GraphicsDevice getDisplay() {
		return getGraphicsConfiguration().getDevice();
	}
	/**
	 * Full screen means the whole screen. but important only one screen
	 * @author Nils Brugger
	 * @version 2018-08-09
	 * @return true if the window size equals the screen size
	 */
	public boolean isFullscreen() {
		return getBounds().equals(getDisplay().getDefaultConfiguration().getBounds());
	}
	
	/**
	 * <b>Description :</b><br>
	 * Iconified is the state when the frame is only in the tsak bar but the frame itsself is not visible
	 * @author Nils Brugger
	 * @version 2018-08-09
	 * @return true if the window is iconified
	 */
	public boolean isIconoified() {
		return getExtendedState() == ICONIFIED;
	}
	/**
	 * <b>Description :</b><br>
	 * Maximizes the window to fit the OS screen.<br>
	 * Means the task bar on windows for example is still visible 
	 * @author Nils Brugger
	 * @version 2018-08-09
	 */
	public void maximize() {
		oldSize = getBounds();
		setBounds(getMaximizedSize());
	}
	/**
	 * Minimizes the frame to the taskbar
	 * @author Nils Brugger
	 * @version 2018-08-09
	 */
	public void iconify() {
		oldSize = getBounds();
		setExtendedState(JFrame.ICONIFIED);
	}
	/**
	 * <b>Description :</b><br>
	 * Makes the frame fullsize. means nothing else visible than the programm itsself.
	 * @author Nils Brugger
	 * @version 2018-08-09
	 */
	public void fullscreen() {
		oldSize = getBounds();
		getDisplay().setFullScreenWindow(this);
	}
	/**
	 * <b>Description :</b><br>
	 * Undos the last resize step (iconify, fullscreen or maximize)
	 * @author Nils Brugger
	 * @version 2018-08-09
	 */
	public void undoResize() {
		if(isFullscreen())
			getDisplay().setFullScreenWindow(null);
		if(isIconoified())
			setExtendedState(NORMAL);
		setBounds(oldSize);
	}

	public Rectangle getMaximizedSize() {
		GraphicsConfiguration gc = getDisplay().getDefaultConfiguration();

		Rectangle bounds = gc.getBounds();

		Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(gc);

		Rectangle effectiveScreenArea = new Rectangle();

		effectiveScreenArea.x = bounds.x + screenInsets.left;
		effectiveScreenArea.y = bounds.y + screenInsets.top;
		effectiveScreenArea.height = bounds.height - screenInsets.top - screenInsets.bottom;
		effectiveScreenArea.width = bounds.width - screenInsets.left - screenInsets.right;
		return effectiveScreenArea;
	}
	
	/**
	 * @param theme the theme to set
	 */
	public void setTheme(Theme theme) {
		this.theme = theme;
	}
	/**
	 * @return the theme
	 */
	public Theme getTheme() {
		return theme;
	}
	/**
	 * @see java.awt.Window#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics paramGraphics) {
		theme.paint((Graphics2D) paramGraphics);
	}
	
	public void addMouseAdapter(MouseAdapter adapter) {
		addMouseListener(adapter);
		addMouseMotionListener(adapter);
		addMouseWheelListener(adapter);
	}
}
