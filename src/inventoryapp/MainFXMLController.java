package inventoryapp;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class MainFXMLController implements Initializable {
    public static Inventory inv = new Inventory();
    @FXML private Button modPartButton;
    @FXML private Button modProductButton;
    @FXML private Button deletePartButton;
    @FXML private Button deleteProductButton;
    @FXML private Button partsSearchButton;
    @FXML private Button productsSearchButton;
    @FXML private TextField partsSearchText;
    @FXML private TextField productsSearchText;
    
    // Parts table
    @FXML public TableView<Part> partsTable;
    @FXML public TableColumn<Part, Integer> partId = new TableColumn("ID");
    @FXML public TableColumn<Part, String> partName = new TableColumn("Name");
    @FXML public TableColumn<Part, Integer> partInv = new TableColumn("Inventory Level");
    @FXML public TableColumn<Part, Double> partPrice = new TableColumn("Price/Cost");
    
    // Products table
    @FXML public TableView<Product> productsTable;
    @FXML public TableColumn<Product, Integer> productId = new TableColumn("ID");
    @FXML public TableColumn<Product, String> productName = new TableColumn("Name");
    @FXML public TableColumn<Product, Integer> productInv = new TableColumn("Inventory Level");
    @FXML public TableColumn<Product, Double> productPrice = new TableColumn("Price/Cost");    
    
    @FXML
    private void addPartButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("addPartFXML.fxml"));
        
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = new Stage();
        stage.setTitle("Add Part");
        stage.setMinHeight(450.00);
        stage.setMaxHeight(450.00);
        stage.setMinWidth(450.00);
        stage.setMaxWidth(450.00);
        stage.setScene(scene);
        stage.show();
    }    
    
    @FXML
    private void modPartButton(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modPartFXML.fxml"));
            Parent mainScene = loader.load();
            ModPartFXMLController modControl = loader.getController();
            modControl.initData(partsTable.getSelectionModel().getSelectedItem());

            Scene modPartScene = new Scene(mainScene);
            Stage stage = new Stage();       
            stage.setTitle("Modify Part");
            stage.setMinHeight(450.00);
            stage.setMaxHeight(450.00);
            stage.setMinWidth(450.00);
            stage.setMaxWidth(450.00);
            stage.setScene(modPartScene);
            stage.show();
          }  catch (IOException ex) {
                System.err.println(ex);
          }
    }
    
    @FXML
    private void partTableSelected() {
        this.modPartButton.setDisable(false);
    }
    
    
    
    @FXML
    private void productTableSelected() {
        this.modProductButton.setDisable(false);
    }
    
    @FXML
    private void addProductButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("addProductFXML.fxml"));
        
        Scene scene = new Scene(fxmlLoader.load(), 750, 1200);
        Stage stage = new Stage();
        stage.setTitle("Add product");
        stage.setMinHeight(750.00);
        stage.setMaxHeight(750.00);
        stage.setMinWidth(1200.00);
        stage.setMaxWidth(1200.00);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void modProductButton(ActionEvent event) throws IOException {        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modProductFXML.fxml"));
            Parent modScene = loader.load();
            ModProductFXMLController modControl = loader.getController();
            modControl.initData(productsTable.getSelectionModel().getSelectedItem());

            Scene modProductScene = new Scene(modScene);
            Stage stage = new Stage();
            stage.setTitle("Modify product");
            stage.setMinHeight(750.00);
            stage.setMaxHeight(750.00);
            stage.setMinWidth(1200.00);
            stage.setMaxWidth(1200.00);
            stage.setScene(modProductScene);
            stage.show();
          }  catch (IOException ex) {
                System.err.println(ex);
          }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partId.setCellValueFactory(new PropertyValueFactory<Part, Integer>("Id"));
        partName.setCellValueFactory(new PropertyValueFactory<Part, String>("Name"));
        partInv.setCellValueFactory(new PropertyValueFactory<Part, Integer>("Stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<Part, Double>("Price"));        
        partsTable.setItems(inv.getAllParts());
        
        productId.setCellValueFactory(new PropertyValueFactory<Product, Integer>("Id"));
        productName.setCellValueFactory(new PropertyValueFactory<Product, String>("Name"));
        productInv.setCellValueFactory(new PropertyValueFactory<Product, Integer>("Stock"));
        productPrice.setCellValueFactory(new PropertyValueFactory<Product, Double>("Price"));
        productsTable.setItems(inv.getAllProducts());
        
        this.modPartButton.setDisable(true);
        this.modProductButton.setDisable(true);
    }    
    
    @FXML
    public void addToPartsList(Part newPart) {
        newPart.setId(1 + partsTable.getItems().size());
        partsTable.getItems().add(newPart);
    }
    
    @FXML
    public void modifyPartsList(Part modPart) {
        inv.updatePart(modPart.getId()-1, modPart);        
    }
    
    @FXML
    public void deletePart() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("Are you sure you want to delete this part?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            inv.deletePart(partsTable.getSelectionModel().getSelectedItem());
        } else {
            return;
        }
    }
    
    public void addToProductsList(Product newProduct) {
        newProduct.setId(1+productsTable.getItems().size());
        productsTable.getItems().add(newProduct);
    }
    
    @FXML
    public void modifyProductsList(Product modProduct) {
        inv.updateProduct(modProduct.getId()-1, modProduct);        
    }
    
    @FXML
    public void deleteProduct() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("Are you sure you want to delete this product?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            inv.deleteProduct(productsTable.getSelectionModel().getSelectedItem());
        } else {
            return;
        }
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
    public void productSearchButton() {
        if (productsSearchText.getLength() == 0) {
            productsTable.setItems(inv.getAllProducts());
        } else {
        productsTable.setItems(inv.lookupProduct(productsSearchText.getText()));
        }
    }      
}
