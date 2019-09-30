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
public class IdadeException extends Exception {

    public IdadeException() {
        super("A idade deve ser um número inteiro positivo.", new Throwable("Idade inválida."));
    }
}
