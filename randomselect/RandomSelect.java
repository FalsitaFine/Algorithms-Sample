import java.util.*;
import java.util.Random;
import java.util.ArrayDeque; 
import java.util.ArrayList; 
import java.util.Collections; 
import java.util.List; 
import java.util.Queue; 
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
//most of them are used in reading/writing txt file



public class RandomSelect {

//for reading from text file

public static String[] readline(String file) {
String strtemp[] = new String[2];
try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
	    int readcursor = 0;
	    while (readcursor<2) {
	    strtemp[readcursor] = br.readLine();
//System.out.println(strtemp[readcursor]);
	    readcursor++;
 }
br.close();
return strtemp;            
 
 } catch (IOException e) {
    e.printStackTrace();
 }
return strtemp;
}

//for writing the result into output.txt

public static void writeline(String writerfile){
   try {
            FileWriter fw = new FileWriter("output.txt");
	    fw.write(writerfile+ " \n");            
            fw.close(); 
        } catch (IOException e) {
           e.printStackTrace();
        }

}


//for swap two members in a array with their index
public static void swap (int A[], int x, int y) {
      int temp = A[x];
      A[x] = A[y];
      A[y] = temp;
}

public static int Partition( int [] numbers, int end){
	//translate from class pdf, made a liitle bit change
int start = -1;
	for(int j=0 ; j <= end-2;j++)
		{
			if(numbers[j] <= numbers[end - 1])
				{
					start++;
					swap(numbers, start, j);
				}
			}
		
		swap(numbers, start+1, end - 1);
		start++;
		return start;
}



//Randomselect method
public static void RandomSelect( int [] numbers, int index)
{

int end = numbers.length;

//get a random pivot
Random rand = new Random();
int randompivot = rand.nextInt(end);
swap (numbers, randompivot, end-1);
int start = Partition(numbers,end);


//if current value is larger than what we want, recursively search the next part of the array
		if(start + 1 > index)
          {
				int [] recursivenumbers= new int[start];
				System.arraycopy(numbers,0,recursivenumbers,0,start);
				RandomSelect(recursivenumbers,index);
          }



// if current value is smaller than what we want, recursively search the next part of the array
		if(start + 1 < index)
		{
			int [] recursivenumbers= new int[end - start - 1];
			System.arraycopy(numbers ,start + 1, recursivenumbers,0, end-start-1);
			RandomSelect(recursivenumbers,index-start-1);
		}


// if current value is what we want, output it
		if(start + 1 == index)
		{
		int outputtemp;
		outputtemp = numbers[start];
		String outputstr = "" + outputtemp;
		writeline(outputstr);	
		}

}

// main method here, just read the numbers, and call randomselect recursively
public static void main(String args[]) {

      String s[] = readline(args[0]);
      int index = Integer.parseInt(s[0]);

      String[] numbersstr = s[1].split(" ");
      int[] numbers = new int[numbersstr.length];

      for(int i = 0; i< numbersstr.length;i++){
	numbers[i] = Integer.parseInt(numbersstr[i]);
	      //System.out.println(numbers[i]);
	}

RandomSelect(numbers,index);

   }
}

