package com.backtoback.media.video.service;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface MpegService {
    CompletableFuture<Void> createHighLight(Long gameSeq, double start, double end) throws InterruptedException, IOException;
}
