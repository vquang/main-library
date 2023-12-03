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
            <a href="/main-library/nhanvien/NVTraTaiLieu.jsp?first=true" class="menu-active">Trả Tài Liệu</a>
        </div>
        <div class="container">
            <div class="content">
                <div class="search-header">
                    <form action="/main-library/nhanvien/NVBanDocController/tra-search" method="post" onsubmit="return beforeSearch()">
                        <div class="search-frame" style="padding:0">
                            <input type="text" name="search" placeholder="Tìm kiếm bạn đọc..." />
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

                        <c:if test="${not empty listMuons}">
                            <h3 style="text-align: start">Danh Sách Tài Liệu</h3>
                            <table style="margin: 0;">
                                <tr>
                                    <th class="small">Mã</th>
                                    <th>Tên Tài Liệu</th>
                                    <th>Tác Giả</th>
                                    <th>Số Lượng</th>
                                    <th>Ngày Mượn</th>
                                    <th>Ngày Phải Trả</th>
                                </tr>
                                <c:forEach var="muon" items="${listMuons}">
                                    <tr id="${muon.id}" onclick="clickTaiLieu(this)">
                                        <td class="small">${muon.taiLieu.id}</td>
                                        <td>${muon.taiLieu.ten}</td>
                                        <td>${muon.taiLieu.tacGia}</td>
                                        <td>${muon.taiLieu.soLuong}</td>
                                        <td>${muon.ngayMuon}</td>
                                        <td>${muon.ngayPhaiTra}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:if>
                    </div>
                    <div style="width: 40%;">
                        <form onsubmit="return validateForm(this)" style="background: white; align-items: center;" action="/main-library/nhanvien/NVTraTaiLieuController" method="post">
                            <input type="hidden" name="nhanVienId" value="${thanhVien.id}" />
                            <input id="taiLieuIds" type="hidden" name="taiLieuIds" />
                            <input id="muonIds" type="hidden" name="muonIds" />
                            <h3>Thông Tin Đã Chọn</h3>
                            <div class="group">
                                <label for="banDocId">Mã Bạn Đọc: </label>
                                <input id="banDocId" type="text" name="banDocId" readonly/>
                            </div>
                            <div class="group">
                                <label for="ten">Tên Bạn Đọc: </label>
                                <input id="ten" type="text" readonly/>
                            </div>
                            <div class="group">
                                <label for="ngayTra">Ngày Trả </label>
                                <input id="ngayTra" type="text" name="ngayTra" readonly/>
                            </div>
                            <div class="group">
                                <label for="tienPhat">Tiền Phạt (VND): </label>
                                <input id="tienPhat" type="text" name="tienPhat" readonly/>
                            </div>
                            <div class="group">
                                <label for="ghiChu">Ghi Chú:</label>
                                <textarea style="height:50px;;" id="ghiChu" name="ghiChu"></textarea>
                            </div>
                            <h3>Danh Sách Tài Liệu Muốn Trả:</h3>
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
                // set ngày trả vào form
                let date = new Date();
                document.getElementById('ngayTra').value = convertDate(date);

                // set list tài liệu vào form
                let soNgayMuon = 0;
                let taiLieus = JSON.parse(localStorage.getItem("taiLieus"));
                if (taiLieus !== null) {
                    let html = `<tr><th class="small">Mã </th><th>Tên Tài Liệu </th><th>Ngày Mượn</th><th>Ngày Phải Trả</th><th class="small">Xóa </th></tr>`;
                    for (let i = 0; i < taiLieus.length; ++i) {
                        soNgayMuon += calculateDate(taiLieus[i].ngayPhaiTra, date);
                        html += '<tr><td class="small">' + taiLieus[i].id + '</td><td>' + taiLieus[i].ten + '</td>'
                                + '<td>' + taiLieus[i].ngayMuon + '</td>'
                                + '<td>' + taiLieus[i].ngayPhaiTra + '</td>'
                                + '<td class="small"><button style="width:50px;background:transparent;" onclick="deleteTaiLieu(this)"><i class="fa-solid fa-trash delete"></i></button></td></tr>'
                    }
                    document.getElementById('mt').innerHTML = html;
                }

                // set bạn đọc vào form
                let banDoc = JSON.parse(localStorage.getItem("banDocTra"));
                if (banDoc !== null) {
                    document.getElementById("banDocId").value = banDoc.id;
                    document.getElementById("ten").value = banDoc.hoTen;
                }

                // set list id taiLieu và list id taiLieuMuon vào form
                document.getElementById("taiLieuIds").value = localStorage.getItem("taiLieuIds");
                let muonIds = JSON.parse(localStorage.getItem("muonIds"));
                if (muonIds === null) {
                    muonIds = [];
                }
                document.getElementById("muonIds").value = JSON.stringify(muonIds);
                document.getElementById('tienPhat').value = soNgayMuon * 2000;

            }
            function send(element) {
                // lưu thông tin bạn đọc vào localStorage
                let banDoc = {
                    id: element.id,
                    hoTen: element.cells[2].textContent
                };
                localStorage.setItem("banDocTra", JSON.stringify(banDoc));

                // lấy danh sách tài liệu đã đặt trước
                let id = element.closest('tr').id;
                let url = '/main-library/nhanvien/NVBanDocController/dang-muon?id=' + id;
                window.location.href = url;
            }

            // xóa localStorage khi search bạn đọc
            function beforeSearch() {
                localStorage.clear();
            }

            function clickTaiLieu(element) {
                let taiLieu = {
                    id: element.id,
                    taiLieuId: element.cells[0].textContent,
                    ten: element.cells[1].textContent,
                    ngayMuon: element.cells[4].textContent,
                    ngayPhaiTra: element.cells[5].textContent
                };
                let muonIds = JSON.parse(localStorage.getItem("muonIds"));
                let taiLieuIds = JSON.parse(localStorage.getItem("taiLieuIds"));
                let taiLieus = JSON.parse(localStorage.getItem("taiLieus"));
                if (muonIds === null) {
                    muonIds = [];
                }
                if (taiLieuIds === null) {
                    taiLieuIds = [];
                }
                if (taiLieus === null) {
                    taiLieus = [];
                }
                for (let i = 0; i < taiLieuIds.length; ++i) {
                    if (taiLieuIds[i] === taiLieu.taiLieuId) {
                        return;
                    }
                }
                muonIds.push(taiLieu.id)
                taiLieuIds.push(taiLieu.taiLieuId);
                taiLieus.push(taiLieu);

                localStorage.setItem("muonIds", JSON.stringify(muonIds));
                localStorage.setItem("taiLieuIds", JSON.stringify(taiLieuIds));
                localStorage.setItem("taiLieus", JSON.stringify(taiLieus));

                init();
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

            function calculateDate(dateString1, dateString2) {
                // Chuyển đổi chuỗi ngày thành đối tượng Date
                const date1 = new Date(dateString1);
                const date2 = new Date(dateString2);

                // Tính toán khoảng cách giữa hai ngày (theo miligiây)
                const timeDifference = date2.getTime() - date1.getTime();
                if (timeDifference <= 0) {
                    return 0;
                }

                // Chuyển đổi khoảng cách từ miligiây sang ngày
                const daysDifference = Math.ceil(timeDifference / (1000 * 60 * 60 * 24));

                return daysDifference;
            }
        </script>
    </body>
</html>