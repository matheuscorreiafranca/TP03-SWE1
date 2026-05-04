/*
 * Disciplina: CBTSWE1 - ADS 571
 * Professor: Wellington Tuler Moraes
 * Trabalho Pratico 01 - CRUD de Produtos
 * Dupla: Matheus Correia de Franca e Davi Leite Coelho
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ViewServlet")
public class ViewServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        List<Produto> produtos = ProdutoDao.getAllProdutos();
        String toast = getToast(request.getParameter("status"));

        UiRenderer.printDocumentStart(out, "Lista de Produtos");
        out.println("<main class='page'>");
        out.println("<nav class='menu'><a href='index.html'>Cadastro</a><a href='ViewServlet'>Listagem</a><a href='creditos.html'>Creditos</a></nav>");
        out.println("<section class='hero'>");
        out.println("<article class='hero-card'>");
        out.println("<span class='eyebrow'>Tabela de produtos</span>");
        out.println("<h1>Produtos cadastrados no banco em memoria.</h1>");
        out.println("<p class='hero-copy'>Consulte, edite e exclua os registros da entidade PRODUTOS sem depender de MySQL ou outro banco externo.</p>");
        out.println("<div class='actions'><a href='index.html#formulario' class='btn btn-primary'>Cadastrar produto</a><a href='#tabela' class='btn btn-secondary'>Ir para tabela</a></div>");
        if (!toast.isEmpty()) {
            out.println("<div class='toast'>" + UiRenderer.escape(toast) + "</div>");
        }
        out.println("</article>");
        out.println("<aside class='hero-aside'>");
        out.println("<div class='stat-card'><div class='stat-label'>Total</div><div class='stat-value'>" + produtos.size() + "</div><p class='stat-note'>Registros ativos enquanto a aplicacao estiver em execucao.</p></div>");
        out.println("</aside>");
        out.println("</section>");
        out.println("<section class='panel' id='tabela'>");
        out.println("<div class='panel-header'><div><span class='eyebrow'>CRUD completo</span><h2 class='panel-title'>Gestao de produtos</h2></div><p>A tabela abaixo representa a entidade PRODUTOS pedida no exercicio.</p></div>");

        if (produtos.isEmpty()) {
            out.println("<div class='table-wrap'><div class='empty-state'><h3>Nenhum produto cadastrado.</h3><p>Use o formulario inicial para incluir o primeiro produto.</p><a href='index.html#formulario' class='btn btn-primary'>Novo produto</a></div></div>");
        } else {
            out.println("<div class='table-wrap'>");
            out.println("<table>");
            out.println("<thead><tr><th>ID</th><th>Nome</th><th>Unid.</th><th>Descricao</th><th>Qtd. mes</th><th>Preco max.</th><th>Acoes</th></tr></thead>");
            out.println("<tbody>");
            for (Produto produto : produtos) {
                String nome = UiRenderer.escape(produto.getNome());
                out.println("<tr>");
                out.println("<td><span class='pill'>#" + produto.getId() + "</span></td>");
                out.println("<td>" + nome + "</td>");
                out.println("<td>" + produto.getUnidadeCompra() + "</td>");
                out.println("<td>" + UiRenderer.escape(produto.getDescricao()) + "</td>");
                out.println("<td>" + UiRenderer.format(produto.getQtdPrevistoMes()) + "</td>");
                out.println("<td>R$ " + UiRenderer.format(produto.getPrecoMaxComprado()) + "</td>");
                out.println("<td><div class='secondary-actions'><a href='EditServlet?id=" + produto.getId() + "' class='btn btn-secondary'>Editar</a><a href='DeleteServlet?id=" + produto.getId() + "' class='btn btn-ghost' onclick='return confirm(\"Excluir este produto?\")'>Excluir</a></div></td>");
                out.println("</tr>");
            }
            out.println("</tbody>");
            out.println("</table>");
            out.println("</div>");
        }

        out.println("</section>");
        out.println("</main>");
        UiRenderer.printFooter(out);
        out.close();
    }

    private String getToast(String status) {
        if ("saved".equals(status)) {
            return "Produto cadastrado com sucesso.";
        }
        if ("updated".equals(status)) {
            return "Produto atualizado com sucesso.";
        }
        if ("deleted".equals(status)) {
            return "Produto removido com sucesso.";
        }
        return "";
    }
}
