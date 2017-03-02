/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;

/**
 *
 * @author Thonon
 */
public class LinkModel extends DataModel
{

    private final LongProperty id_link = new SimpleLongProperty();

    public long getId_link() {
        return id_link.get();
    }

    public void setId_link(long value) {
        id_link.set(value);
    }

    public LongProperty id_linkProperty() {
        return id_link;
    }
    
    
    @Override
    public void writeData() {
        super.writeData(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void loadData() {
        super.loadData(); //To change body of generated methods, choose Tools | Templates.
    }
    
}
