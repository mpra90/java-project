/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter;

import dao.IFuncionarioDAO;
import dao.adapter.GerarLog;
import dao.adapter.ILogAdapter;
import factoryMethodDynamic.FabricaFuncionario;
import factoryMethodDynamic.apoio.LeituraDoProperties;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import presenter.memento.business.Funcionario;
import observer.Observador;
import view.BuscaFuncionarioView;

/**
 *
 * @author marcos
 */
public enum BuscaFuncionarioPresenter implements Observador {
    INSTANCE;
    
    private BuscaFuncionarioView view;
    private ArrayList<Funcionario> funcionarios;
    private ILogAdapter log;
    protected IFuncionarioDAO dao;
    
    private BuscaFuncionarioPresenter() {
        view = new BuscaFuncionarioView();
        TelaPrincipalPresenter.INSTANCE.addView(view);
        funcionarios = new ArrayList<>();
        log = GerarLog.INSTANCE;
        try {
            dao = FabricaFuncionario.criar(LeituraDoProperties.lerPersistenciaFuncionario());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        dao.addObservador(this);
        initListeners();
    }

    public void confTela() {
        view.getjTextFieldNome().setText("");
        popularTabela();
        view.setVisible(true);
    }

    private void initListeners() {
        view.getjTextFieldNome().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                confCampoNome(e);
            }
        });
        
        view.getjButtonBuscar().addActionListener((e) -> {
            confBotaoBuscar();
        });
        
        view.getjButtonNovo().addActionListener((e) -> {
            confBotaoNovo();
        });
        
        view.getjButtonVisualizar().addActionListener((e) -> {
            confBotaoVisualizar();
        });
        
        view.getjButtonVerBonus().addActionListener((e) -> {
            confBotaoVerBonus();
            
        });
        
        view.getjButtonFechar().addActionListener((e) -> {
            confBotaoFechar();
        });
    }
    
    public BuscaFuncionarioView getView() {
        return view;
    }
    
    private void confCampoNome(KeyEvent e) {
        if (e.getKeyCode() == e.VK_BACK_SPACE) {
            if (view.getjTextFieldNome().getText().length() == 1) {
                    popularTabela();
            }
        } else if (e.getKeyCode() == e.VK_ENTER) {
            confBotaoBuscar();
        }
    }
    
    private void confBotaoBuscar() {
        String nome = view.getjTextFieldNome().getText();
        boolean validacaoDoNome = nome.matches("([A-Za-z][A-Za-z]*)+([\\s][A-Za-z][A-Za-z]*)*");
        if (validacaoDoNome) {
            ArrayList<Funcionario> novosFuncionarios = new ArrayList<>();
            boolean achou = false;
            for (Funcionario f : funcionarios) {
                if (f.getNome().equalsIgnoreCase(nome) || f.getNome().startsWith(nome) 
                        || f.getNome().toUpperCase().startsWith(nome) || f.getNome().toLowerCase().startsWith(nome)) {
                    achou = true;
                    novosFuncionarios.add(f);
                }
            }
            if (achou) {
                popularTabelaFuncionariosFiltrados(novosFuncionarios);
            } else {
                JOptionPane.showMessageDialog(null, "Não há registro de funcionarios no sistema!"
                        , "Não há registros", JOptionPane.INFORMATION_MESSAGE);
                view.getjTextFieldNome().setText("");
            }
        }
    }
    
    private void confBotaoNovo() {
        ManterFuncionarioPresenter manterFuncionario = ManterFuncionarioPresenter.getInstance(null);
        manterFuncionario.setVeioPelaTelaDeBuscar(true);
        view.setVisible(false);
    }
    
    private void confBotaoVisualizar() {
        int index = view.getjTableFuncionario().getSelectedRow();
        if (index >= 0) {
            ManterFuncionarioPresenter manterFuncionario = ManterFuncionarioPresenter.getInstance();
            manterFuncionario.matarInstancia();
            manterFuncionario = ManterFuncionarioPresenter.getInstance(funcionarios.get(index));
            manterFuncionario.setVeioPelaTelaDeBuscar(true);
            view.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(null, "Seleciona um campo da tabela para visualizar seu conteúdo."
                    , "Campo inválido", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void confBotaoVerBonus() {
        int index = view.getjTableFuncionario().getSelectedRow();
        if (index >= 0) {
            VisualizacaoBonusPresenter visualizacaoBonus = VisualizacaoBonusPresenter.INSTANCE;
            visualizacaoBonus.setFuncionario(funcionarios.get(index));
            visualizacaoBonus.confTela();
            log.gerarLog(LocalDateTime.now(), funcionarios.get(index).getNome(), "Bônus Consultado");
        } else {
            JOptionPane.showMessageDialog(null, "Seleciona um campo da tabela para visualizar seu bônus."
                    , "Campo inválido", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void confBotaoFechar() {
        ManterFuncionarioPresenter manterFuncionario = ManterFuncionarioPresenter.getInstance();
        manterFuncionario.matarInstancia();
        manterFuncionario.setVeioPelaTelaDeBuscar(false);
        view.setVisible(false);
    }
    
    private void popularTabela() {
        funcionarios.clear();
        funcionarios.addAll(dao.retornarTodosRegistros());
        DefaultTableModel tabela = (DefaultTableModel) view.getjTableFuncionario().getModel();
        DecimalFormat df = new DecimalFormat("#.00");
        if (tabela.getRowCount() != 0) {
            int i = 0;
            while (i < tabela.getRowCount()) {
                tabela.removeRow(i);
            }
        }
        for (Funcionario f : funcionarios) {
           tabela.addRow(new Object[]{f.getNome(), f.getIdade(), f.getCargo(), Double.parseDouble(df.format(f.getSalarioBase()).replace(",", "."))});
        }
    }
    
    private void popularTabelaFuncionariosFiltrados(ArrayList<Funcionario> funcionarios) {
        DefaultTableModel tabela = (DefaultTableModel) view.getjTableFuncionario().getModel();
        DecimalFormat df = new DecimalFormat("#.00");
        if (tabela.getRowCount() != 0) {
            int i = 0;
            while (i < tabela.getRowCount()) {
                tabela.removeRow(i);
            }
        }
        for (Funcionario f : funcionarios) {
           tabela.addRow(new Object[]{f.getNome(), f.getIdade(), f.getCargo(), Double.parseDouble(df.format(f.getSalarioBase()).replace(",", "."))});
        }
    }

    @Override
    public void atualizar() {
        popularTabela();
    }
}
