/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managert;

import Models.PersonModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author Thonon
 */
public class MainViewController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private void onNewIdentity(ActionEvent event)
    {
      // creation d'une nouvelle identité
       PersonModel p = new PersonModel();
      // insert tronk 
       p.insert();
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
       
    }    
    
}
