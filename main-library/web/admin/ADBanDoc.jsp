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
            <a href="/main-library/admin/ADThanhVienController/list?vaiTro=0" class="menu-active">Bạn Đọc</a>
            <a href="/main-library/admin/ADThanhVienController/list?vaiTro=1">Nhân Viên</a>
            <a href="/main-library/admin/ADThanhVienController/list?vaiTro=2">Admin</a>
            <a href="/main-library/admin/ADNhaCungCapController/list">Nhà Cung Cấp</a>
            <a href="/main-library/admin/ADThongKe.jsp">Thống Kê</a>
        </div>
        <div class="container">
            <div class="search-header">
                <a href="/main-library/admin/ADTaoMoiThanhVien.jsp?vaiTro=0" class="submit">Tạo Mới</a>
            </div>
            <h2 class="title">Danh Sách Bạn Đọc</h2>
            <table>
                <tr>
                    <th>Mã</th>
                    <th>Username</th>
                    <th>Họ Tên</th>
                    <th>Số Điện Thoại</th>
                    <th>Địa Chỉ</th>
                    <th>Email</th>
                    <th>Vai Trò</th>
                    <th class="th-tt">Thao Tác</th>
                </tr>
                <c:if test="${not empty listThanhViens}">
                    <c:forEach var="tv" items="${listThanhViens}">
                        <c:choose>
                            <c:when test="${tv.vaiTro == 0}">
                                <c:set var="vaiTro" value="Bạn Đọc"/>
                            </c:when>
                            <c:when test="${tv.vaiTro == 1}">
                                <c:set var="vaiTro" value="Nhân Viên"/>
                            </c:when>
                            <c:when test="${tv.vaiTro == 2}">
                                <c:set var="vaiTro" value="Admin"/>
                            </c:when>
                        </c:choose>
                        <tr id="${tv.id}">
                            <td>${tv.id}</td>
                            <td>${tv.username}</td>
                            <td>${tv.hoTen}</td>
                            <td>${tv.soDienThoai}</td>
                            <td>${tv.diaChi}</td>
                            <td>${tv.email}</td>
                            <td style="font-weight: bold; color: red;">${vaiTro}</td>
                            <td><a style="padding-left:30px;" class="update" href="/main-library/admin/ADThanhVienController/select?id=${tv.id}">
                                    <i class="fa-solid fa-gear"></i></a>
                            </td>

                        </tr>
                    </c:forEach>
                </c:if>
            </table>
        </div>

        <script>
            init();
            function init() {
                if (${param.isDelete == 'false'}) {
                    alert("Không thể xóa do có ràng buộc với hóa đơn!");
                }
            }
        </script>
    </body>
</html>
