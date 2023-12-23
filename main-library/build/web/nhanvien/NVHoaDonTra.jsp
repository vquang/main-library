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
            <div>
                <form class="bill">
                    <div class="bill-header">
                        <h1>Hóa Đơn Trả Sách</h1>
                    </div>
                    <div style="padding: 50px;">
                        <div style="display: flex;justify-content: space-between;">
                            <div>
                                <h2>Thông Tin Bạn Đọc</h2>
                                <div class="group">
                                    <label>Mã Bạn Đọc: </label>
                                    <input type="text" name="maBanDoc" readonly value="${hoaDonTra.phieuTra.banDoc.id}">
                                </div>
                                <div class="group">
                                    <label>Tên Bạn Đọc: </label>
                                    <input type="text" name="hoTenBanDoc" readonly value="${hoaDonTra.phieuTra.banDoc.hoTen}">
                                </div>
                                <div class="group">
                                    <label>Số Điện Thoại: </label>
                                    <input type="text" name="soDienThoai" readonly value="${hoaDonTra.phieuTra.banDoc.soDienThoai}">
                                </div>
                                <div class="group">
                                    <label>Địa Chỉ: </label>
                                    <input type="text" name="diaChi" readonly value="${hoaDonTra.phieuTra.banDoc.diaChi}">
                                </div>
                                <div class="group">
                                    <label>Email: </label>
                                    <input type="text" name="email" readonly value="${hoaDonTra.phieuTra.banDoc.email}">
                                </div>
                            </div>
                            <div>
                                <h2>Nhân Viên Tạo Phiếu:</h2>
                                <div class="group">
                                    <label>Mã Nhân Viên: </label>
                                    <input type="text" name="maNhanVien" readonly value="${hoaDonTra.phieuTra.nhanVien.id}">
                                </div>
                                <div class="group">
                                    <label>Tên Nhân Viên: </label>
                                    <input type="text" name="hoTenNhanVien" readonly value="${hoaDonTra.phieuTra.nhanVien.hoTen}">
                                </div>
                            </div>
                        </div>
                        <div>
                            <h2>Danh Sách Tài Liệu:</h2>
                            <c:choose>
                                <c:when test="${not empty hoaDonTra.phieuTra.listTaiLieuMuons}">
                                    <div class="card-table" style="width:100%;">
                                        <table class="table-shadow card-large">
                                            <tr>
                                                <th>Mã</th>
                                                <th>Tên Tài Liệu</th>
                                                <th>Tác Giả</th>
                                                <th>Ngày Mượn</th>
                                                <th>Ngày Phải Trả</th>
                                            </tr>

                                            <c:forEach var="taiLieuMuon" items="${hoaDonTra.phieuTra.listTaiLieuMuons}">
                                                <tr>
                                                    <td>${taiLieuMuon.taiLieu.id}</td>
                                                    <td>${taiLieuMuon.taiLieu.ten}</td>
                                                    <td>${taiLieuMuon.taiLieu.tacGia}</td>
                                                    <td>${taiLieuMuon.ngayMuon}</td>
                                                    <td>${taiLieuMuon.ngayPhaiTra}</td>
                                                </tr>
                                            </c:forEach>
                                        </table>
                                    </div>
                                </c:when>
                            </c:choose>
                        </div>
                        <div style="margin-top: 20px;">
                            <div class="group">
                                <label>Ngày Trả: </label>
                                <input type="text" name="ngayTra" readonly value="${hoaDonTra.phieuTra.ngayTra}">
                            </div>
                            <div class="group">
                                <label>Tiền Phạt: </label>
                                <input type="text" name="tienPhat" readonly value="${hoaDonTra.tienPhat}">
                            </div>
                            <div class="group">
                                <label>Ghi Chú:</label>
                                <p>${hoaDonTra.ghiChu}</p>
                            </div>
                        </div>
                    </div>
                    <div class="bill-footer">
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
