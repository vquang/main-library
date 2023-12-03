/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.TaiLieu;
import entity.ThanhVien;
import entity.ThongKeBanDoc;
import entity.ThongKeTaiLieu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ThongKeDAO {

    // LẤY LIST THỐNG KÊ TÀI LIỆU ĐƯỢC MƯỢN NHIỀU NHẤT
    public List<ThongKeTaiLieu> getListTKTaiLieus(String thuTu) {
        List<ThongKeTaiLieu> list = new ArrayList<>();
        try {
            DBConnect dbConnect = new DBConnect();
            Connection connection = dbConnect.getConnection();
            String sql = "select tailieu.id, tailieu.ten, tailieu.tacGia, tailieu.moTa, tailieu.soLuong, tailieu.anhBia, "
                    + "count(tailieumuon.id) as soLuotMuon "
                    + "from tailieu "
                    + "join tailieumuon on tailieu.id = tailieumuon.taiLieuId "
                    + "group by tailieu.id "
                    + "order by soLuotMuon " + thuTu;
            PreparedStatement statement = connection.prepareStatement(sql);

            // thực thi truy vấn
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // lấy từ tài liệu
                TaiLieu taiLieu = new TaiLieu();
                taiLieu.setId(resultSet.getInt("tailieu.id"));
                taiLieu.setTen(resultSet.getString("tailieu.ten"));
                taiLieu.setTacGia(resultSet.getString("tailieu.tacGia"));
                taiLieu.setMoTa(resultSet.getString("tailieu.moTa"));
                taiLieu.setSoLuong(resultSet.getInt("tailieu.soLuong"));
                taiLieu.setAnhBia(resultSet.getString("tailieu.anhBia"));

                // set số lượt mượn và tài liệu vào thống kê tài liệu
                ThongKeTaiLieu thongKeTaiLieu = new ThongKeTaiLieu();
                thongKeTaiLieu.setSoLuotMuon(resultSet.getInt("soLuotMuon"));
                thongKeTaiLieu.setTaiLieu(taiLieu);

                list.add(thongKeTaiLieu);
            }

            // đóng kết nối
            dbConnect.close(resultSet, statement, connection);
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    // LẤY LIST THỐNG KÊ BẠN ĐỌC MƯỢN NHIỀU TÀI LIỆU NHẤT
    public List<ThongKeBanDoc> getListTKBanDocs(String thuTu) {
        List<ThongKeBanDoc> list = new ArrayList<>();
        try {
            DBConnect dbConnect = new DBConnect();
            Connection connection = dbConnect.getConnection();
            String sql = "select thanhvien.id, thanhvien.username, thanhvien.hoTen, thanhvien.soDienThoai, "
                    + "thanhvien.diaChi, thanhvien.email, thanhvien.vaiTro, count(tailieumuon.id) as tongTaiLieu "
                    + "from thanhvien "
                    + "JOIN phieumuon on thanhvien.id = phieumuon.banDocId "
                    + "join tailieumuon on phieumuon.id = tailieumuon.phieuMuonId "
                    + "group by thanhvien.id "
                    + "order by tongTaiLieu " + thuTu;
            PreparedStatement statement = connection.prepareStatement(sql);

            // thực thi truy vấn
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // lấy từ bạn đọc
                ThanhVien banDoc = new ThanhVien();
                banDoc.setId(resultSet.getInt("thanhvien.id"));
                banDoc.setHoTen(resultSet.getString("thanhvien.hoTen"));
                banDoc.setSoDienThoai(resultSet.getString("thanhvien.soDienThoai"));
                banDoc.setDiaChi(resultSet.getString("thanhvien.diaChi"));
                banDoc.setEmail(resultSet.getString("thanhvien.email"));
                banDoc.setVaiTro(resultSet.getInt("thanhvien.vaiTro"));

                // set tổng tài liệu đã mượn và bạn đọc vào thống kê tài liệu
                ThongKeBanDoc thongKeBanDoc = new ThongKeBanDoc();
                thongKeBanDoc.setTongTaiLieu(resultSet.getInt("tongTaiLieu"));
                thongKeBanDoc.setBanDoc(banDoc);

                list.add(thongKeBanDoc);
            }

            // đóng kết nối
            dbConnect.close(resultSet, statement, connection);
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }
}
