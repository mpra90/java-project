/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphic.builder;

import dao.IFuncionarioDAO;
import factoryMethodDynamic.FabricaFuncionario;
import factoryMethodDynamic.apoio.LeituraDoProperties;
import java.io.IOException;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import presenter.memento.business.Funcionario;

/**
 *
 * @author marcos
 */
public abstract class Grafico {

    protected ChartPanel componente;
    protected String titulo;
    protected boolean legenda;
    protected boolean toolTips;
    protected boolean urls;
    protected JFreeChart grafico;
    private IFuncionarioDAO dao;

    public Grafico() throws IOException {
        titulo = "Quantidade de funcionários por região";
        legenda = true;
        toolTips = true;
        urls = true;
        dao = FabricaFuncionario.criar(LeituraDoProperties.lerPersistenciaFuncionario());
    }

    public abstract void setarGrafico();

    public abstract void criarComponente();

    public ChartPanel getComponente() {
        return componente;
    }

    protected int quantidadeDeFuncionarioPorRegiao(String regiao) {
        int cont = 0;
        for (Funcionario f : dao.retornarTodosRegistros()) {
            if (f.getRegiao().equals(regiao)) {
                cont++;
            }
        }
        return cont;
    }
}
