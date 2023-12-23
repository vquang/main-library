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
            <a href="/main-library/admin/ADNhaCungCapController/list" class="menu-active">Nhà Cung Cấp</a>
            <a href="/main-library/admin/ADThongKe.jsp">Thống Kê</a>
        </div>
        <div class="container">
            <div class="search-header">
                <a href="/main-library/admin/ADTaoMoiNhaCungCap.jsp" class="submit">Tạo Mới</a>
            </div>
            <h2 class="title">Danh Sách Nhà Cung Cấp</h2>
            <table>
                <tr>
                    <th>Mã</th>
                    <th>Tên Nhà Cung Cấp</th>
                    <th>Số Điện Thoại</th>
                    <th>Địa Chỉ</th>
                    <th class="th-tt">Thao Tác</th>
                </tr>
                <c:if test="${not empty listNCCs}">
                    <c:forEach var="ncc" items="${listNCCs}">
                        <tr id="${ncc.id}">
                            <td>${ncc.id}</td>
                            <td>${ncc.ten}</td>
                            <td>${ncc.soDienThoai}</td>
                            <td>${ncc.diaChi}</td>
                            <td><a style="padding-left:30px;" class="update" href="/main-library/admin/ADNhaCungCapController/select?id=${ncc.id}">
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
