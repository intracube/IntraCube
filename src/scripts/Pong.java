package scripts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JOptionPane;

import org.intracube.api.manifest.ScriptManifest;
import org.intracube.api.sprite.Sprite;
import org.intracube.script.Script;
import org.intracube.script.Task;

@ScriptManifest(
		authors = {"Aaron McClure"},
		version = 1.0,
		description = "Pong, by Aaron McClure!",
		name = "Pong"
)
public class Pong extends Script{

	private Sprite userPaddle = new Sprite(getURLImage("http://i1256.photobucket.com/albums/ii500/a_mcclure/PongResources/PongPanel-1.png"), new Point(0,100));
	private Sprite compPaddle = new Sprite(getURLImage("http://i1256.photobucket.com/albums/ii500/a_mcclure/PongResources/PongPanel-1.png"), new Point(710,20));
	private Sprite ball = new Sprite(getURLImage("http://i1256.photobucket.com/albums/ii500/a_mcclure/PongResources/PongBall-1.png"), new Point(370,195));

	private int compScore=0, userScore=0;

	private Task taskUser, taskComp;
	@Override
	public boolean init() {
		taskUser = new UserControl();
		taskComp = new CompControl();

		startTask(taskUser);
		startTask(taskComp);

		return (JOptionPane.showConfirmDialog(null, "Do you want to play pong?") == 0);
	}

	@Override
	public int loop() {
		moveBall();
		return 10;
	}

	public class UserControl extends Task{

		@Override
		public void run() {
			checkUserPaddle();
			sleep(10);
		}
	}

	public class CompControl extends Task{

		@Override
		public void run() {
			checkCompPaddle();
		}
	}

	private void checkUserPaddle(){
		if (input.getKeyPressed().equals("s")){
			userPaddle.setLocation(0, userPaddle.getLocation().y + 5);
		}else if (input.getKeyPressed().equals("w")){
			userPaddle.setLocation(0, userPaddle.getLocation().y - 5);
		}else if (input.isPressed()){
			log.show("Some other key was pressed! - " + input.getKeyPressed());
			log.show("Press 'w' to move up and 's' to move down");
		}
	}

	private void checkCompPaddle(){
		Point center = new Point(710, compPaddle.getLocation().y+(compPaddle.getImage().getHeight(null)/2));

		if (center.y>ball.getLocation().y){
			compPaddle.setLocation(710, compPaddle.getLocation().y - 5);
		}else if (center.y<ball.getLocation().y){
			compPaddle.setLocation(710, compPaddle.getLocation().y + 5);
		}
	}

	private int x = 370, y = 195;
	private int velX = 4, velY = 5;

	private void moveBall(){
		x += velX;
		y += velY;        

		if (collision.isCollision(userPaddle, ball)) {
			x+=8;
			velX *= -1;
		}

		if (collision.isCollision(compPaddle, ball)) {
			x-=8;
			velX *= -1;  
		}

		if (ball.getLocation().x <= 0){
			compScore++;
			resetBallLoc();
			x = 370;
			y = 195;
			velX = 4;
			velY = 5;
			sleep(1000);	
			log.show("Computer player won the point :(");
		} else if (ball.getLocation().x>=740){
			userScore++;
			resetBallLoc();
			x = 370;
			y = 195;
			velX = 4;
			velY = 5;
			sleep(1000);
			log.show("You won the point!");
		}

		if (y < 0) {   
			y = 0;
			velY *= -1;
		}else if (y > 535){  
			y = 535;
			velY *= -1;
		}
		
		ball.setLocation(new Point(x, y));
	}

	private void resetBallLoc(){
		ball.setLocation(370,195);
	}

	@Override
	public void onFinish() {
		log.show("Thank you for using my script!", Color.blue);
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.black);
		g.fillRect(0,0,getCanvasSize().width, getCanvasSize().height);

		g.setColor(Color.GREEN);
		g.setFont(new Font("sansserif", Font.BOLD, 32));
		g.drawString("Pong Game", 295, 30);
		g.setFont(new Font("sansserif", Font.PLAIN, 18));
		g.setColor(Color.yellow);
		g.drawString("Your score: " + userScore, 55, 20);
		g.drawString("Comp score: " + compScore, 560, 20);

		userPaddle.update(g);
		compPaddle.update(g);
		ball.update(g);
	}

}
