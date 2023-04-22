package com.mobifone.signature.service;


import com.mobifone.signature.exception.BadRequestException;
import com.mobifone.signature.model.SignatureInfo;
import com.mobifone.signature.util.DigitalSignatureUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.List;


/**
 * @author PhanHoang
 * 3/2/2021
 */
@Slf4j
@Service
public class SignatureInfoServiceImpl implements SignatureInfoService {

    public List<SignatureInfo> getSignatureInfo(MultipartFile file) {
        try {
            InputStream inputStream = new BufferedInputStream(file.getInputStream());
            return DigitalSignatureUtil.inspectSignatures(inputStream);

        } catch (IOException | GeneralSecurityException e) {
            log.error(e.getMessage(), e);
            throw new BadRequestException("invalid input");
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
            throw new BadRequestException("one error happened");
        }
    }

    @Override
    public List<SignatureInfo> getSignatureInfo(InputStream file) {
        try {
            return DigitalSignatureUtil.inspectSignatures(file);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BadRequestException(e.getMessage());
        }
    }
}
