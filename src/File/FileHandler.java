package File;

import java.io.*;
import File.FileHandler;

public class FileHandler {
    private String fileName;
    private FileReader fileR;
    private FileWriter fileW;

    public FileHandler(String fileName){
        this.fileName = fileName;
        try{
            fileR = new FileReader(fileName);
            fileW = new FileWriter(fileName);
        }catch(Exception ex){
            System.out.println(ex);
        }
    }



    public FileReader getFileR(){
        return fileR;
    }

    public FileWriter getFileW(){
        return fileW;
    }

    public void closeFileR(){
        try{fileR.close();}catch(Exception ex){System.out.println(ex);}
    }

    public String getLine(int index){
        return "cadena";
    }


}
