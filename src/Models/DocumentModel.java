/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.Blob;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Thonon
 */
public class DocumentModel extends Model
{

    private final StringProperty nom = new SimpleStringProperty();
    private final StringProperty commentaire = new SimpleStringProperty();
    private Blob fichier;

    
    public Blob getFichier() {
        return fichier;
    }

    public void setFichier(Blob fichier) {
        this.fichier = fichier;
    }

    
    
    public String getCommentaire() {
        return commentaire.get();
    }

    public void setCommentaire(String value) {
        commentaire.set(value);
    }

    public StringProperty commentaireProperty() {
        return commentaire;
    }
    

    public String getNom() {
        return nom.get();
    }

    public void setNom(String value) {
        nom.set(value);
    }

    public StringProperty nomProperty() {
        return nom;
    }

    @Override
    public String toString() 
    {
       return this.getNom();
    }
    
    
    
}
