/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.nhanvien;

import dao.DatTruocTaiLieuDAO;
import dao.MuonTaiLieuDAO;
import dao.ThanhVienDAO;
import entity.TaiLieuDatTruoc;
import entity.TaiLieuMuon;
import entity.ThanhVien;
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
public class NVBanDocController extends HttpServlet {

    private final ThanhVienDAO thanhVienDAO = new ThanhVienDAO();
    private final DatTruocTaiLieuDAO datTruocTaiLieuDAO = new DatTruocTaiLieuDAO();
    private final MuonTaiLieuDAO muonTaiLieuDAO = new MuonTaiLieuDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        switch (action) {
            case "/muon-search":
                // TÌM KIẾM BẠN ĐỌC ĐỂ MƯỢN TÀI LIỆU
                muonSearch(request, response);
                break;
            case "/tra-search":
                // TÌM KIẾM BẠN ĐỌC ĐỂ TRẢ TÀI LIỆU
                traSearch(request, response);
                break;
            case "/dat-truoc":
                // TÌM KIẾM NHỮNG TÀI LIỆU BẠN ĐỌC ĐÃ ĐẶT TRƯỚC
                datTruoc(request, response);
                break;
            case "/dang-muon":
                // TÌM KIẾM NHỮNG TÀI LIỆU BẠN ĐỌC ĐANG MƯỢN
                dangMuon(request, response);
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

    public void muonSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // lấy ra giá trị được gửi từ form
        String search = request.getParameter("search");

        // nếu dữ liệu không null thì thực hiện search
        if (search != null) {
            List<ThanhVien> list = thanhVienDAO.searchBanDocs(search);

            // set list bạn đọc vào attribute
            request.setAttribute("listBanDocs", list);
        }

        request.getRequestDispatcher("/nhanvien/NVMuonTaiLieu.jsp").forward(request, response);
    }

    public void traSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // lấy ra giá trị được gửi từ form
        String search = request.getParameter("search");

        // nếu dữ liệu không null thì thực hiện search
        if (search != null) {
            List<ThanhVien> list = thanhVienDAO.searchBanDocs(search);

            // set list bạn đọc vào attribute
            request.setAttribute("listBanDocs", list);
        }

        request.getRequestDispatcher("/nhanvien/NVTraTaiLieu.jsp").forward(request, response);
    }

    public void datTruoc(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy ra id bạn đọc
        String id = request.getParameter("id");

        // nếu dữ liệu không null thì thực hiện search
        if (id != null) {
            List<TaiLieuDatTruoc> list = datTruocTaiLieuDAO.getList(Integer.parseInt(id));

            // set list tài liệu đặt trước vào session
            request.getSession().setAttribute("listDatTruocs", list);
        }

        request.getRequestDispatcher("/nhanvien/NVMuonTaiLieu.jsp").forward(request, response);
    }

    public void dangMuon(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy ra id bạn đọc
        String id = request.getParameter("id");

        // nếu dữ liệu không null thì thực hiện search
        if (id != null) {
            List<TaiLieuMuon> list = muonTaiLieuDAO.getList(Integer.parseInt(id));

            // set list tài liệu đang mượn vào attribute
            request.setAttribute("listMuons", list);
        }

        request.getRequestDispatcher("/nhanvien/NVTraTaiLieu.jsp").forward(request, response);
    }
}
