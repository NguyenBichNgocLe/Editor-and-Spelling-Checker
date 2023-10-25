package uwf.project2;
/***************************************************************
Student Name: Ari Le
File Name: App.java
Assignment number: 2

The JavaFX App class to create the GUI and other methods for this project
***************************************************************/
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.TextArea;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.FileChooser;
import javafx.scene.control.ScrollBar;


/**
 * JavaFX App
 */
public class App extends Application {
	
    public static void main(String[] args) {
        Application.launch(args);
    }
	
	private File file;
	private TextArea textArea;
	private FileWriter writer;
	private BufferedWriter output;
	private Scanner input;
	private boolean fileIsOpen = false;
	private Dictionary dict = new Dictionary();
	
	//saving the list of words that are suggested
	private HashSet<String> suggestions = new HashSet<String>();

	@SuppressWarnings("exports")
	@Override
    public void start(final Stage primaryStage) {
    	
    	//Create TextArea
        textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setPrefHeight(300);
        textArea.setPrefWidth(200);
        
        //Create a scroll bar
        ScrollBar scroll = new ScrollBar();
        
        //Create MenuBar
        MenuBar menuBar = new MenuBar();
        
        //Create menus
        Menu fileMenu = new Menu("File");
        Menu editMenu = new Menu("Edit");
        
        //Create MenuItems for fileMenu
        MenuItem openItem = createOpenItem(primaryStage, textArea);
        MenuItem saveItem = createSaveItem(textArea);
        MenuItem exitItem = createExitItem();
        
        //Create a MenuItem for editMenu
        MenuItem spellCheck = createSpellCheckItem();
        
        //Add MenuItems to the fileMenu
        fileMenu.getItems().addAll(openItem, saveItem, exitItem);
        
        //Add MenuItem to the editMenu
        editMenu.getItems().addAll(spellCheck);
        
        //Add Menus to the menu bar
        menuBar.getMenus().addAll(fileMenu, editMenu);
        
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(5));
        vbox.getChildren().addAll(menuBar, textArea, scroll);
        
        Scene scene = new Scene(vbox, 350, 350);
   
        primaryStage.setTitle("Title");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /*
     * Method to create openItem for fileMenu
     * @param primaryStage, textArea
     * @return openItem
     */
    private MenuItem createOpenItem(Stage primaryStage, TextArea textArea)
    {
    	MenuItem openItem = new MenuItem("Open");
    	openItem.setOnAction(new EventHandler<ActionEvent>() {
    		
    		@Override
    		public void handle(ActionEvent event) {
    			FileChooser fileChooser = new FileChooser();
    			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
    			fileChooser.getExtensionFilters().add(extFilter);
    			file = fileChooser.showOpenDialog(primaryStage);
    			try {
    				input = new Scanner(file);
    				fileIsOpen = true;
    				textArea.setText(input.nextLine());
    				input.close();
    			}
    			catch(Exception e) {}
    		}
    	});
    	return openItem;
    }
    
    /*
     * Method to create saveItem for fileMenu
     * @return saveItem
     */
    private MenuItem createSaveItem(TextArea textArea)
    {
    	MenuItem saveItem = new MenuItem("Save");
    	saveItem.setOnAction(new EventHandler<ActionEvent>() {
    		
    		@Override
    		public void handle(ActionEvent event) {
    			try {
    				if(fileIsOpen == false)
    				{
    					writer = new FileWriter("output.txt");
    				}
    				else 
    				{
    					writer = new FileWriter(file.getName());
    				}
					output = new BufferedWriter(writer);
					output.write(textArea.getText());
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
    	});
    	return saveItem;
    }
    
    /*
     * Method to create exitItem for fileMenu
     * @return exitItem
     */
    private MenuItem createExitItem()
    {
    	MenuItem exitItem = new MenuItem("Exit");
    	exitItem.setOnAction(new EventHandler<ActionEvent>() {
    		
    		@Override
    		public void handle(ActionEvent event) {
    			System.exit(0);
    		}
    	});
    	return exitItem;
    }
    
    /*
     * Method to create spell check item for editMenu
     * @return spellCheck
     */
    private MenuItem createSpellCheckItem()
    {
    	MenuItem spellCheck = new MenuItem("Spell Check");
    	spellCheck.setOnAction(new EventHandler<ActionEvent>() {
    		
    		@Override
    		public void handle(ActionEvent event) {
    			dict.loadInDictionary();
    			String results = "";
    			Scanner in = new Scanner(textArea.getText());
    			in.useDelimiter("[^a-z0-9A-Z]");
    			
    			//clear the suggestions hashSet in case the user put in new sentences
    			if(!suggestions.isEmpty())
    				suggestions.clear();
    			
    			while(in.hasNext())
    			{
    				String str = in.next();
    				if(!dict.isContained(str))
    				{
    					firstMethod(str);
    					secondMethod(str);
    					thirdMethod(str);
    				}
    			}
    			//Create the dialog to show the suggested words
    			Dialog<String> dialog = new Dialog<String>();
    			dialog.setTitle("Suggested words");
    			ButtonType type = new ButtonType("OK", ButtonData.OK_DONE); //create the OK button for the suggested words dialog
    			
    			if(suggestions.isEmpty())
    				dialog.setContentText("No suggested words");
    			else
    			{
    				for(String i: suggestions)
    					results += i + "\n";
    				dialog.setContentText(results);
    			}
    			dialog.getDialogPane().getButtonTypes().add(type); //add the OK button in the dialog pane
    			dialog.showAndWait();
    			in.close();
    		}
    	});
    	
    	return spellCheck;
    }
    
    /*
     * the first method to create suggestions words
     * @param str
     */
    private void firstMethod(String str)
    {
    	String tmp = str;
    	StringBuffer stringBuffer;
    	char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    	for(int i = 0; i < (str.length() + 1); i++)
    	{
    		for(int j = 0; j < alphabet.length; j++)
    		{
    			stringBuffer = new StringBuffer(tmp);
    			stringBuffer.insert(i, alphabet[j]);
    			if(dict.isContained(stringBuffer.toString()) && stringBuffer.toString().length() != 1)
    				suggestions.add(stringBuffer.toString());
    			tmp = str;
    		}
    	}
    }
    
    /*
     * the second method to create suggestion words
     * @param str
     */
    private void secondMethod(String str)
    {
    	StringBuilder str1 = new StringBuilder(str);
    	StringBuilder og = new StringBuilder(str);
    	for(int i = 0; i < str.length(); ++i)
    	{
    		str1 = str1.deleteCharAt(i);
    		if(dict.isContained(str1.toString()))
    			suggestions.add(str1.toString());
    		str1 = new StringBuilder(og.toString());

    	}
    }
    
    /*
     * the third method to create suggestion words
     * @param str
     */
    private void thirdMethod(String str)
    {	
    	for(int i = 0; i < str.length() - 1; ++i)
    	{
    		for(int j = 1; j < str.length(); ++j)
    		{
    			String tmp = swap(str, i, j);
    			if(dict.isContained(tmp))
    				suggestions.add(tmp);
    			tmp = swap(str, i, j);
    		}
    	}
    }
    
    /*
     * method to swap two letters in a string
     * A helper method for method thirdMethod
     * @param str, i, j
     * @return string
     */
    private String swap(String str, int i, int j)
    {
    	char ch[] = str.toCharArray();
    	char tmp = ch[i];
    	ch[i] = ch[j];
    	ch[j] = tmp;
    	String string = new String(ch);
    	return string;
    }

}