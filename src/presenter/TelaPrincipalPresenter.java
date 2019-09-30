/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter;

import dao.IFuncionarioDAO;
import presenter.memento.CalculaSalarioPresenter;
import dao.LogDAO;
import factoryMethodDynamic.FabricaFuncionario;
import factoryMethodDynamic.FabricaLog;
import factoryMethodDynamic.apoio.LeituraDoProperties;
import java.awt.Component;
import java.io.IOException;
import javax.swing.JOptionPane;
import view.TelaPrincipalView;

/**
 *
 * @author marcos
 */
public enum TelaPrincipalPresenter {
    INSTANCE;
    
    private TelaPrincipalView view;
    private LogDAO log;
    protected IFuncionarioDAO dao;

    private TelaPrincipalPresenter() {
        view = new TelaPrincipalView();
        try {
            log = FabricaLog.criar(LeituraDoProperties.lerPersistenciaLog());
            dao = FabricaFuncionario.criar(LeituraDoProperties.lerPersistenciaFuncionario());
            confPainelInferior();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        initListeners();
        log.ler();
        confTela();
        
    }

    private void confTela() {
        view.setVisible(true);
    }

    private void initListeners() {
        view.getjMenuItemInserir().addActionListener((e) -> {
            confItemInserir();
        });
        
        view.getjMenuItemBuscar().addActionListener((e) -> {
            confItemBuscar();
        });
        
        view.getjMenuItemCalcular().addActionListener((e) -> {
            confItemCalcular();
        });
        
        view.getjMenuItemConfig().addActionListener((e) -> {
            try {
                confItemConfigurar();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        view.getjMenuItemGraficoFaltas().addActionListener((e) -> {
            confItemGraficoFaltas();
        });
        
        view.getjMenuItemGraficoRegioes().addActionListener((e) -> {
            confItemGraficoRegioes();
        });
        
        view.getjMenuItemListarFuncionarios().addActionListener((e) -> {
            try {
                confItemListarFuncionarios();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Falha:" + ex.getMessage());
            }
        });
        
        view.getjMenuItemFaltas().addActionListener((e) -> {
            confItemFaltas();
        });
        
        view.getjMenuItemProventos().addActionListener((e) -> {
            confItemProventos();
        });
        
        view.getjMenuItemSair().addActionListener((e) -> {
            try {
                confItemSair();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private void confItemInserir() {
        ManterFuncionarioPresenter.getInstance(null);
    }

    private void confItemBuscar() {
        BuscaFuncionarioPresenter buscarFuncionario = BuscaFuncionarioPresenter.INSTANCE;
        buscarFuncionario.confTela();
    }

    private void confItemCalcular() {
        CalculaSalarioPresenter.INSTANCE.confTela();
    }

    private void confItemConfigurar() throws IOException {
        ConfiguracaoPersistenciaPresenter.INSTANCE.confTela();
    }
    
    private void confItemListarFuncionarios() throws Exception {
//        ListagemFuncionariosPresenter.INSTANCE.imprimir();
    }

    private void confItemSair() throws Exception {
        log.escrever();
        if ((LeituraDoProperties.getPersistenciaFuncionario()!= null) 
                && (LeituraDoProperties.getPersistenciaLog()!= null)) {
            LeituraDoProperties.escrever();
        }
        view.dispose();
    }
    
    private void confItemFaltas() {
        InclusaoDeFaltasPresenter faltasPresenter = InclusaoDeFaltasPresenter.INSTANCE;
        faltasPresenter.confTela();
    }
    
    private void confItemProventos() {
        ProventosEDescontosPresenter proventosPresenter = ProventosEDescontosPresenter.INSTANCE;
        proventosPresenter.confTela();
    }
    
    public void addView(Component c) {
        view.getjDesktopPane().add(c);
    }

    private void confItemGraficoFaltas() {
//        GraficoPresenter.INSTANCE.confTela();
    }

    private void confItemGraficoRegioes() {
        GraficoPresenter.INSTANCE.confTela();
    }
    
    private void confPainelInferior() throws IOException {
        view.getjLabelVersao().setText("2.5");
        String persistencia = LeituraDoProperties.lerPersistenciaFuncionario();
        view.getjLabelPersistencia().setText(persistencia.substring(15));
        view.getjLabelFuncionarios().setText(String.valueOf(dao.retornarTotalDeRegistro()));
    }
}
