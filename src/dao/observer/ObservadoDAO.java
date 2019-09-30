/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.observer;

import java.util.ArrayList;
import observer.Observador;

/**
 *
 * @author marcos
 */
public abstract class ObservadoDAO {
    
    protected ArrayList<Observador> observadores;

    public ObservadoDAO() {
        observadores = new ArrayList<>();
    }
    
    public void addObservador(Observador observador) {
        observadores.add(observador);
    }
    
    public void notificar() {
        for (Observador o : observadores) {
            o.atualizar();
        }
    }
}
