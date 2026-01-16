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
import java.util.ArrayList;

public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    
    public void cadastrarProduto (ProdutosDTO produto){
       
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){
        return new ArrayList<>();
    }

    public void venderProduto(int idProduto) {
        String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";

        try {
            conn = new conectaDAO().connectDB();
            prep = conn.prepareStatement(sql);
            prep.setInt(1, idProduto);
            prep.executeUpdate();

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro);
        }
    }

   
    public ArrayList<ProdutosDTO> listarProdutosVendidos() {

        ArrayList<ProdutosDTO> vendidos = new ArrayList<>();
        String sql = "SELECT * FROM produtos WHERE status = 'Vendido'";

        try {
            conn = new conectaDAO().connectDB();
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();

            while (resultset.next()) {
                ProdutosDTO p = new ProdutosDTO();
                // não precisa preencher os campos se o DTO não estiver definido
                vendidos.add(p);
            }

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro);
        }

        return vendidos;
    }
}

