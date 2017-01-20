package pongSpezial.netController;

import java.io.IOException;
import java.util.Dictionary;
import java.util.List;

import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import pongSpezial.dataModel.Ball;
import pongSpezial.dataModel.Bar;
import pongSpezial.dataModel.BoardState;
import pongSpezial.dataModel.Edge;
import pongSpezial.dataModel.Geometry;
//import pongSpezial.view.GUI;
import pongSpezial.view.State;

public class GUI {

	private final String SPLASH_SCREEN_PATH = "/pongSpezial/view/SplashScreen.fxml";
	private final String MAINMENU_SCREEN_PATH = "/pongSpezial/view/MainMenu.fxml";
	private final String NAMEENTRY_SCREEN_PATH = "/pongSpezial/view/Nameentry.fxml";
	private final String HOSTANDJOIN_SCREEN_PATH = "/pongSpezial/view/HostandJoin.fxml";
	private final String SPCONFIG_SCREEN_PATH = "/pongSpezial/view/LobbyGUIsp.fxml";// SpConfig.fxml
	private final String MPCONFIG_SCREEN_PATH = "/pongSpezial/view/LobbyGUImp.fxml";// MpConfig.fxml
	private final String MPJOIN_SCREEN_PATH = "/pongSpezial/view/LobbyGUImpJoin.fxml";// MpJoin.fxml
	private final String LOBBY_SCREEN_PATH = "/pongSpezial/view/LobbyScreen.fxml";
	private final String GAME_SCREEN_PATH = "/pongSpezial/view/GameScreen.fxml";
	
	private double scaleFactor = 1.0;

	// SplashScreen
	@FXML
	private Button btn_firstStart;
	
	// MainMenu
	@FXML
	private Button btn_sp;
	@FXML
	private Button btn_mp;
	@FXML
	private Button btn_quit;

	// NameEntry
	@FXML
	private Button btn_nameConfirm;
	@FXML
	private TextField txf_nameEntry;

	// HostAndJoin
	@FXML
	private Button btn_selectHost;
	@FXML
	private Button btn_selectJoin;
	@FXML
	private PasswordField pwf_password;
	@FXML
	private TextField txf_ip;

	// LobbyGUIsp
	@FXML
	private Button btn_startGameSP;
	@FXML
	private RadioButton pl1;
	@FXML
	private RadioButton pl2;
	@FXML
	private RadioButton pl3;
	@FXML
	private Slider slBoardSizeSP;

	// LobbyGUImp
	private Button btn_startGameMP;
	@FXML
	private Slider slBoardSizeMP;

	// GameScreen
	@FXML
	private AnchorPane gamePane;
	@FXML
	private Circle ball;
	@FXML
	private Rectangle sp1;
	@FXML
	private Rectangle sp1b;
	@FXML
	private Rectangle sp2;
	@FXML
	private Rectangle sp2b;
	@FXML
	private Rectangle sp3;
	@FXML
	private Rectangle sp3b;
	@FXML
	private Rectangle sp4;
	@FXML
	private Rectangle sp4b;
	@FXML
	private Rectangle bar1;
	@FXML
	private Rectangle bar2;
	@FXML
	private Rectangle bar3;
	@FXML
	private Rectangle bar4;
	@FXML
	private Rectangle co1;
	@FXML
	private Rectangle co11;
	@FXML
	private Rectangle co2;
	@FXML
	private Rectangle co21;
	@FXML
	private Rectangle co3;
	@FXML
	private Rectangle co31;
	@FXML
	private Rectangle co4;
	@FXML
	private Rectangle co41;
	@FXML
	private Button closeGamescreen;
	@FXML
	private ToggleButton soundToggle;
	
	private boolean firstStart = true;

	private State prevState;
	private State state;
	private boolean isSinglePlayer;
	private Dictionary<Integer, Color> playerColors;
	private String name;
	private final Client client;

	private FXMLLoader loader;

	public GUI() {
		this.client=null;
	}

	public GUI(State state, Client client, FXMLLoader loader, Stage primaryStage) {
		this.state = state;
		this.client = client;
		this.loader = loader;
		System.out.println(client.toString());

	}

	@FXML
	public void closeScreen(ActionEvent event) {
		// Close the game and switch to another Screen
	}

	@FXML
	public void switchOnOff(ActionEvent event) {
		// Turn the sound on or off
	}

