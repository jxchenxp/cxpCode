<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="p" uri="http://com/cxp/permission" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
    测试
    部门管理:</br>
    <p:check uri="/system/deptMgt/add">新增</br></p:check>
    <p:check uri="/system/deptMgt/delete">删除</br></p:check>
    <p:check uri="/system/deptMgt/edit">修改</br></p:check>
    <p:check uri="/system/deptMgt/query">查询</br></p:check>
    <p:check uri="/system/deptMgt/import">导入</br></p:check>
    <p:check uri="/system/deptMgt/exoprt">导出</br></p:check>
</body>
</html>