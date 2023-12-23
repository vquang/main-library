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
            <a href="/main-library/nhanvien/NVKhoTaiLieu.jsp">Kho Tài Liệu</a>
            <a href="/main-library/nhanvien/NVNhapTaiLieu.jsp?first=true">Nhập Tài Liệu</a>
            <a href="/main-library/nhanvien/NVMuonTaiLieu.jsp?first=true">Mượn Tài Liệu</a>
            <a href="/main-library/nhanvien/NVTraTaiLieu.jsp?first=true">Trả Tài Liệu</a>
        </div>
        <div class="container">
            <h2 class="title">THÔNG TIN CHI TIẾT TÀI LIỆU</h2>
            <div>
                <div style="display: flex">
                    <div class="left">
                        <c:if test="${not empty img}">
                            <img class="content-item-image" src="/main-library/commons/${img}" />
                        </c:if>
                    </div>
                    <div class="right">
                        <form class="form-img" action="/main-library/commons/UploadImageController" method="post" enctype="multipart/form-data">
                            <input type="file" name="file" required />
                            <button type="submit" class="submit"><i class="fa-solid fa-upload"></i></button>
                        </form>
                        <form class="form-up" action="/main-library/nhanvien/NVTaiLieuController/insert" method="post">
                            <div>
                                <h3>Thông Tin Tài Liệu</h3>
                                <div class="group">
                                    <label for="ten">Tên Tài Liệu:</label>
                                    <input id="ten" type="text" name="ten" required />
                                </div>
                                <div class="group">
                                    <label for="tacGia">Tác Giả:</label>
                                    <input id="tacGia" type="text" name="tacGia" required />
                                </div>
                                <div class="group">
                                    <label for="moTa">Mô Tả:</label>
                                    <textarea id="moTa" name="moTa"></textarea>
                                </div>
                                <input type="hidden" name="soLuong" value="0" />
                                <c:if test="${not empty img}">
                                    <input type="hidden" name="anhBia" value="${img}" />
                                </c:if>
                            </div>
                            <button type="submit" class="submit">Submit</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

    </body>
</html>
