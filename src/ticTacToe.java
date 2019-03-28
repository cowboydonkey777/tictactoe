import java.awt.*;
import java.awt.event.*;
//import java.util.Arrays;
import javax.swing.*;
import javax.swing.JOptionPane.*;

public class ticTacToe extends JPanel
{
  enum gameState { Start, Running, Gameover }
  private gameState GAMESTATE = gameState.Start;

  static int n = 3;
  int[][] tiles = new int[n][n];
  // 0: empty, 1: X, 2: O
  int turn = 7;

  private Color RED = new Color(0xFF0000);
  private Color BLUE = new Color(0x0000FF);
  private Color gridColor = new Color(0x8F8F8F);
  private Color TILE = new Color(0xD3D3D3);


  public ticTacToe()
  {

    setPreferredSize(new Dimension((n*75)+14, (n*75)+14));
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
            if(xpos > 10+i*75 && xpos < (10+70)+(i*75))
            {
              if(ypos > 10+j*75 && ypos < (10+70)+(j*75))
              {
              if(tiles[i][j] == 0) {
                  tiles[i][j] = turn;
                  if (turn == 7) turn = 10;
                  else turn = 7;
              }
              }
            }
          }}
          repaint();

          endValue endTest;
          endTest = end();
          if(endTest.winner) // was endTest.winner == true
          {
            if(endTest.team == 1) JOptionPane.showMessageDialog(null, "X wins!") ;
            else if(endTest.team == 2) JOptionPane.showMessageDialog(null, "O wins!");
            else JOptionPane.showMessageDialog(null, "The cat wins!");

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
    g.fillRect(7, 7, n*75,n*75);
    g.setStroke(new BasicStroke(1));

    if(GAMESTATE == gameState.Running);
    {
      for(int i = 0; i < n; i++)
      {
        for(int j = 0; j < n; j++)
        {
          if(tiles[i][j] == 0)
          {
            g.setColor(TILE);
            g.fillRect(10+i*75, 10+j*75, 70, 70);
          }
          else if(tiles[i][j] == 7)
          {
            g.setColor(TILE);
            g.fillRect(10+i*75, 10+j*75, 70, 70);
            g.setColor(RED);
            g.setStroke(new BasicStroke(3));
            g.drawLine(15+i*75,15+j*75, 75+i*75, 75+j*75);
            g.drawLine(75+i*75,15+j*75, 15+i*75, 75+j*75);

          }
          else if(tiles[i][j] == 10)
          {
            g.setColor(TILE);
            g.fillRect(10+i*75, 10+j*75, 70, 70);
            g.setColor(BLUE);
            g.setStroke(new BasicStroke(3));
            g.drawOval(15+i*75, 15+j*75, 60, 60);
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
    int somethingExists = 0;
    endValue check = new endValue();
    check.winner = false;
    check.team = 0;

    for(int i = 0; i < n; i++){for(int j = 0; j < n; j++){
      hori += tiles[j][i];
      vert += tiles[i][j];
      if(tiles[i][j] != 0) somethingExists++;
    }
      diagDownRight += tiles[i][i];
      diagDownLeft += tiles[(n-1)-i][i];

      if(diagDownRight == 7*3 || diagDownLeft == 7*3 || vert == 7*3 || hori == 7*3)
      {check.winner = true; check.team = 1;} // x wins
      else if(diagDownRight == 10*3 || diagDownLeft == 10*3 || vert == 10*3 || hori == 10*3)
      {check.winner = true; check.team = 2;} // o wins
      else if(i == n-1 && somethingExists == n*n)
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
