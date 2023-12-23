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
            <a href="/main-library/nhanvien/NVNhapTaiLieu.jsp?first=true" class="menu-active">Nhập Tài Liệu</a>
            <a href="/main-library/nhanvien/NVMuonTaiLieu.jsp?first=true">Mượn Tài Liệu</a>
            <a href="/main-library/nhanvien/NVTraTaiLieu.jsp?first=true">Trả Tài Liệu</a>
        </div>
        <div class="container">
            <div>
                <div class="search-header">
                    <form action="/main-library/nhanvien/NVNhaCungCapController/search" method="post" onsubmit="return beforeSearch()">
                        <div class="search-frame" style="padding:0">
                            <input type="text" name="search" placeholder="Tìm kiếm nhà cung cấp..." />
                            <button class="search" type="submit"><i class="fa-solid fa-magnifying-glass"></i></button>
                        </div>
                    </form>
                    <form action="/main-library/nhanvien/NVTaiLieuController/nhap-search" method="post" style="margin-left: 50px;">
                        <div class="search-frame" style="padding:0">
                            <input type="text" name="search" placeholder="Tìm kiếm tài liệu..." />
                            <button class="search" type="submit"><i class="fa-solid fa-magnifying-glass"></i></button>
                        </div>
                    </form>
                </div>
                <div style="display: flex;">
                    <div style="width: 58%;margin-right:2%;">
                        <c:if test="${not empty listNCCs}">
                            <h3 class="title" style="text-align: start">Danh Sách Nhà Cung Cấp</h3>
                            <table style="margin: 0;">
                                <tr>
                                    <th class="small">Mã</th>
                                    <th>Tên</th>
                                    <th>Số Điện Thoại</th>
                                    <th>Địa Chỉ</th>
                                </tr>
                                <c:forEach var="ncc" items="${listNCCs}">
                                    <tr id="${ncc.id}" onclick="clickNCC(this)">
                                        <td class="small">${ncc.id}</td>
                                        <td>${ncc.ten}</td>
                                        <td>${ncc.soDienThoai}</td>
                                        <td>${ncc.diaChi}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:if>

                        <c:if test="${not empty listTaiLieus}">
                            <h3 class="title" style="text-align: start">Danh Sách Tài Liệu</h3>
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
                        <h3 class="title">Thông Tin Đã Chọn</h3>
                        <form class="pre-bill" onsubmit="return validateForm(this)" action="/main-library/nhanvien/NVNhapTaiLieuController" method="post">
                            <input type="hidden" name="nhanVienId" value="${thanhVien.id}" />
                            <input id="listNhaps" type="hidden" name="listNhaps" />
                            <div class="group">
                                <label for="nccId">Mã NCC: </label>
                                <input id="nccId" type="text" name="nccId" readonly/>
                            </div>
                            <div class="group">
                                <label for="ten">Tên NCC: </label>
                                <input id="ten" type="text" readonly/>
                            </div>
                            <div class="group">
                                <label for="ngayNhap">Ngày Nhập: </label>
                                <input id="ngayNhap" type="text" name="ngayNhap" readonly/>
                            </div>
                            <div class="group">
                                <label for="tongTien">Tổng Tiền (VND): </label>
                                <input id="tongTien" type="text" name="tongTien" readonly/>
                            </div>
                            <h3>Danh Sách Tài Liệu Nhập:</h3>
                            <table id="nhap" style="margin-bottom: 10px;"></table>
                            <button type="submit" class="submit">Tạo Hóa Đơn Nhập</button>
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

                // set ngày nhập vào form
                let date = new Date();
                document.getElementById('ngayNhap').value = convertDate(date);

                // set list tài liệu vào form
                let taiLieus = JSON.parse(localStorage.getItem("listNhaps"));
                let html = `<tr><th>Mã </th><th>Tên Tài Liệu </th><th>Số Lượng</th><th>Giá Nhập</th><th>Xóa </th></tr>`;
                if (taiLieus !== null) {

                    for (let i = 0; i < taiLieus.length; ++i) {
                        html += '<tr><td>' + taiLieus[i].id + '</td><td>' + taiLieus[i].ten + '</td>'
                                + '<td><input type="number" name="soLuong" min="0" required pattern="[0-9]+" oninput="onChangeNumber()" value="' +
                                +taiLieus[i].soLuong + '"/></td>'
                                + '<td><input type="number" name="giaNhap" min="0" required pattern="[0-9]+" oninput="onChangeNumber()" value="' +
                                +taiLieus[i].giaNhap + '"/></td>'
                                + '<td><button style="width:50px;background:transparent;margin:auto;justify-content: start;" onclick="deleteTaiLieu(this)"><i class="fa-solid fa-trash delete"></i></button></td></tr>';
                    }
                }
                document.getElementById('nhap').innerHTML = html;
