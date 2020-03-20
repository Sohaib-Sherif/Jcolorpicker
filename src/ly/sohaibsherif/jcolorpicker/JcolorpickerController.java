package ly.sohaibsherif.jcolorpicker;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;

public class JcolorpickerController {
	private Stage window;
	private int valuered;
	private int valuegreen;
	private int valueblue;
	/*
	 * for the dragging thing i took from the Internet.
	 */
	//---------------------------------------------------
	private double startMoveX = -1, startMoveY = -1;
	private Boolean dragging = false;
	private Rectangle moveTrackingRect;
	private Popup moveTrackingPopup;
	// --------------------------------------------------
	@FXML
    private Button Minimizebutton;

    @FXML
    private Button Closebutton;

    @FXML
    private Slider Sliderblue;

    @FXML
    private Slider Slidergreen;

    @FXML
    private Slider Sliderred;
    
    @FXML
    private TextField displayhex;

    @FXML
    private TextField displayrgb;

    @FXML
    private TextField redvalue;

    @FXML
    private TextField greenvalue;

    @FXML
    private TextField bluevalue;
    
    @FXML
    private Label hex;
    
    @FXML
    private Label rgb;
    
    public void close(ActionEvent e) {
    	window = (Stage)Closebutton.getScene().getWindow();
    	window.close();
    }
    public void minimize(ActionEvent e) {
    	window = (Stage)Closebutton.getScene().getWindow();
    	window.setIconified(true);// there's a problem if I immediately minimize the app before moving the slides. 
    }
    
    /**
     * taken from {@link: https://courses.bekwam.net/public_tutorials/bkcourse_flatwinapp_2.html}
     * to be honest I didn't read it nor understand it thoroughly,I made the changes that will make it work
     * I shamelessly copy-pasted it because I didn't have time to research and do it myself,but I know that I
     * need to get and change the x&y of the stage
     * I might seriously implement my first idea which is to have a normal fixed place in the corner of the screen.
     * @param evt
     */
    public void startMoveWindow(MouseEvent evt) {

    	  startMoveX = evt.getScreenX();
    	  startMoveY = evt.getScreenY();
    	  dragging = true;

    	  moveTrackingRect = new Rectangle();
    	  moveTrackingRect.setWidth(Closebutton.getScene().getWidth());
    	  moveTrackingRect.setHeight(Closebutton.getScene().getHeight());
    	  moveTrackingRect.getStyleClass().add( "tracking-rect" );

    	  moveTrackingPopup = new Popup();
    	  moveTrackingPopup.getContent().add(moveTrackingRect);
    	  moveTrackingPopup.show(Closebutton.getScene().getWindow());
    	  moveTrackingPopup.setOnHidden( (e) -> resetMoveOperation());
    	}

    	private void resetMoveOperation() {
    	  startMoveX = 0;
    	  startMoveY = 0;
    	  dragging = false;
    	  moveTrackingRect = null;
    	}
    	
    	public void moveWindow(MouseEvent evt) {

    		  if (dragging) {

    		    double endMoveX = evt.getScreenX();
    		    double endMoveY = evt.getScreenY();

    		    Window w = Closebutton.getScene().getWindow();

    		    double stageX = w.getX();
    		    double stageY = w.getY();

    		    moveTrackingPopup.setX(stageX + (endMoveX - startMoveX));
    		    moveTrackingPopup.setY(stageY + (endMoveY - startMoveY));
    		  }
    		}
    	
