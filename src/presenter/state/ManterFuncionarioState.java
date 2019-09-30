/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter.state;

import presenter.ManterFuncionarioPresenter;

/**
 *
 * @author marcos
 */
public abstract class ManterFuncionarioState {
    
    protected ManterFuncionarioPresenter manterFuncionario;

    public ManterFuncionarioState(ManterFuncionarioPresenter manterFuncionario) {
        this.manterFuncionario = manterFuncionario;
    }
    
    public void incluir() throws Exception {
        throw new Exception("Não é permitido incluir no estado " + getClass().getSimpleName());
    }
    
    public void salvar() throws Exception {
        throw new Exception("Não é permitido salvar no estado " + getClass().getSimpleName());
    }
    
    public void cancelar() throws Exception {
        throw new Exception("Não é permitido cancelar no estado " + getClass().getSimpleName());
    }
    
    public void editar() throws Exception {
        throw new Exception("Não é permitido editar no estado " + getClass().getSimpleName());
    }
    
    public void fechar() throws Exception {
        throw new Exception("Não é permitido fechar no estado " + getClass().getSimpleName());
    }
    
    public void excluir() throws Exception {
        throw new Exception("Não é permitido excluir no estado " + getClass().getSimpleName());
    }
}
