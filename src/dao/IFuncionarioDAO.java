/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.Bonus;
import java.util.ArrayList;
import observer.Observado;
import presenter.memento.business.Funcionario;

/**
 *
 * @author marcos
 */
public interface IFuncionarioDAO extends Observado {
    
    public abstract Funcionario inserirFuncionario(String nome, int idade, String cargo, String bonusInicial, int faltas, String dataDeAdmissao, double salarioBase, String regiao);
    public abstract Bonus inserirBonus(String nome, String data, double valor, int idFuncionario);
    public abstract void deletar(int idFuncionario);
    public abstract void alterar(int idFuncionario, String nome, int idade, String cargo, String bonusInicial, int faltas, String dataDeAdmissao, double salarioBase, String regiao);
    public abstract ArrayList<Funcionario> retornarTodosRegistros();
    public abstract void atualizarRegistroDoSalarioCalculado(ArrayList<Funcionario> funcionarios);
    public abstract int retornarTotalDeRegistro();
}
