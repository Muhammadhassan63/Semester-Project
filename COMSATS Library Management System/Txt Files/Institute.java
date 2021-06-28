/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objectmodelpackage;

/**
 *
 * @author abc
 */

//Abstract Class used
//Below use of abstract class to achieve abstaraction however the purpose of both interface and abstract classes are same 
public abstract class Institute {
    private String instituteName;

    //constructor used for initializing
    
    public Institute() {
    }

    Institute(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getInstituteName() {
        return instituteName;
    }
}