package GUI;

import dumbchain.Dumbchain;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Manage the layout of the GUI
 */

public class GUIView {
    // Table Views column name
    public final String[] blockChainColumns = {"Id", "Hash", "Previous Hash", "Nonce", "#Tx"};
    public final String[] txColumns = {"Block Id", "Hash", "Value"};
    //Tables
    public TableView blockChainTable;
    public TableView txTable = new TableView();
    public SplitPane tableViews = new SplitPane();
    // Buttons
    public Button btnAddTxDir = new Button("Add TX");
    public Button btnAddTxMine = new Button("Add TX");
    public Button btnMineBlock = new Button("Mine Block");
    public Button btnValidate = new Button("Validate");
    public Button btnRestore = new Button("Restore");
    public Button btnReset = new Button("Reset");
    //Text fields
    public TextField tfTXDir = new TextField();
    public TextField tfTXMin = new TextField();
    public TextField tfBlockMin = new TextField();
    //Status
    public Text chainStatus = new Text();
    public Text blockState = new Text();

    /**
     * Build the GUI layout
     *
     * @return Layout
     */
    public BorderPane build() {
        // Build the top panel
        HBox topPanel = this.buildTopPanel();
        topPanel.getStyleClass().add("topPanel");
        // Build main panel
        BorderPane borderPane = new BorderPane();
        // Set border pane panels
        borderPane.setTop(topPanel);
        borderPane.setCenter(tableViews);
        return borderPane;
    }

    /**
     * Build the TopPanel
     *
     * @return TopPanel
     */
    private HBox buildTopPanel() {

        HBox topPanel = new HBox();

        VBox directBox = this.buildDirectBOX();
        VBox minerBox = this.buildMinerBOX();
        VBox statusBox = this.buildStatusBox();

        topPanel.getChildren().addAll(directBox, minerBox, statusBox);

        return topPanel;

    }

    /**
     * Build the direct box
     *
     * @return directBox
     */
    private VBox buildDirectBOX() {
        VBox directBox = new VBox();
        HBox.setHgrow(directBox, Priority.ALWAYS);
        tfTXDir.setPromptText("transaction value");

        HBox innerBcBox = new HBox();
        innerBcBox.getChildren().addAll(tfTXDir, btnAddTxDir);
        innerBcBox.setSpacing(10);

        directBox.getChildren().addAll(new Text("Interact directly with the DumbChain"), innerBcBox);
        directBox.getStyleClass().add("padding-10");

        return directBox;
    }

    /**
     * Build the miner box
     *
     * @return minerBox
     */
    private VBox buildMinerBOX() {
        VBox minerBox = new VBox();
        tfTXMin.setPromptText("transaction value");
        tfBlockMin.setPromptText("block id");

        HBox innerMinerBoxTX = new HBox();
        innerMinerBoxTX.setSpacing(10);
        innerMinerBoxTX.getChildren().addAll(tfTXMin, btnAddTxMine);

        HBox innerBlockState = new HBox();
        blockState.setText("0/" + Dumbchain.BLOCK_THRESHOLD);
        innerBlockState.getChildren().addAll(new Text("Miner's block state: "), blockState);

        HBox innerMinerBoxMine = new HBox();
        innerMinerBoxMine.setSpacing(10);
        innerMinerBoxMine.getChildren().addAll(tfBlockMin, btnMineBlock);
        minerBox.getChildren().addAll(new Text("Interact as a Miner"), innerMinerBoxTX, innerBlockState, innerMinerBoxMine);

        minerBox.getStyleClass().add("padding-10");
        HBox.setHgrow(minerBox, Priority.ALWAYS);

        return minerBox;
    }

    /**
     * Build status box
     *
     * @return minerBox
     */
    private VBox buildStatusBox() {
        VBox statusBox = new VBox();
        HBox.setHgrow(statusBox, Priority.ALWAYS);

        HBox validationBox = new HBox();
        validationBox.setSpacing(10);
        chainStatus.getStyleClass().add("status");
        chainStatus.setText("UNKNOWN");
        validationBox.getChildren().addAll(new Text("Blockchain Integrity : "), chainStatus);

        HBox opBox = new HBox();
        opBox.setSpacing(10);
        opBox.getChildren().addAll(btnValidate, btnRestore, btnReset);

        statusBox.getChildren().addAll(validationBox, opBox);
        statusBox.getStyleClass().add("padding-10");

        return statusBox;
    }


}
