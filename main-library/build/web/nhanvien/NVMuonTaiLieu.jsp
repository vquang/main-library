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
        <%
            String first = request.getParameter("first");
            if(first != null && first.equals("true")) {
                request.getSession().removeAttribute("listDatTruocs");
            }
            
        %>
        <div class="header">
            <a href="/main-library/nhanvien/NVTaiLieuController/view">Trang Chủ</a>
            <a href="/main-library/commons/TaiKhoanController/logout?vaiTro=1">Đăng Xuất</a>
        </div>
        <div class="menu">
            <a href="/main-library/nhanvien/NVKhoTaiLieu.jsp">Kho Tài Liệu</a>
            <a href="/main-library/nhanvien/NVNhapTaiLieu.jsp?first=true">Nhập Tài Liệu</a>
            <a href="/main-library/nhanvien/NVMuonTaiLieu.jsp?first=true" class="menu-active">Mượn Tài Liệu</a>
            <a href="/main-library/nhanvien/NVTraTaiLieu.jsp?first=true">Trả Tài Liệu</a>
        </div>
        <div class="container">
            <div class="content">
                <div class="search-header">
                    <form action="/main-library/nhanvien/NVBanDocController/muon-search" method="post" onsubmit="return beforeSearch()">
                        <div class="search-frame" style="padding:0">
                            <input type="text" name="search" placeholder="Tìm kiếm bạn đọc..." />
                            <button class="search" type="submit"><i class="fa-solid fa-magnifying-glass"></i></button>
                        </div>
                    </form>
                    <form action="/main-library/nhanvien/NVTaiLieuController/muon-search" method="post">
                        <div class="search-frame" style="padding:0">
                            <input type="text" name="search" placeholder="Tìm kiếm tài liệu..." />
                            <button class="search" type="submit"><i class="fa-solid fa-magnifying-glass"></i></button>
                        </div>
                    </form>
                </div>
                <div class="content" style="display: flex;">
                    <div style="width: 60%">
                        <c:if test="${not empty listBanDocs}">
                            <h3 style="text-align: start">Danh Sách Bạn Đọc</h3>
                            <table style="margin: 0;">
                                <tr>
                                    <th class="small">Mã</th>
                                    <th>Username</th>
                                    <th>Họ Tên</th>
                                    <th>Số Điện Thoại</th>
                                    <th>Địa Chỉ</th>
                                    <th>Email</th>
                                </tr>
                                <c:forEach var="banDoc" items="${listBanDocs}">
                                    <tr id="${banDoc.id}" onclick="send(this)">
                                        <td class="small">${banDoc.id}</td>
                                        <td>${banDoc.username}</td>
                                        <td>${banDoc.hoTen}</td>
                                        <td>${banDoc.soDienThoai}</td>
                                        <td>${banDoc.diaChi}</td>
                                        <td>${banDoc.email}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:if>

                        <c:if test="${not empty listTaiLieus}">
                            <h3 style="text-align: start">Danh Sách Tài Liệu</h3>
                            <table style="margin: 0;">
                                <tr>
                                    <th class="small">Mã</th>
                                    <th>Tên Tài Liệu</th>
                                    <th>Tác Giả</th>
                                    <th>Số Lượng</th>
                                </tr>
                                <c:forEach var="taiLieu" items="${listTaiLieus}">
                                    <tr id="${taiLieu.id}" onclick="clickTaiLieu(this)">
                                        <td class="small">${taiLieu.id}</td>
                                        <td>${taiLieu.ten}</td>
                                        <td>${taiLieu.tacGia}</td>
                                        <td>${taiLieu.soLuong}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:if>
                    </div>
                    <div style="width: 40%;">
                        <form onsubmit="return validateForm(this)" style="background: white; align-items: center;" action="/main-library/nhanvien/NVMuonTaiLieuController" method="post">
                            <input type="hidden" name="nhanVienId" value="${thanhVien.id}" />
                            <input id="taiLieuIds" type="hidden" name="taiLieuIds" />
                            <input id="datTruocIds" type="hidden" name="datTruocIds" />
                            <h3>Thông Tin Đã Chọn</h3>
                            <div class="group">
                                <label for="banDocId">Mã Bạn Đọc: </label>
                                <input id="banDocId" name="banDocId" type="text" readonly/>
                            </div>
                            <div class="group">
                                <label for="ten">Tên Bạn Đọc: </label>
                                <input id="ten" type="text" readonly/>
                            </div>
                            <div class="group">
                                <label for="ngayMuon">Ngày Mượn: </label>
                                <input id="ngayMuon" type="text" name="ngayMuon" readonly/>
                            </div>
                            <div class="group">
                                <label for="ngayPhaiTra">Ngày Phải Trả: </label>
                                <input id="ngayPhaiTra" type="text" name="ngayPhaiTra" readonly/>
                            </div>
                            <c:if test="${not empty listDatTruocs}">
                                <h3>Danh Sách Tài Liệu Đã Đặt Trước:</h3>
                                <table id="dt" style="margin: 0;">
                                    <tr>
                                        <th class="small">Mã</th>
                                        <th>Tên Tài Liệu</th>
                                    </tr>
                                    <c:forEach var="datTruoc" items="${listDatTruocs}">
                                        <tr>
                                        <input type="hidden" value="${datTruoc.id}" />
                                        <td class="small">${datTruoc.taiLieu.id}</td>
                                        <td>${datTruoc.taiLieu.ten}</td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </c:if>
                            <h3>Danh Sách Tài Liệu Mượn Thêm:</h3>
                            <table id="mt"></table>
                            <button type="submit" class="submit">Tạo Phiếu Mượn</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <script>
            init();
            function init() {
                // xóa localStorage nếu là lần đầu vào trang
                let first = '${param.first}';
                if (first !== null && first === 'true') {
                    localStorage.clear();
                }

                // đặt lại list tài liệu đặt trước vào localStorage
                setDatTruoc();
                // set ngày mượn và ngày phải trả vào form
                let date = new Date();
                document.getElementById('ngayMuon').value = convertDate(date);
                date.setDate(date.getDate() + 5);
                document.getElementById('ngayPhaiTra').value = convertDate(date);

                // set list tài liệu vào form
                let taiLieus = JSON.parse(localStorage.getItem("taiLieus"));
                if (taiLieus !== null) {
                    let html = `<tr><th class="small">Mã </th><th>Tên Tài Liệu </th><th class="small">Xóa </th></tr>`;
                    for (let i = 0; i < taiLieus.length; ++i) {
                        html += '<tr><td class="small">' + taiLieus[i].id + '</td><td>' + taiLieus[i].ten + '</td>'
                                + '<td class="small"><button style="width:50px;background:transparent;" onclick="deleteTaiLieu(this)"><i class="fa-solid fa-trash delete"></i></button></td></tr>'
                    }
                    document.getElementById('mt').innerHTML = html;
                }

                // set bạn đọc vào form
                let banDoc = JSON.parse(localStorage.getItem("banDocMuon"));
                if (banDoc !== null) {
                    document.getElementById("banDocId").value = banDoc.id;
                    document.getElementById("ten").value = banDoc.hoTen;
                }

                // set list id taiLieu và list id taiLieuMuon vào form
                document.getElementById("taiLieuIds").value = localStorage.getItem("taiLieuIds");
                let datTruocIds = JSON.parse(localStorage.getItem("datTruocIds"));
                if(datTruocIds === null) {
                    datTruocIds = [];
                }
                document.getElementById("datTruocIds").value = JSON.stringify(datTruocIds);
            }
            function send(element) {
                // lưu thông tin bạn đọc vào localStorage
                let banDoc = {
                    id: element.id,
                    hoTen: element.cells[2].textContent
                };
                localStorage.setItem("banDocMuon", JSON.stringify(banDoc));

                // lấy danh sách tài liệu đã đặt trước
                let id = element.closest('tr').id;
                let url = '/main-library/nhanvien/NVBanDocController/dat-truoc?id=' + id;
                window.location.href = url;
            }

            // xóa localStorage khi search bạn đọc
            function beforeSearch() {
                localStorage.clear();
            }

            function clickTaiLieu(element) {
                let taiLieu = {
                    id: element.id,
                    ten: element.cells[1].textContent
                };
                let taiLieuIds = JSON.parse(localStorage.getItem("taiLieuIds"));
                let taiLieus = JSON.parse(localStorage.getItem("taiLieus"));
                if (taiLieuIds === null) {
                    taiLieuIds = [];
                }
                if (taiLieus === null) {
                    taiLieus = [];
                }
                for (let i = 0; i < taiLieuIds.length; ++i) {
                    if (taiLieuIds[i] === taiLieu.id) {
                        return;
                    }
                }
                taiLieuIds.push(taiLieu.id);
                taiLieus.push(taiLieu);

                localStorage.setItem("taiLieuIds", JSON.stringify(taiLieuIds));
                localStorage.setItem("taiLieus", JSON.stringify(taiLieus));

                init();
            }

            function setDatTruoc() {
                let table = document.getElementById('dt');
                if (table !== null) {
                    let rows = table.querySelectorAll('tr');
                    let datTruocIds = [];
                    let taiLieuIds = [];
                    for (let i = 1; i < rows.length; ++i) {
                        datTruocIds.push(rows[i].querySelector('input[type="hidden"]').value);
                        taiLieuIds.push(rows[i].cells[0].textContent);
                    }
                    localStorage.setItem("datTruocIds", JSON.stringify(datTruocIds));
                    localStorage.setItem("taiLieuIds", JSON.stringify(taiLieuIds));
                }
            }

            function deleteTaiLieu(element) {
                let id = element.closest('tr').cells[0].textContent;
                let taiLieus = JSON.parse(localStorage.getItem("taiLieus"));
                let taiLieuIds = JSON.parse(localStorage.getItem("taiLieuIds"));

                for (let i = 0; i < taiLieus.length; ++i) {
                    if (taiLieus[i].id === id) {
                        taiLieus.splice(i, 1);
                    }
                }
                for (let i = 0; i < taiLieuIds.length; ++i) {
                    if (taiLieuIds[i] === id) {
                        taiLieuIds.splice(i, 1);
                    }
                }

                localStorage.setItem("taiLieus", JSON.stringify(taiLieus));
                localStorage.setItem("taiLieuIds", JSON.stringify(taiLieuIds));
                init();
            }

            function convertDate(date) {
                var nam = date.getFullYear();
                var thang = date.getMonth() + 1;
                var ngay = date.getDate();
                var newDate = nam + '-' + (thang < 10 ? '0' : '') + thang + '-' + (ngay < 10 ? '0' : '') + ngay;
                return newDate;
            }

            function validateForm(element) {
                init();
                var inputs = element.querySelectorAll('input');

                for (var i = 0; i < inputs.length; i++) {
                    if (inputs[i].value.trim() === '') {
                        alert('Vui lòng điền đầy đủ thông tin vào tất cả các trường.');
                        return false;
                    }
                }
                return true;
            }
        </script>
    </body>
</html>
