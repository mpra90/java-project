/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter;

import dao.IFuncionarioDAO;
import factoryMethodDynamic.FabricaFuncionario;
import factoryMethodDynamic.apoio.LeituraDoProperties;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import presenter.memento.business.Funcionario;

/**
 *
 * @author marcos
 */
public enum ListagemFuncionariosPresenter {
    INSTANCE;
    
    protected IFuncionarioDAO dao;

    private ListagemFuncionariosPresenter() {
        try {
            dao = FabricaFuncionario.criar(LeituraDoProperties.lerPersistenciaFuncionario());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void imprimir() throws Exception {
        ArrayList<Funcionario> funcionarios = dao.retornarTodosRegistros();
        //cria datasource a partir da collection
        JRBeanCollectionDataSource jrds = new JRBeanCollectionDataSource(funcionarios);

        Map<String, Object> parametros = new HashMap();
        parametros.put("totalFuncionarios", Integer.toString(funcionarios.size()));

        //preenche relatorio com parâmetros e datasource
        JasperPrint printer = JasperFillManager.fillReport("ListagemFuncionariosSemBD.jasper", parametros, jrds);

        //abre visualizador
        JasperViewer jv = new JasperViewer(printer, false);
        jv.setTitle("Listagem de Funcionarios");
        jv.setVisible(true);
    }
}
