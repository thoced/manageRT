/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerInterface;

import Models.PersonModel;

/**
 *
 * @author Thonon
 */
public abstract class Controller 
{
    protected PersonModel model;
    public abstract PersonModel getModel() ;
    public abstract void setModel(PersonModel model);
  
}
