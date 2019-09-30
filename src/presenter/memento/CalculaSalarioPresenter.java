/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter.memento;

import dao.IFuncionarioDAO;
import dao.adapter.GerarLog;
import dao.adapter.ILogAdapter;
import factoryMethodDynamic.FabricaFuncionario;
import factoryMethodDynamic.apoio.LeituraDoProperties;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import presenter.memento.business.Funcionario;
import observer.Observador;
import presenter.TelaPrincipalPresenter;
import presenter.VisualizacaoBonusPresenter;
import view.CalculaSalarioView;

/**
 *
 * @author marcos
 */
public enum CalculaSalarioPresenter implements Observador {
    INSTANCE;

    private CalculaSalarioView view;
    private ArrayList<Funcionario> funcionarios;
    private ArrayList<Funcionario> vetorApoio;
    private ILogAdapter log;
    protected IFuncionarioDAO dao;

    private CalculaSalarioPresenter() {
        view = new CalculaSalarioView();
        funcionarios = new ArrayList<>();
        vetorApoio = new ArrayList<>();
        log = GerarLog.INSTANCE;
        try {
            dao = FabricaFuncionario.criar(LeituraDoProperties.lerPersistenciaFuncionario());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        dao.addObservador(this);
        TelaPrincipalPresenter.INSTANCE.addView(view);
        initListeners();
    }

    public void confTela() {
        view.getDatePicker().getComponentToggleCalendarButton().setText("");
        view.getDatePicker().getComponentToggleCalendarButton().setIcon(new ImageIcon(getClass().getResource("/icons/icons8-schedule-16.png")));
        view.getDatePicker().getSettings().setLocale(new Locale("pt", "BR"));
        view.getDatePicker().getSettings().setFormatForDatesCommonEra("dd/MM/uuuu");
        view.getjButtonRestaurar().setVisible(false);
        popularTabela();
        view.setVisible(true);
    }

    private void initListeners() {
        view.getjButtonBuscar().addActionListener((e) -> {
            confBotaoBuscar();
        });

        view.getjButtonCalcular().addActionListener((e) -> {
            confBotaoCalcular();
        });

        view.getjButtonVerBonus().addActionListener((e) -> {
            confBotaoVerBonus();

        });

        view.getjButtonRestaurar().addActionListener((e) -> {
            confBotaoRestaurar();
        });
        
        view.getDatePicker().getComponentDateTextField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == e.VK_BACK_SPACE) {
                    if (view.getDatePicker().getText().length() == 1) {
                        popularTabela();
                    }
                }
            }
            
        });

        view.getjButtonFechar().addActionListener((e) -> {
            view.setVisible(false);
            view.getDatePicker().setText("");
        });
    }

    private void confBotaoBuscar() {
        String dataDoCalculoSalario = view.getDatePicker().getText();
        ArrayList<Funcionario> arrayAux = new ArrayList<>();
        boolean achou = false;
        for (Funcionario f : funcionarios) {
            if ((f.getDataDoCalculoSalario() != null) && (f.getDataDoCalculoSalario().format(DateTimeFormatter.ofPattern("dd/MM/uuuu")).equals(dataDoCalculoSalario))) {
                arrayAux.add(f);
                achou = true;
            }
        }
        if (achou) {
            popularTabelaFuncionariosFiltrados(arrayAux);
        } else {
            JOptionPane.showMessageDialog(null, "Não há registro de realização do calculo de salário na data especificada!",
                     "Data inválida.", JOptionPane.INFORMATION_MESSAGE);
            view.getDatePicker().setText("");
        }
    }

    private void confBotaoCalcular() {
        boolean entrou = false;
        for (Funcionario f : funcionarios) {
            if (f.getSalarioComBonus() == 0.0) {
                entrou = true;
                break;
            }
        }
        if (entrou) {
            for (Funcionario f : funcionarios) {
                if (f.getSalarioComBonus() == 0.0) {
                    Zelador.addMemento(f.criarMemento());
                    f.calcularSalario();
                    f.setDataDoCalculoSalario(LocalDate.now());
                    vetorApoio.add(f);
                    log.gerarLog(LocalDateTime.now(), f.getNome(), "Salário Calculado");
                }
            }
            dao.atualizarRegistroDoSalarioCalculado(funcionarios);
            view.getjButtonRestaurar().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Os funcionários listados já tiveram seus salários calculados.", "Operação inválida.", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void confBotaoVerBonus() {
        int index = view.getjTable().getSelectedRow();
        if (index >= 0) {
            VisualizacaoBonusPresenter visualizacaoBonus = VisualizacaoBonusPresenter.INSTANCE;
            visualizacaoBonus.setFuncionario(funcionarios.get(index));
            visualizacaoBonus.confTela();
        } else {
            JOptionPane.showMessageDialog(null, "Seleciona um campo da tabela para visualizar seu bônus.",
                     "Campo inválido", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void confBotaoRestaurar() {
        int i = 1;
        for (Funcionario f : vetorApoio) {
            vetorApoio.get(vetorApoio.size() - i).restaurar(Zelador.getUltimoEstadoSalvo());
            log.gerarLog(LocalDateTime.now(), f.getNome(), "Salário Restaurado");
            i++;
        }
        view.getjButtonRestaurar().setVisible(false);
        dao.atualizarRegistroDoSalarioCalculado(vetorApoio);
        vetorApoio.clear();
    }

    private void popularTabela() {
        funcionarios.clear();
        funcionarios.addAll(dao.retornarTodosRegistros());
        DefaultTableModel tabela = (DefaultTableModel) view.getjTable().getModel();
        if (tabela.getRowCount() != 0) {
            int i = 0;
            while (i < tabela.getRowCount()) {
                tabela.removeRow(i);
            }
        }
        for (Funcionario f : funcionarios) {
            if (f.getDataDoCalculoSalario() != null) {
                if (f.getSalarioComBonus() != 0) {
                    tabela.addRow(new Object[]{f.getDataDoCalculoSalario().format(DateTimeFormatter.ofPattern("dd/MM/uuuu")), f.getNome(), f.getSalarioBase(), f.retornaValorTotalDosBonus(), f.getSalarioComBonus()});
                } else {
                    tabela.addRow(new Object[]{f.getDataDoCalculoSalario().format(DateTimeFormatter.ofPattern("dd/MM/uuuu")), f.getNome(), f.getSalarioBase(), 0.0, f.getSalarioComBonus()});
                }
            } else {
                tabela.addRow(new Object[]{"", f.getNome(), f.getSalarioBase(), 0.0, f.getSalarioComBonus()});
            }
        }
    }

    @Override
    public void atualizar() {
        popularTabela();
    }

    private void popularTabelaFuncionariosFiltrados(ArrayList<Funcionario> arrayAux) {
        funcionarios.clear();
        funcionarios.addAll(arrayAux);
        DefaultTableModel tabela = (DefaultTableModel) view.getjTable().getModel();
        if (tabela.getRowCount() != 0) {
            int i = 0;
            while (i < tabela.getRowCount()) {
                tabela.removeRow(i);
            }
        }
        for (Funcionario f : arrayAux) {
            if (f.getSalarioComBonus() != 0.0) {
                tabela.addRow(new Object[]{f.getDataDoCalculoSalario().format(DateTimeFormatter.ofPattern("dd/MM/uuuu")), f.getNome(), f.getSalarioBase(), f.retornaValorTotalDosBonus(), f.getSalarioComBonus()});
            } else {
                tabela.addRow(new Object[]{f.getDataDoCalculoSalario().format(DateTimeFormatter.ofPattern("dd/MM/uuuu")), f.getNome(), f.getSalarioBase(), 0.0, f.getSalarioComBonus()});
            }
        }
    }
}
