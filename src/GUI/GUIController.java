package GUI;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Manage events that occur on the GUI
 */
public class GUIController {

    private GUIView view;
    private GUIModel model;

    public GUIController() {
        this.view = new GUIView();
        this.model = new GUIModel();
    }

    /**
     * Get a view and build the Scene
     *
     * @param stage primary stage
     */
    public void buildView(Stage stage) {
        Scene scene = new Scene(view.build(), 1280, 720);
        scene.getStylesheets().add("styles.css");
        stage.setTitle("Dumbcoin GUI");
        stage.setScene(scene);

        this.populateTableViews();
        this.setupHandler();

        stage.show();
    }

    /**
     * Setup the event handlers for the events
     */
    private void setupHandler() {

        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);

        // Add direct TX to blockchain
        view.btnAddTxDir.setOnAction(e -> {
            model.addDirectTX(view.tfTXDir.getText());
            view.tfTXDir.clear();
            this.refreshTableViews();
        });

        // Add a transaction to blockchain as a Miner
        view.btnAddTxMine.setOnAction(e -> {
            if (model.addMinerTX(view.tfTXMin.getText())) {
                infoAlert.setTitle("New block");
                infoAlert.setHeaderText("The miner has mined a new block");
                infoAlert.setContentText("The miner's TX pool reached the block threshold.");
                infoAlert.showAndWait();
                this.refreshTableViews();
            }
            view.blockState.setText(model.getMinerPoolState() + "/" + model.getBlockThreshold());
            view.tfTXMin.clear();
        });

        // Mine a block
        view.btnMineBlock.setOnAction(e -> {
            try {
                int blockId = Integer.parseInt(view.tfBlockMin.getText());
                view.tfBlockMin.clear();
                if (blockId >= 0 && blockId < model.getChainSize()) {
                    model.mineBlock(blockId);
                    this.refreshTableViews();
                }
            } catch (Exception exception) {
                errorAlert.setHeaderText("Mining Block Error");
                errorAlert.setContentText("The block is already mined.");
                errorAlert.showAndWait();
            }
        });

        // Blockchain validation
        view.btnValidate.setOnAction(e -> {
            String chainStatus = model.validateBlockchain();
            if (chainStatus.equals("OK")) {
                view.chainStatus.setText("VALID");
                view.chainStatus.setFill(Color.GREEN);
            } else {
                view.chainStatus.setText("INVALID");
                view.chainStatus.setFill(Color.FIREBRICK);
                errorAlert.setHeaderText("Invalid blockchain status");
                errorAlert.setContentText(chainStatus);
                errorAlert.showAndWait();
            }
        });

        //Restore
        view.btnRestore.setOnAction(e -> {
            String restoreStatus = model.restoreState();

            if (restoreStatus == "OK") {
                this.refreshTableViews();
                view.chainStatus.setText("UNKNOWN");
                view.chainStatus.setFill(Color.BLACK);
            } else {
                errorAlert.setHeaderText("No other state");
                errorAlert.setContentText(restoreStatus);
                errorAlert.showAndWait();
            }
        });

        //Reset
        view.btnReset.setOnAction(e -> {
            model.resetBlockchain();
            view.chainStatus.setText("UNKNOWN");
            view.chainStatus.setFill(Color.BLACK);
            this.refreshTableViews();
        });


    }

    /**
     * Populate the table views (blockchain data and transaction data)
     */
    private void populateTableViews() {

        String[][] chainData = model.getChainData();
        String[][] txData = model.getTXData();

        view.blockChainTable = TableManager.createTableView(chainData, view.blockChainColumns);
        view.txTable = TableManager.createTableView(txData, view.txColumns);

        view.tableViews.getItems().addAll(view.blockChainTable, view.txTable);
        view.blockChainTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    /**
     * Refresh the table views data
     */
    private void refreshTableViews() {
        view.blockChainTable.getItems().clear();
        view.txTable.getItems().clear();
        view.txTable.setItems(TableManager.buildData(model.getTXData()));
        view.blockChainTable.setItems(TableManager.buildData(model.getChainData()));
    }

}
