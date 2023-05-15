package com.backtoback.media.video.service;

import com.backtoback.media.video.domain.HighLight;
import com.backtoback.media.video.domain.Record;
import com.backtoback.media.video.repository.HighLightRepository;
import com.backtoback.media.video.repository.RecordRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Service
public class MpegServiceImpl implements MpegService{

    private final RecordRepository recordRepository;

    private final HighLightRepository highLightRepository;

    @Value("${mpeg.path}")
    private String ffmpegPath;

    @Async
    @Override
    public CompletableFuture<Void> createHighLight(Long gameSeq, double start, double end) throws InterruptedException, IOException {

        Record record = recordRepository.findById(gameSeq.toString()).orElseThrow();
        String inputPath = record.getRecordPath();
        String ffmpegPath = "/usr/bin/ffmpeg";
        String outputPath = "/highlight/"+ UUID.randomUUID().toString()+".mp4";

        List<String> command = Arrays.asList(
                ffmpegPath,
                "-i", inputPath,
                "-ss",Double.toString(start),
                "-t", Double.toString(end-start),
                "-c:v", "libx264",
                "-s", "320x178",
                "-c:a", "aac",
                "-b:v", "1M",
                "-b:a", "128k",
                "-r", "10",
                "-movflags", "+faststart",
                outputPath
        );

        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true);


        Process process = pb.start();

        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        process.getErrorStream().close();
        process.getInputStream().close();
        process.getOutputStream().close();
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Failed to convert webm to mp4");
        }

        HighLight highLight = new HighLight();
        highLight.setId(UUID.randomUUID().toString());
        highLight.setGameSeq(gameSeq.toString());
        highLight.setHighLightPath(outputPath);
        highLightRepository.save(highLight);

        return CompletableFuture.completedFuture(null);
    }

}
