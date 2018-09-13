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
	private Robot preventer;
	private GrabPosition grab;
	private Point lastPos;

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
				grab = getGrabPosition(e.getLocationOnScreen(), theme.getFrame().getBounds(), theme.getResizeRadius());
				e.consume();
			}
		}
		adaptCursor();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (!SwingUtilities.isLeftMouseButton(e))
			return;
		if (grab != null) {
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
		System.out.println("Move");
		grab = getGrabPosition(e.getLocationOnScreen(), theme.getFrame().getBounds(), theme.getResizeRadius());
		adaptCursor();
		grab = null;
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
		lastPos = e.getLocationOnScreen();
		if (grab != null) {
			Point now = e.getLocationOnScreen();
			if (stayInArea(now, e)) {
				return;
			}
			resize(grab, now);
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
		int cursor = Cursor.DEFAULT_CURSOR;

		if (grab != null && theme.currentlyResizeable()) {
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
	private void resize(GrabPosition grabPosition, Point p) {
		CustomFrame frame = theme.getFrame();
		switch (grabPosition) {
		case BOTTOM:
			frame.setSize(frame.getWidth(), (int) (p.getY() - frame.getY()));
			break;
		case BOTTOM_LEFT:
			frame.setBounds(p.x, frame.getY(), frame.getWidth() + (frame.getX() - p.x), p.y - frame.getY());
			break;
		case BOTTOM_RIGHT:
			frame.setSize((int) (p.getX() - frame.getX()), (int) (p.getY() - frame.getY()));
			break;
		case LEFT:
			frame.setBounds(p.x, frame.getY(), frame.getWidth() + (frame.getX() - p.x), frame.getHeight());
			break;
		case RIGHT:
			frame.setSize((int) (p.getX() - frame.getX()), frame.getHeight());
			break;
		case TOP:
			frame.setBounds(frame.getX(), p.y, frame.getWidth(), (frame.getHeight() + frame.getY())-p.y);
			break;
		case TOP_LEFT:
			frame.setBounds(p.x, p.y, frame.getWidth() + (frame.getX() - p.x), (frame.getHeight() + frame.getY())-p.y);
			break;
		case TOP_RIGHT:
			frame.setBounds(frame.getX(), p.y, (p.x-frame.getX()), (frame.getHeight() + frame.getY())-p.y);
			break;
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
