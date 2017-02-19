/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PersonViewPackage;

import DocumentViewPackage.DocumentController;
import GestionPackage.GestionController;
import Models.PersonModel;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import javax.swing.undo.UndoManager;




/**
 * FXML Controller class
 *
 * @author Thonon
 */
public class PersonController implements Initializable {

    @FXML
    private AnchorPane anchor;
    @FXML
    private ComboBox comboPriorite;
    @FXML
    private ComboBox comboCategorie;
    @FXML
    private TextField nom;
    @FXML
    private TextField numeroNational;
    @FXML
    private DatePicker dateNaissance;
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
    @FXML
    private Button bDocuments;
    @FXML
    private Button bLiens;
    @FXML
    private ImageView photo;
    
    private ObservableList<String> oListPriorite;
    private ObservableList<String> oListCategorie;
    
    private PersonModel model;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // modification de la couleur de fond du imageview
        dateNaissance.setValue(LocalDate.now());
        
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
    
    public void setModel(PersonModel model) throws CloneNotSupportedException, SQLException
    {
        if(model != null)
        {
         this.model = model;
         nom.textProperty().bindBidirectional(model.nomProperty());
         prenom.textProperty().bindBidirectional(model.prenomProperty());
         numeroNational.textProperty().bindBidirectional(model.numNationalProperty());
         dateNaissance.valueProperty().bindBidirectional(model.dateNaissanceProperty());
         adresse.textProperty().bindBidirectional(model.adresseProperty());
         numero.textProperty().bindBidirectional(model.numeroProperty());
         boite.textProperty().bindBidirectional(model.boiteProperty());
         ville.textProperty().bindBidirectional(model.villeProperty());
         codePostal.textProperty().bindBidirectional(model.codePostalProperty());
         comboPriorite.valueProperty().bindBidirectional(model.prioriteProperty());
         comboCategorie.valueProperty().bindBidirectional(model.categorieProperty()); 
         
         // chargement de la photo
         if(model.getPhoto() != null)
         {
             Image ima = new Image(model.getPhoto().getBinaryStream());
             photo.setImage(ima);
         }
           
        }
    }

    @FXML
    public void cancel(ActionEvent event)
    {
       if(anchor != null)
         anchor.getScene().getWindow().hide();
        
    }
    
     @FXML
    public void ok(ActionEvent event)
    {
            
       if(anchor != null)
           anchor.getScene().getWindow().hide();
    }

     @FXML
    public void handleGestion(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionPackage/GestionView.fxml"));
        AnchorPane pane = loader.load();
        GestionController controller = (GestionController)loader.getController();
        controller.setModel(this.model);
        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }
    
     @FXML
    public void handleDocuments(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/DocumentViewPackage/DocumentView.fxml"));
        AnchorPane pane = loader.load();
        DocumentController controller = (DocumentController)loader.getController();
        controller.setModel(this.model);
        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }
    
    @FXML
    public void clicPhoto() throws MalformedURLException, FileNotFoundException, IOException, SQLException  
    {
        // clic sur la photo
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Fichier bmp (Bitmap)", "*.bmp"),
                new FileChooser.ExtensionFilter("Fichier jpg (Jpeg)", "*.jpg"),
                new FileChooser.ExtensionFilter("Fichier png (Portable Network Graphics)","*.png"));
        fc.setTitle("Choisissez la photo");
        File file = fc.showOpenDialog(anchor.getScene().getWindow());
        if(file != null)
        {
          Image ima = new Image(file.toURL().toString());
          photo.setImage(ima);
          InputStream stream = new FileInputStream(file.getAbsolutePath());
         
          byte[] b = new byte[stream.available()];
          stream.read(b);
          Blob blob = new SerialBlob(b);
          model.setPhoto(blob);
         
          
        }
        
    }
    
}
