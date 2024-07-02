<!DOCTYPE nav PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Trensfert d'argent en ligne</title>
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<link rel="stylesheet" href="css/bootstrap.css">
	
	<style type="text/css">
		.navbar-nav .nav-link.active {
		  color: white; /* Couleur du texte */
		  font-weight: bold; /* Gras */
		}
	</style>
</head>
<body>
	<nav class="navbar navbar-expand-lg bg-dark" data-bs-theme="dark">
	  <div class="container-fluid">
	    <a class="navbar-brand" href="#">
	      Transfert d'argent en ligne
	    </a>
	    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarColor01" aria-controls="navbarColor01" aria-expanded="false" aria-label="Basculer la navigation">
	      <span class="navbar-toggler-icon"></span>
	    </button>
	    <div class="collapse navbar-collapse" id="navbarColor01">
	      <ul class="navbar-nav ms-auto">
	        <li class="nav-item">
	          <a class="nav-link active" href="<%= request.getContextPath() %>/">
	            Accueil
	            <span class="visually-hidden">(actuel)</span>
	          </a>
	        </li>
	        <li class="nav-item">
	          <a class="nav-link" href="<%= request.getContextPath() %>/Client/list">
	            Client
	          </a>
	        </li>
	        <li class="nav-item">
	          <a class="nav-link" href="<%= request.getContextPath() %>/taux/listeT">
	            Taux
	          </a>
	        </li>
	        <li class="nav-item">
	          <a class="nav-link" href="<%= request.getContextPath() %>/frais/listF">
	            Frais
	          </a>
	        </li>
	        <li class="nav-item">
	          <a class="nav-link" href="<%= request.getContextPath() %>/envoye/listeE">
	            Envoyé
	          </a>
	        </li>
	      </ul>
	    </div>
	  </div>
	</nav>
		
</body>
</html>
