<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Editer les sous-titres</title>
</head>
<body>
<h2>Editing of last inserted Translation file : 
<c:if test="${not empty subtitles }" >
	<a href="<c:url value="download">
		<c:param name="file" value="${subtitles.get(0).getFilename()}"/></c:url>">
		<c:out value="${subtitles.get(0).getFilename()}"></c:out>
	</a>
</c:if>
</h2>
<p>At any time, you may upload a new transation file : <a href="fileme">File me now</a></p>
    <form method="post">    
        <input type="submit" style="position:fixed; top: 10px; right: 10px;" />
	    <table>
	        <c:forEach items="${ subtitles }" var="sequence">
	        	<tr>
	        		<td style="text-align:right;">Sequence</td>
	        		<td style="text-align:right"><c:out value="${ sequence.getSequence() }" /></td>
	        	</tr>
	        	<c:forEach items="${sequence.getStrings()}" var="string">
	        		<tr>
	        			<td style="text-align:right;"><c:out value="${ string }"></c:out></td>
	        			<td><input type="text" name="" id="" size="35" /></td>
	        		</tr>
	        	</c:forEach>
	    	</c:forEach>
	    </table>
    </form>
</body>
</html>