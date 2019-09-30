/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter.command;

import javax.swing.JOptionPane;
import presenter.BuscaFuncionarioPresenter;
import presenter.ManterFuncionarioPresenter;

/**
 *
 * @author marcos
 */
public class ComandoExcluir extends Command {

    public ComandoExcluir(ManterFuncionarioPresenter manterFuncionario) {
        super(manterFuncionario);
    }

    @Override
    public void executar() throws Exception {
        int confirmar = JOptionPane.showConfirmDialog(null, "Deseja Realmente Excluir o funcionario " 
                + manterFuncionario.getFuncionario().getNome() + " ?", "Confirmação de Exclusão", JOptionPane.YES_NO_OPTION);
        
        if (confirmar == 0) {
            manterFuncionario.deletarFuncionarioNoBanco();
            
            JOptionPane.showMessageDialog(null, "Funcionario " + manterFuncionario.getFuncionario().getNome() 
                    + " excluido com sucesso!", "Exclusão Realizada", JOptionPane.INFORMATION_MESSAGE);
            
            manterFuncionario.gerarLog("Exclusão");
            
            if (manterFuncionario.veioPelaTelaDeBuscar()) {
                BuscaFuncionarioPresenter buscarFuncionario = BuscaFuncionarioPresenter.INSTANCE;
                manterFuncionario.getView().setVisible(false);
                buscarFuncionario.confTela();
            } else {
                manterFuncionario.getView().setVisible(false);
            }
        }
    }
}
