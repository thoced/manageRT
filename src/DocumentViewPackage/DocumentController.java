/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DocumentViewPackage;

import Models.ConnectionSQL;
import Models.DocumentModel;
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
import javafx.scene.control.Button;
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
public class DocumentController implements Initializable 
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
     
    // private ObservableList<DocumentModel> oDocuments;
     
     private long personId = -1;

    public void setPersonId(long id)
    {
        personId = id;
        // chargement des données
        this.refreshView();
    }
  
    private void refreshView()
    {
         if(personId < 0)
           return;
        // clear de la liste des documents
        DocumentModel.oDocuments.clear();
         
           // chargement de la liste des documents attachés à la personne
       String sql = "select * from t_documents where ref_id_identity = ?";
         try 
         {
             // statement
             PreparedStatement st = ConnectionSQL.getCon().prepareStatement(sql);
             st.setLong(1, personId);
             ResultSet result = st.executeQuery();
             while(result.next())
             {
                 DocumentModel model = new DocumentModel();
                 model.setId(result.getLong("id"));
                 model.setNom(result.getString("nom"));
                 model.setCommentaire(result.getString("commentaire"));
                 model.setFichier(result.getBlob("fichier"));
                 //oDocuments.add(model);
                 DocumentModel.oDocuments.add(model);
             }
             
         } catch (SQLException ex) {
             Logger.getLogger(DocumentController.class.getName()).log(Level.SEVERE, null, ex);
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
        controller.setId(personId);
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
           File temp = File.createTempFile(model.getNom() + new Date().getTime(), ".pdf");
           temp.deleteOnExit();
           FileOutputStream stream = new FileOutputStream(temp);
           stream.write(model.getFichier().getBytes(1,(int)model.getFichier().length()));
           stream.flush();
           stream.close();
          
         if(System.getProperty("os.name").contains("Windows"))
         {
            Runtime runtime = Runtime.getRuntime();
            String[] args = { "cmd.exe","/C", temp.getAbsolutePath() };
            final Process process = runtime.exec(args);
         }
         else
         {
             if(System.getProperty("os.name").contains("Linux"))
             {
                 Runtime runtime = Runtime.getRuntime();
                 String[] args = { "/bin/sh", "-c",temp.getAbsolutePath()  };
                 final Process process = runtime.exec(args);
             }

         }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
            // instance de oDocuments
            //oDocuments = FXCollections.observableArrayList();
            // bind
            listDocuments.setItems(DocumentModel.oDocuments);
       
    }    
    
}
