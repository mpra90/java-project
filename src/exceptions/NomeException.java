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
public class NomeException extends Exception {

    public NomeException() {
        super("O nome deve ser composto apenas por letras.", new Throwable("Nome inválido."));
    }
}
