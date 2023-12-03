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
            <div class="content">
                <h1>TOP NHỮNG TÀI LIỆU PHỔ BIẾN</h1>
                <c:if test="${not empty listTop10}">
                    <div class="content-imgs">
                        <c:forEach var="taiLieu" items="${listTop10}">
                            <a class="content-item" href="/main-library/nhanvien/NVTaiLieuController/select?id=${taiLieu.id}">
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
        
        <%
            request.getSession().removeAttribute("img");
        %>
    </body>
</html>