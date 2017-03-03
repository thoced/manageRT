/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managert;

import Models.PersonModel;
import javafx.scene.control.TableRow;

/**
 *
 * @author Thonon
 */
public class EvenementTableRow extends TableRow<PersonModel>
{

    @Override
    protected void updateItem(PersonModel item, boolean empty) 
    {
        super.updateItem(item, empty); 
        
        this.setStyle(null);
        this.setText(null);
        
        if(item != null)
        {
            if(item.isEvenementRappel())
            {
                this.setStyle("-fx-background-color:lightgreen");
            }
        }
    }
    
}
