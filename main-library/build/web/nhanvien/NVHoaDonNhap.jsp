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
                        <h1>Hóa Đơn Nhập</h1>
                    </div>
                    <div style="padding: 50px;">
                        <div style="display: flex;justify-content: space-between;">
                            <div>
                                <div class="group">
                                    <label>Mã Nhà Cung Cấp: </label>
                                    <input type="text" name="maBanDoc" readonly value="${hoaDonNhap.nhaCungCap.id}">
                                </div>
                                <div class="group">
                                    <label>Tên Nhà Cung Cấp: </label>
                                    <input type="text" name="hoTenBanDoc" readonly value="${hoaDonNhap.nhaCungCap.ten}">
                                </div>
                                <div class="group">
                                    <label>Số Điện Thoại: </label>
                                    <input type="text" name="soDienThoai" readonly value="${hoaDonNhap.nhaCungCap.soDienThoai}">
                                </div>
                                <div class="group">
                                    <label>Địa Chỉ: </label>
                                    <input type="text" name="diaChi" readonly value="${hoaDonNhap.nhaCungCap.diaChi}">
                                </div>
                            </div>
                            <div>
                                <div class="group">
                                    <label>Mã Nhân Viên: </label>
                                    <input type="text" name="maNhanVien" readonly value="${hoaDonNhap.nhanVien.id}">
                                </div>
                                <div class="group">
                                    <label>Tên Nhân Viên: </label>
                                    <input type="text" name="hoTenNhanVien" readonly value="${hoaDonNhap.nhanVien.hoTen}">
                                </div>
                            </div>
                        </div>
                        <div>
                            <h3>Danh Sách Tài Liệu:</h3>
                            <c:choose>
                                <c:when test="${not empty hoaDonNhap.listTaiLieuNhaps}">
                                    <div class="card-table" style="width:100%;">
                                        <table>
                                            <tr class="tr-header">
                                                <th>Mã</th>
                                                <th>Tên Tài Liệu</th>
                                                <th>Tác Giả</th>
                                                <th>Số Lượng Nhập</th>
                                                <th>Giá Nhập</th>
                                            </tr>

                                            <c:forEach var="taiLieuNhap" items="${hoaDonNhap.listTaiLieuNhaps}">
                                                <tr>
                                                    <td>${taiLieuNhap.taiLieu.id}</td>
                                                    <td>${taiLieuNhap.taiLieu.ten}</td>
                                                    <td>${taiLieuNhap.taiLieu.tacGia}</td>
                                                    <td>${taiLieuNhap.soLuong}</td>
                                                    <td>${taiLieuNhap.giaNhap}</td>
                                                </tr>
                                            </c:forEach>
                                        </table>
                                    </div>
                                </c:when>
                            </c:choose>
                        </div>
                        <div style="margin-top: 20px;">
                            <div class="group">
                                <label>Ngày Nhập </label>
                                <input type="text" name="ngayNhap" readonly value="${hoaDonNhap.ngayNhap}">
                            </div>
                            <div class="group">
                                <label>Tổng Tiền (VND): </label>
                                <input type="text" name="tongTien" readonly value="${hoaDonNhap.tongTien}">
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
