/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDateTime;

/**
 *
 * @author marcos
 */
public class Log {
    
    private String usuario = "Administrador";
    private LocalDateTime data;
    private String nomeDoFuncionario;
    private String operacao;

    public Log(LocalDateTime data, String nomeDoFuncionario, String operacao) {
        this.data = data;
        this.nomeDoFuncionario = nomeDoFuncionario;
        this.operacao = operacao;
    }

    public String getUsuario() {
        return usuario;
    }
    
    public LocalDateTime getData() {
        return data;
    }

    public String getNomeDoFuncionario() {
        return nomeDoFuncionario;
    }

    public String getOperacao() {
        return operacao;
    }
}
