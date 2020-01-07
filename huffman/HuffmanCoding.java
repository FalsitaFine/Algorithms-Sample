import java.util.ArrayDeque; 
import java.util.ArrayList; 
import java.util.Collections; 
import java.util.List; 
import java.util.Queue; 
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
//most of them are used in reading/writing txt file


public class HuffmanCoding { 

private static int n;
private static int min;
public static String writeString; 


//for reading txt file
public static String readline(String file) {
       String str = "";
try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            
while ((str = br.readLine()) != null) {
return str;
 }
            
 br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
return str;
}

//for writing the result into output.txt

public static void writeline(String[] writerfile){
try{
           FileWriter fw = new FileWriter("output.txt");
           for(int i = 0; i< writerfile.length;i++){
	    fw.write(writerfile[i]+ " \n");            
 }
 fw.close(); 
 } catch (IOException e) {
 e.printStackTrace();
 }

}

//counting the number(or frequency) of each character.

public static int[] counter(char[] A) {
	//I stored the total number of characters in this "count", for example, the number of "a" in count[0], "b" in count[1], etc
    int [] count=new int[26];
    int index = 0;
    int aindex = (int)'a';
    for (int i=0; i<A.length; i++) {
    index = (int)A[i];
    count[index-aindex]++;
        }
return count;
}



 // a Node, contains the character, its number, its coding, and the right&left children of it(if exist).
    public static class Node { 
        int count; 
        Node left; 
        Node right; 
        String character = ""; 
        String coding = ""; 
        public Node(String character, int count) { 
            this.character = character; 
            this.count = count; 
        } 
 
        public String getCode() { 
            return character + ":" + coding; 
        } 
 
      
//for compare if two nodes are "equal"
        public boolean equals(Object obj) { 
            if (this == obj) 
                return true; 
            if (obj == null) 
                return false; 
            if (getClass() != obj.getClass()) 
                return false; 
            Node comparewith = (Node) obj; 
            if (count == 0) { 
                if (comparewith.count != 0) 
                    return false; 
            } else if (!character.equals(comparewith.character)) 
                return false; 
            return true; 
        } 
 
    } 

//Main here
public static void main(String[] args) { 

//for reading the text file
	String read;
	read = readline(args[0]);
	int aindex = (int)'a';
	HuffmanCoding.writeString = read; 
	char[] input = writeString.toCharArray(); 
	List<Node> nodes = new ArrayList<Node>(); 


//for counting how many times each character appears
	int [] count = new int[26];
	count = counter(input);
	int totalcount = 0;

//chuild a list of nodes with those characters which appears in the text with their frequency.
        for (int i = 0; i < 26; i++) { 
            if(count[i]!=0){
		String temps = "";
		temps+= (char)(i+aindex);
        Node temp = new Node(temps, count[i]);  
        nodes.add(temp); 
		totalcount++;
            } 
        } 

//build a tree with these nodes
        Node root = HuffmanCoding.createTree(nodes); 
        Search(root, nodes); 

//output the coding for each characters 
	String[] output = new String[totalcount];
	int cursor = 0;
   	 for (int i = 0; i < 26; i++) { 
	  if(count[i]!=0){
	  String temps = "";
	  temps+= (char)(i+aindex);
	  Node temp = new Node(temps, count[i]);  
	  output[cursor] = nodes.get(nodes.indexOf(temp)).getCode(); 
	  cursor++;
  }                   
	writeline(output);
 } 

} 

//method of building a huffman tree with a list of nodes
    private static Node createTree(List<Node> nodes) {
	//Create a new tree
        List<Node> tree = new ArrayList<Node>(nodes); 
	//If there are still nodes in this tree
        while (tree.size() > 1) { 
            heapsort(tree); 
		//get the two smallest node in this tree
            Node left = tree.get(tree.size() - 1); 
            Node right = tree.get(tree.size() - 2); 
		//remove them, add the sum of them into the tree
            Node totaloftwo = new Node(null, left.count + right.count); 
            totaloftwo.left = left; 
            totaloftwo.right = right; 
            tree.remove(tree.size() - 1); 
            tree.remove(tree.size() - 1); 
            tree.add(totaloftwo); 
        } 
        //return the root of the tree
        return tree.get(0); 
    } 
 
//heapsort, used to sort the nodes, help to choose the smallest two.
//can be any kind of sorting method here. only need to sort the nodes from small to large.
//I write these sorting methods for Problem 1, so it is easier for me to use it again here.
   public static void heapsort(List<Node> nodes) {
      buildheap(nodes);
      for(int i=nodes.size() - 1; i>0; i--) {
         swap(nodes,0, i);
         n=n-1;
         minheap(nodes, 0);
      }
   }

   public static void buildheap(List<Node> nodes) {
      n = nodes.size() - 1;
      for(int i=n/2; i>=0; i--){
         minheap(nodes,i);
      }
   }

   public static void minheap(List<Node> nodes, int i) { 
     int left = 2*i;
     int right = 2*i+1;

      if(left <= n && nodes.get(left).count > nodes.get(i).count){
         min = i;
      } else if(left <= n) {
         min = left;
      }
      if(right <= n && nodes.get(right).count < nodes.get(min).count) {
      min = right;
      } 
      if(min!=i) {
         swap(nodes,i, min);
         minheap(nodes, min);
      }
   }

// swap two nodes with their index 

    private static void swap(List<Node> nodes, int i, int j) { 
        Node temp; 
        temp = nodes.get(i); 
        nodes.set(i, nodes.get(j)); 
        nodes.set(j, temp); 
    } 
 
// After build a Huffmantree, search each node in it and get their coding results.
// A typical traversal method
    public static void Search(Node root, List<Node> nodes) { 
        Queue<Node> queue = new ArrayDeque<Node>(); 
        if (root != null) { 
            queue.offer(root); 
        } 
        while (!queue.isEmpty()) { 
            Node currentplace = queue.poll(); 
            if (currentplace.left != null) { 
                queue.offer(currentplace.left); 
                currentplace.left.coding = currentplace.coding + "0"; 
            } else { 
                ((Node) nodes.get(nodes.indexOf(currentplace))).coding = currentplace.coding; 
            } 
            if (currentplace.right != null) { 
                queue.offer(currentplace.right); 
                currentplace.right.coding = currentplace.coding + "1"; 
            } 
        } 
 
    } 
}  
