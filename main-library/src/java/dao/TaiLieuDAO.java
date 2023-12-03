/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.TaiLieu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class TaiLieuDAO {

    // LẤY RA 1 TÀI LIỆU
    public TaiLieu select(int id) {
        TaiLieu taiLieu = new TaiLieu();
        
        try {
            DBConnect dbConnect = new DBConnect();
            Connection connection = dbConnect.getConnection();
            String sql = "select * from tailieu where id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            // thực thi truy vấn
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                taiLieu.setId(resultSet.getInt("id"));
                taiLieu.setTen(resultSet.getString("ten"));
                taiLieu.setTacGia(resultSet.getString("tacGia"));
                taiLieu.setMoTa(resultSet.getString("moTa"));
                taiLieu.setSoLuong(resultSet.getInt("soLuong"));
                taiLieu.setAnhBia(resultSet.getString("anhBia"));
            }

            // đóng kết nối
            dbConnect.close(resultSet, statement, connection);
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return taiLieu;
    }

    // THÊM 1 TÀI LIỆU MỚI, TRẢ VỀ ID CỦA TÀI LIỆU VỪA THÊM
    public int insert(TaiLieu taiLieu) {
        int id = 0;
        try {
            DBConnect dbConnect = new DBConnect();
            Connection connection = dbConnect.getConnection();
            String sql = "insert into tailieu (ten, tacGia, moTa, soLuong, anhBia) values (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, taiLieu.getTen());
            statement.setString(2, taiLieu.getTacGia());
            statement.setString(3, taiLieu.getMoTa());
            statement.setInt(4, taiLieu.getSoLuong());
            statement.setString(5, taiLieu.getAnhBia());

            // thực thi truy vấn
            statement.executeUpdate();
            // lấy ra ID tài liệu vừa tạo vừa tạo
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
            }

            // đóng kết nối
            dbConnect.close(null, statement, connection);
        } catch (Exception e) {
            System.out.println(e);
        }
        return id;
    }

    // SỬA THÔNG TIN 1 TÀI LIỆU
    public boolean update(TaiLieu taiLieu) {
        boolean ok = false;
        try {
            DBConnect dbConnect = new DBConnect();
            Connection connection = dbConnect.getConnection();
            String sql = "update tailieu set ten = ?, tacGia = ?, moTa = ?, anhBia = ? where id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, taiLieu.getTen());
            statement.setString(2, taiLieu.getTacGia());
            statement.setString(3, taiLieu.getMoTa());
            statement.setString(4, taiLieu.getAnhBia());
            statement.setInt(5, taiLieu.getId());

            // thực thi truy vấn
            int rows = statement.executeUpdate();
            if (rows > 0) {
                ok = true;
            }

            // đóng kết nối
            dbConnect.close(null, statement, connection);
        } catch (Exception e) {
            System.out.println(e);
        }
        return ok;
    }

    // XÓA 1 TÀI LIỆU
    public boolean delete(int id) {
        boolean ok = false;
        
        try {
            DBConnect dbConnect = new DBConnect();
            Connection connection = dbConnect.getConnection();
            String sql = "delete from tailieu where id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            // thực thi truy vấn
            int rows = statement.executeUpdate();
            if (rows > 0) {
                ok = true;
            }

            // đóng kết nối
            dbConnect.close(null, statement, connection);
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return ok;
    }

    // TÌM KIẾM LIST TÀI LIỆU
    public List<TaiLieu> search(String ten) {
        List<TaiLieu> list = new ArrayList<>();
        
        try {
            DBConnect dbConnect = new DBConnect();
            Connection connection = dbConnect.getConnection();
            String sql = "select * from tailieu where ten like ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + ten + "%");

            // thực thi truy vấn
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                TaiLieu taiLieu = new TaiLieu();
                taiLieu.setId(resultSet.getInt("id"));
                taiLieu.setTen(resultSet.getString("ten"));
                taiLieu.setTacGia(resultSet.getString("tacGia"));
                taiLieu.setMoTa(resultSet.getString("moTa"));
                taiLieu.setSoLuong(resultSet.getInt("soLuong"));
                taiLieu.setAnhBia(resultSet.getString("anhBia"));
                
                list.add(taiLieu);
            }

            // đóng kết nối
            dbConnect.close(resultSet, statement, connection);
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return list;
    }

    // LẤY RA 10 TÀI LIỆU ĐƯỢC MƯỢN NHIỀU NHẤT
    public List<TaiLieu> getTopTen() {
        List<TaiLieu> list = new ArrayList<>();
        
        try {
            DBConnect dbConnect = new DBConnect();
            Connection connection = dbConnect.getConnection();
            String sql = "select tailieu.id, tailieu.ten, tailieu.tacGia, tailieu.moTa, tailieu.soLuong, tailieu.anhBia, "
                    + "count(tailieumuon.id) as soLuotMuon "
                    + "from tailieu "
                    + "join tailieumuon on tailieu.id = tailieumuon.taiLieuId "
                    + "group by tailieu.id "
                    + "order by soLuotMuon desc "
                    + "limit 10";
            PreparedStatement statement = connection.prepareStatement(sql);

            // thực thi truy vấn
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                TaiLieu taiLieu = new TaiLieu();
                taiLieu.setId(resultSet.getInt("tailieu.id"));
                taiLieu.setTen(resultSet.getString("tailieu.ten"));
                taiLieu.setTacGia(resultSet.getString("tailieu.tacGia"));
                taiLieu.setMoTa(resultSet.getString("tailieu.moTa"));
                taiLieu.setSoLuong(resultSet.getInt("tailieu.soLuong"));
                taiLieu.setAnhBia(resultSet.getString("tailieu.anhBia"));
                
                list.add(taiLieu);
            }

            // đóng kết nối
            dbConnect.close(resultSet, statement, connection);
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return list;
    }
}
