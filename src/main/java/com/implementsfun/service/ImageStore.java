package com.implementsfun.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 很明显测试client-streaming类似ali OSS的断点续传
 */
public interface ImageStore {
    String save(String laptopID, String imageType, ByteArrayOutputStream imageData)throws IOException;
}
