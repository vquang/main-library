/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.ThanhVien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.VaiTro;

/**
 *
 * @author Admin
 */
public class ThanhVienDAO {

    // KIỂM TRA ĐĂNG NHẬP
    public List<ThanhVien> checkLogin(ThanhVien thanhVien) {
        List<ThanhVien> list = new ArrayList<>();
        try {
            DBConnect dbConnect = new DBConnect();
            Connection connection = dbConnect.getConnection();
            String sql = "select * from thanhvien where username = ? and password = ? and vaiTro = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, thanhVien.getUsername());
            statement.setString(2, thanhVien.getPassword());
            statement.setInt(3, thanhVien.getVaiTro());

            // thực thi truy vấn
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String hoTen = resultSet.getString("hoTen");
                String soDienThoai = resultSet.getString("soDienThoai");
                String diaChi = resultSet.getString("diaChi");
                String email = resultSet.getString("email");
                int vaiTro = resultSet.getInt("vaiTro");

                ThanhVien tv = new ThanhVien(id, "", "", hoTen, soDienThoai, diaChi, email, vaiTro);
                list.add(tv);
            }

            // đóng kết nối
            dbConnect.close(resultSet, statement, connection);
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    // LẤY TẤT CẢ THÀNH VIÊN THEO VAI TRÒ
    public List<ThanhVien> getAll(int vaiTro) {
        List<ThanhVien> list = new ArrayList<>();

        try {
            DBConnect dbConnect = new DBConnect();
            Connection connection = dbConnect.getConnection();
            String sql = "select * from thanhvien where vaiTro = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, vaiTro);

            // thực thi truy vấn
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ThanhVien thanhVien = new ThanhVien();
                thanhVien.setId(resultSet.getInt("id"));
                thanhVien.setUsername(resultSet.getString("username"));
                thanhVien.setHoTen(resultSet.getString("hoTen"));
                thanhVien.setSoDienThoai(resultSet.getString("soDienThoai"));
                thanhVien.setDiaChi(resultSet.getString("diaChi"));
                thanhVien.setEmail(resultSet.getString("email"));
                thanhVien.setVaiTro(resultSet.getInt("vaiTro"));

                list.add(thanhVien);

            }

            // đóng kết nối
            dbConnect.close(resultSet, statement, connection);
        } catch (Exception e) {
            System.out.println(e);
        }

        return list;
    }

    // LẤY THÔNG TIN 1 THÀNH VIÊN
    public ThanhVien select(int id) {
        ThanhVien thanhVien = new ThanhVien();

        try {
            DBConnect dbConnect = new DBConnect();
            Connection connection = dbConnect.getConnection();
            String sql = "select * from thanhvien where id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            // thực thi truy vấn
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                thanhVien.setId(resultSet.getInt("id"));
                thanhVien.setUsername(resultSet.getString("username"));
                thanhVien.setHoTen(resultSet.getString("hoTen"));
                thanhVien.setSoDienThoai(resultSet.getString("soDienThoai"));
                thanhVien.setDiaChi(resultSet.getString("diaChi"));
                thanhVien.setEmail(resultSet.getString("email"));
                thanhVien.setVaiTro(resultSet.getInt("vaiTro"));

            }
            // đóng kết nối
            dbConnect.close(resultSet, statement, connection);
        } catch (Exception e) {
            System.out.println(e);
        }

        return thanhVien;
    }

    // THÊM 1 THÀNH VIÊN MỚI
    public int insert(ThanhVien thanhVien) {
        int id = 0;
        try {
            DBConnect dbConnect = new DBConnect();
            Connection connection = dbConnect.getConnection();
            String sql = "insert into thanhvien (username, password, hoTen, soDienThoai, diaChi, email, vaiTro) "
                    + "values (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, thanhVien.getUsername());
            statement.setString(2, thanhVien.getPassword());
            statement.setString(3, thanhVien.getHoTen());
            statement.setString(4, thanhVien.getSoDienThoai());
            statement.setString(5, thanhVien.getDiaChi());
            statement.setString(6, thanhVien.getEmail());
            statement.setInt(7, thanhVien.getVaiTro());

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

    // SỬA THÔNG TIN 1 THÀNH VIÊN
    public boolean update(ThanhVien thanhVien) {
        boolean ok = false;
        try {
            DBConnect dbConnect = new DBConnect();
            Connection connection = dbConnect.getConnection();
            String sql = "update thanhvien set hoTen = ?, soDienThoai = ?, diaChi = ?, email = ? where id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, thanhVien.getHoTen());
            statement.setString(2, thanhVien.getSoDienThoai());
            statement.setString(3, thanhVien.getDiaChi());
            statement.setString(4, thanhVien.getEmail());
            statement.setInt(5, thanhVien.getId());

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

    // XÓA THÔNG TIN 1 THÀNH VIÊN
    public boolean delete(int id) {
        boolean ok = false;
        try {
            DBConnect dbConnect = new DBConnect();
            Connection connection = dbConnect.getConnection();
            String sql = "delete from thanhvien where id = ?";
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

    // TÌM KIẾM LIST BẠN ĐỌC
    public List<ThanhVien> searchBanDocs(String search) {
        List<ThanhVien> list = new ArrayList<>();
        try {
            DBConnect dbConnect = new DBConnect();
            Connection connection = dbConnect.getConnection();
            String sql = "select * from thanhvien where hoTen like ? and vaiTro = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + search + "%");
            statement.setInt(2, VaiTro.BANDOC.getValue());

            // thực thi truy vấn
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ThanhVien thanhVien = new ThanhVien();
                thanhVien.setId(resultSet.getInt("id"));
                thanhVien.setUsername(resultSet.getString("username"));
                thanhVien.setHoTen(resultSet.getString("hoTen"));
                thanhVien.setSoDienThoai(resultSet.getString("soDienThoai"));
                thanhVien.setDiaChi(resultSet.getString("diaChi"));
                thanhVien.setEmail(resultSet.getString("email"));
                thanhVien.setVaiTro(resultSet.getInt("vaiTro"));

                list.add(thanhVien);
            }

            // đóng kết nối
            dbConnect.close(resultSet, statement, connection);
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }
}
