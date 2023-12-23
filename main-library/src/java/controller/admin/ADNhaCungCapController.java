/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

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
public class ADNhaCungCapController extends HttpServlet {

    private final NhaCungCapDAO nhaCungCapDAO = new NhaCungCapDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        switch (action) {
            case "/list":
                // LẤY RA DANH SÁCH NHÀ CUNG CẤP
                list(request, response);
                break;
            case "/select":
                // LẤY THÔNG TIN 1 NHÀ CUNG CẤP
                select(request, response);
                break;
            case "/insert":
                // TẠO MỚI 1 NHÀ CUNG CẤP
                insert(request, response);
                break;
            case "/update":
                // SỬA THÔNG TIN 1 NHÀ CUNG CẤP
                update(request, response);
                break;
            case "/delete":
                // XÓA 1 NHÀ CUNG CẤP
                delete(request, response);
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

    public void list(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<NhaCungCap> list = nhaCungCapDAO.getAll();

        // set list nhà cung cấp vào attribute
        request.setAttribute("listNCCs", list);

        request.getRequestDispatcher("/admin/ADNhaCungCap.jsp").forward(request, response);
    }

    public void select(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // lấy id từ parameter
        String id = request.getParameter("id");

        // nếu id khác null thì lấy dữ liệu
        if (id != null) {
            NhaCungCap ncc = nhaCungCapDAO.select(Integer.parseInt(id));

            // set nhà cung cấp vào attribute
            request.setAttribute("ncc", ncc);

        }

        request.getRequestDispatcher("/admin/ADCapNhatNhaCungCap.jsp").forward(request, response);
    }

    public void insert(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // lấy ra giá trị được gửi từ form
        String ten = request.getParameter("ten");
        String soDienThoai = request.getParameter("soDienThoai");
        String diaChi = request.getParameter("diaChi");

        // nếu dữ liệu không null thì thực hiện insert
        if (ten != null && soDienThoai != null && diaChi != null) {
            NhaCungCap ncc = new NhaCungCap();
            ncc.setTen(ten);
            ncc.setSoDienThoai(soDienThoai);
            ncc.setDiaChi(diaChi);

            int id = nhaCungCapDAO.insert(ncc);

            response.sendRedirect("/main-library/admin/ADNhaCungCapController/select?id=" + id);
        }
    }

    public void update(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // lấy ra giá trị được gửi từ form
        String id = request.getParameter("id");
        String ten = request.getParameter("ten");
        String soDienThoai = request.getParameter("soDienThoai");
        String diaChi = request.getParameter("diaChi");

        // nếu dữ liệu không null thì thực hiện insert
        if (id != null && ten != null && soDienThoai != null && diaChi != null) {
            NhaCungCap ncc = new NhaCungCap();
            ncc.setId(Integer.parseInt(id));
            ncc.setTen(ten);
            ncc.setSoDienThoai(soDienThoai);
            ncc.setDiaChi(diaChi);

            nhaCungCapDAO.update(ncc);

            response.sendRedirect("/main-library/admin/ADNhaCungCapController/select?id=" + id);
        }
    }

    public void delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // lấy id từ parameter
        String id = request.getParameter("id");

        // nếu id khác null thì lấy dữ liệu
        if (id != null) {
            boolean ok = nhaCungCapDAO.delete(Integer.parseInt(id));

            if (ok) {
                response.sendRedirect("/main-library/admin/ADNhaCungCapController/list?isDelete=true");
            } else {
                response.sendRedirect("/main-library/admin/ADNhaCungCapController/list?isDelete=false");
            }
        }
    }
}
