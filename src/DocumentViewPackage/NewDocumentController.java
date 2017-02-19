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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javax.sql.rowset.serial.SerialBlob;

/**
 *
 * @author Thonon
 */
public class NewDocumentController extends Controller implements Initializable 
{
     @FXML
     private TextField titre;
     @FXML
     private TextArea commentaire;
     
     private SerialBlob documentBlob;
     private String suffix = "   ";
     
     private long id = -1;
     
      private ObservableList<DocumentModel> oDocuments;
     
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
     
    }
    
    @FXML
    public void handleLierFichier() throws FileNotFoundException, IOException, SQLException 
    {
        // clic sur la photo
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF (Portable Document Format)", "*.pdf"));
        fc.setTitle("Choisissez un document de type PDF");
        File file = fc.showOpenDialog(titre.getScene().getWindow());
        if(file != null)
        {
           FileInputStream stream = new FileInputStream(file);
           byte[] bytes = new byte[stream.available()];
           stream.read(bytes);
           if(documentBlob == null)
            documentBlob = new SerialBlob(bytes);
           else
            documentBlob.setBytes(0, bytes);

          
        }
    }
    @FXML
    public void handleValider() throws SQLException
    {
       
        DocumentModel model = new DocumentModel();
        model.setNom(titre.getText());
        model.setCommentaire(commentaire.getText());
        model.setFichier(documentBlob);
        this.model.getoDocuments().add(model);

        // hide de la vue
        titre.getScene().getWindow().hide();
    }
    
    @FXML
    public void handleUndo()
    {
        titre.getScene().getWindow().hide();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ObservableList<DocumentModel> getoDocuments() {
        return oDocuments;
    }

    public void setoDocuments(ObservableList<DocumentModel> oDocuments) {
        this.oDocuments = oDocuments;
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
    }
    
    
}
