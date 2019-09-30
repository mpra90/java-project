/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factoryMethodDynamic;

import dao.LogDAO;
import factoryMethodDynamic.apoio.LeituraDoProperties;
import java.util.Properties;

/**
 *
 * @author marcos
 */
public class FabricaLog {
    
    public static LogDAO criar(String tipoDePersistencia) {
        LogDAO dao = null;
        try {
            Properties prop = LeituraDoProperties.getProp();
            String key = LeituraDoProperties.getKey();
            tipoDePersistencia = prop.getProperty(key);
            Class classe = Class.forName(tipoDePersistencia);
            Object objeto = classe.newInstance();
            dao = (LogDAO) objeto;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dao;
    }
}
