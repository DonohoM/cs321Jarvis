
Jarvis: The Support Chatbot

Jarvis is an interactive chatbot built using Alice2.0 and AIML (Artificial Intelligence Markup Language) designed to offer emotional support and kind words to its users. When you need a little encouragement or a comforting conversation, Jarvis is there for you.
Features

    A chatbot powered by Alice2.0 and AIML, known for its ability to understand and respond in natural language.
    Provides comforting and supportive responses to users.
    Switch between a user-friendly interface and a developer mode for deep interaction with Jarvis's "brain."

Technologies Used

    Java: The main programming language used to build the project.
    AIML (Artificial Intelligence Markup Language): A markup language used for creating conversation patterns.
    AliceBot2.0: A chatbot engine that interprets AIML files and generates responses.

Installation

    Clone the Repository:
    Begin by cloning the repository to your local machine:

git clone https://github.com/DonohoM/jarvis.git
cd jarvis

Dependencies:
This project uses Java, so make sure you have the latest version of Java installed. If you're unsure, you can install it via Oracle's website.

Build the Project:
If you're using a build tool like Maven or Gradle, you can use it to build the project. Otherwise, compile the Java files manually:

javac -d bin src/*.java

Run the Project:
After building, you can run the main program using:

    java -cp bin Cs321Jarvis

Usage

To interact with Jarvis via its user-friendly interface:

    Run the application normally (without making any modifications).
    Jarvis will start with a graphical user interface (GUI) where you can chat with it directly.

Developer Mode

If you're interested in interacting with Jarvis's "brain" directly (via the terminal), you can switch to Developer Mode:

    Open the Cs321Jarvis.java file.
    In the mainFunction2 method, comment out the following line:

    SwingUtilities.invokeLater(() -> new Cs321Jarvis());

    This will launch the bot in terminal-based mode, where you can interact with Jarvis directly by typing into the terminal.
