package com.app.file;

import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class VideoUtil {
    /**
     * 获取时长
     */
    public static long getDuration(File file) throws Exception {
        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(file);
        ff.start();
        long duration = ff.getLengthInTime() / (1000 * 1000);
        ff.stop();
        return duration;
    }

    /**
     * 转MP4
     */
    public static String convertToMp4(File file) throws Exception {
        FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(file);
        Frame captured_frame = null;
        frameGrabber.start();
        String fileName = file.getAbsolutePath() + "ToMp4.mp4";
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(fileName, frameGrabber.getImageWidth(),
                frameGrabber.getImageHeight(), frameGrabber.getAudioChannels());
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264); // avcodec.AV_CODEC_ID_H264 //AV_CODEC_ID_MPEG4
        recorder.setFormat("mp4");
        recorder.setFrameRate(frameGrabber.getFrameRate());
        recorder.setSampleRate(frameGrabber.getSampleRate());
        recorder.setAudioChannels(frameGrabber.getAudioChannels());
        recorder.setFrameRate(frameGrabber.getFrameRate());
        recorder.start();
        while ((captured_frame = frameGrabber.grabFrame()) != null) {
            recorder.setTimestamp(frameGrabber.getTimestamp());
            recorder.record(captured_frame);
        }
        recorder.stop();
        recorder.release();
        frameGrabber.stop();
        return fileName;
    }

    /**
     * @param file   源视频文件
     * @param imgDir 图片存放路径文件夹
     * @param step   取帧间隔，每隔多少帧截取一张
     * @throws Exception
     * @title 获取视频帧转存图片
     */
    public static void getFramePics(File file, String imgDir, int step) throws Exception {

        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(file); // 获取视频文件
        ff.start();
        int len = ff.getLengthInVideoFrames(); // 视频帧长度
        int index = 0; // 帧数
        int currIndex = 0;// 图片计数
        Frame frame = null;
        while (index <= len) {
            frame = ff.grabImage(); // 获取该帧图片流
            if (frame != null && frame.image != null) {
                getImage(frame, imgDir, currIndex, step); // 生成帧图片
                currIndex++;
            }
            index++;
        }
        ff.stop();
    }

    private static void getImage(Frame frame, String imgDir, int currIndex, int step) {
        if (currIndex % step != 0) {
            return;
        }
        File dir = new File(imgDir);
        if (!dir.isDirectory() || !dir.exists()) {
            dir.mkdirs();
        }
        File targetFile = new File(imgDir + currIndex + ".jpg");
        String imgSuffix = "jpg";

        Java2DFrameConverter converter = new Java2DFrameConverter();
        BufferedImage srcBi = converter.getBufferedImage(frame);
        int owidth = srcBi.getWidth();
        int oheight = srcBi.getHeight();
        // 对截取的帧进行等比例缩放
        int width = 800;
        int height = (int) (((double) width / owidth) * oheight);
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        bi.getGraphics().drawImage(srcBi.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
        try {
            ImageIO.write(bi, imgSuffix, targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
