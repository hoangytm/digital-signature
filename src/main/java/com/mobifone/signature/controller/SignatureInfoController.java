package com.mobifone.signature.controller;

import com.mobifone.signature.model.SignatureInfo;
import com.mobifone.signature.service.SignatureInfoServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;


@RestController
@RequestMapping("digital-signature")
public class SignatureInfoController {

    private final SignatureInfoServiceImpl signatureInfoServiceImpl;

    public SignatureInfoController(SignatureInfoServiceImpl signatureInfoServiceImpl) {
        this.signatureInfoServiceImpl = signatureInfoServiceImpl;
    }

    @PostMapping("verify")
    public List<SignatureInfo> verify(
            @RequestParam("file") @NotNull MultipartFile file) {
        return signatureInfoServiceImpl.getSignatureInfo(file);
    }
}
