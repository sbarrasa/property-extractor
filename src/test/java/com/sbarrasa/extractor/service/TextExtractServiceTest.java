package com.sbarrasa.extractor.service;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.mock.web.MockMultipartFile;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TextExtractServiceTest {

  private final TextExtractService service = new TextExtractService();
  private int cntChars = 100;

  @Test
  void testExtractAllFiles() throws Exception {
    for (Resource res : new PathMatchingResourcePatternResolver()
            .getResources("classpath:files/*"))
      testFile(res);
  }

  private void testFile(Resource res) throws Exception {
    try (InputStream is = res.getInputStream()) {
      var file = new MockMultipartFile("file", res.getFilename(), "application/octet-stream", is);
      String text = service.extract(file);
      assertNotNull(text);
      System.out.printf("%s (first %d chars): %s%n", res.getFilename(), cntChars, firstN(text, cntChars));
    }
  }

  private String firstN(String text, int n) {
    return text.length() <= n ? text : text.substring(0, n);
  }
}
