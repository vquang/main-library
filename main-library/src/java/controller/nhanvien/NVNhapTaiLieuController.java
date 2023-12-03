/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.nhanvien;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dao.NhapTaiLieuDAO;
import entity.HoaDonNhap;
import entity.NhaCungCap;
import entity.TaiLieu;
import entity.TaiLieuNhap;
import entity.ThanhVien;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import utils.Time;

/**
 *
 * @author Admin
 */
public class NVNhapTaiLieuController extends HttpServlet {

    private final NhapTaiLieuDAO nhapTaiLieuDAO = new NhapTaiLieuDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = insert(request, response);
        HoaDonNhap hoaDonNhap = select(request, response, id);

        // lưu hóa đơn nhập vừa tạo vào session
        request.getSession().setAttribute("hoaDonNhap", hoaDonNhap);

        response.sendRedirect("/main-library/nhanvien/NVHoaDonNhap.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    public int insert(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // lấy các giá trị từ form
        int nhanVienId = Integer.parseInt(request.getParameter("nhanVienId"));
        int nccId = Integer.parseInt(request.getParameter("nccId"));
        Date ngayNhap = Time.stringToDate(request.getParameter("ngayNhap"));
        double tongTien = Double.parseDouble(request.getParameter("tongTien"));
        // lấy list tài liệu nhập
        List<TaiLieuNhap> listTaiLieuNhaps = new ArrayList<>();
        String jsonString = request.getParameter("listNhaps");
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            int id = jsonObject.get("id").getAsInt();
            int soLuong = jsonObject.get("soLuong").getAsInt();
            double giaNhap = jsonObject.get("giaNhap").getAsDouble();

            // tạo tài liệu
            TaiLieu taiLieu = new TaiLieu();
            taiLieu.setId(id);

            // tạo tài liệu nhập
            TaiLieuNhap taiLieuNhap = new TaiLieuNhap();
            taiLieuNhap.setSoLuong(soLuong);
            taiLieuNhap.setGiaNhap(giaNhap);
            taiLieuNhap.setTaiLieu(taiLieu);

            listTaiLieuNhaps.add(taiLieuNhap);
        }

        // tạo nhân viên
        ThanhVien nhanVien = new ThanhVien();
        nhanVien.setId(nhanVienId);

        // tạo nhà cung cấp
        NhaCungCap nhaCungCap = new NhaCungCap();
        nhaCungCap.setId(nccId);

        // tạo hóa đơn nhập
        HoaDonNhap hoaDonNhap = new HoaDonNhap();
        hoaDonNhap.setNgayNhap(ngayNhap);
        hoaDonNhap.setTongTien(tongTien);
        hoaDonNhap.setNhanVien(nhanVien);
        hoaDonNhap.setNhaCungCap(nhaCungCap);
        hoaDonNhap.setListTaiLieuNhaps(listTaiLieuNhaps);

        return nhapTaiLieuDAO.insert(hoaDonNhap);
    }

    public HoaDonNhap select(HttpServletRequest request, HttpServletResponse response, int id)
            throws ServletException, IOException {
        return nhapTaiLieuDAO.select(id);
    }

}
