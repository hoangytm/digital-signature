package com.mobifone.signature.util;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.annot.PdfWidgetAnnotation;
import com.itextpdf.signatures.CertificateInfo;
import com.itextpdf.signatures.PdfPKCS7;
import com.itextpdf.signatures.SignaturePermissions;
import com.itextpdf.signatures.SignatureUtil;
import com.mobifone.signature.model.SignatureInfo;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
@Slf4j
public class DigitalSignatureUtil {
    public static List<SignatureInfo> inspectSignatures(InputStream file) throws IOException, GeneralSecurityException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(file));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, false);
        SignaturePermissions perms = null;
        SignatureUtil signUtil = new SignatureUtil(pdfDoc);
        List<String> names = signUtil.getSignatureNames();
        List<SignatureInfo> signatureInfoList = new ArrayList<>();
        for (String name : names) {
            log.info("inspectSignatures " + "===== " + name + " =====");
            SignatureInfo signatureInfo = new SignatureInfo();
            signatureInfo.setName(name);
            perms = inspectSignature(pdfDoc, signUtil, form, name, perms, signatureInfo);
            signatureInfoList.add(signatureInfo);
        }
        return signatureInfoList;
    }

    public static SignaturePermissions inspectSignature(PdfDocument pdfDoc, SignatureUtil signUtil, PdfAcroForm form,
                                                        String name, SignaturePermissions perms, SignatureInfo signatureInfo) throws GeneralSecurityException {
        List<PdfWidgetAnnotation> widgets = form.getField(name).getWidgets();
        if (widgets != null && widgets.size() > 0) {
            Rectangle pos = widgets.get(0).getRectangle().toRectangle();
            int pageNum = pdfDoc.getPageNumber(widgets.get(0).getPage());
            if (pos.getWidth() == 0 || pos.getHeight() == 0) {
                signatureInfo.setPositionSignature("Không hiển thị chữ kí số trên văn bản");
            } else {
                signatureInfo.setPositionSignature(String.format("Trang %s; llx: %s, lly: %s, urx: %s; ury: %s",
                        pageNum, pos.getLeft(), pos.getBottom(), pos.getRight(), pos.getTop()));
            }
        }

        PdfPKCS7 pkcs7 = signUtil.readSignatureData(name);
        X509Certificate cert = pkcs7.getSigningCertificate();
        SimpleDateFormat date_format = new SimpleDateFormat("dd-MM-yyyy kk:mm:ss");
        signatureInfo.setSigner(CertificateInfo.getSubjectFields(cert).getField("CN"));
        signatureInfo.setSignOn(date_format.format(pkcs7.getSignDate().getTime()));
        signatureInfo.setDeptGrantCert(CertificateInfo.getIssuerFields(cert).getField("O"));
        signatureInfo.setLocation(pkcs7.getLocation());
        signatureInfo.setReason(pkcs7.getReason());
        signatureInfo.setIsModified(!pkcs7.verifySignatureIntegrityAndAuthenticity());
        String endTime = date_format.format(pkcs7.getSigningCertificate().getNotAfter());
        String startTime = date_format.format(pkcs7.getSigningCertificate().getNotBefore());
        signatureInfo.setStartDateCert(startTime);
        signatureInfo.setEndDateCert(endTime);
        signatureInfo.setDurationValidCertificate("Từ " + startTime + " đến " + endTime);
        try {
            cert.checkValidity(pkcs7.getSignDate().getTime());
            signatureInfo.setSignOnValidTime(true);
        } catch (Exception exception) {
            exception.printStackTrace();
            signatureInfo.setSignOnValidTime(false);
        }
        PdfDictionary sigDict = signUtil.getSignatureDictionary(name);
        return new SignaturePermissions(sigDict, perms);
    }
}
