/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.bandoc;

import entity.TaiLieuDatTruoc;
import dao.DatTruocTaiLieuDAO;
import entity.TaiLieu;
import entity.ThanhVien;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import utils.Time;

/**
 *
 * @author Admin
 */
public class BDDatTruocController extends HttpServlet {

    private final DatTruocTaiLieuDAO datTruocTaiLieuDAO = new DatTruocTaiLieuDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        switch (action) {
            case "/view":
                // LẤY DANH SÁCH TÀI LIỆU ĐÃ ĐẶT TRƯỚC
                view(request, response);
                break;
            case "/order":
                // BẠN ĐỌC ĐẶT TRƯỚC TÀI LIỆU
                order(request, response);
                break;
            case "/cancel":
                // BẠN ĐỌC HỦY ĐẶT TRƯỚC TÀI LIỆU
                cancel(request, response);
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

    public void view(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // lấy id từ parameter
        String id = request.getParameter("id");

        // nếu id khác null thì search dữ liệu
        if (id != null) {
            List<TaiLieuDatTruoc> list = datTruocTaiLieuDAO.getList(Integer.parseInt(id));

            // set list tài liệu đặt trước vào session
            request.getSession().setAttribute("listDatTruocs", list);
            request.getSession().setAttribute("datTruocIds", list.stream().map(dt -> dt.getTaiLieu().getId()).toList());
        }

        request.getRequestDispatcher("/bandoc/BDDatTruocTaiLieu.jsp").forward(request, response);
    }

    public void order(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // lấy dữ liệu từ form
        String banDocId = request.getParameter("banDocId");
        String taiLieuId = request.getParameter("taiLieuId");
        String ngayDat = request.getParameter("ngayDat");
        String ngayHetHan = request.getParameter("ngayHetHan");

        // nếu giá trị khác null thì insert
        if (banDocId != null & taiLieuId != null && ngayDat != null && ngayHetHan != null) {
            // tạo bạn đọc
            ThanhVien banDoc = new ThanhVien();
            banDoc.setId(Integer.parseInt(banDocId));

            // tạo tài liệu
            TaiLieu taiLieu = new TaiLieu();
            taiLieu.setId(Integer.parseInt(taiLieuId));

            // tạo tài liệu đặt trước
            TaiLieuDatTruoc taiLieuDatTruoc = new TaiLieuDatTruoc();
            taiLieuDatTruoc.setNgayDat(Time.stringToDate(ngayDat));
            taiLieuDatTruoc.setNgayHetHan(Time.stringToDate(ngayHetHan));
            taiLieuDatTruoc.setTrangThai(0);
            taiLieuDatTruoc.setBanDoc(banDoc);
            taiLieuDatTruoc.setTaiLieu(taiLieu);

            datTruocTaiLieuDAO.insert(taiLieuDatTruoc);

            // thêm id tài liệu vào list id tài liệu đã đặt trước trong session
            List<Integer> datTruocIds = (List<Integer>) request.getSession().getAttribute("datTruocIds");
            List<Integer> list = new ArrayList<>();
            if (datTruocIds == null) {
                list = new ArrayList<>();
            } else {
                list = new ArrayList<>(datTruocIds);
            }

            list.add(Integer.valueOf(taiLieuId));
            request.getSession().setAttribute("datTruocIds", list);

        }

        response.sendRedirect("/main-library/bandoc/BDTaiLieuController/select?isOrder=true&id=" + taiLieuId);
    }

    public void cancel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // lấy dữ liệu từ parameter
        String banDocId = request.getParameter("banDocId");
        String taiLieuId = request.getParameter("taiLieuId");
        String datTruocId = request.getParameter("datTruocId");
        // nếu giá trị khác null thì delete
        if (banDocId != null && taiLieuId != null && datTruocId != null) {
            boolean ok = datTruocTaiLieuDAO.delete(Integer.parseInt(datTruocId), Integer.parseInt(taiLieuId));

        }

        response.sendRedirect("/main-library/bandoc/BDDatTruocController/view?id=" + banDocId);
    }
}