//                onChangeNumber();

                // setnhà cung cấp vào form
                let ncc = JSON.parse(localStorage.getItem("ncc"));
                if (ncc !== null) {
                    document.getElementById("nccId").value = ncc.id;
                    document.getElementById("ten").value = ncc.ten;
                }

                // set list id taiLieu và list id taiLieuMuon vào form
                document.getElementById("listNhaps").value = localStorage.getItem("listNhaps");
            }
            function clickNCC(element) {
                // lưu thông tin nhà cung cấp vào localStorage
                let ncc = {
                    id: element.id,
                    ten: element.cells[1].textContent
                };
                localStorage.setItem("ncc", JSON.stringify(ncc));

                init();
            }

            // xóa localStorage khi search nhà cung cấp
            function beforeSearch() {
                localStorage.clear();
            }

            function clickTaiLieu(element) {
                let taiLieu = {
                    id: element.id,
                    ten: element.cells[1].textContent,
                    soLuong: 0,
                    giaNhap: 0
                };
                let listNhaps = JSON.parse(localStorage.getItem("listNhaps"));
                if (listNhaps === null) {
                    listNhaps = [];
                }
                for (let i = 0; i < listNhaps.length; ++i) {
                    if (listNhaps[i].id === taiLieu.id) {
                        return;
                    }
                }
                listNhaps.push(taiLieu);

                localStorage.setItem("listNhaps", JSON.stringify(listNhaps));

                init();
            }

            function deleteTaiLieu(element) {
                let id = element.closest('tr').cells[0].textContent;
                let listNhaps = JSON.parse(localStorage.getItem("listNhaps"));

                for (let i = 0; i < listNhaps.length; ++i) {
                    if (listNhaps[i].id === id) {
                        listNhaps.splice(i, 1);
                    }
                }

                localStorage.setItem("listNhaps", JSON.stringify(listNhaps));
                init();
            }

            function convertDate(date) {
                var nam = date.getFullYear();
                var thang = date.getMonth() + 1;
                var ngay = date.getDate();
                var newDate = nam + '-' + (thang < 10 ? '0' : '') + thang + '-' + (ngay < 10 ? '0' : '') + ngay;
                return newDate;
            }

            function onChangeNumber() {
                let listNhaps = JSON.parse(localStorage.getItem("listNhaps"));
                let table = document.getElementById('nhap');
                let rows = table.querySelectorAll('tr');
                let tong = 0;
                for (let i = 1; i < rows.length; ++i) {

                    let soLuong = rows[i].querySelector('input[name="soLuong"]').value;
                    let giaNhap = rows[i].querySelector('input[name="giaNhap"]').value;

                    if (soLuong !== null && giaNhap !== null) {
                        soLuong = parseInt(soLuong);
                        giaNhap = parseInt(giaNhap);

                        tong += (soLuong * giaNhap);
                    }

                    for (let j = 0; j < listNhaps.length; ++j) {
                        if (listNhaps[j].id === rows[i].cells[0].textContent) {
                            listNhaps[j].soLuong = soLuong;
                            listNhaps[j].giaNhap = giaNhap;
                            break;
                        }
                    }
                }


                localStorage.setItem("listNhaps", JSON.stringify(listNhaps));

                document.getElementById("tongTien").value = tong;
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
