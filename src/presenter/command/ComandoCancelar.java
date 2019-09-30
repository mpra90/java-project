/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter.command;

import presenter.BuscaFuncionarioPresenter;
import presenter.ManterFuncionarioPresenter;
import presenter.state.Edicao;
import presenter.state.Inclusao;

/**
 *
 * @author marcos
 */
public class ComandoCancelar extends Command {

    public ComandoCancelar(ManterFuncionarioPresenter manterFuncionario) {
        super(manterFuncionario);
    }

    @Override
    public void executar() throws Exception {
        if ((manterFuncionario.getEstado() instanceof Inclusao)) {
            if (manterFuncionario.veioPelaTelaDeBuscar()) {
                BuscaFuncionarioPresenter buscarFuncionario = BuscaFuncionarioPresenter.INSTANCE;
                manterFuncionario.getView().setVisible(false);
                buscarFuncionario.confTela();
            } else {
                manterFuncionario.getView().setVisible(false);
            }
        } else if (manterFuncionario.getEstado() instanceof Edicao) {
            manterFuncionario.getView().setVisible(false);
        }
        manterFuncionario.matarInstancia();
    }
}
