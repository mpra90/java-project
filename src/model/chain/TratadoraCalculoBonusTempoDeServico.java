/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.chain;

import java.time.LocalDate;
import java.time.Period;
import model.Bonus;
import java.time.format.DateTimeFormatter;
import presenter.memento.business.Funcionario;

/**
 *
 * @author marcos
 */
public class TratadoraCalculoBonusTempoDeServico extends TratadoraCalculoBonus {

    public TratadoraCalculoBonusTempoDeServico(String nomeDoBonus) {
        super(nomeDoBonus);
    }

    @Override
    boolean aceita(Funcionario f) {
        Period periodo = Period.between(f.getDataDeAdmissao(), LocalDate.now());
        if (periodo.getYears() >= 5) {
            salarioComBonus = f.getSalarioBase() * 0.05;
            return true;
        }
        return false;
    }

    @Override
    void doHandle(Funcionario f) {
        String data = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/uuuu"));
        Bonus b = dao.inserirBonus(nomeDoBonus, data, salarioComBonus, f.getId());
        f.addBonus(b);
    }
}
