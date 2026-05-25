(function () {
    var root = document.documentElement;
    var storedTheme = localStorage.getItem("bookstore-theme");

    if (storedTheme) {
        root.setAttribute("data-theme", storedTheme);
    }

    document.querySelectorAll("[data-theme-toggle]").forEach(function (button) {
        button.addEventListener("click", function () {
            var nextTheme = root.getAttribute("data-theme") === "dark" ? "light" : "dark";
            root.setAttribute("data-theme", nextTheme);
            localStorage.setItem("bookstore-theme", nextTheme);
        });
    });

    var searchInput = document.querySelector("[data-book-search]");
    var sortInput = document.querySelector("[data-book-sort]");
    var list = document.querySelector("[data-book-list]");
    var emptySearch = document.querySelector("[data-empty-search]");

    function getCards() {
        return Array.prototype.slice.call(document.querySelectorAll("[data-book-card]"));
    }

    function normalize(value) {
        return String(value || "").toLowerCase().normalize("NFD").replace(/[\u0300-\u036f]/g, "");
    }

    function filterAndSortBooks() {
        if (!list) {
            return;
        }

        var query = normalize(searchInput ? searchInput.value : "");
        var visibleCount = 0;
        var cards = getCards();

        cards.forEach(function (card) {
            var haystack = normalize(card.dataset.title + " " + card.dataset.author);
            var visible = haystack.indexOf(query) !== -1;
            card.hidden = !visible;
            if (visible) {
                visibleCount += 1;
            }
        });

        var sorted = cards.slice().sort(function (a, b) {
            var mode = sortInput ? sortInput.value : "recent";
            if (mode === "title") {
                return normalize(a.dataset.title).localeCompare(normalize(b.dataset.title));
            }
            if (mode === "author") {
                return normalize(a.dataset.author).localeCompare(normalize(b.dataset.author));
            }
            if (mode === "priceAsc") {
                return Number(a.dataset.price) - Number(b.dataset.price);
            }
            if (mode === "priceDesc") {
                return Number(b.dataset.price) - Number(a.dataset.price);
            }
            return 0;
        });

        sorted.forEach(function (card) {
            list.appendChild(card);
        });

        if (emptySearch) {
            emptySearch.hidden = visibleCount !== 0;
        }
    }

    if (searchInput) {
        searchInput.addEventListener("input", filterAndSortBooks);
    }
    if (sortInput) {
        sortInput.addEventListener("change", filterAndSortBooks);
    }

    var form = document.querySelector("[data-book-form]");
    if (form) {
        var titleInput = form.querySelector("[name='title']");
        var authorInput = form.querySelector("[name='author']");
        var priceInput = form.querySelector("[name='price']");
        var previewTitle = document.querySelector("[data-preview-title]");
        var previewAuthor = document.querySelector("[data-preview-author]");
        var previewPrice = document.querySelector("[data-preview-price]");
        var previewInitials = document.querySelector("[data-preview-initials]");

        function updatePreview() {
            var title = titleInput.value.trim() || "Titulo do livro";
            var author = authorInput.value.trim() || "Autor";
            var rawPrice = priceInput.value.trim().replace(",", ".") || "0";
            var numericPrice = Number(rawPrice);
            var price = Number.isFinite(numericPrice) ? numericPrice : 0;
            var parts = title.split(/\s+/);
            var initials = (parts[0] ? parts[0][0] : "B") + (parts[1] ? parts[1][0] : "K");

            previewTitle.textContent = title;
            previewAuthor.textContent = "por " + author;
            previewPrice.textContent = "R$ " + price.toFixed(2);
            previewInitials.textContent = initials.toUpperCase();
        }

        function markValidity(input) {
            var field = input.closest(".field");
            var invalid = !input.validity.valid;
            input.classList.toggle("is-invalid", invalid);
            if (field) {
                field.classList.toggle("has-error", invalid);
            }
            return !invalid;
        }

        [titleInput, authorInput, priceInput].forEach(function (input) {
            input.addEventListener("input", function () {
                updatePreview();
                if (input.classList.contains("is-invalid")) {
                    markValidity(input);
                }
            });
            input.addEventListener("blur", function () {
                markValidity(input);
            });
        });

        form.addEventListener("submit", function (event) {
            var valid = [titleInput, authorInput, priceInput].every(markValidity);
            if (!valid) {
                event.preventDefault();
                form.querySelector(".is-invalid").focus();
            }
        });

        updatePreview();
    }
})();
