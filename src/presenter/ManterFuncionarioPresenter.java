/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter;

import dao.IFuncionarioDAO;
import dao.adapter.GerarLog;
import dao.adapter.ILogAdapter;
import exceptions.BonusException;
import exceptions.CargoException;
import exceptions.FaltasException;
import exceptions.IdadeException;
import exceptions.NomeException;
import exceptions.RegiaoException;
import exceptions.SalarioException;
import factoryMethodDynamic.FabricaFuncionario;
import factoryMethodDynamic.apoio.LeituraDoProperties;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import presenter.memento.business.Funcionario;
import presenter.state.ManterFuncionarioState;
import presenter.state.Inclusao;
import presenter.state.Visualizacao;
import view.ManterFuncionarioView;

/**
 *
 * @author marcos
 */
public class ManterFuncionarioPresenter {
    
    private static ManterFuncionarioPresenter instance;
    private ManterFuncionarioView view;
    private ManterFuncionarioState estado;
    private Funcionario funcionario;
    private boolean telaDeBuscar;
    private ILogAdapter log;
    private IFuncionarioDAO funcionarioDao;
    
    private ManterFuncionarioPresenter(Funcionario funcionario) {
        view = new ManterFuncionarioView();
        TelaPrincipalPresenter.INSTANCE.addView(view);
        log = GerarLog.INSTANCE;
        try {
            funcionarioDao = FabricaFuncionario.criar(LeituraDoProperties.lerPersistenciaFuncionario());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        initListeners();
        confTela();
        this.funcionario = funcionario;
        if (funcionario == null) {
            estado = new Inclusao(this);
        } else {
            estado = new Visualizacao(this);
        }
    }
    
    private ManterFuncionarioPresenter() {
    }
    
    public static ManterFuncionarioPresenter getInstance(Funcionario f) {
        if (instance == null) {
            instance = new ManterFuncionarioPresenter(f);
        }
        return instance;
    }
    
    public static ManterFuncionarioPresenter getInstance() {
        if (instance == null) {
            instance = new ManterFuncionarioPresenter();
        }
        return instance;
    }
    
    private void confTela() {
        view.getDatePicker().getComponentToggleCalendarButton().setText("");
        view.getDatePicker().getComponentToggleCalendarButton().setIcon(new ImageIcon(getClass().getResource("/icons/icons8-schedule-16.png")));
        view.getDatePicker().getSettings().setLocale(new Locale("pt", "BR"));
        view.getDatePicker().getSettings().setFormatForDatesCommonEra("dd/MM/uuuu");
    }
    
    private void initListeners() {
        view.getjButtonSalvar().addActionListener((e)-> {
            try {
                salvar();
            } catch (FaltasException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), ex.getCause().getMessage(), JOptionPane.WARNING_MESSAGE);
            } catch (SalarioException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), ex.getCause().getMessage(), JOptionPane.WARNING_MESSAGE);
            } catch (IdadeException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), ex.getCause().getMessage(), JOptionPane.WARNING_MESSAGE);
            } catch (NomeException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), ex.getCause().getMessage(), JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Falha: " + ex.getMessage());
            }
        });

        view.getjButtonIncluir().addActionListener((e) -> {
            try {
                incluir();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
            }
        });

        view.getjButtonEditar().addActionListener((e) -> {
            try {
                editar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
            }
        });

        view.getjButtonExcluir().addActionListener((e) -> {
            try {
                excluir();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
            }
        });

        view.getjButtonFechar().addActionListener((e) -> {
            try {
                fechar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
            }
        });

        view.getjButtonCancelar().addActionListener((e) -> {
            try {
                cancelar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
            }
        });
        
        view.getjButtonVerBonus().addActionListener((e) -> {
            confBotaoVerBonus();
        });
    }
    
    public void incluir() throws Exception {
        estado.incluir();
    }
    
    public void salvar() throws Exception {
        estado.salvar();
    }
    
    public void cancelar() throws Exception {
        estado.cancelar();
    }
    
    public void editar() throws Exception {
        estado.editar();    
    }
    
    public void fechar() throws Exception {
        estado.fechar();
    }
    
    public void excluir() throws Exception {
        estado.excluir();
    }
    
    private void confBotaoVerBonus() {
        VisualizacaoBonusPresenter visualizacaoBonus = VisualizacaoBonusPresenter.INSTANCE;
        visualizacaoBonus.setFuncionario(funcionario);
        visualizacaoBonus.confTela();
        log.gerarLog(LocalDateTime.now(), funcionario.getNome(), "Bônus Consultado");
    }
    
    public void gerarLog(String operacao) {
        log.gerarLog(LocalDateTime.now(), funcionario.getNome(), operacao);
    }
    
    public Funcionario inserirFuncionarioNoBanco(String nome, int idade, String cargo, String bonusInicial, int faltas, String dataDeAdmissao, double salarioBase, String regiao) {
        return funcionarioDao.inserirFuncionario(nome, idade, cargo, bonusInicial, faltas, dataDeAdmissao, salarioBase, regiao);
    }
    
    public void alterarFuncionarioNoBanco(int idFuncionario, String nome, int idade, String cargo, String bonusInicial, int faltas, String dataDeAdmissao, double salarioBase, String regiao) {
        funcionarioDao.alterar(idFuncionario, nome, idade, cargo, bonusInicial, faltas, dataDeAdmissao, salarioBase, regiao);
    }
    
    public void deletarFuncionarioNoBanco() {
        funcionarioDao.deletar(funcionario.getId());
    }
  
    public void matarInstancia() {
        instance = null;
    }
    
    public ManterFuncionarioView getView() {
        return view;
    }
    
    public ManterFuncionarioState getEstado() {
        return estado;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public boolean veioPelaTelaDeBuscar() {
        return telaDeBuscar;
    }

    public void setVeioPelaTelaDeBuscar(boolean telaDeBuscar) {
        this.telaDeBuscar = telaDeBuscar;
    }
    
    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;      
    }

    public void setEstado(ManterFuncionarioState estado) {
        this.estado = estado;
    }
    
    public void limparCampos() {
        view.getjTextFieldNome().setText("");
        view.getjTextFieldIdade().setText("");
        view.getjTextFieldIdade().setText("");
        view.getjTextFieldFaltas().setText("");
        view.getjTextFieldSalario().setText("");
        view.getjComboBoxCargo().setSelectedIndex(0);
        view.getjComboBoxBonus().setSelectedIndex(0);
        view.getDatePicker().setText("");
    }
    
    public void bloquearCampos() {
        view.getjTextFieldNome().setEditable(false);
        view.getjTextFieldIdade().setEditable(false);
        view.getjTextFieldFaltas().setEditable(false);
        view.getjTextFieldSalario().setEditable(false);
        view.getjComboBoxCargo().setEnabled(false);
        view.getjComboBoxBonus().setEnabled(false);
        view.getDatePicker().setEnabled(false);
        view.getjComboBoxRegiao().setEnabled(false);
    }
    
    public void desbloquearCampos() {
        view.getjTextFieldNome().setEditable(true);
        view.getjTextFieldIdade().setEditable(true);
        view.getjTextFieldFaltas().setEditable(true);
        view.getjTextFieldSalario().setEditable(true);
        view.getjComboBoxCargo().setEnabled(true);
        view.getjComboBoxBonus().setEnabled(true);
        view.getDatePicker().setEnabled(true);
        view.getjComboBoxRegiao().setEnabled(true);
    }
    
    public boolean validarFuncionario() throws Exception {
        String nome = view.getjTextFieldNome().getText();
        boolean validacaoDoNome = nome.matches("([A-Za-z][A-Za-z]*)+([\\s][A-Za-z][A-Za-z]*)*");
        String idade = view.getjTextFieldIdade().getText();
        boolean validacaoDaIdade = idade.matches("[0-9]*");
        String salario = view.getjTextFieldSalario().getText().replace(",", ".");
        boolean validacaoDoSalario = salario.matches("[1-9][0-9]*+([0-9]*|[.][0-9]*)");
        String faltas = view.getjTextFieldFaltas().getText();
        boolean validacaoDasFaltas = faltas.matches("[0-9]*");
        String cargo = (String) view.getjComboBoxCargo().getSelectedItem();
        String bonusInicial = (String) view.getjComboBoxBonus().getSelectedItem();
        String regiao = (String) view.getjComboBoxRegiao().getSelectedItem();
        if (validacaoDoNome) {
            if (validacaoDaIdade) {
                if (!cargo.equals("Nenhum")) {   
                    if (!bonusInicial.equals("Nenhum")) {
                        if (!regiao.equals("Nenhum")) {
                            if (validacaoDoSalario) {
                                if (validacaoDasFaltas) {
                                    return true;
                                } else {
                                    throw new FaltasException();
                                }
                            } else {
                                throw new SalarioException();
                            }
                        } else {
                            throw new RegiaoException();
                        }
                    } else {
                        throw new BonusException();
                    }
                } else {
                    throw new CargoException();
                }
            } else {
                throw new IdadeException();
            }
        } else {
            throw new NomeException();
        }
    }
}
