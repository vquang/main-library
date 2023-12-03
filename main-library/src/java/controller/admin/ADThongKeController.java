/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dao.ThongKeDAO;
import entity.ThongKeBanDoc;
import entity.ThongKeTaiLieu;
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
public class ADThongKeController extends HttpServlet {

    private final ThongKeDAO thongKeDAO = new ThongKeDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        switch (action) {
            case "/tailieu":
                // THỐNG KÊ TÀI LIỆU THEO SỐ LƯỢT MƯỢN
                tkTaiLieu(request, response);
                break;
            case "/bandoc":
                // THỐNG KÊ BẠN ĐỌC THEO SỐ TÀI LIỆU ĐÃ MƯỢN
                tkBanDoc(request, response);
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

    public void tkTaiLieu(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // lấy dữ liệu từ parameter
        String thuTu = request.getParameter("thuTu");

        // nếu thuTu khác null thì lấy danh sách thông kê
        if (thuTu != null) {
            List<ThongKeTaiLieu> list = thongKeDAO.getListTKTaiLieus(thuTu);

            // set list thống kê tài liệu vào attribute
            request.setAttribute("thongKeTLs", list);
        }
        request.getRequestDispatcher("/admin/ADThongKe.jsp").forward(request, response);
    }

    public void tkBanDoc(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // lấy dữ liệu từ parameter
        String thuTu = request.getParameter("thuTu");

        // nếu thuTu khác null thì lấy danh sách thông kê
        if (thuTu != null) {
            List<ThongKeBanDoc> list = thongKeDAO.getListTKBanDocs(thuTu);

            // set list thống kê tài liệu vào attribute
            request.setAttribute("thongKeBDs", list);
        }
        request.getRequestDispatcher("/admin/ADThongKe.jsp").forward(request, response);
    }
}
