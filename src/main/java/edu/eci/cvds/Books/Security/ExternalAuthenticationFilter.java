package edu.eci.cvds.Books.Security;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.security.core.context.SecurityContextHolder;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.web.authentication.AuthenticationFilter;


@Component
public class ExternalAuthenticationFilter  extends OncePerRequestFilter {

    private final String AUTH_API_URL = "https://cvds-project-cnb6c0cuddfyc9fe.mexicocentral-01.azurewebsites.net/validador/validar";

    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {


        String authHeader = request.getHeader("Authorization");

        // Si el encabezado "Authorization" está ausente o no tiene el formato esperado, se retorna un error
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Authorization header is missing or invalid");
            return;
        }

        // Extraer el token del encabezado "Authorization" (después de "Bearer")
        String token = authHeader.substring(7);
        String rol = request.getParameter("rol");
        String nombreUsuario = request.getParameter("nombreUsuario");

        // Llama a la API externa para validar el token y obtener usuario y rol
        JsonNode validationResponse = validateTokenWithExternalApi(token,rol,nombreUsuario);

        // Si la respuesta es válida (el campo "valid" es verdadero), proceder con la autenticación
        if (validationResponse != null && validationResponse.get("valid").asBoolean()) {
            String username = validationResponse.get("username").asText();
            String role = validationResponse.get("role").asText();

            // Crear una lista de roles basada en la respuesta de la API externa
            List<SimpleGrantedAuthority> roles = List.of(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));

            // Configura el contexto de seguridad
            Authentication auth = new UsernamePasswordAuthenticationToken(username, null, roles);
            SecurityContextHolder.getContext().setAuthentication(auth);

            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid token");
        }
    }

    /**
     * Método para validar el token enviándolo a la API externa. Se realiza una solicitud HTTP POST
     * para validar el token y se obtiene una respuesta que indica si el token es válido o no.
     *
     * @param token El token de autorización que se desea validar.
     * @return Un objeto JsonNode que contiene la respuesta de la API externa. Si el token es válido,
     *         contiene un campo "valid" con valor true, junto con el nombre de usuario y el rol.
     */
    private JsonNode validateTokenWithExternalApi(String token, String rol, String nombreUsuario) {
        try {
            // Establecer la conexión con la API externa usando la URL proporcionada
            HttpURLConnection connection = (HttpURLConnection) new URL(AUTH_API_URL).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Crea el cuerpo de la solicitud JSON
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode requestBody = mapper.createObjectNode();
            requestBody.put("token", token);
            requestBody.put("rol", rol);
            requestBody.put("nombreUsuario", nombreUsuario);

            // Escribir el cuerpo de la solicitud en el OutputStream de la conexión
            try (OutputStream os = connection.getOutputStream()) {
                os.write(requestBody.toString().getBytes(StandardCharsets.UTF_8));
            }

            // Si la respuesta es 200 OK, leer la respuesta y convertirla a un objeto JsonNode
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpServletResponse.SC_OK) {
                ObjectMapper objectMapper = new ObjectMapper();
                ObjectNode responseNode = objectMapper.createObjectNode();
                responseNode.put("valid", true);
                responseNode.put("username", nombreUsuario);
                responseNode.put("role", rol);
                return responseNode;
//                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
//                    StringBuilder response = new StringBuilder();
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        response.append(line);
//                    }
//                    // Convertir la respuesta JSON a un objeto JsonNode utilizando ObjectMapper
//                    return mapper.readTree(response.toString());
               // }
            } else {
                //token invalido
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


// Simula la respuesta de la API externa
//    private ObjectNode validateTokenWithExternalApi(String token) {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            ObjectNode responseNode = objectMapper.createObjectNode();
//
//            responseNode.put("valid", true);
//            responseNode.put("username", "user123");
//            responseNode.put("role", "admin");
//            return responseNode;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
}

