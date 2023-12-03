/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.bandoc;

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
public class BDTaiLieuController extends HttpServlet {

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
            case "/view":
                // LẤY TOP 10 TÀI LIỆU ĐƯỢC MƯỢN NHIỀU NHẤT
                view(request, response);
                break;
            case "/search":
                // TÌM KIẾM TÀI LIỆU ĐỂ XEM
                search(request, response);
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

            // set tài liệu vào session
            request.getSession().setAttribute("taiLieu", taiLieu);

        }

        request.getRequestDispatcher("/bandoc/BDChiTietTaiLieu.jsp").forward(request, response);
    }

    public void view(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<TaiLieu> list = taiLieuDAO.getTopTen();

        // set list tài liệu vào attribute
        request.setAttribute("listTop10", list);

        request.getRequestDispatcher("/bandoc/BanDocHome.jsp").forward(request, response);
    }

    public void search(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // lấy ra giá trị được gửi từ form
        String search = request.getParameter("search");

        // nếu dữ liệu không null thì thực hiện search
        if (search != null) {
            List<TaiLieu> list = taiLieuDAO.search(search);

            // set list tài liệu vào attribute
            request.setAttribute("listTaiLieus", list);
        }

        request.getRequestDispatcher("/bandoc/BDKhoTaiLieu.jsp").forward(request, response);
    }
}
