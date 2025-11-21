import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    private class Tile {
        int x;
        int y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }  

    int boardWidth;
    int boardHeight;
    int tileSize = 25;
    
    //snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;

    //food
    Tile food;
    Random random;

    //game logic
    int velocityX;
    int velocityY;
    Timer gameLoop;

    boolean gameOver = false;
    int highScore = 0;

    SnakeGame(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<Tile>();

        food = new Tile(10, 10);
        random = new Random();
        placeFood();

        velocityX = 1;
        velocityY = 0;
        
        //game timer
        gameLoop = new Timer(100, this);
        gameLoop.start();
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        //Grid Lines
        for(int i = 0; i < boardWidth/tileSize; i++) {
            g.drawLine(i*tileSize, 0, i*tileSize, boardHeight);
            g.drawLine(0, i*tileSize, boardWidth, i*tileSize); 
        }

        //Food (mouse icon)
        g.setColor(Color.gray);
        int fx = food.x * tileSize;
        int fy = food.y * tileSize;
        //body
        g.fillOval(fx + 4, fy + 8, 16, 12);
        //head
        g.fillOval(fx + 14, fy + 10, 10, 8);
        //ears
        g.fillOval(fx + 16, fy + 6, 6, 6);
        g.fillOval(fx + 20, fy + 8, 5, 5);
        //tail
        g.drawArc(fx - 2, fy + 10, 10, 10, 0, -180);
        //eye
        g.setColor(Color.black);
        g.fillOval(fx + 20, fy + 12, 2, 2);

        //Snake Body
        for (int i = snakeBody.size() - 1; i >= 0; i--) {
            Tile snakePart = snakeBody.get(i);
            int bx = snakePart.x * tileSize;
            int by = snakePart.y * tileSize;
            //alternate colors for pattern
            if (i % 2 == 0) {
                g.setColor(new Color(34, 139, 34)); //forest green
            } else {
                g.setColor(new Color(50, 205, 50)); //lime green
            }
            g.fillRoundRect(bx + 2, by + 2, tileSize - 4, tileSize - 4, 10, 10);
            //scales pattern
            g.setColor(new Color(0, 100, 0));
            g.drawArc(bx + 4, by + 6, 8, 6, 0, 180);
            g.drawArc(bx + 12, by + 6, 8, 6, 0, 180);
            g.drawArc(bx + 8, by + 14, 8, 6, 0, 180);
        }

        //Snake Head
        int hx = snakeHead.x * tileSize;
        int hy = snakeHead.y * tileSize;
        g.setColor(new Color(34, 139, 34)); //forest green
        g.fillRoundRect(hx + 1, hy + 1, tileSize - 2, tileSize - 2, 12, 12);
        //eyes
        g.setColor(Color.yellow);
        if (velocityX == 1) { //moving right
            g.fillOval(hx + 15, hy + 4, 6, 6);
            g.fillOval(hx + 15, hy + 14, 6, 6);
        } else if (velocityX == -1) { //moving left
            g.fillOval(hx + 4, hy + 4, 6, 6);
            g.fillOval(hx + 4, hy + 14, 6, 6);
        } else if (velocityY == -1) { //moving up
            g.fillOval(hx + 4, hy + 4, 6, 6);
            g.fillOval(hx + 14, hy + 4, 6, 6);
        } else { //moving down
            g.fillOval(hx + 4, hy + 15, 6, 6);
            g.fillOval(hx + 14, hy + 15, 6, 6);
        }
        //pupils
        g.setColor(Color.black);
        if (velocityX == 1) {
            g.fillOval(hx + 17, hy + 6, 3, 3);
            g.fillOval(hx + 17, hy + 16, 3, 3);
        } else if (velocityX == -1) {
            g.fillOval(hx + 5, hy + 6, 3, 3);
            g.fillOval(hx + 5, hy + 16, 3, 3);
        } else if (velocityY == -1) {
            g.fillOval(hx + 6, hy + 5, 3, 3);
            g.fillOval(hx + 16, hy + 5, 3, 3);
        } else {
            g.fillOval(hx + 6, hy + 17, 3, 3);
            g.fillOval(hx + 16, hy + 17, 3, 3);
        }
        //tongue
        g.setColor(Color.red);
        if (velocityX == 1) {
            g.fillRect(hx + 22, hy + 11, 6, 2);
            g.fillRect(hx + 26, hy + 9, 2, 2);
            g.fillRect(hx + 26, hy + 13, 2, 2);
        } else if (velocityX == -1) {
            g.fillRect(hx - 4, hy + 11, 6, 2);
            g.fillRect(hx - 4, hy + 9, 2, 2);
            g.fillRect(hx - 4, hy + 13, 2, 2);
        } else if (velocityY == -1) {
            g.fillRect(hx + 11, hy - 4, 2, 6);
            g.fillRect(hx + 9, hy - 4, 2, 2);
            g.fillRect(hx + 13, hy - 4, 2, 2);
        } else {
            g.fillRect(hx + 11, hy + 22, 2, 6);
            g.fillRect(hx + 9, hy + 26, 2, 2);
            g.fillRect(hx + 13, hy + 26, 2, 2);
        }

        //Score
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (gameOver) {
            g.setColor(Color.red);
            g.drawString("Game Over: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
            g.drawString("High Score: " + String.valueOf(highScore), tileSize - 16, tileSize + 20);
            g.drawString("Press SPACE to restart", tileSize - 16, tileSize + 40);
        }
        else {
            g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
            g.drawString("High Score: " + String.valueOf(highScore), tileSize - 16, tileSize + 20);
        }
    }

    public void placeFood(){
        food.x = random.nextInt(boardWidth/tileSize);
        food.y = random.nextInt(boardHeight/tileSize);
    }

    public void move() {
        //eat food
        if (collision(snakeHead, food)) {
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        //move snake body
        for (int i = snakeBody.size()-1; i >= 0; i--) {
            Tile snakePart = snakeBody.get(i);
            if (i == 0) {
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }
            else {
                Tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }
        //move snake head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        //game over conditions
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            if (collision(snakeHead, snakePart)) {
                gameOver = true;
            }
        }

        //wrap around edges
        if (snakeHead.x < 0) {
            snakeHead.x = boardWidth / tileSize - 1;
        }
        else if (snakeHead.x >= boardWidth / tileSize) {
            snakeHead.x = 0;
        }
        if (snakeHead.y < 0) {
            snakeHead.y = boardHeight / tileSize - 1;
        }
        else if (snakeHead.y >= boardHeight / tileSize) {
            snakeHead.y = 0;
        }

        //update high score when game ends
        if (gameOver && snakeBody.size() > highScore) {
            highScore = snakeBody.size();
        }
    }

    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void resetGame() {
        snakeHead.x = 5;
        snakeHead.y = 5;
        snakeBody.clear();
        velocityX = 1;
        velocityY = 0;
        gameOver = false;
        placeFood();
        gameLoop.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            gameLoop.stop();
        }
    }  

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && gameOver) {
            resetGame();
        }
        else if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
            velocityX = 0;
            velocityY = -1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
            velocityX = 0;
            velocityY = 1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
            velocityX = -1;
            velocityY = 0;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
            velocityX = 1;
            velocityY = 0;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}