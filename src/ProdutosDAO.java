import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    
  public void cadastrarProduto(ProdutosDTO produto) {

    String sql = "INSERT INTO produtos (nome, preco, status) VALUES (?, ?, ?)";

    try {
        conn = new conectaDAO().connectDB();
        prep = conn.prepareStatement(sql);

        prep.setString(1, produto.getNome());
        prep.setDouble(2, produto.getValor());
        prep.setString(3, produto.getStatus());

        prep.executeUpdate();

        JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");

    } catch (Exception erro) {
        JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + erro.getMessage());
    }
}

    
   public ArrayList<ProdutosDTO> listarProdutos() {

    ArrayList<ProdutosDTO> lista = new ArrayList<>();
    String sql = "SELECT * FROM produtos";

    try {
        conn = new conectaDAO().connectDB();
        prep = conn.prepareStatement(sql);
        resultset = prep.executeQuery();

        while (resultset.next()) {
            ProdutosDTO p = new ProdutosDTO();
            p.setId(resultset.getInt("id"));
            p.setNome(resultset.getString("nome"));
            p.setValor(resultset.getDouble("preco")); // ou valor, conforme o banco
            p.setStatus(resultset.getString("status"));
            lista.add(p);
        }

    } catch (Exception erro) {
        JOptionPane.showMessageDialog(null, erro.getMessage());
    }

    return lista;
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
                
                // ADICIONADO: preencher os campos do DTO
                p.setId(resultset.getInt("id"));
                p.setNome(resultset.getString("nome"));
                p.setValor(resultset.getDouble("preco"));
                p.setStatus(resultset.getString("status"));
                
                vendidos.add(p);
            }

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro);
        }

        return vendidos;
    }
    public void venderProduto(int idProduto) {

    String select = "SELECT * FROM produtos WHERE id = ?";
    String insert = "INSERT INTO ProdutosVendidos (nome, preco, status) VALUES (?, ?, ?)";
    String update = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";

    try {
        conn = new conectaDAO().connectDB();

        // 1️⃣ Buscar produto
        prep = conn.prepareStatement(select);
        prep.setInt(1, idProduto);
        resultset = prep.executeQuery();

        if (resultset.next()) {

            String nome = resultset.getString("nome");
            double preco = resultset.getDouble("preco");

            // 2️⃣ Inserir em ProdutosVendidos
            prep = conn.prepareStatement(insert);
            prep.setString(1, nome);
            prep.setDouble(2, preco);
            prep.setString(3, "Vendido");
            prep.executeUpdate();

            // 3️⃣ Atualizar status no produtos
            prep = conn.prepareStatement(update);
            prep.setInt(1, idProduto);
            prep.executeUpdate();

            JOptionPane.showMessageDialog(null, "Produto vendido com sucesso!");
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }
}

}
