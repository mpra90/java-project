/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter.memento.business;

import java.time.LocalDate;

/**
 *
 * @author marcos
 */
public class MementoFuncionario {
  
    private double salarioComBonus;
    private LocalDate dataDoCalculoSalario;

    MementoFuncionario(double salarioComBonus, LocalDate dataDoCalculoSalario) {
        this.salarioComBonus = salarioComBonus;
        this.dataDoCalculoSalario = dataDoCalculoSalario;
    }

    public MementoFuncionario(MementoFuncionario copia) {
        this.salarioComBonus = copia.salarioComBonus;
        this.dataDoCalculoSalario = copia.dataDoCalculoSalario;
    }

    
    public double getSalarioComBonus() {
        return salarioComBonus;
    }

    public LocalDate getDataDoCalculoSalario() {
        return dataDoCalculoSalario;
    }

/*    
    ArrayList<Funcionario> funcionarios;

    MementoFuncionario(ArrayList<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }
    
    ArrayList<Funcionario> getFuncionarios() {
        return funcionarios;
    }
*/
}
