package com.astrolabsoftware.AstroLabNet.Browser.Components;

// AWT
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

// Swing
import javax.swing.AbstractButton; 
import javax.swing.ImageIcon;
import javax.swing.JButton;

public final class SimpleButton extends JButton {

  public SimpleButton(String name,
                      int alignment,
                      Font font,
                      String tip,
                      ActionListener listener) {
    super(name);
    setHorizontalTextPosition(AbstractButton.CENTER);
    setFont(font);
    if (tip != null) {
      setToolTipText(tip);
      }
	  addActionListener(listener);
    }

  public SimpleButton(String name,
                      ImageIcon icon,
                      int alignment,
                      Font font,
                      Dimension size,
                      String tip,
                      ActionListener listener) {
    super(name, icon); 
    setHorizontalTextPosition(AbstractButton.CENTER);
    setForeground(getBackground()); 
    setMinimumSize(size);
    setMaximumSize(size);
    setFont(font);
    if (tip != null) {
      setToolTipText(tip);
      }
	  addActionListener(listener);
    }

  }
