/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author marcos
 */
public class BonusException extends Exception {
    
    public BonusException() {
        super("O bônus deve ser escolhido.", new Throwable("Bonus inválido."));
    }
}
