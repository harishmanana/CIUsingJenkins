<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">

<head>
<title>Keep-Board</title>
</head>

<body>
	<c:if test="${error != null}">
		<font color="red">${error}</font>
	</c:if>

	<Form action="add" method="post">
		<table>	
			<tr>
				<td>Note Title</td>
				<td><input type="text" name="noteTitle" /></td>
			</tr>
			<tr>
				<td>Contents</td>
				<td><Textarea name="noteContent" rows=4 cols=50></Textarea></td>
			</tr>
			<tr>
				<td>Note Status</td>
				<td><input type="text" name="noteStatus" /></td>
			</tr>
			<tr>
				<td colspan=2 align="center"><Input type="Submit" value="Send" /></td>
			</tr>
		</table>
	</Form>


	<!-- display all existing notes in a tabular structure with Id, Title,Content,Status, Created Date and Action -->
	<table border="1">
		<tr>
			<td width="50">Note Id</td>
			<td width="200">Note Title</td>
			<td width="300">Contents</td>
			<td width="100">Note Status</td>
			<td width="250">Created On</td>
			<td width="200">Action</td>
		</tr>

		<c:forEach items="${allNotes}" var="note">
			<tr>
				<td>${note.noteId}</td>
				<td>${note.noteTitle}</td>
				<td>${note.noteContent}</td>
				<td>${note.noteStatus}</td>
				<td>${note.createdAt}</td>
				<td><A href="delete?noteId=${note.noteId}">Delete</A></td>
			</tr>
		</c:forEach>
	</table>
</body>

</html>