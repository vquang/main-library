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
            <a href="/main-library/admin/AdminHome.jsp">Trang Chủ</a>
            <a href="/main-library/commons/TaiKhoanController/logout?vaiTro=2">Đăng Xuất</a>
        </div>
        <div class="menu">
            <a href="/main-library/admin/ADThanhVienController/list?vaiTro=0">Bạn Đọc</a>
            <a href="/main-library/admin/ADThanhVienController/list?vaiTro=1">Nhân Viên</a>
            <a href="/main-library/admin/ADThanhVienController/list?vaiTro=2">Admin</a>
            <a href="/main-library/admin/ADThongKe.jsp">Thống Kê</a>
        </div>
        <div class="container">
            <div class="content" style="margin:10px;">
                <form style="margin: auto;width:400px;background: white;"
                      action="/main-library/admin/ADThanhVienController/insert" method="post" onsubmit="return validateForm(this)">
                    <c:choose>
                        <c:when test="${param.vaiTro == 0}">
                            <c:set var="vaiTro" value="Bạn Đọc"/>
                        </c:when>
                        <c:when test="${param.vaiTro == 1}">
                            <c:set var="vaiTro" value="Nhân Viên"/>
                        </c:when>
                        <c:when test="${param.vaiTro == 2}">
                            <c:set var="vaiTro" value="Admin"/>
                        </c:when>
                    </c:choose>
                    <h3>Form Tạo Mới ${vaiTro}</h3>
                    <input type="hidden" name="vaiTro" value="${param.vaiTro}" />
                    <div class="group">
                        <label for="username">Username:</label>
                        <input id="username" type="text"  name="username" required/>
                    </div>
                    <div class="group">
                        <label for="password">Password:</label>
                        <input id="password" type="password"  name="password" required/>
                    </div>
                    <div class="group">
                        <label for="hoTen">Họ Tên:</label>
                        <input id="hoTen" type="text"  name="hoTen" required/>
                    </div>
                    <div class="group">
                        <label for="soDienThoai">Số Điện Thoại:</label>
                        <input id="soDienThoai" type="text"  name="soDienThoai" required/>
                    </div>
                    <div class="group">
                        <label for="diaChi">Địa Chỉ:</label>
                        <input id="diaChi" type="text"  name="diaChi" required/>
                    </div>
                    <div class="group">
                        <label for="email">Email:</label>
                        <input id="email" type="text"  name="email" required />
                    </div>
                    <button type="submit" class="submit">submit</button>
                </form>
            </div>
        </div>

        <script>
            function validateForm(element) {
                let phoneRegex = /^[0-9]{10}$/;
                let phoneInput = element.querySelector('input[name="soDienThoai"]');
                let soDienThoai = phoneInput.value;
                if (!phoneRegex.test(soDienThoai)) {
                    phoneInput.style.border = '2px solid red';
                    phoneInput.style.boxShadow = '2px 2px 5px red';
                    return false;
                }
                return true;
            }
        </script>
    </body>
</html>
