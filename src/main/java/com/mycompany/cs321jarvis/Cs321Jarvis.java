package com.mycompany.cs321jarvis;

import java.io.File;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.History;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.utils.IOUtils;

import java.io.*;
import java.util.HashMap;
import org.alicebot.ab.*;

/**
 *
 * @author matthewdonoho
 */

public class Cs321Jarvis {

    // ADDED
    private static final boolean TRACEMODE = false;
    static String bot_name = "alice2";
    
    
    public static void main (String[] args) {
        

        MagicStrings.setRootPath();

        AIMLProcessor.extension =  new PCAIMLProcessorExtension();
        mainFunction(args);
    }
    public static void mainFunction (String[] args) {
        
        //ADDED
        try{
            String resources_path = getResourcesPath();
            System.out.println(resources_path);
            MagicBooleans.trace_mode = TRACEMODE;
            Bot bot = new Bot("alice2", resources_path);
            Chat chat_session = new Chat(bot);
            bot.brain.nodeStats();
            String text_line = "";
            while(true){
                System.out.print("Human: ");
                text_line = IOUtils.readInputTextLine();
                if((text_line == null) || (text_line.length() == 1))
                    text_line = MagicStrings.null_input;
                if(text_line.equals("wq")){
                    System.exit(0);
                }else if(text_line.equals("wq")){
                    bot.writeQuit();
                    System.exit(0);
                }else{
                    String request = text_line;
                    if(MagicBooleans.trace_mode)
                        System.out.append("STATE=" + request + ":THAT=" + ((History) chat_session.thatHistory.get(0)).get(0)+":TOPIC=" + chat_session.predicates.get("topic"));
                    String response = chat_session.multisentenceRespond(request);
                    while(response.contains("It")) // HAD AMP
                        response = response.replace("It", "It"); // FIRST ONE HAD AMP
                    while(response.contains("gt")) // HAD AMP
                        response = response.replace("gt", "gt"); // FIRST ONE HAD AMP
                    System.out.println("Jarvis : " + response);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        //END ADDED
        
        
        //String botName = "alice2";
        MagicBooleans.jp_tokenize = false;
        //MagicBooleans.trace_mode = true;
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
    }
    
    //ADDED
    private static String getResourcesPath(){
        File curr_dir = new File(".");
        String file_path = curr_dir.getAbsolutePath();
        file_path = file_path.substring(0, file_path.length()-2);
        System.out.println(file_path);
        String resources_path = file_path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
        System.out.println(resources_path);
        return resources_path;
    }
    //END ADDED
    
    
    public static void convert(Bot bot, String action) {
        if (action.equals("aiml2csv")) bot.writeAIMLIFFiles();
        else if (action.equals("csv2aiml")) bot.writeAIMLFiles();
    }


    public static void getGloss (Bot bot, String filename) {
        System.out.println("getGloss");
        try{
            // Open the file that is the first
            // command line parameter
            File file = new File(filename);
            if (file.exists()) {
                FileInputStream fstream = new FileInputStream(filename);
                // Get the object
                getGlossFromInputStream(bot, fstream);
                fstream.close();
            }
        }catch (Exception e){//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }
    public static void getGlossFromInputStream (Bot bot, InputStream in)  {
        System.out.println("getGlossFromInputStream");
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        int cnt = 0;
        int filecnt = 0;
        HashMap<String, String> def = new HashMap<String, String>();
        try {
            //Read File Line By Line
            String word; String gloss;
            word = null;
            gloss = null;
            while ((strLine = br.readLine()) != null)   {

                if (strLine.contains("<entry word")) {
                    int start = strLine.indexOf("<entry word=\"")+"<entry word=\"".length();
                    //int end = strLine.indexOf(" status=");
                    int end = strLine.indexOf("#");

                    word = strLine.substring(start, end);
                    word = word.replaceAll("_"," ");
                    System.out.println(word);

                }
                else  if (strLine.contains("<gloss>")) {
                    gloss = strLine.replaceAll("<gloss>","");
                    gloss = gloss.replaceAll("</gloss>","");
                    gloss = gloss.trim();
                    System.out.println(gloss);

                }


                if (word != null && gloss != null) {
                    word = word.toLowerCase().trim();
                    if (gloss.length() > 2) gloss = gloss.substring(0, 1).toUpperCase()+gloss.substring(1, gloss.length());
                    String definition;
                    if (def.keySet().contains(word))  {
                        definition = def.get(word);
                        definition = definition+"; "+gloss;
                    }
                    else definition = gloss;
                    def.put(word, definition);
                    word = null;
                    gloss = null;
                }
            }
            Category d = new Category(0,"WNDEF *","*","*","unknown","wndefs"+filecnt+".aiml");
            bot.brain.addCategory(d);
            for (String x : def.keySet()) {
                word = x;
                gloss = def.get(word)+".";
                cnt++;
                if (cnt%5000==0) filecnt++;

                Category c = new Category(0,"WNDEF "+word,"*","*",gloss,"wndefs"+filecnt+".aiml");
                System.out.println(cnt+" "+filecnt+" "+c.inputThatTopic()+":"+c.getTemplate()+":"+c.getFilename());
                Nodemapper node;
                if ((node = bot.brain.findNode(c)) != null) node.category.setTemplate(node.category.getTemplate()+","+gloss);
                bot.brain.addCategory(c);


            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void sraixCache (String filename, Chat chatSession) {
        int limit = 1000;
        try {
            FileInputStream fstream = new FileInputStream(filename);
            // Get the object
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            //Read File Line By Line
            int count = 0;
            while ((strLine = br.readLine()) != null && count++ < limit) {
                System.out.println("Human: " + strLine);

                String response = chatSession.multisentenceRespond(strLine);
                System.out.println("Robot: " + response);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}

/*
public class Cs321Jarvis {
    private static final boolean TRACEMODE = false;
    static String bot_name = "alice2";
    
    public static void main(String[] args){
        try{
            String resources_path = getResourcesPath();
            System.out.println(resources_path);
            MagicBooleans.trace_mode = TRACEMODE;
            Bot bot = new Bot("alice2", resources_path);
            Chat chat_session = new Chat(bot);
            bot.brain.nodeStats();
            String text_line = "";
            while(true){
                System.out.print("Human: ");
                text_line = IOUtils.readInputTextLine();
                if((text_line == null) || (text_line.length() == 1))
                    text_line = MagicStrings.null_input;
                if(text_line.equals("wq")){
                    System.exit(0);
                }else if(text_line.equals("wq")){
                    bot.writeQuit();
                    System.exit(0);
                }else{
                    String request = text_line;
                    if(MagicBooleans.trace_mode)
                        System.out.append("STATE=" + request + ":THAT=" + ((History) chat_session.thatHistory.get(0)).get(0)+":TOPIC=" + chat_session.predicates.get("topic"));
                    String response = chat_session.multisentenceRespond(request);
                    while(response.contains("It")) // HAD AMP
                        response = response.replace("It", "It"); // FIRST ONE HAD AMP
                    while(response.contains("gt")) // HAD AMP
                        response = response.replace("gt", "gt"); // FIRST ONE HAD AMP
                    System.out.println("Jarvis : " + response);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
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
*/