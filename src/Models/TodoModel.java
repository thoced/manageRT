/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.time.LocalDate;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Thonon
 */
public class TodoModel extends Model
{
    private final StringProperty text = new SimpleStringProperty();
    private final StringProperty titre = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> dateRappel = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> dateCreation = new SimpleObjectProperty<>();
    private final BooleanProperty rappel = new SimpleBooleanProperty();
  

   
    
    public boolean isRappel() {
        return rappel.get();
    }

    public void setRappel(boolean value) {
        rappel.set(value);
    }

    public BooleanProperty rappelProperty() {
        return rappel;
    }
    

    public LocalDate getDateCreation() {
        return dateCreation.get();
    }

    public void setDateCreation(LocalDate value) {
        dateCreation.set(value);
    }

    public ObjectProperty dateCreationProperty() {
        return dateCreation;
    }
    

    public LocalDate getDateRappel()
    {
        return dateRappel.get();
    }

    public void setDateRappel(LocalDate value) {
        dateRappel.set(value);
    }

    public ObjectProperty dateRappelProperty() {
        return dateRappel;
    }
    
     

    public String getTitre() {
        return titre.get();
    }

    public void setTitre(String value) {
        titre.set(value);
    }

    public StringProperty titreProperty() {
        return titre;
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
    
    
    
}
