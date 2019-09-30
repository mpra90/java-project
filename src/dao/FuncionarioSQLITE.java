/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import model.Bonus;
import java.time.LocalDate;
import java.util.ArrayList;
import dao.observer.ObservadoDAO;
import java.util.logging.Level;
import java.util.logging.Logger;
import presenter.memento.business.Funcionario;

/**
 *
 * @author marcos
 */
public class FuncionarioSQLITE extends ObservadoDAO implements IFuncionarioDAO {

    private static FuncionarioSQLITE instance = null;
    private String url;
    private Connection conexao;

    private FuncionarioSQLITE() {
        super();
        url = "jdbc:sqlite:./database/mydb.sqlite";
        conexao = null;
    }

    public static FuncionarioSQLITE getInstance() {
        if (instance == null) {
            instance = new FuncionarioSQLITE();
        }
        return instance;
    }

    private void conectar() {
        try {
            conexao = DriverManager.getConnection(url);
            System.out.println("Conexao com o Banco SQLITE conectada com sucesso!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void desconectar() {
        try {
            conexao.close();
            System.out.println("Conexao com o Banco SQLITE encerrada com sucesso!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Funcionario inserirFuncionario(String nome, int idade, String cargo, String bonusInicial, int faltas, String dataDeAdmissao, double salarioBase, String regiao) {
        conectar();
        String query0 = "INSERT INTO funcionarios(nome, idade, cargo, regiao, bonusInicial, faltas, dataDeAdmissao, salarioBase"
                + ", dataDoCalculoSalario, salarioComBonus) VALUES(?,?,?,?,?,?,?,?,?,?)";
        String query1 = "SELECT MAX(idFuncionario) AS idFuncionario FROM funcionarios";
        int idFuncionario = 0;
        try {
            PreparedStatement stm = conexao.prepareStatement(query0);
            stm.setString(1, nome);
            stm.setInt(2, idade);
            stm.setString(3, cargo);
            stm.setString(4, regiao);
            stm.setString(5, bonusInicial);
            stm.setInt(6, faltas);
            stm.setString(7, dataDeAdmissao);
            stm.setDouble(8, salarioBase);
            stm.setString(9, "");
            stm.setDouble(10, 0.0);
            stm.executeUpdate();
            stm = conexao.prepareStatement(query1);
            ResultSet resultado = stm.executeQuery();
            idFuncionario = resultado.getInt("idFuncionario");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        desconectar();
        LocalDate data = LocalDate.parse(dataDeAdmissao, DateTimeFormatter.ofPattern("dd/MM/uuuu"));
        Funcionario f = new Funcionario(idFuncionario, nome, idade, cargo, bonusInicial, faltas, data, salarioBase, regiao);
        notificar();
        return f;
    }

    @Override
    public Bonus inserirBonus(String nome, String data, double valor, int idFuncionario) {
        conectar();
        String query0 = "INSERT INTO bonus(nome, data, valor, idFuncionario) VALUES(?,?,?,?)";
        String query1 = "SELECT MAX(idBonus) AS idBonus FROM bonus";
        int idBonus = 0;
        try {
            PreparedStatement stm = conexao.prepareStatement(query0);
            stm.setString(1, nome);
            stm.setString(2, data);
            stm.setDouble(3, valor);
            stm.setInt(4, idFuncionario);
            stm.executeUpdate();
            stm = conexao.prepareStatement(query1);
            ResultSet resultado = stm.executeQuery();
            idBonus = resultado.getInt("idBonus");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        desconectar();
        LocalDate lData = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/uuuu"));
        return new Bonus(idBonus, nome, lData, valor);
    }

    @Override
    public void deletar(int idFuncionario) {
        conectar();
        String query0 = "DELETE FROM bonus WHERE idFuncionario = ?";
        String query1 = "DELETE FROM funcionarios WHERE idFuncionario = ?";
        try {
            PreparedStatement stm = conexao.prepareStatement(query0);
            stm.setInt(1, idFuncionario);
            stm.executeUpdate();
            stm = conexao.prepareStatement(query1);
            stm.setInt(1, idFuncionario);
            stm.executeUpdate();
            notificar();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        desconectar();
        notificar();
    }

    @Override
    public void alterar(int idFuncionario, String nome, int idade, String cargo, String bonusInicial, int faltas, String dataDeAdmissao, double salarioBase, String regiao) {
        conectar();
        String query0 = "UPDATE funcionarios SET nome = ?, idade = ?, cargo = ?, regiao = ?, bonusInicial = ?, faltas = ?,"
                + " dataDeAdmissao = ?, salarioBase = ?, salarioComBonus = ? WHERE idFuncionario = ?";
        String query1 = "DELETE FROM bonus WHERE idFuncionario = ?";
        try {
            PreparedStatement stm = conexao.prepareStatement(query0);
            stm.setString(1, nome);
            stm.setInt(2, idade);
            stm.setString(3, cargo);
            stm.setString(4, regiao);
            stm.setString(5, bonusInicial);
            stm.setInt(6, faltas);
            stm.setString(7, dataDeAdmissao);
            stm.setDouble(8, salarioBase);
            stm.setDouble(9, 0.0);
            stm.setInt(10, idFuncionario);
            stm.executeUpdate();
            stm = conexao.prepareStatement(query1);
            stm.setInt(1, idFuncionario);
            stm.executeUpdate();
            notificar();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        desconectar();
        notificar();
    }

    @Override
    public ArrayList<Funcionario> retornarTodosRegistros() {
        conectar();
        String query = "SELECT idFuncionario, nome, idade, cargo, regiao, bonusInicial, faltas, dataDeAdmissao,"
                + "salarioBase, dataDoCalculoSalario, salarioComBonus FROM funcionarios";
        ArrayList<Funcionario> funcionarios = new ArrayList<>();;
        try {
            PreparedStatement stm = conexao.prepareStatement(query);
            ResultSet resultado = stm.executeQuery();
            while (resultado.next()) {
                int idF = resultado.getInt("idFuncionario");
                String nome = resultado.getString("nome");
                int idade = resultado.getInt("idade");
                String cargo = resultado.getString("cargo");
                String regiao = resultado.getString("regiao");
                String bonusInicial = resultado.getString("bonusInicial");
                int faltas = resultado.getInt("faltas");
                String dataDeAdmissaoString = resultado.getString("dataDeAdmissao");
                LocalDate dataDeAdmissao = LocalDate.parse(dataDeAdmissaoString, DateTimeFormatter.ofPattern("dd/MM/uuuu"));
                double salarioBase = resultado.getDouble("salarioBase");
                String dataDoCalculoSalarioString = resultado.getString("dataDoCalculoSalario");
                LocalDate dataDoCalculoSalario = null;
                if (!dataDoCalculoSalarioString.equals("")) {
                    dataDoCalculoSalario = LocalDate.parse(dataDoCalculoSalarioString, DateTimeFormatter.ofPattern("dd/MM/uuuu"));
                }
                double salarioComBonus = resultado.getDouble("salarioComBonus");
                ArrayList<Bonus> bonus = retornarRegistrosBonus(idF);
                Funcionario f = new Funcionario(idF, nome, idade, cargo, bonusInicial, faltas, dataDeAdmissao, salarioBase, dataDoCalculoSalario, salarioComBonus, bonus, regiao);
                funcionarios.add(f);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        desconectar();
        return funcionarios;
    }

    private ArrayList<Bonus> retornarRegistrosBonus(int idF) {
        String query = "SELECT idBonus, nome, data, valor FROM bonus WHERE idFuncionario = ?";
        ArrayList<Bonus> bonus = new ArrayList<>();
        try {
            PreparedStatement stm = conexao.prepareStatement(query);
            stm.setInt(1, idF);
            ResultSet resultado = stm.executeQuery();
            while (resultado.next()) {
                int idB = resultado.getInt("idBonus");
                String nome = resultado.getString("nome");
                String dataString = resultado.getString("data");
                LocalDate data = LocalDate.parse(dataString, DateTimeFormatter.ofPattern("dd/MM/uuuu"));
                double valor = resultado.getDouble("valor");
                Bonus b = new Bonus(idB, nome, data, valor);
                bonus.add(b);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return bonus;
    }

    @Override
    public void atualizarRegistroDoSalarioCalculado(ArrayList<Funcionario> funcionarios) {
        conectar();
        String query = "UPDATE funcionarios SET dataDoCalculoSalario = ?, salarioComBonus = ? WHERE idFuncionario = ?";
        try {
            PreparedStatement stm = conexao.prepareStatement(query);
            for (Funcionario f : funcionarios) {
                if (f.getDataDoCalculoSalario() != null) {
                    stm.setString(1, f.getDataDoCalculoSalario().format(DateTimeFormatter.ofPattern("dd/MM/uuuu")));
                } else {
                    stm.setString(1, "");
                }
                stm.setDouble(2, f.getSalarioComBonus());
                stm.setInt(3, f.getId());
                stm.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        desconectar();
        notificar();
    }

    @Override
    public int retornarTotalDeRegistro() {
        conectar();
        String query = "SELECT COUNT(idFuncionario) AS quantidade FROM funcionarios";
        int qtd = 0;
        try {
            PreparedStatement stm = conexao.prepareStatement(query);
            ResultSet result = stm.executeQuery();
            qtd = result.getInt("quantidade");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        desconectar();
        return qtd;
    }
}
