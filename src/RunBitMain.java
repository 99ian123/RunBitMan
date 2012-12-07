import java.awt.*; // So java knows that is is a GUI
import javax.swing.*; // Buttons/panes/panels (etc)

import java.awt.event.*; // For event handling
public class RunBitMain extends JFrame implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//RunBitMan - Copyright Ian Millstein and Alok Tripathy 2012. All Rights Reserved. - Source Code is Here.

	//Graphics
	public Graphics dbg;
	public int headX = 110, headY = 253, bodyStartX = 115, bodyStartY = 263, bodyEndX = 115, bodyEndY = 278, armStartX = 110, armStartY = 272, armEndX = 120, armEndY = 272, rightLegEndX = 110, rightLegEndY = 285, leftLegEndX = 120, leftLegEndY = 285;
	public int monHeadX = 260, monHeadY = 256;
	public int SputHeadX = 350, SputHeadY = 200;
	public int blockX = 430;

	//Controls
	public boolean jumpKeyPressed, rightKeyPressed, leftKeyPressed, falling;
	public float velocityX = 5, velocityY = 5, gravity = 0.5f;
	public int counter;
	public int direction;
	public int direction1;

	//Screen
	public Image dbImage = null;
	public int dbHeight = 600, dbWidth = 600;
	public int score, life = 3;
	public Container c;

	//Program
	public boolean running;
	public Thread animator;

	public RunBitMain() {

		super( "RunBitMan Copyright Ian Millstein and Alok Tripathy 2012. All Rights Reserved." );
		c = getContentPane();
		c.setLayout( new FlowLayout() );
		startGame();
		KeyHandler handler = new KeyHandler();
		addKeyListener(  handler );
		setSize( 600, 600 );
		setVisible(true);

	}

	public void startGame() {

		if( animator == null || !running ) {
			animator = new Thread( this );
			animator.start();
		}
	}

	public void run() {

		running = true;

		try {
			JOptionPane.showMessageDialog( null, "Instructions:\nGet BitMan to the gold block without being touched by the monster!\n\nAre you up for the challenge?", "Prepare", JOptionPane.WARNING_MESSAGE ); 

		}

		catch( Exception e ) {}
		while( running ) {
			gameRender();
			paintScreen();

			try {
				Thread.sleep( 20 );
			}
			catch( Exception e ){}
		}
	}

	public void gameRender() {

		if( dbImage == null ) {
			dbImage = createImage( dbWidth, dbHeight );
			if( dbImage == null )
				return;
		}

		dbg = dbImage.getGraphics();

		dbg.setColor( Color.white ); // screen
		dbg.fillRect( 0, 0, dbWidth, dbHeight );

		dbg.setColor( Color.black );

		dbg.setColor( Color.blue ); // main platform
		dbg.fillRect(100, 287, 380, 26);

		dbg.setColor( Color.orange);
		dbg.fillRect( blockX, 272, 15, 15);

		dbg.setColor( Color.black ); // BitMan
		dbg.drawOval( headX, headY, 10, 10 );
		dbg.drawLine( bodyStartX, bodyStartY, bodyEndX, bodyEndY );
		dbg.drawLine( armStartX, armStartY, armEndX, armEndY );
		dbg.drawLine( bodyEndX, bodyEndY, leftLegEndX, leftLegEndY );
		dbg.drawLine( bodyEndX, bodyEndY, rightLegEndX, rightLegEndY );

		dbg.setColor( Color.green); // monster
		dbg.fillOval( monHeadX , monHeadY, 30, 30);

		dbg.setColor( Color.magenta ); // monster #2
		dbg.fillOval( SputHeadX, SputHeadY, 25, 25);

		dbg.setColor( Color.yellow );
		dbg.drawRect( monHeadX, monHeadY, 30, 30);

		dbg.setColor( Color.yellow );
		dbg.drawRect(SputHeadX, SputHeadY, 25, 25);

		if( jumpKeyPressed && counter <= 20) {
			headY -= velocityY;
			bodyStartY -= velocityY;
			bodyEndY -= velocityY;
			armStartY -= velocityY;
			armEndY -= velocityY;
			rightLegEndY -= velocityY;
			leftLegEndY -= velocityY;
			counter++;
		}

		else if( jumpKeyPressed && counter > 20 )
			jumpKeyPressed = false;


		if( !jumpKeyPressed )
			falling = true;
		else
			falling = false;


		if( rightKeyPressed ) {
			headX += velocityX;
			bodyStartX += velocityX;
			bodyEndX += velocityX;
			armStartX += velocityX;
			armEndX += velocityX;
			rightLegEndX += velocityX;
			leftLegEndX += velocityX;

		}

		if (leftKeyPressed) {
			headX -= velocityX;
			bodyStartX -= velocityX;
			bodyEndX -= velocityX;
			armStartX -= velocityX;
			armEndX -= velocityX;
			rightLegEndX -= velocityX;
			leftLegEndX -= velocityX;
		}

		if( headY >=  253 ) {
			headY = 253;
			falling = false;
			counter = 0;
		}


		if( bodyStartY >= 263 )
			bodyStartY = 263;


		if( bodyEndY >= 278 )
			bodyEndY = 278;


		if( armStartY >= 272 )
			armStartY = 272;


		if( armEndY >= 272 )
			armEndY = 272;


		if( rightLegEndY >= 285 )
			rightLegEndY = 285;


		if( leftLegEndY >= 285 )
			leftLegEndY = 285;


		if( falling ) {
			headY += velocityY;
			bodyStartY += velocityY;
			bodyEndY += velocityY;
			armStartY += velocityY;
			armEndY += velocityY;
			rightLegEndY += velocityY;
			leftLegEndY += velocityY;
		}

		dbg.setColor( Color.black );
		dbg.drawString( "Score: " + score, 100, 100 );
		dbg.drawString("Lives remaining: " + life, 100, 120);

		for( int i = 0; i <= 900; i++ ) {

			if(( armStartX <= monHeadX + 30 && armEndX >= monHeadX )) {
				if( rightLegEndY >= monHeadY && rightLegEndY <= monHeadY + 30) {

					lost();
					life--;

					if( life == 0 ) {
						JOptionPane.showMessageDialog( null, "You Lose!\nYour high score was: " + score + " points" , "Lost", JOptionPane.WARNING_MESSAGE ); // Message displayed when player dies
						score = 0;
						life = 3; // how many lives player starts with
					}

				}

			}
		}

		if(( armStartX <= SputHeadX + 30 && armEndX >= SputHeadX )) {
			if( rightLegEndY >= SputHeadY && rightLegEndY <= SputHeadY + 30) {

				lost();
				life--;

				if( life == 0 ) {
					RunBitMain.main(null);
				}

			}

		}

		for( int i = 0; i <= 225; i++ ) {

			if( ( armStartX <= blockX + 15 && armEndX >= blockX ) ) {
				if( rightLegEndY >= 272 && rightLegEndY <= 287 ) {
					lost();
					score++;

					int randNum = ( int ) ( Math.random() * 430 );
					if( randNum < 110 )
						randNum = 110;
					blockX = randNum;
					break;
				}
			}
		}

		if( headX < 110 ) {
			headX = 110; headY = 253; bodyStartX = 115; bodyStartY = 263; bodyEndX = 115; bodyEndY = 278; armStartX = 110; armStartY = 272; armEndX = 120; armEndY = 272; rightLegEndX = 110; rightLegEndY = 285; leftLegEndX = 120; leftLegEndY = 285;
		}
		else if( headX >= 470 ) {
			headX = 470; headY = 253; bodyStartX = 475; bodyStartY = 263; bodyEndX = 475; bodyEndY = 278; armStartX = 470; armStartY = 272; armEndX = 480; armEndY = 272; rightLegEndX = 470; rightLegEndY = 285; leftLegEndX = 480; leftLegEndY = 285;
		}

		if( direction % 2 == 0 )
			monHeadX -= 6; // speed of monster
		else
			monHeadX += 6; 


		if( monHeadX <= 100 || monHeadX >= 450 )
			direction++;


		if( direction1 % 2 == 0 )
			SputHeadX -= 4; // speed of second monster
		else
			SputHeadX += 4;


		if (SputHeadX <= 100 || SputHeadX > 450 )
			direction1++;

	}


	public void lost() {

		try {
			Thread.sleep( 500 ); // How much time before player spawns after death
		}
		catch( Exception e ) {}


		headX = 110; headY = 253; bodyStartX = 115; bodyStartY = 263; bodyEndX = 115; bodyEndY = 278; armStartX = 110; armStartY = 272; armEndX = 120; armEndY = 272; rightLegEndX = 110; rightLegEndY = 285; leftLegEndX = 120; leftLegEndY = 285;
		monHeadX = 260; monHeadY = 256;
		direction = 0;
	}

	public void onJumpKeyPressed() {

		velocityY = -12.0f;
	}
	public void paintScreen() {

		Graphics g;

		try {

			g = this.getGraphics();
			if( ( g != null ) && ( dbImage != null ) ) {
				g.drawImage( dbImage, 0, 0, null );
				Toolkit.getDefaultToolkit().sync();
				g.dispose();
			}
		}
		catch( Exception e ) {}
	}


	public static void main( String[] args ) {

		RunBitMain app = new RunBitMain();

		app.addWindowListener(
				new WindowAdapter() {
					public void windowClosing( WindowEvent e ) {
						System.exit( 0 );
					}
				}
				);
	}


	private class KeyHandler extends KeyAdapter {


		public void keyPressed( KeyEvent e ) {


			if( e.getKeyCode() == KeyEvent.VK_UP )
				jumpKeyPressed = true;


			if( e.getKeyCode() == KeyEvent.VK_RIGHT )
				rightKeyPressed = true;


			if( e.getKeyCode() == KeyEvent.VK_LEFT )
				leftKeyPressed = true;


		}


		public void keyReleased( KeyEvent e ) {


			if( e.getKeyCode() == KeyEvent.VK_UP )
				jumpKeyPressed = false;


			if( e.getKeyCode() == KeyEvent.VK_RIGHT )
				rightKeyPressed = false;


			if( e.getKeyCode() == KeyEvent.VK_LEFT )
				leftKeyPressed = false;


		}
	}
}