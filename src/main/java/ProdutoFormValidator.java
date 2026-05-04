// Matheus Correia de Franca, Davi Leite Coelho
import jakarta.servlet.http.HttpServletRequest;

/**
 * Validador centralizado para formulário de produtos
 * Fornece métodos para validar e criar produtos a partir de requisições HTTP
 */
public class ProdutoFormValidator {
    
    /**
     * Valida e cria um novo produto a partir dos parâmetros da requisição
     * @param request HttpServletRequest com dados do formulário
     * @return Produto validado
     * @throws IllegalArgumentException se algum campo for inválido
     */
    public static Produto criarProduto(HttpServletRequest request) {
        Produto produto = new Produto();
        produto.setNome(obterTextoObrigatorio(request, "nome", "Nome"));
        produto.setUnidadeCompra(obterInteiroPositivo(request, "unidadeCompra", "Unidade de compra"));
        produto.setDescricao(obterTextoObrigatorio(request, "descricao", "Descrição"));
        produto.setQtdPrevistoMes(obterDecimalNaoNegativo(request, "qtdPrevistoMes", "Quantidade prevista do mês"));
        produto.setPrecoMaxComprado(obterDecimalNaoNegativo(request, "precoMaxComprado", "Preço máximo comprado"));
        return produto;
    }
    
    /**
     * Valida e cria um produto incluindo seu ID (para atualizações)
     * @param request HttpServletRequest com dados do formulário
     * @return Produto validado com ID
     * @throws IllegalArgumentException se algum campo for inválido
     */
    public static Produto criarProdutoComId(HttpServletRequest request) {
        Produto produto = criarProduto(request);
        produto.setId(obterId(request));
        return produto;
    }
    
    /**
     * Extrai e valida o ID do produto da requisição
     * @param request HttpServletRequest
     * @return ID validado do produto
     * @throws IllegalArgumentException se ID inválido ou ausente
     */
    public static int obterId(HttpServletRequest request) {
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            throw new IllegalArgumentException("ID do produto é obrigatorio.");
        }
        try {
            int id = Integer.parseInt(idStr);
            if (id <= 0) {
                throw new IllegalArgumentException("ID deve ser maior que zero.");
            }
            return id;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID deve ser um numero valido.");
        }
    }
    
    /**
     * Obtém e valida um texto obrigatório
     * @param request HttpServletRequest
     * @param paramName Nome do parâmetro
     * @param fieldName Nome amigável do campo
     * @return Texto validado
     * @throws IllegalArgumentException se vazio ou nulo
     */
    private static String obterTextoObrigatorio(HttpServletRequest request, String paramName, String fieldName) {
        String value = request.getParameter(paramName);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " e obrigatorio.");
        }
        return value.trim();
    }
    
    /**
     * Obtém e valida um inteiro positivo
     * @param request HttpServletRequest
     * @param paramName Nome do parâmetro
     * @param fieldName Nome amigável do campo
     * @return Inteiro validado
     * @throws IllegalArgumentException se inválido ou não positivo
     */
    private static int obterInteiroPositivo(HttpServletRequest request, String paramName, String fieldName) {
        String value = request.getParameter(paramName);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " e obrigatorio.");
        }
        try {
            int intValue = Integer.parseInt(value);
            if (intValue <= 0) {
                throw new IllegalArgumentException(fieldName + " deve ser maior que zero.");
            }
            return intValue;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " deve ser um numero inteiro valido.");
        }
    }
    
    /**
     * Obtém e valida um número decimal não negativo
     * @param request HttpServletRequest
     * @param paramName Nome do parâmetro
     * @param fieldName Nome amigável do campo
     * @return Double validado
     * @throws IllegalArgumentException se inválido ou negativo
     */
    private static double obterDecimalNaoNegativo(HttpServletRequest request, String paramName, String fieldName) {
        String value = request.getParameter(paramName);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " e obrigatorio.");
        }
        try {
            double doubleValue = Double.parseDouble(value);
            if (doubleValue < 0) {
                throw new IllegalArgumentException(fieldName + " nao pode ser negativo.");
            }
            return doubleValue;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " deve ser um numero decimal valido.");
        }
    }
}
