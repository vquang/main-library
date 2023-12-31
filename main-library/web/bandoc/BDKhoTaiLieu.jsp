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
            <a href="/main-library/bandoc/BDKhoTaiLieu.jsp" class="menu-active">Kho Tài Liệu</a>
            <a href="/main-library/bandoc/BDDatTruocController/view?id=${thanhVien.id}">Kho Đặt Trước</a>
        </div>
        <div class="container">
            <div class="search-header">
                <form action="/main-library/bandoc/BDTaiLieuController/search" method="post">
                    <div class="search-frame" style="padding:0">
                        <input type="text" name="search" placeholder="Tìm kiếm tài liệu..." />
                        <button class="search" type="submit"><i class="fa-solid fa-magnifying-glass"></i></button>
                    </div>
                </form>
            </div>
            <h2 class="title">Kho Tài Liệu</h2>
            <c:if test="${not empty listTaiLieus}">
                <div class="content">
                    <c:forEach var="taiLieu" items="${listTaiLieus}">
                        <a class="content-item" href="/main-library/bandoc/BDTaiLieuController/select?id=${taiLieu.id}">
                            <img class="content-item-image" src="/main-library/commons/${taiLieu.anhBia}" />
                            <span class="content-item-text" style="color:black;font-size: 20px;">${taiLieu.ten}</span>
                            <span class="content-item-text" >Tác giả: ${taiLieu.tacGia}</span>
                            <span class="content-item-text" >Số lượng: ${taiLieu.soLuong}</span>
                        </a>
                    </c:forEach>
                </div>
            </c:if>
        </div>
    </div>
</body>
</html>
