/*
 * Disciplina: CBTSWE1 - ADS 571
 * Professor: Wellington Tuler Moraes
 * Trabalho Pratico 01 - CRUD de Produtos
 * Dupla: Matheus Correia de Franca e Davi Leite Coelho
 */

public class Produto {
    private int id;
    private String nome;
    private int unidadeCompra;
    private String descricao;
    private double qtdPrevistoMes;
    private double precoMaxComprado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getUnidadeCompra() {
        return unidadeCompra;
    }

    public void setUnidadeCompra(int unidadeCompra) {
        this.unidadeCompra = unidadeCompra;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getQtdPrevistoMes() {
        return qtdPrevistoMes;
    }

    public void setQtdPrevistoMes(double qtdPrevistoMes) {
        this.qtdPrevistoMes = qtdPrevistoMes;
    }

    public double getPrecoMaxComprado() {
        return precoMaxComprado;
    }

    public void setPrecoMaxComprado(double precoMaxComprado) {
        this.precoMaxComprado = precoMaxComprado;
    }
}
