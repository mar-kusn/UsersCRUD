<%--
  Created by IntelliJ IDEA.
  User: mariusz
  Date: 07.11.2020
  Time: 14:34
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="header.jsp" %>

<!-- Begin Page Content -->
<div class="container-fluid">

    <!-- Page Heading -->
    <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <h1 class="h3 mb-0 text-gray-800">UsersCRUD</h1>
        <a href="/users/edit" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm"><i
                class="fas fa-download fa-sm text-white-50"></i> Dodaj użytkownika</a>
    </div>


    <!-- DataTales Example -->
    <div class="card shadow mb-4">
        <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-primary">Edycja użytkownika</h6>
            <c:if test="${not empty param.errorMsg}">
                <div class="text-xs font-weight-bold text-success text-uppercase mb-1">
                    <div class="card mb-4 py-3 border-bottom-danger">
                        <div class="card-body"> ${errorMsg} </div>
                    </div>
                </div>
            </c:if>
        </div>
        <div class="card-body">
            <form method="post">
                <div class="form-group">
                    <label for="username">Nazwa</label>
                    <input value="${user.userName}" type="text" class="form-control" id="username" name="userName"
                           placeholder="Nazwa użytkownika">
                    <input type="hidden" name="id" value="${user.id}"/>
                </div>
                <div class="form-group">
                    <label for="email">Email</label>
                    <input value="${user.email}" type="email" class="form-control" id="email" name="email"
                           placeholder="Email użytkownika">
                </div>
                <div class="form-group">
                    <label for="password">Hasło</label>
                    <input type="password" class="form-control" id="password" name="password"
                           placeholder="Hasło użytkownika">
                </div>
                <button type="submit" class="btn btn-primary">Zapisz</button>
            </form>
        </div>
    </div>
    <!-- /.container-fluid -->

<%@include file="footer.jsp" %>