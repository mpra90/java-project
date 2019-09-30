/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.chain;

import dao.IFuncionarioDAO;
import factoryMethodDynamic.FabricaFuncionario;
import factoryMethodDynamic.apoio.LeituraDoProperties;
import java.io.IOException;
import presenter.memento.business.Funcionario;
/**
 *
 * @author marcos
 */
public abstract class TratadoraCalculoBonus {
    
    protected String nomeDoBonus;
    protected double salarioComBonus;
    protected IFuncionarioDAO dao;

    public TratadoraCalculoBonus(String nomeDoBonus) {
        this.nomeDoBonus = nomeDoBonus;
        try {
            dao = FabricaFuncionario.criar(LeituraDoProperties.lerPersistenciaFuncionario());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    abstract boolean aceita(Funcionario f);
    
    abstract void doHandle(Funcionario f);
}
