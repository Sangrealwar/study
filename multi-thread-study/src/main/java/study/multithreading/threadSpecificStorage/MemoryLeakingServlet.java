package study.multithreading.threadSpecificStorage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 名称：
 * 功能：ThreadLocal的内存泄露，造成这种现象是因为Web应用程序的类加载器持有对ThreadLocal的可达应用
 * 条件：
 * Created by wq on 2017/9/9.
 */
public class MemoryLeakingServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    //自定义的ThreadLocal实例由WebAppClassLoader负责加载
    private final static ThreadLocal<Counter> TL_COUNTER = new ThreadLocal<Counter>() {
        @Override
        protected Counter initialValue() {
            return new Counter();
        }
    };

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pwr = resp.getWriter();
        pwr.write(String.valueOf(TL_COUNTER.get().getAndIncrement()));
        pwr.close();
    }
}
