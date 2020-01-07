cuixx327@umn.edu
Tianming cui
5326541
The code has been tested with sample input on CSEL-KH1260-09



usage: bash run.sh inputfilename(or ./run.sh inputfilename)
put the inputfile in the same direction with the code(and .sh),
then type bash run.sh,
the program will give out an output.txt
Assumption: No circular prerequisites so DFS can terminate correctly.

First, I tried to using DFS method to searching the graph, get the finish time of each vertex and then sort them in order to solve the problem(like in the textbook),
But then I found it will be an easier way to push the vertex into a stack immediately after it was finished. And to get the final result, just pop them from the stack in a certain order. The program is working like that.
The output may not be same with the given answer, but it should be a correct order.
It is strange that if I use a array instead of stack, the output order will be wrong.


Thank you for reading!
Have a good day!