	@FXML
	public void quit() {
		System.exit(0);

	}

	@FXML
	public void click(ActionEvent e) throws IOException {
		Control c = (Button) e.getSource();
		Stage primaryStage = (Stage) c.getScene().getWindow();

		prevState = state;

		if (c.getId().toString().equals("btn_firstStart")) {
			state = State.NAMEENTRY;

		}

		if (c.getId().toString().equals("btn_nameConfirm")) {
			state = State.MAINMENU;
		}

		if (c.getId().toString().equals("btn_sp")) {
			state = State.SPCONFIG;
		}

		if (c.getId().toString().equals("btn_mp")) {
			state = State.HOSTANDJOIN;
		}

		if (c.getId().toString().equals("btn_selectHost")) {
			state = State.MPCONFIG;
		}

		if (c.getId().toString().equals("btn_startGameMP")) {
			
			state = State.GAME;
			Client.instance.boardsize = slBoardSizeMP.getValue();

		}

		if (c.getId().toString().equals("btn_startGameSP")) {
			state = State.GAME;
			Client.instance.boardsize = slBoardSizeSP.getValue();
		}

		switchScreen(primaryStage);

	}

	public void switchScreen(Stage primaryStage) {
		try {

			switch (state) {
			case SPLASH:

				showScreenType(primaryStage, SPLASH_SCREEN_PATH, "Splashscreen");

				break;

			case MAINMENU:

				showScreenType(primaryStage, MAINMENU_SCREEN_PATH, "Mainmenu");

				break;

			case NAMEENTRY:

				showScreenType(primaryStage, NAMEENTRY_SCREEN_PATH, "title");

				break;

			case HOSTANDJOIN:

				showScreenType(primaryStage, HOSTANDJOIN_SCREEN_PATH, "title");

				break;

			case SPCONFIG:

				showScreenType(primaryStage, SPCONFIG_SCREEN_PATH, "Singleplayer");

				break;

			case MPCONFIG:

				showScreenType(primaryStage, MPCONFIG_SCREEN_PATH, "Multiplayer");

				break;

			case MPJOIN:

				showScreenType(primaryStage, MPJOIN_SCREEN_PATH, "title");

				break;

			case LOBBY:

				showScreenType(primaryStage, LOBBY_SCREEN_PATH, "title");

				if (prevState != state.MPJOIN) {
					// Man ist entweder MPHost oder SinglePlayer
					// Server muss gestartet werden
					client.startServer();
				}

				break;

			case GAME:

				showScreenType(primaryStage, GAME_SCREEN_PATH, "title");

				break;

			default:
				break;
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void showScreenType(Stage primaryStage, String screenPath, String title) throws IOException
	{

		FXMLLoader loader = new FXMLLoader(getClass().getResource(screenPath));
		Parent root = loader.load();
		primaryStage.setScene(new Scene(root));
		primaryStage.setTitle(title);
		primaryStage.show();

		// Bind Key Events - example
		
		if (state == State.GAME)
		{			
			keyInput(primaryStage);
			scaleWindow(primaryStage);
			
			Client.instance.run(primaryStage);
		}

	}
	
	private void getGamescreenFXMLControlReferences(Stage primaryStage)
	{
		gamePane 		= (AnchorPane)   primaryStage.getScene().lookup("#gamePane"); 		
		ball 			= (Circle)	     primaryStage.getScene().lookup("#ball"); 				
		sp1 			= (Rectangle)    primaryStage.getScene().lookup("#sp1"); 				
		sp1b 			= (Rectangle)    primaryStage.getScene().lookup("#sp1b"); 				
		sp2 			= (Rectangle)    primaryStage.getScene().lookup("#sp2"); 				
		sp2b 			= (Rectangle)    primaryStage.getScene().lookup("#sp2b"); 				
		sp3 			= (Rectangle)    primaryStage.getScene().lookup("#sp3"); 				
		sp3b 			= (Rectangle)    primaryStage.getScene().lookup("#sp3b"); 				
		sp4 			= (Rectangle)    primaryStage.getScene().lookup("#sp4"); 				
		sp4b 			= (Rectangle)    primaryStage.getScene().lookup("#sp4b"); 				
		bar1 			= (Rectangle)    primaryStage.getScene().lookup("#bar1"); 				
		bar2 			= (Rectangle)    primaryStage.getScene().lookup("#bar2"); 				
		bar3 			= (Rectangle)    primaryStage.getScene().lookup("#bar3"); 				
		bar4 			= (Rectangle)    primaryStage.getScene().lookup("#bar4"); 				
		co1 			= (Rectangle)    primaryStage.getScene().lookup("#co1"); 				
		co11 			= (Rectangle)    primaryStage.getScene().lookup("#co11"); 				
		co2 			= (Rectangle)    primaryStage.getScene().lookup("#co2"); 				
		co21 			= (Rectangle)    primaryStage.getScene().lookup("#co21"); 				
		co3 			= (Rectangle)    primaryStage.getScene().lookup("#co3"); 				
		co31 			= (Rectangle)    primaryStage.getScene().lookup("#co31"); 				
		co4 			= (Rectangle)    primaryStage.getScene().lookup("#co4"); 				
		co41 			= (Rectangle)    primaryStage.getScene().lookup("#co41"); 				
		closeGamescreen = (Button)       primaryStage.getScene().lookup("#closeGamescreen"); 	
		soundToggle 	= (ToggleButton) primaryStage.getScene().lookup("#soundToggle"); 

	}

	public void keyInput(Stage primaryStage) {
		System.out.println("In methode");

		// Stage primaryStage = (Stage) closeGamescreen.getScene().getWindow();

		System.out.println(primaryStage.toString());

		primaryStage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				System.out.println("press");
				Client.instance.validateInput(event);// hier werden Nullppinter geworfen
			}
		});

		primaryStage.getScene().setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				System.out.println("release");
				Client.instance.validateInput(event);// hier werden Nullppinter geworfen
				
				getGamescreenFXMLControlReferences(primaryStage);
				System.out.println(bar1.getLayoutX() + "/" + bar1.getLayoutY());
				bar1.setLayoutX(bar1.getLayoutX() + 10);
				bar1.setLayoutY(bar1.getLayoutY() + 10);

			}
		});

	}
	
	public void updateGUI(BoardState boardstate, Stage primaryStage)
	{
		getGamescreenFXMLControlReferences(primaryStage);
		
		List<Geometry> list = boardstate.getGeometries();
		
		Rectangle[] rectangle={sp1,sp1b,sp2,sp2b,sp3,sp3b,sp4,sp4b,co1,co2,co3,co4,co11,co21,co31,co41};
		if(firstStart)
		{			
			scaleObjects();
			
			for (Geometry geometry : list)
			{
				Point2D pos = geometry.getPosition();
				Point2D dim = geometry.getCollisionSize();
				
				if(geometry instanceof Bar)
				{
					Bar bar = (Bar) geometry;
					
					int id = bar.getControllingPlayer().getPlayerID();
					
					System.out.println("bar " + id + " pos: " + pos.getX() + "/" + pos.getY());
					System.out.println("bar " + id + " dim: " + dim.getX() + "/" + dim.getY());
					
					switch (id)
					{
						case 1 :
							bar1.setLayoutX(0);
							bar1.setLayoutY(0);
							bar1.setHeight(bar.getCollisionSize().getX()
									* scaleFactor);
							bar1.setWidth(bar.getCollisionSize().getY()
									* scaleFactor);
							break;

						case 2 :
							bar2.setLayoutX(100);
							bar2.setLayoutY(10);
							bar2.setHeight(bar.getCollisionSize().getX()
									* scaleFactor);
							bar2.setWidth(bar.getCollisionSize().getY()
									* scaleFactor);
							break;

						case 3 :
							bar3.setLayoutX(pos.getX() * scaleFactor);
							bar3.setLayoutY(pos.getY() * scaleFactor);
							bar3.setHeight(bar.getCollisionSize().getX()
									* scaleFactor);
							bar3.setWidth(bar.getCollisionSize().getY()
									* scaleFactor);
							break;

						case 4 :
							bar4.setLayoutX(pos.getX() * scaleFactor);
							bar4.setLayoutY(pos.getY() * scaleFactor);
							bar4.setHeight(bar.getCollisionSize().getX()
									* scaleFactor);
							bar4.setWidth(bar.getCollisionSize().getY()
									* scaleFactor);
							break;

						default :
							break;
					}
				}
				
				else if(geometry instanceof Ball)
				{
					ball.setLayoutX(pos.getX() * scaleFactor);
					ball.setLayoutY(pos.getY() * scaleFactor);
					ball.setRadius(((Ball) geometry).getRadius() * scaleFactor);
				}
				
				if(geometry instanceof Edge)
				{
					for(int i=0; i<rectangle.length; i++)
					{

						rectangle[i].setLayoutX(((Edge) geometry).getPosition().getX() * scaleFactor);
						rectangle[i].setLayoutY(((Edge) geometry).getPosition().getY() * scaleFactor);
						rectangle[i].setHeight( ((Edge) geometry).getCollisionSize().getY() * scaleFactor);
						rectangle[i].setWidth(  ((Edge) geometry).getCollisionSize().getX() * scaleFactor);
						//if(((Edge) geometry).isEdgeVisible() == false)
						//{
						//	fadeOutBars(rectangle[i]);
						//}

					}

				}
			}

			firstStart=false;
		}
		else if(false)
		{
			for (Geometry geometry : list)
			{
				Point2D test = geometry.getPosition();
				if(geometry instanceof Bar)
				{
					int id = ((Bar) geometry).getControllingPlayer().getPlayerID();
					switch (id)
					{
					case 1:
						bar1.setLayoutX(test.getX() * scaleFactor);
						bar1.setLayoutY(test.getY() * scaleFactor);
						bar1.setHeight(((Bar) geometry).getWidth() * scaleFactor);
						if(((Bar) geometry).getControllingPlayer().getLifes()==0)
						{
							//fadeOutBars(bar1);
							//fadeInBars(sp1);
						}
					break;
					
					case 2:
						bar2.setLayoutX(test.getX() * scaleFactor);
						bar2.setLayoutY(test.getY() * scaleFactor);
						bar2.setHeight(((Bar) geometry).getWidth() * scaleFactor);
						if(((Bar) geometry).getControllingPlayer().getLifes()==0)
						{
							//fadeOutBars(bar2);
							//fadeInBars(sp2);
						}
					break;
					
					case 3:
						bar3.setLayoutX(test.getX() * scaleFactor);
						bar3.setLayoutY(test.getY() * scaleFactor);
						bar3.setWidth(((Bar) geometry).getWidth() * scaleFactor);
						if(((Bar) geometry).getControllingPlayer().getLifes()==0)
						{
							//fadeOutBars(bar3);
							//fadeInBars(sp3);
						}
					break;
					case 4:
						bar4.setLayoutX(test.getX() * scaleFactor);
						bar4.setLayoutY(test.getY() * scaleFactor);
						bar4.setWidth(((Bar) geometry).getWidth() * scaleFactor);
						if(((Bar) geometry).getControllingPlayer().getLifes()==0)
						{
							//fadeOutBars(bar4);
							//fadeInBars(sp4);
						}
					break;
					default:
						break;
					}
				}
				if(geometry instanceof Ball)
				{
					ball.setLayoutX(test.getX() * scaleFactor);
					ball.setLayoutY(test.getY() * scaleFactor);
					ball.setRadius(((Ball) geometry).getRadius() * scaleFactor);
				}
			}
		}
	}
					
							
	public void fadeOutBars(Rectangle name)
	{
		FadeTransition fade = new FadeTransition(Duration.millis(1000), name);
		fade.setFromValue(1.0);
		fade.setToValue(0);
		fade.setAutoReverse(true);

		fade.play();
	}
	
	public void fadeInBars(Rectangle name)
	{
		FadeTransition fade = new FadeTransition(Duration.millis(1000), name);
		fade.setFromValue(0);
		fade.setToValue(1.0);
		fade.setAutoReverse(true);

		fade.play();
	}
	
	private void switchOnOff()
	{
		
		
	}
	
	
	private void closeScreen()
	{
		Stage stage = (Stage) closeGamescreen.getScene().getWindow();
		stage.close();
	}
	
	
	
	private void scaleWindow(Stage primaryStage)
	{
		//maximize window
		primaryStage.setMaximized(true);
	}
	
	private void scaleObjects()
	{
		double gamePaneSize = gamePane.getHeight();
		scaleFactor = gamePaneSize / Client.instance.boardsize;
		
		System.out.println("gamePaneSize / Client.instance.boardsize = scaleFactor:");
		System.out.println(gamePaneSize + " / " + Client.instance.boardsize + " = " + scaleFactor);

	}
	

}
