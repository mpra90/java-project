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
public class SalarioException extends Exception {

    public SalarioException() {
        super("O salário deve ser informado com um número maior que zero.", new Throwable("Salário inválido"));
    }
}
