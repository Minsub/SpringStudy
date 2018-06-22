package com.kakao.minsub.spring.controller;

import com.kakao.minsub.spring.exception.*;
import com.kakao.minsub.spring.exception.BadRequestException;
import com.kakao.minsub.spring.model.UploadAttachmentResponse;
import io.swagger.annotations.Api;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

@Api("attachments Test")
@Path("/attachments")
@Produces(MediaType.APPLICATION_JSON)
public class AttachmentController {
//    private final String PATH = "/Users/kakao/temp/storage/";
    private final String PATH = "/tmp/";

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public ResponseEntity<?> upload(@FormDataParam("file") InputStream inputStream,
                                    @FormDataParam("file") FormDataContentDisposition contentDisposition,
                                    @FormDataParam("file") final FormDataBodyPart body) throws IOException {

        String fileName = contentDisposition.getFileName();
        String fileNameExtension = FilenameUtils.getExtension(fileName).toLowerCase();

        String newFileName = RandomStringUtils.randomAlphanumeric(32) + "." + fileNameExtension;
        Files.copy(inputStream, Paths.get(PATH + newFileName));

        UploadAttachmentResponse response = new UploadAttachmentResponse();
        response.setAttachmentUrl("/attachments/download/" + newFileName);
        response.setFileContentType(contentDisposition.getType());
        response.setFileName(fileName);
        response.setFileSize(contentDisposition.getSize());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GET
    @Path("/download/{fileName}")
    public Response download(@PathParam("fileName") String fileName) {
        StreamingOutput fileStream = output -> {
            java.nio.file.Path path = Paths.get(PATH + fileName);
            if (!Files.exists(path)) {
                throw new BadRequestException("Not found file: " + fileName);
            }
            try {
                output.write(Files.readAllBytes(path));
                output.flush();
            } catch (Exception e) {
                throw new BadRequestException("download Error");
            }
        };
        
        return Response
                .ok(fileStream, MediaType.APPLICATION_OCTET_STREAM)
                .header("content-disposition","attachment; filename="+fileName)
                .build();
    }
}
