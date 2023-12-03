<%-- 
    Document   : NhanVienHome
    Created on : Dec 2, 2023, 10:22:37 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="/main-library/commons/styles.css">
        <link rel="stylesheet" type="text/css" href="/main-library/commons/icons/css/all.css">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="header">
        </div>
        <div class="container">
            <div class="content">
                <form style="background: white; width: 400px;"
                      action="/main-library/commons/TaiKhoanController/admin-login" method="post">
                    <h3>Form Đăng Nhập Admin</h3>
                    <div class="group">
                        <label for="username">Username:</label>
                        <input id="username" type="text"  name="username" required/>
                    </div>
                    <div class="group">
                        <label for="password">Password:</label>
                        <input id="password" type="password"  name="password" required/>
                    </div>
                    <button type="submit" class="submit">submit</button>
                </form>
            </div>
        </div>
    </body>
</html>