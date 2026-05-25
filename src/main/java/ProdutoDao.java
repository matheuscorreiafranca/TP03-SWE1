/*
 * Disciplina: CBTSWE1 - ADS 571
 * Professor: Wellington Tuler Moraes
 * Trabalho Pratico 01 - CRUD de Produtos
 * Dupla: Matheus Correia de Franca e Davi Leite Coelho
 */

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProdutoDao {
    private static final Map<Integer, Produto> BANCO = new LinkedHashMap<Integer, Produto>();
    private static int proximoId = 1;

    static {
        save(criarProduto("Arroz tipo 1", 10, "Pacote de 5kg para reposicao do estoque mensal.", 80.0, 28.90));
        save(criarProduto("Cafe torrado", 20, "Cafe tradicional em embalagem de 500g.", 45.0, 18.50));
        save(criarProduto("Detergente neutro", 24, "Unidade de 500ml para compras recorrentes.", 120.0, 2.99));
    }

    private ProdutoDao() {
    }

    public static synchronized int save(Produto produto) {
        produto.setId(proximoId++);
        BANCO.put(produto.getId(), produto);
        return 1;
    }

    public static synchronized int update(Produto produto) {
        if (!BANCO.containsKey(produto.getId())) {
            return 0;
        }

        BANCO.put(produto.getId(), produto);
        return 1;
    }

    public static synchronized int delete(int id) {
        return BANCO.remove(id) == null ? 0 : 1;
    }

    public static synchronized Produto getProdutoById(int id) {
        Produto produto = BANCO.get(id);
        if (produto == null) {
            return null;
        }

        return copiar(produto);
    }

    public static synchronized Produto findById(int id) {
        return getProdutoById(id);
    }

    public static synchronized List<Produto> getAllProdutos() {
        List<Produto> produtos = new ArrayList<Produto>();
        for (Produto produto : BANCO.values()) {
            produtos.add(copiar(produto));
        }
        return produtos;
    }

    private static Produto criarProduto(String nome, int unidadeCompra, String descricao, double qtdPrevistoMes, double precoMaxComprado) {
        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setUnidadeCompra(unidadeCompra);
        produto.setDescricao(descricao);
        produto.setQtdPrevistoMes(qtdPrevistoMes);
        produto.setPrecoMaxComprado(precoMaxComprado);
        return produto;
    }

    private static Produto copiar(Produto original) {
        Produto copia = new Produto();
        copia.setId(original.getId());
        copia.setNome(original.getNome());
        copia.setUnidadeCompra(original.getUnidadeCompra());
        copia.setDescricao(original.getDescricao());
        copia.setQtdPrevistoMes(original.getQtdPrevistoMes());
        copia.setPrecoMaxComprado(original.getPrecoMaxComprado());
        return copia;
    }
}
