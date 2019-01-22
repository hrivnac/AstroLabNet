package com.astrolabsoftware.AstroLabNet.Browser.Components;

// AWT
import java.awt.Color;
import java.awt.Dimension;

// Swing
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.BevelBorder;

/** {@link JPanel} holding <code>JTree</code>.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public final class TreePanel extends JPanel {

  /** TBD */
  public TreePanel(JTree tree) {
    // Scroll Pane
    JScrollPane disconnecterScrollPane = new JScrollPane(tree);
    disconnecterScrollPane.setViewportBorder(BorderFactory.createLineBorder(Color.black));
    tree.setLayout(new BoxLayout(tree, BoxLayout.Y_AXIS));
    tree.setBorder(new BevelBorder(BevelBorder.RAISED));
    disconnecterScrollPane.setPreferredSize(new Dimension(50, 50));
    // Panel
    add(disconnecterScrollPane);
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

  }