    	public void endMoveWindow(MouseEvent evt) {

    		  if (dragging) {
    		    double endMoveX = evt.getScreenX();
    		    double endMoveY = evt.getScreenY();

    		    Window w = Closebutton.getScene().getWindow();

    		    double stageX = w.getX();
    		    double stageY = w.getY();

    		    w.setX(stageX + (endMoveX - startMoveX));
    		    w.setY(stageY + (endMoveY - startMoveY));

    		    if (moveTrackingPopup != null) {
    		      moveTrackingPopup.hide();
    		      moveTrackingPopup = null;
    		    }
    		  }

    		  resetMoveOperation();
    		}
		
    	
    	public void Slider(MouseEvent e) {
    		valuered = (int) Sliderred.getValue();
    		valuegreen = (int) Slidergreen.getValue();
    		valueblue = (int) Sliderblue.getValue();
    		redvalue.setText((Integer.toString((valuered))));
    		greenvalue.setText((Integer.toString((valuegreen))));
    		bluevalue.setText((Integer.toString((valueblue))));
    		//TODO make a color(rgb) variable here, assign values,then set the scene.setfill or something.hope it works
    		displayrgb.setText("("+ Integer.toString(valuered)+","+Integer.toString(valuegreen)+","+
    						   Integer.toString(valueblue)+")");
    		/*Color color = Color.rgb(valuered, valuegreen, valueblue); // no need any more.
    		 * displayhex.setText(String.format( "#%02X%02X%02X",
    	            (int)( color.getRed() * 255 ),
    	            (int)( color.getGreen() * 255 ),
    	            (int)( color.getBlue() * 255 ) ));*/
    		/*
    		 * displayhex.setText("#"+Integer.toHexString(valuered).toUpperCase()
    		 * Integer.toHexString(valuegreen).toUpperCase()+
    		 * Integer.toHexString(valueblue).toUpperCase())
    		 * Even though both approaches work, the latter give 000 when the value of colors are 0 0 0 
    		 * but that means I will have to use a format to correct it's display 
    		 */
    		displayhex.setText(String.format("#%02X%02X%02X",valuered,valuegreen,valueblue));// My work.best
    		colorChanger();
    		
    	}
    	
    	public void colorChanger() {
    		/*Paints the background */
    		Closebutton.getScene().getRoot().setStyle("-fx-background-color:"+
    				String.format("#%02X%02X%02X",valuered,valuegreen,valueblue));
    		
    		changebuttons();
    		changehexcolor();
    		changetextcolor();
    	}
    	
    	public void changehexcolor(){
    		if (valuered <= 150 && valuegreen <= 45) {
    			hex.setStyle("-fx-text-fill:white");
    			rgb.setStyle("-fx-text-fill:white");
    		}
    		else if (valuered <= 150 && valuegreen == 0 && valueblue == 0) {
    			hex.setStyle("-fx-text-fill:white");
    			rgb.setStyle("-fx-text-fill:white");
    		}
    		else {
    			hex.setStyle("-fx-text-fill:black");
    			rgb.setStyle("-fx-text-fill:black");
    		}
    	}

    	public void changetextcolor() {
    		String text =
    				"-fx-background-color:"+
    				String.format("#%02X%02X%02X",valuered,valuegreen,valueblue)+";"+
    				"-fx-text-fill:white;";
    		String textblack =
    				"-fx-background-color:"+
    	    		String.format("#%02X%02X%02X",valuered,valuegreen,valueblue)+";"+
    	    		"-fx-text-fill:black;";
    		
    		if (valuered <= 150 && valuegreen <= 45) {
    			displayhex.setStyle(text);
    			displayrgb.setStyle(text);
    			redvalue.setStyle(text);
    			greenvalue.setStyle(text);
    			bluevalue.setStyle(text);
    		}
    		else if (valuered <= 150 && valuegreen == 0 && valueblue == 0) {
    			displayhex.setStyle(text);
    			displayrgb.setStyle(text);
    			redvalue.setStyle(text);
    			greenvalue.setStyle(text);
    			bluevalue.setStyle(text);
    		}
    		else {
    			displayhex.setStyle(textblack);
    			displayrgb.setStyle(textblack);	
    			redvalue.setStyle(textblack);
    			greenvalue.setStyle(textblack);
    			bluevalue.setStyle(textblack);
    		}
    	}
    	
