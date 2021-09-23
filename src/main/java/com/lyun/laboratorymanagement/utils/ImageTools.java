package com.lyun.laboratorymanagement.utils;

import lombok.SneakyThrows;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;

public class ImageTools {
    @SneakyThrows
    public static String imgToBase64(BufferedImage image){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(image,"jpg",stream);
        return Base64.getEncoder().encodeToString(stream.toByteArray());
    }
}
