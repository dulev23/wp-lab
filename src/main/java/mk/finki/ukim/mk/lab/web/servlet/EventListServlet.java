//package mk.finki.ukim.mk.lab.web.servlet;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import mk.finki.ukim.mk.lab.model.Category;
//import mk.finki.ukim.mk.lab.model.Event;
//import mk.finki.ukim.mk.lab.model.EventBooking;
//import mk.finki.ukim.mk.lab.service.CategoryService;
//import mk.finki.ukim.mk.lab.service.EventBookingService;
//import mk.finki.ukim.mk.lab.service.EventService;
//import org.thymeleaf.context.WebContext;
//import org.thymeleaf.spring6.SpringTemplateEngine;
//import org.thymeleaf.web.IWebExchange;
//import org.thymeleaf.web.servlet.JakartaServletWebApplication;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@WebServlet(name = "eventList-servlet")
//public class EventListServlet extends HttpServlet {
//
//    private final SpringTemplateEngine templateEngine;
//    private final EventService eventService;
//    private final EventBookingService eventBookingService;
//    private final CategoryService categoryService;
//
//    public EventListServlet(SpringTemplateEngine templateEngine, EventService eventService, EventBookingService eventBookingService, CategoryService categoryService) {
//        this.templateEngine = templateEngine;
//        this.eventService = eventService;
//        this.eventBookingService = eventBookingService;
//        this.categoryService = categoryService;
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext()).buildExchange(req, resp);
//        WebContext context = new WebContext(webExchange);
//        String searchByName = req.getParameter("searchByName");
//        String searchByRating = req.getParameter("searchByRating");
//        String searchByCategory = req.getParameter("searchByCategory");
//        context.setVariable("categories", categoryService.listCategories());
//        List<Event> eventList = new ArrayList<>();
//        int rating;
//
//        if (searchByName != null && searchByRating != null && !searchByName.isEmpty() && !searchByRating.isEmpty()) {
//            rating = Integer.parseInt(searchByRating);
//            eventList = eventList.stream().filter(event -> event.getName().toLowerCase().contains(searchByName.toLowerCase())
//                            && event.getPopularityScore() >= rating)
//                    .collect(Collectors.toList());
//            context.setVariable("event", eventList);
//        } else if (searchByName != null && !searchByName.isEmpty()) {
//            context.setVariable("event", eventService.searchEvents(searchByName));
//        } else if (searchByRating != null && !searchByRating.isEmpty()) {
//            context.setVariable("event", eventService.searchByRating(Integer.parseInt(searchByRating)));
//        } else if (searchByCategory != null && !searchByCategory.isEmpty()) {
//            context.setVariable("event", eventService.searchByCategory(new Category(searchByCategory)));
//        } else {
//            context.setVariable("event", eventService.listAll());
//        }
//        templateEngine.process("listEvents.html", context, resp.getWriter());
//    }
//
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String eventName = req.getParameter("eventName");
//        String attendeeName = req.getParameter("attendeeName");
//        String attendeeAddress = req.getParameter("attendeeAddress");
//        int numTickets = Integer.parseInt(req.getParameter("numTickets"));
//        EventBooking eb = eventBookingService.placeBooking(eventName,attendeeName,attendeeAddress,numTickets);
//        req.getSession().setAttribute("eventBooking", eb);
//        resp.sendRedirect("/eventBooking");
//    }
//}
