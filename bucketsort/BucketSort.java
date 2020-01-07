import java.util.*;
import java.util.Random;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.io.File;  
import java.io.InputStreamReader;  
import java.io.BufferedReader;  
import java.io.BufferedWriter;  
import java.io.FileInputStream;  
import java.io.FileWriter;  
import java.io.FileOutputStream;  
import java.io.FileReader;  
import java.io.RandomAccessFile;  
import java.io.IOException;
import java.lang.*;

//most of them are used in reading/writing txt file


public class BucketSort{

//for reading txt file
public static String readline(String file) {
 String readresult = "";
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String str;
 int cursor = 0;

 while ((str = br.readLine()) != null) {
  //System.out.println(str);
  readresult = str;
  cursor++;
 }
            
  br.close();
return readresult;
        } catch (IOException e) {
            e.printStackTrace();
        }
return readresult;
}

//for writing the result into output.txt

public static void writeline(String[] writerfile){
   try {
           FileWriter fw = new FileWriter("output.txt");
           for(int i = 0; i< writerfile.length;i++){
	    if(writerfile[i] != null)
	    fw.write(writerfile[i]+ "  ");            
 }
            fw.close(); 
        } catch (IOException e) {
           e.printStackTrace();
        }

}

//For coding the names, here I change every name(string) into a double number in order to sort them.
public static double[] coding(String[] a) {

	double []b = new double[a.length];

	double [] nametoint = new double[a.length];
	int aindex = (int)'A';
	char temp;
	double shifter = 1;

	for (int i = 0; i<a.length; i++){
	 shifter = 1;
	 for (int j = 0; j<a[i].length();j++){

	  temp = a[i].charAt(j);
	  //System.out.println(temp);
	  nametoint[i] += temp/shifter;
	  shifter = shifter * 200;
	 }
	  //System.out.println(nametoint[i]);
	 }
 return nametoint;
}

//A class, I build it for some global variables
public static class unsorted{
    private unsorted(){}
    static double [][] bucket = new double[100][1000];
    static String [][] buckets = new String[100][1000];
    static int []bucketunit = new int[128];
    static double bucketunitsize = 3;

}


