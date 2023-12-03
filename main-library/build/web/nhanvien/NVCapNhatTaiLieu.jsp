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
                <h1>THÔNG TIN CHI TIẾT TÀI LIỆU</h1>
                <div class="content" style="display: flex;justify-content: center;">
                    <c:if test="${not empty taiLieu}">
                        <div class="left" style="width: 250px;background: white;">
                            <c:choose>
                                <c:when test="${not empty img}">
                                    <img class="content-item-image" src="/main-library/commons/${img}" />
                                </c:when>
                                <c:otherwise>
                                    <img class="content-item-image" src="/main-library/commons/${taiLieu.anhBia}" />
                                </c:otherwise>
                            </c:choose>
                            <form class="form-img" action="/main-library/commons/UploadImageController" method="post" enctype="multipart/form-data">
                                <input type="file" name="file" required />
                                <input type="hidden" name="id" value="${taiLieu.id}" />
                                <button type="submit" class="submit"><i class="fa-solid fa-upload"></i></button>
                            </form>
                        </div>
                        <div class="right" style="background: white;">
                            <form action="/main-library/nhanvien/NVTaiLieuController/update" method="post">
                                <h3>Thông Tin Tài Liệu</h3>

                                <div class="group">
                                    <label for="id">Mã Tài Liệu:</label>
                                    <input id="id" type="text" name="id" value="${taiLieu.id}" readonly />
                                </div>
                                <div class="group">
                                    <label for="ten">Tên Tài Liệu:</label>
                                    <input id="ten" type="text" name="ten" value="${taiLieu.ten}" required />
                                </div>
                                <div class="group">
                                    <label for="tacGia">Tác Giả:</label>
                                    <input id="tacGia" type="text" name="tacGia" value="${taiLieu.tacGia}" required />
                                </div>
                                <div class="group">
                                    <label for="moTa">Mô Tả:</label>
                                    <textarea id="moTa" name="moTa">${taiLieu.moTa}</textarea>
                                </div>
                                <c:choose>
                                    <c:when test="${not empty img}">
                                        <input type="hidden" name="anhBia" value="${img}" />
                                    </c:when>
                                    <c:otherwise>
                                        <input type="hidden" name="anhBia" value="${taiLieu.anhBia}" />
                                    </c:otherwise>
                                </c:choose>
                                <button onclick="submit" class="submit">Submit</button>

                            </form>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
        
    </body>
</html>
