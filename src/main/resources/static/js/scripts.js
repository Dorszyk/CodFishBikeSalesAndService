/**
 * Dynamiczne zarządzanie listą części i usług w formularzu serwisanta.
 * Funkcja musi być dostępna globalnie przed DOMContentLoaded.
 * Version: 2026-01-28-v2
 */
console.log("[DEBUG] scripts.js loaded - setupServiceForm being defined");

window.setupServiceForm = function(config) {
    console.log("[DEBUG] setupServiceForm called with config:", config);
    
    const {
        partsContainerId,
        addPartBtnId,
        partDTOs,
        servicesContainerId,
        addServiceBtnId,
        serviceDTOs
    } = config;

    const partsContainer = document.getElementById(partsContainerId);
    const addPartBtn = document.getElementById(addPartBtnId);
    
    console.log("[DEBUG] Parts container element:", partsContainer);
    console.log("[DEBUG] Add Part button element:", addPartBtn);
    
    if (!partsContainer) console.warn('Parts container not found:', partsContainerId);
    if (!addPartBtn) console.warn('Add Part button not found:', addPartBtnId);

    function createPartRow(index) {
        const row = document.createElement('div');
        row.className = 'row g-2 align-items-center mb-2 part-row';
        let options = '<option value="NONE">-- Choose Part --</option>';
        if (partDTOs && Array.isArray(partDTOs)) {
            partDTOs.forEach(part => {
                options += `<option value="${part.serialNumber}">${part.description} (${part.price} zł)</option>`;
            });
        }

        row.innerHTML = `
            <div class="col-md-8">
                <select name="parts[${index}].partSerialNumber" class="form-select shadow-none">
                    ${options}
                </select>
            </div>
            <div class="col-md-3">
                <div class="input-group input-group-sm">
                    <span class="input-group-text bg-white small text-muted">Qty</span>
                    <input type="number" name="parts[${index}].partQuantity" class="form-control shadow-none text-center" value="1" min="1"/>
                </div>
            </div>
            <div class="col-md-1 text-end">
                <button type="button" class="btn btn-link text-danger remove-part-btn p-0">
                    <i class="fas fa-minus-circle fa-lg"></i>
                </button>
            </div>
        `;
        row.querySelector('.remove-part-btn').addEventListener('click', (e) => {
            e.preventDefault();
            row.remove();
            reindexParts();
        });
        return row;
    }

    function reindexParts() {
        if (!partsContainer) return;
        const rows = partsContainer.querySelectorAll('.part-row');
        rows.forEach((row, i) => {
            const select = row.querySelector('select');
            const input = row.querySelector('input[name*=".partQuantity"]');
            if (select) select.name = `parts[${i}].partSerialNumber`;
            if (input) input.name = `parts[${i}].partQuantity`;
        });
    }

    if (addPartBtn && partsContainer) {
        console.log("[DEBUG] Setting up Add Part button onclick handler");
        // Używamy delegacji zdarzeń na kontenerze zamiast klonowania przycisku
        addPartBtn.onclick = (e) => {
            console.log("[DEBUG] Add Part button onclick triggered");
            e.preventDefault();
            console.log("Add Part clicked");
            const currentRows = partsContainer.querySelectorAll('.part-row');
            console.log("Adding part row at index:", currentRows.length);
            partsContainer.appendChild(createPartRow(currentRows.length));
        };
    } else {
        console.error("[DEBUG] Cannot setup Add Part handler - button or container missing");
    }

    const servicesContainer = document.getElementById(servicesContainerId);
    const addServiceBtn = document.getElementById(addServiceBtnId);
    
    console.log("[DEBUG] Services container element:", servicesContainer);
    console.log("[DEBUG] Add Service button element:", addServiceBtn);
    
    if (!servicesContainer) console.warn('Services container not found:', servicesContainerId);
    if (!addServiceBtn) console.warn('Add Service button not found:', addServiceBtnId);

    function createServiceRow(index) {
        const row = document.createElement('div');
        row.className = 'row g-2 align-items-center mb-2 service-row';
        let options = '<option value="">-- Choose Service --</option>';
        if (serviceDTOs && Array.isArray(serviceDTOs)) {
            serviceDTOs.forEach(service => {
                options += `<option value="${service.serviceCode}">${service.description} (${service.price} zł)</option>`;
            });
        }

        row.innerHTML = `
            <div class="col-md-8">
                <select name="services[${index}].serviceCode" class="form-select shadow-none" required>
                    ${options}
                </select>
            </div>
            <div class="col-md-3">
                <div class="input-group input-group-sm">
                    <span class="input-group-text bg-white small text-muted">Hrs</span>
                    <input type="number" step="0.5" name="services[${index}].hours" class="form-control shadow-none text-center" value="1" min="0.5"/>
                </div>
            </div>
            <div class="col-md-1 text-end">
                <button type="button" class="btn btn-link text-danger remove-service-btn p-0">
                    <i class="fas fa-minus-circle fa-lg"></i>
                </button>
            </div>
        `;
        row.querySelector('.remove-service-btn').addEventListener('click', (e) => {
            e.preventDefault();
            row.remove();
            reindexServices();
        });
        return row;
    }

    function reindexServices() {
        if (!servicesContainer) return;
        const rows = servicesContainer.querySelectorAll('.service-row');
        rows.forEach((row, i) => {
            const select = row.querySelector('select');
            const input = row.querySelector('input[name*=".hours"]');
            if (select) select.name = `services[${i}].serviceCode`;
            if (input) input.name = `services[${i}].hours`;
        });
    }

    if (addServiceBtn && servicesContainer) {
        console.log("[DEBUG] Setting up Add Service button onclick handler");
        addServiceBtn.onclick = (e) => {
            console.log("[DEBUG] Add Service button onclick triggered");
            e.preventDefault();
            console.log("Add Service clicked");
            const currentRows = servicesContainer.querySelectorAll('.service-row');
            console.log("Adding service row at index:", currentRows.length);
            servicesContainer.appendChild(createServiceRow(currentRows.length));
        };
    } else {
        console.error("[DEBUG] Cannot setup Add Service handler - button or container missing");
    }

    // Inicjalizacja pierwszych wierszy - dodaj pierwszy wiersz jeśli kontenery są puste
    console.log("[DEBUG] Checking if initial rows need to be added");
    if (partsContainer && partsContainer.querySelectorAll('.part-row').length === 0) {
        console.log("[DEBUG] Adding initial part row");
        partsContainer.appendChild(createPartRow(0));
    }
    if (servicesContainer && servicesContainer.querySelectorAll('.service-row').length === 0) {
        console.log("[DEBUG] Adding initial service row");
        servicesContainer.appendChild(createServiceRow(0));
    }
    console.log("[DEBUG] setupServiceForm completed successfully");
};

