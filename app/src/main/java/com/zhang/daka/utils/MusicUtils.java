package com.zhang.daka.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Albums;
import android.provider.MediaStore.Audio.Media;

import com.zhang.daka.model.AlbumModel;
import com.zhang.daka.model.MusicModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MusicUtils {
    public static final int FILTER_SIZE = 1 * 1024 * 1024;// 1MB
    public static final int FILTER_DURATION = 1 * 60 * 1000;// 1分钟
    private static String[] proj_music = new String[]{
            Media._ID, Media.TITLE,
            Media.DATA, Media.ALBUM_ID,
            Media.ALBUM, Media.ARTIST,
            Media.ARTIST_ID, Media.DURATION, Media.SIZE};
    public static ArrayList<MusicModel> queryMusic(Context context) {
        ArrayList<MusicModel> list=new ArrayList<>();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        ContentResolver cr = context.getContentResolver();

        StringBuilder select = new StringBuilder(" 1=1 and title != ''");
        // 查询语句：检索出.mp3为后缀名，时长大于1分钟，文件大小大于1MB的媒体文件
        select.append(" and " + Media.SIZE + " > " + FILTER_SIZE);
        select.append(" and " + Media.DURATION + " > " + FILTER_DURATION);

        final String songSortOrder = PreferencesUtility.getInstance(context).getSongSortOrder();

        list = getMusicListCursor(cr.query(uri, proj_music,
                select.toString(), null,
                songSortOrder));

        return list;

    }

    public static ArrayList<MusicModel> getMusicListCursor(Cursor cursor) {
        if (cursor == null) {
            return null;
        }

        ArrayList<MusicModel> musicList = new ArrayList<>();
        while (cursor.moveToNext()) {
            MusicModel music = new MusicModel();
            music.songId = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.Audio.Media._ID));
            music.albumId = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
            music.albumName = cursor.getString(cursor
                    .getColumnIndex(Albums.ALBUM));
            music.albumData = getAlbumArtUri(music.albumId) + "";
            music.duration = cursor.getInt(cursor
                    .getColumnIndex(Media.DURATION));
            music.musicName = cursor.getString(cursor
                    .getColumnIndex(Media.TITLE));
            music.artist = cursor.getString(cursor
                    .getColumnIndex(Media.ARTIST));
            music.artistId = cursor.getLong(cursor.getColumnIndex(Media.ARTIST_ID));
            String filePath = cursor.getString(cursor
                    .getColumnIndex(Media.DATA));
            music.data = filePath;
            music.folder = filePath.substring(0, filePath.lastIndexOf(File.separator));
            music.size = cursor.getInt(cursor
                    .getColumnIndex(Media.SIZE));
            music.islocal = true;
            musicList.add(music);
        }
        cursor.close();
        return musicList;
    }

    public static List<AlbumModel> getAlbumList(Cursor cursor) {
        List<AlbumModel> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            AlbumModel info = new AlbumModel();
            info.album_name = cursor.getString(cursor
                    .getColumnIndex(Albums.ALBUM));
            info.album_id = cursor.getInt(cursor.getColumnIndex(Albums._ID));
            info.number_of_songs = cursor.getInt(cursor
                    .getColumnIndex(Albums.NUMBER_OF_SONGS));
            info.album_art = getAlbumArtUri(info.album_id) + "";
            info.album_artist = cursor.getString(cursor.getColumnIndex(Albums.ARTIST));
            list.add(info);
        }
        cursor.close();
        return list;
    }

    public static Uri getAlbumArtUri(long albumId) {
        return ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), albumId);
    }
}
