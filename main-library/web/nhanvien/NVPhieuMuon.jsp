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
            <div class="content" style="margin-top: 20px;">
                <form style="background: white; width:820px;align-items: center;">
                    <h1>Phiếu Mượn Sách</h1>
                    <div>
                        <h2>Thông Tin Bạn Đọc</h2>
                        <div class="group">
                            <label>Mã Bạn Đọc: </label>
                            <input type="text" name="maBanDoc" readonly value="${phieuMuon.banDoc.id}">
                        </div>
                        <div class="group">
                            <label>Tên Bạn Đọc: </label>
                            <input type="text" name="hoTenBanDoc" readonly value="${phieuMuon.banDoc.hoTen}">
                        </div>
                        <div class="group">
                            <label>Số Điện Thoại: </label>
                            <input type="text" name="soDienThoai" readonly value="${phieuMuon.banDoc.soDienThoai}">
                        </div>
                        <div class="group">
                            <label>Địa Chỉ: </label>
                            <input type="text" name="diaChi" readonly value="${phieuMuon.banDoc.diaChi}">
                        </div>
                        <div class="group">
                            <label>Email: </label>
                            <input type="text" name="email" readonly value="${phieuMuon.banDoc.email}">
                        </div>
                    </div>
                    <div>
                        <h2>Nhân Viên Tạo Phiếu:</h2>
                        <div class="group">
                            <label>Mã Nhân Viên: </label>
                            <input type="text" name="maNhanVien" readonly value="${phieuMuon.nhanVien.id}">
                        </div>
                        <div class="group">
                            <label>Tên Nhân Viên: </label>
                            <input type="text" name="hoTenNhanVien" readonly value="${phieuMuon.nhanVien.hoTen}">
                        </div>
                    </div>
                    <div>
                        <h2>Thông Tin Tài Liệu:</h2>
                        <div class="group">
                            <label>Ngày Mượn: </label>
                            <input type="text" name="ngayMuon" readonly value="${phieuMuon.ngayMuon}">
                        </div>
                        <div class="group">
                            <label>Ngày Phải Trả: </label>
                            <input type="text" name="ngayPhaiTra" readonly value="${phieuMuon.ngayPhaiTra}">
                        </div>
                        <h2>Danh Sách Tài Liệu:</h2>
                    </div>
                    <div>
                        <c:choose>
                            <c:when test="${not empty phieuMuon.listTaiLieuMuons}">
                                <div class="card-table" style="width:100%;">
                                    <table class="table-shadow card-large">
                                        <tr>
                                            <th>Mã</th>
                                            <th>Tên Tài Liệu</th>
                                            <th>Tác Giả</th>
                                        </tr>

                                        <c:forEach var="taiLieuMuon" items="${phieuMuon.listTaiLieuMuons}">
                                            <tr>
                                                <td>${taiLieuMuon.taiLieu.id}</td>
                                                <td>${taiLieuMuon.taiLieu.ten}</td>
                                                <td>${taiLieuMuon.taiLieu.tacGia}</td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </div>
                            </c:when>
                        </c:choose>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
