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
public class FaltasException extends Exception {

    public FaltasException() {
        super("As faltas devem ser um número inteiro positivo.", new Throwable("Faltas inválida."));
    }
}
