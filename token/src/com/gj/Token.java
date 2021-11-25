package com.gj;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;

public class Token {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int i=0;
        String filePath = "";
        String tokenS = "";
        System.out.println("--------输入文件夹路径（0退出）：");
        while(scanner.hasNext()){
            String next = scanner.next();
            if(i==0) {
                filePath = next;
                System.out.println("--接收filePath--"+filePath);
                System.out.println("--------输入新的32位token（0退出）：");
            }
            if(i==1) {
                tokenS = next;
                System.out.println("--接收tokenS--"+tokenS);
            }
            if("0".equalsIgnoreCase(next) || i==1){
                break;
            }
            i++;
        }
        File file = new File(filePath);
        if(file.isDirectory() && !"".equalsIgnoreCase(tokenS)){
            if(tokenS.length()==32) {
                File[] files = file.listFiles();
                for (File f : files) {
                    if (f.getName().matches("(.*).ini")) {
                        Long length = f.length();
                        byte[] fileContext = new byte[length.intValue()];
                        FileInputStream fis = new FileInputStream(f);
                        fis.read(fileContext);
                        fis.close();
                        String s = new String(fileContext);
                        int token = s.indexOf("token");
                        String rep = s.substring(token, token + 40);
                        String newStr = s.replace(rep, "token = " + tokenS.trim());
                        FileOutputStream fos = new FileOutputStream(f);
                        fos.write(newStr.getBytes());
                        fos.flush();
                        fos.close();
                        System.out.println(f.getName());
                    }
                }
            }else{
                System.out.println("-----token长度不对！---");
            }
        }

        scanner.close();
    }
}
