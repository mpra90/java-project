/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factoryMethodDynamic.apoio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author marcos
 */
public class LeituraDoProperties {

    private static String path1 = "src/properties/persistenciaFuncionario.properties";
    private static String path2 = "src/properties/persistenciaLog.properties";
    private static Properties prop = new Properties();
    private static String key = "prop.nome";
    private static String persistenciaFuncionario;
    private static String persistenciaLog;

    public static String lerPersistenciaFuncionario() throws IOException {
        prop.load(new FileInputStream(path1));
        return prop.getProperty(key);
    }

    public static String lerPersistenciaLog() throws IOException {
        prop.load(new FileInputStream(path2));
        return prop.getProperty(key);
    }

    public static void escrever() throws Exception {
        //lendo | escrevendo no arquivo de persistencia do funcionario
        FileInputStream in = new FileInputStream(path1);
        prop.load(in);

        FileOutputStream out = new FileOutputStream(path1);
        prop.setProperty(key, persistenciaFuncionario);
        prop.store(out, null);

        //lendo | escrevendo no arquivo de persistencia do log
        in = new FileInputStream(path2);
        prop.load(in);
        in.close();

        out = new FileOutputStream(path2);
        prop.setProperty(key, persistenciaLog);
        prop.store(out, null);
        out.close();
    }

    public static String getKey() {
        return key;
    }

    public static Properties getProp() {
        return prop;
    }

    public static String getPersistenciaFuncionario() {
        return persistenciaFuncionario;
    }

    public static String getPersistenciaLog() {
        return persistenciaLog;
    }

    public static void setPersistenciaFuncionario(String persistenciaFuncionario) {
        LeituraDoProperties.persistenciaFuncionario = persistenciaFuncionario;
    }

    public static void setPersistenciaLog(String persistenciaLog) {
        LeituraDoProperties.persistenciaLog = persistenciaLog;
    }
}
