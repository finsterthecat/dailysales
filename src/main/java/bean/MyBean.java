/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import javax.ejb.Stateless;

/**
 *
 * @author tonybrouwer
 */
@Stateless
public class MyBean {

    public int addNumbers(int numberA, int numberB) {
        return numberA +numberB;
    }
}
