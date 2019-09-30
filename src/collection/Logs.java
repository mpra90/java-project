/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collection;

import java.util.ArrayList;
import model.Log;

/**
 *
 * @author marcos
 */
public enum Logs {
    INSTANCE;
    
    private ArrayList<Log> collection;
    
    private Logs() {
        collection = new ArrayList<>();
    }
    
    public void addLog(Log l) {
        collection.add(l);
    }
    
    public ArrayList<Log> getLogs() {
        return collection;
    }
}
