<%@ page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@taglib uri='http://vdab.be/tags' prefix='vdab'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<!DOCTYPE html>
<html lang="nl">
<head>
<title>Cultuurhuis</title>
<link rel='icon' href='images/favicon.ico'>
<meta name='viewport' content='width=device-width,initial-scale=1'>
<link rel='stylesheet' href='styles/default.css'>
</head>
<body>
	<vdab:header title='voorstellingen' image='voorstellingen' />
	<h2>Genres</h2>
	<nav>
		<ul>
			<c:forEach var='genre' items='${genres}'>
				<li><a
					href="<c:url value='/'><c:param name='genre_id' value='${genre.id}'/></c:url>">${genre.naam}</a></li>
			</c:forEach>
		</ul>
	</nav>
	<c:choose>
		<c:when test="${not empty fout}">
			<div class='fout'>${fout}</div>
		</c:when>
		<c:when test="${not empty genreNaam}">
			<h2><c:out value='${genreNaam} ' />voorstellingen</h2>
		</c:when>	
		<c:when test="${not empty voorstelling}">
			<table>
				<tr>
					<th>Datum</th>
					<th>Titel</th>
					<th>Uitvoerders</th>
					<th>Prijs</th>
					<th>Vrije plaatsen</th>
					<th>Reserveren</th>
				</tr>
				<c:forEach var='voorstelling' items='${genreVoorstellingen}'>
					<tr>
						<td><c:out value='${voorstelling.datum}' /></td>
						<td><c:out value='${voorstelling.titel}' /></td>
						<td><c:out value='${voorstelling.uitvoerders}' /></td>
						<td><c:out value='€${voorstelling.prijs}' /></td>
						<td><c:out value='${voorstelling.vrijePlaatsen}' /></td>
						<c:if test="${voorstelling.vrijePlaatsen > 0}">
							<td><a
								href="<c:url value='/reserveren.htm'><c:param name='voorstelling_id' value='${voorstelling.id}'/></c:url>">Reserveren</a></td>
						</c:if>
					</tr>
				</c:forEach>
			</table>
		</c:when>
	</c:choose>
</body>
</html>
