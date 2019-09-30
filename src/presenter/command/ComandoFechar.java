/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter.command;

import presenter.BuscaFuncionarioPresenter;
import presenter.ManterFuncionarioPresenter;

/**
 *
 * @author marcos
 */
public class ComandoFechar extends Command {

    public ComandoFechar(ManterFuncionarioPresenter manterFuncionario) {
        super(manterFuncionario);
    }

    @Override
    public void executar() throws Exception {
        if (manterFuncionario.veioPelaTelaDeBuscar()) {
            BuscaFuncionarioPresenter buscarFuncionario = BuscaFuncionarioPresenter.INSTANCE;
            manterFuncionario.getView().setVisible(false);
            buscarFuncionario.confTela();
        } else {
            manterFuncionario.getView().setVisible(false);
        }
        manterFuncionario.matarInstancia();
    }
}
