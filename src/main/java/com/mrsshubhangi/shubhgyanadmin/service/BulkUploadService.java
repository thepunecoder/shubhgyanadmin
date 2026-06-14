package com.mrsshubhangi.shubhgyanadmin.service;

import org.springframework.web.multipart.MultipartFile;

public interface BulkUploadService {

    int upload(MultipartFile file);
}