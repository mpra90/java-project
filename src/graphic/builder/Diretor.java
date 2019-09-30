/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphic.builder;

import org.jfree.chart.ChartPanel;

/**
 *
 * @author marcos
 */
public class Diretor {
    
    private Grafico builder;

    public Diretor(Grafico builder) {
        this.builder = builder;
    }
    
    public ChartPanel build() {
        builder.setarGrafico();
        builder.criarComponente();
        return builder.getComponente();
    }
}
