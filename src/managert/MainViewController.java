/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managert;

import Models.PersonModel;
import PersonViewPackage.PersonController;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Thonon
 */
public class MainViewController implements Initializable {
    
   
    
    @FXML
    private void onNewIdentity(ActionEvent event) throws SQLException
    {
      // creation d'une nouvelle identité
       PersonModel model = new PersonModel();
      // insert tronk 
       model.insert();
       
     
       
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PersonViewPackage/PersonView.fxml"));
              try 
              {
                  AnchorPane ap = loader.load();
                  // recuperation du controller
                  PersonController pc = loader.getController();
                 
                  if(model != null)
                      model.select(model.getId());
                  // set du model
                  pc.setModel(model);
                  // création de la scene
                  Scene scene = new Scene(ap);
                  // creatin du stage
                  Stage stage = new Stage();
                  // ajout de la scene au stage
                  stage.setScene(scene);
                  // affichage de la vue
                  stage.showAndWait();
                

              } catch (IOException ex) {
                  Logger.getLogger(ManageRT.class.getName()).log(Level.SEVERE, null, ex);
              } catch (CloneNotSupportedException ex) {
                  Logger.getLogger(ManageRT.class.getName()).log(Level.SEVERE, null, ex);
              }
        
      
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
       
    }    
    
}
