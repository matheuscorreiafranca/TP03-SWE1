// Matheus Correia de Franca, Davi Leite Coelho
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet para exibir detalhes completos de um produto
 */
@WebServlet("/DetalhesServlet")
public class DetalhesServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            int id = ProdutoFormValidator.obterId(request);
            Produto produto = ProdutoDao.findById(id);
            
            if (produto == null) {
                UiRenderer.printMessage(out, "Erro", "Produto não encontrado", 
                    "O produto com ID " + id + " não existe.",
                    "ViewServlet", "Voltar para lista");
                return;
            }
            
            UiRenderer.printDocumentStart(out, "Detalhes do Produto");
            
            out.println("    <nav class=\"menu\">");
            out.println("        <a href=\"index.html\">Inicio</a>");
            out.println("        <a href=\"ViewServlet\">Produtos</a>");
            out.println("        <a href=\"creditos\">Creditos</a>");
            out.println("    </nav>");
            out.println("");
            
            out.println("    <main class=\"page\">");
            out.println("        <section class=\"hero\">");
            out.println("            <article class=\"hero-card\">");
            out.println("                <span class=\"eyebrow\">Detalhes do Produto</span>");
            out.println("                <h1>" + UiRenderer.escape(produto.getNome()) + "</h1>");
            out.println("            </article>");
            out.println("        </section>");
            out.println("");
            
            UiRenderer.printProdutoDetalhes(out, produto);
            
            out.println("        <section class=\"grid-2\">");
            out.println("            <a href=\"EditServlet?id=" + produto.getId() + "\" class=\"btn-primary\">✏️ Editar</a>");
            out.println("            <a href=\"DeleteServlet?id=" + produto.getId() + "\" class=\"btn-ghost\">🗑️ Deletar</a>");
            out.println("        </section>");
            out.println("");
            out.println("        <section class=\"grid-2\">");
            out.println("            <a href=\"ViewServlet\" class=\"btn-secondary\">← Voltar</a>");
            out.println("        </section>");
            out.println("    </main>");
            
            UiRenderer.printDocumentEnd(out);
            
        } catch (IllegalArgumentException exception) {
            UiRenderer.printMessage(out, "Erro", "ID inválido", 
                exception.getMessage(), "ViewServlet", "Voltar");
        }
    }
}
