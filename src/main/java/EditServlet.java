/*
 * Disciplina: CBTSWE1 - ADS 571
 * Professor: Wellington Tuler Moraes
 * Trabalho Pratico 01 - CRUD de Produtos
 * Dupla: Matheus Correia de Franca e Davi Leite Coelho
 */

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/EditServlet")
public class EditServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        int id = Integer.parseInt(request.getParameter("id"));
        Produto produto = ProdutoDao.getProdutoById(id);

        if (produto == null) {
            UiRenderer.printDocumentStart(out, "Produto nao encontrado");
            UiRenderer.printMessage(out, "Registro inexistente", "Produto nao encontrado.",
                    "O item selecionado pode ter sido removido da memoria da aplicacao.", "ViewServlet", "Voltar para a listagem");
            UiRenderer.printFooter(out);
            out.close();
            return;
        }

        UiRenderer.printDocumentStart(out, "Editar Produto");
        out.println("<main class='page'>");
        out.println("<nav class='menu'><a href='index.html'>Cadastro</a><a href='ViewServlet'>Listagem</a><a href='creditos.html'>Creditos</a></nav>");
        out.println("<section class='hero'>");
        out.println("<article class='hero-card'>");
        out.println("<span class='eyebrow'>Edicao de produto</span>");
        out.println("<h1>Ajuste o cadastro do produto #" + produto.getId() + ".</h1>");
        out.println("<p class='hero-copy'>Atualize nome, unidade de compra, descricao, previsao mensal e preco maximo comprado.</p>");
        out.println("</article>");
        out.println("<aside class='hero-aside'>");
        out.println("<div class='stat-card'><div class='stat-label'>Produto atual</div><div class='stat-value'>" + UiRenderer.escape(produto.getNome()) + "</div><p class='stat-note'>As alteracoes ficam disponiveis imediatamente no banco em memoria.</p></div>");
        out.println("</aside>");
        out.println("</section>");
        out.println("<section class='panel'>");
        out.println("<div class='panel-header'><div><span class='eyebrow'>Formulario</span><h2 class='panel-title'>Editar produto</h2></div><p>Confira os campos antes de salvar.</p></div>");
        out.println("<form action='EditServlet2' method='post'>");
        out.println("<input type='hidden' name='id' value='" + produto.getId() + "'>");
        UiRenderer.printProdutoFields(out, produto);
        out.println("<div class='form-actions'><button type='submit' class='btn btn-primary'>Salvar alteracoes</button><a href='ViewServlet' class='btn btn-secondary'>Cancelar</a></div>");
        out.println("</form>");
        out.println("</section>");
        out.println("</main>");
        UiRenderer.printFooter(out);
        out.close();
    }
}
