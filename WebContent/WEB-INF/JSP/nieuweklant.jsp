<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri='http://vdab.be/tags' prefix='vdab'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<!DOCTYPE html>
<html lang="nl">
<vdab:head title="Nieuwe klant"/>
<body>
	<vdab:header title='nieuwe klant  ' image='nieuweklant' />
	<a href="<c:url value="/index.htm"/>">Voorstellingen</a>
	<c:choose>
		<c:when test="${not empty mandje}">
			<a href="<c:url value="/reservatiemandje.htm"/>">Reservatiemandje</a>
			<a href="<c:url value="/bevestiging.htm"/>">Bevestiging
				reservatie</a>
			<c:choose>
				<c:when test="${not empty gebruiker}">
					<p>U bent al aangemeld als klant, zie bevestiging Reservatie!</p>
				</c:when>
				<c:otherwise>
					<form method="post" id="nieuweklantform">
						<label>Voornaam:<input name="voornaam"
							value="${param.voornaam}"></label> <label>Familienaam:<input
							name="familienaam" value="${param.familienaam}"></label> <label>Straat:<input
							name="straat" value="${param.straat}"></label> <label>Huisnr.:<input
							name="huisnr" value="${param.huisnr}"></label> <label>Postcode:<input
							name="postcode" value="${param.postcode}"></label> <label>Gemeente:<input
							name="gemeente" value="${param.gemeente}"></label> <label>Gebruikersnaam:<input
							name="gebruikersnaam" value="${param.gebruikersnaam}"></label> <label>Paswoord:<input
							name="paswoord" type="password"></label> <label>Herhaal
							paswoord:<input name="herhaalpaswoord" type="password">
						</label> <input type="submit" value="OK" id="okknop">
					</form>
					<ul>
						<c:forEach var='eenfout' items='${fouten}'>
							<li>${eenfout}</li>
						</c:forEach>
					</ul>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<p>U hebt nog geen reservaties, kies een voorstelling.</p>
		</c:otherwise>
	</c:choose>
	<script>
		document.getElementById("nieuweklantform").onsubmit = function() {
			if (!navigator.cookieEnabled) {
				alert("Dit werkt enkel als cookies aanstaan");
				return false;
			}
			document.getElementById("okknop").disabled = true;
		};
	</script>
</body>
</html>
