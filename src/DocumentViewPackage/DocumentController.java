/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DocumentViewPackage;

import ControllerInterface.Controller;
import Models.ConnectionSQL;
import Models.DocumentModel;
import Models.PersonModel;
import UtilPackage.ViewPdfController;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.sql.rowset.serial.SerialBlob;

/**
 * FXML Controller class
 *
 * @author Thonon
 */
public class DocumentController extends Controller implements Initializable 
{
     @FXML
     private ListView listDocuments;
     @FXML
     private TextArea commentaire;
     
     @FXML
     private Button bVoirFichier;
     @FXML
     private Button bUndo;
     @FXML
     private Button bConfirm;
     
    private ObservableList<DocumentModel> oDocuments;
     
     private long personId = -1;
     
    

    public void setPersonId(long id)
    {
        personId = id;
       
    }

    
     @FXML
    public void handleDeleteDocument() throws SQLException
    {
        // suppresion d'un document
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
            this.model.getoDocuments().remove(model);
            commentaire.setText("");
        }
    }
    
    @FXML
    public void handleClic()
    {
        // clic sur un élément
        DocumentModel model = (DocumentModel) listDocuments.getSelectionModel().getSelectedItem();
        if(model != null)
        {
            commentaire.setText(model.getCommentaire());
        }
    }
     @FXML
    public void handleUndo()
    {
        listDocuments.getScene().getWindow().hide();
    }
    
    @FXML
    public void handleNewDocument() throws IOException
    {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/DocumentViewPackage/NewDocumentView.fxml"));
        AnchorPane pane = loader.load();  
        NewDocumentController controller = loader.getController();
        controller.setModel(this.model);
        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
        
    }
    
    @FXML
    public void handleVoirFichier() throws IOException, SQLException
    {
        // récupération du DocumentModel sélectionné
        DocumentModel model = (DocumentModel) listDocuments.getSelectionModel().getSelectedItem();
        if(model != null && model.getFichier() != null)
        {
          ViewPdfController vpc = new ViewPdfController(model.getNom(),model.getFichier());
          vpc.ShowPDFDocument();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
         
    }    

    @Override
    public PersonModel getModel() 
    {
        return this.model;
    }

    @Override
    public void setModel(PersonModel model) 
    {
      this.model = model;
      
      this.listDocuments.setItems(this.model.getoDocuments());
    }
    
}
