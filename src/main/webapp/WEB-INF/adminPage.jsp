<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin Page</title>
<style>
	table, td{
		border:1px solid black;
	}
</style>
</head>
<body>
    <h1>Welcome to the Admin Page <c:out value="${currUser.username}"></c:out></h1>
    
    <form id="logoutForm" method="POST" action="/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Logout!" />
    </form>
    <table>
    	<thead>
    		<tr>
    			<th>Name</th>
    			<th>Email</th>
   				<th>Action</th>
    		</tr>
    	</thead>
    	<tbody>
    		<c:forEach var="user" items="${users }">
    			<tr>
    				<td>${user.username }</td>
    				<td>${user.email }</td>
    				<c:if test="${user.roles.get(0).name.contains('ROLE_USER') }">
	    				<td><a href="/delete/${user.id }">Delete</a>    <a href="/admin/${user.id }">Make Admin</a></td>
    				
    				</c:if>
			    	<c:if test = "${user.roles.get(0).name.contains('ROLE_ADMIN')}">
			        	<td>Admin</td>
			    	</c:if>
    				
    			</tr>
    		</c:forEach>
    	</tbody>
    </table>
</body>
</html>