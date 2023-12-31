/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.PhieuMuon;
import entity.TaiLieu;
import entity.TaiLieuMuon;
import entity.ThanhVien;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Admin
 */
public class MuonTaiLieuDAO {

    // TẠO 1 PHIẾU MƯỢN MỚI => TRẢ VỀ ID PHIẾU MƯỢN MỚI TẠO
    public int insert(PhieuMuon phieuMuon, List<Integer> datTruocIds, List<Integer> taiLieuDTIds) {
        int id = 0;

        try {
            DBConnect dbConnect = new DBConnect();
            Connection connection = dbConnect.getConnection();

            // INSERT PHIẾU MƯỢN
            String sql = "insert into phieumuon (ngayMuon, ngayPhaiTra, nhanVienId, banDocId) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setDate(1, phieuMuon.getNgayMuon());
            statement.setDate(2, phieuMuon.getNgayPhaiTra());
            statement.setInt(3, phieuMuon.getNhanVien().getId());
            statement.setInt(4, phieuMuon.getBanDoc().getId());
            statement.executeUpdate();
            // lấy ra id phiếu mượn vừa tạo
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
            }

            // INSERT TÀI LIỆU MƯỢN, ĐỒNG THỜI LẤY RA LIST ID CỦA TÀI LIỆU
            List<Integer> listTaiLieuIds = new ArrayList<>();
            sql = "insert into tailieumuon (trangThai, taiLieuId, phieuMuonId) VALUES (?, ?, ?)";
            statement = connection.prepareStatement(sql);
            for (int i = 0; i < phieuMuon.getListTaiLieuMuons().size(); ++i) {
                int taiLieuId = phieuMuon.getListTaiLieuMuons().get(i).getTaiLieu().getId();
                statement.setInt(1, 0);
                statement.setInt(2, taiLieuId);
                statement.setInt(3, id);
                statement.executeUpdate();

                listTaiLieuIds.add(taiLieuId);
            }

            // GIẢM SỐ LƯỢNG CỦA TÀI LIỆU ĐI 1 (DO MỖI TÀI LIỆU BỊ MƯỢN MẤT 1 CUỐN)
            String placeholders = listTaiLieuIds.stream().map(i -> "?").collect(Collectors.joining(", "));
            sql = "update tailieu set soLuong = soLuong - 1 where id in (" + placeholders + ")";
            statement = connection.prepareStatement(sql);
            boolean ok = false;
            for (int i = 1; i <= listTaiLieuIds.size(); ++i) {
                if (!taiLieuDTIds.contains(listTaiLieuIds.get(i - 1))) {
                    ok = true;
                    statement.setInt(i, listTaiLieuIds.get(i - 1));
                }
            }
            if (ok) {
                statement.executeUpdate();
            }

            // SET TRẠNG THÁI CỦA TÀI LIỆU ĐẶT TRƯỚC LÀ 1
            // (TRẠNG THÁI 0 TỨC LÀ TÀI LIỆU ĐƯỢC ĐẶT TRƯỚC NHƯNG CHƯA ĐƯỢC MƯỢN)
            // (TRẠNG THÁI 1 TỨC LÀ TÀI LIỆU ĐƯỢC ĐẶT TRƯỚC VÀ ĐÃ ĐƯỢC MƯỢN)
            placeholders = datTruocIds.stream().map(i -> "?").collect(Collectors.joining(", "));
            sql = "update tailieudattruoc set trangthai = 1 where id in (" + placeholders + ")";
            statement = connection.prepareStatement(sql);
            ok = false;
            for (int i = 1; i <= datTruocIds.size(); ++i) {
                ok = true;
                statement.setInt(i, datTruocIds.get(i - 1));
            }
            if (ok) {
                statement.executeUpdate();
            }

            // đóng kết nối
            dbConnect.close(null, statement, connection);
        } catch (Exception e) {
            System.out.println(e);
        }

