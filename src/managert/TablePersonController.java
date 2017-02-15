/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managert;

import Models.ConnectionSQL;
import Models.PersonModel;
import PersonViewPackage.PersonController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EventListener;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentEvent.EventType;

/**
 * FXML Controller class
 *
 * @author Thonon
 */
public class TablePersonController implements Initializable, EventListener {

    @FXML
    private TableView table;
    @FXML
    private TableColumn<PersonModel,String> nomColumn;
    @FXML
    private TableColumn<PersonModel,String> prenomColumn;
    @FXML
    private TableColumn<PersonModel,String> prioriteColumn;
    @FXML
    private TableColumn<PersonModel,String> categorieColumn;
     
    private ObservableList<PersonModel> listPerson;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // instance de listPerson
        listPerson = FXCollections.observableArrayList();
        // bind de listPerson avec la tableview
        table.setItems(listPerson);
      
       // bind des colonnes
        nomColumn.setCellValueFactory(cellData->cellData.getValue().nomProperty());
        prenomColumn.setCellValueFactory(cellData->cellData.getValue().prenomProperty());
        prioriteColumn.setCellValueFactory(cellData->cellData.getValue().prioriteProperty());
        categorieColumn.setCellValueFactory(cellData->cellData.getValue().categorieProperty());
       
        // tablecell 
       prioriteColumn.setCellFactory(tableCell->new ProprieteTableCell());
       categorieColumn.setCellFactory(tableCell->new ProprieteTableCell());
      // refresh view
      this.refreshView();
    }  

    
    
    public TableView getTable() {
        return table;
    }
    
    public void refreshView()
    {
        // clear du list person
        listPerson.clear();
        // requete sql de chargement de l'ensemble des personnes
        String sql = "select * from t_identity";
        try 
        {
            PreparedStatement st = ConnectionSQL.getCon().prepareStatement(sql);
            
            ResultSet result = st.executeQuery();
            //result.first();
            while(result.next())
            {
                PersonModel model = new PersonModel();
                model.setId(result.getInt("id"));
                model.setNom(result.getString("nom"));
                model.setPrenom(result.getString("prenom"));
                model.setPriorite(result.getString("priorite"));
                model.setCategorie(result.getString("categorie"));
                // ajout dans le tableview
                listPerson.add(model);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TablePersonController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    @FXML
    public void handleClic(Event event) throws SQLException
    {
        if(event.getSource() == table)
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PersonViewPackage/PersonView.fxml"));
            try 
            {
                AnchorPane ap = loader.load();
                // recuperation du controller
                PersonController pc = loader.getController();
                // récupération du model
                PersonModel model = (PersonModel)((TableView)event.getSource()).getSelectionModel().getSelectedItem();
                if(model != null)
                    model.select(model.getId());
                // set du model
                pc.setModel(model);
                // création de la scene
                Scene scene = new Scene(ap);
                // creatin du stage
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                // ajout de la scene au stage
                stage.setScene(scene);
                // affichage de la vue
                stage.showAndWait();
                // select de la nouvelle vue
                this.refreshView();
                
            } catch (IOException ex) {
                Logger.getLogger(ManageRT.class.getName()).log(Level.SEVERE, null, ex);
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(ManageRT.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        
    }

   
    
}
