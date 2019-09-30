/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter;

import factoryMethodDynamic.apoio.LeituraDoProperties;
import java.io.IOException;
import javax.swing.JOptionPane;
import view.ConfiguracaoPersistenciaView;

/**
 *
 * @author marcos
 */
public enum ConfiguracaoPersistenciaPresenter {
    INSTANCE;

    private ConfiguracaoPersistenciaView view;
    private String funcionarioRadioButton;
    private String logRadioButton;

    private ConfiguracaoPersistenciaPresenter() {
        view = new ConfiguracaoPersistenciaView();
        TelaPrincipalPresenter.INSTANCE.addView(view);
        initListeners();
    }

    public void confTela() throws IOException {
/*        funcionarioRadioButton = LeituraDoProperties.lerPersistenciaFuncionario();
        if (funcionarioRadioButton.equals("dao.FuncionarioMYSQL")) {
            view.getjRadioButtonMYSQL().setSelected(true);
        } else {
            view.getjRadioButtonSQLITE().setSelected(true);
        }
*/
        view.getjRadioButtonSQLITE().setSelected(true);
        view.getjRadioButtonMYSQL().setEnabled(false);

        logRadioButton = LeituraDoProperties.lerPersistenciaLog();
        if (logRadioButton.equals("dao.LogTXT")) {
            view.getjRadioButtonTXT().setSelected(true);
        } else if (logRadioButton.equals("dao.LogJSON")) {
            view.getjRadioButtonJSON().setSelected(true);
        } else {
            view.getjRadioButtonXML().setSelected(true);
        }
        view.setVisible(true);
    }

    private void initListeners() {
        view.getjRadioButtonMYSQL().addActionListener((e) -> {
            view.getjRadioButtonSQLITE().setSelected(false);
            funcionarioRadioButton = "dao.Funcionario" + view.getjRadioButtonMYSQL().getText();
        });

        view.getjRadioButtonSQLITE().addActionListener((e) -> {
            view.getjRadioButtonMYSQL().setSelected(false);
            funcionarioRadioButton = "dao.Funcionario" + view.getjRadioButtonSQLITE().getText();
        });

        view.getjRadioButtonTXT().addActionListener((e) -> {
            view.getjRadioButtonJSON().setSelected(false);
            view.getjRadioButtonXML().setSelected(false);
            logRadioButton = "dao.Log" + view.getjRadioButtonTXT().getText();
        });

        view.getjRadioButtonJSON().addActionListener((e) -> {
            view.getjRadioButtonTXT().setSelected(false);
            view.getjRadioButtonXML().setSelected(false);
            logRadioButton = "dao.Log" + view.getjRadioButtonJSON().getText();
        });

        view.getjRadioButtonXML().addActionListener((e) -> {
            view.getjRadioButtonTXT().setSelected(false);
            view.getjRadioButtonJSON().setSelected(false);
            logRadioButton = "dao.Log" + view.getjRadioButtonXML().getText();
        });

        view.getjButtonSalvar().addActionListener((e) -> {
            confBotaoSalvar();
        });

        view.getjButtonFechar().addActionListener((e) -> {
            view.setVisible(false);
        });
    }

    private void confBotaoSalvar() {
        if (view.getjRadioButtonSQLITE().isSelected() || view.getjRadioButtonSQLITE().isSelected() || 
                view.getjRadioButtonJSON().isSelected() || view.getjRadioButtonTXT().isSelected() ||
                view.getjRadioButtonTXT().isSelected()) {
            
//                LeituraDoProperties.setPersistenciaFuncionario(funcionarioRadioButton);
                LeituraDoProperties.setPersistenciaLog(logRadioButton);
                JOptionPane.showMessageDialog(null, "Persistencia salva com sucesso! Feche o sistema para a alteração ser concluída.", "Sucesso.", JOptionPane.INFORMATION_MESSAGE);
                
                view.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(null, "Escolha uma opção de persistencia para funcionario e uma opção de persistencia para log.", "Atenção.", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
