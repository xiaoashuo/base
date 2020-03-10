package com.lovecyy.file.up.example1;

import com.lovecyy.file.up.example3.pojo.MultipartFileParam;
import com.lovecyy.file.up.example3.utils.Constants;
import com.lovecyy.file.up.example3.utils.FileMD5Util;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author ys
 * @topic
 * @date 2020/3/9 14:54
 */
@Service
public class FileManage {
    private static final Logger log= LoggerFactory.getLogger(FileManage.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${yakir.upload.dir}")
    private String finalDirPath;
    @Value("${yakir.upload.chunkSize}")
    private long CHUNK_SIZE;

    /**
     * 方式一、通过RandomAccessFile 完成上传
     * @param param
     * @throws IOException
     */
    public void uploadFileRandomAccessFile(MultipartFileParam param) throws IOException{
        String fileName = param.getName();
        //临时文件目录
        String tempDirPath = finalDirPath+param.getMd5();
        //临时文件名字
        String tempFileName=fileName+"_tmp";
        //若临时目录文件夹不存在 则创建
        File tempDir = new File(tempDirPath);
        if (!tempDir.exists()){
            tempDir.mkdirs();
        }
        File tempFile = new File(tempDirPath,tempFileName);

        RandomAccessFile accessTmpFile = new RandomAccessFile(tempFile, "rw");
        //当前文件已经读取到的位置 即偏移量
        long offset=CHUNK_SIZE*param.getChunk();
        //定位到该分片的偏移量
        accessTmpFile.seek(offset);
        //写入该分片数据
        accessTmpFile.write(param.getFile().getBytes());
        //释放
        accessTmpFile.close();
        boolean isOk=checkAndSetUploadProgress(param,tempDirPath);
        if (isOk){
            boolean flag=renameFile(tempFile,fileName);
            System.out.println("upload complete "+ flag+"name="+fileName);
        }
    }

    public  void uploadFileByMappedByteBuffer(MultipartFileParam param) throws IOException{
        //得到文件名字
        String fileName = param.getName();
        //得到上传目录  真实路径+md5
        String uploadDirPath = finalDirPath + param.getMd5();
        //临时文件名字 123_tmp
        String tempFileName=fileName+"_tmp";
        File tempDir = new File(uploadDirPath);
        //判断路径是否存在 不存在 则创建
        if (!tempDir.exists()){
            tempDir.mkdirs();
        }
        File tempFile = new File(uploadDirPath,tempFileName);
        RandomAccessFile tempRaf = new RandomAccessFile(tempFile, "rw");
        FileChannel fileChannel = tempRaf.getChannel();
        //写入该分片数据

        long offset = CHUNK_SIZE * param.getChunk();
        byte[] fileData = param.getFile().getBytes();
        //直接子节缓冲
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, offset, fileData.length);
        mappedByteBuffer.put(fileData);
        ///释放
        FileMD5Util.freedMappedByteBuffer(mappedByteBuffer);
        fileChannel.close();
        boolean isOk=checkAndSetUploadProgress(param,uploadDirPath);
        if (isOk){
            boolean flag=renameFile(tempFile,fileName);
            System.out.println("upload complete "+ flag+"name="+fileName);
        }
    }


    /**
     * 上传完成后重命名文件名字
     * @param toBeRenamed
     * @param toFileNewName
     * @return
     */
    private boolean renameFile(File toBeRenamed, String toFileNewName) {
        //检查要重命名的文件是否存在 是否是文件
        if (!toBeRenamed.exists()||toBeRenamed.isDirectory()){
            log.info("File does not exist: "+toBeRenamed.getName());
            return false;
        }
        String parent = toBeRenamed.getParent();
        File newFile = new File(parent + File.separatorChar + toFileNewName);
        //修改文件名 如果被新文件名已存在，那么renameTo()不会成功
        return toBeRenamed.renameTo(newFile);

    }


    /**
     * 检查文件是否上传完成 并设置当前上传进度
     * @param param
     * @param uploadDirPath
     * @return
     */
    private boolean checkAndSetUploadProgress(MultipartFileParam param, String uploadDirPath) throws IOException{
        String fileName = param.getName();
        File confFile = new File(uploadDirPath, fileName + ".conf");
        RandomAccessFile accessConfFile = new RandomAccessFile(confFile, "rw");
        System.out.println("set part "+param.getChunk()+" complete");
        accessConfFile.setLength(param.getChunkTotal());
        accessConfFile.seek(param.getChunk());
        accessConfFile.write(Byte.MAX_VALUE);
        //completeList 检查是否全部完成 如果数组里是否全部都是 (全部分片都成功上传)
        byte[] completeList = FileUtils.readFileToByteArray(confFile);
        byte isComplete=Byte.MAX_VALUE;
        for (int i = 0; i < completeList.length; i++) {
            //与运算, 如果有部分没有完成则 isComplete 不是 Byte.MAX_VALUE
            isComplete=(byte)(isComplete&completeList[i]);
            System.out.println("check part "+i+" complete?:"+completeList[i]);
        }
        accessConfFile.close();
        if (isComplete==Byte.MAX_VALUE){
            stringRedisTemplate.opsForHash().put(Constants.FILE_UPLOAD_STATUS,param.getMd5(),"true");
            stringRedisTemplate.delete(Constants.FILE_MD5_KEY+param.getMd5());
            confFile.delete();
            return true;
        }else{
            if (!stringRedisTemplate.opsForHash().hasKey(Constants.FILE_UPLOAD_STATUS, param.getMd5())) {
                stringRedisTemplate.opsForHash().put(Constants.FILE_UPLOAD_STATUS, param.getMd5(), "false");
            }
            if (stringRedisTemplate.hasKey(Constants.FILE_MD5_KEY + param.getMd5())) {
                stringRedisTemplate.opsForValue().set(Constants.FILE_MD5_KEY + param.getMd5(), uploadDirPath + "/" + fileName + ".conf");
            }
            return false;
        }

    }


}
