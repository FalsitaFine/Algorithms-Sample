cuixx327@umn.edu
Tianming cui
5326541
The code has been tested with sample input on CSEL-KH1260-09



usage: bash run.sh inputfilename(or ./run.sh inputfilename)
put the inputfile in the same direction with the code(and .sh),
then type bash run.sh,
the program will give out an output.txt
Assumption: No edge or route longer than 2000000, no extra empty lines in the inputfile.

Here I use Floyd method to solve this problem(as the problem says it is an undirected graph, Floyd will be an easy solvement). When we use Floyd, to detect negative cycle will be easy: because the graph is undirected, each negative edge can be a negative cycle, so when detected a negative edge, we can immediately end the program and give out "Negative cycle".

Thank you for reading!
Have a good day!
