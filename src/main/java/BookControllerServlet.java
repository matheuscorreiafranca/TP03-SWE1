/*
 * Disciplina: CBTSWE1 - ADS 571
 * Professor: Wellington Tuler Moraes
 * Trabalho Pratico 02 - CRUD de Livros
 * Dupla: Matheus Correia de Franca e Davi Leite Coelho
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/list", "/new", "/insert", "/edit", "/update", "/delete"})
public class BookControllerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getServletPath();
        try {
            if ("/new".equals(action)) {
                showForm(response, null);
            } else if ("/insert".equals(action)) {
                insertBook(request, response);
            } else if ("/edit".equals(action)) {
                Book book = BookDao.findById(parseId(request));
                if (book == null) {
                    printError(response, "Livro nao encontrado", "O livro solicitado nao existe mais no catalogo.");
                    return;
                }
                showForm(response, book);
            } else if ("/update".equals(action)) {
                updateBook(request, response);
            } else if ("/delete".equals(action)) {
                BookDao.delete(parseId(request));
                response.sendRedirect("list?status=deleted");
            } else {
                listBooks(request, response);
            }
        } catch (IllegalArgumentException exception) {
            printError(response, "Dados invalidos", exception.getMessage());
        } catch (SQLException exception) {
            printError(response, "Erro no banco de dados", exception.getMessage());
        }
    }

    private void listBooks(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        List<Book> books = BookDao.findAll();
        PrintWriter out = response.getWriter();

        UiRenderer.printDocumentStart(out, "Bookstore - Catalog");
        printMenu(out, "list");
        out.println("<main class='app-shell page-shell'>");
        out.println("<section class='hero hero-catalog' aria-labelledby='catalog-title'>");
        out.println("<div class='hero-content'>");
        out.println("<span class='eyebrow'>Premium digital library</span>");
        out.println("<h1 id='catalog-title'>Books Management</h1>");
        out.println("<p class='hero-copy'>Organize o acervo, revise precos e mantenha o catalogo pronto para apresentacao em uma experiencia refinada de livraria digital.</p>");
        out.println("<div class='actions'><a href='new' class='btn btn-primary'>Add New Book</a><a href='#catalog' class='btn btn-secondary'>Explore catalog</a></div>");
        String toast = getToast(request.getParameter("status"));
        if (!toast.isEmpty()) {
            out.println("<div class='toast success' role='status'><span class='status-dot'></span>" + UiRenderer.escape(toast) + "</div>");
        }
        out.println("</div>");
        out.println("<aside class='hero-metrics' aria-label='Resumo do catalogo'>");
        out.println("<div class='metric-card'><span>Total books</span><strong>" + books.size() + "</strong><small>Registros no MySQL</small></div>");
        out.println("<div class='metric-card accent'><span>Storage</span><strong>JDBC</strong><small>Tomcat + MySQL</small></div>");
        out.println("</aside>");
        out.println("</section>");

        out.println("<section class='catalog-panel' id='catalog' aria-labelledby='catalog-heading'>");
        out.println("<div class='section-header'>");
        out.println("<div><span class='eyebrow'>Curated shelf</span><h2 id='catalog-heading'>Catalogo inteligente</h2><p>Busque, ordene e acesse acoes sem perder contexto.</p></div>");
        out.println("<div class='catalog-tools' role='search'>");
        out.println("<label class='sr-only' for='bookSearch'>Buscar livro</label>");
        out.println("<input id='bookSearch' type='search' placeholder='Buscar por titulo ou autor' autocomplete='off' data-book-search>");
        out.println("<label class='sr-only' for='bookSort'>Ordenar livros</label>");
        out.println("<select id='bookSort' data-book-sort aria-label='Ordenar livros'><option value='recent'>Recentes</option><option value='title'>Titulo A-Z</option><option value='author'>Autor A-Z</option><option value='priceAsc'>Menor preco</option><option value='priceDesc'>Maior preco</option></select>");
        out.println("</div>");
        out.println("</div>");

        if (books.isEmpty()) {
            out.println("<div class='empty-state elevated'>");
            out.println("<div class='empty-cover' aria-hidden='true'>B</div>");
            out.println("<h3>Sua prateleira ainda esta vazia.</h3>");
            out.println("<p>Cadastre o primeiro livro e transforme esta area em uma vitrine editorial.</p>");
            out.println("<a href='new' class='btn btn-primary'>Add New Book</a>");
            out.println("</div>");
        } else {
            out.println("<div class='book-grid catalog-grid' data-book-list>");
            for (Book book : books) {
                printBookCard(out, book);
            }
            out.println("</div>");
            out.println("<div class='empty-state search-empty' data-empty-search hidden><h3>Nenhum livro encontrado.</h3><p>Tente buscar por outro titulo ou autor.</p></div>");
        }

        out.println("</section>");
        out.println("</main>");
        UiRenderer.printFooter(out);
    }

    private void printBookCard(PrintWriter out, Book book) {
        String title = UiRenderer.escape(book.getTitle());
        String author = UiRenderer.escape(book.getAuthor());
        String initials = getInitials(book.getTitle());
        String tone = "tone-" + ((book.getId() % 5) + 1);

        out.println("<article class='book-card' data-book-card data-title='" + title.toLowerCase() + "' data-author='" + author.toLowerCase() + "' data-price='" + UiRenderer.format(book.getPrice()) + "'>");
        out.println("<div class='book-cover " + tone + "' aria-hidden='true'><span>" + initials + "</span></div>");
        out.println("<div class='book-content'>");
        out.println("<div class='book-meta'><span class='badge pill'>#" + book.getId() + "</span><span>Catalog item</span></div>");
        out.println("<h3>" + title + "</h3>");
        out.println("<p class='book-author'>por " + author + "</p>");
        out.println("<div class='book-footer'><strong class='price'>R$ " + UiRenderer.format(book.getPrice()) + "</strong><div class='card-actions'><a href='edit?id=" + book.getId() + "' class='btn btn-secondary btn-small' aria-label='Editar " + title + "'>Edit</a><a href='delete?id=" + book.getId() + "' class='btn btn-danger btn-small' aria-label='Excluir " + title + "' onclick='return confirm(\"Delete this book?\")'>Delete</a></div></div>");
        out.println("</div>");
        out.println("</article>");
    }

    private void showForm(HttpServletResponse response, Book book) throws IOException {
        PrintWriter out = response.getWriter();
        boolean editing = book != null;

        UiRenderer.printDocumentStart(out, editing ? "Edit Book" : "Add New Book");
        printMenu(out, "new");
        out.println("<main class='app-shell page-shell'>");
        out.println("<section class='hero hero-form' aria-labelledby='form-title'>");
        out.println("<div class='hero-content'>");
        out.println("<span class='eyebrow'>" + (editing ? "Edit workflow" : "Create workflow") + "</span>");
        out.println("<h1 id='form-title'>" + (editing ? "Ajuste este titulo com precisao." : "Cadastre um novo livro com cara de vitrine.") + "</h1>");
        out.println("<p class='hero-copy'>Um fluxo enxuto para publicar titulo, autoria e preco com clareza editorial e validacao acessivel.</p>");
        out.println("<div class='flow-steps' aria-label='Fluxo do formulario'><span class='active'>Details</span><span>Review</span><span>Save</span></div>");
        out.println("</div>");
        out.println("<aside class='editor-note'><span>Database</span><strong>book</strong><p>Campos preservados: title, author e price.</p></aside>");
        out.println("</section>");

        out.println("<section class='editor-layout'>");
        out.println("<article class='preview-card' aria-label='Previa visual do livro'>");
        out.println("<div class='book-cover hero-cover tone-3'><span data-preview-initials>BK</span></div>");
        out.println("<span class='eyebrow'>Live preview</span>");
        out.println("<h2 data-preview-title>" + UiRenderer.escape(editing ? book.getTitle() : "Titulo do livro") + "</h2>");
        out.println("<p data-preview-author>" + UiRenderer.escape(editing ? "por " + book.getAuthor() : "por Autor") + "</p>");
        out.println("<strong data-preview-price>R$ " + UiRenderer.format(editing ? book.getPrice() : 0) + "</strong>");
        out.println("</article>");

        out.println("<section class='form-card' aria-labelledby='book-form-heading'>");
        out.println("<div class='section-header compact'><div><span class='eyebrow'>Book details</span><h2 id='book-form-heading'>" + (editing ? "Edit Book" : "Add New Book") + "</h2><p>Todos os campos sao obrigatorios.</p></div></div>");
        out.println("<form action='" + (editing ? "update" : "insert") + "' method='post' data-book-form novalidate>");
        if (editing) {
            out.println("<input type='hidden' name='id' value='" + book.getId() + "'>");
        }
        printBookFields(out, editing ? book : new Book());
        out.println("<div class='form-actions'><button type='submit' class='btn btn-primary'>Salvar livro</button><a href='list' class='btn btn-secondary'>Cancelar</a></div>");
        out.println("</form>");
        out.println("</section>");
        out.println("</section>");
        out.println("</main>");
        UiRenderer.printFooter(out);
    }

    private void insertBook(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        BookDao.save(readBook(request, 0));
        response.sendRedirect("list?status=saved");
    }

    private void updateBook(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int status = BookDao.update(readBook(request, parseId(request)));
        response.sendRedirect("list?status=" + (status > 0 ? "updated" : "missing"));
    }

    private Book readBook(HttpServletRequest request, int id) {
        String title = require(request, "title", "Title");
        String author = require(request, "author", "Author");
        double price = parsePrice(require(request, "price", "Price"));
        if (price < 0) {
            throw new IllegalArgumentException("Price nao pode ser negativo.");
        }
        return new Book(id, title, author, price);
    }

    private void printBookFields(PrintWriter out, Book book) {
        out.println("<div class='form-grid'>");
        out.println("<div class='field full'><label for='title'>Titulo do livro</label><input class='input' type='text' id='title' name='title' value='" + UiRenderer.escape(book.getTitle()) + "' placeholder='Ex.: Clean Code' required maxlength='128' data-preview-source='title'><span class='field-help'>Use um titulo claro. Ele precisa ser unico no catalogo.</span><span class='field-error' data-error-for='title'>Informe o titulo do livro.</span></div>");
        out.println("<div class='field'><label for='author'>Autor</label><input class='input' type='text' id='author' name='author' value='" + UiRenderer.escape(book.getAuthor()) + "' placeholder='Ex.: Robert C. Martin' required maxlength='45' data-preview-source='author'><span class='field-help'>Nome principal exibido nos cards.</span><span class='field-error' data-error-for='author'>Informe o autor.</span></div>");
        out.println("<div class='field'><label for='price'>Preco</label><div class='currency-input'><span>R$</span><input class='input' type='number' id='price' name='price' min='0' step='0.01' value='" + UiRenderer.format(book.getPrice()) + "' placeholder='0.00' required data-preview-source='price'></div><span class='field-help'>Use ponto ou virgula para centavos.</span><span class='field-error' data-error-for='price'>Informe um preco valido.</span></div>");
        out.println("</div>");
    }

    private void printMenu(PrintWriter out, String active) {
        out.println("<header class='topbar'>");
        out.println("<nav class='nav nav-shell' aria-label='Navegacao principal'>");
        out.println("<a href='index.html' class='brand brand-link' aria-label='Bookstore dashboard'><span class='brand-mark' aria-hidden='true'>B</span><span>Bookstore</span></a>");
        out.println("<div class='nav-links'>");
        out.println("<a href='index.html' class='nav-link" + activeClassName(active, "dashboard") + "' data-nav='dashboard'" + activeAria(active, "dashboard") + ">Dashboard</a>");
        out.println("<a href='new' class='nav-link" + activeClassName(active, "new") + "' data-nav='new'" + activeAria(active, "new") + ">Add New Book</a>");
        out.println("<a href='list' class='nav-link" + activeClassName(active, "list") + "' data-nav='list'" + activeAria(active, "list") + ">List All Books</a>");
        out.println("<a href='creditos.html' class='nav-link" + activeClassName(active, "credits") + "' data-nav='credits'" + activeAria(active, "credits") + ">Credits</a>");
        out.println("</div>");
        out.println("<button class='theme-toggle' type='button' aria-label='Alternar tema' data-theme-toggle><span aria-hidden='true'>◐</span></button>");
        out.println("</nav>");
        out.println("</header>");
    }

    private String activeClassName(String active, String item) {
        return item.equals(active) ? " active" : "";
    }

    private String activeAria(String active, String item) {
        return item.equals(active) ? " aria-current='page'" : "";
    }

    private void printError(HttpServletResponse response, String title, String message) throws IOException {
        PrintWriter out = response.getWriter();
        UiRenderer.printDocumentStart(out, title);
        printMenu(out, "");
        UiRenderer.printMessage(out, "Erro", title, message, "list", "Voltar para livros");
        UiRenderer.printFooter(out);
    }

    private int parseId(HttpServletRequest request) {
        String value = require(request, "id", "ID");
        try {
            int id = Integer.parseInt(value);
            if (id <= 0) {
                throw new IllegalArgumentException("ID deve ser maior que zero.");
            }
            return id;
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("ID deve ser um numero valido.");
        }
    }

    private double parsePrice(String value) {
        try {
            return Double.parseDouble(value.replace(",", "."));
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Price deve ser um numero valido.");
        }
    }

    private String require(HttpServletRequest request, String parameter, String label) {
        String value = request.getParameter(parameter);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(label + " e obrigatorio.");
        }
        return value.trim();
    }

    private String getToast(String status) {
        if ("saved".equals(status)) {
            return "Livro salvo no catalogo.";
        }
        if ("updated".equals(status)) {
            return "Livro atualizado com sucesso.";
        }
        if ("deleted".equals(status)) {
            return "Livro removido do catalogo.";
        }
        if ("missing".equals(status)) {
            return "Livro nao encontrado para atualizacao.";
        }
        return "";
    }

    private String getInitials(String value) {
        if (value == null || value.trim().isEmpty()) {
            return "BK";
        }
        String[] parts = value.trim().split("\\s+");
        String first = parts[0].substring(0, 1);
        String second = parts.length > 1 ? parts[1].substring(0, 1) : "";
        return UiRenderer.escape((first + second).toUpperCase());
    }
}
