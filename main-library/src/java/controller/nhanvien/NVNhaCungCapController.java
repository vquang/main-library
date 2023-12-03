/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.nhanvien;

import dao.NhaCungCapDAO;
import entity.NhaCungCap;
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
public class NVNhaCungCapController extends HttpServlet {

    private final NhaCungCapDAO nhaCungCapDAO = new NhaCungCapDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        switch (action) {
            case "/search":
                // TÌM KIẾM BẠN ĐỌC ĐỂ MƯỢN TÀI LIỆU
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

    public void search(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // lấy ra giá trị được gửi từ form
        String search = request.getParameter("search");

        // nếu dữ liệu không null thì thực hiện search
        if (search != null) {
            List<NhaCungCap> list = nhaCungCapDAO.searchNCCs(search);

            // set list nhà cung cấp vào attribute
            request.setAttribute("listNCCs", list);
        }

        request.getRequestDispatcher("/nhanvien/NVNhapTaiLieu.jsp").forward(request, response);
    }
}
