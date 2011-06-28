package khiem.gwt.demo.hangman.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class HangMan implements EntryPoint {
  
  private FlowPanel letters = new FlowPanel();
  private Label wordLabel = new Label();
  private Label wrongLabel = new Label();
  private Image image = new Image();
  
  private final int MAX_GUESSES = 6;
  private int misses;
  private char[] word;
  private char[] visibleWord;
  private StringBuilder wrongWord;
  
  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    RootPanel hangman = RootPanel.get("main-panel");
    hangman.add(letters);
    hangman.add(image);
    hangman.add(wordLabel);
    hangman.add(wrongLabel);
    startGame();
        
  }
  
  public void startGame() {
    letters.clear();
    for (char letter = 'A'; letter <= 'Z'; letter++) {
      final Button button = new Button(Character.toString(letter));
      
      button.addClickHandler(new ClickHandler() {    
        @Override
        public void onClick(ClickEvent event) {
          button.setEnabled(false);
          guess(button.getText().charAt(0));
        }
      });
      
      letters.add(button);
      
    }
    
    // set initial image
    image.setUrl("images/hm1.jpg");
    setWord("environment");
  }
  
  public void guess(char letter) {
    boolean badGuess = true;
    boolean wordFinished = true;

    //check for matches for this letter
    for( int i = 0; i < word.length; i++ ) {
      if( word[i] == letter ) {
        visibleWord[i] = letter;
        badGuess = false;
      }
      else if(visibleWord[i]=='_') {
        wordFinished = false;
      }
    }
    
    wordLabel.setText( new String(visibleWord) );

    if(wordFinished) {
      Window.alert("Congratulations, you got the answer!");
      startGame();
    }
    else if (badGuess) {
      misses++;
      wrongWord.append(letter + " ");
      wrongLabel.setText(wrongWord.toString());
      image.setUrl("images/hm"+Integer.toString(misses+1)+".jpg");
      if(misses == MAX_GUESSES) {
        Window.alert("You ran out of guesses! The answer is "+ new String(word));
        startGame();
      }
    }
  }
  
  // set up a new word to guess
  public void setWord(String newWord) {
    word = newWord.toUpperCase().toCharArray();
    visibleWord = new char[word.length];
    for (int i = 0; i < word.length; i++){
      if (word[i] != ' ')
        visibleWord[i]='_';
      else
        visibleWord[i]=' ';
    }
    wrongWord = new StringBuilder();
    
    wordLabel.setText(new String(visibleWord));
    wrongLabel.setText("Wrong Letters: ");
  }
}
