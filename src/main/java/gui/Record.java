package gui;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Record {       //线程共享资源
    private static final String DIRPATH="./record";
    private String filePath;
    private BufferedWriter out;

    private List<String> recordList=new ArrayList<>();

    private boolean isClose=false;

    public Record(){
//        DIRPATH=this.getClass().getClassLoader().getResource("record").getPath();       //CLASSPATH
        File dir=new File(DIRPATH);
        File recordPath=new File("./record");
        if(!recordPath.exists()||!recordPath.isDirectory()) {
            recordPath.mkdirs();
        }
        filePath = DIRPATH+ "/record"+ dir.list().length +".txt";
//        System.out.println(filePath);

        recordList.add("Calabash Brothers record.");

        /*
        File file=new File(filePath);
        try {
            file.createNewFile();
            out=new BufferedWriter(new FileWriter(file));
        }catch (IOException e){
            System.out.println("There is a existed file.");
            e.printStackTrace();
        }

        try{
            out.write("Calabash Brothers record.\r\n");
            out.flush();
        }catch (IOException e){
            System.out.println("Error to write file.");
            e.printStackTrace();
        }
        */
    }

    public synchronized void recordFormation(String goodFormation,String evilFormation){
        recordList.add(goodFormation);
        recordList.add(evilFormation);
        /*
        try{
            out.write(goodFormation+"\r\n");
            out.write(evilFormation+"\r\n");
            out.flush();
        }catch (IOException e){
            System.out.println("Error to write file.");
            e.printStackTrace();
        }
        */
    }

    public synchronized void recordMove(String name,int x,int y){
        recordList.add("move "+name+" "+x+" "+y);
        /*
        try{
            out.write("move "+name+" "+x+" "+y+"\r\n");
            out.flush();
        }catch (IOException e){
            System.out.println("Error to write file.");
            e.printStackTrace();
        }
        */
    }

    public synchronized void recordAttack(String attacker,String target){
        recordList.add("attack "+attacker+" "+target);
        /*
        try{
            out.write("attack "+attacker+" "+target+"\r\n");
            out.flush();
        }catch (IOException e){
            System.out.println("Error to write file.");
            e.printStackTrace();
        }
        */
    }

    public synchronized  void recordDead(String name){
        recordList.add("dead "+name);
    }

    public synchronized void recordOver(){
        if(!isClose) {
            File file=new File(filePath);
            try {
                file.createNewFile();
//                out=new BufferedWriter(new FileWriter(file));
                out=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));

                for(String item:recordList){
                    out.write(item+"\r\n");
                }
                out.write("Over");
                out.flush();

                out.close();
                isClose = true;
            }catch (IOException e){
                System.out.println("Fail to write the record.");
                e.printStackTrace();
            }
        }
        /*
        if(!isClose) {
            try {
                out.write("over");
                out.flush();
            } catch (IOException e) {
                System.out.println("Error to write file.");
                e.printStackTrace();
            }

            try {
                out.close();
                isClose=true;
            } catch (IOException e) {
                System.out.println("Fail to close writing record.");
                e.printStackTrace();
            }
        }
        */
    }
}
