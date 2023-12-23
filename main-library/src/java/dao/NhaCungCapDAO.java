/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.NhaCungCap;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class NhaCungCapDAO {

    // LẤY TẤT CẢ NHÀ CUNG CẤP
    public List<NhaCungCap> getAll() {
        List<NhaCungCap> list = new ArrayList<>();

        try {
            DBConnect dbConnect = new DBConnect();
            Connection connection = dbConnect.getConnection();
            String sql = "select * from nhacungcap";
            PreparedStatement statement = connection.prepareStatement(sql);

            // thực thi truy vấn
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                NhaCungCap ncc = new NhaCungCap();
                ncc.setId(resultSet.getInt("id"));
                ncc.setTen(resultSet.getString("ten"));
                ncc.setDiaChi(resultSet.getString("diaChi"));
                ncc.setSoDienThoai(resultSet.getString("soDienThoai"));

                list.add(ncc);

            }

            // đóng kết nối
            dbConnect.close(resultSet, statement, connection);
        } catch (Exception e) {
            System.out.println(e);
        }

        return list;
    }

    // LẤY THÔNG TIN 1 NHÀ CUNG CẤP
    public NhaCungCap select(int id) {
        NhaCungCap ncc = new NhaCungCap();

        try {
            DBConnect dbConnect = new DBConnect();
            Connection connection = dbConnect.getConnection();
            String sql = "select * from nhacungcap where id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            // thực thi truy vấn
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ncc.setId(resultSet.getInt("id"));
                ncc.setTen(resultSet.getString("ten"));
                ncc.setDiaChi(resultSet.getString("diaChi"));
                ncc.setSoDienThoai(resultSet.getString("soDienThoai"));

            }
            // đóng kết nối
            dbConnect.close(resultSet, statement, connection);
        } catch (Exception e) {
            System.out.println(e);
        }

        return ncc;
    }

    // THÊM 1 NHÀ CUNG CẤP MỚI
    public int insert(NhaCungCap ncc) {
        int id = 0;
        try {
            DBConnect dbConnect = new DBConnect();
            Connection connection = dbConnect.getConnection();
            String sql = "insert into nhacungcap (ten, diaChi, soDienThoai) "
                    + "values (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, ncc.getTen());
            statement.setString(2, ncc.getDiaChi());
            statement.setString(3, ncc.getSoDienThoai());

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

    // SỬA THÔNG TIN 1 NHÀ CUNG CẤP
    public boolean update(NhaCungCap ncc) {
        boolean ok = false;
        try {
            DBConnect dbConnect = new DBConnect();
            Connection connection = dbConnect.getConnection();
            String sql = "update nhacungcap set ten = ?, diaChi = ?, soDienThoai = ? where id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, ncc.getTen());
            statement.setString(2, ncc.getDiaChi());
            statement.setString(3, ncc.getSoDienThoai());
            statement.setInt(4, ncc.getId());

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

    // XÓA THÔNG TIN 1 NHÀ CUNG CẤP
    public boolean delete(int id) {
        boolean ok = false;
        try {
            DBConnect dbConnect = new DBConnect();
            Connection connection = dbConnect.getConnection();
            String sql = "delete from nhacungcap where id = ?";
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

    // TÌM KIẾM LIST NHÀ CUNG CẤP
    public List<NhaCungCap> searchNCCs(String search) {
        List<NhaCungCap> list = new ArrayList<>();
        try {
            DBConnect dbConnect = new DBConnect();
            Connection connection = dbConnect.getConnection();
            String sql = "select * from nhacungcap where ten like ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + search + "%");

            // thực thi truy vấn
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                NhaCungCap ncc = new NhaCungCap();
                ncc.setId(resultSet.getInt("id"));
                ncc.setTen(resultSet.getString("ten"));
                ncc.setDiaChi(resultSet.getString("diaChi"));
                ncc.setSoDienThoai(resultSet.getString("soDienThoai"));

                list.add(ncc);
            }

            // đóng kết nối
            dbConnect.close(resultSet, statement, connection);
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }
}
