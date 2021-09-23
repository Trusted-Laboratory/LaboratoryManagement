package com.lyun.laboratorymanagement;

import com.lyun.laboratorymanagement.utils.PathTools;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
@MapperScan("com.lyun.laboratorymanagement.dao")
public class LaboratoryManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(LaboratoryManagementApplication.class, args);
        System.out.println("当前运行路径:"+PathTools.getRunPath());
        init();
    }

    private static void init(){
        File memberPhoto = new File(PathTools.getRunPath() + "/member_photo");
        if (!memberPhoto.exists())memberPhoto.mkdirs();
    }

}
