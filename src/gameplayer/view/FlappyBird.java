package gameplayer.view;

import gamedata.controller.CreatorController;
import gameengine.controller.Game;
import gameengine.controller.GameInfo;
import gameengine.controller.Level;
import gameengine.model.Actions.*;
import gameengine.model.Actor;
import gameengine.model.Attribute;
import gameengine.model.AttributeType;
import gameengine.model.Rule;
import gameengine.model.Triggers.*;
import gameplayer.controller.GameController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.ParallelCamera;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author blakekaplan
 */
public class FlappyBird extends Application {
    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     * <p>
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set. The primary stage will be embedded in
     *                     the browser if the application was launched as an applet.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages and will not be embedded in the browser.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        GameInfo info = new GameInfo();
        info.setMyCurrentLevelNum(0);
        info.setName("Flappy Bird");

        List<Level> levels = new ArrayList<>();

        Level level1 = new Level();
        level1.setMyBackgroundImgName("flappybackground.png");
        levels.add(level1);

        Actor pipeTop = new Actor();
        pipeTop.setID(10);
        pipeTop.setImageViewName("toppipe.png");
        pipeTop.addRule(new Rule(new TickTrigger(20), new MoveLeft(pipeTop)));

        Actor pipeBottom = new Actor();
        pipeBottom.setID(11);
        pipeBottom.setImageViewName("bottompipe.png");
        pipeBottom.addRule(new Rule(new TickTrigger(20), new MoveLeft(pipeBottom)));

        Actor invisibleLine = new Actor();
        invisibleLine.setID(12);
        invisibleLine.setImageViewName("redball.png");
        invisibleLine.addRule(new Rule(new TickTrigger(20), new MoveLeft(invisibleLine)));

        Actor gameSide = new Actor();
        gameSide.setID(13);
        gameSide.setImageViewName("gameside.png");

        Actor floor = new Actor();
        floor.setID(2);
        floor.setImageViewName("floor.png");
        floor.setY(275);

//        level1.addActor(pipeTop);
//        level1.addActor(pipeBottom);
//        level1.addActor(invisibleLine);

        level1.addRule(new Rule(new TickTrigger(75), new CreateActor(level1, pipeTop, 1024, 1024, -100, 0)));
        level1.addRule(new Rule(new TickTrigger(75), new CreateActor(level1, pipeBottom, 1024, 1024, 350, 400)));
        //level1.addRule(new Rule(new TickTrigger(75), new CreateActor(level1, invisibleLine, 1024, 1024, 200, 200)));

        Actor player = new Actor();
        player.addRule(new Rule(new KeyTrigger(KeyCode.SPACE), new MoveUp(player)));
        player.isMainPlayer();
        player.setID(1);
        player.setImageViewName("flappybird1.png");
        player.addSpriteImage("flappybird2.png");
        player.addSpriteImage("flappybird3.png");

        player.addRule(new Rule(new SideCollision(player,pipeTop), new LoseGame(player)));
        player.addRule(new Rule(new SideCollision(player,pipeTop), new LoseGame(player)));
        player.addRule(new Rule(new BottomCollision(player, pipeBottom), new LoseGame(level1)));
        player.addRule(new Rule(new SideCollision(player,pipeBottom), new LoseGame(player)));
        player.addRule(new Rule(new TickTrigger(), new ApplyPhysics(player)));
        player.addRule(new Rule(new SideCollision(player, invisibleLine), new ChangeAttribute(level1,AttributeType.POINTS,10)));
        player.addRule(new Rule(new TopCollision(player, floor), new MoveUp(player)));
        player.addRule(new Rule(new TickTrigger(5), new NextImage(player)));

        gameSide.addRule(new Rule(new SideCollision(gameSide, pipeTop), new Destroy(pipeTop)));
        gameSide.addRule(new Rule(new SideCollision(gameSide, pipeBottom), new Destroy(pipeBottom)));

        level1.addAttribute(new Attribute(AttributeType.POINTS,0,level1));


        level1.addActor(player);
        level1.addActor(floor);
        level1.addActor(gameSide);

        Group group = new Group();
        Scene scene = new Scene(group);

        Game model = new Game(info, levels);
        CreatorController c = new CreatorController(model);
        c.saveForEditing(new File("gamefiles/FlappyBird.xml"));
        ParallelCamera camera = new ParallelCamera();
        GameScreen view = new GameScreen(camera);

        GameController controller = new GameController(model);
        controller.setGame(model);
        controller.setGameView(view);

        SubScene sub = view.getScene();
        sub.fillProperty().set(Color.BLUE);
        group.getChildren().add(sub);

        Stage stage = new Stage();
        stage.setWidth(800);
        stage.setHeight(600);

        sub.setCamera(camera);
        stage.setScene(scene);
        stage.show();
        controller.initialize(0);
    }

    public static void main(String[] args){
        launch(args);
    }
}