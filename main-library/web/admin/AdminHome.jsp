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
            <a href="/main-library/admin/AdminHome.jsp" class="header-active">Trang Chủ</a>
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
            <div class="home-container">
                <a class="home-item" href="/main-library/admin/ADThanhVienController/list?vaiTro=0"
                   style="background-image: linear-gradient(45deg, rgb(0, 153, 153), rgb(0, 255, 204));">
                    <i class="fa-solid fa-user-group"></i>
                    <h1>Bạn Đọc</h1>
                </a>
                <a class="home-item" href="/main-library/admin/ADThanhVienController/list?vaiTro=1"
                   style="background-image: linear-gradient(45deg, rgb(255, 0, 102), rgb(255, 153, 153));">
                    <i class="fa-solid fa-user-group"></i>
                    <h1>Nhân Viên</h1>
                </a>
                <a class="home-item" href="/main-library/admin/ADThanhVienController/list?vaiTro=2"
                   style="background-image: linear-gradient(45deg, rgb(51, 153, 255), rgb(0, 255, 255));">
                    <i class="fa-solid fa-user-group"></i>
                    <h1>Admin</h1>
                </a>
            </div>
            <div class="home-container">
                <a class="home-item" href="/main-library/admin/ADNhaCungCapController/list"
                   style="background-image: linear-gradient(45deg, rgb(204, 51, 255), rgb(255, 153, 255));">
                    <i class="fa-solid fa-truck-field"></i>
                    <h1>Nhà Cung Cấp</h1>
                </a>
                <a class="home-item" href="/main-library/admin/ADThongKe.jsp"
                   style="background-image: linear-gradient(45deg, rgb(255, 153, 0), rgb(255, 255, 0));">
                    <i class="fa-solid fa-chart-line"></i>
                    <h1>Thống Kê</h1>
                </a>
            </div>
        </div>
    </body>
</html>
