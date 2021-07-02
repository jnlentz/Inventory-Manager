package inventoryapp;

import static inventoryapp.MainFXMLController.inv;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ModProductFXMLController implements Initializable {
        private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
        @FXML private TextField idText;
        @FXML private TextField nameText;
        @FXML private TextField invText;
        @FXML private TextField priceText;
        @FXML private TextField minText;
        @FXML private TextField maxText;
        @FXML private TextField partsSearchText;
        @FXML private Button partsSearchButton;
        @FXML private Button addButton;
        @FXML private Button saveButton;
        @FXML private Button closeButton;
        @FXML private Button deleteButton;

        // Parts table
        @FXML public TableView<Part> partsTable;
        @FXML public TableColumn<Part, Integer> partId = new TableColumn("ID");
        @FXML public TableColumn<Part, String> partName = new TableColumn("Name");
        @FXML public TableColumn<Part, Integer> partInv = new TableColumn("Inventory Level");
        @FXML public TableColumn<Part, Double> partPrice = new TableColumn("Price/Cost");

        // Products table
        @FXML public TableView<Part> associatedPartsTable;
        @FXML public TableColumn<Part, Integer> aPartId = new TableColumn("ID");
        @FXML public TableColumn<Part, String> aPartName = new TableColumn("Name");
        @FXML public TableColumn<Part, Integer> aPartInv = new TableColumn("Inventory Level");
        @FXML public TableColumn<Part, Double> aPartPrice = new TableColumn("Price/Cost");

        
        
        public void initData(Product selectedProduct) {
            idText.setText(Integer.toString(selectedProduct.getId()));
            nameText.setText(selectedProduct.getName());
            invText.setText(Integer.toString(selectedProduct.getStock()));
            priceText.setText(Double.toString(selectedProduct.getPrice()));
            minText.setText(Integer.toString(selectedProduct.getMin()));
            maxText.setText(Integer.toString(selectedProduct.getMax()));
            this.associatedParts = selectedProduct.getAllAssociatedParts();
            associatedPartsTable.setItems(associatedParts);
        }
    
    @FXML
    public void partsTableSelected() {
        this.addButton.setDisable(false);
    }
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partId.setCellValueFactory(new PropertyValueFactory<Part, Integer>("Id"));
        partName.setCellValueFactory(new PropertyValueFactory<Part, String>("Name"));
        partInv.setCellValueFactory(new PropertyValueFactory<Part, Integer>("Stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<Part, Double>("Price"));        
        partsTable.setItems(inv.getAllParts());
        
        aPartId.setCellValueFactory(new PropertyValueFactory<Part, Integer>("Id"));
        aPartName.setCellValueFactory(new PropertyValueFactory<Part, String>("Name"));
        aPartInv.setCellValueFactory(new PropertyValueFactory<Part, Integer>("Stock"));
        aPartPrice.setCellValueFactory(new PropertyValueFactory<Part, Double>("Price"));
        associatedPartsTable.setItems(associatedParts);
        
        this.addButton.setDisable(true);
    }    
    
    @FXML
    public void addPartButton() {
        associatedParts.add(partsTable.getSelectionModel().getSelectedItem());
    }
    
    @FXML
    public void saveButton() {       
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mainFXML.fxml"));
            Parent root = (Parent) loader.load();
            MainFXMLController mainController = loader.getController();
             if(Integer.parseInt(invText.getText()) > Integer.parseInt(maxText.getText())){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Inventory level too high");
                alert.setContentText("Quantity in stock must be lower than max allowable quantaty");
                alert.showAndWait();
                return;
            }
            Product newProduct = new Product(associatedParts, Integer.parseInt(idText.getText()), nameText.getText(), Double.parseDouble(priceText.getText()), Integer.parseInt(invText.getText()), Integer.parseInt(minText.getText()), Integer.parseInt(maxText.getText()));
            mainController.modifyProductsList(newProduct);
            closeWindow();
          }  catch (IOException ex) {
                System.err.println(ex);
          }       
    }
    
    @FXML
    public void deleteButton() {
        associatedPartsTable.getItems().remove(associatedPartsTable.getSelectionModel().getSelectedItem());
    }
    
    @FXML
    public void partSearchButton() {
        if (partsSearchText.getLength() == 0) {
            partsTable.setItems(inv.getAllParts());
        } else {
        partsTable.setItems(inv.lookupPart(partsSearchText.getText()));
        }
    }
    
    @FXML
    public void closeWindow() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }    
}
