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
            <a href="/main-library/bandoc/BDDatTruocController/view?id=${thanhVien.id}">Kho Đặt Trước</a>
        </div>
        <div class="container">
            <div class="alert">
                <h3>Đặt Trước Thành Công</h3>
            </div>
            <div>
                <h2 class="title">THÔNG TIN CHI TIẾT TÀI LIỆU</h2>
                <div style="display:flex;">
                    <c:if test="${not empty taiLieu}">
                        <div class="left">
                            <img class="content-item-image" src="/main-library/commons/${taiLieu.anhBia}" />
                        </div>
                        <div class="right">
                            <form class="form-up" action="/main-library/bandoc/BDDatTruocController/order" method="post" onsubmit="return beforeOrder(this)">
                                <div>
                                    <input type="hidden" name="banDocId" value="${thanhVien.id}" />
                                    <input type="hidden" name="taiLieuId" value="${taiLieu.id}" />
                                    <input id="ngayDat" type="hidden" name="ngayDat" />
                                    <input id="ngayHetHan" type="hidden" name="ngayHetHan" />
                                    <div class="group">
                                        <label for="ten">Tên Tài Liệu:</label>
                                        <input id="ten" type="text" name="ten" value="${taiLieu.ten}" readonly />
                                    </div>
                                    <div class="group">
                                        <label for="tacGia">Tác Giả:</label>
                                        <input id="tacGia" type="text" name="tacGia" value="${taiLieu.tacGia}" readonly />
                                    </div>
                                    <div class="group">
                                        <label for="soLuong">Số Lượng:</label>
                                        <input style="font-weight: bold; color: red;" id="soLuong" type="text" name="soLuong" value="${taiLieu.soLuong}" readonly />
                                    </div>
                                    <div class="group">
                                        <label for="moTa">Mô Tả:</label>
                                        <textarea id="moTa" name="moTa" readonly>${taiLieu.moTa}</textarea>
                                    </div>
                                    <input type="hidden" name="anhBia" value="${taiLieu.anhBia}" />
                                </div>

                                <button type="submit" class="submit">Đặt Trước</button>
                            </form>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>

        <script>
            init();
            function init() {
                if (${param.isOrder == 'true'}) {
                    document.querySelector('.alert').style.display = 'block';
                }
                let list = '${datTruocIds}';
                if (list.includes('${param.id}')) {
                    document.querySelector('.submit').type = 'button';
                    document.querySelector('.submit').style.opacity = '0.2';
                }
            }
            function beforeOrder(element) {
                let soLuong = Number(document.getElementById('soLuong').value);
                if (soLuong <= 0) {
                    alert("Không đủ tài liệu trong kho!");
                    return false;
                }

                let date = new Date();
                document.getElementById('ngayDat').value = convertDate(date);
                date.setDate(date.getDate() + 3);
                document.getElementById('ngayHetHan').value = convertDate(date);
                return true;
            }
            function convertDate(date) {
                var nam = date.getFullYear();
                var thang = date.getMonth() + 1;
                var ngay = date.getDate();
                var newDate = nam + '-' + (thang < 10 ? '0' : '') + thang + '-' + (ngay < 10 ? '0' : '') + ngay;
                return newDate;
            }
        </script>
    </body>
</html>
