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
            <div class="login">
                <div class="login-left" style="background-image: linear-gradient(45deg, rgb(0, 153, 153), rgb(102, 255, 255));">
                    <h1>Chào Mừng Tới</h1>
                    <h1>Thư Viện</h1>
                </div>
                <form class="login-right login-nv"
                      action="/main-library/commons/TaiKhoanController/nhanvien-login" method="post">
                    <h3>Form Đăng Nhập Nhân Viên</h3>
                    <div class="group">
                        <label for="username">Username:</label>
                        <input id="username" type="text"  name="username" required/>
                    </div>
                    <div class="group">
                        <label for="password">Password:</label>
                        <input id="password" type="password"  name="password" required/>
                    </div>
                    <button type="submit" class="submit">Login</button>
                </form>
            </div>
            <div class="error">
                <h3>Thông Tin Không Chính Xác!</h3>
            </div>
        </div>

        <script>
            init();
            function init() {
                if (${param.error == 'true'}) {
                    document.querySelector('.error').style.display = 'block';
                }
            }
        </script>
    </body>
</html>