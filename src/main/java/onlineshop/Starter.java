package onlineshop;

import onlineshop.dao.jdbc.JdbcProductDao;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import onlineshop.dao.jdbc.JdbcUserDao;
import onlineshop.service.impl.ProductService;
import onlineshop.service.impl.SessionService;
import onlineshop.service.impl.UserService;
import onlineshop.webservice.auth.AdminRoleFilter;
import onlineshop.webservice.auth.AuthFilter;
import onlineshop.webservice.auth.UserFilter;
import onlineshop.webservice.servlets.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.postgresql.ds.PGSimpleDataSource;

import javax.servlet.DispatcherType;
import javax.sql.DataSource;

public class Starter {
    private static final String PROPERTIES_PATH = "src/main/resources/config.properties";

    public static void main(String[] args) throws Exception {

        //config
        DataSource dataSource = getDataSource();
        JdbcProductDao jdbcProductDao = new JdbcProductDao(dataSource);
        ProductService productService = new ProductService();
        productService.setProductDao(jdbcProductDao);

        //JdbcUserDao jdbcUserDao = new JdbcUserDao(jdbcConnectionFactory);
        JdbcUserDao jdbcUserDao = new JdbcUserDao(dataSource);
        UserService userService = new UserService();
        userService.setUserDao(jdbcUserDao);

        SessionService sessionService = new SessionService(userService);

        GetAllProductServlet getAllProductServlet = new GetAllProductServlet();
        getAllProductServlet.setProductService(productService);
        UpdateProductServlet updateProductServlet = new UpdateProductServlet();
        updateProductServlet.setProductService(productService);
        AddProductServlet addProductServlet = new AddProductServlet();
        addProductServlet.setProductService(productService);
        DeleteProductServlet deleteProductServlet =new DeleteProductServlet();
        deleteProductServlet.setProductService(productService);


        LoginServlet loginServlet = new LoginServlet(sessionService);
        LogoutServlet logoutServlet = new LogoutServlet(sessionService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(getAllProductServlet), "/products");
        context.addServlet(new ServletHolder(updateProductServlet), "/products/update");
        context.addServlet(new ServletHolder(addProductServlet), "/products/add");
        context.addServlet(new ServletHolder(deleteProductServlet), "/products/delete");

        context.addServlet(new ServletHolder(loginServlet), "/login");
        context.addServlet(new ServletHolder(logoutServlet),"/logout");

        context.addFilter(new FilterHolder(new AuthFilter(sessionService)),"/products/*", EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(new FilterHolder(new AdminRoleFilter(sessionService)),"/products/add", EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(new FilterHolder(new AdminRoleFilter(sessionService)),"/products/update", EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(new FilterHolder(new AdminRoleFilter(sessionService)),"/products/delete", EnumSet.of(DispatcherType.REQUEST));
        //context.addFilter(new FilterHolder(new UserFilter(sessionService,userService)),"/products/*", EnumSet.of(DispatcherType.REQUEST));

        Server server = new Server(8080);
        server.setHandler(context);
        server.start();

    }


    private static Properties readProperties() throws IOException {
        FileInputStream fileInputStream =null;
        Properties property = new Properties();
        try {
            fileInputStream = new FileInputStream(PROPERTIES_PATH);
            property.load(fileInputStream);
        } catch (IOException e) {
            System.err.println("config file not found");
        } finally {
            fileInputStream.close();
        }
        return property;
    }

    private static DataSource getDataSource() throws IOException {
        Properties properties = readProperties();
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(properties.getProperty("db.host"));
        dataSource.setUser(properties.getProperty("db.login"));
        dataSource.setPassword(properties.getProperty("db.password"));
        return dataSource;
    }
}
