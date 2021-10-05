# Ad-Free Sodoku (README.md)

## 1 Motivation
Ad-Free Sodoku is an app that allows you to play Sodoku puzzles, and doesn't contain any annoying ads, or in-app purchases. 
I'm designing this project for the sole purpose that I wanted an app that will allow me to play sodoku puzzles eaisily and accessibly,
without the need for annoyances like daily rewards, in-game-currencies, forced advertisements, or unnecessary in-app-purchases.
While I don't intend to profit from this app, the target audience would be anyone who, like me, wants a free sodoku app that doesn't contain
any of the annoyances listed previously.

## 2 Meeting Minimum Requirements
The app will consist of four main screens, a Main Menu, a Game Activity, a Stats Page, and an Options Menu.
The Main Menu consists of three buttons that go to each of the other three screens.
The Game Activity has the sodoku board, and 9 buttons with which to insert numbers into the puzzle.
The Stats Page shows all data stored by the app (ex: High Score (Best Time), Total Games Won/Lost).
The Options Menu has a difficulty adjuster, and a color scheme picker.

### 2.1 User Interface
#### 2.1.1 Main Menu

![Main Menu Diagram](/assets/images/MainMenu.PNG)

#### 2.1.2 Game Activity

![Game Activity Diagram](/assets/images/GameActivity.PNG)

#### 2.1.3 Stats Page

![Stats Page DIagram](/assets/images/StatsPage.PNG)

#### 2.1.4 Options Menu

![Option Menu Diagram](/assets/images/OptionMenu.PNG)


### 2.2 Data Persistence
The following will be stored locally on the device:
OnDeath, the current progress of a game will be saved. i.e. all the numbers/notes inputed into the current board will be saved.
The Stats Page will store the following information:
* High Score (Time)
* Average Score (Time)
* Total Games Played
* Total Games Won (Number and %)
* Current Win Streak
* Longest Win Streak

### 2.3 Communication
App Will have integration with Google Play (Better Desc. Coming Soon)

## 3 MVC Breakdown

### 3.1 View Descriptions
#### 3.1.1 Main Menu

**3.1.1.1**

* TITLE: BUTTON-Play/Resume Game

* DESC: OnClick: Starts the Game Activity

**3.1.1.2**

* TITLE: BUTTON-Stats

* DESC: OnClick: Starts the Stats Activity

**3.1.1.3**

* TITLE: BUTTON-Options

* DESC: OnClick: Starts the Options Activity

#### 3.1.2 Game Activity

**3.1.2.1**

* TITLE: TEXTVIEW-Time/Score

* DESC: Shows current elapsed time since puzzle was started and open on screen.

**3.1.2.2**

* TITLE: BUTTON-Setting

* DESC: OnClick: Opens a PopupWindow and pauses the game timer. The popup window contains three buttons: RESUME, RESTART, EXIT

**3.1.2.2.1**

* TITLE: BUTTON-RESUME

* DESC: OnClick: Closes the PopupWindow

**3.1.2.2.2**

* TITLE: BUTTON-RESTART

* DESC: OnClick: Closes the PopupWindow, and Resets the GAME VIEW

**3.1.2.2.3**

* TITLE: BUTTON-EXIT

* DESC: OnClick: Closes the PopupWindow, and returns to the Main Menu (Game State will be Saved)

**3.1.2.3**

* TITLE: GAME VIEW

* DESC: The GAME VIEW is comprised of a series of nested TextViews, each of which become highlighed onClick. Any numbers in Number Input that are highlighted are un-highlighted.

**3.1.2.4**

* TITLE: Number Input

* DESC: Number Input is a series of nested BUTTONS

* DESC: OnClick(number picked) If a TextView in GAMEVIEW is highlighted, the number picked is put into the GAMEVIEW in the highlighted square.

* DESC: OnClick(number picked) If a TextView in GAMEVIEW is NOT highlighted, the number picked is highlighted, and some "hints" may be displayed.

#### 3.1.3 Stats Page

**3.1.3.1**

* TITLE: ListView-Scrolling List of Stats

* DESC: Displays the stored data described in 2.2

**3.1.3.2**

* TITLE: BUTTON-Back

* DESC: Returns to the Main Menu Activity

#### 3.1.4 Options Menu

**3.1.4.1**

* TITLE: Slider-Difficulty Slider

* DESC: The value of the slider determines the difficulty of newly generated sodoku puzzles.

**3.1.4.2**

* TITLE: RadioButtonGroup-Theme Selection

* DESC: The Theme of the App is determined based on which RadioButton is Clicked. This update happens when a radioButton is selected.

**3.1.4.3**

* TITLE: BUTTON-Back

* DESC: Returns to the Main Menu Activity

### 3.2 Model Descriptions

### 3.3 Controller Descriptions
The Game Activity and Options Menu will both have view models that store the data contained within them. 
Data will be saved as it is inputed, and will persist after termination.
ViewModels will be used to save data while the app is running, and data will be saved locally when the app is terminated.
OnCreate will pull all of the locally saved data and re-instantiate it into the ViewModels.
