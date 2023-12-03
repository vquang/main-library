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
            <a href="/main-library/admin/AdminHome.jsp">Trang Chủ</a>
            <a href="/main-library/commons/TaiKhoanController/logout?vaiTro=2">Đăng Xuất</a>
        </div>
        <div class="menu">
            <a href="/main-library/admin/ADThanhVienController/list?vaiTro=0">Bạn Đọc</a>
            <a href="/main-library/admin/ADThanhVienController/list?vaiTro=1">Nhân Viên</a>
            <a href="/main-library/admin/ADThanhVienController/list?vaiTro=2">Admin</a>
            <a href="/main-library/admin/ADThongKe.jsp" class="menu-active">Thống Kê</a>
        </div>
        <div class="container">
            <div class="search-header" style="margin-top: 10px;">
                <a onclick="doSearch(this)" id="tl" class="submit" style="margin-right:10px;">Tài Liệu</a>
                <a onclick="doSearch(this)" id ="bd" class="submit">Bạn Đọc</a>
            </div>
            <div class="content">
                <table>
                    <c:if test="${not empty thongKeTLs}">
                        <h1>Thống Kê Tài Liệu Theo Lượt Mượn</h1>
                        <tr>
                            <th>Mã</th>
                            <th>Tên Tài Liệu</th>
                            <th>Tác Giả</th>
                            <th>Số Lượng</th>
                            <th onclick="changeThuTu(this)">Số Lượt Mượn <i class="arrow fa-solid fa-arrow-down"></i></th>
                        </tr>
                        <c:forEach var="tk" items="${thongKeTLs}">
                            <tr>
                                <td>${tk.taiLieu.id}</td>
                                <td>${tk.taiLieu.ten}</td>
                                <td>${tk.taiLieu.tacGia}</td>
                                <td>${tk.taiLieu.soLuong}</td>
                                <td class="td-sort" style="font-weight: bold;color:red;">${tk.soLuotMuon}</td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    <c:if test="${not empty thongKeBDs}">
                        <h1>Thống Kê Bạn Đọc Theo Số Tài Liệu Đã Mượn</h1>
                        <tr>
                            <th>Mã</th>
                            <th>Họ Tên</th>
                            <th>Số Điện Thoại</th>
                            <th>Địa Chỉ</th>
                            <th>Email</th>
                            <th onclick="changeThuTu(this)">Số Tài Liệu Mượn <i class="arrow fa-solid fa-arrow-down"></i></th>
                        </tr>
                        <c:forEach var="tk" items="${thongKeBDs}">
                            <tr>
                                <td>${tk.banDoc.id}</td>
                                <td>${tk.banDoc.hoTen}</td>
                                <td>${tk.banDoc.soDienThoai}</td>
                                <td>${tk.banDoc.diaChi}</td>
                                <td>${tk.banDoc.email}</td>
                                <td class="td-sort" style="font-weight: bold;color:red;width:200px;">${tk.tongTaiLieu}</td>
                            </tr>
                        </c:forEach>
                    </c:if>
                </table>
            </div>
        </div>

        <script>
            init();
            function init() {
                let thuTu = localStorage.getItem("thuTu");
                let arrows = document.querySelectorAll('.arrow');
                if (thuTu !== null) {
                    for (let i = 0; i < arrows.length; ++i) {
                        if (thuTu === 'asc') {
                            arrows[i].classList.remove('fa-arrow-down');
                            arrows[i].classList.add('fa-arrow-up');
                        } else {
                            arrows[i].classList.remove('fa-arrow-up');
                            arrows[i].classList.add('fa-arrow-down');
                        }
                    }
                }
            }
            function doSearch(element) {
                let id = element.id;
                if (id === 'tl') {
                    localStorage.setItem("tk", 'tailieu');
                    localStorage.setItem("thuTu", 'desc');
                    window.location.href = "/main-library/admin/ADThongKeController/tailieu?thuTu=desc";
                } else if (id === 'bd') {
                    localStorage.setItem("tk", 'bandoc');
                    localStorage.setItem("thuTu", 'desc');
                    window.location.href = "/main-library/admin/ADThongKeController/bandoc?thuTu=desc";
                }
            }
            function changeThuTu(element) {
                let icon = element.querySelector('i');
                let url = '/main-library/admin/ADThongKeController/' + localStorage.getItem("tk");
                let thuTu = '';
                if (icon.classList.contains('fa-arrow-down')) {
                    icon.classList.remove('fa-arrow-down');
                    icon.classList.add('fa-arrow-up');
                    thuTu = 'asc';
                } else {
                    icon.classList.add('fa-arrow-down');
                    icon.classList.remove('fa-arrow-up');
                    thuTu = 'desc';
                }
                localStorage.setItem("thuTu", thuTu);
                window.location.href = url + '?thuTu=' + thuTu;
            }
        </script>
    </body>
</html>
