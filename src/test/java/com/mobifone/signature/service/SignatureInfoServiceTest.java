package com.mobifone.signature.service;

import com.mobifone.signature.exception.BadRequestException;
import com.mobifone.signature.model.SignatureInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class SignatureInfoServiceTest {
    @InjectMocks
    private SignatureInfoServiceImpl underTest;


    @Test
    public void testNoSignature() throws FileNotFoundException {
//        given
        String EXAMPLE1 = "C:\\Users\\HP\\OneDrive\\Desktop\\sample.pdf";
//        provide
        List<SignatureInfo> actual = underTest.getSignatureInfo(new FileInputStream(EXAMPLE1));
//           then
        Assertions.assertEquals(actual.size(), 0);
    }

    @Test
    public void testException() throws FileNotFoundException {
//        given
        String EXAMPLE1 = "C:\\Users\\HP\\OneDrive\\Desktop\\4-2023-Yeucau Hoi KTeNN ra soat Dieule.pdf";
//           then
        assertThatThrownBy(() -> underTest.getSignatureInfo(new FileInputStream(EXAMPLE1)))
                .isInstanceOf(BadRequestException.class);
    }


}
