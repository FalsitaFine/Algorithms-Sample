cuixx327@umn.edu
Tianming cui
5326541
The code has been tested with sample input on CSEL-KH1260-09



usage: bash run.sh inputfilename(or ./run.sh inputfilename)
put the inputfile in the same direction with the code(and .sh),
then type bash run.sh,
the program will give out an output.txt
Assumption: No edge or route longer than 2000000, no extra empty lines in the inputfile.

It is a Johnson's algorithm, by reweighting, it can deal with the graphs with negative edges. However, if it detect a route through which the path will become shorter and shorter(a negative cycle), it will end the program and give out "Negative cycle".


Thank you for reading!
Have a good day!
