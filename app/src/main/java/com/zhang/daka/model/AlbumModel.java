package com.zhang.daka.model;

import java.io.Serializable;

public class AlbumModel implements Serializable {
    //专辑名称
    public String album_name;
    //专辑在数据库中的id
    public int album_id = -1;
    //专辑的歌曲数目
    public int number_of_songs = 0;
    //专辑封面图片路径
    public String album_art;
    public String album_artist;
    public String album_sort;
}
