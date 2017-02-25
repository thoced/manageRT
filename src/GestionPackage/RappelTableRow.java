/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionPackage;

import Models.TodoModel;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import javafx.scene.control.TableRow;

/**
 *
 * @author Thonon
 */
public class RappelTableRow extends TableRow<TodoModel>
{

    @Override
    protected void updateItem(TodoModel item, boolean empty) 
    {
        super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
        this.setStyle(null);
        if(item != null && !empty)
        {
            if(item.isRappel())
            {
                if(item.getDateRappel().isBefore((ChronoLocalDate)LocalDate.now()))
                {
                 this.setStyle("-fx-background-color:lightgreen");
                 
                }
                else
                  this.setStyle(null);
                
            }
        }
    }

 
    
}
