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
		<h1>Deposit Form</h1>
		<form action="depositForm.mm">
			<table>
				<tr>
					<td>Account Number:</td>
					<td><input type="number" name="accId"></td>
				</tr>
				<tr>
					<td>Amount:</td>
					<td><input type="number" name="amount"></td>
				</tr>
				<tr>
					<td><input type="submit" name="submit" value="Submit">
					</td>
				</tr>
			</table>
		</form>
	</center>
	<div>
		<jsp:include page="homeLink.html"></jsp:include>
	</div>
</body>
</html>