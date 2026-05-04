/*
 * Disciplina: CBTSWE1 - ADS 571
 * Professor: Wellington Tuler Moraes
 * Trabalho Pratico 01 - CRUD de Produtos
 * Dupla: Matheus Correia de Franca e Davi Leite Coelho
 */

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class UiRenderer {
    private static final DecimalFormat DECIMAL = new DecimalFormat("0.00", DecimalFormatSymbols.getInstance(Locale.US));

    private UiRenderer() {
    }

    public static void printDocumentStart(PrintWriter out, String title) {
        out.println("<!DOCTYPE html>");
        out.println("<html lang='pt-BR'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>" + escape(title) + "</title>");
        out.println("<link rel='stylesheet' href='style.css'>");
        out.println("</head>");
        out.println("<body>");
    }

    public static void printProdutoFields(PrintWriter out, Produto produto) {
        out.println("<div class='grid-2'>");
        out.println("<div class='field'><label for='nome'>Nome</label><input type='text' id='nome' name='nome' value='" + escape(produto.getNome()) + "' required maxlength='80'></div>");
        out.println("<div class='field'><label for='unidadeCompra'>Unidade de compra</label><input type='number' id='unidadeCompra' name='unidadeCompra' min='1' step='1' value='" + produto.getUnidadeCompra() + "' required></div>");
        out.println("<div class='field'><label for='qtdPrevistoMes'>Qtd. previsto mes</label><input type='number' id='qtdPrevistoMes' name='qtdPrevistoMes' min='0' step='0.01' value='" + format(produto.getQtdPrevistoMes()) + "' required></div>");
        out.println("<div class='field'><label for='precoMaxComprado'>Preco max. comprado</label><input type='number' id='precoMaxComprado' name='precoMaxComprado' min='0' step='0.01' value='" + format(produto.getPrecoMaxComprado()) + "' required></div>");
        out.println("<div class='field full'><label for='descricao'>Descricao</label><textarea id='descricao' name='descricao' rows='4' required maxlength='240'>" + escape(produto.getDescricao()) + "</textarea></div>");
        out.println("</div>");
    }

    public static void printMessage(PrintWriter out, String eyebrow, String title, String text, String href, String label) {
        out.println("<main class='page message-layout'>");
        out.println("<section class='message-card'>");
        out.println("<span class='eyebrow'>" + escape(eyebrow) + "</span>");
        out.println("<h1>" + escape(title) + "</h1>");
        out.println("<p>" + escape(text) + "</p>");
        out.println("<div class='form-actions centered'><a href='" + escape(href) + "' class='btn btn-primary'>" + escape(label) + "</a></div>");
        out.println("</section>");
        out.println("</main>");
    }

    public static void printFooter(PrintWriter out) {
        out.println("<footer class='footer'>Projeto realizado por Matheus Correia de Franca e Davi Leite Coelho para a materia de Sistemas Web 1.</footer>");
        out.println("</body></html>");
    }

    public static String format(double value) {
        return DECIMAL.format(value);
    }

    public static String escape(String value) {
        if (value == null) {
            return "";
        }

        return value
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&#39;");
    }
}
