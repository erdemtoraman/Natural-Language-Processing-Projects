import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Random;

/**
 * Created by Erdem on 3/16/2016.
 */
public class FileClassifier {
    static String sDir="";
    static String parentPath = "";
    public static String[] main(String[] args) throws IOException {

        sDir = args[0];
        parentPath= new File(sDir).getParentFile().getAbsolutePath();
        new File(parentPath+"/testData").mkdir();
        new File(parentPath+"/trainingData").mkdir();
    fillTheData(sDir);

      String[] retArgs = new String [2];
        retArgs[0] = parentPath+"/testData";
        retArgs[1] =parentPath+"/trainingData";
        return retArgs;
    }

    private static void decideOnTheFile(String absolutePath, int leftTestRight) throws IOException {

     //   System.out.println("Deciding on :"+ absolutePath);
        File source = new File(absolutePath);
        File targetFile;

        String authorName = source.getParentFile().getName();
        if(leftTestRight <= 0 ){
            targetFile = new File(parentPath +"/trainingData/"+authorName+"/"+source.getName());
       //     System.out.println("Decided to copy it into  :"+ targetFile.getAbsolutePath());
        }else{

            Random random = new Random();
            int answer = random.nextInt(100) + 1;
            if(answer > 50){
                targetFile = new File(parentPath +"/testData/"+authorName+"/"+source.getName());
          //      System.out.println("Decided to copy it into  :"+ targetFile.getAbsolutePath());
            }else{
                targetFile = new File(parentPath +"/trainingData/"+authorName+"/"+source.getName());
            //    System.out.println("Decided to copy it into  :"+ targetFile.getAbsolutePath());
            }
        }

        Files.copy(source.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
     /*   System.out.println("DECISION PROCESS IS OVER");
        System.out.println("=================================================");*/
    }

    private static void fillTheData(String tempDir) throws IOException {
        File dir =  new File(tempDir);
        File[] faFiles =dir.listFiles();
        for(File file: faFiles){
            if(file.isDirectory()){
                new File(parentPath+"/trainingData/"+file.getName()).mkdir();
                new File(parentPath+"/testData/"+file.getName()).mkdir();
                fillTheData(file.getAbsolutePath());
            }
            else{
                int totalNumOfFiles = faFiles.length;
                int alreadyTest = new File(parentPath+"/testData/"+file.getParentFile().getName()).listFiles().length;
                decideOnTheFile(file.getAbsolutePath(),(totalNumOfFiles*4/10) - alreadyTest);
            }
        }


    }


}
