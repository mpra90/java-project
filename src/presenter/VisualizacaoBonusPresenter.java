/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter;

import model.Bonus;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import presenter.memento.business.Funcionario;
import view.VisualizacaoBonusView;

/**
 *
 * @author marcos
 */
public enum VisualizacaoBonusPresenter {
    INSTANCE;
    
    private VisualizacaoBonusView view;
    private ArrayList<Bonus> bonus;
    private Funcionario f;

    private VisualizacaoBonusPresenter() {
        view = new VisualizacaoBonusView();
        bonus = new ArrayList<>();
        TelaPrincipalPresenter.INSTANCE.addView(view);
        initListeners();
    }

    public void confTela() {
        view.getjLabelNomeDoFuncionario().setText(f.getNome());
        popularTabela();
        view.setVisible(true);
    }

    private void initListeners() {
        view.getjButtonFechar().addActionListener((e) -> {
            view.setVisible(false);
        });
    }

    private void popularTabela() {
        bonus.clear();
        bonus.addAll(f.getBonusRecebido());
        DefaultTableModel tabela = (DefaultTableModel) view.getjTableVisualizacaoBonus().getModel();
        if (tabela.getRowCount() == 0) {
            for (Bonus b : bonus) {
               tabela.addRow(new Object[]{b.getNome(), b.getData().format(DateTimeFormatter.ofPattern("dd/MM/uuuu")), b.getValor()});
            }
        } else {
            int i = 0;
            while (i < tabela.getRowCount()) {
                tabela.removeRow(i);
            }
            for (Bonus b : bonus) {
               tabela.addRow(new Object[]{b.getNome(), b.getData().format(DateTimeFormatter.ofPattern("dd/MM/uuuu")), b.getValor()});
            }
        }
    }

    public void setFuncionario(Funcionario f) {
        this.f = f;
    }
}
