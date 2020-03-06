package com.example.player.helper;

import com.danikula.videocache.file.FileNameGenerator;

public class PlayerFileNameGenerator implements FileNameGenerator {

    @Override
    public String generate(String url) {
        String[] split = url.split("/");
        return split[split.length - 1];
    }
}
