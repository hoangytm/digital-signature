package com.mobifone.signature.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignatureInfo {

    private String quote;

    private String name;
    //    vi tri cua chu ki so
    private String positionSignature;
    //    ten nguoi ki
    private String signer;
    //    thoi gian ki
    private String signOn;
    //    dia diem ki
    private String location;
    //    li do
    private String reason;
    //    da bi chinh sua hay chua
    private Boolean isModified;
    //    thoi gian hieu luc cua chung thu so
    private String durationValidCertificate;
    //    don vi cap chung thu so
    private String deptGrantCert;
    //    ngày cấp chứng thư số
    private String startDateCert;
    //  ngày hết hạn chứng thư số
    private String endDateCert;

    private boolean signOnValidTime;
    //  chứng thư số đã bị thư hồi hay chưa
    private Boolean isRevoked;
}
