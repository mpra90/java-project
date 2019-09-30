/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.chain;

import model.Bonus;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import presenter.memento.business.Funcionario;

/**
 *
 * @author marcos
 */
public class TratadoraCalculoBonusNormal extends TratadoraCalculoBonus {

    public TratadoraCalculoBonusNormal(String nomeDoBonus) {
        super(nomeDoBonus);
    }
    
    @Override
    boolean aceita(Funcionario f) {
        return this.nomeDoBonus.equals(f.getBonusInicial());
    }
    
    @Override
    void doHandle(Funcionario f) {
        salarioComBonus = f.getSalarioBase() * 0.05;
        String data = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/uuuu"));
        Bonus b = dao.inserirBonus(nomeDoBonus, data, salarioComBonus, f.getId());
        f.addBonus(b);
    }
}
