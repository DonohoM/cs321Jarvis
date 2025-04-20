package com.mycompany.cs321jarvis;

import org.alicebot.ab.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

//import com.mycompany.cs321jarvis.jarvisGUI;

/**
 *
 * @author matthewdonoho
 */

public class Cs321Jarvis extends JFrame{
    
    // ADDED
    private static final boolean TRACEMODE = false;
    static String bot_name = "alice2";
    
    static JPanel chatPanel;
    static JTextField inputField;
    static JButton sendButton;
    static JScrollPane scrollPane;
    
    
    
    // GUI
    public Cs321Jarvis(){
        // Set up the main frame
        setTitle("Jarvis");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Chat panel to hold messages
        chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));  // Stacks messages vertically
        chatPanel.setBackground(new Color(20, 20, 20)); // Light iMessage-style background color

        // Scrollable chat panel
        scrollPane = new JScrollPane(chatPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // Input field and send button at the bottom
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        sendButton = new JButton("Send");

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);
        
        
        // Action for the send button
        sendButton.addActionListener((ActionEvent e) -> {
            sendMessage();
        });

        // Action for pressing Enter in the input field
        inputField.addActionListener((ActionEvent e) -> {
            sendMessage();
        });
        
        // Make the frame visible
        setVisible(true);
    }
    
    // ADDED GUI
    
    private void sendMessage(){
        System.out.println("Send_Message_Class");
        //String message = inputField.getText().trim();
        //text = new JTextField(message);
        //inputField = new JTextField(text);
        try{
            String resources_path = getResourcesPath();
            System.out.println(resources_path);
            MagicBooleans.trace_mode = TRACEMODE;
            Bot bot = new Bot("alice2", resources_path);
            Chat chat_session = new Chat(bot);
            bot.brain.nodeStats();
            String text_line = "";
            
            // add the invoce later here but in the main func 2 
            while(true){
                System.out.print("Human: ");
                
                text_line = inputField.getText().trim();
                System.out.println(text_line);
                
                inputField.setText("");
                
                if((text_line == null) || (text_line.length() == 1)){
                    addBubbleMessage(text_line, new Color(0, 122, 255), SwingConstants.RIGHT);
                    text_line = MagicStrings.null_input;
                }
                if(text_line.equals("wq")){
                    System.exit(0);
                }else if(text_line.equals("wq")){
                    bot.writeQuit();
                    System.exit(0);
                }else{
                    String request = text_line;
                    addBubbleMessage(request, new Color(0, 122, 255), SwingConstants.RIGHT);
                    if(MagicBooleans.trace_mode)
                        System.out.append("STATE=" + request + ":THAT=" + ((History) chat_session.thatHistory.get(0)).get(0)+":TOPIC=" + chat_session.predicates.get("topic"));
                    String response = chat_session.multisentenceRespond(request);
                    while(response.contains("It")) // HAD AMP
                        response = response.replace("It", "It"); // FIRST ONE HAD AMP
                    while(response.contains("gt")) // HAD AMP
                        response = response.replace("gt", "gt"); // FIRST ONE HAD AMP
                    System.out.println("Jarvis : " + response);
                    addBubbleMessage(response, new Color(230, 230, 235), SwingConstants.LEFT); // Jarvis's message in gray
                }
                break;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    };
    
    private void addBubbleMessage(String message, Color bubbleColor, int alignment) {
        // Create a new RoundedPanel for the chat bubble
        roundedPanel bubble = new roundedPanel(bubbleColor, 20);  // Reduced corner radius for a sleeker look
        bubble.setLayout(new BorderLayout());

        // Create a JTextArea for the message that supports wrapping
        JTextArea messageArea = new JTextArea(message);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setOpaque(false);
        messageArea.setEditable(false);
        messageArea.setFont(new Font("Arial", Font.PLAIN, 14));
        messageArea.setBorder(new EmptyBorder(12, 16, 12, 16)); // Adjusted padding

        // Calculate the preferred size of the text
        int maxWidth = (int) (chatPanel.getWidth() * 0.75); // Maximum width is 75% of chat panel width
        int minWidth = 50; // Minimum width to prevent tiny bubbles

        // Calculate the width based on the text content
        FontMetrics fm = messageArea.getFontMetrics(messageArea.getFont());
        int textWidth = fm.stringWidth(message);
        int lines = 1;
        if (textWidth > maxWidth) {
            if(maxWidth == 0)
                maxWidth = 1;
            lines = (textWidth / maxWidth) + 1;
            textWidth = maxWidth;
        }

        int bubbleWidth = Math.max(minWidth, Math.min(textWidth + 39, maxWidth)); // 32 is for left and right padding

        // Calculate the bubble height based on the number of lines
        int bubbleHeight = Math.max(fm.getHeight() * lines + 30, 30); // 24 is for top and bottom padding, 40 is minimum height

        // Set the preferred size of the bubble based on the calculated dimensions
        bubble.setPreferredSize(new Dimension(bubbleWidth, bubbleHeight));

        // Add the JTextArea to the bubble
        bubble.add(messageArea, BorderLayout.CENTER);

        // Set alignment for the message bubble and add it to the message panel
        JPanel messagePanel = new JPanel();
        messagePanel.setOpaque(false);

        // Use FlowLayout for left or right alignment
        if (alignment == SwingConstants.LEFT) {
            messagePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        } else {
            messagePanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        }

        messagePanel.add(bubble);

        // Add the message panel to the chat panel
        chatPanel.add(messagePanel);
        chatPanel.revalidate();
        chatPanel.repaint();

        // Scroll to the bottom of the chat panel
        SwingUtilities.invokeLater(() -> {
            JScrollBar vertical = scrollPane.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        });
    }
    // ADDED GUI END
    
    public static void main (String[] args) {
        //SwingUtilities.invokeLater(() -> new Cs321Jarvis());
        
        MagicStrings.setRootPath();

        AIMLProcessor.extension =  new PCAIMLProcessorExtension();
        
        mainFunction2(args);
    }
    
    
    public static void convert(Bot bot, String action) {
        if (action.equals("aiml2csv")) bot.writeAIMLIFFiles();
        else if (action.equals("csv2aiml")) bot.writeAIMLFiles();
    }

    // Main func 2
    
    public static void mainFunction2(String[] args){
        
        System.out.println("Begining Function 2");
        
        MagicBooleans.jp_tokenize = false;
        String action="chat";
        System.out.println(MagicStrings.program_name_version);
        for (String s : args) {
            //System.out.println(s);
            String[] splitArg = s.split("=");
            if (splitArg.length >= 2) {
                String option = splitArg[0];
                String value = splitArg[1];
                //if (MagicBooleans.trace_mode) System.out.println(option+"='"+value+"'");
                if (option.equals("bot")) bot_name = value;
                if (option.equals("action")) action = value;
                if (option.equals("trace")) {
                    if (value.equals("true")) MagicBooleans.trace_mode = true;
                    else MagicBooleans.trace_mode = false;
                }
                if (option.equals("morph")) {
                    if (value.equals("true")) MagicBooleans.jp_tokenize = true;
                    else {
                        MagicBooleans.jp_tokenize = false;
                    }
                }
             }
        }
        if (MagicBooleans.trace_mode) System.out.println("Working Directory = " + MagicStrings.root_path);
        Graphmaster.enableShortCuts = true;
        //Timer timer = new Timer();
        Bot bot = new Bot(bot_name, MagicStrings.root_path, action); //
        //EnglishNumberToWords.makeSetMap(bot);
        //getGloss(bot, "c:/ab/data/wn30-lfs/wne-2006-12-06.xml");
        if (MagicBooleans.make_verbs_sets_maps) Verbs.makeVerbSetsMaps(bot);
        //bot.preProcessor.normalizeFile("c:/ab/data/log2.txt", "c:/ab/data/log2normal.txt");
        //System.exit(0);
        if (bot.brain.getCategories().size() < MagicNumbers.brain_print_size) bot.brain.printgraph();
        if (MagicBooleans.trace_mode) System.out.println("Action = '"+action+"'");
        if (action.equals("chat") || action.equals("chat-app")) {
			boolean doWrites = ! action.equals("chat-app");
                        // comment out below line for developer mode
                        SwingUtilities.invokeLater(() -> new Cs321Jarvis());
			TestAB.testChat(bot, doWrites, MagicBooleans.trace_mode);
		}
        //else if (action.equals("test")) testSuite(bot, MagicStrings.root_path+"/data/find.txt");
        else if (action.equals("ab")) TestAB.testAB(bot, TestAB.sample_file);
        else if (action.equals("aiml2csv") || action.equals("csv2aiml")) convert(bot, action);
        else if (action.equals("abwq")){AB ab = new AB(bot, TestAB.sample_file);  ab.abwq();}
		else if (action.equals("test")) { TestAB.runTests(bot, MagicBooleans.trace_mode);     }
        else if (action.equals("shadow")) { MagicBooleans.trace_mode = false; bot.shadowChecker();}
        else if (action.equals("iqtest")) { ChatTest ct = new ChatTest(bot);
                try {
                    ct.testMultisentenceRespond();
                }
            catch (Exception ex) { ex.printStackTrace(); }
            }
        else System.out.println("Unrecognized action "+action);
        
        //SwingUtilities.invokeLater(() -> new Cs321Jarvis());
        
    }
    private static String getResourcesPath(){
        File curr_dir = new File(".");
        String file_path = curr_dir.getAbsolutePath();
        file_path = file_path.substring(0, file_path.length()-2);
        System.out.println(file_path);
        String resources_path = file_path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
        System.out.println(resources_path);
        return resources_path;
    }
}
