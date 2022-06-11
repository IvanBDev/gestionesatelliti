<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!doctype html>
<html lang="it" class="h-100" >
	 <head>
	 
	 	<!-- Common imports in pages -->
	 	<jsp:include page="../header.jsp" />
	   
	   <title>Modifica</title>
	 </head>
	   <body class="d-flex flex-column h-100">
	   
	   		<!-- Fixed navbar -->
	   		<jsp:include page="../navbar.jsp"></jsp:include>
	    
			
			<!-- Begin page content -->
			<main class="flex-shrink-0">
			  <div class="container">
			  
			  <div class='card'>
				    <div class='card-header'>
				        <h5>Modifica l'elemento selezionato</h5> 
				    </div>
				    <div class='card-body'>
				    
							<form:form modelAttribute="edit_satellite_attr" method="post" action="${pageContext.request.contextPath}/satellite/update" class="row g-3">
							
								<div class="col-md-6">
									<label for="denominazione" class="form-label">Denominazione:</label>
	                        		<input class="form-control" id="denominazione" type="text" name="denominazione" value = "${edit_satellite_attr.denominazione}">
								</div>
								
								<div class="col-md-6">
									<label for="codice" class="form-label">Codice:</label>
	                        		<input class="form-control" id="codice" type="text" name="codice" value = "${edit_satellite_attr.codice}">
								</div>
								
								<div class="col-md-6">
									<label for="dataLancio" class="form-label">Data di Lancio:</label>
									<fmt:formatDate pattern='yyyy-MM-dd' var="parsedDate" type='date' value='${edit_satellite_attr.dataLancio}' />
	                        		<input class="form-control" id="dataLancio" type="date" title="formato : gg/mm/aaaa"  name="dataLancio" value = "${parsedDate}">
								</div>
								
								<div class="col-md-6">
									<label for="dataRientro" class="form-label">Data di Rientro:</label>
									<fmt:formatDate pattern='yyyy-MM-dd' var="parsedDate" type='date' value='${edit_satellite_attr.dataRientro}' />
	                        		<input class="form-control" id="dataRientro" type="date" title="formato : gg/mm/aaaa"  name="dataRientro" value = "${parsedDate}">
								</div>
								
								<div class="col-md-3">
									<label for="stato" class="form-label">Stato</label>
								    <spring:bind path="stato">
									    <select class="form-select ${status.error ? 'is-invalid' : ''}" id="stato" name="stato" required>
									    	<option value="" selected>--SELEZIONA--</option>
									    	<option value="IN_MOVIMENTO" ${edit_satellite_attr.stato == 'IN_MOVIMENTO'?'selected':''}>IN_MOVIMENTO</option>
									      	<option value="FISSO" ${edit_satellite_attr.stato == 'FISSO'?'selected':''}>FISSO</option>
									      	<option value="DISATTIVATO" ${edit_satellite_attr.stato == 'DISATTIVATO'?'selected':''}>DISMESSO</option>
								    	</select>
								    </spring:bind>
								    <form:errors  path="stato" cssClass="error_field" />
								</div>
								
								<input type="hidden" name="id" value="${edit_satellite_attr.id}">
								
							<div class="col-12">
								<button type="submit" name="insertSubmit" value="insertSubmit" id="insertSubmit" class="btn btn-primary">Conferma</button>
							</div>
		
						</form:form>
				    
					<!-- end card-body -->			   
				    </div>
				<!-- end card -->
				</div>		
					  
			    
			  <!-- end container -->  
			  </div>
			  
			</main>
			
			<!-- Footer -->
			<jsp:include page="../footer.jsp" />
	  </body>
</html>