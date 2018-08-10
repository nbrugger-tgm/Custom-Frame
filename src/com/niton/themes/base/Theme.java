package com.niton.themes.base;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.Icon;

import com.niton.frames.CustomFrame;

/**
 * This is the Theme Class
 * @author Nils Brugger
 * @version 2018-08-08
 */
public abstract class Theme{
	protected CustomFrame frame;
	/**
	 * <b>Description :</b><br>
	 * You may add any listeners you need to the frame in here
	 * @author Nils Brugger
	 * @version 2018-08-09
	 */
	public abstract void addListeners();
	/** 
	 * @author Nils Brugger
	 * @version 2018-08-08
	 * @return the area in which the user needs to click to trigger the action on close
	 */
	public abstract Rectangle getCloseArea();
	/**
	 * @author Nils Brugger
	 * @version 2018-08-08
	 * @return the area in which the user needs to click to iconify the Window
	 */
	public abstract Rectangle getMaximizeArea();	
	/**
	 * @author Nils Brugger
	 * @version 2018-08-08
	 * @return the area in which the user needs to click to make the frame
	 */
	public abstract Rectangle getMinimizeArea();
	
	/**
	 * <b>Description :</b><br>
	 * This method should provide the area in which the content of the window will be.<br>
	 * it <b>HAS TO BE</b> <i>smaller</i> than the parameter because the outer areas are the border for your theme<br>
	 * @author Nils Brugger
	 * @version 2018-08-09
	 * @return the area for the content
	 */
	public abstract Rectangle getContentSize();
	
	/**
	 * <b>Description :</b><br>
	 * This method needs to paint the background of the window and the border.<br>
	 * The painting of the sub components doesn't needs to be done.
	 * @author Nils Brugger
	 * @version 2018-08-09
	 * @param g the graphics to paint on
	 */
	public abstract void paint(Graphics2D g);

	
	/**
	 * @return the frame
	 */
	public CustomFrame getFrame() {
		return frame;
	}
	/**
	 * @param frame the frame to set
	 */
	public void setFrame(CustomFrame frame) {
		this.frame = frame;
	}

	public void paintRectangle(Rectangle rec,Graphics2D g) {
		g.drawRect(rec.x, rec.y, rec.width, rec.height);
	}
	public void fillRectangle(Rectangle rec,Graphics2D g) {
		g.fillRect(rec.x, rec.y, rec.width, rec.height);
	}
	public void paintRectangle(Rectangle rec,Graphics2D g,int size,Color color) {
		Color bev = g.getColor();
		g.setColor(color);
		Stroke sbev = g.getStroke();
		g.setStroke(new BasicStroke(size));
		paintRectangle(rec, g);
		g.setColor(bev);
		g.setStroke(sbev);
	}
	public void paintOval(Rectangle oval,Graphics2D g) {
		g.drawOval(oval.x, oval.y, oval.width, oval.height);
	}
	public void paintOval(Rectangle rec,Graphics2D g,int size,Color color) {
		Color bev = g.getColor();
		g.setColor(color);
		Stroke sbev = g.getStroke();
		g.setStroke(new BasicStroke(size));
		paintOval(rec, g);
		g.setColor(bev);
		g.setStroke(sbev);
	}
	public void fillRectangle(Rectangle rec,Graphics2D g,Color color) {
		Color bev = g.getColor();
		g.setColor(color);
		fillRectangle(rec, g);
		g.setColor(bev);
	}
	public void fillOval(Rectangle oval,Graphics2D g) {
		g.fillOval(oval.x, oval.y, oval.width, oval.height);
	}
	public void fillOval(Rectangle rec,Graphics2D g,Color color) {
		Color bev = g.getColor();
		g.setColor(color);
		paintOval(rec, g);
		g.setColor(bev);
	}
	public void write(String text,Color c,Font font,int x,int y,Graphics2D g) {
		Color cbev = g.getColor();
		Font fbev = g.getFont();
		g.setColor(c);
		g.setFont(font);
		g.drawString(text, x, y+font.getSize());
		g.setColor(cbev);
		g.setFont(fbev);
	}
	public void write(String text,Color c,String fontName,int fontSize,boolean cursive,boolean bold,int x,int y,Graphics2D g) {
		write(text, c, new Font(fontName, (cursive?Font.ITALIC:0)+(bold?Font.BOLD:0), fontSize), x, y, g);
	}
	public void write(String text,Color c,int x,int y,Graphics2D g) {
		write(text, c, g.getFont(), x, y, g);
	}
	public void write(String text,int x,int y,Graphics2D g) {
		write(text, getFrame().getForeground(), x, y, g);
	}
}

