/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.nhanvien;

import dao.TaiLieuDAO;
import entity.TaiLieu;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author Admin
 */
public class NVTaiLieuController extends HttpServlet {

    private final TaiLieuDAO taiLieuDAO = new TaiLieuDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        switch (action) {
            case "/select":
                // LẤY THÔNG TIN 1 TÀI LIỆU
                select(request, response);
                break;
            case "/insert":
                // TẠO 1 TÀI LIỆU MỚI
                insert(request, response);
                break;
            case "/update":
                // SỬA 1 TÀI LIỆU
                update(request, response);
                break;
            case "/delete":
                // XÓA 1 TÀI LIỆU
                delete(request, response);
                break;
            case "/view":
                // LẤY TOP 10 TÀI LIỆU ĐƯỢC MƯỢN NHIỀU NHẤT
                view(request, response);
                break;
            case "/kho-search":
                // TÌM KIẾM TÀI LIỆU ĐỂ XEM
                khoSearch(request, response);
                break;
            case "/muon-search":
                // TÌM KIẾM TÀI LIỆU ĐỂ MƯỢN
                muonSearch(request, response);
                break;
            case "/tra-search":
                // TÌM KIẾM TÀI LIỆU ĐỂ TRẢ
                traSearch(request, response);
                break;
            case "/nhap-search":
                // TÌM KIẾM TÀI LIỆU ĐỂ NHẬP
                nhapSearch(request, response);
                break;
            default:
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    public void select(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // lấy ra giá trị được gửi từ parameter
        String id = request.getParameter("id");

        // nếu dữ liệu không null thì thực hiện insert
        if (id != null) {
            TaiLieu taiLieu = taiLieuDAO.select(Integer.parseInt(id));

            // set tài liệu vào attribute
            request.setAttribute("taiLieu", taiLieu);
        }

        request.getRequestDispatcher("/nhanvien/NVCapNhatTaiLieu.jsp").forward(request, response);
    }

    public void insert(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // lấy ra giá trị được gửi từ form
        String ten = request.getParameter("ten");
        String tacGia = request.getParameter("tacGia");
        String moTa = request.getParameter("moTa");
        String soLuong = request.getParameter("soLuong");
        String anhBia = request.getParameter("anhBia");

        // nếu dữ liệu không null thì thực hiện insert
        if (ten != null && tacGia != null && soLuong != null && anhBia != null) {
            TaiLieu taiLieu = new TaiLieu();
            taiLieu.setTen(ten);
            taiLieu.setTacGia(tacGia);
            taiLieu.setMoTa(moTa);
            taiLieu.setSoLuong(Integer.parseInt(soLuong));
            taiLieu.setAnhBia(anhBia);

            int id = taiLieuDAO.insert(taiLieu);

            response.sendRedirect("/main-library/nhanvien/NVTaiLieuController/select?id=" + id);
        }

    }

    public void update(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // lấy ra giá trị được gửi từ form
        String id = request.getParameter("id");
        String ten = request.getParameter("ten");
        String tacGia = request.getParameter("tacGia");
        String moTa = request.getParameter("moTa");
        String anhBia = request.getParameter("anhBia");

        // nếu dữ liệu không null thì thực hiện update
        if (id != null && ten != null && tacGia != null && anhBia != null) {
            TaiLieu taiLieu = new TaiLieu();
            taiLieu.setId(Integer.parseInt(id));
            taiLieu.setTen(ten);
            taiLieu.setTacGia(tacGia);
            taiLieu.setMoTa(moTa);
            taiLieu.setAnhBia(anhBia);

            taiLieuDAO.update(taiLieu);
        }

        response.sendRedirect("/main-library/nhanvien/NVTaiLieuController/select?id=" + id);
    }

    public void delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // lấy ra giá trị được gửi từ parameter
        String id = request.getParameter("id");

        // nếu dữ liệu không null thì thực hiện delete
        if (id != null) {
            boolean ok = taiLieuDAO.delete(Integer.parseInt(id));
            if (ok) {
                response.sendRedirect("/main-library/nhanvien/NVKhoTaiLieu.jsp?isDelete=true");
            } else {
                response.sendRedirect("/main-library/nhanvien/NVKhoTaiLieu.jsp?isDelete=false");
            }
        }
    }

    public void view(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<TaiLieu> list = taiLieuDAO.getTopTen();

        // set list tài liệu vào attribute
        request.setAttribute("listTop10", list);

        request.getRequestDispatcher("/nhanvien/NhanVienHome.jsp").forward(request, response);
    }

    public void khoSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // lấy ra giá trị được gửi từ form
        String search = request.getParameter("search");

        // nếu dữ liệu không null thì thực hiện search
        if (search != null) {
            List<TaiLieu> list = taiLieuDAO.search(search);

            // set list tài liệu vào attribute
            request.setAttribute("listTaiLieus", list);
        }

        request.getRequestDispatcher("/nhanvien/NVKhoTaiLieu.jsp").forward(request, response);
    }

    public void muonSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // lấy ra giá trị được gửi từ form
        String search = request.getParameter("search");

        // nếu dữ liệu không null thì thực hiện search
        if (search != null) {
            List<TaiLieu> list = taiLieuDAO.search(search);

            // set list tài liệu vào attribute
            request.setAttribute("listTaiLieus", list);
        }

        request.getRequestDispatcher("/nhanvien/NVMuonTaiLieu.jsp").forward(request, response);
    }

    public void traSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // lấy ra giá trị được gửi từ form
        String search = request.getParameter("search");

        // nếu dữ liệu không null thì thực hiện search
        if (search != null) {
            List<TaiLieu> list = taiLieuDAO.search(search);

            // set list tài liệu vào attribute
            request.setAttribute("listTaiLieus", list);
        }

        request.getRequestDispatcher("/nhanvien/NVTraTaiLieu.jsp").forward(request, response);
    }

    public void nhapSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // lấy ra giá trị được gửi từ form
        String search = request.getParameter("search");

        // nếu dữ liệu không null thì thực hiện search
        if (search != null) {
            List<TaiLieu> list = taiLieuDAO.search(search);

            // set list tài liệu vào attribute
            request.setAttribute("listTaiLieus", list);
        }

        request.getRequestDispatcher("/nhanvien/NVNhapTaiLieu.jsp").forward(request, response);
    }
}
