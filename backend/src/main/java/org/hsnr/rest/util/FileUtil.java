package org.hsnr.rest.util;

import com.google.common.io.ByteStreams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class FileUtil {

  private static final Logger LOG = LoggerFactory.getLogger(FileUtil.class);

  private FileUtil() {
  }

  public static boolean isImage(String type) {
    return type.equals("image");
  }

  public static String getMimeType(String fileName) {
    File f = new File(fileName);
    String mimeType = "";
    try {
      mimeType = Files.probeContentType(f.toPath());
    } catch (IOException e) {
      LOG.error("Failed to get the mime type of the file", e);
    }
    String[] type = mimeType.split("/");
    return type[0];
  }

  public static byte[] getDefaultImage() {
    ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    InputStream is = classloader.getResourceAsStream("default_image.png");
    byte[] data = new byte[]{};
    try {
      data = ByteStreams.toByteArray(is);
    } catch (IOException e) {
      LOG.error("Failed to get the byte stream of default_image.png file", e);
    }
    return data;
  }
}
