package org.mx.servicio.seguridad;

import org.jose4j.lang.JoseException;
import org.mx.modelo.comun.bo.UsuarioBO;
import org.mx.servicio.comun.LlaveTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.annotation.security.PermitAll;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Logger;

@Configuration
public class SecurityServletFilter extends HttpFilter {
    private final static Logger log = Logger.getLogger(SecurityServletFilter.class.getName());

    @Autowired
    private LlaveTokenService llaveService;

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        String token = request.getHeader(SecurityConstants.HEADER_AUTHORIZACION_KEY);

        if (request.getServletPath().equals("/controller")) {
            try {
                for (Class cls : getClasses("org.mx.controllers")) {
                    for (Method method : getMethodsAnnotatedWith(cls, PermitAll.class)) {
                        if (method.isAnnotationPresent(PermitAll.class)) {
                            if (method.isAnnotationPresent(RequestMapping.class)) {
                                if (request.getRequestURI().contains(method.getAnnotation(RequestMapping.class).value()[0])) {
                                    chain.doFilter(request, response);
                                    return;
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                log.severe("Error al verificar las anotaciones de validación de seguridad. " + e.getMessage());
                e.printStackTrace();
            }
        } else if (request.getHttpServletMapping().getServletName().equals("Jersey REST Service")) {
            //Permite la petición para los resources de Jersey
            chain.doFilter(request, response);
            return;
        }

        try {

            UsuarioBO usuario = llaveService.validacionToken(token);
            if (null == usuario || usuario.getIdUsuario() <= 0 || token == null) {
                response.sendError(Response.Status.FORBIDDEN.getStatusCode(), "Acceso no autorizado al recurso");
                log.info("Intento de acceso no autorizado al recurso");
                return;
            }
        } catch (JoseException e) {
            log.severe("Error al validar el token. " + e.getMessage());
            e.printStackTrace();
        }

        chain.doFilter(request, response);
    }

    private Iterable<Class> getClasses(String packageName) throws ClassNotFoundException, IOException, URISyntaxException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            URI uri = new URI(resource.toString());
            dirs.add(new File(uri.getPath()));
        }
        List<Class> classes = new ArrayList<Class>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }

        return classes;
    }

    /**
     * Recursive method used to find all classes in a given directory and
     * subdirs.
     *
     * @param directory   The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    private List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists())
            return classes;

        File[] files = directory.listFiles();

        for (File file : files) {
            if (file.isDirectory())
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            else if (file.getName().endsWith(".class"))
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
        }
        return classes;
    }

    private List<Method> getMethodsAnnotatedWith(final Class<?> type, final Class<? extends Annotation> annotation) {
        final List<Method> methods = new ArrayList<Method>();
        Class<?> klass = type;
        while (klass != Object.class) { // need to iterated thought hierarchy in order to retrieve methods from above the current instance
            // iterate though the list of methods declared in the class represented by klass variable, and add those annotated with the specified annotation
            for (final Method method : klass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(annotation)) {
                    Annotation annotInstance = method.getAnnotation(annotation);

                    methods.add(method);
                }
            }
            // move to the upper class in the hierarchy in search for more methods
            klass = klass.getSuperclass();
        }
        return methods;
    }
}
