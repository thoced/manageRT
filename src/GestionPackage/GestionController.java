/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionPackage;

import ControllerInterface.Controller;
import Models.ConnectionSQL;
import Models.PersonModel;
import Models.TodoModel;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * FXML Controller class
 *
 * @author Thonon
 */
public class GestionController extends Controller implements  Initializable {

    private long idPerson;
    @FXML
    private TableView tabTodo;
    @FXML
    private TableColumn<TodoModel,String> columnTodo;
    @FXML
    private TableColumn<TodoModel,LocalDate> columnDateCreation;
    @FXML
    private TableColumn<TodoModel,LocalDate> columnDateRappel;
    @FXML
    private TableColumn<TodoModel,Boolean> columnRappel;
    @FXML
    private TextArea commentaire;
    @FXML
    private Label dateCreation;
    @FXML
    private Label dateRappel;
    
    private TodoModel modelCurrent;
    
    private ObservableList<TodoModel> oTodo;
    
    private CommentaireChangeListener listener;
    /**
     * Initializes the controller class.
     */
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
           
          
            // bind des colonnes
            columnTodo.setCellValueFactory(cellData->cellData.getValue().titreProperty());
            columnDateCreation.setCellValueFactory(cellData->cellData.getValue().dateCreationProperty());
            columnDateRappel.setCellValueFactory(cellData->cellData.getValue().dateRappelProperty());
            columnRappel.setCellValueFactory(cellData->cellData.getValue().rappelProperty());
            // cellfactory
            columnRappel.setCellFactory(a->new RappelTableCell());
            columnDateRappel.setCellFactory(a->new DateRappelTableCell());
            
            // ajout du callback tablerow sur la table todo pour changer la couleur en fonction d'un rappel
            tabTodo.setRowFactory(a->new RappelTableRow());
            
            
            // listener d'enregistrement automatique sur la perte du focus
            listener = new CommentaireChangeListener();
            commentaire.focusedProperty().addListener(listener);
            // listener sur le changement de l'état checkbox 
            

    }
     
    private void refreshData()
    {
        
        
    }
    
    
    public void handleSupprimer(ActionEvent event) throws IOException, SQLException
    {
        TodoModel model = (TodoModel) tabTodo.getSelectionModel().getSelectedItem();
        if(model != null)
        {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Suppression d'un ToDo");
                alert.setContentText("Etes-vous sûr de vouloir supprimer le Todo : '" + model.getTitre() + "' ?");
                ButtonType bOui = new ButtonType("Oui");
                ButtonType bNon = new ButtonType("Non");
                alert.getButtonTypes().setAll(bNon,bOui);
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == bNon)
                    return;
               /* String sql = "delete from t_todo where id = ?";
                PreparedStatement st = ConnectionSQL.getCon().prepareStatement(sql);
                st.setLong(1, modelCurrent.getId());
                st.execute();*/
               
                oTodo.remove(model);
               
                // rechargement des données
                //this.refreshData();

        }
    }
    
    @FXML
    public void handleAjoutTodo(ActionEvent event) throws IOException
    {
        // ouverture de la vue
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/GestionPackage/AjoutTodoView.fxml"));
        AnchorPane anchor = loader.load();
        AjoutTodoController controller = loader.getController();
        controller.setModel(this.model); 
        Scene scene = new Scene(anchor);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    public void handleSelectTodoModel()
    {
        TodoModel model = (TodoModel) tabTodo.getSelectionModel().getSelectedItem();
        if(model != null)
        {
            // mise à jour du model dans le listener, pour l'enregistrement lors de la perte du focus sur l'objet commentaire
           listener.setModel(model);
          
            //commentaire.setText(model.getText());
            //commentaire.textProperty().bind(model.textProperty());
            if(modelCurrent != null)
                commentaire.textProperty().unbindBidirectional(modelCurrent.textProperty());
            
            commentaire.textProperty().bindBidirectional(model.textProperty());
            dateRappel.setText(model.getDateRappel().toString());
            dateCreation.setText(model.getDateCreation().toString());
            modelCurrent = model; 
            
        }
    }
    
    @FXML
    public void handleValider()
    {
        
        commentaire.getScene().getWindow().hide();
    }
    
    public long getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(long idPerson) {
        this.idPerson = idPerson;
        this.refreshData();
    }

    @Override
    public PersonModel getModel() 
    {
        return this.model;
    }

    @Override
    public void setModel(PersonModel model) {
       this.model = model;
       
       this.tabTodo.setItems(this.model.getoTodos());
    }

   
   
}
