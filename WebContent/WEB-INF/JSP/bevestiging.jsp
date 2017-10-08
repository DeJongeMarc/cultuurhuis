<%@ page contentType="text/html" pageEncoding="UTF-8"%>
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
	<vdab:header title='bevestiging reservaties' image='bevestig' />
	<a href="<c:url value="/index.htm"/>">Voorstellingen</a>
	<c:choose>
		<c:when test="${mandjeAanwezig}">
			<a href="<c:url value="/reservatiemandje.htm"/>">Reservatiemandje</a>
			<h2>Stap 1: wie ben je ?</h2>
			<c:choose>
				<c:when test="${empty klant}">
					<form method="post">
						<label>Gebruikersnaam:<span>${fouten.gebruikersnaam}</span><input
							name="gebruikersnaam" value="${param.gebruikersnaam}" autofocus required></label> <label>Paswoord:<span>${fouten.paswoord}</span><input
							name="paswoord" type="password" required></label> <input type="submit"
							value="Zoek me op" id="zoekknop">
					</form>
					<form action="<c:url value ="/nieuweklant.htm"/>">
						<input type="submit" value="Ik ben nieuw" id="nieuwknop">
					</form>
					<c:out value="${fouten.klant}" />
					<h2>Stap 2: Bevestigen</h2>
					<input type="button" value="Bevestigen" id="bevestigknop" disabled>
				</c:when>
				<c:otherwise>
					<label>Gebruikersnaam:<input name="gebruikersnaam" value="${param.gebruikersnaam}" disabled></label>
					<label>Paswoord:<input name="paswoord" type="password" disabled></label>
					<input type="button" value="Zoek me op" id="zoekknop" disabled>
					<input type="button" value="Ik ben nieuw" id="nieuwknop" disabled>
					<c:out
						value="${klant.voornaam} ${klant.familienaam} ${klant.straat} ${klant.huisnr} ${klant.postcode} ${klant.gemeente}"></c:out>
					<h2>Stap 2: Bevestigen</h2>
					<form action="<c:url value ="/overzicht.htm"/>">
					<input type="submit" value="Bevestigen" id="bevestigknop">
					</form>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<p>U hebt nog geen reservaties, kies een voorstelling.</p>
		</c:otherwise>
	</c:choose>
</body>
</html>