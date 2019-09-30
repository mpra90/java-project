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
public class RegiaoException extends Exception {
    
    public RegiaoException() {
        super("A região deve ser escolhida.", new Throwable("Região inválida."));
    }
}