        return id;
    }

    // LẤY RA THÔNG TIN PHIẾU MƯỢN VỪA TẠO
    public PhieuMuon select(int id) {
        PhieuMuon phieuMuon = new PhieuMuon();
        try {
            DBConnect dbConnect = new DBConnect();
            Connection connection = dbConnect.getConnection();
            String sql = "select * from phieumuon "
                    + "join tailieumuon on phieumuon.id = tailieumuon.phieuMuonId "
                    + "join tailieu on tailieu.id = tailieumuon.taiLIeuId "
                    + "join thanhvien as bandoc on bandoc.id = phieumuon.banDocId "
                    + "join thanhvien as nhanvien on nhanvien.id = phieumuon.nhanVienId "
                    + "where phieumuon.id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            List<TaiLieuMuon> listTaiLieuMuons = new ArrayList<>();

            while (resultSet.next()) {
                // lấy từ phiếu mượn
                Date ngayMuon = resultSet.getDate("phieumuon.ngayMuon");
                Date ngayPhaiTra = resultSet.getDate("phieumuon.ngayPhaiTra");
                phieuMuon.setId(id);
                phieuMuon.setNgayMuon(ngayMuon);
                phieuMuon.setNgayPhaiTra(ngayPhaiTra);

                // lấy từ tài liệu mượn
                TaiLieuMuon taiLieuMuon = new TaiLieuMuon();
                taiLieuMuon.setId(resultSet.getInt("tailieumuon.id"));
                taiLieuMuon.setTrangThai(resultSet.getInt("tailieumuon.trangThai"));
                taiLieuMuon.setNgayMuon(ngayMuon);
                taiLieuMuon.setNgayPhaiTra(ngayPhaiTra);

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

                // set nhân viên và bạn đọc vào phiếu mượn
                phieuMuon.setBanDoc(banDoc);
                phieuMuon.setNhanVien(nhanVien);

                // set tài liệu vào tài liệu mượn
                taiLieuMuon.setTaiLieu(taiLieu);
                listTaiLieuMuons.add(taiLieuMuon);
            }

            // set list tài liệu mượn vào phiếu mượn
            phieuMuon.setListTaiLieuMuons(listTaiLieuMuons);

            // đóng kết nối
            dbConnect.close(resultSet, statement, connection);
        } catch (Exception e) {
            System.out.println(e);
        }
        return phieuMuon;
    }

    // LẤY RA LIST TÀI LIỆU MƯỢN CỦA BẠN ĐỌC
    public List<TaiLieuMuon> getList(int banDocId) {
        List<TaiLieuMuon> list = new ArrayList<>();
        try {
            DBConnect dbConnect = new DBConnect();
            Connection connection = dbConnect.getConnection();
            String sql = "select * from phieumuon join tailieumuon "
                    + "on phieumuon.id = tailieumuon.phieuMuonId "
                    + "join tailieu "
                    + "on tailieu.id = tailieumuon.taiLieuId "
                    + "where phieumuon.banDocId = ? and tailieumuon.trangThai = 0";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, banDocId);

            // thực thi truy vấn
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // lấy ra thông tin phiếu mượn
                Date ngayMuon = resultSet.getDate("phieumuon.ngayMuon");
                Date ngayPhaiTra = resultSet.getDate("phieumuon.ngayPhaiTra");

                // lấy ra thông tin tài liệu
                TaiLieu taiLieu = new TaiLieu();
                taiLieu.setId(resultSet.getInt("tailieu.id"));
                taiLieu.setTen(resultSet.getString("tailieu.ten"));
                taiLieu.setTacGia(resultSet.getString("tailieu.tacGia"));
                taiLieu.setMoTa(resultSet.getString("tailieu.moTa"));
                taiLieu.setSoLuong(resultSet.getInt("tailieu.soLuong"));
                taiLieu.setAnhBia(resultSet.getString("tailieu.anhBia"));

                // lấy ra thông tin tài liệu mượn
                TaiLieuMuon taiLieuMuon = new TaiLieuMuon();
                taiLieuMuon.setId(resultSet.getInt("tailieumuon.id"));
                taiLieuMuon.setTrangThai(resultSet.getInt("tailieumuon.trangThai"));
                taiLieuMuon.setNgayMuon(ngayMuon);
                taiLieuMuon.setNgayPhaiTra(ngayPhaiTra);

                // set tài liệu vào tài liệu mượn
                taiLieuMuon.setTaiLieu(taiLieu);

                list.add(taiLieuMuon);
            }

            // đóng kết nối
            dbConnect.close(resultSet, statement, connection);
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }
}
