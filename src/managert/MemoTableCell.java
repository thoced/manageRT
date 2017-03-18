/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managert;

import Models.MemoModel;
import Models.PersonModel;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.TableCell;

/**
 *
 * @author Thonon
 */
public class MemoTableCell extends TableCell<MemoModel,LocalDateTime>
{

    @Override
    protected void updateItem(LocalDateTime item, boolean empty) 
    {
        super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
        this.setText(null);
        if(item != null)
        {
          this.setText(item.format(DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm")));
        }
    }
    
}
