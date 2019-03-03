package onlineshop.webservice.servlets;

import onlineshop.service.impl.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteProductServlet extends HttpServlet {
    private ProductService productService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.valueOf(req.getParameter("id"));
        productService.deleteById(id);
        Cookie[] cookies = req.getCookies();
        System.out.println(cookies.length);
        for (Cookie cookie : cookies) {
            System.out.println("cookie name "+cookie.getName());
            System.out.println("cookie value "+cookie.getValue());
        }
        resp.sendRedirect("/products");

    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
