package com.niton.listeners;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.SwingUtilities;

import com.niton.frames.CustomFrame;

/**
 * This is the ContentPaneFitterListener Class
 * @author Nils Brugger
 * @version 2018-08-09
 */
public class ContentPaneFitterListener implements ComponentListener {
	CustomFrame frame;

	public ContentPaneFitterListener(CustomFrame frame) {
		super();
		this.frame = frame;
	}

	/**
	 * @see java.awt.event.ComponentListener#componentResized(java.awt.event.ComponentEvent)
	 */
	@Override
	public void componentResized(ComponentEvent paramComponentEvent) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				frame.fitContentPane();
			}
		});
	}

	/**
	 * @see java.awt.event.ComponentListener#componentMoved(java.awt.event.ComponentEvent)
	 */
	@Override
	public void componentMoved(ComponentEvent paramComponentEvent) {
	}

	/**
	 * @see java.awt.event.ComponentListener#componentShown(java.awt.event.ComponentEvent)
	 */
	@Override
	public void componentShown(ComponentEvent paramComponentEvent) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				frame.fitContentPane();
			}
		});
	}

	/**
	 * @see java.awt.event.ComponentListener#componentHidden(java.awt.event.ComponentEvent)
	 */
	@Override
	public void componentHidden(ComponentEvent paramComponentEvent) {
	}
}

