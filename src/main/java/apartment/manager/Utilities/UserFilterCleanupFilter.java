//package apartment.manager.Utilities;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import org.hibernate.Session;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.lang.NonNull;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
// TODO: Drop this file
//@Component
//public class UserFilterCleanupFilter extends OncePerRequestFilter {
//
//    @Autowired
//    HttpSession session;
//    Session hibernateSession;
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @Override
//    protected void doFilterInternal(@NonNull HttpServletRequest request,
//                                    @NonNull HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//        try {
//            enableUserFilter();
//            filterChain.doFilter(request, response);
//        } finally {
//            disableUserFilter();
//        }
//    }
//
//    private void enableUserFilter() {
//        hibernateSession = entityManager.unwrap(Session.class);
//        hibernateSession.enableFilter("userFilter").setParameter("userId", 1L);
//        //.setParameter("userId", (Long)session.getAttribute(JwtAuthenticationFilter.USER_ID_SESSION_ATTRIBUTE));
//
//        if (hibernateSession.getEnabledFilter("userFilter") != null) {
//            hibernateSession.disableFilter("userFilter");
//        }
//    }
//
//    private void disableUserFilter() {
//        Session session = entityManager.unwrap(Session.class);
//        if (session.getEnabledFilter("userFilter") != null) {
//            session.disableFilter("userFilter");
//        }
//    }
//}
//
