/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Thonon
 */
public class MemoModel extends Model
{

    private final StringProperty text = new SimpleStringProperty();
    
    private final ObjectProperty<LocalDateTime> dateTime = new SimpleObjectProperty<>();

    public LocalDateTime getDateTime() {
        return dateTime.get();
    }

    public void setDateTime(LocalDateTime value) {
        dateTime.set(value);
    }

    public ObjectProperty dateTimeProperty() {
        return dateTime;
    }
    
    
    

    public MemoModel() 
    {
        
    }
    public MemoModel(long id) 
    {
        this.id = id;
        text.addListener(new MemoModelListener(id));
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
