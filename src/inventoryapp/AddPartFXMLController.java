package inventoryapp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AddPartFXMLController implements Initializable {
    @FXML private TextField idText;
    @FXML private TextField nameText;
    @FXML private TextField invText;
    @FXML private TextField priceText;
    @FXML private TextField minText;
    @FXML private TextField maxText;    
    @FXML private TextField partSourceText;
    @FXML private Label partSourceLabel;
    @FXML private RadioButton inHouseButton;
    @FXML private RadioButton outsourcedButton;
    @FXML private ToggleGroup partSourceGroup;
    @FXML private Button closeButton;
    
    @FXML
    public void inHouseButton() {
        partSourceLabel.setText("Machine ID");
        partSourceText.setPromptText("Machine ID");
    }
    
    @FXML
    public void outsourcedButton() {
        partSourceLabel.setText("Company Name");
        partSourceText.setPromptText("Company Name");
    }
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partSourceGroup = new ToggleGroup();
        this.inHouseButton.setToggleGroup(partSourceGroup);
        this.outsourcedButton.setToggleGroup(partSourceGroup);                  
    }   
    
    public Part newPart() {
        if (inHouseButton.isSelected()) {
            InHouse temp = new InHouse(0, nameText.getText(), Double.parseDouble(priceText.getText()), Integer.parseInt(invText.getText()), Integer.parseInt(minText.getText()), Integer.parseInt(maxText.getText()), Integer.parseInt(partSourceText.getText()));
            return temp;
        } else {
            Outsourced temp = new Outsourced(0, nameText.getText(), Double.parseDouble(priceText.getText()), Integer.parseInt(invText.getText()), Integer.parseInt(minText.getText()), Integer.parseInt(maxText.getText()), partSourceText.getText());
            return temp;
        }
    }    
    
    public void addPartButton() {       
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mainFXML.fxml"));
            Parent root = (Parent) loader.load();
            MainFXMLController mainController = loader.getController();
            if(Integer.parseInt(invText.getText()) > Integer.parseInt(maxText.getText())){
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Inventory level too high");
                alert.setContentText("Quantity in stock must be lower than max allowable quantaty");
                alert.showAndWait();
                return;
            }
            mainController.addToPartsList(newPart());
            closeWindow();
          }  catch (IOException ex) {
                System.err.println(ex);
          }
    }  
    
    @FXML
    public void closeWindow() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    
   
        
    
}