//The bucketsort itself
public static String[] bucketsort(double[] a, String[] b, int bucketsize) {
	 String [] result = new String[b.length];
	 result[1] = "1";
	 String[] sortednames = b;
	 double[] sortednametoint = a;

	 int getbucketsize = bucketsize;
	 int bucketnumber = 0;
	 int jcursor = 0;
	 int icursor = -getbucketsize;

//If this bucketsort is called by recursive(this bucket has more than 10 members
//It looks long, but actually very simple: split one bucket into two buckets
//begin here
	 if (getbucketsize < 0 ){

//Move buckets in order to make the nect bucket empty 
	  if(unsorted.bucket[icursor+1][0] != 0){
	   while(unsorted.bucket[icursor][0] > 0){
	    icursor++;
	  }

	  while(icursor > -getbucketsize + 1){
   
	    unsorted.bucket[icursor] = unsorted.bucket[icursor-1];
	    unsorted.buckets[icursor] = unsorted.buckets[icursor-1];
	    unsorted.bucketunit[icursor] = unsorted.bucketunit[icursor-1];

	    icursor--;
          }
}

	  while(unsorted.bucket[-getbucketsize][jcursor]>0){
	    jcursor++;
	  }

	 jcursor--;

	 if (jcursor == 0){
	  return result;
	 }

//Randomly choose a member in the old bucket, put all mumbers larger than it into the next bucket
	 double compare;
	 Random rand = new Random();
	 int randompivot = rand.nextInt(jcursor+1);
	 //System.out.println(randompivot);
	 int copycursor;
	 copycursor = 0;
	 int tempcursor;
	 tempcursor = 0;
	 compare = unsorted.bucket[-getbucketsize][randompivot];
	 double [][]tempbucket = new double[100][1000] ;
	 String [][]tempbuckets = new String[100][1000] ;

	 int emptycursor = 0;

	 while(unsorted.bucket[-getbucketsize+1][emptycursor] > 0){
	  unsorted.bucket[-getbucketsize+1][emptycursor] = -1;
	  unsorted.buckets[-getbucketsize+1][emptycursor] = "";
	  emptycursor++;
 	 }


	 while(jcursor>=0 && unsorted.bucket[-getbucketsize][jcursor] > 0){
	  if (unsorted.bucket[-getbucketsize][jcursor] >= compare){
	  //System.out.println("putting into new bucket");
	   unsorted.bucket[-getbucketsize+1][copycursor] = unsorted.bucket[-getbucketsize][jcursor];
	   unsorted.buckets[-getbucketsize+1][copycursor] = unsorted.buckets[-getbucketsize][jcursor];
	   copycursor++;
	   unsorted.bucket[-getbucketsize][jcursor] = -1;
	   unsorted.buckets[-getbucketsize][jcursor] = "";
	   unsorted.bucketunit[-getbucketsize]--;
	   } else{
	  //System.out.println("putting into old bucket");
	   tempbucket[-getbucketsize][tempcursor] = unsorted.bucket[-getbucketsize][jcursor];
	   tempbuckets[-getbucketsize][tempcursor] = unsorted.buckets[-getbucketsize][jcursor];
	   tempcursor++;

           }
	jcursor--;
         }

	 unsorted.bucket[-getbucketsize] = tempbucket[-getbucketsize];
	 unsorted.buckets[-getbucketsize] = tempbuckets[-getbucketsize];
	 unsorted.bucketunit[-getbucketsize+1]=copycursor;


	return result;   
   
 }
//finish here



//The normal bucket sort(not called by recursive)

//put different things into different buckets
	 for (int i=0; i< b.length; i++) {
	  bucketnumber = (int)sortednametoint[i]/getbucketsize;
	  unsorted.bucket[bucketnumber][unsorted.bucketunit[bucketnumber]] = sortednametoint[i];
	  unsorted.buckets[bucketnumber][unsorted.bucketunit[bucketnumber]] = sortednames[i];
	  unsorted.bucketunit[bucketnumber]++;
	 }

//if one bucket have more than 10 members in it, then split it into two bucket by calling the bucketsort function recursively.
	 for (int i=0; i< b.length; i++) {
	  bucketnumber = (int)sortednametoint[i]/getbucketsize;
	  if (unsorted.bucketunit[bucketnumber] > 10){
	    bucketsort(unsorted.bucket[bucketnumber],unsorted.buckets[bucketnumber],-bucketnumber) ;
	  }

        }


//A insertion sort to sort each buckets.
 for (int i = 0; i<100; i++){ 
    for (int n = 0; n < unsorted.bucketunit[i]; n++) {
        for (int m = n; m > 0; m--) {
            if (unsorted.bucket[i][m] < unsorted.bucket[i][m - 1]) {
        double tempp = unsorted.bucket[i][m];
        String temps =unsorted. buckets[i][m];
        unsorted.bucket[i][m] = unsorted.bucket[i][m - 1];
        unsorted.buckets[i][m] = unsorted.buckets[i][m - 1];
        unsorted.bucket[i][m - 1] = tempp;
        unsorted.buckets[i][m - 1] = temps;
            } else {
                break;
            }
        }
    }
}


      int outPos=0;

//combine these little buckets together
    for (int i=0; i<128; i++) {

    if (unsorted.bucketunit[i] != 0){
         for (int j=0; j<unsorted.bucketunit[i]; j++) {
         if(unsorted.buckets[i][j]!=null){
         result[outPos++] = unsorted.buckets[i][j];

         }
         }
    }
      }
return result;
}







public static void main(String[] args) {
	 String s = readline(args[0]); 
	 String[] names = s.split(" ") ;  

	 double [] result = new double[names.length];
	 String [] results = new String[names.length];
         result = coding(names);
         results = bucketsort(result,names,5);

	 writeline(results);

  }
}

