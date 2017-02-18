/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managert;

import Models.ConnectionSQL;
import Models.PersonModel;
import PersonViewPackage.PersonController;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Thonon
 */
public class MainViewController implements Initializable {
    
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
    
    // information
    @FXML
    private Label labelNom;
    @FXML
    private Label labelPrenom;
    @FXML
    private Label labelDateNaissance;
    @FXML
    private Label labelNumero;
    @FXML
    private Label labelAdresse;
    @FXML
    private Label labelCodePostal;
    @FXML
    private Label labelVille;
    @FXML
    private ImageView photo;
    
    @FXML
    private void onNewIdentity(ActionEvent event) throws SQLException
    {
      // creation d'une nouvelle identité
       PersonModel model = new PersonModel();
       model.setNom("Nouvelle personne");
       model.setPrenom("Nouvelle personne");
       model.setDateNaissance(LocalDate.now());
       // ajout dans la list
       PersonModel.listPerson.add(model);
      // insert tronk 
      // model.insert();
       
       
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PersonViewPackage/PersonView.fxml"));
              try 
              {
                  AnchorPane ap = loader.load();
                  // recuperation du controller
                  PersonController pc = loader.getController();
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
                

              } catch (IOException ex) {
                  Logger.getLogger(ManageRT.class.getName()).log(Level.SEVERE, null, ex);
              } catch (CloneNotSupportedException ex) {
                  Logger.getLogger(ManageRT.class.getName()).log(Level.SEVERE, null, ex);
              }
        
      
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // instance de listPerson
        //listPerson = FXCollections.observableArrayList();
        // bind de listPerson avec la tableview
        table.setItems(PersonModel.listPerson);
      
       // bind des colonnes
        nomColumn.setCellValueFactory(cellData->cellData.getValue().nomProperty());
        prenomColumn.setCellValueFactory(cellData->cellData.getValue().prenomProperty());
        prioriteColumn.setCellValueFactory(cellData->cellData.getValue().prioriteProperty());
        categorieColumn.setCellValueFactory(cellData->cellData.getValue().categorieProperty());
       
        // tablecell 
        prioriteColumn.setCellFactory(a->new ProprieteTableCell());
      // categorieColumn.setCellFactory(tableCell->new ProprieteTableCell());
      // refresh view
      this.refreshView();
    }    
    
    public void refreshView()
    {
        // clear du list person
        PersonModel.listPerson.clear();
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
                model.setNom(result.getString("nom"));
                model.setPrenom(result.getString("prenom"));
                model.setNumNational(result.getString("num_national"));
                java.sql.Date d = result.getDate("date_naissance");
                model.setDateNaissance(d.toLocalDate());
                model.setAdresse(result.getString("adresse"));
                model.setNumero(result.getString("numero"));
                model.setBoite(result.getString("boite"));
                model.setVille(result.getString("ville"));
                model.setCodePostal(result.getString("code_postal"));
                model.setCategorie(result.getString("categorie"));
                model.setPriorite(result.getString("priorite"));
                model.setPhoto(result.getBlob("photo"));
                // ajout dans le tableview
                PersonModel.listPerson.add(model);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        // listener
        TablePersonChangeListener listener = new TablePersonChangeListener();
        PersonModel.listPerson.addListener(listener);
    }
    
    @FXML
    public void handleClic(Event event) throws SQLException
    {
        if(event.getSource() == table )
        {
            
            PersonModel model = (PersonModel)((TableView)event.getSource()).getSelectionModel().getSelectedItem();
            if(model == null)
                   return;
            
            MouseEvent ev = (MouseEvent)event;
            if(ev.getClickCount() > 1)
            {
            
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/PersonViewPackage/PersonView.fxml"));
                try 
                {
                    AnchorPane ap = loader.load();
                    // recuperation du controller
                    PersonController pc = loader.getController();
                    // set du model
                    pc.setModel(model);
                    // création de la scene
                    Scene scene = new Scene(ap);
                    // creatin du stage
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setMaximized(false);
                    stage.setFullScreen(false);
                    stage.initStyle(StageStyle.UNIFIED);
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
            else
            {
                labelNom.textProperty().bind(model.nomProperty());
                labelPrenom.textProperty().bind(model.prenomProperty());
                labelDateNaissance.textProperty().bind(new SimpleObjectProperty<String>(model.getDateNaissance().toString()));
                labelAdresse.textProperty().bind(model.adresseProperty());
                labelNumero.textProperty().bind(model.numeroProperty());
                labelCodePostal.textProperty().bind(model.codePostalProperty());
                labelVille.textProperty().bind(model.villeProperty());
               // labelAdresse.setText(model.getAdresse() + " " + model.getNumero() + " à " + model.getCodePostal() + " " + model.getVille());
                if(model.getPhoto() != null)
                {
                    Image ima = new Image(model.getPhoto().getBinaryStream());
                    photo.setImage(ima);
                }
                else
                    photo.setImage(null);
            }
        }
        
        
        
    }

    
}
