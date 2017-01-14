package io.caojun221.examples.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.junit.Test;

public class TomcatTest {

    private static class TestServlet extends HttpServlet {

        private static final long serialVersionUID = 8827762876548735293L;

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            resp.getWriter().write("hello world");
        }
    }

    @Test
    public void test() throws Exception {
        Tomcat tomcat = new Tomcat();

        Context ctx = tomcat.addContext("", null);

        Tomcat.addServlet(ctx, "testServlet", new TestServlet());
        ctx.addServletMappingDecoded("/", "testServlet");

        tomcat.start();
        tomcat.getServer().await();
    }
}
