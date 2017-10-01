<%@ tag description='menu' pageEncoding='UTF-8'%>
<%@attribute name='title' required="true" type="java.lang.String"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<nav>
	<ul>
		<c:forEach var='genre' items='${genres}'>
		<li><a href="<c:url value='/'/>" name='id' value='${genre.id}'>${genre.naam}</a></li>
			<!--  <li><label><input type='checkbox' name='id'
					value='${genre.id}'> <c:out value='${genre.naam}' /></label></li>-->
		</c:forEach>
	</ul>
</nav>

