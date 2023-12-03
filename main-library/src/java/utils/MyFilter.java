/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import entity.ThanhVien;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import static utils.VaiTro.*;

/**
 *
 * @author Admin
 */
@WebFilter("/*")
public class MyFilter implements Filter {

    @Override
    public void doFilter(ServletRequest rq, ServletResponse rp, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) rq;
        HttpServletResponse response = (HttpServletResponse) rp;
        String uri = request.getRequestURI();
        if (uri.contains("/commons")) {
            chain.doFilter(request, response);
        } else {
            ThanhVien thanhVien = (ThanhVien) request.getSession().getAttribute("thanhVien");
            if (thanhVien != null) {
                int vaiTro = thanhVien.getVaiTro();
                if (vaiTro == BANDOC.getValue() && !uri.contains("/bandoc/")) {
                    response.sendRedirect(request.getContextPath() + "/commons/BDLogin.jsp");
                } else if (vaiTro == NHANVIEN.getValue() && !uri.contains("/nhanvien/")) {
                    response.sendRedirect(request.getContextPath() + "/commons/NVLogin.jsp");
                } else if (vaiTro == ADMIN.getValue() && !uri.contains("/admin/")) {
                    response.sendRedirect(request.getContextPath() + "/commons/AdminLogin.jsp");
                } else {
                    chain.doFilter(request, response);
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/commons/NVLogin.jsp");
            }
        }
    }

}
