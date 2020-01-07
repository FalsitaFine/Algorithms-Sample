cuixx327@umn.edu
Tianming cui
5326541
The code has been tested with sample input on CSEL-KH1260-09



usage: bash run.sh inputfilename(or ./run.sh inputfilename)
put the inputfile in the same direction with the code(and .sh),
then type bash run.sh,
the program will give out an output.txt
Assumption: all characters in input file are in lower case(same in the problem description)

The solvement here is, first implement a common finite automata:
Compute the Transision Function of given pattern, then use it going over the given string, find where state number is equals to the length of given pattern.

Then, in order to find the pattern from backwards, I reverse the pattern, rebuild the Transision Function and search it again.

Thank you for reading!
Have a good day!
