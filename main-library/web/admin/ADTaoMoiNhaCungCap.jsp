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
            <a href="/main-library/admin/ADNhaCungCapController/list">Nhà Cung Cấp</a>
            <a href="/main-library/admin/ADThongKe.jsp">Thống Kê</a>
        </div>
        <div class="container">
            <form class="form-mem"action="/main-library/admin/ADNhaCungCapController/insert" method="post" onsubmit="return validateForm(this)">
                <div class="mem-header">
                    <h3>Form Thông Tin Nhà Cung Cấp</h3>
                </div>
                <div style="padding:0 50px 50px 50px;">
                    <div class="group">
                        <label for="ten">Tên Nhà Cung Cấp:</label>
                        <input id="ten" type="text"  name="ten" required/>
                    </div>
                    <div class="group">
                        <label for="soDienThoai">Số Điện Thoại:</label>
                        <input id="soDienThoai" type="text"  name="soDienThoai" required/>
                    </div>
                    <div class="group">
                        <label for="diaChi">Địa Chỉ:</label>
                        <input id="diaChi" type="text"  name="diaChi" required/>
                    </div>
                    <button type="submit" class="submit">submit</button>
                </div>
            </form>
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
