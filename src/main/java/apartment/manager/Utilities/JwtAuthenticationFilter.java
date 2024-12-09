package apartment.manager.Utilities;

import apartment.manager.Utilities.models.GlobalExceptionCode;
import apartment.manager.business.UserService;
import apartment.manager.entity.User;
import apartment.manager.presentation.models.ErrorDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        if (skipAuthentication(request)) {
            chain.doFilter(request, response);
            return;
        }
        // Check if the Authorization header contains a Bearer token
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            handleResponse("Authentication token doesn't exist or invalid. Please get a valid token and try again", response);
            return;
        }
        String token = authorizationHeader.substring(7);
        try {
            if (jwtProvider.validateToken(token) != null) {
                String email = jwtProvider.getEmail(token);
                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    User user = this.userService.loadUserByUsername(email);
                    Claims claims = jwtProvider.getClaims(token);
                    Long userVersion = Long.parseLong((String) claims.get(JwtProvider.USER_VERSION_CLAIM));
                    if (!Objects.equals(userVersion, user.getVersion())) {
                        handleResponse("User details were changed. Please use a new token", response);
                        return;
                    }
                    // Set the authentication object into the context
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        } catch (SecurityException | MalformedJwtException | io.jsonwebtoken.security.SignatureException e) {
            handleResponse("JWT was expired or incorrect", response);
            return;
        } catch (ExpiredJwtException e) {
            handleResponse("Expired JWT token.", response);
            return;
        } catch (UnsupportedJwtException e) {
            handleResponse("Unsupported JWT token.", response);
            return;
        } catch (IllegalArgumentException e) {
            handleResponse("JWT token compact of handler are invalid.", response);
            return;
        }
        chain.doFilter(request, response);
    }

    private void handleResponse(String message, HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ErrorDetails errorDetails = new ErrorDetails(message, GlobalExceptionCode.AUTHENTICATION, new Date());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(errorDetails);
        response.getWriter().write(jsonResponse);
    }

    private boolean skipAuthentication(HttpServletRequest request) {
        return Arrays.stream(WebConfig.PUBLIC_URLS).anyMatch(path -> request.getRequestURI().startsWith(path.substring(0, path.length() - 2)));
    }
}