    	public void changebuttons() {
    		int redinverse = 255 - valuered;
    		int greeninverse = 255 - valuegreen;
    		int blueinverse = 255 - valueblue;
    		String buttonnormal =
    				"-fx-body-color:"+
    				String.format("#%02X%02X%02X",valuered,valuegreen,valueblue)+";"+
    				"-fx-inner-border:"+
    				String.format("#%02X%02X%02X",valuered,valuegreen,valueblue)+";"+
    				"-fx-shadow-highlight-color:"+
    				String.format("#%02X%02X%02X",valuered,valuegreen,valueblue)+";"+
    				"-fx-text-fill:black;";
    		String buttoninversed = 
    				"-fx-body-color:"+
    				String.format("#%02X%02X%02X",redinverse,greeninverse,blueinverse)+";"+
    				"-fx-inner-border:"+
    				String.format("#%02X%02X%02X",redinverse,greeninverse,blueinverse)+";"+
    				"-fx-shadow-highlight-color:"+
    				String.format("#%02X%02X%02X",valuered,valuegreen,valueblue);//so there don't be a different color
    		String blackbackground =
    				"-fx-body-color:"+
    				String.format("#%02X%02X%02X",valuered,valuegreen,valueblue)+";"+
    				"-fx-shadow-highlight-color:"+
    				String.format("#%02X%02X%02X",valuered,valuegreen,valueblue)+";"+
    				"-fx-outer-border:"+
    				String.format("#%02X%02X%02X",valuered,valuegreen,valueblue)+";"+
    				"-fx-text-fill:white;";
    		String blackbackgroundhover =
    				"-fx-body-color:"+
    				String.format("#%02X%02X%02X",redinverse,greeninverse,blueinverse)+";"+
    				"-fx-inner-border:"+
    				String.format("#%02X%02X%02X",redinverse,greeninverse,blueinverse)+";"+
    				"-fx-text-fill:black;"+
    				"-fx-shadow-highlight-color:"+
    				String.format("#%02X%02X%02X",valuered,valuegreen,valueblue)+";";
    		String blueonly = 
    				"-fx-body-color:"+
    	    		String.format("#%02X%02X%02X",valuered,valuegreen,valueblue)+";"+
    				"-fx-shadow-highlight-color:"+
    	    		String.format("#%02X%02X%02X",valuered,valuegreen,valueblue)+";"+
    	    		"-fx-outer-border:"+
    				String.format("#%02X%02X%02X",valuered,valuegreen,valueblue)+";"+
    	    		"-fx-text-fill:white;";
    		String blueonlyhover = 
    				"-fx-body-color:"+ 
    				String.format("#%02X%02X%02X",redinverse,greeninverse,blueinverse)+";"+
    				"-fx-shadow-highlight-color:"+
    				String.format("#%02X%02X%02X",valuered,valuegreen,valueblue)+";"+
    				"-fx-inner-border:"+
    				String.format("#%02X%02X%02X",redinverse,greeninverse,blueinverse)+";"+
    				"-fx-text-fill:black;";
    		String redonly =
    				"-fx-body-color:"+
    	    	    String.format("#%02X%02X%02X",valuered,valuegreen,valueblue)+";"+
    	    		"-fx-shadow-highlight-color:"+
    	    	    String.format("#%02X%02X%02X",valuered,valuegreen,valueblue)+";"+
    	    	    "-fx-outer-border:"+
    	    		String.format("#%02X%02X%02X",valuered,valuegreen,valueblue)+";"+
    	    	    "-fx-text-fill:white;";
    		String redonlyhover = 
    				"-fx-body-color:"+ 
    	    		String.format("#%02X%02X%02X",redinverse,greeninverse,blueinverse)+";"+
    	    		"-fx-shadow-highlight-color:"+
    	    		String.format("#%02X%02X%02X",valuered,valuegreen,valueblue)+";"+
    	    		"-fx-inner-border:"+
    	    		String.format("#%02X%02X%02X",redinverse,greeninverse,blueinverse)+";"+
    	    		"-fx-text-fill:black;";
    		if(valuered <= 85 && valuegreen <= 85 && valueblue <= 85){
    			Closebutton.setStyle(blackbackground);
    			Closebutton.setOnMouseEntered(e -> Closebutton.setStyle(blackbackgroundhover)); 
    			Closebutton.setOnMouseExited(e -> Closebutton.setStyle(blackbackground));
    			Minimizebutton.setStyle(blackbackground);
    			Minimizebutton.setOnMouseEntered(e -> Minimizebutton.setStyle(blackbackgroundhover));
    			Minimizebutton.setOnMouseExited(e -> Minimizebutton.setStyle(blackbackground));
    		}
    		else if (valuered == 0 && valuegreen == 0) {//test later after finishing rest
    			Closebutton.setStyle(blueonly);
    			Closebutton.setOnMouseEntered(e -> Closebutton.setStyle(blueonlyhover)); 
    			Closebutton.setOnMouseExited(e -> Closebutton.setStyle(blueonly));
    			Minimizebutton.setStyle(blueonly);
    			Minimizebutton.setOnMouseEntered(e -> Minimizebutton.setStyle(blueonlyhover));
    			Minimizebutton.setOnMouseExited(e -> Minimizebutton.setStyle(blueonly));
    		}
    		else if (valuered <= 150 && valuegreen == 0 && valueblue == 0) {
    			Closebutton.setStyle(redonly);
    			Closebutton.setOnMouseEntered(e -> Closebutton.setStyle(redonlyhover)); 
    			Closebutton.setOnMouseExited(e -> Closebutton.setStyle(redonly));
    			Minimizebutton.setStyle(redonly);
    			Minimizebutton.setOnMouseEntered(e -> Minimizebutton.setStyle(redonlyhover));
    			Minimizebutton.setOnMouseExited(e -> Minimizebutton.setStyle(redonly));
    		}
    		else if (valuered <= 150 && valuegreen <= 45) {
    			Closebutton.setStyle(redonly);
    			Closebutton.setOnMouseEntered(e -> Closebutton.setStyle(redonlyhover)); 
    			Closebutton.setOnMouseExited(e -> Closebutton.setStyle(redonly));
    			Minimizebutton.setStyle(redonly);
    			Minimizebutton.setOnMouseEntered(e -> Minimizebutton.setStyle(redonlyhover));
    			Minimizebutton.setOnMouseExited(e -> Minimizebutton.setStyle(redonly));
    		}
    		else {	
    			Closebutton.setStyle(buttonnormal);
    			Closebutton.setOnMouseEntered(e -> Closebutton.setStyle(buttoninversed));// nice lambda.
    			Closebutton.setOnMouseExited(e -> Closebutton.setStyle(buttonnormal));// return everything to normal
    			Minimizebutton.setStyle(buttonnormal);
    			Minimizebutton.setOnMouseEntered(e -> Minimizebutton.setStyle(buttoninversed));
    			Minimizebutton.setOnMouseExited(e -> Minimizebutton.setStyle(buttonnormal));
    		}
    	}
    		//TODO fix problems tomorrow,almost done

    	
    	
