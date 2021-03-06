/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managert;

import DocumentViewPackage.DocumentController;
import DocumentViewPackage.NewDocumentController;
import DocumentViewPackage.NewDocumentController.TYPE_DOCUMENT;
import GestionPackage.AjoutTodoController;
import GestionPackage.Apostille;
import GestionPackage.DateRappelTableCell;
import GestionPackage.RappelTableCell;
import GestionPackage.RappelTableRow;
import LinkPackage.LinkController;
import Models.ApostilleModel;
import Models.CategorieModel;
import Models.ConnectionSQL;
import Models.DataModel;
import Models.DocumentModel;
import Models.MemoModel;
import Models.PersonModel;
import Models.PrioriteModel;
import Models.TodoModel;
import PersonViewPackage.PersonController;
import UtilPackage.ViewPdfController;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    private TableColumn<PersonModel,PrioriteModel> prioriteColumn;
    @FXML
    private TableColumn<PersonModel,CategorieModel> categorieColumn;
   // @FXML
   // private TableColumn<PersonModel,Boolean> evenementColumn;
    
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
    
     // Documents
    @FXML
    private ListView listApostilles;
    @FXML
    private Button bAjoutApostilles;
    @FXML
    private Button bSuppressionApostille;
    @FXML
    private Button bVoirApostille;
    @FXML
    private TextArea areaApostilles;
    
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
    private TextArea areaTodoCommentaire;
    @FXML
    private Button bAjoutTodo;
    @FXML
    private Button bSuppressionDocumentTodo;
    
    // Links
    @FXML
    private ListView listLinks;
    @FXML
    private Button bAddLink;
    // Memos
     @FXML
     private TextArea textMemo;
     @FXML
     private TableView tableMemos;
     @FXML
     private TableColumn<MemoModel,LocalDateTime> memoDateTimeColumn;
     @FXML
     private TableColumn<MemoModel,String> memoTextColumn;
     
   
    // Evenement
    
  
    private DocumentModel backDocument;
    
    @FXML
    private void handleCreateApostille() throws IOException
    {
        Apostille apo = new Apostille();
    }
    
    @FXML
    private void handleClicMemo() throws IOException
    {
            MemoModel model = (MemoModel) tableMemos.getSelectionModel().getSelectedItem();
            if(model != null)
            {
               textMemo.setText(model.getText());

            }
        
    }
    
    @FXML
    private void handleUpdateMemo() throws IOException
    {
        // on récupère le text inscrit dans le textMemo
        if(!textMemo.getText().isEmpty())
        {
            MemoModel model = (MemoModel) tableMemos.getSelectionModel().getSelectedItem();
            if(model != null)
            {
                // modif du model
                model.setText(textMemo.getText());
                model.setDateTime(LocalDateTime.now());
                // refresh de la listMemos
                tableMemos.refresh();

            }
        }
    }
    
    @FXML
    private void handleAddMemo() throws IOException
    {
        // on récupère le text inscrit dans le textMemo
        if(!textMemo.getText().isEmpty())
        {
            MemoModel model = new MemoModel();
            model.setText(textMemo.getText());
            model.setDateTime(LocalDateTime.now());
            this.currentModel.getoMemos().add(model);
            // effacement du text dans la vue
            textMemo.clear();
        }
    }
    
    @FXML
    private void handleDeleteMemo() throws IOException
    {
       MemoModel model = (MemoModel) tableMemos.getSelectionModel().getSelectedItem();
       if(model != null)
       {
           this.currentModel.getoMemos().remove(model);
           // clear du text de la vue
           textMemo.clear();
       }
    }
    
    @FXML
    private void handleAddLink() throws IOException
    {
        // chargement de la vue linkview
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/LinkPackage/LinkView.fxml"));
        AnchorPane pane = loader.load();
        LinkController controller = loader.getController();
        controller.setModel(currentModel);
        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Selectionner les liaisons");
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.setResizable(false);
        stage.showAndWait();
        
    }
    
    @FXML
    private void handleDeleteLink() throws IOException
    {
        PersonModel model = (PersonModel) listLinks.getSelectionModel().getSelectedItem();
        if(model != null)
        {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Suppresion d'un lien");
            alert.setContentText("Etes vous sûr de supprimer ce lien ?");
            ButtonType bOui = new ButtonType("Oui");
            ButtonType bNon = new ButtonType("Non");
            alert.getButtonTypes().setAll(bNon,bOui);
            Optional<ButtonType> ret = alert.showAndWait();
            if(ret.get() == bOui)
            {
                currentModel.getoLinks().remove(model);
            }
            
        }
    }
    
    @FXML
    private void handleQuitter() throws IOException
    {
        // fermeture du programme
        table.getScene().getWindow().hide();
    }
    
    @FXML
    private void handleClicTodo() throws IOException
    {
      TodoModel model = (TodoModel) tableTodos.getSelectionModel().getSelectedItem();
       
      if(model != null)
        areaTodoCommentaire.textProperty().bind(model.textProperty());
       else
        areaTodoCommentaire.setText("");
          
    }
   
     @FXML
    private void handleClicDocument() throws IOException
    {
      DocumentModel model = (DocumentModel) listDocuments.getSelectionModel().getSelectedItem();
       
      if(model != null)
       {
        areaCommentaire.textProperty().bind(model.commentaireProperty());
        // si le model ne possède pas de fichier pdf attaché, disable du bouton view document
        if(model.getFichier() == null)
            bVoirDocument.setDisable(true);
        else
            bVoirDocument.setDisable(false);
       }
      
    }
    
     @FXML
    private void handleClicApostille() throws IOException
    {
      ApostilleModel model = (ApostilleModel) listApostilles.getSelectionModel().getSelectedItem();
       
      if(model != null)
       {
        areaApostilles.textProperty().bind(model.commentaireProperty());
        // si le model ne possède pas de fichier pdf attaché, disable du bouton view document
        if(model.getFichier() == null)
            bVoirApostille.setDisable(true);
        else
            bVoirApostille.setDisable(false);
       }
      
    }
    
      @FXML
    private void handleViewDocument(ActionEvent event) throws IOException
    {
        DocumentModel model = (DocumentModel) listDocuments.getSelectionModel().getSelectedItem();
        if(model != null)
        {
            Blob blob = model.getFichier();
            if(blob != null)
            {
                try
                {
                    ViewPdfController vpc = new ViewPdfController(model.getNom(),model.getFichier());
                    vpc.ShowPDFDocument();
                } catch (SQLException ex) {
                    Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    @FXML
    private void handleAjoutDocument(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/DocumentViewPackage/NewDocumentView.fxml"));
        AnchorPane pane = loader.load();  
        NewDocumentController controller = loader.getController();
        controller.setType(TYPE_DOCUMENT.DOC); // on specifie que c'est un document de type doc
        controller.setModel(this.currentModel);
        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Création d'un document");
        stage.initStyle(StageStyle.UTILITY);
        stage.setResizable(false);
        stage.showAndWait();
    }
    
    @FXML
    private void handleAjoutApostille(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/DocumentViewPackage/NewDocumentView.fxml"));
        AnchorPane pane = loader.load();  
        NewDocumentController controller = loader.getController();
        controller.setType(TYPE_DOCUMENT.APO); // on specifie que c'est un document de type doc
        controller.setModel(this.currentModel);
        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Création d'un document");
        stage.initStyle(StageStyle.UTILITY);
        stage.setResizable(false);
        stage.showAndWait();
    }
    
     @FXML
    private void handleAjoutTodo(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/GestionPackage/AjoutTodoView.fxml"));
        AnchorPane pane = loader.load();  
        AjoutTodoController controller = loader.getController();
        controller.setModel(this.currentModel);
        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Créatio d'une tâche");
        stage.initStyle(StageStyle.UTILITY);
        stage.setResizable(false);
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
            areaCommentaire.textProperty().unbind();
            areaCommentaire.setText("");
            bVoirDocument.setDisable(true);
            
         }
    }
    
    @FXML
    private void handleSuppressionApostille(ActionEvent event) throws IOException
    {
        ApostilleModel model = (ApostilleModel) listApostilles.getSelectionModel().getSelectedItem();
        if(model != null)
        {
            // view de confirmation
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Suppression d'une apostille");
            alert.setContentText("Etes-vous sûr de supprimer l'apostille '" + model.getNom() + "' ?");
            ButtonType buttonOui = new ButtonType("Oui");
            ButtonType buttonNon = new ButtonType("Non");
            alert.getButtonTypes().setAll(buttonNon,buttonOui);
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == buttonNon)
                return;

            //
            this.currentModel.getoApostilles().remove(model);
            areaApostilles.textProperty().unbind();
            areaApostilles.setText("");
            bVoirApostille.setDisable(true);
            
         }
    }
    
      @FXML
    private void handleSuppressionTodo(ActionEvent event) throws IOException
    {
        TodoModel model = (TodoModel) tableTodos.getSelectionModel().getSelectedItem();
        if(model != null)
        {
            // view de confirmation
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Suppression d'une tâche");
            alert.setContentText("Etes-vous sûr de supprimer la tâche '" + model.getTitre() + "' ?");
            ButtonType buttonOui = new ButtonType("Oui");
            ButtonType buttonNon = new ButtonType("Non");
            alert.getButtonTypes().setAll(buttonNon,buttonOui);
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == buttonNon)
                return;

            //
            this.currentModel.getoTodos().remove(model);
            areaTodoCommentaire.textProperty().unbind();
            areaTodoCommentaire.setText("");
            
         }
    }
    
    @FXML
    private void onHelp(ActionEvent event) throws SQLException
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HelpPackage/HelpView.fxml"));
            AnchorPane ap = loader.load();
            Scene scene = new Scene(ap);
            // creatin du stage
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            // ajout de la scene au stage
            stage.setScene(scene);
            // affichage de la vue
            stage.setTitle("Aide");
            stage.initStyle(StageStyle.UTILITY);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    @FXML
    private void onDeleteIdentity(ActionEvent event) throws SQLException
    {
        PersonModel model = (PersonModel)table.getSelectionModel().getSelectedItem();
        if(model != null)
        {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Suppression d'une fiche personne");
            alert.setContentText("Etes-vous sûr de supprimer la fiche personne '" + model.getNom() + " " + model.getPrenom() +  "' ?");
            ButtonType buttonOui = new ButtonType("Oui");
            ButtonType buttonNon = new ButtonType("Non");
            alert.getButtonTypes().setAll(buttonNon,buttonOui);
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == buttonNon)
                return;
            
            // suppression de la fiche en cours dans l'observablelist
            DataModel.getoPersons().remove(model);
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
       DataModel.getoPersons().add(model);
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
                  stage.initStyle(StageStyle.UTILITY);
                  stage.setResizable(false);
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
        table.setItems(DataModel.getoPersons());
        // ajout du tablerowfactory
        table.setRowFactory(a->new EvenementTableRow());

       // bind des colonnes
        nomColumn.setCellValueFactory(cellData->cellData.getValue().nomProperty());
        prenomColumn.setCellValueFactory(cellData->cellData.getValue().prenomProperty());
        prioriteColumn.setCellValueFactory(cellData->cellData.getValue().prioriteProperty());
        categorieColumn.setCellValueFactory(cellData->cellData.getValue().categorieProperty());
        //evenementColumn.setCellValueFactory(cellData->cellData.getValue().evenementRappelProperty());
       
        // tablecell 
        prioriteColumn.setCellFactory(a->new ProprieteTableCell());
       // evenementColumn.setCellFactory(a->new EvenementRappelTableCell());
      // categorieColumn.setCellFactory(tableCell->new ProprieteTableCell());
      // refresh view
      // ajout callback à la table todo
        tableTodos.setRowFactory(a->new RappelTableRow());
      
    
      // listener de perte de focus
           
            listDocuments.itemsProperty().addListener(new ChangeListener() 
            {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) 
            {
                if(listDocuments.getSelectionModel().isEmpty())
                {
                    areaCommentaire.textProperty().unbind();
                    areaCommentaire.setText("");
                }
            }
        });
            
            
            tableTodos.itemsProperty().addListener(new ChangeListener() 
            {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) 
            {
                if(tableTodos.getSelectionModel().isEmpty())
                {
                    areaTodoCommentaire.textProperty().unbind();
                    areaTodoCommentaire.setText("");
                }
            }
        });
           // configuration du listMemos
         
                  
    }    
    
    public void refreshView()
    {
      
    }
    
    @FXML
    public void handleClic(Event event) throws SQLException
    {
        if(event.getSource() == table)
        {
            // disable du bouton ('voir document')
            bVoirDocument.setDisable(true);
            bVoirApostille.setDisable(true);
            
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
                    stage.initStyle(StageStyle.UTILITY);
                    stage.setResizable(false);
                    stage.setTitle("Fiche personne");
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
                columnTitre.setCellValueFactory(cellData->cellData.getValue().titreProperty());
                columnDateCreation.setCellValueFactory(cellData->cellData.getValue().dateCreationProperty());
                columnDateRappel.setCellValueFactory(cellData->cellData.getValue().dateRappelProperty());
                columnDateRappel.setCellFactory(a->new DateRappelTableCell());
                columnRappel.setCellValueFactory(cellData->cellData.getValue().rappelProperty());
                columnRappel.setCellFactory(a->new RappelTableCell());
                tableTodos.setItems(currentModel.getoTodos());
                
               // listeApostilles
                listApostilles.setItems(currentModel.getoApostilles());
                // listLinks
                listLinks.setItems(currentModel.getoLinks());
                // listMemos
               memoDateTimeColumn.setCellValueFactory(cellData->cellData.getValue().dateTimeProperty());
               memoTextColumn.setCellValueFactory(cellData->cellData.getValue().textProperty());
               memoDateTimeColumn.setCellFactory(a->new MemoTableCell());
               tableMemos.setItems(currentModel.getoMemos());
            }
        }
        
        
        
    }

    
}
  