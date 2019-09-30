/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphic.builder;

import java.io.IOException;
import java.util.Locale;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author marcos
 */
public class GraficoDePizza extends Grafico {

    public GraficoDePizza() throws IOException {
        super();
    }

    public DefaultPieDataset createPieDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Sul", quantidadeDeFuncionarioPorRegiao("Sul"));
        dataset.setValue("Sudeste", quantidadeDeFuncionarioPorRegiao("Sudeste"));
        dataset.setValue("Centro-Oeste", quantidadeDeFuncionarioPorRegiao("Centro-Oeste"));
        dataset.setValue("Nordeste", quantidadeDeFuncionarioPorRegiao("Nordeste"));
        dataset.setValue("Norte", quantidadeDeFuncionarioPorRegiao("Norte"));
        return dataset;
    }

    @Override
    public void setarGrafico() {
        grafico = ChartFactory.createPieChart(titulo, createPieDataset(), legenda, toolTips, Locale.ITALY);
    }

    @Override
    public void criarComponente() {
        componente = new ChartPanel(grafico, true);
    }
}