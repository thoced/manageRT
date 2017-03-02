/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LinkPackage;

import ControllerInterface.Controller;
import Models.DataModel;
import Models.PersonModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Thonon
 */
public class LinkController extends Controller implements Initializable {

    
    private PersonModel model;
    
    @FXML
    private TableView tablePerson;
    @FXML
    private TableColumn<PersonModel,String> columnNom;
    @FXML
    private TableColumn<PersonModel,String> columnPrenom;
    @FXML
    private TableColumn<PersonModel,String> columnDateNaissance;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
       tablePerson.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
       // chargement de la liste des identité
       tablePerson.setItems(DataModel.getoPersons());
       
       columnNom.setCellValueFactory(cellData->cellData.getValue().nomProperty());
       columnPrenom.setCellValueFactory(cellData->cellData.getValue().prenomProperty());
       columnDateNaissance.setCellValueFactory(cellData->cellData.getValue().dateNaissanceProperty());
        
    }  
    
    @FXML
    public void handleConfirmer()
    {
        // récupération de la liste des sélections
        ObservableList<PersonModel> oSelected = tablePerson.getSelectionModel().getSelectedItems();
        if(oSelected != null)
        {
            model.getoLinks().addAll(oSelected);
        }
        
        tablePerson.getScene().getWindow().hide();
    }
    
    @FXML
    public void handleAnnuler()
    {
        
        
        tablePerson.getScene().getWindow().hide();
    }

    @Override
    public PersonModel getModel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setModel(PersonModel model)
    {
         this.model = model;
    }
    
}
