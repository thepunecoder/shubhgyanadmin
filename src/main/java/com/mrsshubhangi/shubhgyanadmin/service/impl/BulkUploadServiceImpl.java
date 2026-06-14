package com.mrsshubhangi.shubhgyanadmin.service.impl;

import com.mrsshubhangi.shubhgyanadmin.service.BulkUploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BulkUploadServiceImpl
        implements BulkUploadService {
    @Override
    public int upload(MultipartFile file) {
        return 0;
    }
}