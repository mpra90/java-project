/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.adapter;

import collection.Logs;
import java.time.LocalDateTime;
import model.Log;

/**
 *
 * @author marcos
 */
public enum GerarLog implements ILogAdapter {
    INSTANCE;
    
    private Logs logs;
    
    private GerarLog() {
        logs = Logs.INSTANCE;
    }
    
    @Override
    public void gerarLog(LocalDateTime data, String nomeDoFuncionario, String operacao) {
        Log l = new Log(data, nomeDoFuncionario, operacao);
        logs.addLog(l);
    }
}
