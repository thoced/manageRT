/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PersonViewPackage;

import Models.PersonModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.SingleSelectionModel;



/**
 * FXML Controller class
 *
 * @author Thonon
 */
public class PersonController implements Initializable,InvalidationListener {

    @FXML
    private ComboBox comboPriorite;
    @FXML
    private ComboBox comboCategorie;
    @FXML
    private TextField nom;
    @FXML
    private TextField numeroNational;
    @FXML
    private TextField dateNaissance;
    @FXML
    private TextField adresse;
    @FXML
    private TextField numero;
    @FXML
    private TextField boite;
    @FXML
    private TextField ville;
    @FXML
    private TextField codePostal;
    @FXML
    private TextField prenom;
    @FXML
    private Button bCancel;
    
    
    private ObservableList<String> oListPriorite;
    private ObservableList<String> oListCategorie;
    
    private PersonModel model;
    private PersonModel backupModel;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        
       if(comboPriorite != null)
       {
           oListPriorite = FXCollections.observableArrayList();
           oListPriorite.add("Surveillance normale");
           oListPriorite.add("Surveillance régulière");
           oListPriorite.add("Surveillance accrue");
           comboPriorite.setItems(oListPriorite);
       }
       
       if(comboCategorie != null)
       {
           oListCategorie = FXCollections.observableArrayList();
           oListCategorie.add("Radicalisé");
           oListCategorie.add("Retour de zone de combat");
           oListCategorie.add("Retour après refoulement");
           comboCategorie.setItems(oListCategorie);
       }
           
    
    }    
    
    public void setModel(PersonModel model) throws CloneNotSupportedException
    {
        if(model != null)
        {
          comboPriorite.valueProperty().bindBidirectional(model.prioriteProperty());
          comboCategorie.valueProperty().bindBidirectional(model.categorieProperty());
          nom.textProperty().bindBidirectional(model.nomProperty());
          prenom.textProperty().bindBidirectional(model.prenomProperty());
          nom.textProperty().addListener(this);
        
        
        }
    }
    
    @FXML
    public void cancel(ActionEvent event)
    {
        if(backupModel != null)
        {
            this.model.setNom(backupModel.getNom());
            this.model.setPrenom(backupModel.getPrenom());
        }
    }

    @Override
    public void invalidated(Observable observable) 
    {
       System.out.println("invalidation: " + observable);
    }
    
}
