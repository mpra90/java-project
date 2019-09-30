/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter.memento;

import java.util.ArrayList;
import presenter.memento.business.MementoFuncionario;

/**
 *
 * @author marcos
 */
public class Zelador {
    
    private static ArrayList<MementoFuncionario> estados = new ArrayList<>();
    
    public static void addMemento(MementoFuncionario memento) {
        estados.add(memento);
    }
    
    public static MementoFuncionario getUltimoEstadoSalvo() throws RuntimeException {
        if (estados.size() <= 0) {
            throw new RuntimeException("Não há estados salvos.");
        }
        MementoFuncionario estadoSalvo = estados.get(estados.size() - 1);
        estados.remove(estados.size() - 1);
        return estadoSalvo;
    }
    
    public static int getTamanho() {
        return estados.size();
    }
}
