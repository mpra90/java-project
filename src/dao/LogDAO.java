/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import collection.Logs;
import dao.adapter.GerarLog;
import dao.adapter.ILogAdapter;

/**
 *
 * @author marcos
 */
public abstract class LogDAO {
    
    protected ILogAdapter log;
    protected Logs collection;

    public LogDAO() {
        log = GerarLog.INSTANCE;
        collection = Logs.INSTANCE;
    }
    
    public abstract void ler();
    public abstract void escrever();
}