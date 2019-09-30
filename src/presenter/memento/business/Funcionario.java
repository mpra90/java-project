/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter.memento.business;

import model.chain.ProcessadoraCalculoBonus;
import java.time.LocalDate;
import java.util.ArrayList;
import model.Bonus;

/**
 *
 * @author marcos
 */
public class Funcionario {
    
    private int id;
    private String nome;
    private int idade;
    private String cargo;
    private int faltas;
    private LocalDate dataDeAdmissao;
    private double salarioBase;
    private double salarioComBonus;
    private String bonusInicial;
    private ArrayList<Bonus> bonusRecebido;
    private LocalDate dataDoCalculoSalario;
    private String regiao;

    //construtor para criar funcionario apartir da interface grafica
    public Funcionario(int id, String nome, int idade, String cargo, String bonusInicial, int faltas, LocalDate dataDeAdmissao, double salarioBase, String regiao) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.cargo = cargo;
        this.bonusInicial = bonusInicial;
        this.faltas = faltas;
        this.dataDeAdmissao = dataDeAdmissao;
        this.salarioBase = salarioBase;
        bonusRecebido = new ArrayList<>();
        this.regiao = regiao;
        ProcessadoraCalculoBonus.INSTANCE.processar(this);
    }
    
    //construtor para criar funcionario apartir do banco de dados
    public Funcionario(int id, String nome, int idade, String cargo, String bonusInicial, int faltas, LocalDate dataDeAdmissao, double salarioBase, LocalDate dataDoCalculoSalario, double salarioComBonus, ArrayList<Bonus> bonusRecebido, String regiao) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.cargo = cargo;
        this.bonusInicial = bonusInicial;
        this.faltas = faltas;
        this.dataDeAdmissao = dataDeAdmissao;
        this.salarioBase = salarioBase;
        this.dataDoCalculoSalario = dataDoCalculoSalario;
        this.salarioComBonus = salarioComBonus;
        this.bonusRecebido = bonusRecebido;
        this.regiao = regiao;
    }
    
    public MementoFuncionario criarMemento() {
        return new MementoFuncionario(salarioComBonus, dataDoCalculoSalario);
    }
    
    public void restaurar(MementoFuncionario memento) {
        this.salarioComBonus = memento.getSalarioComBonus();
        this.dataDoCalculoSalario = memento.getDataDoCalculoSalario();
    }
    
    
    public void calcularSalario() {
        for (Bonus b : bonusRecebido) {
            salarioComBonus += b.getValor();
        }
        salarioComBonus += salarioBase;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public String getCargo() {
        return cargo;
    }

    public int getFaltas() {
        return faltas;
    }

    public LocalDate getDataDeAdmissao() {
        return dataDeAdmissao;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public double getSalarioComBonus() {
        return salarioComBonus;
    }
    
    public void addBonus(Bonus b) {
        bonusRecebido.add(b);
    }

    public ArrayList<Bonus> getBonusRecebido() {
        return bonusRecebido;
    }
    
    public boolean bonusEstaVazio() {
        return bonusRecebido.isEmpty();
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void setFaltas(int faltas) {
        this.faltas = faltas;
    }

    public void setSalarioBase(double salarioBase) {
        this.salarioBase = salarioBase;
    }
    
    public void setDataDeAdmissao(LocalDate dataDeAdmissao) {
        this.dataDeAdmissao = dataDeAdmissao;
    }

    public void mudarDeBonus(String bonus) {
        salarioComBonus = 0;
        bonusInicial = bonus;
        bonusRecebido.clear();
        ProcessadoraCalculoBonus.INSTANCE.processar(this);
    }

    public String getBonusInicial() {
        return bonusInicial;
    }

    public LocalDate getDataDoCalculoSalario() {
        return dataDoCalculoSalario;
    }

    public void setDataDoCalculoSalario(LocalDate dataDoCalculoSalario) {
        this.dataDoCalculoSalario = dataDoCalculoSalario;
    }
    
    public double retornaValorTotalDosBonus() {
        double valor = 0.0;
        for (Bonus b : bonusRecebido) {
            valor += b.getValor();
        }
        return valor;
    }

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }
}
