/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter.state;

import java.time.format.DateTimeFormatter;
import presenter.ManterFuncionarioPresenter;
import presenter.command.ComandoExcluir;
import presenter.command.ComandoFechar;
import presenter.command.Command;

/**
 *
 * @author marcos
 */
public class Visualizacao extends ManterFuncionarioState {
    
    Command comandoExcluir;
    Command comandoFechar;
    
    public Visualizacao(ManterFuncionarioPresenter funcionarioPresenter) {
        super(funcionarioPresenter);
        comandoExcluir = new ComandoExcluir(manterFuncionario);
        comandoFechar = new ComandoFechar(manterFuncionario);
        confTela();
    }

    private void confTela() {
        manterFuncionario.getView().getjLabelTituloDaJanela().setText("Visualizar Funcionário");
        manterFuncionario.getView().getjTextFieldNome().setText(manterFuncionario.getFuncionario().getNome());
        manterFuncionario.getView().getjTextFieldIdade().setText(String.valueOf(manterFuncionario.getFuncionario().getIdade()));
        manterFuncionario.getView().getjComboBoxCargo().setSelectedItem(manterFuncionario.getFuncionario().getCargo());
        manterFuncionario.getView().getjComboBoxRegiao().setSelectedItem(manterFuncionario.getFuncionario().getRegiao());
        manterFuncionario.getView().getjComboBoxBonus().setSelectedItem(manterFuncionario.getFuncionario().getBonusInicial());
        manterFuncionario.getView().getjTextFieldSalario().setText(String.format("%.2f",manterFuncionario.getFuncionario().getSalarioBase()));
        manterFuncionario.getView().getDatePicker().setText(manterFuncionario.getFuncionario().getDataDeAdmissao().format(DateTimeFormatter.ofPattern("dd/MM/uuuu")));
        manterFuncionario.getView().getjTextFieldFaltas().setText(String.valueOf(manterFuncionario.getFuncionario().getFaltas()));
        manterFuncionario.bloquearCampos();
        manterFuncionario.getView().getjButtonEditar().setVisible(true);
        manterFuncionario.getView().getjButtonExcluir().setVisible(true);
        manterFuncionario.getView().getjButtonVerBonus().setVisible(true);
        manterFuncionario.getView().getjButtonIncluir().setVisible(true);
        manterFuncionario.getView().getjButtonFechar().setVisible(true);
        manterFuncionario.getView().getjButtonSalvar().setVisible(false);
        manterFuncionario.getView().getjButtonCancelar().setVisible(false);
        manterFuncionario.getView().setVisible(true);
    }
    
    @Override
    public void incluir() throws Exception {
        manterFuncionario.setEstado(new Inclusao(manterFuncionario));
    }

    @Override
    public void editar() throws Exception {
        manterFuncionario.setEstado(new Edicao(manterFuncionario));
    }

    @Override
    public void excluir() throws Exception {
       comandoExcluir.executar();
    }

    @Override
    public void fechar() throws Exception {
        comandoFechar.executar();
    }
}
