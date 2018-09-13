package com.niton.listeners;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Window.Type;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.niton.frames.CustomFrame;
import com.niton.themes.base.ResizeableTheme;

/**
 * This is the ResizeListener Class
 * 
 * @author Nils Brugger
 * @version 2018-08-09
 */
public class ResizeListener extends MouseAdapter {
	private ResizeableTheme theme;
	private Point lastPos;
	private Robot preventer;
	private GrabPosition grab;

	public ResizeListener(ResizeableTheme theme) {
		super();
		this.theme = theme;
		try {
			preventer = new Robot(theme.getFrame().getDisplay());
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (!SwingUtilities.isLeftMouseButton(e))
			return;
		Point relative = e.getPoint();
		if (theme.getCloseArea().contains(relative) || theme.getMaximizeArea().contains(relative)
				|| theme.getMinimizeArea().contains(relative)) {
			return;
		}
		if (theme.currentlyResizeable()) {
			if (getGrabPosition(e.getLocationOnScreen(), theme.getFrame().getBounds(),
					theme.getResizeRadius()) != null) {
				lastPos = e.getLocationOnScreen();
				grab = getGrabPosition(lastPos, theme.getFrame().getBounds(), theme.getResizeRadius());
				e.consume();
			}
		}
		adaptCursor();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (!SwingUtilities.isLeftMouseButton(e))
			return;
		if (lastPos != null) {
			lastPos = null;
			grab = null;
			e.consume();
			theme.getFrame().repaint();
		}
		adaptCursor();
	}

	/**
	 * @see java.awt.event.MouseAdapter#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		
		GrabPosition pos = getGrabPosition(e.getLocationOnScreen(), theme.getFrame().getBounds(),
				theme.getResizeRadius());
		CustomFrame frame = theme.getFrame();
		int cursor = Cursor.DEFAULT_CURSOR;
		if (pos != null) {
			switch (pos) {
			case TOP:
			case BOTTOM:
				cursor = Cursor.N_RESIZE_CURSOR;
				break;
			case TOP_RIGHT:
			case BOTTOM_LEFT:
				cursor = Cursor.NE_RESIZE_CURSOR;
				break;
			case TOP_LEFT:
			case BOTTOM_RIGHT:
				cursor = Cursor.NW_RESIZE_CURSOR;
				break;
			case RIGHT:
			case LEFT:
				cursor = Cursor.E_RESIZE_CURSOR;
				break;
			}
		}
		frame.setCursor(Cursor.getPredefinedCursor(cursor));
	}

	/**
	 * @see java.awt.event.MouseAdapter#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		theme.getFrame().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (!SwingUtilities.isLeftMouseButton(e))
			return;
		if (lastPos != null) {
			Point now = e.getLocationOnScreen();
			if(getGrabPosition(lastPos, theme.getFrame().getBounds(), theme.getResizeRadius()) != grab && getGrabPosition(now, theme.getFrame().getBounds(), theme.getResizeRadius()) != grab)
				return;
			
			if (stayInArea(now, e)) {
				return;
			}
			resize(grab, (int) (now.getX() - lastPos.getX()), (int) (now.getY() - lastPos.getY()));

			lastPos = e.getLocationOnScreen();
			e.consume();

		}
	}

	/**
	 * <b>Description :</b><br>
	 * 
	 * @author Nils Brugger
	 * @version 2018-09-12
	 */
	private void adaptCursor() {
		if (!theme.currentlyResizeable())
			return;
		if(grab == null)
			return;
		int cursor = Cursor.DEFAULT_CURSOR;
		switch (grab) {
		case TOP:
		case BOTTOM:
			cursor = Cursor.N_RESIZE_CURSOR;
			break;
		case TOP_RIGHT:
		case BOTTOM_LEFT:
			cursor = Cursor.NE_RESIZE_CURSOR;
			break;
		case TOP_LEFT:
		case BOTTOM_RIGHT:
			cursor = Cursor.NW_RESIZE_CURSOR;
			break;
		case RIGHT:
		case LEFT:
			cursor = Cursor.E_RESIZE_CURSOR;
			break;
		}
	
		theme.getFrame().setCursor(Cursor.getPredefinedCursor(cursor));
	}

	/**
	 * <b>Description :</b><br>
	 * 
	 * @author Nils Brugger
	 * @version 2018-08-09
	 * @param point
	 * @param bounds
	 * @return
	 */
	private GrabPosition getGrabPosition(Point point, Rectangle bounds, int disdance) {
		GrabPosition pos = null;
		Point corner = bounds.getLocation();
		if (corner.distance(point) < disdance) {
			pos = GrabPosition.TOP_LEFT;
		}
		corner.translate((int) bounds.getWidth(), 0);
		if (corner.distance(point) < disdance) {
			pos = GrabPosition.TOP_RIGHT;
		}
		corner.translate(0, (int) bounds.getHeight());
		if (corner.distance(point) < disdance) {
			pos = GrabPosition.BOTTOM_RIGHT;
		}
		corner.translate((int) -bounds.getWidth(), 0);
		if (corner.distance(point) < disdance) {
			pos = GrabPosition.BOTTOM_LEFT;
		}
		if (pos == null) {
			if (point.getX() - bounds.getX() < disdance && -(point.getX() - bounds.getX()) < disdance)
				pos = GrabPosition.LEFT;
			if (point.getY() - bounds.getY() < disdance && -(point.getY() - bounds.getY()) < disdance)
				pos = GrabPosition.TOP;
			if ((bounds.getHeight() + bounds.getY()) - point.getY() < disdance
					&& -((bounds.getHeight() + bounds.getY()) - point.getY()) < disdance)
				pos = GrabPosition.BOTTOM;
			if ((bounds.getWidth() + bounds.getX()) - point.getX() < disdance
					&& -((bounds.getWidth() + bounds.getX()) - point.getX()) < disdance)
				pos = GrabPosition.RIGHT;
		}
		return pos;
	}

	/**
	 * <b>Description :</b><br>
	 * 
	 * @author Nils Brugger
	 * @version 2018-08-09
	 * @param grabPosition
	 * @param x
	 * @param y
	 */
	private void resize(GrabPosition grabPosition, int x, int y) {
		CustomFrame frame = theme.getFrame();
		int oldW = frame.getWidth();
		int oldH = frame.getHeight();
		int oldX = frame.getX();
		int oldY = frame.getY();
		switch (grabPosition) {
		case BOTTOM:
			frame.setBounds(oldX, oldY, oldW, oldH + y);
			break;
		case BOTTOM_LEFT:
			frame.setBounds(oldX + x, oldY, oldW - x, oldH + y);
			break;
		case BOTTOM_RIGHT:
			frame.setBounds(oldX, oldY, oldW + x, oldH + y);
			break;
		case LEFT:
			frame.setBounds(oldX + x, oldY, oldW - x, oldH);
			break;
		case RIGHT:
			frame.setBounds(oldX, oldY, oldW + x, oldH);
			break;
		case TOP:
			frame.setBounds(oldX, oldY + y, oldW, oldH - y);
			break;
		case TOP_LEFT:
			frame.setBounds(oldX - x, oldY - y, oldW + x, oldH + y);
			break;
		case TOP_RIGHT:
			frame.setBounds(oldX, oldY - y, oldW + x, oldH + y);
			break;
		}
		if (frame.getWidth() <= frame.getMinimumSize().getWidth()) {
			frame.setSize((int) frame.getMinimumSize().getWidth(), frame.getHeight());
			if (grabPosition == GrabPosition.LEFT || grabPosition == GrabPosition.BOTTOM_LEFT) {
				frame.setLocation(oldX, oldY);
			}
		}
		if (frame.getHeight() <= frame.getMinimumSize().getHeight())
			frame.setSize(frame.getWidth(), (int) frame.getMinimumSize().getHeight());

		if (frame.getHeight() >= frame.getMaximumSize().getWidth()) {
			frame.setSize(frame.getWidth(), (int) frame.getMaximumSize().getHeight());
		}
		if (frame.getWidth() >= frame.getMaximumSize().getWidth()) {
			frame.setSize((int) frame.getMaximumSize().getWidth(), frame.getHeight());
		}
	}

	private boolean stayInArea(Point now, InputEvent e) {
		Rectangle allowed = theme.getFrame().getMaximizedSize();
		if (!allowed.contains(now)) {
			if (preventer != null) {
				if (allowed.contains(now.x, lastPos.y)) {
					preventer.mouseMove(now.x, lastPos.y);
					now.y = lastPos.y;
				} else if (allowed.contains(lastPos.x, now.y)) {
					preventer.mouseMove(lastPos.x, now.y);
					now.x = lastPos.x;
				} else {
					preventer.mouseMove(lastPos.x, lastPos.y);
					now.setLocation(lastPos.x, lastPos.y);
				}
			}
			e.consume();
			return true;
		}
		return false;
	}
}
