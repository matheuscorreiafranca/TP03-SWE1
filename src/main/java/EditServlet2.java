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

@WebServlet("/EditServlet2")
public class EditServlet2 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        Produto produto = new Produto();
        produto.setId(Integer.parseInt(request.getParameter("id")));
        produto.setNome(request.getParameter("nome"));
        produto.setUnidadeCompra(Integer.parseInt(request.getParameter("unidadeCompra")));
        produto.setDescricao(request.getParameter("descricao"));
        produto.setQtdPrevistoMes(Double.parseDouble(request.getParameter("qtdPrevistoMes").replace(",", ".")));
        produto.setPrecoMaxComprado(Double.parseDouble(request.getParameter("precoMaxComprado").replace(",", ".")));

        int status = ProdutoDao.update(produto);
        if (status > 0) {
            response.sendRedirect("ViewServlet?status=updated");
            return;
        }

        PrintWriter out = response.getWriter();
        UiRenderer.printDocumentStart(out, "Falha na Atualizacao");
        UiRenderer.printMessage(out, "Atualizacao interrompida", "Os dados nao puderam ser atualizados.",
                "O produto informado nao foi localizado no banco em memoria.", "ViewServlet", "Ir para a listagem");
        UiRenderer.printFooter(out);
        out.close();
    }
}
