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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
       
        
        // enregistrement sql
        String sql = "insert into t_todo (ref_id_identity,titre,text,date_creation,date_rappel) values (?,?,?,?,?)";
        PreparedStatement st = ConnectionSQL.getCon().prepareStatement(sql);
        st.setLong(1, this.getIdPerson());
        st.setString(2, model.getTitre());
        st.setString(3, model.getText());
        st.setDate(4, java.sql.Date.valueOf(model.getDateCreation()));
        st.setDate(5,java.sql.Date.valueOf(model.getDateRappel()));
        st.execute();
        // ajotu du model dans la liste
        TodoModel.oTodo.add(model);
        titre.getScene().getWindow().hide();
    }

    public long getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(long idPerson) {
        this.idPerson = idPerson;
    }
    
}
