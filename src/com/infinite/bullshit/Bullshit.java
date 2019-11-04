package com.infinite.bullshit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Email: 690797861@qq.com
 * Author: Infinite
 * Date: 2019-11-04 - 14:23
 * Description: Class description
 */
public class Bullshit {
    private static final int REPEAT_COUNT = 1;
    private int indexBullshit = 0;
    private int indexQuotes = 0;

    public static void main(String[] args) {
        Bullshit bullshit = new Bullshit();
        bullshit.sayBullshit();
    }


    private String readData() {
        StringBuilder sb = new StringBuilder(1000);
        try {
            FileReader fileReader = new FileReader(new File(Bullshit.class.getResource("/").getPath(),
                    "data.json"));
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String tmp;
            while ((tmp = bufferedReader.readLine()) != null) {
                sb.append(tmp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private void sayBullshit() {
        String data = readData();
        JSONObject jsonObject = JSON.parseObject(data);
        List<String> famousList = getShuffleList(jsonObject.getJSONArray("famous").toJavaList(String.class));
        List<String> boshList = getShuffleList(jsonObject.getJSONArray("bosh").toJavaList(String.class));
        List<String> afterList = jsonObject.getJSONArray("after").toJavaList(String.class);
        List<String> beforeList = jsonObject.getJSONArray("before").toJavaList(String.class);

        String topic = getArticleTopic();
        StringBuilder sb = new StringBuilder(6000);
        for (int i = 0, length = topic.length(); i < length; i++) {
            while (sb.length() < 6000) {
                int random = getRandom(0, 100);
                if (random < 5) {
                    sb.append(getAnotherParagraph());
                } else if (random < 20) {
                    sb.append(getNextQuotes(famousList, afterList, beforeList));
                } else {
                    sb.append(getNextBullshit(boshList));
                }
            }
        }
        String article = sb.toString().replaceAll("x", topic);
        System.out.println("你的狗屁文章是：\n");
        System.out.println(article);
    }

    private static List<String> getShuffleList(List<String> list) {
        List<String> result = new ArrayList<>(100);
        if (REPEAT_COUNT > 1) {
            for (int i = 0; i < REPEAT_COUNT; i++) {
                result.addAll(new ArrayList<>(list));
            }
        } else {
            result.addAll(list);
        }
        Collections.shuffle(result);
        return result;
    }

    private String getAnotherParagraph() {
        return ".\r\n    ";
    }

    private String getNextQuotes(List<String> list, List<String> a, List<String> b) {
        String s = list.get(indexQuotes % (list.size() - 1));
        s = s.replace("a", getRandomText(a));
        s = s.replace("b", getRandomText(b));
        indexQuotes++;
        return s;
    }

    private String getRandomText(List<String> list) {
        int r = getRandom(0, list.size() - 1);
        return list.get(r % (list.size() - 1));
    }

    private String getNextBullshit(List<String> list) {
        String bull = list.get(indexBullshit % (list.size() - 1));
        indexBullshit++;
        return bull;
    }

    private String getArticleTopic() {
        String topic = null;
        try {
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
            System.out.println("请输入文章主题：");
            topic = br.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (topic == null || topic.length() == 0) {
            topic = "自由发挥";
        }
        System.out.println("主题为：" + topic);
        return topic;
    }


    private static int getRandom(int min, int max) {
        return (int) Math.round(Math.random() * (max - min) + min);
    }

}
