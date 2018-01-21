<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<%
if(session.getAttribute("hashcodeverified")==null || session.getAttribute("username")== null){
response.sendRedirect("Errorpage.jsp");
}
%>

<body>
<h1>Willkommen auf der RESET PASSWORT SEITE</h1>

<form>
<input type = "text" name="password">
<input type = "text" name="password2">
<input type="submit" value="Change Password"> 

</form>
</body>
</html>