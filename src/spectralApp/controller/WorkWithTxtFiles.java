package spectralApp.controller;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Tim on 19.06.2017.
 */


public class WorkWithTxtFiles implements WorkWithFiles{

    final static String DEFAULT_FILE_DIR = "files/";
    static List<String> content = new ArrayList<>();
    static Path filePath = null;
    String errorMsg = "";
    String serviceMsg = "";
    BufferedReader br;
    BufferedWriter bw;
    FileWriter fw;
    FileReader fr;


    private WorkWithTxtFiles(){};

    public static class Singleton{
        public static final WorkWithTxtFiles holder = new WorkWithTxtFiles();
    }

    public static WorkWithTxtFiles getInstance(){return Singleton.holder;}

    @Override
    public int loadFile(String filename) {
        Path path = Paths.get(filename);
        List<String> fileContent = new ArrayList<String>();
        content.clear();
        try {
            br = null;
            br = new BufferedReader(new FileReader(filename));
            String line;
            while((line = br.readLine()) != null){
                fileContent.add(line);
            }
            content.addAll(fileContent);
            filePath = path;
            serviceMsg = "Successfully read from the file";
            br.close();
            return 1;

        } catch (IOException e) {
            e.printStackTrace();
            errorMsg = "Error while loading file";
            try {
                br.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (NullPointerException e2){

            }
            return 0;
        }

    }

    @Override
    public int loadFile(String filename, String dir) {
        if(dir.length()==0){
            filename = DEFAULT_FILE_DIR + filename;
            loadFile(filename);
        }else {
            filename = dir + filename;
            loadFile(filename);
        }
            return 1;
    }

    @Override
    public int createFile(String filename) {

        Path path = Paths.get(filename);
        try {
            if(!Files.exists(path)) {
                Files.createFile(path);
                loadFile(filename);
                return 1;
            }else{
                filename = createDefaultNewFile();
                loadFile(filename);
                return 2;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }

    }

    public int createFile(String filename, String dir) {
        String fullPath = DEFAULT_FILE_DIR + filename;
        if(dir == null || dir.length() == 0) {
            createFile(fullPath);
            filePath = Paths.get(fullPath);
            return 1;
        }else{
            filename = dir+filename;
            createFile(filename);
            filePath = Paths.get(filename);
            return 2;
        }
    }

    @Override
    public int saveFile(String filename, JTextArea textArea) {
        File file = new File(filename);
        String fileDir = filename.substring(0, filename.lastIndexOf("\\")+1);
        String fileName = filename.substring(filename.lastIndexOf("\\")+1, filename.length());

        if(!file.exists()) {
            try {
                Files.delete(filePath);
                filePath = Paths.get(fileDir+fileName);
                createFile(fileName, fileDir);
                fw = new FileWriter(file);
                bw = new BufferedWriter(fw);

                List<String> contentOfTA = Arrays.asList(textArea.getText().split("\n"));
                for(String str: contentOfTA) {
                    bw.append(str+"\n");
                }

                return 1;
            }catch (IOException e) {
                e.printStackTrace();
                errorMsg = "Error while writing file";
                return -1;
            }finally{
                try {
                    if (bw != null) {
                        bw.close();
                    }
                    if (fw != null) {
                        fw.close();
                    }
                }catch (IOException e) {
                        errorMsg = "Error has occured while reader closing";
                        e.printStackTrace();
                }
            }
        }else{
            return 0;
        }
        //return 0;
    }

    @Override
    public int deleteFile(String str) {

        return 0;
    }

    @Override
    public int unloadFile(String str) {
        return 0;
    }

    public void deleteCurrentFile(){
        try {
            Files.delete(filePath);
            serviceMsg = "File has been successfully deleted.";
            filePath = null;
        } catch (IOException e) {
            errorMsg = "Error while deliting file";
            e.printStackTrace();
        }
    }


    public List<String> getContent(){
        return content;
    }

    public List<String> getTheFileList(){
        File allfiles = new File(DEFAULT_FILE_DIR);
        ArrayList<String> list = new ArrayList<String>();
        list.addAll(Arrays.asList(allfiles.list()));
        return list;
    }

    public String createDefaultNewFile() {

        WorkWithTxtFiles wwf = WorkWithTxtFiles.getInstance();
        List<String> fileList = wwf.getTheFileList();
        String filename = "";

        for(int i = 0; i<1000; i++) {
            boolean flag =  false;
            for (String file : fileList) {
                if (file.contains("untitled(" + i + ")")) {
                     flag = true;
                }

            }
            if(!flag) {
                filename = "untitled(" + i + ").txt";
                wwf.createFile(filename, null);
                break;
            }
        }

        System.out.println(content);
        return filename;
    }



    public static void main(String[] args) {
        WorkWithTxtFiles helper = new WorkWithTxtFiles();
//        WorkWithTxtFiles helper = WorkWithTxtFiles.getInstance();
//        helper.loadFile("file.txt","");
//        System.out.println(helper.content);
//        helper.createFile("File2.txt");
//        String str[] = new String[]{"23213", "31wqewe"};
//        //helper.content.addAll(Arrays.asList(str));
//        helper.saveFile("File1.txt", Arrays.asList(str));
//        System.out.println(helper.content);
//        helper.loadFile("File2.txt", "");
//        System.out.println(helper.content);
        String pathToFile = filePath.toString();
        String fileDir = pathToFile.substring(0, pathToFile.lastIndexOf("\\"));
        String fileName = pathToFile.substring(pathToFile.lastIndexOf("\\"), pathToFile.length());
        helper.loadFile(fileName, fileDir);
        //helper.loadFile("MonoPhos-4Ph-Ment-S.log", "F:\\Work\\spectra\\Almaz\\MonoPhos\\");
        for(String str1: content){
            System.out.println(str1);
        }
    }

}
