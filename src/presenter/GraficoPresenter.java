/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter;

import dao.IFuncionarioDAO;
import factoryMethodDynamic.FabricaFuncionario;
import factoryMethodDynamic.apoio.LeituraDoProperties;
import graphic.builder.Diretor;
import graphic.builder.GraficoDeBarra;
import graphic.builder.GraficoDePizza;
import java.io.IOException;
import observer.Observador;
import org.jfree.chart.ChartPanel;
import view.GraficoView;

/**
 *
 * @author marcos
 */
public enum GraficoPresenter implements Observador {
    INSTANCE;

    private GraficoView view;
    protected IFuncionarioDAO dao;

    private GraficoPresenter() {
        this.view = new GraficoView();
        initListeners();
        TelaPrincipalPresenter.INSTANCE.addView(view);
        try {
            dao = FabricaFuncionario.criar(LeituraDoProperties.lerPersistenciaFuncionario());
            plotarGraficoDeBarra();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        dao.addObservador(this);
    }

    private void initListeners() {
        view.getjComboBoxGrafico().addActionListener((e) -> {
            try {
                confComboBoxGrafico();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        view.getjButtonFechar().addActionListener((e) -> {
            view.setVisible(false);
        });
    }

    public void confTela() {
        view.getjLabelTitulo().setText("Gráfico de Regiões");
        view.setVisible(true);
    }

    private void confComboBoxGrafico() throws IOException {
        String itemSelecionado = (String) view.getjComboBoxGrafico().getSelectedItem();
        if (itemSelecionado.equals("Gráfico de barra")) {
            plotarGraficoDeBarra();
        } else {
            plotarGraficoDePizza();
        }
    }

    private void plotarGraficoDeBarra() throws IOException {
        Diretor diretor = new Diretor(new GraficoDeBarra());
        plotarGrafico(diretor);
    }

    private void plotarGraficoDePizza() throws IOException {
        Diretor diretor = new Diretor(new GraficoDePizza());
        plotarGrafico(diretor);
    }

    private void plotarGrafico(Diretor diretor) {
        ChartPanel grafico = diretor.build();
        grafico.setSize(view.getjPanel().getWidth(), view.getjPanel().getHeight());
        grafico.setVisible(true);
        view.getjPanel().removeAll();
        view.getjPanel().add(grafico);
        view.getjPanel().revalidate();
        view.getjPanel().repaint();
    }

    @Override
    public void atualizar() {
        if (view.isVisible()) {
            try {
                confComboBoxGrafico();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
