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
                <div class="search-header">
                    <form action="/main-library/nhanvien/NVTaiLieuController/kho-search" method="post">
                        <div class="search-frame" style="padding:0">
                            <input type="text" name="search" placeholder="Tìm kiếm tài liệu..." />
                            <button class="search" type="submit"><i class="fa-solid fa-magnifying-glass"></i></button>
                        </div>
                    </form>
                    <a href="/main-library/nhanvien/NVThemMoiTaiLieu.jsp" class="submit">Tạo Mới</a>
                </div>
                <h1>Kho Tài Liệu</h1>
                <c:if test="${not empty listTaiLieus}">
                    <table>
                        <tr>
                            <th>Mã</th>
                            <th>Tên Tài Liệu</th>
                            <th>Tác Giả</th>
                            <th>Số Lượng</th>
                            <th colspan="2">Thao Tác</th>
                        </tr>
                        <c:forEach var="taiLieu" items="${listTaiLieus}">
                            <tr id="${taiLieu.id}">
                                <td>${taiLieu.id}</td>
                                <td>${taiLieu.ten}</td>
                                <td>${taiLieu.tacGia}</td>
                                <td>${taiLieu.soLuong}</td>
                                <td class="small">
                                    <a href="/main-library/nhanvien/NVTaiLieuController/select?id=${taiLieu.id}">
                                        <i class="fa-solid fa-gear update"></i>
                                    </a>
                                </td>
                                <td class="small"><a onclick="onDelete(this)"><i class="fa-solid fa-trash delete"></i></a></td>
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
            function onDelete(element) {
                let result = confirm("Xác Nhận Xóa?");
                if (result) {
                    let id = element.closest('tr').id;
                    let url = '/main-library/nhanvien/NVTaiLieuController/delete?id=' + id;
                    window.location.href = url;
                }
            }
        </script>
    </body>
</html>
