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

    setPreferredSize(new Dimension(n*160, n*160));
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
            if(xpos > 20+i*150 && xpos < (20+140)+(i*150))
            { if(ypos > 20+j*150 && ypos < (20+140)+(j*150)) {
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
    g.fillRect(n*5, n*5, n*150,n*150);
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
            g.fillRect(20+i*150, 20+j*150, 140, 140);
          }
          else if(tiles[i][j] == 1)
          {
            g.setColor(WHITE);
            g.fillRect(20+i*150, 20+j*150, 140, 140);
            g.setColor(RED);
            g.setStroke(new BasicStroke(5));
            g.drawLine(25+i*150,25+j*150, 155+i*150, 155+j*150);
            g.drawLine(155+i*150,25+j*150, 25+i*150, 155+j*150);

          }
          else if(tiles[i][j] == 10)
          {
            g.setColor(WHITE);
            g.fillRect(20+i*150, 20+j*150, 140, 140);
            g.setColor(BLUE);
            g.setStroke(new BasicStroke(5));
            g.drawOval(25+i*150, 25+j*150, 130, 130);
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
