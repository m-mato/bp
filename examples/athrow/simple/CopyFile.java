package simple;

import java.io.*;

public class CopyFile {
   private String matiak;

   public static void main(String args[])
   {
      if(args.length != 1) {
         throw new IllegalArgumentException("Wrong number of arguments !");
      }
      
      try{
         copy(args[0]);
         System.out.println("File was succesfully copied !");
      } catch(UncheckedIOException ex) {
         System.out.println("File was not succesfully copied !");
      }
   }

   public static void copy(String path) throws UncheckedIOException {
      try (Reader in= new FileReader(path);
           Writer out= new FileWriter("output.txt")) {
         
         int c;
         while ((c = in.read()) != -1) {
            out.write(c);
         }  
      } catch(IOException ex) {
         throw new UncheckedIOException(ex);
      }
   }
}