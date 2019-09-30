/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.chain;

import java.util.ArrayList;
import presenter.memento.business.Funcionario;

/**
 *
 * @author marcos
 */
public enum ProcessadoraCalculoBonus {
    INSTANCE;
    
    private ArrayList<TratadoraCalculoBonus> tratadoras;

    private ProcessadoraCalculoBonus() {
        tratadoras = new ArrayList<>();
        tratadoras.add(new TratadoraCalculoBonusNormal("Normal"));
        tratadoras.add(new TratadoraCalculoBonusGeneroso("Generoso"));
        tratadoras.add(new TratadoraCalculoBonusAssiduidade("Assiduidade"));
        tratadoras.add(new TratadoraCalculoBonusPorIdade("Idade"));
        tratadoras.add(new TratadoraCalculoBonusTempoDeServico("Tempo de Servico"));
    }
    
    public void processar(Funcionario f) {
        for (TratadoraCalculoBonus t : tratadoras) {
            if (t.aceita(f)) {
                t.doHandle(f);
            }
        }
    }
}
