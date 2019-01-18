package com.astrolabsoftware.AstroLabNet.Browser;

// Java
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.Math.floor;

// AWT
import java.awt.Cursor;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Log4J
import org.apache.log4j.Logger;

public final class RootActionListener implements ActionListener {

  public RootActionListener(RootFrame frame) {
    _frame = frame;
    }

  public final void actionPerformed(ActionEvent ae) {
    String name = ((Component)ae.getSource()).getName();
    String value = ae.getActionCommand();
    if (name == null) {
      if (value.equals("Exit")) {
        _frame.close();
        }
      }
    }

  private RootFrame _frame;
    
  /** Logging . */
  private static Logger log = Logger.getLogger(RootActionListener.class);

  }
