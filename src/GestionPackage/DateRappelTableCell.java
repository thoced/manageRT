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
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;

/**
 *
 * @author Thonon
 */
public class DateRappelTableCell extends TableCell<TodoModel,LocalDate> implements ChangeListener
{

    @Override
    protected void updateItem(LocalDate item, boolean empty) 
    {
        super.updateItem(item, empty); 
        
        if(item != null && !empty)
        {
            this.setText(null);
            DatePicker picker = new DatePicker(item);
            picker.valueProperty().addListener(this);
            this.setGraphic(picker);
        }
        else
            this.setGraphic(null);
        
    }

    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) 
    {
         TodoModel model = (TodoModel) this.getTableRow().getItem();
         if(model != null)
         {
             try {
                 model.setDateRappel((LocalDate)newValue);
                 String sql = "update t_todo set date_rappel = ? where id = ?";
                 PreparedStatement st = ConnectionSQL.getCon().prepareStatement(sql);
                 st.setDate(1, java.sql.Date.valueOf(model.getDateRappel()));
                 st.setLong(2, model.getId());
                 st.execute();
             } catch (SQLException ex) {
                 Logger.getLogger(RappelTableCell.class.getName()).log(Level.SEVERE, null, ex);
             }
         }
    }
    
}
