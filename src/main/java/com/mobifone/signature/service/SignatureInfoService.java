package com.mobifone.signature.service;

import com.mobifone.signature.model.SignatureInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface SignatureInfoService {
    List<SignatureInfo> getSignatureInfo(MultipartFile file);

    List<SignatureInfo> getSignatureInfo(InputStream file);
}
