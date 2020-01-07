cuixx327@umn.edu
Tianming cui
5326541
The code has been tested with sample input on CSEL-KH1260-09



usage: bash run.sh inputfilename(or ./run.sh inputfilename)
put the inputfile in the same direction with the code(and .sh),
then type bash run.sh,
the program will give out an output.txt
Assumption: source and destination should be correct values(eg. if there is a 4*4 matrix, source and destination cannot be larger or equals to 4)

Here the program get the shortest path from the source to every other vertex, but only output the result for the given destination.
Just like the implementation in the textbook, the Dijkstra method here is simply chooses the "closest" vertex in V - S to add to set S, where S is the result route.

Thank you for reading!
Have a good day!
