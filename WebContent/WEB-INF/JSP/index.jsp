<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri='http://vdab.be/tags' prefix='vdab'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
<!DOCTYPE html>
<html lang="nl">
<vdab:head title="Cultuurhuis"/>
<body>
	<vdab:header title='voorstellingen' image='voorstellingen' />
	<c:if test="${not empty mandje}">
	<a href="<c:url value="/reservatiemandje.htm"/>">Reservatiemandje</a>
	<a href="<c:url value="/bevestiging.htm"/>">Bevestig reservatie</a>
	</c:if>
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
		<c:when test="${not empty genre and empty fout}">
			<h2>
				<c:out value='${genre.naam} ' />
				voorstellingen
			</h2>
			<c:choose>
				<c:when test="${not empty genreVoorstellingen}">
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
								<td>
								<fmt:parseDate value="${voorstelling.datum}" pattern="yyyy-MM-dd'T'HH:mm" var="datumVoorstelling"/>
								<fmt:formatDate value="${datumVoorstelling}" type="both" dateStyle="short" timeStyle="short"/>
								</td>
								<td><c:out value='${voorstelling.titel}' /></td>
								<td><c:out value='${voorstelling.uitvoerders}' /></td>
								<td><c:out value='€${voorstelling.prijs}' /></td>
								<td class="rechts"><c:out value='${voorstelling.vrijePlaatsen}'/></td>
								<c:if test="${voorstelling.vrijePlaatsen > 0}">
									<td><a
										href="<c:url value='/reserveren.htm'><c:param name='voorstelling_id' value='${voorstelling.id}'/></c:url>">Reserveren</a></td>
								</c:if>
							</tr>
						</c:forEach>
					</table>
				</c:when>
				<c:otherwise>
					<c:out
						value="Geen toekomstige voorstellingen gepland voor deze genre" />
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
		<div class='fout'>${fout}</div>
		</c:otherwise>
	</c:choose>
</body>
</html>
