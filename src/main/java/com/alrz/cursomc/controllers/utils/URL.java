package com.alrz.cursomc.controllers.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {

    public static List<Long> decodeLongList(String s) {
//        String[] vet = s.split(",");
//        List<Long> list = new ArrayList<>();
//        for(int i = 0; i < vet.length; i++) {
//            list.add(Long.parseLong(vet[i]));
//        }
        return Arrays.stream(s.split(",")).map(Long::parseLong).collect(Collectors.toList());
    }

    public static String decodeParam(String s) {
        try {
            return URLDecoder.decode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}
