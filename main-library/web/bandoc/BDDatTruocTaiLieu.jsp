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
            <a href="/main-library/bandoc/BDTaiLieuController/view">Trang Chủ</a>
            <a href="/main-library/commons/TaiKhoanController/logout?vaiTro=0">Đăng Xuất</a>
        </div>
        <div class="menu">
            <a href="/main-library/bandoc/BDKhoTaiLieu.jsp">Kho Tài Liệu</a>
            <a href="/main-library/bandoc/BDDatTruocController/view?id=${thanhVien.id}" class="menu-active">Kho Đặt Trước</a>
        </div>
        <div class="container">
            <div class="container">
                <h1>Danh Sách Tài Liệu Đặt Trước</h1>
                <table>
                    <tr>
                        <th>Mã</th>
                        <th>Tên Tài Liệu</th>
                        <th>Tác Giả</th>
                        <th>Ngày Đặt</th>
                        <th>Ngày Hết Hạn</th>
                        <th>Xóa</th>
                    </tr>
                    <c:choose>
                        <c:when test="${not empty listDatTruocs}">
                            <c:forEach var="datTruoc" items="${listDatTruocs}">
                                <tr class="tr" id="${datTruoc.id}">
                                    <td>${datTruoc.taiLieu.id}</td>
                                    <td>${datTruoc.taiLieu.ten}</td>
                                    <td>${datTruoc.taiLieu.tacGia}</td>
                                    <td>${datTruoc.ngayDat}</td>
                                    <td>${datTruoc.ngayHetHan}</td>
                                    <td class="small"><a onclick="onDelete(this)"><i class="fa-solid fa-trash delete"></i></a></td>
                                </tr>
                            </c:forEach>
                        </c:when>
                    </c:choose>
                </table>
            </div>
        </div>
    </div>

    <script>
        function onDelete(element) {
            let result = confirm("Xác Nhận Xóa?");
            if (result) {
                let id = element.closest('tr').id;
                let taiLieuId = element.closest('tr').cells[0].textContent;
                let url = '/main-library/bandoc/BDDatTruocController/cancel?banDocId=' + '${thanhVien.id}' + '&taiLieuId=' + taiLieuId + '&datTruocId=' + id;
                window.location.href = url;
            }
        }
    </script>
</body>
</html>
