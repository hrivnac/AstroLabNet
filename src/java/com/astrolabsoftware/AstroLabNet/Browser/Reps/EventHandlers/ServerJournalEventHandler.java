package com.astrolabsoftware.AstroLabNet.Browser.Reps.EventHandlers;

import com.astrolabsoftware.AstroLabNet.Browser.Reps.ServerRep;
import com.astrolabsoftware.AstroLabNet.Browser.BrowserWindow;
import com.astrolabsoftware.AstroLabNet.Browser.Components.Images;
import com.astrolabsoftware.AstroLabNet.Browser.Components.HeaderLabel;
import com.astrolabsoftware.AstroLabNet.Browser.Components.SimpleButton;
import com.astrolabsoftware.AstroLabNet.HBaser.HBaseClient;
import com.astrolabsoftware.AstroLabNet.Journal.JournalTableView;

// JavaFX
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.geometry.Orientation;

// JFXtras
import jfxtras.scene.control.LocalDateTimePicker;

// org.json
import org.json.JSONObject;

// Java
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDateTime;
import java.sql.Timestamp;

// Log4J
import org.apache.log4j.Logger;

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
  public void handle(ActionEvent event) {
    // Desc
    Label desc = new HeaderLabel("Journal Search", "Search Journal Database");
    // Start
    LocalDateTimePicker start = new LocalDateTimePicker(LocalDateTime.now().minusDays(1));
     // Stop
    LocalDateTimePicker stop = new LocalDateTimePicker(LocalDateTime.now());
    // Period = Start + Stop
    HBox period = new HBox(10);
    period.setSpacing(5);
    period.setAlignment(Pos.CENTER);
    period.getChildren().addAll(start, stop);
    // ActorLabel
    Label actorLabel = new Label("Actor");
    // Actor
    ComboBox<String> actor = new ComboBox<>();
    actor.getItems().add("*");
    actor.getItems().add("Action");
    actor.getItems().add("Job");
    actor.getSelectionModel().select(0);
    //actor.setEditable(true);
    // RCLabel
    Label rcLabel = new Label("RC");
    // RC
    ComboBox<String> rc = new ComboBox<>();
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
    // ResultTable
    JournalTableView resultTable = new JournalTableView(); 
    // Pane = Desc + Cmd + ButtonBox + ResultTable
    SplitPane pane = new SplitPane();
    pane.setDividerPositions(0.5);
    pane.setOrientation(Orientation.VERTICAL);
    pane.getItems().addAll(cmdBox, resultTable);
    // Actions
    search.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        Map<String, String> filterMap = new HashMap<>();
        String actorV   = actor.getValue();
        String rcV      = rc.getValue();
        String actionV  = action.getText();
        String resultV  = result.getText();
        String commentV = comment.getText();
        if (!actorV.equals("*")) {
          filterMap.put("i:actor", actorV + ":BinaryComparator");
          }
        if (!rcV.equals("*")) {
          filterMap.put("d:rc", rcV + ":BinaryComparator");
          }
        if (!actionV.equals("")) {
          filterMap.put("i:action", actionV + ":SubstringComparator");
          }
         if (!resultV.equals("")) {
          filterMap.put("d:result", resultV + ":SubstringComparator");
          }
        if (!commentV.equals("")) {
          filterMap.put("c:comment", commentV + ":SubstringComparator");
          }
        resultTable.getItems().clear();
        JSONObject json = _hbase.scan2JSON("astrolabnet.journal.1",
                                           filterMap,
                                           0,
                                           Timestamp.valueOf(start.getLocalDateTime()).getTime(),
                                           Timestamp.valueOf(stop.getLocalDateTime() ).getTime());
        resultTable.addJSONEntry(json);
        resultTable.refresh();
        }
      });
    // Show
    Tab tab = _browser.addTab(pane, toString(), Images.JOURNAL);
    }
    
  @Override
  public String toString() {
    return "Journal on " + _hbase.toString();
    }
  
  private BrowserWindow _browser;  
    
  private HBaseClient _hbase;

  /** Logging . */
  private static Logger log = Logger.getLogger(ServerJournalEventHandler.class);
    
  }
