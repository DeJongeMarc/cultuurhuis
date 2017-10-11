<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri='http://vdab.be/tags' prefix='vdab'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<!DOCTYPE html>
<html lang="nl">
<vdab:head title="Overzicht"/>
<body>
	<vdab:header image="bevestig" title="overzicht" />
	<a href="<c:url value="/index.htm"/>">Voorstellingen</a>
	<c:choose>
		<c:when test="${not empty overzichtmandje and not empty gebruiker}">
			<h2>Gelukte reserveringen</h2>
			<c:choose>
			<c:when test="${not empty gelukteReserveringen}">
			<table>
				<tr>
					<th>Datum</th>
					<th>Titel</th>
					<th>Uitvoerders</th>
					<th>Prijs</th>
					<th>Plaatsen</th>
				</tr>
				<c:forEach var='voorstelling' items='${gelukteReserveringen}'>
					<tr>
						<td><c:out value='${voorstelling.datum}' /></td>
						<td><c:out value='${voorstelling.titel}' /></td>
						<td><c:out value='${voorstelling.uitvoerders}' /></td>
						<td><c:out value='€${voorstelling.prijs}' /></td>
						<td class="rechts"><c:out value='${overzichtmandje[voorstelling.id]}' /></td>
					</tr>
				</c:forEach>
			</table>
			</c:when>
			<c:otherwise>
			<p>U hebt geen gelukte reservaties.</p>
			</c:otherwise>
			</c:choose>
			<h2>Mislukte reserveringen</h2>
			<c:choose>
			<c:when test="${not empty mislukteReserveringen}">
			<table>
				<tr>
					<th>Datum</th>
					<th>Titel</th>
					<th>Uitvoerders</th>
					<th>Prijs</th>
					<th>Plaatsen</th>
					<th>VrijePlaatsen</th>
				</tr>
				<c:forEach var='voorstelling' items='${mislukteReserveringen}'>
					<tr>
						<td><c:out value='${voorstelling.datum}' /></td>
						<td><c:out value='${voorstelling.titel}' /></td>
						<td><c:out value='${voorstelling.uitvoerders}' /></td>
						<td><c:out value='€${voorstelling.prijs}' /></td>
						<td class="rechts"><c:out value='${overzichtmandje[voorstelling.id]}' /></td>
						<td class="rechts"><c:out value='${voorstelling.vrijePlaatsen}' /></td>
					</tr>
				</c:forEach>
			</table>
			</c:when>
			<c:otherwise>
			<p>Al uw reserveringen zijn gelukt</p>
			</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<p>U hebt nog geen reservaties of bent nog niet aangemeld als gebruiker.</p>
		</c:otherwise>
	</c:choose>
</body>