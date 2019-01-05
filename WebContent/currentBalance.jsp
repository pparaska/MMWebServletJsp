<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<center>
		<h1>Get Current Balance</h1>
		<form action="currentBalance.mm">
			<tr>
				<td>Account Number:</td>
				<td><input type="number" name="currentBal"></td>
			</tr>

			<tr>

				<td><input type="submit" name="submit" value="Submit"></td>

			</tr>

		</form>
	</center>
	<div>
		<jsp:include page="homeLink.html"></jsp:include>
	</div>
</body>
</html>