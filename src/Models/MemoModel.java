/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Thonon
 */
public class MemoModel extends Model
{

    private final StringProperty text = new SimpleStringProperty();

    public MemoModel() 
    {
        text.addListener(new MemoModelListener());
    }

    
    
    public String getText() {
        return text.get();
    }

    public void setText(String value) {
        text.set(value);
    }

    public StringProperty textProperty() {
        return text;
    }

    @Override
    public String toString() {
        
        if(this.getText().length() >= 64)
            return this.getText().substring(0, 64).concat("..."); //To change body of generated methods, choose Tools | Templates.
        else
            return this.getText();
    }
    
    
}
