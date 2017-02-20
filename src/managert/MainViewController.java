/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managert;

import DocumentViewPackage.DocumentController;
import DocumentViewPackage.NewDocumentController;
import GestionPackage.DateRappelTableCell;
import GestionPackage.RappelTableCell;
import Models.ConnectionSQL;
import Models.DataModel;
import Models.DocumentModel;
import Models.PersonModel;
import Models.TodoModel;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
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
    
    public static DataModel dataModel = new DataModel();
    
    private PersonModel currentModel;
    
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
    
    // Documents
    @FXML
    private ListView listDocuments;
    @FXML
    private Button bAjoutDocument;
    @FXML
    private Button bSuppressionDocument;
    @FXML
    private Button bVoirDocument;
    @FXML
    private TextArea areaCommentaire;
    
    // Todos
    @FXML
    private TableView tableTodos;
    @FXML
    private TableColumn<TodoModel,String> columnTitre;
    @FXML
    private TableColumn<TodoModel,LocalDate> columnDateCreation;
    @FXML
    private TableColumn<TodoModel,LocalDate> columnDateRappel;
    @FXML
    private TableColumn<TodoModel,Boolean> columnRappel;
    @FXML
    private Button bAjoutTodo;
    @FXML
    private Button bSuppressionDocumentTodo;
  
    private DocumentModel backDocument;
    
     @FXML
    private void handleClicDocument() throws IOException
    {
      DocumentModel model = (DocumentModel) listDocuments.getSelectionModel().getSelectedItem();
       
      if(model != null)
       {
        areaCommentaire.textProperty().bind(model.commentaireProperty());
       }
      
    }
    
    @FXML
    private void handleAjoutDocument(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/DocumentViewPackage/NewDocumentView.fxml"));
        AnchorPane pane = loader.load();  
        NewDocumentController controller = loader.getController();
        controller.setModel(this.currentModel);
        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }
    
    @FXML
    private void handleSuppressionDocument(ActionEvent event) throws IOException
    {
        DocumentModel model = (DocumentModel) listDocuments.getSelectionModel().getSelectedItem();
        if(model != null)
        {
            // view de confirmation
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Suppression d'un document");
            alert.setContentText("Etes-vous sûr de supprimer le document '" + model.getNom() + "' ?");
            ButtonType buttonOui = new ButtonType("Oui");
            ButtonType buttonNon = new ButtonType("Non");
            alert.getButtonTypes().setAll(buttonNon,buttonOui);
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == buttonNon)
                return;

            //
            this.currentModel.getoDocuments().remove(model);
         }
    }
   
    @FXML
    private void onNewIdentity(ActionEvent event) throws SQLException
    {
      // creation d'une nouvelle identité
       PersonModel model = new PersonModel();
       model.setNom("Nouvelle personne");
       model.setPrenom("Nouvelle personne");
       model.setDateNaissance(LocalDate.now());
       // ajout dans la list
       dataModel.getoPersons().add(model);
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
        dataModel.loadData();
        // instance de listPerson
        //listPerson = FXCollections.observableArrayList();
        // bind de listPerson avec la tableview
        table.setItems(dataModel.getoPersons());
      
       // bind des colonnes
        nomColumn.setCellValueFactory(cellData->cellData.getValue().nomProperty());
        prenomColumn.setCellValueFactory(cellData->cellData.getValue().prenomProperty());
        prioriteColumn.setCellValueFactory(cellData->cellData.getValue().prioriteProperty());
        categorieColumn.setCellValueFactory(cellData->cellData.getValue().categorieProperty());
       
        // tablecell 
        prioriteColumn.setCellFactory(a->new ProprieteTableCell());
      // categorieColumn.setCellFactory(tableCell->new ProprieteTableCell());
      // refresh view
      
     
      
     
      
    }    
    
    public void refreshView()
    {
      
    }
    
    @FXML
    public void handleClic(Event event) throws SQLException
    {
        if(event.getSource() == table )
        {
            
           currentModel = (PersonModel)((TableView)event.getSource()).getSelectionModel().getSelectedItem();
            if(currentModel == null)
                   return;
            
            // chargement des données du model
            currentModel.loadData();
            
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
                    pc.setModel(currentModel);
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
                // Information
                labelNom.textProperty().bind(currentModel.nomProperty());
                labelPrenom.textProperty().bind(currentModel.prenomProperty());
                labelDateNaissance.textProperty().bind(new SimpleObjectProperty<String>(currentModel.getDateNaissance().toString()));
                labelAdresse.textProperty().bind(currentModel.adresseProperty());
                labelNumero.textProperty().bind(currentModel.numeroProperty());
                labelCodePostal.textProperty().bind(currentModel.codePostalProperty());
                labelVille.textProperty().bind(currentModel.villeProperty());
               // labelAdresse.setText(model.getAdresse() + " " + model.getNumero() + " à " + model.getCodePostal() + " " + model.getVille());
                if(currentModel.getPhoto() != null)
                {
                    Image ima = new Image(currentModel.getPhoto().getBinaryStream());
                    photo.setImage(ima);
                }
                else
                    photo.setImage(null);
                
               // listedocuments
                listDocuments.setItems(currentModel.getoDocuments());
                // listeTodos
               // columnTodo.setCellValueFactory(cellData->cellData.getValue().titreProperty());
                columnTitre.setCellValueFactory(cellData->cellData.getValue().titreProperty());
                columnDateCreation.setCellValueFactory(cellData->cellData.getValue().dateCreationProperty());
                columnDateRappel.setCellValueFactory(cellData->cellData.getValue().dateRappelProperty());
                columnDateRappel.setCellFactory(a->new DateRappelTableCell());
                columnRappel.setCellValueFactory(cellData->cellData.getValue().rappelProperty());
                columnRappel.setCellFactory(a->new RappelTableCell());
                tableTodos.setItems(currentModel.getoTodos());
                
            }
        }
        
        
        
    }

    
}
  