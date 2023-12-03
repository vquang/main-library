/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.nhanvien;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dao.TraTaiLieuDAO;
import entity.HoaDonTra;
import entity.PhieuTra;
import entity.TaiLieuMuon;
import entity.ThanhVien;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Type;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import utils.Time;

/**
 *
 * @author Admin
 */
public class NVTraTaiLieuController extends HttpServlet {

    private final TraTaiLieuDAO traTraLieuDAO = new TraTaiLieuDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = insert(request, response);
        HoaDonTra hoaDonTra = select(request, response, id);

        // lưu hóa đơn trả vừa tạo vào session
        request.getSession().setAttribute("hoaDonTra", hoaDonTra);

        response.sendRedirect("/main-library/nhanvien/NVHoaDonTra.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    // TẠO HÓA ĐƠN TRẢ
    public int insert(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // lấy các giá trị từ form
        int nhanVienId = Integer.parseInt(request.getParameter("nhanVienId"));
        int banDocId = Integer.parseInt(request.getParameter("banDocId"));
        Date ngayTra = Time.stringToDate(request.getParameter("ngayTra"));
        double tienPhat = Double.parseDouble(request.getParameter("tienPhat"));
        String ghiChu = request.getParameter("ghiChu");
        
        Type listType = new TypeToken<List<Integer>>() {}.getType();
        // lấy ra list id của taiLieuMuon
        List<Integer> muonIds = new Gson().fromJson(request.getParameter("muonIds"), listType);
        // lấy ra list id tài liệu, đồng thời tạo map <id tài liệu, số lượng tài liệu>
        List<Integer> taiLieuIds = new Gson().fromJson(request.getParameter("taiLieuIds"), listType);
        
        Map<Integer, Integer> mapTaiLieuIds = taiLieuIds.stream()
                .collect(Collectors.groupingBy(e -> e, Collectors.summingInt(e -> 1)));

        // tạo nhân viên
        ThanhVien nhanVien = new ThanhVien();
        nhanVien.setId(nhanVienId);

        // tạo bạn đọc
        ThanhVien banDoc = new ThanhVien();
        banDoc.setId(banDocId);

        // tạo list tài liệu mượn
        List<TaiLieuMuon> listTaiLieuMuons = new ArrayList<>();
        for (int id : muonIds) {
            TaiLieuMuon taiLieuMuon = new TaiLieuMuon();
            taiLieuMuon.setId(id);

            listTaiLieuMuons.add(taiLieuMuon);
        }

        // tạo phiếu trả
        PhieuTra phieuTra = new PhieuTra();
        phieuTra.setNgayTra(ngayTra);
        phieuTra.setNhanVien(nhanVien);
        phieuTra.setBanDoc(banDoc);
        phieuTra.setListTaiLieuMuons(listTaiLieuMuons);

        // tạo hóa đơn trả
        HoaDonTra hoaDonTra = new HoaDonTra();
        hoaDonTra.setTienPhat(tienPhat);
        hoaDonTra.setGhiChu(ghiChu);
        hoaDonTra.setPhieuTra(phieuTra);

        return traTraLieuDAO.insert(hoaDonTra, mapTaiLieuIds);
    }

    // LẤY HÓA ĐƠN TRẢ VỪA TẠO
    public HoaDonTra select(HttpServletRequest request, HttpServletResponse response, int id)
            throws ServletException, IOException {
        return traTraLieuDAO.select(id);
    }
}
