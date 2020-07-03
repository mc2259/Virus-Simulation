# Virus-Simulation

##  Modelling-viruses

This simulation of how an infectious virus spreads, provides a real-world example of the general tree data-structure


We all get bug or infections, or viruses, from time to time. Sometimes they are just an annoyance. Right now, the
world is dealing with a highly infectious and dangerous one, the coronavirus. The United States Centers for Disease
Control and Prevention (CDC) tracks how a virus is spreading and attempts to determine where it will spread next,
so preparations can be made to fight it. People develop programs to simulated the spread of viruses in order to learn
about them, just as programs are written to simulate weather. 

##  How does the model work:




The model is initialized with a population of people —1, 2, 10, 50— however many you choose. Each human
has a health range, say 0..10. A health of 10 means the human is very healthy and 0 means the human has died. In
the beginning, everyone is as healthy as possible. Generally speaking, the larger the health range, the longer the program will run since there are more possible states of the simulation.
At the beginning of the simulation, you supply several probabilities: 

(1) The probability that any two humans are
close to each other —call them neighbors. A probability of 1 means everyone is close to everyone; a probability of 0
means no one is close to anyone. 

(2) The probability that a human will get the virus from a neighbor. 

(3) The probability that a human will become immune —once immune, the person is healthy forever.
Once you have given this input, the program starts by making one random person sick and making that person
the root of a new virus tree. Initially, that is the only node in the tree.


A series of time steps follows. In each time step, several things happen.
(1) one random healthy neighbor of each sick human may get the virus and be inserted into the virus tree as the
sick human’s child.
(2) Each sick human either gets well and henceforth immune from the virus or gets sicker (their health decreases).
(3) A sick human whose health decreases to 0 dies.


These time steps continue until everyone in the virus tree
is either healthy or dead. When the simulation is over,
the results are shown in a GUI on your monitor.

<img width="373" alt="Screenshot 2020-07-03 at 4 04 55 PM" src="https://user-images.githubusercontent.com/57819870/86461125-220fa600-bd47-11ea-8dde-474c7c9906d2.png">

From the above picture we can see:

HOA is at depth 0 (the root); KUM is at depth 1, Lin is at depth 2, MEG and AMY are at depth 3, and DEB is at depth 4.HOA got the virus first, HOA infected KUM, KUM
infected LIN, LIN infected MEG and AMY, and AMY
infected DEB. All of them got ill, and four died (HOA,
KUM, LIN, and AMY, indicate by a red circle). The two
with a green circle are alive and healthy.


<img width="199" alt="Screenshot 2020-07-03 at 4 05 03 PM" src="https://user-images.githubusercontent.com/57819870/86461139-28058700-bd47-11ea-9189-7741de33372b.png">


The information in the panel to the right( as can be seen in the picture )  appears only
when you click some node of the tree. The data changes
as you click on different nodes of the tree. The data that
appears in the panel comes from selecting (with your mouse) DEB and then MEG. The common ancestor of DEB
and MEG: LIN. For MEG, the last selected person, MEG, shows: the depth, 3; the width at (number of nodes at) that
level, 2; the parent, LIN; number of children, 0; subtree size, 1; the subtree depth, 0; and width, 1.
An execution may result in a tree with one node, as shown to the right. This happens
when the one human with the virus doesn’t infect anyone —they may have no neighbor
or, when generating a random number to test whether a neighbor gets infected, the result
is always “no”.

## Running

The executable class of this project is Virus.java. To run the project, open Virus.java and click run (green button
with white arrow) or use menu item Run -> Run. You will be prompted for a few values via the console at the bottom of Eclipse. These are:


1. Size of population: how many humans to model. A positive integer. A higher number may result in a larger
tree.

2. Amount of health per human: how long a human can have the virus before dying. A positive integer. A
higher number may result in a larger tree.

3. Probability of connection: how likely two random humans are likely to be neighbors). A float in the range
[0,1]. A higher number may result in a larger tree.
4. Probability of getting the virus: how likely a human is to get the virus when in contact with an infected
neighbor. A float in the range [0,1]. A higher may number result in a larger tree.


5. Probability of becoming immune: how likely a human with the virus will become immune (fight off the virus). A float in the range [0,1]. A lower number may result in a larger tree.
A nice set of starting values is: (50, 5, 0.7, 0.4, 0.1)

