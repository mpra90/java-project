/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter.state;

import presenter.ManterFuncionarioPresenter;
import presenter.command.ComandoCancelar;
import presenter.command.ComandoSalvar;
import presenter.command.Command;

/**
 *
 * @author marcos
 */
public class Inclusao extends ManterFuncionarioState {
    
    private Command comandoSalvar;
    private Command comandoCancelar;
    
    public Inclusao(ManterFuncionarioPresenter manterFuncionario) {
        super(manterFuncionario);
        comandoSalvar = new ComandoSalvar(manterFuncionario);
        comandoCancelar = new ComandoCancelar(manterFuncionario);
        confTela();
    }    

    private void confTela() {
        manterFuncionario.getView().getjLabelTituloDaJanela().setText("Incluir Funcionário");
        manterFuncionario.limparCampos();
        manterFuncionario.desbloquearCampos();
        manterFuncionario.getView().getjButtonSalvar().setVisible(true);
        manterFuncionario.getView().getjButtonCancelar().setVisible(true);
        manterFuncionario.getView().getjButtonEditar().setVisible(false);
        manterFuncionario.getView().getjButtonExcluir().setVisible(false);
        manterFuncionario.getView().getjButtonVerBonus().setVisible(false);
        manterFuncionario.getView().getjButtonFechar().setVisible(false);
        manterFuncionario.getView().getjButtonIncluir().setVisible(false);
        manterFuncionario.getView().setVisible(true);
    }
    
    @Override
    public void salvar() throws Exception {
        comandoSalvar.executar();    
        manterFuncionario.setEstado(new Visualizacao(manterFuncionario));
    }

    @Override
    public void cancelar() throws Exception {
       comandoCancelar.executar();
    }
}
