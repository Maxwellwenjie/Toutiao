package com.nowcoder.toutiao.service;

import com.nowcoder.toutiao.dao.NewsDAO;
import com.nowcoder.toutiao.model.News;
import com.nowcoder.toutiao.utils.ToutiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class NewsService {
    @Autowired(required = false)
    NewsDAO newsDAO;
    private static final Logger logger = LoggerFactory.getLogger(NewsService.class);

    public List<News> getLatestNews(int userId, int offset, int limit){
        return newsDAO.selectByUserIdAndOffset(userId, offset, limit);
    }

    public void addNews(News news) {
        newsDAO.InsertNews(news);
    }

    public News getNewsById(int newsId) {
        return newsDAO.getNewsById(newsId);
    }

    public String getUserId(int newsId) {
        return newsDAO.getUserId(newsId);
    }

    public int updateCommentCount(int id, int count) {
        return newsDAO.updateCommentCount(id, count);
    }

    public void updateLikeCount(int newId, long likeCount) {
        newsDAO.updateLikeCount(newId,(int)likeCount);
    }

    public String saveImage(MultipartFile file) throws IOException {
        int dotPos = file.getOriginalFilename().lastIndexOf(".");
        if (dotPos < 0){
            return null;
        }
        String fileExt =file.getOriginalFilename().substring(dotPos+1);
        if(!ToutiaoUtil.IsFileAllowed(fileExt.toLowerCase())){
            return null;
        }
        String fileName = UUID.randomUUID().toString().replace("-","")+"."+fileExt;
        //文件夹 D:/upload/ 可能不存在 windows可以创建文件夹，但如果换到Linux怎么办
        Files.copy(file.getInputStream(),new File(ToutiaoUtil.IMAGE_DIR+fileName).toPath(), StandardCopyOption.REPLACE_EXISTING);

        return ToutiaoUtil.TOUTIAN_DOMAIN+"image/?name="+fileName;
    }

}
