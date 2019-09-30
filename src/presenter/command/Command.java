/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter.command;

import presenter.ManterFuncionarioPresenter;

/**
 *
 * @author marcos
 */
public abstract class Command {
    
    protected ManterFuncionarioPresenter manterFuncionario;

    public Command(ManterFuncionarioPresenter manterFuncionario) {
        this.manterFuncionario = manterFuncionario;
    }

    public abstract void executar() throws Exception;
    
    void desfazer() throws Exception {
        throw new Exception("Não é permitido desfazer no comando " + getClass().getSimpleName());
    }
}
