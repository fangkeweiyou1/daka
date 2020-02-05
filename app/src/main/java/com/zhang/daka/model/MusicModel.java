package com.zhang.daka.model;

import java.io.Serializable;

public class MusicModel implements Serializable {
    /**
     * 数据库中的_id
     */
    public long songId = -1;
    public int albumId = -1;
    public String albumName;
    public String albumData;
    //时长
    public int duration;
    //音乐名称
    public String musicName;
    //音乐作者
    public String artist;
    public long artistId;
    //路径
    public String data;
    public String folder;
    public String lrc;
    public boolean islocal;
    public String sort;


    //文件大小
    public int size;
    /**
     * 0表示没有收藏 1表示收藏
     */
    public int favorite = 0;

    @Override
    public String toString() {
        return "MusicModel{" +
                "songId=" + songId +
                ", albumId=" + albumId +
                ", albumName='" + albumName + '\'' +
                ", albumData='" + albumData + '\'' +
                ", duration=" + duration +
                ", musicName='" + musicName + '\'' +
                ", artist='" + artist + '\'' +
                ", artistId=" + artistId +
                ", data='" + data + '\'' +
                ", folder='" + folder + '\'' +
                ", lrc='" + lrc + '\'' +
                ", islocal=" + islocal +
                ", sort='" + sort + '\'' +
                ", size=" + size +
                ", favorite=" + favorite +
                '}';
    }
}
