
Jarvis: The Support Chatbot

Jarvis is an interactive chatbot built using Alice2.0 and AIML (Artificial Intelligence Markup Language) designed 
to offer emotional support and kind words to its users. When you need a little encouragement or a comforting 
conversation, Jarvis is there for you.

Features

    A chatbot powered by Alice2.0 and AIML, known for its ability to understand and respond in natural language.
    Provides comforting and supportive responses to users.
    Switch between a user-friendly interface and a developer mode for deep interaction with Jarvis's "brain."

Technologies Used

    Java: The main programming language used to build the project.
    AIML (Artificial Intelligence Markup Language): A markup language used for creating conversation patterns.
    AliceBot2.0: A chatbot engine that interprets AIML files and generates responses.

INSTRUCTIONS

    Dependencies:
    This project uses Java, so make sure you have the latest version of Java installed. The Java Development Kit is
    required to compile the project. If you're unsure, you can install it via Oracle's website.

    Runing the program:

    The Jar file has dependencies that require it to be run from the root of the project folder. Dont run the one from the target folder,
    it wont work unless you move it outside of the target folder and into the root of the project folder. 

    If the jar file still doesnt work after you double click on it, then navigate to the root folder and run:
    java -jar target/cs321Jarvis-1.0-SNAPSHOT.jar   
    in your terminal
    

    Building:
    Use maven to build the project (Cs321Jarvis.java) from the netbeans IDE or from the terminal.

    Run the Project:
    After building, you can run the main program (Cs321Jarvis.java) using the run button at the top of the netbeans IDE. 

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
