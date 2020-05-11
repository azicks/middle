package ru.job4j.servlets.controllers;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.servlets.UserStorage;
import ru.job4j.servlets.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class CreateUserController extends HttpServlet {
    private final ValidateService service = ValidateService.getInstance();
    private final UserStorage userStorage = UserStorage.getInstance();
    private static final Logger log = LogManager.getLogger(CreateUserController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("roles", userStorage.getUserRoles());
        req.getRequestDispatcher("/WEB-INF/views/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean isMultipart = ServletFileUpload.isMultipartContent(req);
        if (!isMultipart) {
            log.error("Not Multipart Content");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Not Multipart Content");
            return;
        }
        String name = null;
        String login = null;
        String password = null;
        String email = null;
        String imageFile = null;
        String role = null;
        String country = null;
        String city = null;
        DiskFileItemFactory factory = new DiskFileItemFactory();
        File tempDir = (File) getServletContext().getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(tempDir);
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(1024 * 1024 * 3);
        String path = getServletContext().getRealPath("/bin/images/");
        try {
            List<FileItem> items = upload.parseRequest(req);
            for (FileItem item : items) {
                if (item.isFormField()) {
                    name = "name".equals(item.getFieldName()) ? item.getString("utf-8") : name;
                    login = "login".equals(item.getFieldName()) ? item.getString("utf-8") : login;
                    password = "password".equals(item.getFieldName()) ? item.getString("utf-8") : password;
                    email = "email".equals(item.getFieldName()) ? item.getString("utf-8") : email;
                    role = "role".equals(item.getFieldName()) ? item.getString("utf-8") : role;
                    country = "country".equals(item.getFieldName()) ? item.getString("utf-8") : country;
                    city = "city".equals(item.getFieldName()) ? item.getString("utf-8") : city;
                } else {
                    imageFile = item.getName();
                    if (!imageFile.isEmpty()) {
                        File uploadedFile = new File(path + imageFile);
                        uploadedFile.mkdirs();
                        if (uploadedFile.exists()) {
                            uploadedFile.delete();
                        }
                        item.write(uploadedFile);
                    }
                }
            }
            long id = service.addUser(name, login, password, email, imageFile, role, country, city);
            if (id == -2) {
                String message = String.format("User with login %s already exists", login);
                log.trace(message);
                resp.sendError(HttpServletResponse.SC_CONFLICT, message);
                return;
            }
            if (id > 0) {
                renameImageFile(String.valueOf(id), path, imageFile);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            return;
        }
        resp.sendRedirect("/");
    }
    private void renameImageFile(String id, String path, String imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            Files.move(Path.of(path + imageFile), Path.of(path + id + "_" + imageFile), StandardCopyOption.REPLACE_EXISTING);
        }
    }
}

