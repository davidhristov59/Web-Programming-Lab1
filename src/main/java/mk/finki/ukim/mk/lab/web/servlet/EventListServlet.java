package mk.finki.ukim.mk.lab.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.service.EventService;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "event-servlet") //ako sum na / (root directory) ne treba da se napise urlPattern, prazno se ostava
public class EventListServlet extends HttpServlet {

    private final EventService service;
    private final SpringTemplateEngine templateEngine;

    public EventListServlet(EventService service, SpringTemplateEngine templateEngine) {
        this.service = service;
        this.templateEngine = templateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //1 and 0.6 - for filtering

        String searchName = req.getParameter("searchName");
        String searchRatingString = req.getParameter("searchRating");
        Double searchRating = (searchRatingString != null) ? Double.parseDouble(searchRatingString) : 0.0;

        List<Event> events = service.listAll();

        if (searchName != null && !searchName.isEmpty() ){
            events = service.searchEvents(searchName);
        }

        events = events.stream()
                .filter(e -> e.getPopularityScore() >= searchRating)
                .collect(Collectors.toList());


        IWebExchange exchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);

        WebContext context = new WebContext(exchange);

        context.setVariable("events", events);
        //context.setVariable("events", service.listAll());

        templateEngine.process("listEvents.html", context, resp.getWriter());

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("eventName");
        String numTickets = req.getParameter("numTickets");
        String user = req.getParameter("user"); //dodadeno

//        System.out.println(name);
//        System.out.println(numTickets);

        req.getSession().setAttribute("eventName", name);
        req.getSession().setAttribute("numTickets", numTickets);
        req.getSession().setAttribute("user", user); //jas si go dodavam

        resp.sendRedirect("/eventBooking");
    }


}
