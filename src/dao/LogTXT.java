/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import model.Log;

/**
 *
 * @author marcos
 */
public class LogTXT extends LogDAO {
    
    private final String path;

    public LogTXT() {
        path = "src/data/logs.txt";
    }    

    @Override
    public void ler() {
    }

    @Override
    public void escrever() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
            for (Log l : collection.getLogs()) {
                writer.append("Usuário: " + l.getUsuario());
                writer.newLine();
                writer.append("Data: " + l.getData().format(DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm:ss")));
                writer.newLine();
                writer.append("Funcionario: " + l.getNomeDoFuncionario());
                writer.newLine();
                writer.append("Operação: " + l.getOperacao());
                writer.newLine();
                writer.append("---------------------");
                writer.newLine();
            }
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
