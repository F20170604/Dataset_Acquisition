# Dataset_Acquisition
Backend for Gamification of NLP Dataset Acquisition Platform

# PROJECT DESCRIPTION

This project is all about the development of a Dataset Acquisition Platform for Words which can be presented as an intuitive game to people.

Our game is inspired from the game of Candy Crush where several shapes fall on each other and as soon as they form a pattern of similar colours, that group of colours disappear and give points to the user.

Our game is a little bit different. In our game, a specific number of pre-defined English words having different scores and random colours will fall down randomly in our grid. As soon as the user enters an English word and its corresponding translation in the second language i.e. Konkani in our specific case, one of the 3 following things can happen:
1. User enters a word present in the grid: The game will search for the largest group of words having same colour, connected to the input and remove it. The full score corresponding to each word removed will get added to the user’s score.
2. User enters a word not present in the grid: The game will then search for the largest group of words having same colour, connected to the word most closely related to the input and remove it. In this case half of the score corresponding to each word removed will get added to the user’s score.
3. User enters an input which has been already once or is invalid: In this case, our game will ask once again for the input.

After generating the score, our game will then add a new word to the game which is related to our user’s input.

Our game also has an incentive for the user - For every 10 words played by the user, the game provides an opportunity to the user to double the score of any word present in the grid.

The game ends as soon as one column of the columns get filled with words.

## Game Features
* Automated Board Refill if in any point of time all words are scored up by the user
* Parallel saving of English and Second Language Words in the form os a .csv file so even if our game ends
abruptly, our data is not lost
* Custom word placement in the grid - Administrator can choose its own position of words in the grid instead of randomly placing them while board filling.
* Polyglot Codebase - Using Java as the main language for backend gives us 2.5x faster operations in comparison to Python. Python is used for Machine Learning happening and is only invoked when needed.
* Hash Tables are used for Data Queries to provide amortised O(1) Time complexity for search, add and replace operations.

# PROJECT DESIGN

![](images/System Design.png?raw=true)
