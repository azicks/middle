package ru.job4j.servlets;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class CreateUserController extends HttpServlet {
    private final ValidateService service = ValidateService.getInstance();
    private static final Logger log = LogManager.getLogger(CreateUserController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        req.setCharacterEncoding("utf-8");
        String name = null;
        String login = null;
        String email = null;
        String imageFile = null;
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
                    name = "name".equals(item.getFieldName()) ? item.getString() : name;
                    login = "login".equals(item.getFieldName()) ? item.getString() : login;
                    email = "email".equals(item.getFieldName()) ? item.getString() : email;
                } else {
                    imageFile = item.getName();
                    File uploadedFile = new File(path + imageFile);
                    uploadedFile.mkdirs();
                    if (uploadedFile.exists()) {
                        uploadedFile.delete();
                    }
                    item.write(uploadedFile);
                }
            }
            long id = service.addUser(name, login, email, imageFile);
            if (id != -1) {
                Files.move(Path.of(path + imageFile), Path.of(path + id + "_" + imageFile), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        resp.sendRedirect("/");
    }
}

