<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="closeAccountForm.mm">
		<center>
			<h1>Close Account</h1>
			<table>
				<tr>
					<td>Account Number:</td>
					<td><input type="number" name="accountNumber"></td>
				</tr>

				<tr>
					<td></td>
					<td><input type="submit" name="submit" value="Close Account"></td>
				</tr>
				</form>
			</table>
		</center>
		<div>
			<jsp:include page="homeLink.html"></jsp:include>
		</div>
</body>
</html>