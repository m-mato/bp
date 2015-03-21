import java.io.*;

public class CopyFile {
   public static void main(String args[])
   {
      if(args.length != 1) {
         throw new IllegalArgumentException("Wrong number of arguments !");
      }
      
      try{
         copy(args[0]);
         System.out.println("OK !");
      } catch(UncheckedIOException ex) {
         System.err.println("IO Error: ");
         ex.printStackTrace();
         System.exit(1);
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