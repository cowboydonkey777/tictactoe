import java.awt.*;
import java.awt.event.*;
//import java.util.Arrays;
import javax.swing.*;

public class ticTacToe extends JPanel
{

  enum gameState { Start, Running, Gameover }
  private gameState GAMESTATE = gameState.Start;

  int[][] tiles = new int[3][3];
  // 0: empty, 1: X, 2: O
  int turn = 1;
  static int n = 3;

  private Color RED = new Color(0xFF0000);
  private Color BLUE = new Color(0x0000FF);
  private Color gridColor = new Color(0x8F8F8F);
  private Color WHITE = new Color(0xFFFFFF);


  public ticTacToe()
  {

    setPreferredSize(new Dimension(n*150, n*150));
    setBackground(new Color(0xc8a2c8));
    setFocusable(true);

    addMouseListener(new MouseAdapter()
    {
      @Override
      public void mousePressed(MouseEvent me)
      {
        int xpos = me.getX();
        int ypos = me.getY();

        if(GAMESTATE == gameState.Running)
        {
          for(int i = 0; i < n; i++) { for(int j = 0; j < n; j++)
          {
            if(xpos > 35+i*130 && xpos < (35+120)+(i*130))
            { if(ypos > 35+j*130 && ypos < (35+120)+(j*130)) {
                tiles[i][j] = turn;
                if(turn == 1) turn = 10;
                else turn = 1;
              }}
          }}
          repaint();

          endValue endTest = new endValue();
          endTest = end();
          if(endTest.winner == true)
          {
            String c = "The cat";
            if(endTest.team == 1) c = "X";
            if(endTest.team == 2) c = "O";
            System.out.println(c + " wins!");
            GAMESTATE = gameState.Start;
          }
        }
      }
    });

    addKeyListener(new KeyAdapter()
    {
      @Override
      public void keyPressed(KeyEvent ke)
      {
        switch (ke.getKeyCode())
        {
          case KeyEvent.VK_ENTER:
            startGame();
            break;
        }
        repaint();
      }
    });
  }

  @Override
  public void paintComponent(Graphics gg)
  {
    super.paintComponent(gg);
    Graphics2D g = (Graphics2D) gg;

    drawGrid(g);
  }

  void startGame() {
  if(GAMESTATE == gameState.Start)
  {
    tiles = new int[n][n];
    if(GAMESTATE != gameState.Running){
      for(int i = 0; i < n; i++){ for(int j = 0; j < n; j++)
      { tiles[i][j] = 0;}}}
    GAMESTATE = gameState.Running;
  }
}

  void drawGrid(Graphics2D g)
  {
    g.setColor(gridColor);
    g.fillRect(25, 25, 400,400);
    g.setStroke(new BasicStroke(1));

    if(GAMESTATE == gameState.Running);
    {
      for(int i = 0; i < n; i++)
      {
        for(int j = 0; j < n; j++)
        {
          if(tiles[i][j] == 0)
          {
            g.setColor(WHITE);
            g.fillRect(35+i*130, 35+j*130, 120, 120);
          }
          else if(tiles[i][j] == 1)
          {
            g.setColor(WHITE);
            g.fillRect(35+i*130, 35+j*130, 120, 120);
            g.setColor(RED);
            g.setStroke(new BasicStroke(5));
            g.drawLine(40+i*130,40+j*130, 150+i*130, 150+j*130);
            g.drawLine(150+i*130,40+j*130, 40+i*130, 150+j*130);

          }
          else if(tiles[i][j] == 10)
          {
            g.setColor(WHITE);
            g.fillRect(35+i*130, 35+j*130, 120, 120);
            g.setColor(BLUE);
            g.setStroke(new BasicStroke(5));
            g.drawOval(40+i*130, 40+j*130, 110, 110);
          }
        }
      }
    }
    repaint();
  }

  endValue end()
  {
    int diagDownRight = 0;
    int diagDownLeft = 0;
    int hori = 0;
    int vert = 0;
    boolean somethingExists = false;
    endValue check = new endValue();
    check.winner = false;
    check.team = 0;

    for(int i = 0; i < n; i++){for(int j = 0; j < n; j++){
      hori += tiles[j][i];
      vert += tiles[i][j];
      if(tiles[i][j] != 0) somethingExists = true;
    }
      diagDownRight += tiles[i][i];
      diagDownLeft += tiles[2-i][i];

      if(diagDownRight == n || diagDownLeft == n || vert == n || hori == n)
      {check.winner = true; check.team = 1;} // x wins
      else if(diagDownRight == 10*n || diagDownLeft == 10*n || vert == 10*n || hori == 10*n)
      {check.winner = true; check.team = 2;} // o wins
      else if(i == n && somethingExists == true)
      {check.winner = true; check.team = 0;} // no one wins

      hori = 0;
      vert = 0;
    }
    return check;
  }

  public static void main(String[] args)
  {
    SwingUtilities.invokeLater(() ->
    {
      JFrame f = new JFrame();
      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      f.setTitle("Tic-Tac-Toe");
      f.setResizable(false);
      f.add(new ticTacToe(), BorderLayout.CENTER);
      f.pack();
      f.setLocationRelativeTo(null);
      f.setVisible(true);
    });
  }
}

class endValue{
  boolean winner;
  int team;
}
