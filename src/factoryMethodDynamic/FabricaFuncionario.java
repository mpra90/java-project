/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factoryMethodDynamic;

import dao.IFuncionarioDAO;
import java.lang.reflect.Method;

/**
 *
 * @author marcos
 */
public class FabricaFuncionario {
    
    public static IFuncionarioDAO criar(String tipoDePersistencia) {
        IFuncionarioDAO dao = null;
        try {
            Class classe = Class.forName(tipoDePersistencia);
            Method factoryMethod = classe.getDeclaredMethod("getInstance");
            Object singleton = factoryMethod.invoke(null);
            dao = (IFuncionarioDAO) singleton;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dao;
    }
}
