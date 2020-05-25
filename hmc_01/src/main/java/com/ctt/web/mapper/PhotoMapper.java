package com.ctt.web.mapper;

import com.ctt.web.bean.Photo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description
 * @auther HHF
 * @create 2020-05-05 上午 11:32
 */
public interface PhotoMapper {

    public List<Photo> getPhotoList(@Param("pid") String pid, @Param("searchName") String searchName);

    public void addPhoto(Photo photo);

    public void batchAddPhoto(List<Photo> photo);

    public void updatePhoto(Photo photo);

    public void deletePhoto(@Param("id") String id);

    public Photo getPhotoById(@Param("id") String id);
}
