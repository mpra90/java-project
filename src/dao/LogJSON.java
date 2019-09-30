/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import model.Log;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author marcos
 */
public class LogJSON extends LogDAO {

    private final JSONObject objLog;
    private final String path;

    public LogJSON() {
        super();
        objLog = new JSONObject();
        path = "src/data/logs.json";
        
    }

    @Override
    public void ler() {
        try {
            //tenta ler o arquivo .json
            Scanner scanner = new Scanner(new FileReader(path));
            String jsonString = scanner.useDelimiter("\\A").next();
            
            //converte a string em JSONObject
            JSONObject obj = new JSONObject(jsonString);
            
            //pega o array do objeto principal, que é o funcionarios
            JSONArray arrayLog = obj.getJSONArray("logs");
            
            JSONObject objAux;
            
            for (int i = 0; i < arrayLog.length(); i++) {
                objAux = arrayLog.getJSONObject(i);
                String usuario = objAux.getString("Usuário");
                LocalDateTime dataHora = LocalDateTime.parse(objAux.getString("Data"), DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm:ss"));
                String funcionario = objAux.getString("Funcionario");
                String operacao = objAux.getString("Operação");
                
                log.gerarLog(dataHora, funcionario, operacao);
            }  
        } catch(FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void escrever() {
        JSONArray arrayLog = new JSONArray();
        for (Log l : collection.getLogs()) {
            JSONObject objAtual = new JSONObject();
            objAtual.put("Usuário", l.getUsuario());
            objAtual.put("Data", l.getData().format(DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm:ss")));
            objAtual.put("Funcionario", l.getNomeDoFuncionario());
            objAtual.put("Operação", l.getOperacao());
            arrayLog.put(objAtual);
        }
        objLog.put("logs", arrayLog);
        gerarArquivo();
    }

    private void gerarArquivo() {
        try {
            FileWriter file = new FileWriter(path);
            objLog.write(file, 4, 2);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
