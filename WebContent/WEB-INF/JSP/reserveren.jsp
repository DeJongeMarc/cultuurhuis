<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri='http://vdab.be/tags' prefix='vdab'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
<!DOCTYPE html>
<html lang="nl">
<vdab:head title="Reserveren"/>
<body>
	<vdab:header image="reserveren" title="reserveren" />
	<a href="<c:url value="/index.htm"/>">Voorstellingen</a>
	<c:if test="${not empty mandje}">
	<a href="<c:url value="/reservatiemandje.htm"/>">Reservatiemandje</a>
	<a href="<c:url value="/bevestiging.htm"/>">Bevestiging reservatie</a>
	</c:if>
	<c:choose>
		<c:when test="${empty voorstellingReserveren or foutId}">
			<div class='fout'>Geen voorstellingen beschikbaar, kies een
				andere voorstelling.</div>
		</c:when>
		<c:otherwise>
			<p>Voorstelling:</p>
			<c:out value='${voorstellingReserveren.titel}' />
			<p>Uitvoerders:</p>
			<c:out value='${voorstellingReserveren.uitvoerders}' />
			<p>Datum:</p>
			<fmt:parseDate value="${voorstellingReserveren.datum}" pattern="yyyy-MM-dd'T'HH:mm" var="datumVoorstelling"/>
			<fmt:formatDate value="${datumVoorstelling}" type="both" dateStyle="short" timeStyle="short"/>
			<p>Prijs:</p>
			<c:out value='€${voorstellingReserveren.prijs}' />
			<p>Vrije plaatsen:</p>
			<c:out value='${voorstellingReserveren.vrijePlaatsen}' />
			<form method="post" id="reserverenform">
				<label>Plaatsen:<span>${foutAantal}</span><input
					name="aantalPlaatsen" value="${aantalPlaatsen}" autofocus
					type="number" min="1" max="${voorstellingReserveren.vrijePlaatsen}"
					required></label> <input type="hidden" name="reservatie_id"
					value="${voorstellingReserveren.id}"> <input type="submit"
					value="Reserveren" id="reserverenknop">
			</form>
		</c:otherwise>
	</c:choose>
	<script>
		document.getElementById("reserverenform").onsubmit = function() {
			if (!navigator.cookieEnabled) {
				alert("Dit werkt enkel als cookies aanstaan");
				return false;
			}
			document.getElementById("reserverenknop").disabled = true;
		};
	</script>
</body>
</html>