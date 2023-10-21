package com.education.common.utils;


/**
 * 视频信息工具类
 * @author zjt
 * @create_at 2021年12月24日 0024 13:54
 * @since version 1.0.4
 */
public class VideoUtils {

   // private static final Logger logger = LoggerFactory.getLogger(VideoUtils.class);

    /**
     * 从本地文件获取视频信息
     * @param videoFile
     * @return
     */
   /* public static VideoInfo getVideoInfo(File videoFile) {
        try {
            MultimediaObject multimediaObject = new MultimediaObject(videoFile);
            MultimediaInfo info = multimediaObject.getInfo();
            long duration = (long) Math.ceil((double) info.getDuration() / 1000);
            VideoInfo videoInfo = new VideoInfo();
            videoInfo.setDuration(duration);
            videoInfo.setDurationStr(DateUtils.secondToHourMinute(duration));
            videoInfo.setHeight(info.getVideo().getSize().getHeight());
            videoInfo.setWidth(info.getVideo().getSize().getWidth());
            return videoInfo;
        } catch (Exception e) {
            logger.error("获取视频文件信息异常", e);
        }
        return null;
    }*/

    /**
     * 从流中获取视频信息
     * 先将视频文件下载到本地，读取完毕之后删除本地文件
     * @param inputStream
     * @param saveVideoPath
     * @return
     */
  /*  public static VideoInfo getVideoInfo(InputStream inputStream, String saveVideoPath) {
        File videoFile = new File(saveVideoPath);
        FileUtil.writeFromStream(inputStream, videoFile);
        try {
            return getVideoInfo(videoFile);
        } finally {
            // 获取完信息之后删除视频文件
            FileUtil.del(videoFile);
        }
    }*/

    /**
     * 从url 中获取视频文件信息
     * @param videoUrl
     * @param saveVideoPath
     * @return
     */
   /* public static VideoInfo getVideoInfo(String videoUrl, String saveVideoPath) {
        InputStream inputStream = RequestUtils.getInputStreamFromUrl(videoUrl);
        return getVideoInfo(inputStream, saveVideoPath);
    }*/
}
