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
	<vdab:header title='nieuwe klant  ' image='nieuweklant' />
	<a href="<c:url value="/index.htm"/>">Voorstellingen</a>
	<c:choose>
		<c:when test="${mandjeAanwezig}">
			<a href="<c:url value="/reservatiemandje.htm"/>">Reservatiemandje</a>
			<a href="<c:url value="/bevestiging.htm"/>">Bevestiging reservatie</a>
			<label>Voornaam:<input name="voornaam" value="${param.voornaam}"></label>
			<label>Familienaam:<input name="familienaam" value="${param.familienaam}"></label>
			<label>Straat:<input name="straat" value="${straat}"></label>
			<label>Huisnr.:<input name="huisnr" value="${param.huisnr}"></label>
			<label>Postcode:<input name="postcode" value="${param.postcode}"></label>
			<label>Gemeente:<input name="gemeente" value="${param.gemeente}"></label>
			<label>Gebruikersnaam:<input name="gebruikersnaam" value="${param.gebruikersnaam}"></label>
			<label>Paswoord:<input name="paswoord" type="password"></label>
			<label>Herhaal paswoord:<input name="herhaalpaswoord" type="password"></label>
		</c:when>
		<c:otherwise>
			<p>U hebt nog geen reservaties, kies een voorstelling.</p>
		</c:otherwise>
	</c:choose>
</body>
</html>
