<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri='http://vdab.be/tags' prefix='vdab'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<!DOCTYPE html>
<html lang="nl">
<vdab:head title="Reservatiemandje"/>
<body>
	<vdab:header image="mandje" title="reservatiemandje" />
	<a href="<c:url value="/index.htm"/>">Voorstellingen</a>
	<c:choose>
		<c:when test="${not empty reservatieMandje}">
			<a href="<c:url value="/bevestiging.htm"/>">Bevestiging
				reservatie</a>
			<form id="verwijderform">
				<table>
					<tr>
						<th>Datum</th>
						<th>Titel</th>
						<th>Uitvoerders</th>
						<th>Prijs</th>
						<th>Plaatsen</th>
						<th><input type="submit" value="Verwijderen"
							id="verwijderknop"></th>
					</tr>
					<c:forEach var="voorstelling" items="${voorstellingenReservatie}">
						<tr>
							<td><c:out value='${voorstelling.datum}' /></td>
							<td><c:out value='${voorstelling.titel}' /></td>
							<td><c:out value='${voorstelling.uitvoerders}' /></td>
							<td><c:out value='€${voorstelling.prijs}' /></td>
							<td class="rechts"><c:out value='${reservatieMandje[voorstelling.id]}' /></td>
							<td><input type="checkbox" name="verwijderd_Id"
								value="${voorstelling.id}"></td>
						</tr>
					</c:forEach>
				</table>
			</form>
			<p>
				Te Betalen:
				<c:out value="€${teBetalen}" />
			</p>
		</c:when>
		<c:otherwise>
			<div class='fout'>Uw mandje is leeg, kies
				een voorstelling.</div>
		</c:otherwise>
	</c:choose>
	<script>
		document.getElementById("verwijderform").onsubmit = function() {
			if (!navigator.cookieEnabled) {
				alert("Dit werkt enkel als cookies aanstaan");
				return false;
			}
			document.getElementById("verwijderknop").disabled = true;
		};
	</script>
</body>
</html>