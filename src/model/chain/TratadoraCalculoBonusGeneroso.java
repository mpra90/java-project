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
public class TratadoraCalculoBonusGeneroso extends TratadoraCalculoBonus {

    public TratadoraCalculoBonusGeneroso(String nomeDoBonus) {
        super(nomeDoBonus);
    }

    @Override
    public boolean aceita(Funcionario f) {
        return this.nomeDoBonus.equals(f.getBonusInicial());
    }

    @Override
    public void doHandle(Funcionario f) {
        salarioComBonus = f.getSalarioBase() * 0.2;
        String data = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/uuuu"));
        Bonus b = dao.inserirBonus(nomeDoBonus, data, salarioComBonus, f.getId());
        f.addBonus(b);
    }
}