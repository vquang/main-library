package controller.commons;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import dao.ThanhVienDAO;
import entity.ThanhVien;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import static utils.VaiTro.*;

/**
 *
 * @author Admin
 */
public class TaiKhoanController extends HttpServlet {

    private final ThanhVienDAO thanhVienDAO = new ThanhVienDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        switch (action) {
            case "/bandoc-login":
                // BẠN ĐỌC ĐĂNG NHẬP
                bdLogin(request, response);
                break;
            case "/nhanvien-login":
                // NHÂN VIÊN ĐĂNG NHẬP
                nvLogin(request, response);
                break;
            case "/admin-login":
                // ADMIN ĐĂNG NHẬP
                adminLogin(request, response);
                break;
            case "/logout":
                // ĐĂNG XUẤT
                logout(request, response);
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

    protected void bdLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        ThanhVien thanhVien = new ThanhVien();
        thanhVien.setUsername(username);
        thanhVien.setPassword(password);
        thanhVien.setVaiTro(BANDOC.getValue());
        List<ThanhVien> list = thanhVienDAO.checkLogin(thanhVien);

        if (list.isEmpty()) {
            response.sendRedirect("/main-library/commons/BDLogin.jsp?error=true");
        } else {
            request.getSession().setAttribute("thanhVien", list.get(0));
            response.sendRedirect("/main-library/bandoc/BDTaiLieuController/view");
        }
    }

    protected void nvLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        ThanhVien thanhVien = new ThanhVien();
        thanhVien.setUsername(username);
        thanhVien.setPassword(password);
        thanhVien.setVaiTro(NHANVIEN.getValue());
        List<ThanhVien> list = thanhVienDAO.checkLogin(thanhVien);

        if (list.isEmpty()) {
            response.sendRedirect("/main-library/commons/NVLogin.jsp?error=true");
        } else {
            request.getSession().setAttribute("thanhVien", list.get(0));
            response.sendRedirect("/main-library/nhanvien/NVTaiLieuController/view");
        }
    }

    protected void adminLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        ThanhVien thanhVien = new ThanhVien();
        thanhVien.setUsername(username);
        thanhVien.setPassword(password);
        thanhVien.setVaiTro(ADMIN.getValue());
        List<ThanhVien> list = thanhVienDAO.checkLogin(thanhVien);

        if (list.isEmpty()) {
            response.sendRedirect("/main-library/commons/ADLogin.jsp?error=true");
        } else {
            request.getSession().setAttribute("thanhVien", list.get(0));
            response.sendRedirect("/main-library/admin/AdminHome.jsp");
        }
    }

    protected void logout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int vaiTro = Integer.parseInt(request.getParameter("vaiTro"));
        request.getSession().removeAttribute("thanhVien");
        if (vaiTro == BANDOC.getValue()) {
            response.sendRedirect("/main-library/commons/BDLogin.jsp");
        } else if (vaiTro == NHANVIEN.getValue()) {
            response.sendRedirect("/main-library/commons/NVLogin.jsp");
        } else if (vaiTro == ADMIN.getValue()) {
            response.sendRedirect("/main-library/commons/ADLogin.jsp");
        }
    }
}