    	@FXML // this removes the null pointer issue
    	public void initialize() { //I was able to use color changer after I changed my initialize.
    		
    		int maxValue = 255;
    		setValuered(255);
    		setValuegreen(255);
    		setValueblue(255);
    		/*
    		 * May be I should change things here and put it in another method to make things less repetitive
    		 * you know, may be a method that takes a string(newValue and then do the if statements,i think i should 
    		 * use a get slider and set slider with that. 
    		 */
    		/**
    		 * The following is brought from
    		 * @link {https://gist.github.com/jewelsea/1962045}
    		 */
    		redvalue.textProperty().addListener(new ChangeListener<String>() {		 

    			@Override
    			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    				if(newValue.length() <=3) {
    					if(newValue == null || "".equals(newValue)) {//FINALLY!!!
    						/*So I don't forget,""for empty string*/
    						
    						Sliderred.setValue(0);
    						valuered = (int)Sliderred.getValue();
    						
    						redvalue.focusedProperty().addListener(new ChangeListener<Boolean>() {

								@Override
								public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
										Boolean newValue) {
										if("".equalsIgnoreCase(redvalue.getText()))
											redvalue.setText("0");//working,but may be I should put it outside the if.									
								}
    						});
    					}
    					else if(Integer.parseInt(newValue) > maxValue) {
    						redvalue.setText("255");
    						Sliderred.setValue(255);
    						valuered = (int)Sliderred.getValue();
    					}
    					else if(Integer.parseInt(newValue) <= 0) {
    						redvalue.setText("0");
    						Sliderred.setValue(0);
    						valuered = (int)Sliderred.getValue();
    					}
    					else {
    						try {
    							Sliderred.setValue(Double.parseDouble(newValue));
    						}catch(IllegalArgumentException e) {
    							Sliderred.setValue(255);
    						}
    						valuered = (int)Sliderred.getValue();
    					}
    				}
    				else {
    					Sliderred.setValue(255);
    					redvalue.setText("255");
    				}
    				 colorChanger();
    			}
    		}); 
    		redvalue.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>(){

				@Override
				public void handle(KeyEvent event) {
					 if (!"0123456789".contains(event.getCharacter())) {
				            event.consume();
				          }					
				}
    			
    		});
    		
    		greenvalue.textProperty().addListener(new ChangeListener<String>() {

    			@Override
    			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    				if(newValue.length() <=3) {
    					if(newValue == null || "".equals(newValue)) {

    						Slidergreen.setValue(0);
    						valuegreen = (int)Slidergreen.getValue();
    						
    						greenvalue.focusedProperty().addListener(new ChangeListener<Boolean>() {

								@Override
								public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
										Boolean newValue) {
									if("".equalsIgnoreCase(greenvalue.getText()))
										greenvalue.setText("0");
																		
								}
    						});
    					}
    					else if(Integer.parseInt(newValue) > maxValue) {
    						greenvalue.setText("255");
    						Slidergreen.setValue(255);
    						valuegreen = (int)Slidergreen.getValue();
    					}
    					else if(Integer.parseInt(newValue) <= 0) {
    						greenvalue.setText("0");
    						Slidergreen.setValue(0);
    						valuegreen = (int)Slidergreen.getValue();
    					}
    					else {
    						try {
    							Slidergreen.setValue(Double.parseDouble(newValue));
    						}catch(IllegalArgumentException e) {
    							Slidergreen.setValue(255);
    						}
    						valuegreen = (int)Slidergreen.getValue();
    					}
    				}
    				else {
    					Slidergreen.setValue(255);
    					greenvalue.setText("255");
    				}
    				colorChanger();
    			}

    		});

    		greenvalue.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>(){

				@Override
				public void handle(KeyEvent event) {
					 if (!"0123456789".contains(event.getCharacter())) {
				            event.consume();
				          }					
				}
    		});
    		
    		bluevalue.textProperty().addListener(new ChangeListener<String>() {

    			@Override
    			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    				if (newValue.length() <= 3) {
    					if(newValue == null || "".equals(newValue)) {
    						
    						Sliderblue.setValue(0);
    						valueblue = (int)Sliderblue.getValue();
    						bluevalue.focusedProperty().addListener(new ChangeListener<Boolean>() {

								@Override
								public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
										Boolean newValue) {
									if("".equalsIgnoreCase(bluevalue.getText()))
										bluevalue.setText("0");									
								}
    						});
    					}
    					else if(Integer.parseInt(newValue) > maxValue) {
    						bluevalue.setText("255");
    						Sliderblue.setValue(255);
    						valueblue = (int)Sliderblue.getValue();
    					}
    					else if(Integer.parseInt(newValue) <= 0) {
    						bluevalue.setText("0");
    						Sliderblue.setValue(0);
    						valueblue = (int)Sliderblue.getValue();
    					}
    					else {
    						try {
    							Sliderblue.setValue(Double.parseDouble(newValue));
    						}catch(IllegalArgumentException e) {
    							Sliderblue.setValue(255);
    						}
    						valueblue = (int)Sliderblue.getValue();
    					}
    				}
    				else {
    					Sliderblue.setValue(255);
    					bluevalue.setText("255");
    				}
    				colorChanger();
    			}

    		});
    		
    		
    		bluevalue.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>(){

				@Override
				public void handle(KeyEvent event) {
					 if (!"0123456789".contains(event.getCharacter())) {
				            event.consume();
				          }					
				}
    		});
    	
    	}
		public int getValuered() {
			return valuered;
		}
		public void setValuered(int valuered) {
			this.valuered = valuered;
		}
		public int getValuegreen() {
			return valuegreen;
		}
		public void setValuegreen(int valuegreen) {
			this.valuegreen = valuegreen;
		}
		public int getValueblue() {
			return valueblue;
		}
		public void setValueblue(int valueblue) {
			this.valueblue = valueblue;
		}
    	
} 