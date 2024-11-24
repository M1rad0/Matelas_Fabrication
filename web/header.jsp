<%-- 
    Document   : header
    Created on : 20 nov. 2024, 21:58:41
    Author     : Mirado
--%>

    <!-- Custom CSS -->
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
        }
        .navbar-custom {
            background-color: #007bff;
        }
        .navbar-custom .navbar-brand,
        .navbar-custom .nav-link {
            color: #fff;
        }
        .navbar-custom .nav-link:hover {
            color: #d1ecf1;
        }
        .container {
            margin-top: 20px;
        }
        table{
            width: 100%;
        }
        th,td{
            padding: 10px;
        }
    </style>

    <header>
        <nav class="navbar navbar-expand-lg navbar-custom">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">Matelas</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link" href="formulaireInsertCSV.jsp">Inserer CSV</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="afficherMachines">Informations machines</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="formulaireAnnee.jsp">Informations annuelles</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    </header>