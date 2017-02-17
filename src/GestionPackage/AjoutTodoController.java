/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionPackage;

import Models.ConnectionSQL;
import Models.TodoModel;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Thonon
 */
public class AjoutTodoController implements Initializable {

    private long idPerson;
    @FXML
    private TextField titre;
    @FXML
    private TextArea commentaire;
    @FXML
    private DatePicker datePicker;
    @FXML
    private CheckBox rappel;
    /**
     * Initializes the controller class.
     */
    private ObservableList<TodoModel> oTodo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // initialisation de la date de rappel à la date d'aujourd'hui
       datePicker.valueProperty().set(LocalDate.now());
    }   
     @FXML
    public void handleUndo(ActionEvent event) throws IOException, SQLException
    {
         titre.getScene().getWindow().hide();
    }
      
    @FXML
    public void handleValider(ActionEvent event) throws IOException, SQLException
    {
        // creatin du model
        TodoModel model = new TodoModel();
        model.setTitre(titre.getText());
        model.setText(commentaire.getText());
        model.dateRappelProperty().bindBidirectional(datePicker.valueProperty());
        model.setDateCreation(LocalDate.now());
        model.rappelProperty().bind(rappel.selectedProperty());

        // ajotu du model dans la liste
        oTodo.add(model);
        titre.getScene().getWindow().hide();
    }

    public long getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(long idPerson) {
        this.idPerson = idPerson;
    }

    public void setoTodo(ObservableList<TodoModel> oTodo) {
        this.oTodo = oTodo;
    }
    
}
