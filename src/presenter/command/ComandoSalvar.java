/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter.command;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import presenter.memento.business.Funcionario;
import presenter.ManterFuncionarioPresenter;
import presenter.state.Edicao;
import presenter.state.Inclusao;

/**
 *
 * @author marcos
 */
public class ComandoSalvar extends Command {

    public ComandoSalvar(ManterFuncionarioPresenter manterFuncionario) {
        super(manterFuncionario);
    }

    @Override
    public void executar() throws Exception {
        if (manterFuncionario.validarFuncionario()) {
            String nome = manterFuncionario.getView().getjTextFieldNome().getText();
            int idade = Integer.parseInt(manterFuncionario.getView().getjTextFieldIdade().getText());
            String cargo = (String) manterFuncionario.getView().getjComboBoxCargo().getSelectedItem();
            String bonusInicial = (String) manterFuncionario.getView().getjComboBoxBonus().getSelectedItem();
            double salario = Double.parseDouble(manterFuncionario.getView().getjTextFieldSalario().getText().replace(",", "."));
            LocalDate data = manterFuncionario.getView().getDatePicker().getDate();
            int faltas = Integer.parseInt(manterFuncionario.getView().getjTextFieldFaltas().getText());
            String regiao = (String) manterFuncionario.getView().getjComboBoxRegiao().getSelectedItem();
            
            if (manterFuncionario.getEstado() instanceof Inclusao) {
                Funcionario f = manterFuncionario.inserirFuncionarioNoBanco(nome, idade, cargo, bonusInicial, faltas, data.format(DateTimeFormatter.ofPattern("dd/MM/uuuu")), salario, regiao);

                manterFuncionario.setFuncionario(f);

                JOptionPane.showMessageDialog(null, "Funcionario " + manterFuncionario.getFuncionario().getNome() 
                    + " salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                
                manterFuncionario.gerarLog("Inclusão");
                
            } else if (manterFuncionario.getEstado() instanceof Edicao) {
                String dataString = data.format(DateTimeFormatter.ofPattern("dd/MM/uuuu"));
                int idFuncionario = manterFuncionario.getFuncionario().getId();

                manterFuncionario.alterarFuncionarioNoBanco(idFuncionario, nome, idade, cargo, bonusInicial, faltas, dataString, salario, regiao);

                manterFuncionario.getFuncionario().setNome(nome);
                manterFuncionario.getFuncionario().setIdade(idade);
                manterFuncionario.getFuncionario().setCargo(cargo);
                manterFuncionario.getFuncionario().setFaltas(faltas);
                manterFuncionario.getFuncionario().setDataDeAdmissao(data);
                manterFuncionario.getFuncionario().setSalarioBase(salario);
                manterFuncionario.getFuncionario().setRegiao(regiao);
                manterFuncionario.getFuncionario().mudarDeBonus(bonusInicial);

                JOptionPane.showMessageDialog(null, "Funcionario " + manterFuncionario.getFuncionario().getNome() 
                    + " salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                
                manterFuncionario.gerarLog("Edição");
            }
        }
    }

    @Override
    public void desfazer() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
