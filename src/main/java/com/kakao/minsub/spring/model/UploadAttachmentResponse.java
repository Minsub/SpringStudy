package com.kakao.minsub.spring.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UploadAttachmentResponse {
    
    private String fileName;
    
    private long fileSize;
    
    private String fileContentType;
    
    private String attachmentUrl;
}
