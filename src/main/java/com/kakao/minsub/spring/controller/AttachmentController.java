package com.kakao.minsub.spring.controller;

import com.kakao.minsub.spring.model.UploadAttachmentResponse;
import io.swagger.annotations.Api;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Api("attachments Test")
@Path("/attachments")
@Produces(MediaType.APPLICATION_JSON)
public class AttachmentController {

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public ResponseEntity<?> upload2(@FormDataParam("file") InputStream inputStream,
                                     @FormDataParam("file") FormDataContentDisposition contentDisposition,
                                     @FormDataParam("file") final FormDataBodyPart body) throws IOException {

        String fileName = contentDisposition.getFileName();
        String fileNameExtension = FilenameUtils.getExtension(fileName).toLowerCase();

        String newFileName = RandomStringUtils.randomAlphanumeric(32) + "." + fileNameExtension;
        Files.copy(inputStream, Paths.get("/Users/kakao/temp/storage/" + newFileName));

        UploadAttachmentResponse response = new UploadAttachmentResponse();
        response.setAttachmentUrl("/attachments/" + newFileName);
        response.setFileContentType(contentDisposition.getType());
        response.setFileName(fileName);
        response.setFileSize(contentDisposition.getSize());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
