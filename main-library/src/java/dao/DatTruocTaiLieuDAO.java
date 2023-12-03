/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.TaiLieu;
import entity.TaiLieuDatTruoc;
import entity.ThanhVien;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.VaiTro;

/**
 *
 * @author Admin
 */
public class DatTruocTaiLieuDAO {

    // INSERT TÀI LIỆU ĐẶT TRƯỚC
    public boolean insert(TaiLieuDatTruoc taiLieuDatTruoc) {
        boolean ok = false;

        try {
            DBConnect dbConnect = new DBConnect();
            Connection connection = dbConnect.getConnection();

            // đặt trước tài liệu
            String sql = "insert into tailieudattruoc (ngayDat, ngayHetHan, trangThai, taiLieuId, banDocId) values (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDate(1, taiLieuDatTruoc.getNgayDat());
            statement.setDate(2, taiLieuDatTruoc.getNgayHetHan());
            statement.setInt(3, taiLieuDatTruoc.getTrangThai());
            statement.setInt(4, taiLieuDatTruoc.getTaiLieu().getId());
            statement.setInt(5, taiLieuDatTruoc.getBanDoc().getId());
            int rows = statement.executeUpdate();
            if (rows > 0) {
                ok = true;
            }

            // giảm số lượng tài liệu đi 1
            sql = "update tailieu set soLuong = soLuong - 1 where id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, taiLieuDatTruoc.getTaiLieu().getId());
            statement.executeUpdate();

            // đóng kết nối
            dbConnect.close(null, statement, connection);
        } catch (Exception e) {
            System.out.println(e);
        }

        return ok;
    }

    // HỦY TÀI LIỆU ĐẶT TRƯỚC
    public boolean delete(int id, int taiLieuId) {
        boolean ok = false;
        try {
            DBConnect dbConnect = new DBConnect();
            Connection connection = dbConnect.getConnection();

            // xóa tài liệu đặt trước
            String sql = "delete from tailieudattruoc where id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            int rows = statement.executeUpdate();
            if (rows > 0) {
                ok = true;
            }

            // tăng số lượng tài liệu lên 1
            sql = "update tailieu set soLuong = soLuong + 1 where id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, taiLieuId);
            statement.executeUpdate();

            // đóng kết nối
            dbConnect.close(null, statement, connection);
        } catch (Exception e) {
            System.out.println(e);
        }
        return ok;
    }

    // LẤY RA LIST TÀI LIỆU ĐÃ ĐẶT TRƯỚC CỦA BẠN ĐỌC
    public List<TaiLieuDatTruoc> getList(int banDocId) {
        List<TaiLieuDatTruoc> list = new ArrayList<>();
        try {
            DBConnect dbConnect = new DBConnect();
            Connection connection = dbConnect.getConnection();
            String sql = "select * from tailieudattruoc join tailieu "
                    + "on tailieudattruoc.taiLieuId = taiLieu.id "
                    + "join thanhvien "
                    + "on tailieudattruoc.banDocId = thanhvien.id "
                    + "where tailieudattruoc.banDocId = ? and tailieudattruoc.trangThai = 0";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, banDocId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // lấy từ tài liệu đặt trước
                TaiLieuDatTruoc taiLieuDatTruoc = new TaiLieuDatTruoc();
                taiLieuDatTruoc.setId(resultSet.getInt("tailieudattruoc.id"));
                taiLieuDatTruoc.setNgayDat(resultSet.getDate("tailieudattruoc.ngayDat"));
                taiLieuDatTruoc.setNgayHetHan(resultSet.getDate("tailieudattruoc.ngayHetHan"));
                taiLieuDatTruoc.setTrangThai(resultSet.getInt("tailieudattruoc.trangThai"));

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
                banDoc.setId(resultSet.getInt("thanhvien.id"));
                banDoc.setHoTen(resultSet.getString("thanhvien.hoTen"));
                banDoc.setSoDienThoai(resultSet.getString("thanhvien.soDienThoai"));
                banDoc.setDiaChi(resultSet.getString("thanhvien.diaChi"));
                banDoc.setEmail(resultSet.getString("thanhvien.email"));
                banDoc.setVaiTro(resultSet.getInt("thanhvien.vaiTro"));

                // set bạn đọc và tài liệu vào tài liệu đặt trước
                taiLieuDatTruoc.setBanDoc(banDoc);
                taiLieuDatTruoc.setTaiLieu(taiLieu);

                list.add(taiLieuDatTruoc);
            }

            // đóng kết nối
            dbConnect.close(resultSet, statement, connection);
        } catch (Exception e) {
            System.out.println(e);
        }

        return list;
    }
}
