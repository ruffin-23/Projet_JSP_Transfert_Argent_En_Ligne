
	<nav class="navbar navbar-expand-lg bg-primary" data-bs-theme="dark">
	  <div class="container-fluid">
	    <a class="navbar-brand" href="#"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">Transfert d'argent en ligne</font></font></a>
	    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarColor01" aria-controls="navbarColor01" aria-expanded="false" aria-label="Basculer la navigation">
	      <span class="navbar-toggler-icon"></span>
	    </button>
	    <div class="collapse navbar-collapse" id="navbarColor01">
	      <ul class="navbar-nav me-auto">
	        <li class="nav-item">
	          <a class="nav-link active" href="#"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">Accueil
	            </font></font><span class="visually-hidden"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">(actuel)</font></font></span>
	          </a>
	        </li>
	        <li class="nav-item">
	          <a class="nav-link" href="<%= request.getContextPath() %>/list"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">Client</font></font></a>
	        </li>
	        <li class="nav-item">
	          <a class="nav-link" href="<%= request.getContextPath() %>/taux/listT"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">Taux</font></font></a>
	        </li>
	        <li class="nav-item">
	          <a class="nav-link" href="#"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">Frais</font></font></a>
	        </li>
	      </ul>
	      
	      	<form class="d-flex" action="search" method="post">
			    <input class="form-control me-sm-2" type="search" name="searchKeyword" placeholder="Recherche">
			    <button class="btn btn-secondary my-2 my-sm-0" type="submit">Recherche</button>
			</form>	

	    </div>
	  </div>
	</nav>
