package com.lyun.laboratorymanagement.service;

import com.lyun.laboratorymanagement.dao.ImageDao;
import com.lyun.laboratorymanagement.entity.Image;
import com.lyun.laboratorymanagement.utils.ImageTools;
import com.lyun.laboratorymanagement.utils.PathTools;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Service
@Transactional
public class ImageServiceImpl implements ImageService{

    @Autowired
    private ImageDao imageDao;
    @Override
    public List<Image> findAll() {
        return imageDao.findAll();
    }

    @SneakyThrows
    @Override
    public String getById(Integer id) {
        if (id != null){
            BufferedImage image = ImageIO.read(new File(PathTools.getRunPath() + "/images/" + imageDao.getById(id).getImg() + ".jpg"));
            return ImageTools.imgToBase64(image);
        }
        return null;
    }

    @Override
    public Image getByImg(String img) {
        if (img ==null)return null;
        return imageDao.getByImg(DigestUtils.md5DigestAsHex(img.getBytes(StandardCharsets.UTF_8)));
    }

    @SneakyThrows
    @Override
    public void addImg(String img) {
        imageDao.addImg(DigestUtils.md5DigestAsHex(img.getBytes(StandardCharsets.UTF_8)));
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bytes1 = decoder.decode(img);
        File imageFile = new File(PathTools.getRunPath() + "/images/" + DigestUtils.md5DigestAsHex(img.getBytes(StandardCharsets.UTF_8))+".jpg");
        if (!imageFile.getParentFile().exists())imageFile.getParentFile().mkdirs();
        System.out.println(imageFile.getAbsolutePath());
        OutputStream out = new FileOutputStream(imageFile);
        out.write(bytes1);
        out.flush();
        out.close();
    }
}
