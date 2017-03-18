/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author Thonon
 */
public class MemoModelListener implements InvalidationListener
{

   

    @Override
    public void invalidated(Observable observable)
    {
        System.out.println("changement now");
    }
    
}
