### Learning to play Pursuer Evader using a Self-Organizing Map

![The Pursuer-Evader Library](images/screenshot.png)

#### Introduction
I have developed this project as part of my job as Teaching Assistant for the course [Introduction to Adaptive Systems](http://www.cs.uu.nl/docs/vakken/ias/) by Gerard Vreeswijk. The task was the for the students to develop an A.I. that controls the "Evader" in a variant of the [Pursuer Evader](http://www.wikiwand.com/en/Pursuit-evasion) game. The behavior of the evader is determined by implementing a Self-Organizing Map (SOM), which can be trained by playing a large number of rounds of the game. The results are displayed in a table or graph that shows the percentage of wins by the evader over a large number of rounds. The goal is that this percentage increases when the game develops, and that the evader eventually wins as much rounds as possible.

In the Pusuer Evader Tracking (PET) game there are two playeres: A puruser and an evader. The evader tries to escape from the pursuer, while the pursuer has as its goal to catch the evader. The game is played on an infinitely large, two-dimensional field, in which agents have a constant starting position relative to each other, but a random starting direction. The tension in the game lies in the fact that the pursuer moves with a higer speed, while the evader is able to turn more quickly.

#### Usage information for the Pursuer-evader library

The library consists of a JAR file, that you have to import into your Java project. 

- Create a new Java project
- Add the example implementation (```ExampleAI.java```) to the ```src``` directory
- Import the library as an external library
- Run ExampleAI.java as an Applet.
