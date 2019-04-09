package com.astrolabsoftware.AstroLabNet.Browser.Reps.EventHandlers;

import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;
import com.astrolabsoftware.AstroLabNet.Browser.Components.*;
import com.astrolabsoftware.AstroLabNet.Browser.Reps.*;
import com.astrolabsoftware.AstroLabNet.DB.*;
import com.astrolabsoftware.AstroLabNet.HBaser.HBaseClient;

// JavaFX
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.geometry.Orientation;

// Java
import java.time.LocalDate;

/** <code>ServerTJournalEventHandler</code> implements {@link EventHandler} for {@link ServerRep}.
  * It handles <em>Journal</em> database.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
public class ServerJournalEventHandler implements EventHandler<ActionEvent> {

  /** Create.
    * @param serverRep The associated {@link ServerRep}. */
  public ServerJournalEventHandler(ServerRep serverRep) {
    _browser = serverRep.browser();
    _hbase   = serverRep.hbase();
    }
 
  @Override
  // TBD: Use europeean date
  // TBD: use time
  public void handle(ActionEvent event) {
    // Desc
    Label desc = new Label("Journal");  
    // Start
    DatePicker start = new DatePicker(LocalDate.now().minusDays(1));
    // Interval
    Label interval = new Label("-");    
     // Stop
    DatePicker stop = new DatePicker(LocalDate.now());
    // Period = Start + Interval + Stop
    HBox period = new HBox(10);
    period.setSpacing(5);
    period.setAlignment(Pos.CENTER);
    period.getChildren().addAll(start, interval, stop);
    // ActorLabel
    Label actorLabel = new Label("Actor");
    // Actor
    ComboBox actor = new ComboBox();
    actor.getItems().add("*");
    actor.getItems().add("Action");
    actor.getItems().add("Job");
    actor.getSelectionModel().select(0);
    //actor.setEditable(true);
    // RCLabel
    Label rcLabel = new Label("RC");
    // RC
    ComboBox rc = new ComboBox();
    rc.getItems().add("*");
    rc.getItems().add("+1");
    rc.getItems().add("0");
    rc.getItems().add("-1");
    rc.getSelectionModel().select(0);
    //rc.setEditable(true);
    // ActionLabel
    Label actionLabel = new Label("Action");
    // Action
    TextField action = new TextField();
    // ResultLabel
    Label resultLabel = new Label("Result");
    // Result
    TextField result = new TextField();
    // CommentLabel
    Label commentLabel = new Label("Comment");
    // Comment
    TextField comment = new TextField();
    // Selection = Actors + RCs + Action + Result + Comment
    GridPane selection = new GridPane();
    selection.setHgap(10);
    selection.setVgap(10);
    selection.add(actorLabel,  0, 0);
    selection.add(actor,       1, 0);
    selection.add(rcLabel,     0, 1);
    selection.add(rc,          1, 1);
    selection.add(actionLabel, 0, 2);
    selection.add(action,      1, 2);
    selection.add(resultLabel, 0, 3);
    selection.add(result,      1, 3);
    selection.add(commentLabel,0, 4);
    selection.add(comment,     1, 4);
    // SelectionBox = Selection
    HBox selectionBox = new HBox(10);
    selectionBox.setSpacing(5);
    selectionBox.setAlignment(Pos.CENTER);
    selectionBox.getChildren().addAll(selection);    
    // Search
    Button search = new SimpleButton("Search", "Search Journal Database");
    // ButtonBox = Search
    HBox buttonBox = new HBox(10);
    buttonBox.setSpacing(5);
    buttonBox.setAlignment(Pos.CENTER);
    buttonBox.getChildren().addAll(search);
    // CmdBox = Desc + Period + SelectionBox + ButtonBox 
    VBox cmdBox = new VBox();
    cmdBox.setSpacing(5);
    cmdBox.setAlignment(Pos.CENTER);
    cmdBox.getChildren().addAll(desc, period, selectionBox, buttonBox);
    // ResultText
    TextFlow resultText = new TextFlow(); 
    Text result0 = new Text();
    result0.setFill(Color.DARKGREEN);
    resultText.getChildren().add(result0);
    // ScrollPane = ResultText
    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setFitToWidth(true);
    scrollPane.setContent(resultText);
    // Pane = Desc + Cmd + ButtonBox + ScrollPane
    SplitPane pane = new SplitPane();
    pane.setDividerPositions(0.5);
    pane.setOrientation(Orientation.VERTICAL);
    pane.getItems().addAll(cmdBox, scrollPane);
    // Actions
    search.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        resultText.getChildren().add(new Text(_hbase.scan("astrolabnet.journal.1")));
        }
      });
    // Show
    Tab tab = _browser.addTab(pane, toString(), Images.JOURNAL);
    }
  
  private BrowserWindow _browser;  
    
  private HBaseClient _hbase;
    
  }
