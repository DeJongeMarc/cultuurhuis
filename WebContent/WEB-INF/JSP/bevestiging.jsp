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
	<vdab:header title='bevestiging reservaties' image='bevestig' />
	<a href="<c:url value="/index.htm"/>">Voorstellingen</a>
	<c:choose>
		<c:when test="${mandjeAanwezig}">
			<a href="<c:url value="/reservatiemandje.htm"/>">Reservatiemandje</a>
			<h2>Stap 1: wie ben je ?</h2>
		</c:when>
		<c:otherwise>
			<p>U hebt nog geen reservaties, kies een voorstelling.</p>
		</c:otherwise>
	</c:choose>
</body>
</html>