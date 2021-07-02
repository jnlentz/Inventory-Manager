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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class ModPartFXMLController implements Initializable {
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
    
    public boolean partType(Part part) {
        InHouse temp = new InHouse(0, "temp", 0.00, 0, 0, 0, 0);
        if (part.getClass() == temp.getClass()) {
            return true;
        } else {
            return false;
        }
    }
    
    public void initData(Part selectedPart) {
        idText.setText(Integer.toString(selectedPart.getId()));
        nameText.setText(selectedPart.getName());
        invText.setText(Integer.toString(selectedPart.getStock()));
        priceText.setText(Double.toString(selectedPart.getPrice()));
        minText.setText(Integer.toString(selectedPart.getMin()));
        maxText.setText(Integer.toString(selectedPart.getMax()));
        if (partType(selectedPart)) {
            InHouse temp = (InHouse) selectedPart;
            partSourceText.setText(Integer.toString(temp.getMachineId()));
            inHouseButton.setSelected(true);
            inHouseButton();
            
        } else {
            Outsourced temp = (Outsourced) selectedPart;
            partSourceText.setText(temp.getCompanyName());
            outsourcedButton.setSelected(true);
            outsourcedButton();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partSourceGroup = new ToggleGroup();
        this.inHouseButton.setToggleGroup(partSourceGroup);
        this.outsourcedButton.setToggleGroup(partSourceGroup);
    }    
    
    public Part updatedPart() {
        if (inHouseButton.isSelected()) {
            InHouse temp = new InHouse(Integer.parseInt(idText.getText()), nameText.getText(), Double.parseDouble(priceText.getText()), Integer.parseInt(invText.getText()), Integer.parseInt(minText.getText()), Integer.parseInt(maxText.getText()), Integer.parseInt(partSourceText.getText()));
            return temp;
        } else {
            Outsourced temp = new Outsourced(Integer.parseInt(idText.getText()), nameText.getText(), Double.parseDouble(priceText.getText()), Integer.parseInt(invText.getText()), Integer.parseInt(minText.getText()), Integer.parseInt(maxText.getText()), partSourceText.getText());
            return temp;
        }
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
            mainController.modifyPartsList(updatedPart());
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
