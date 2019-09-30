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
public class CargoException extends Exception {

    public CargoException() {
        super("O cargo deve ser escolhido.", new Throwable("Cargo inválido."));
    }
}