<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri='http://vdab.be/tags' prefix='vdab'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<!DOCTYPE html>
<html lang="nl">
<head>
<title>Overzicht</title>
<link rel='icon' href='images/favicon.ico'>
<meta name='viewport' content='width=device-width,initial-scale=1'>
<link rel='stylesheet' href='styles/default.css'>
</head>
<body>
	<vdab:header image="bevestig" title="overzicht" />
	<a href="<c:url value="/index.htm"/>">Voorstellingen</a>
	<c:choose>
		<c:when test="${not empty mandje}">
			<h2>Gelukte reserveringen</h2>
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
						<td><c:out value='${mandje[voorstelling.id]}' /></td>
					</tr>
				</c:forEach>
			</table>
			<h2>Mislukte reserveringen</h2>
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
						<td><c:out value='${mandje[voorstelling.id]}' /></td>
						<td><c:out value='${voorstelling.vrijePlaatsen}' /></td>
					</tr>
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
			<p>U hebt nog geen reservaties, kies een voorstelling.</p>
		</c:otherwise>
	</c:choose>
</body>