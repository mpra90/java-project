/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphic.builder;

import java.io.IOException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author marcos
 */
public class GraficoDeBarra extends Grafico {

    public GraficoDeBarra() throws IOException {
        super();
    }

    public CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(quantidadeDeFuncionarioPorRegiao("Sul"), "Sul", "Sul");
        dataset.addValue(quantidadeDeFuncionarioPorRegiao("Sudeste"), "Sudeste", "Sudeste");
        dataset.addValue(quantidadeDeFuncionarioPorRegiao("Centro-Oeste"), "Centro-Oeste", "Centro-Oeste");
        dataset.addValue(quantidadeDeFuncionarioPorRegiao("Nordeste"), "Nordeste", "Nordeste");
        dataset.addValue(quantidadeDeFuncionarioPorRegiao("Norte"), "Norte", "Norte");
        return dataset;
    }

    @Override
    public void setarGrafico() {
        this.grafico = ChartFactory.createBarChart3D(titulo, titulo, titulo, createDataset(), PlotOrientation.VERTICAL, legenda, toolTips, urls);
    }

    @Override
    public void criarComponente() {
        this.componente = new ChartPanel(grafico, true);
    }
}
