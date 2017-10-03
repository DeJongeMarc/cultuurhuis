<%@ page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@taglib uri='http://vdab.be/tags' prefix='vdab'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<!DOCTYPE html>
<html lang="nl">
<head>
<title>Reserveren</title>
<link rel='icon' href='images/favicon.ico'>
<meta name='viewport' content='width=device-width,initial-scale=1'>
<link rel='stylesheet' href='styles/default.css'>
</head>
<body>
<vdab:header image="reserveren" title="reserveren"/>
<a href="<c:url value="/index.htm"/>">Voorstellingen</a>
<c:choose>
<c:when test="${not empty fout}">
<div class='fout'>${fout}</div>
</c:when>
<c:when test="${empty voorstellingReserveren}">
<div class='fout'>Geen voorstellingen beschikbaar</div>
</c:when>
<c:otherwise>
<p>Voorstellingen:</p>
<c:out value='${voorstellingReserveren.titel}'/>
<p>Uitvoerders:</p>
<c:out value='${voorstellingReserveren.uitvoerders}'/>
<p>Datum:</p>
<c:out value='${voorstellingReserveren.datum}'/>
<p>Prijs:</p>
<c:out value='€${voorstellingReserveren.prijs}'/>
<p>Vrije plaatsen:</p>
<c:out value='${voorstellingReserveren.vrijePlaatsen}'/>
</c:otherwise>
</c:choose>
</body>
</html>