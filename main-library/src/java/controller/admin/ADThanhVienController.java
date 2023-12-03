/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dao.ThanhVienDAO;
import entity.ThanhVien;
import static utils.VaiTro.*;
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
public class ADThanhVienController extends HttpServlet {

    private final ThanhVienDAO thanhVienDAO = new ThanhVienDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        switch (action) {
            case "/list":
                // LẤY RA DANH SÁCH THÀNH VIÊN
                list(request, response);
                break;
            case "/select":
                // LẤY THÔNG TIN 1 THÀNH VIÊN
                select(request, response);
                break;
            case "/insert":
                // TẠO MỚI 1 THÀNH VIÊN
                insert(request, response);
                break;
            case "/update":
                // SỬA THÔNG TIN 1 THÀNH VIÊN
                update(request, response);
                break;
            case "/delete":
                // XÓA 1 THÀNH VIÊN
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
        // lấy vai trò từ parameter
        int vaiTro = Integer.parseInt(request.getParameter("vaiTro"));
        List<ThanhVien> list = thanhVienDAO.getAll(vaiTro);

        // set list thành viên vào attribute
        request.setAttribute("listThanhViens", list);

        // kiểm tra xem request đến từ trang nào
        String url = "";
        if (vaiTro == BANDOC.getValue()) {
            url = "/admin/ADBanDoc.jsp";
        } else if (vaiTro == NHANVIEN.getValue()) {
            url = "/admin/ADNhanVien.jsp";
        } else if (vaiTro == ADMIN.getValue()) {
            url = "/admin/ADAdmin.jsp";
        }
        request.getRequestDispatcher(url).forward(request, response);
    }

    public void select(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // lấy id từ parameter
        String id = request.getParameter("id");

        // nếu id khác null thì lấy dữ liệu
        if (id != null) {
            ThanhVien thanhVien = thanhVienDAO.select(Integer.parseInt(id));

            // set thành viên vào attribute
            request.setAttribute("tv", thanhVien);

        }

        request.getRequestDispatcher("/admin/ADCapNhatThanhVien.jsp").forward(request, response);
    }

    public void insert(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // lấy ra giá trị được gửi từ form
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String hoTen = request.getParameter("hoTen");
        String soDienThoai = request.getParameter("soDienThoai");
        String diaChi = request.getParameter("diaChi");
        String email = request.getParameter("email");
        String vaiTro = request.getParameter("vaiTro");

        // nếu dữ liệu không null thì thực hiện insert
        if (username != null && password != null && hoTen != null && soDienThoai != null
                && diaChi != null && email != null && vaiTro != null) {
            ThanhVien thanhVien = new ThanhVien();
            thanhVien.setUsername(username);
            thanhVien.setPassword(password);
            thanhVien.setHoTen(hoTen);
            thanhVien.setSoDienThoai(soDienThoai);
            thanhVien.setDiaChi(diaChi);
            thanhVien.setEmail(email);
            thanhVien.setVaiTro(Integer.parseInt(vaiTro));

            int id = thanhVienDAO.insert(thanhVien);

            response.sendRedirect("/main-library/admin/ADThanhVienController/select?id=" + id);
        }
    }

    public void update(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // lấy ra giá trị được gửi từ form
        String id = request.getParameter("id");
        String hoTen = request.getParameter("hoTen");
        String soDienThoai = request.getParameter("soDienThoai");
        String diaChi = request.getParameter("diaChi");
        String email = request.getParameter("email");
        String vaiTro = request.getParameter("vaiTro");

        // nếu dữ liệu không null thì thực hiện insert
        if (id != null && hoTen != null
                && soDienThoai != null && diaChi != null && email != null && vaiTro != null) {
            ThanhVien thanhVien = new ThanhVien();
            thanhVien.setId(Integer.parseInt(id));
            thanhVien.setHoTen(hoTen);
            thanhVien.setSoDienThoai(soDienThoai);
            thanhVien.setDiaChi(diaChi);
            thanhVien.setEmail(email);
            thanhVien.setVaiTro(Integer.parseInt(vaiTro));

            thanhVienDAO.update(thanhVien);

            response.sendRedirect("/main-library/admin/ADThanhVienController/select?id=" + id);
        }
    }

    public void delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // lấy id từ parameter
        String id = request.getParameter("id");
        int vaiTro = Integer.parseInt(request.getParameter("vaiTro"));

        // nếu id khác null thì lấy dữ liệu
        if (id != null) {
            boolean ok = thanhVienDAO.delete(Integer.parseInt(id));

            if (ok) {
                response.sendRedirect("/main-library/admin/ADThanhVienController/list?vaiTro=" + vaiTro + "&isDelete=true");
            } else {
                response.sendRedirect("/main-library/admin/ADThanhVienController/list?vaiTro=" + vaiTro + "&isDelete=false");
            }
        }
    }
}
