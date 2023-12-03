/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.HoaDonNhap;
import entity.NhaCungCap;
import entity.TaiLieu;
import entity.TaiLieuNhap;
import entity.ThanhVien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class NhapTaiLieuDAO {

    // TẠO 1 HÓA ĐƠN NHẬP TÀI LIỆU => TRẢ LẠI ID HÓA ĐƠN VỪA TẠO
    public int insert(HoaDonNhap hoaDonNhap) {
        int hoaDonId = 0;

        try {
            DBConnect dbConnect = new DBConnect();
            Connection connection = dbConnect.getConnection();

            // INSERT HÓA ĐƠN NHẬP
            String sql = "insert into hoadonnhap (ngayNhap, tongTien, nhanVienId, nhaCungCapId) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setDate(1, hoaDonNhap.getNgayNhap());
            statement.setDouble(2, hoaDonNhap.getTongTien());
            statement.setInt(3, hoaDonNhap.getNhanVien().getId());
            statement.setInt(4, hoaDonNhap.getNhaCungCap().getId());
            statement.executeUpdate();
            // lấy ra ID hóa đơn nhập vừa tạo
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                hoaDonId = generatedKeys.getInt(1);
            }

            // INSERT TÀI LIỆU NHẬP
            Map<Integer, Integer> mapTaiLieuIds = new HashMap<>();  // tạo ra 1 map chứa cặp <id tài liệu, số lượng được nhập>
            sql = "insert into tailieunhap (soLuong, giaNhap, taiLieuId, hoaDonNhapId) values (?, ?, ?, ?)";
            statement = connection.prepareStatement(sql);
            for (int i = 0; i < hoaDonNhap.getListTaiLieuNhaps().size(); ++i) {
                TaiLieuNhap taiLieuNhap = hoaDonNhap.getListTaiLieuNhaps().get(i);
                int taiLieuId = taiLieuNhap.getTaiLieu().getId();
                int soLuong = taiLieuNhap.getSoLuong();
                statement.setInt(1, soLuong);
                statement.setDouble(2, taiLieuNhap.getGiaNhap());
                statement.setInt(3, taiLieuId);
                statement.setInt(4, hoaDonId);
                statement.executeUpdate();
                mapTaiLieuIds.put(taiLieuId, soLuong);
            }

            // TĂNG SỐ LƯỢNG TÀI LIỆU SAU KHI ĐƯỢC NHẬP
            sql = "update tailieu set soLuong = soLuong + ? where id = ?";
            statement = connection.prepareStatement(sql);
            for (Map.Entry<Integer, Integer> entry : mapTaiLieuIds.entrySet()) {
                int id = entry.getKey();
                int count = entry.getValue();

                statement.setInt(1, count);
                statement.setInt(2, id);
                statement.addBatch();
            }
            statement.executeBatch();   // dùng executeBatch() thay vì executeUpdate() để làm tăng hiệu suất khi thực thi nhiều câu lệnh sql

            // đóng kết nối
            dbConnect.close(null, statement, connection);
        } catch (Exception e) {
            System.out.println(e);
        }

        return hoaDonId;
    }

    // LẤY RA HÓA ĐƠN NHẬP VỪA TẠO
    public HoaDonNhap select(int id) {
        HoaDonNhap hoaDonNhap = new HoaDonNhap();
        try {
            DBConnect dbConnect = new DBConnect();
            Connection connection = dbConnect.getConnection();
            String sql = "select * from hoadonnhap "
                    + "join tailieunhap on hoadonnhap.id = tailieunhap.hoaDonNhapId "
                    + "join tailieu on tailieu.id = tailieunhap.taiLIeuId "
                    + "join thanhvien as nhanvien on nhanvien.id = hoadonnhap.nhanVienId "
                    + "join nhacungcap on nhacungcap.id = hoadonnhap.nhaCungCapId "
                    + "where hoadonnhap.id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            List<TaiLieuNhap> listTaiLieuNhaps = new ArrayList<>();

            while (resultSet.next()) {
                // lấy từ hóa đơn nhập
                hoaDonNhap.setId(id);
                hoaDonNhap.setNgayNhap(resultSet.getDate("hoadonnhap.ngayNhap"));
                hoaDonNhap.setTongTien(resultSet.getDouble("hoadonnhap.tongTien"));

                // lấy từ tài liệu nhập
                TaiLieuNhap taiLieuNhap = new TaiLieuNhap();
                taiLieuNhap.setId(resultSet.getInt("tailieunhap.id"));
                taiLieuNhap.setGiaNhap(resultSet.getDouble("tailieunhap.giaNhap"));
                taiLieuNhap.setSoLuong(resultSet.getInt("tailieunhap.soLuong"));

                // lấy từ tài liệu
                TaiLieu taiLieu = new TaiLieu();
                taiLieu.setId(resultSet.getInt("tailieu.id"));
                taiLieu.setTen(resultSet.getString("tailieu.ten"));
                taiLieu.setTacGia(resultSet.getString("tailieu.tacGia"));
                taiLieu.setMoTa(resultSet.getString("tailieu.moTa"));
                taiLieu.setSoLuong(resultSet.getInt("tailieu.soLuong"));
                taiLieu.setAnhBia(resultSet.getString("tailieu.anhBia"));

                // lấy từ nhân viên
                ThanhVien nhanVien = new ThanhVien();
                nhanVien.setId(resultSet.getInt("nhanvien.id"));
                nhanVien.setHoTen(resultSet.getString("nhanvien.hoTen"));
                nhanVien.setSoDienThoai(resultSet.getString("nhanvien.soDienThoai"));
                nhanVien.setDiaChi(resultSet.getString("nhanvien.diaChi"));
                nhanVien.setEmail(resultSet.getString("nhanvien.email"));
                nhanVien.setVaiTro(resultSet.getInt("nhanvien.vaiTro"));

                // Lấy từ nhà cung cấp
                NhaCungCap ncc = new NhaCungCap();
                ncc.setId(resultSet.getInt("nhacungcap.id"));
                ncc.setTen(resultSet.getString("nhacungcap.ten"));
                ncc.setDiaChi(resultSet.getString("nhacungcap.diaChi"));
                ncc.setSoDienThoai(resultSet.getString("nhacungcap.soDienThoai"));

                // set nhân viên và nhà cung cấp vào hóa đơn
                hoaDonNhap.setNhanVien(nhanVien);
                hoaDonNhap.setNhaCungCap(ncc);

                // set tài liệu vào tài liệu nhập
                taiLieuNhap.setTaiLieu(taiLieu);
                listTaiLieuNhaps.add(taiLieuNhap);
            }
            // set list tài liệu nhập vào hóa đơn nhập
            hoaDonNhap.setListTaiLieuNhaps(listTaiLieuNhaps);

            // đóng kết nối
            dbConnect.close(resultSet, statement, connection);
        } catch (Exception e) {
            System.out.println(e);
        }
        return hoaDonNhap;
    }
}
