/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.HoaDonTra;
import entity.PhieuTra;
import entity.TaiLieu;
import entity.TaiLieuMuon;
import entity.ThanhVien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class TraTaiLieuDAO {

    // INSERT PHIẾU TRẢ VÀ HÓA ĐƠN TRẢ => TRẢ VỀ ID CỦA HÓA ĐƠN TRẢ VỪA TẠO
    public int insert(HoaDonTra hoaDonTra, Map<Integer, Integer> mapTaiLieuIds) {
        int hoaDonTraId = 0;
        try {

            // INSERT PHIẾU TRẢ, ĐỒNG THỜI LẤY RA ID CỦA PHIÊU TRẢ
            DBConnect dbConnect = new DBConnect();
            Connection connection = dbConnect.getConnection();
            String sql = "insert into phieutra (ngayTra, nhanVienId, banDocId) value (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            PhieuTra phieuTra = hoaDonTra.getPhieuTra();
            statement.setDate(1, phieuTra.getNgayTra());
            statement.setInt(2, phieuTra.getNhanVien().getId());
            statement.setInt(3, phieuTra.getBanDoc().getId());
            statement.executeUpdate();
            // lấy ra id của phiếu trả vừa tạo
            ResultSet generatedKeys = statement.getGeneratedKeys();
            int phieuTraId = 0;
            if (generatedKeys.next()) {
                phieuTraId = generatedKeys.getInt(1);
            }

            // UPDATE TRẠNG THÁI CỦA TÀI LIỆU MƯỢN VỀ 1 (0: ĐANG MƯỢN, 1: ĐÃ TRẢ))
            sql = "update tailieumuon set trangThai = ?, phieuTraId = ? WHERE id = ?";
            statement = connection.prepareStatement(sql);
            for (int i = 0; i < phieuTra.getListTaiLieuMuons().size(); ++i) {
                int taiLieuTraId = phieuTra.getListTaiLieuMuons().get(i).getId();
                statement.setInt(1, 1);
                statement.setInt(2, phieuTraId);
                statement.setInt(3, taiLieuTraId);
                statement.executeUpdate();
            }

            // UPDATE SỐ LƯỢNG TÀI LIỆU (SAU KHI TRẢ TÀI LIỆU THÌ SỐ LƯỢNG TÀI LIỆU TRONG KHO TĂNG LÊN)
            sql = "update tailieu set soLuong = soLuong + ? where id = ?";
            statement = connection.prepareStatement(sql);
            for (Map.Entry<Integer, Integer> entry : mapTaiLieuIds.entrySet()) {
                int id = entry.getKey();
                int count = entry.getValue();

                statement.setInt(1, count);
                statement.setInt(2, id);
                statement.addBatch();
            }
            statement.executeBatch();

            // INSERT HÓA ĐƠN TRẢ, ĐỒNG THỜI LẤY RA ID CỦA HÓA ĐƠN TRẢ VỪA TẠO
            sql = "insert into hoadontra (tienPhat, ghiChu, phieuTraId) values (?, ?, ?)";
            statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setDouble(1, hoaDonTra.getTienPhat());
            statement.setString(2, hoaDonTra.getGhiChu());
            statement.setInt(3, phieuTraId);
            statement.executeUpdate();
            // lấy ra id của hóa đơn trả vừa tạo
            generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                hoaDonTraId = generatedKeys.getInt(1);
            }

            // đóng kết nối
            dbConnect.close(null, statement, connection);
        } catch (Exception e) {
            System.out.println(e);
        }
        return hoaDonTraId;
    }

    // LẤY RA HÓA ĐƠN TRẢ VỪA TẠO
    public HoaDonTra select(int id) {
        HoaDonTra hoaDonTra = new HoaDonTra();

        try {
            DBConnect dbConnect = new DBConnect();
            Connection connection = dbConnect.getConnection();
            String sql = "select * from hoadontra "
                    + "join phieutra on hoadontra.phieuTraId = phieutra.id "
                    + "join tailieumuon on phieutra.id = tailieumuon.phieuTraId "
                    + "join phieumuon on phieumuon.id = tailieumuon.phieuMuonId "
                    + "join tailieu on tailieu.id = tailieumuon.taiLIeuId "
                    + "join thanhvien as bandoc on bandoc.id = phieutra.banDocId "
                    + "join thanhvien as nhanvien on nhanvien.id = phieutra.nhanVienId "
                    + "where hoadontra.id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            PhieuTra phieuTra = new PhieuTra();
            List<TaiLieuMuon> listTaiLieuMuons = new ArrayList<>();

            while (resultSet.next()) {
                // lấy từ hóa đơn trả
                hoaDonTra.setId(id);
                hoaDonTra.setTienPhat(resultSet.getDouble("hoadontra.tienPhat"));
                hoaDonTra.setGhiChu(resultSet.getString("hoaDonTra.ghiChu"));

                // lấy từ phiếu trả
                phieuTra.setId(resultSet.getInt("phieutra.id"));
                phieuTra.setNgayTra(resultSet.getDate("phieutra.ngayTra"));

                // lấy từ tài liệu mượn
                TaiLieuMuon taiLieuMuon = new TaiLieuMuon();
                taiLieuMuon.setId(resultSet.getInt("tailieumuon.id"));
                taiLieuMuon.setTrangThai(resultSet.getInt("tailieumuon.trangThai"));
                taiLieuMuon.setNgayMuon(resultSet.getDate("phieumuon.ngayMuon"));
                taiLieuMuon.setNgayPhaiTra(resultSet.getDate("phieumuon.ngayPhaiTra"));

                // lấy từ tài liệu
                TaiLieu taiLieu = new TaiLieu();
                taiLieu.setId(resultSet.getInt("tailieu.id"));
                taiLieu.setTen(resultSet.getString("tailieu.ten"));
                taiLieu.setTacGia(resultSet.getString("tailieu.tacGia"));
                taiLieu.setMoTa(resultSet.getString("tailieu.moTa"));
                taiLieu.setSoLuong(resultSet.getInt("tailieu.soLuong"));
                taiLieu.setAnhBia(resultSet.getString("tailieu.anhBia"));

                // lấy từ bạn đọc
                ThanhVien banDoc = new ThanhVien();
                banDoc.setId(resultSet.getInt("bandoc.id"));
                banDoc.setHoTen(resultSet.getString("bandoc.hoTen"));
                banDoc.setSoDienThoai(resultSet.getString("bandoc.soDienThoai"));
                banDoc.setDiaChi(resultSet.getString("bandoc.diaChi"));
                banDoc.setEmail(resultSet.getString("bandoc.email"));
                banDoc.setVaiTro(resultSet.getInt("bandoc.vaiTro"));

                // lấy từ nhân viên
                ThanhVien nhanVien = new ThanhVien();
                nhanVien.setId(resultSet.getInt("nhanvien.id"));
                nhanVien.setHoTen(resultSet.getString("nhanvien.hoTen"));
                nhanVien.setSoDienThoai(resultSet.getString("nhanvien.soDienThoai"));
                nhanVien.setDiaChi(resultSet.getString("nhanvien.diaChi"));
                nhanVien.setEmail(resultSet.getString("nhanvien.email"));
                nhanVien.setVaiTro(resultSet.getInt("nhanvien.vaiTro"));

                // set bạn đọc và nhân viên vào phiếu trả
                phieuTra.setBanDoc(banDoc);
                phieuTra.setNhanVien(nhanVien);

                // set tài liệu vào tài liệu mượn
                taiLieuMuon.setTaiLieu(taiLieu);
                listTaiLieuMuons.add(taiLieuMuon);
            }

            // set list tài liệu mượn vào phiếu trả
            phieuTra.setListTaiLieuMuons(listTaiLieuMuons);

            // set phiếu trả vào hóa đơn trả
            hoaDonTra.setPhieuTra(phieuTra);

            // đóng kết nối
            dbConnect.close(resultSet, statement, connection);
        } catch (Exception e) {
            System.out.println(e);
        }

        return hoaDonTra;
    }
}
