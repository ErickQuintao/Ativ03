/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void cadastrarProduto (ProdutosDTO produto){
 conn = new conectaDAO().connectDB();
                String sql = "INSERT INTO produtos(nome, valor,status) VALUES "
                        + "(?, ?, ?)"; 
                try {
                    PreparedStatement stmt = this.conn.prepareStatement(sql);
                    stmt.setString(1, produto.getNome());  
                    stmt.setInt(2, produto.getValor());
                    stmt.setString(3, produto.getStatus());
                    stmt.execute();  
                    
                    
                } catch (Exception e) {
                        System.out.println("Erro ao inserir produtos: " + e.getMessage());
                    }
                   
        
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){ 
        try {
        // Conectar ao banco
        conn = new conectaDAO().connectDB();

        // Instrução SQL
          String sql = "SELECT id, nome, valor, status FROM produtos;";
         PreparedStatement stmt = this.conn.prepareStatement(sql);
        // Executar a instrução SQL e pegar os resultados
        ResultSet resposta = stmt.executeQuery();

        while (resposta.next()) {
            // Cria uma nova instância de AtendimentoClass
            ProdutosDTO c = new ProdutosDTO();
            
            // Armazenar as informações utilizando métodos set para cada atributo
            c.setId(resposta.getInt("id"));
            c.setNome(resposta.getString("nome"));
            c.setValor(resposta.getInt("valor"));
            c.setStatus(resposta.getString("status"));
            // Adiciona o objeto AtendimentoClass à lista
            listagem.add(c);
        }

    } catch (SQLException e) {
        System.out.println("Erro ao listar os registros do banco de dados!");
        e.printStackTrace(); // Isso ajudará a identificar a causa do erro
    }

        return listagem;
    }
    
    public void venderProduto(int id){
    conn = new conectaDAO().connectDB();
    String sql = "UPDATE produtos SET status=? WHERE id=?"; // Correção aqui
    try {
        PreparedStatement stmt = this.conn.prepareStatement(sql);
        stmt.setString(1, "Vendido");
        stmt.setInt(2, id); // Setando o valor do ID aqui
        stmt.executeUpdate(); // Método correto para UPDATE
    } catch (Exception e) {
        System.out.println("Erro ao atualizar: " + e.getMessage());
    }
}

    public ArrayList<ProdutosDTO> listarProdutosVendidos(){
    try {
        // Conectar ao banco
        conn = new conectaDAO().connectDB();

        // Instrução SQL
        String sql = "SELECT id, nome, valor, status FROM produtos WHERE status=?";
        PreparedStatement stmt = this.conn.prepareStatement(sql);
        stmt.setString(1, "Vendido"); // Definir o status como "Vendido"

        // Executar a instrução SQL e pegar os resultados
        ResultSet rs = stmt.executeQuery();

        // Verificar se há resultados
        while (rs.next()) {
            // Criar uma nova instância de ProdutosDTO
            ProdutosDTO c = new ProdutosDTO();
            
            // Armazenar as informações utilizando métodos set para cada atributo
            c.setId(rs.getInt("id"));
            c.setNome(rs.getString("nome"));
            c.setValor(rs.getInt("valor"));
            c.setStatus(rs.getString("status"));
            
            // Adicionar o objeto ProdutosDTO à lista
            listagem.add(c);
        }

        // Se não houver resultados, imprima uma mensagem
        if (listagem.isEmpty()) {
            System.out.println("Nenhum produto encontrado com o status 'Vendido'.");
        }

        // Fechar o PreparedStatement e o Connection
        stmt.close();
        conn.close();

    } catch (SQLException e) {
        System.out.println("Erro ao listar os registros do banco de dados!");
        e.printStackTrace(); // Isso ajudará a identificar a causa do erro
    }

    return listagem;
}


    
        
}