console.log("[DEBUG] window.setupServiceForm defined:", typeof window.setupServiceForm);

// Sprawdzenie czy jQuery jest dostępne
if (typeof $ === 'undefined' && typeof jQuery !== 'undefined') {
    var $ = jQuery;
}

if (typeof $ !== 'undefined') {
    $(document).ready(function () {
        console.log("scripts.js loaded and ready");
    /**
     * Inicjalizacja paginacji dla tabeli.
     * @param {string} tableId - ID tabeli (np. "#bikes-table")
     * @param {string} containerId - ID kontenera paginacji (np. "#pagination-container")
     * @param {number} perPage - Liczba elementów na stronę
     * @param {number} visiblePages - Liczba widocznych przycisków stron
     */
    function setupPagination(tableId, containerId, perPage, visiblePages) {
        var $items = $(tableId + " tbody tr");
        var numItems = $items.length;

        if (numItems > perPage) {
            $items.slice(perPage).hide();
            $(containerId).twbsPagination({
                totalPages: Math.ceil(numItems / perPage),
                visiblePages: visiblePages || 5,
                onPageClick: function (event, page) {
                    var start = perPage * (page - 1);
                    var end = start + perPage;
                    $items.hide().slice(start, end).show();
                }
            });
        }
    }

    /**
     * Inicjalizacja wyszukiwania w tabeli.
     * @param {string} inputId - ID pola wyszukiwania (np. "#searchInput")
     * @param {string} tableId - ID tabeli (np. "#bikes-table")
     */
    function setupSearch(inputId, tableId) {
        $(inputId).on("keyup", function () {
            var value = $(this).val().toLowerCase();
            $(tableId + " tbody tr").filter(function () {
                $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1);
            });
        });
    }

    /**
     * Uniwersalne wyszukiwanie dla tabel oznaczonych klasą .searchable-table
     * oraz dla list (np. list-group z elementami .history-item)
     */
    function setupUniversalSearch() {
        const searchInputs = document.querySelectorAll('[data-search-target]');
        searchInputs.forEach(input => {
            input.addEventListener('input', function () {
                const targetSelector = this.dataset.searchTarget;
                const filter = this.value.toLowerCase();
                
                // Najpierw próbuj znaleźć wiersze tabeli
                let rows = document.querySelectorAll(`${targetSelector} tbody tr`);
                
                // Jeśli nie ma wierszy tabeli, szukaj elementów listy (np. .history-item, .list-group-item)
                if (rows.length === 0) {
                    rows = document.querySelectorAll(`${targetSelector} .history-item, ${targetSelector} .list-group-item-action`);
                }

                rows.forEach(row => {
                    const text = row.textContent.toLowerCase();
                    row.style.display = text.includes(filter) ? '' : 'none';
                });
            });
        });
    }

    /**
     * Automatyczne wypełnianie formularza danymi z wiersza tabeli.
     * Wymaga atrybutów data-fill-target na wierszach i data-field-name na polach formularza.
     */
    function setupTableFormAutoFill() {
        const rows = document.querySelectorAll('[data-fill-form]');
        rows.forEach(row => {
            row.addEventListener('click', function () {
                const formSelector = this.dataset.fillForm;
                const form = document.querySelector(formSelector);
                if (!form) return;

                // Opcjonalne przełączenie zakładki (Bootstrap)
                const tabSelector = this.dataset.tabTarget;
                if (tabSelector) {
                    const tabEl = document.querySelector(tabSelector);
                    if (tabEl) {
                        const bsTab = bootstrap.Tab.getOrCreateInstance(tabEl);
                        bsTab.show();
                    }
                }

                // Wypełnianie pól formularza na podstawie dataset wiersza
                Object.keys(this.dataset).forEach(key => {
                    const field = form.querySelector(`[name="${key}"], #${key}, [data-field="${key}"]`);
                    if (field) {
                        if (field.tagName === 'SELECT') {
                            // Znajdź opcję o danej wartości
                            const option = Array.from(field.options).find(opt => opt.value === this.dataset[key]);
                            if (option) {
                                field.value = this.dataset[key];
                            } else {
                                // Spróbuj znaleźć opcję po tekście (jeśli wartość to np. "Brand Model")
                                const optionByText = Array.from(field.options).find(opt => opt.text === this.dataset[key]);
                                if (optionByText) {
                                    field.value = optionByText.value;
                                }
                            }
                        } else {
                            field.value = this.dataset[key];
                        }
                        // Wyzwalacz zdarzenia 'change' po zmianie wartości
                        field.dispatchEvent(new Event('change', { bubbles: true }));
                    }
                });

                // Podświetlenie aktywnego wiersza
                const siblings = row.parentElement.querySelectorAll('[data-fill-form]');
                siblings.forEach(s => {
                    s.classList.remove('table-primary', 'active-row', 'border-primary');
                    if (s.classList.contains('border-4')) {
                        s.classList.add('border-light');
                    }
                });
                row.classList.add('table-primary', 'active-row');
                if (row.classList.contains('border-4')) {
                    row.classList.remove('border-light');
                    row.classList.add('border-primary');
                }
            });
        });
    }

    /**
     * Obsługa paska bocznego (sidebar)
     */
    function setupSidebar() {
        window.toggleSidebar = function () {
            const sidebar = document.getElementById('sidebar');
            if (sidebar) sidebar.classList.toggle('show');
        };

        const currentPath = window.location.pathname;
        const navLinks = document.querySelectorAll('.sidebar-custom .nav-link');

        navLinks.forEach(link => {
            if (link.getAttribute('href') === currentPath) {
                link.classList.add('active-nav');
            }
        });
    }

    /**
     * Scroll active item into view
     */
    function setupScrollActive() {
        const activeItem = document.querySelector('.active-bike, .active-row');
        if (activeItem) {
            activeItem.scrollIntoView({ behavior: 'smooth', block: 'center' });
        }
    }

    // Automatyczna inicjalizacja
    setupUniversalSearch();
    setupTableFormAutoFill();
    setupSidebar();
    window.addEventListener('load', setupScrollActive);

    // 1. Paginacja (legacy)
    if ($("#bikes-table").length && $("#pagination-container").length) {
        setupPagination("#bikes-table", "#pagination-container", 25);
    }
    if ($("#salesmen-table").length && $("#pagination-container-salesman").length) {
        setupPagination("#salesmen-table", "#pagination-container-salesman", 10);
    }
    if ($("#personRepairing-table").length && $("#pagination-container-personRepairing").length) {
        setupPagination("#personRepairing-table", "#pagination-container-personRepairing", 10);
    }
    if ($("#customers-table").length && $("#pagination-container-customers").length) {
        setupPagination("#customers-table", "#pagination-container-customers", 15);
    }
    if ($("#invoice-table").length && $("#pagination-container").length) {
        setupPagination("#invoice-table", "#pagination-container", 10);
    }
    if ($("#services-table").length && $("#pagination-container-services").length) {
        setupPagination("#services-table", "#pagination-container-services", 10);
    }
    if ($("#parts-table").length && $("#pagination-container-parts").length) {
        setupPagination("#parts-table", "#pagination-container-parts", 10);
    }

    // Specyficzne dla portalu sprzedawcy
    if ($("#customer-table1").length && $("#pagination-container1").length) {
        setupPagination("#customer-table1", "#pagination-container1", 10);
    }

    // Specyficzne dla portalu serwisanta (dwie tabele na jednej stronie)
    if ($("#parts-bikes").length && $("#pagination-container").length) {
        setupPagination("#parts-bikes", "#pagination-container", 10, 3);
    }
    if ($("#parts-serial").length && $("#pagination-container1").length) {
        setupPagination("#parts-serial", "#pagination-container1", 10, 3);
    }

    // 2. Wyszukiwanie (legacy)
    if ($("#searchInput").length) {
        var targetTable = "#bikes-table";
        if ($("#invoice-table").length) targetTable = "#invoice-table";
        else if ($("#parts-table").length) targetTable = "#parts-table";
        else if ($("#services-table").length) targetTable = "#services-table";
        else if ($("#parts-bikes").length) targetTable = "#parts-bikes";

        setupSearch("#searchInput", targetTable);
    }

    if ($("#searchInput1").length) {
        if ($("#parts-serial").length) setupSearch("#searchInput1", "#parts-serial");
        if ($("#customer-table1").length) setupSearch("#searchInput1", "#customer-table1");
    }

    if ($("#searchInputSalesman").length && $("#salesmen-table").length) {
        setupSearch("#searchInputSalesman", "#salesmen-table");
    }

    if ($("#searchInputPersonRepairing").length && $("#personRepairing-table").length) {
        setupSearch("#searchInputPersonRepairing", "#personRepairing-table");
    }

    if ($("#searchInputCustomers").length && $("#customers-table").length) {
        setupSearch("#searchInputCustomers", "#customers-table");
    }

    /**
     * Kopiuje tekst do schowka.
     * @param {string} text - Tekst do skopiowania
     */
    window.copyToClipboard = function (text) {
        var $temp = $("<input>");
        $("body").append($temp);
        $temp.val(text).select();
        document.execCommand("copy");
        $temp.remove();
    }
    });
} else {
    console.warn("[DEBUG] jQuery not available, skipping $(document).ready() initialization");
}