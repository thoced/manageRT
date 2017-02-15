/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managert;

import Models.PersonModel;
import javafx.scene.control.TableCell;
import javafx.scene.paint.Color;

/**
 *
 * @author Thonon
 */
public class ProprieteTableCell extends TableCell<PersonModel,String>
{

    @Override
    protected void updateItem(String item, boolean empty)
    {
        
        super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
        
      
        
        if(item != null)
        {
            String style;
            switch(item)
            {
                // propriete
                case "Surveillance normale": style = "-fx-background-color:lightblue";
                break;
                case "Surveillance régulière": style = "-fx-background-color:orange";
                break;
                case "Surveillance accrue": style = "-fx-background-color:Tomato";
                break;
                            
                default : style = "-fx-background-color:lightblue";
                break;
 
            }
            this.setText(item);
            this.setStyle(style);
        }
        
        System.out.println(item);
        
    }

   
  
    
}
