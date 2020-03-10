package com.lovecyy.file.up.example1.dao;

import com.lovecyy.file.up.example1.pojo.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ys
 * @topic
 * @date 2020/3/10 14:21
 */
public interface UploadFileRepository extends JpaRepository<UploadFile, String> {
    UploadFile findByFileMd5(String fileMd5);

    void deleteByFileMd5(String fileMd5);
}
