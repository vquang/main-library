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
            <a href="/main-library/nhanvien/NVTaiLieuController/view">Trang Chủ</a>
            <a href="/main-library/commons/TaiKhoanController/logout?vaiTro=1">Đăng Xuất</a>
        </div>
        <div class="menu">
            <a href="/main-library/nhanvien/NVKhoTaiLieu.jsp" class="menu-active">Kho Tài Liệu</a>
            <a href="/main-library/nhanvien/NVNhapTaiLieu.jsp?first=true">Nhập Tài Liệu</a>
            <a href="/main-library/nhanvien/NVMuonTaiLieu.jsp?first=true">Mượn Tài Liệu</a>
            <a href="/main-library/nhanvien/NVTraTaiLieu.jsp?first=true">Trả Tài Liệu</a>
        </div>
        <div class="container">
            <div class="content">
                <div class="search-header" style="justify-content: space-between;">
                    <form action="/main-library/nhanvien/NVTaiLieuController/kho-search" method="post">
                        <div class="search-frame">
                            <input type="text" name="search" placeholder="Tìm kiếm tài liệu..." />
                            <button class="search" type="submit"><i class="fa-solid fa-magnifying-glass"></i></button>
                        </div>
                    </form>
                    <a href="/main-library/nhanvien/NVThemMoiTaiLieu.jsp" class="submit">Tạo Mới</a>
                </div>
                <h2 class="title">Kho Tài Liệu</h2>
                <c:if test="${not empty listTaiLieus}">
                    <table>
                        <tr class="tr-header">
                            <th>Mã</th>
                            <th>Tên Tài Liệu</th>
                            <th>Tác Giả</th>
                            <th>Số Lượng</th>
                            <th class="th-tt">Thao Tác</th>
                        </tr>
                        <c:forEach var="taiLieu" items="${listTaiLieus}">
                            <tr id="${taiLieu.id}">
                                <td>${taiLieu.id}</td>
                                <td>${taiLieu.ten}</td>
                                <td>${taiLieu.tacGia}</td>
                                <td>${taiLieu.soLuong}</td>
                                <td class="operation">
                                    <a style="padding-left:30px;" class="update" href="/main-library/nhanvien/NVTaiLieuController/select?id=${taiLieu.id}">
                                        <i class="fa-solid fa-gear"></i>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:if>
            </div>
        </div>

        <%
            request.getSession().removeAttribute("img");
        %>

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
