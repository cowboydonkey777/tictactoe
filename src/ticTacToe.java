import java.awt.*;
import java.awt.event.*;
//import java.util.Arrays;
import javax.swing.*;
import javax.swing.JOptionPane.*;

public class ticTacToe extends JPanel
{
  enum gameState { Start, Running, Gameover }
  private gameState GAMESTATE = gameState.Start;

  static int n = 5;
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
    int L = 5;
    if(L > n) L = n;

    int diagDownRight = 0;
    int diagDownLeft = 0;
    int hori = 0;
    int vert = 0;
    int somethingExists = 0;
    endValue check = new endValue();
    check.winner = false;
    check.team = 0;

    for(int i = 0; i < (n-L+1); i++){for(int j = 0; j < n; j++){
      hori = 0; vert = 0; diagDownRight = 0; diagDownLeft = 0;
      for(int k = 0; k < L; k++)
      {
        hori += tiles[k+i][j];
        vert += tiles[j][k+i];// + tiles[i][j+1] + tiles[i][j+2];
        if((j+k) <= (n-1)){
          diagDownRight += tiles[k+i][k+j];
          diagDownLeft += tiles[(n-1)-(k+i)][k+j];
          //System.out.print("stuff: " + (k+i) + " " + (k+j) + '\n');
        }
        //diagDownLeft += tiles[(n - j) - (k+1)][i+k];
      }
      /*for(int k = 0; k < (n-L+1); k++)
      {
        diagDownRight += tiles[i][k];
      }*/
      System.out.print("hori: " + hori + " vert: " + vert + " diagDR: " + diagDownRight + " diagDL: " + diagDownLeft + '\n');
      //diagDownLeft += tiles[(n - j) - 1][i] + tiles[(n - j) - 2][i + 1] + tiles[(n - j) - 3][i + 2];
      if(vert == 7*L || hori == 7*L || vert == 10*L || hori == 10*L) break;
      else if(diagDownRight == 7*L || diagDownRight == 10*L  || diagDownLeft == 7*L|| diagDownLeft == 10*L) break;
      somethingExists++;
    }
      if(diagDownRight == 7*L || diagDownLeft == 7*L || vert == 7*L || hori == 7*L)
      {check.winner = true; check.team = 1; break;} // x wins
      else if(diagDownRight == 10*L || diagDownLeft == 10*L || vert == 10*L || hori == 10*L)
      {check.winner = true; check.team = 2; break;} // o wins
      else if(i == n-1 && somethingExists == n*n)
      {check.winner = true; check.team = 0; break;} // no one wins
    }
    System.out.println('\n');
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
