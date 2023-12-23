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
            <a href="/main-library/bandoc/BDTaiLieuController/view" class="header-active">Trang Chủ</a>
            <a href="/main-library/commons/TaiKhoanController/logout?vaiTro=0">Đăng Xuất</a>
        </div>
        <div class="menu">
            <a href="/main-library/bandoc/BDKhoTaiLieu.jsp">Kho Tài Liệu</a>
            <a href="/main-library/bandoc/BDDatTruocController/view?id=${thanhVien.id}">Kho Đặt Trước</a>
        </div>
        <div class="container">
            <h2 class="title">TOP NHỮNG TÀI LIỆU PHỔ BIẾN</h2>
            <div class="content">
                <c:if test="${not empty listTop10}">
                    <c:forEach var="taiLieu" items="${listTop10}">
                        <a class="content-item" href="/main-library/bandoc/BDTaiLieuController/select?id=${taiLieu.id}">
                            <img class="content-item-image" src="/main-library/commons/${taiLieu.anhBia}" />
                            <span class="content-item-text" style="color:black;font-size: 20px;">${taiLieu.ten}</span>
                            <span class="content-item-text" >Tác giả: ${taiLieu.tacGia}</span>
                        </a>
                    </c:forEach>
                </c:if>
            </div>
        </div>
    </body>
</html>
