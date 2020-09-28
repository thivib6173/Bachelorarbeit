package io.microservice.thi.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ibm.websphere.jaxrs20.multipart.IAttachment;
import com.ibm.websphere.jaxrs20.multipart.IMultipartBody;
import io.microservice.thi.product.DAO.ImageDAO;
import io.microservice.thi.product.model.Image;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.activation.DataHandler;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Path("upload")
@Tag(name = "ImageUpload")
public class ImageService {

    @Inject
    private ImageDAO imDAO;

    @SuppressWarnings({ "unchecked", "unused", "rawtypes" })
    @POST
    @Consumes("multipart/form-data")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response uploadFile(IMultipartBody input) throws IOException {
        DataHandler handler;
        List<IAttachment> attachmentList = input.getAllAttachments();
        JsonObjectBuilder jb = Json.createObjectBuilder();
        Image image = null;
        for (IAttachment attr : attachmentList) {
            handler = attr.getDataHandler();
            try {
                InputStream stream = handler.getInputStream();
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                String content = attr.getHeader("Content-Type");
                String name = attr.getHeader("Content-Disposition");
                name = name.replaceFirst("(?i)^.*filename=\"?([^\"]+)\"?.*$", "$1");
                String decodedToUTF8 = new String(name.getBytes("ISO-8859-1"), "UTF-8");
                int read = 0;
                byte[] bytes = new byte[1024];
                while ((read = stream.read(bytes)) != -1) {
                    buffer.write(bytes, 0, read);
                }
                stream.close();
                byte[] filebytes = buffer.toByteArray();
                image = imDAO.createImage(filebytes,content,name);
                jb.add("filename", decodedToUTF8);
                buffer.flush();
                buffer.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        JsonObject json = null;
        json = jb.build();
        return Response.ok(json).build();
    }

    private String getFileName(MultivaluedMap<String, String> header) {

        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {

                String[] name = filename.split("=");

                String finalFileName = name[1].trim().replaceAll("\"", "");
                return finalFileName;
            }
        }
        return "unknown";
    }

    @GET
    @Path("{id}")
    public Response getFile(@PathParam("id") int id) throws FileNotFoundException {
        Image data = imDAO.getImageById(id);
        byte[] output = data.getFile();

        return Response.ok(output, data.getContent().toString())
                .header("Content-Disposition", "attachement; filename=\"" + data.getName() + "\"").build();
    }


    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response deleteImage(@PathParam("id") Integer id) throws JsonProcessingException {
        Integer dok = imDAO.deleteImage(id);
        if(dok == 1)
            return Response.ok("Datei gelöscht").build();
        else
            return Response.noContent().build();
    }

}


