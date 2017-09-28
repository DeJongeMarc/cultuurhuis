<%@tag description='header van de pagina' pageEncoding='UTF-8'%>
<%@attribute name='title' required="true" type="java.lang.String"%>
<%@attribute name='image' required="true" type="java.lang.String"%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%> 
<header>
<h1>Het Cultuurhuis: ${title}       <img alt="${image}" src="images/${image}.png"></h1>
</header>

