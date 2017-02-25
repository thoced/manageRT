/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Thonon
 */
public class PrioriteModel implements IDataModel
{
    
    

    private final LongProperty id = new SimpleLongProperty();
    private final StringProperty type = new SimpleStringProperty();

    public PrioriteModel() 
    {
        
    }

    public PrioriteModel(String type) 
    {
        this.setType(type);
    }

    
    
    public String getType() {
        return type.get();
    }

    public void setType(String value) {
        type.set(value);
    }

    public StringProperty typeProperty() {
        return type;
    }
    

    public long getId() {
        return id.get();
    }

    public void setId(long value) {
        id.set(value);
    }

    public LongProperty idProperty() {
        return id;
    }

    @Override
    public String toString() {
        return type.getValue(); //To change body of generated methods, choose Tools | Templates.
    }
    
    

    @Override
    public void loadData() 
    {
        
    }

    @Override
    public void writeData() 
    {
        
    }
    
}
