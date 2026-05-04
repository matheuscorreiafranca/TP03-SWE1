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

@WebServlet("/SaveServlet")
public class SaveServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        Produto produto = new Produto();
        produto.setNome(request.getParameter("nome"));
        produto.setUnidadeCompra(parseInt(request.getParameter("unidadeCompra")));
        produto.setDescricao(request.getParameter("descricao"));
        produto.setQtdPrevistoMes(parseDouble(request.getParameter("qtdPrevistoMes")));
        produto.setPrecoMaxComprado(parseDouble(request.getParameter("precoMaxComprado")));

        int status = ProdutoDao.save(produto);
        if (status > 0) {
            response.sendRedirect("ViewServlet?status=saved");
            return;
        }

        PrintWriter out = response.getWriter();
        UiRenderer.printDocumentStart(out, "Falha no Cadastro");
        UiRenderer.printMessage(out, "Falha no processo", "Nao foi possivel salvar o produto.",
                "Revise os dados informados e tente novamente.", "index.html", "Voltar ao formulario");
        UiRenderer.printFooter(out);
        out.close();
    }

    private int parseInt(String value) {
        return Integer.parseInt(value);
    }

    private double parseDouble(String value) {
        return Double.parseDouble(value.replace(",", "."));
    }
}
