<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/include/head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加分组</title>
<script type="text/javascript">
function turnback(){
	window.location.href="<%=request.getContextPath() %>/mi001.do";
}
</script>
</head>
<body>
<form method="post" action="<%=request.getContextPath() %>/mi001.do?method=save">
<div><c:out value="${resultCode}"></c:out></div>
<table>
	<tr><td>组号</td><td><input id="privilegeId" name="privilegeId" type="text" /></td></tr>
	<tr><td>组名</td><td><input id="groupname" name="groupname"  type="text" /></td></tr>
	<tr><td colSpan="2" align="center"><input type="submit" value="提交"/><input type="button" onclick="turnback()" value="返回" /> </td></tr>
</table>

</form>
</body>
</html>