

/**
 *
 * @author matthewdonoho
 */

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class jarvisGUI extends JFrame {
    private JPanel chatPanel;
    private JTextField inputField;
    private JButton sendButton;
    private JScrollPane scrollPane;

    public jarvisGUI() {
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
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        // Action for pressing Enter in the input field
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        // Make the frame visible
        setVisible(true);
    }

    // Method to handle sending messages
    private void sendMessage() {
        String message = inputField.getText().trim();
        if (!message.isEmpty()) {
            addBubbleMessage(message, new Color(0, 122, 255), SwingConstants.RIGHT); // User's message in blue
            inputField.setText("");  // Clear the input field
            
            // Simple Jarvis response
            addBubbleMessage("I'm here to help!", new Color(230, 230, 235), SwingConstants.LEFT); // Jarvis's message in gray
        }
    }

    // Method to add a bubble message to the chat panel  
 
    private void addBubbleMessage(String message, Color bubbleColor, int alignment) {
        // Create a new RoundedPanel for the chat bubble
        //roundedPanel bubble = new roundedPanel(bubbleColor, 20);  // Reduced corner radius for a sleeker look
        //bubble.setLayout(new BorderLayout());

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
            lines = (textWidth / maxWidth) + 1;
            textWidth = maxWidth;
        }

        int bubbleWidth = Math.max(minWidth, Math.min(textWidth + 39, maxWidth)); // 32 is for left and right padding

        // Calculate the bubble height based on the number of lines
        int bubbleHeight = Math.max(fm.getHeight() * lines + 30, 30); // 24 is for top and bottom padding, 40 is minimum height

        // Set the preferred size of the bubble based on the calculated dimensions
        //bubble.setPreferredSize(new Dimension(bubbleWidth, bubbleHeight));

        // Add the JTextArea to the bubble
        //bubble.add(messageArea, BorderLayout.CENTER);

        // Set alignment for the message bubble and add it to the message panel
        JPanel messagePanel = new JPanel();
        messagePanel.setOpaque(false);

        // Use FlowLayout for left or right alignment
        if (alignment == SwingConstants.LEFT) {
            messagePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        } else {
            messagePanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        }

        //messagePanel.add(bubble);

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
    
    // Main method to run the GUI
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new jarvisGUI());
    }
    
}