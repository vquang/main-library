/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.nhanvien;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dao.MuonTaiLieuDAO;
import entity.PhieuMuon;
import entity.TaiLieu;
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
import utils.Time;

/**
 *
 * @author Admin
 */
public class NVMuonTaiLieuController extends HttpServlet {

    private final MuonTaiLieuDAO muonTaiLieuDAO = new MuonTaiLieuDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = insert(request, response);
        PhieuMuon phieuMuon = select(request, response, id);

        // lưu phiếu mượn vừa tạo vào session
        request.getSession().setAttribute("phieuMuon", phieuMuon);

        response.sendRedirect("/main-library/nhanvien/NVPhieuMuon.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    // TẠO PHIẾU MƯỢN
    public int insert(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // lấy các giá trị từ form
        int nhanVienId = Integer.parseInt(request.getParameter("nhanVienId"));
        int banDocId = Integer.parseInt(request.getParameter("banDocId"));
        Date ngayMuon = Time.stringToDate(request.getParameter("ngayMuon"));
        Date ngayPhaiTra = Time.stringToDate(request.getParameter("ngayPhaiTra"));
        
        // lấy ra list id tài liệu đặt trước
        Type listType = new TypeToken<List<Integer>>() {}.getType();
        List<Integer> taiLieuIds = new Gson().fromJson(request.getParameter("taiLieuIds"), listType);
        List<Integer> datTruocIds = new Gson().fromJson(request.getParameter("datTruocIds"), listType);

        // tạo nhân viên
        ThanhVien nhanVien = new ThanhVien();
        nhanVien.setId(nhanVienId);

        // tạo bạn đọc
        ThanhVien banDoc = new ThanhVien();
        banDoc.setId(banDocId);

        // tạo list tài liệu mượn
        List<TaiLieuMuon> listTaiLieuMuons = new ArrayList<>();
        for (Integer id : taiLieuIds) {
            TaiLieu taiLieu = new TaiLieu();
            taiLieu.setId(id);

            TaiLieuMuon taiLieuMuon = new TaiLieuMuon();
            taiLieuMuon.setTaiLieu(taiLieu);

            listTaiLieuMuons.add(taiLieuMuon);
        }

        // tạo phiếu mượn
        PhieuMuon phieuMuon = new PhieuMuon();
        phieuMuon.setNgayMuon(ngayMuon);
        phieuMuon.setNgayPhaiTra(ngayPhaiTra);
        phieuMuon.setNhanVien(nhanVien);
        phieuMuon.setBanDoc(banDoc);
        phieuMuon.setListTaiLieuMuons(listTaiLieuMuons);

        return muonTaiLieuDAO.insert(phieuMuon, datTruocIds);
    }

    // LẤY PHIẾU MƯỢN VỪA TẠO
    public PhieuMuon select(HttpServletRequest request, HttpServletResponse response, int id)
            throws ServletException, IOException {
        return muonTaiLieuDAO.select(id);
    }
}
