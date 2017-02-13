/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managert;

import Models.PersonModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableView;

/**
 * FXML Controller class
 *
 * @author Thonon
 */
public class TablePersonController implements Initializable {

    @FXML
    private TableView table;
    @FXML
    private TableColumn<PersonModel,String> nomColumn;
     @FXML
    private TableColumn<PersonModel,String> prenomColumn;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
       // bind des colonne
        nomColumn.setCellValueFactory(cellData->cellData.getValue().nomProperty());
        prenomColumn.setCellValueFactory(cellData->cellData.getValue().prenomProperty());
    }  

    public TableView getTable() {
        return table;
    }
    
    
    
}
