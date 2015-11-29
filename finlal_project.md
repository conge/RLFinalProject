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
TD-Lambda (TD(λ)), a machine learning algorithm designed for reinforcement learning problems using temporal information, is invented by Richard S. Sutton. In his paper introducing TD(λ) (Sutton, 1988), Sutton provided a 5-step random walking problem as an example and performed two computational experiments to demonstrate the performance of the method. The purpose of this report is to replicate the experiments using the method described in Sutton's paper.

The 5-step random walking example consists of a Markov chain of 5 states (B, C, D, E, and F) plus two absorbing states (A and G, Figure 1). The reward for states A and G are 0 and 1, respectively. For the non-absorbing states, the transition probabilities of moving to the right and to the right are both 0.5, and the ideal probability of a walk ending in state G from each state is:	T = {1/6 2/6 3/6 4/6 5/6}, (e.g. p(G|B) = 1/6). The goal is to learn the estimated transition probabilities (a weight vector w) over a series of random walks using the TD method. 

## Method

To evaluate the performance of TD(λ) method, 100 training sets, each consists of 10 random walking sequences, were generated for the two computational experiments. In experiment 1 (repeated presentation), each training set was fed to the learning algorithm repeatedly until it converges (e.g. the learning procedure stops producing significant changes in w). The weight vector w will be updated only after the algorithm was trained on the 10 sequences in a training set, before the next learning episode. 

In experiment 2, the algorithm will see the training sets only once. And the weight vector w is updated after each sequence. To demonstrate the effect of learning rate (α) on the performance of TD(λ), multiple learning rate were applied to the learning algorithm.

For both experiments, the root mean squared (RMS) error of w against the ideal predictions T were calculated for each training set. The RMS errors of the 100 training sets were then averaged and plotted against λ and learning rate to demonstrate and evaluate the performance of TD(λ) method.

Although Sutton mentioned that the algorithm will always converge if small learning rate is used in the repeated presentations of training set, the learning rate and  the convergence threshold were not reported for experiment 1. In my replication, learning rate α = 0.05 and convergence threshold ε = 0.0005 were used.

## Results

In the repeated presentation experiment,  the performance declines as λ increases with TD(0) method has the best performance. The error increases rapidly as λ approaching to 1 (Figure 2) with TD(1) has the worst performance.

As shown in figure 3, the value of learning rate has evident effects on the performance of TD(λ) and most of the TD(λ) performed the best when α = 0.2 (the best α in Sutton's original paper is 0.3). Figure 4 shows the best RMS error level of for each λ in TD(λ) when the best α is applied to the corresponding λ. In this case, TD(0) does not have the best performance any more, and TD(0.2) seems to have the best performance.

## Discussion

## Reference

Sutton, R. S. (1988). Learning to predict by the methods of temporal differences. *Machine learning*, *3*(1), 9-44.
