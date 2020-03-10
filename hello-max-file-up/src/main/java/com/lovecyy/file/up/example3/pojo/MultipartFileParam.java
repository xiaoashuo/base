package com.lovecyy.file.up.example3.pojo;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * @author ys
 * @topic
 * @date 2020/3/8 13:01
 */
public class MultipartFileParam implements Serializable {
    /**
     * 用户id
     */
    private String uid;
    /**
     * 文件传输任务ID
     */
    private String taskId;
    /**
     * 当前为第几分片
     */
    private int chunk;
    /**
     * 每个分块大小
     */
    private long size;
    /**
     * 分片总数
     */
    private int chunkTotal;
    /**
     * 当前文件名
     */
    private String name;
    /**
     * 分块文件传输对象
     */
    private MultipartFile file;
    /**
     * 文件MD5
     */
    private String md5;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public int getChunk() {
        return chunk;
    }

    public void setChunk(int chunk) {
        this.chunk = chunk;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getChunkTotal() {
        return chunkTotal;
    }

    public void setChunkTotal(int chunkTotal) {
        this.chunkTotal = chunkTotal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    @Override
    public String toString() {
        return "MultipartFileParam{" +
                "uid='" + uid + '\'' +
                ", taskId='" + taskId + '\'' +
                ", chunk=" + chunk +
                ", size=" + size +
                ", chunkTotal=" + chunkTotal +
                ", name='" + name + '\'' +
                ", file=" + file +
                ", md5='" + md5 + '\'' +
                '}';
    }
}
