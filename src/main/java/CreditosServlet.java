// Matheus Correia de Franca, Davi Leite Coelho
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet para exibir página de créditos da dupla
 */
@WebServlet("/creditos")
public class CreditosServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        UiRenderer.printDocumentStart(out, "Créditos");
        
        out.println("    <nav class=\"menu\">");
        out.println("        <a href=\"index.html\">Inicio</a>");
        out.println("        <a href=\"ViewServlet\">Produtos</a>");
        out.println("        <a href=\"creditos\">Creditos</a>");
        out.println("    </nav>");
        out.println("");
        out.println("    <main class=\"page\">");
        out.println("        <section class=\"hero\">");
        out.println("            <article class=\"hero-card\">");
        out.println("                <span class=\"eyebrow\">Equipe de Desenvolvimento</span>");
        out.println("                <h1>Conheça a Dupla</h1>");
        out.println("                <p>Projeto CRUD TP02-CBTSWE1 do IFSP Campus Cubatão</p>");
        out.println("            </article>");
        out.println("        </section>");
        out.println("");
        out.println("        <section class=\"detail-grid\">");
        out.println("            <article class=\"detail-card\">");
        out.println("                <div class=\"detail-label\">Desenvolvedor 1</div>");
        out.println("                <div class=\"detail-value\">Matheus Correia de Franca</div>");
        out.println("                <div class=\"detail-text\">Desenvolvedor Backend e Frontend</div>");
        out.println("            </article>");
        out.println("            <article class=\"detail-card\">");
        out.println("                <div class=\"detail-label\">Desenvolvedor 2</div>");
        out.println("                <div class=\"detail-value\">Davi Leite Coelho</div>");
        out.println("                <div class=\"detail-text\">Desenvolvedor Backend e Frontend</div>");
        out.println("            </article>");
        out.println("        </section>");
        out.println("");
        out.println("        <section class=\"detail-grid\">");
        out.println("            <article class=\"detail-card\">");
        out.println("                <div class=\"detail-label\">Curso</div>");
        out.println("                <div class=\"detail-value\">Análise e Desenvolvimento de Sistemas</div>");
        out.println("                <div class=\"detail-text\">ADS 571 - CBTSWE1</div>");
        out.println("            </article>");
        out.println("            <article class=\"detail-card\">");
        out.println("                <div class=\"detail-label\">Instituição</div>");
        out.println("                <div class=\"detail-value\">IFSP Campus Cubatão</div>");
        out.println("                <div class=\"detail-text\">Instituto Federal de Educação, Ciência e Tecnologia de São Paulo</div>");
        out.println("            </article>");
        out.println("        </section>");
        out.println("");
        out.println("        <section class=\"detail-grid\">");
        out.println("            <article class=\"detail-card\">");
        out.println("                <div class=\"detail-label\">Projeto</div>");
        out.println("                <div class=\"detail-value\">TP02-CBTSWE1</div>");
        out.println("                <div class=\"detail-text\">CRUD Completo com Banco de Dados em Memória</div>");
        out.println("            </article>");
        out.println("            <article class=\"detail-card\">");
        out.println("                <div class=\"detail-label\">Tecnologias</div>");
        out.println("                <div class=\"detail-value\">Java 17 + Servlet 5.0</div>");
        out.println("                <div class=\"detail-text\">Tomcat 10.1.16 + HTML/CSS Moderno</div>");
        out.println("            </article>");
        out.println("        </section>");
        out.println("");
        out.println("        <section class=\"detail-grid\">");
        out.println("            <article class=\"detail-card\" style=\"grid-column: span 2;\">");
        out.println("                <div class=\"detail-label\">Repositório</div>");
        out.println("                <div class=\"detail-value\">GitHub</div>");
        out.println("                <div class=\"detail-text\">");
        out.println("                    <a href=\"https://github.com/matheuscorreiafranca/TP02-CBTSWE1\" target=\"_blank\" style=\"color: var(--brand);\">github.com/matheuscorreiafranca/TP02-CBTSWE1</a>");
        out.println("                </div>");
        out.println("            </article>");
        out.println("        </section>");
        out.println("");
        out.println("    </main>");
        
        UiRenderer.printDocumentEnd(out);
    }
}
