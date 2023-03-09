package com.example.demo.file;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

public class ReadSelectedLine {
    /**
     * 读取文件指定行。
     */
    public static void main(String[] args) throws IOException {
        // 指定读取的行号
        int lineNumber = 2;
        // 读取文件
        File sourceFile = new File("/Users/apple/Documents/project/spring-boot-learn/example/src/main/resources/cfg/application.yml");
        // 读取指定的行
        readAppointedLineNumber(sourceFile, lineNumber);
        // 获取文件的内容的总行数
        System.out.println("总行数：" + getTotalLines(sourceFile));
    }

    // 读取文件指定行。
    static void readAppointedLineNumber(File sourceFile, int startLine) throws IOException {
        FileReader in = new FileReader(sourceFile);
        LineNumberReader reader = new LineNumberReader(in);

        System.out.println("从第几行开始读" + startLine);
        reader.setLineNumber(startLine);
        String nextLine = reader.readLine();
        while (nextLine != null) {
            System.out.println(nextLine);
            nextLine = reader.readLine();
        }

        String s = null;
        int line = 1;
        if (startLine < 0 || startLine > getTotalLines(sourceFile)) {
            System.out.println("不在文件的行数范围之内。");
        } else {

            System.out.println("当前行号为:" + reader.getLineNumber());

            reader.setLineNumber(startLine);
            System.out.println("更改后行号为:" + reader.getLineNumber());
            long i = reader.getLineNumber();
            while (reader.readLine() != null) {
                line++;
                //if (i == line) {
                s = reader.readLine();
                System.out.println(s);
                //	break;
                //}
            }

        }

        reader.close();
        in.close();

    }

    // 文件内容的总行数。
    static int getTotalLines(File file) throws IOException {
        FileReader in = new FileReader(file);
        LineNumberReader reader = new LineNumberReader(in);
        String s = reader.readLine();
        int lines = 0;
        while (s != null) {
            lines++;
            s = reader.readLine();
        }
        reader.close();
        in.close();
        return lines;
    }
}// 最后一行