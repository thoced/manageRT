/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managert;

import Models.PersonModel;
import javafx.scene.control.TableCell;

/**
 *
 * @author Thonon
 */
public class EvenementRappelTableCell extends TableCell<PersonModel,Boolean>
{

    @Override
    protected void updateItem(Boolean item, boolean empty) 
    {
        super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
        
        if(item != null)
        {
            this.setText("");
            if(item.booleanValue())
            {
                this.setText("RAPPEL !!!");
                this.setStyle("-fx-background-color:orange");
            }
            else
            {
               // this.setStyle("-fx-background-color:white");
            }
        }
    }
    
}
