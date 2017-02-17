/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionPackage;

import Models.ConnectionSQL;
import Models.TodoModel;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;


/**
 *
 * @author Thonon
 */
public class RappelTableCell extends TableCell<TodoModel,Boolean> implements ChangeListener
{ 
   // private CheckBox box = new CheckBox("");
    
    @Override
    protected void updateItem(Boolean item, boolean empty) 
    {
        super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
               
        if(item != null && !empty)
        {
            this.setText("");
            CheckBox box = new CheckBox();
            box.setSelected(item);
            box.selectedProperty().addListener(this);
            this.setGraphic(box);
        }
        else
        {
            this.setGraphic(null);
        }
           
       
    }
    
    

    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) 
    {
         TodoModel model = (TodoModel) this.getTableRow().getItem();
         if(model != null)
         {
             try {
                 model.setRappel((boolean)newValue);
                 String sql = "update t_todo set rappel = ? where id = ?";
                 PreparedStatement st = ConnectionSQL.getCon().prepareStatement(sql);
                 st.setBoolean(1, model.isRappel());
                 st.setLong(2, model.getId());
                 st.execute();
             } catch (SQLException ex) {
                 Logger.getLogger(RappelTableCell.class.getName()).log(Level.SEVERE, null, ex);
             }
         }
    
    }
    
    
    
}
