# Finlal Project

One aspect of research in reinforcement learning (or any scientific field) is the replication of previously published results. One benefit to replication is to aid your own understanding of the results. Another is that it puts you in a good position for being able to extend and compare new contributions to what’s in the existing literature. Replication can be challenging. Researchers often find that important parameters needed to replicate results from papers are not stated in papers or that the procedures stated in papers have ambiguity or subtle errors. Sometimes obtaining the same pattern of results isn’t possible.

Read Richard Sutton’s 1988 paper and create an implementation and replication of the results in Figures 3, 4, and 5. (You might also compare these results with those in Chapter 7 of Sutton’s textbook: [https://webdocs.cs.ualberta.ca/~sutton/book/ebook/node73.html](https://webdocs.cs.ualberta.ca/~sutton/book/ebook/node73.html).)

You will present your work at the end of the semester via a short (at most 3 minutes) video presentation and a 2-to-3-page written report. The report/presentation should include a description of the experiment replicated, how the experiment was implemented, and the outcome of the experiment. You should describe how well the results match the results given in the paper as well as significant differences. Also describe any pitfalls you ran into while trying to replicate the experiment from the paper (e.g. unclear parameters, contradictory descriptions of the procedure to follow, results that differ wildly from the published results). What steps did you take to overcome those pitfalls? What assumptions did you make? Why are these assumptions justified?

Grades will be based on the fidelity of the replication (25%), how well you show you understand the original paper (25%), the quality of your presentation (25%), and your written report (25%).

* description of the experiment replicated
* how the experiment was implemented
* the outcome of the experiment
*  describe how well the results match the results given in the paper
*  differences
*  any pitfalls you ran into 
*  What assumptions did you make? 
*  Why are these assumptions justified


----

TD-Lambda, a machine learning algorithm designed for reinforcement learning problems, is invented by Richard S. Sutton. In his paper introducing TD-lambda (Sutton, 1988), Sutton provided a 5-step random walking problem as an example and performed two computational experiments to demostrate the performance of the method. The purpose of this report is to replicate the experiments using the method discribed in Sutton's paper.

The 5-step random walking example consists of a Markov chain of 5 states (B, C, D, E, and F) plus two absorbing states (A and G, Figure 1). The reward for states A and G are 0 and 1, respectively. For the non-absorbing states, the transition probabilities of moving to the right are 0.5, and the ideal probability of a walk ending in state G from each state is:	T = {1/6 2/6 3/6 4/6 5/6}, (e.g. p(G|B)=1/6). The goal is to learn the estimated transition probabilities as a weight vector w over a series of random walks. The RMS error between the ideal predictions and the estimated weight vector w is used to evaluate the performance fo the TD-lambda method.

