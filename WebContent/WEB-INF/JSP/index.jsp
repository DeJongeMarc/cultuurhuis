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
<vdab:header title='voorstellingen' image='voorstellingen'/>
<h2>Genres</h2>
<nav>
	<ul>
		<c:forEach var='genre' items='${genres}'>
		<li><a href="<c:url value='/'><c:param name='id' value='${genre.id}'/></c:url>">${genre.naam}</a></li>
			<!--  <li><label><input type='checkbox' name='id'
					value='${genre.id}'> <c:out value='${genre.naam}' /></label></li>-->
		</c:forEach>
	</ul>
</nav>
<c:if test='${not empty gekozenGenre}'>
		<h2><c:out value='${gekozenGenre.naam} '/>voorstellingen</h2>
		<ul class='zebra'>
			<c:forEach var='genre' items='${genreVoorstellingen}'>
				<li><c:out value='${genre.naam}' /></li>
			</c:forEach>
		</ul>
	</c:if>
</body>
</html>
