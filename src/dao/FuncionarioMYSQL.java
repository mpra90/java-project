/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import model.Bonus;
import dao.observer.ObservadoDAO;
import presenter.memento.business.Funcionario;

/**
 *
 * @author marcos
 */
public class FuncionarioMYSQL extends ObservadoDAO implements IFuncionarioDAO {

    public FuncionarioMYSQL() {
        super();
    }

    @Override
    public Funcionario inserirFuncionario(String nome, int idade, String cargo, String bonusInicial, int faltas, String dataDeAdmissao, double salarioBase, String regiao) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates. 
    }

    @Override
    public Bonus inserirBonus(String nome, String data, double valor, int idFuncionario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deletar(int idFuncionario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void alterar(int idFuncionario, String nome, int idade, String cargo, String bonusInicial, int faltas, String dataDeAdmissao, double salarioBase, String regiao) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Funcionario> retornarTodosRegistros() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void atualizarRegistroDoSalarioCalculado(ArrayList<Funcionario> funcionarios) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int retornarTotalDeRegistro() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
