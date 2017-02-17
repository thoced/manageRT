/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionPackage;

import Models.TodoModel;
import java.time.LocalDate;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;

/**
 *
 * @author Thonon
 */
public class DateRappelTableCell extends TableCell<TodoModel,LocalDate>
{

    @Override
    protected void updateItem(LocalDate item, boolean empty) 
    {
        super.updateItem(item, empty); 
        
        if(!empty)
        {
            this.setText(null);
            DatePicker picker = new DatePicker(item);
            this.setGraphic(picker);
        }
        
    }
    
}
