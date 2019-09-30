/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import presenter.TelaPrincipalPresenter;

/**
 *
 * @author marcos
 */
public class Bonus {
    
    private final int id;
    private final String nome;
    private final LocalDate data;
    private final double valor;
/*
    //construtor para criar um bonus apartir do chain
    public Bonus(String nome, LocalDate data, double valor) {
        this.nome = nome;
        this.data = data;
        this.valor = valor;
//        id = TelaPrincipalPresenter.INSTANCE.getDao().retornarMaiorRegistroBonus() + 1; //para ser o próximo id
//        id = TelaPrincipalPresenter.INSTANCE.getDao().retornarMaiorRegistroBonus();
    }
*/
    //construtor para criar um bonus apartir do banco de dados
    public Bonus(int id, String nome, LocalDate data, double valor) {
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.valor = valor;
    }   

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getData() {
        return data;
    }

    public double getValor() {
        return valor;
    }
}