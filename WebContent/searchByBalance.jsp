<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="searchByBalance.mm">
		Enter Minimum Balance: <input type="number" name="txtMinBalance" /><br />
		Enter Maximum Balance: <input type="number" name="txtMaxBalance" /><br />

		<br /> <input type="submit" value="Submit">
		<div>
			<jsp:include page="homeLink.html"></jsp:include>
		</div>


	</form>
</body>
</html>