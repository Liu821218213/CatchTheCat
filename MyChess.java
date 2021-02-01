package CatchTheCat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyChess extends JPanel implements MouseListener {
    static int k = 11;  //边长，11个circle
    static int x0 = Circle.r * 3, y0 = Circle.r * 3;  //棋盘左上角位置
    Cat cat;
    String string = "";

    //构造函数
    public MyChess() {
        cat = new Cat(k / 2, k / 2);  //cat初始化在棋盘中心
        this.addMouseListener(this);
        for (int i = 0; i < k; i++)
            for (int j = 0; j < k; j++) {  //初始化棋盘
                cat.circle[i][j] = new Circle(x0 + (2 * i + j % 2) * Circle.r, y0 + 2 * j * Circle.r);
            }
        initBlocked();
    }

    //随机初始化阻塞的位置
    public void initBlocked() {
        string = "点击小圆点，围住小猫";
        for (int i = 0; i <= k / 2; i++) { // i < 6
            int x = (int) (Math.random() * k);  //x，y取值为0至k的整数，左闭右开
            int y = (int) (Math.random() * k);
            if (x != 5 || y != 5)  //概率应该是 21 // 121 ？
                cat.circle[x][y].isBlocked = true;
        }
    }

    //画棋盘
    public void paint(Graphics g) {
        super.paint(g);  //画笔g
        g.setFont(new Font("Thoma", Font.BOLD, Circle.r));
        g.setColor(Color.black);
        //得到当前大小，居中显示文字 (Circle.r * 30 - g.getFontMetrics().stringWidth(string)) / 2
        g.drawString(string, (Circle.r * 30 - g.getFontMetrics().stringWidth(string)) / 2, Circle.r * 2);

        for (Circle[] circle : cat.circle)
            for (int j = 0; j < k; j++) {  //画圆圈
                if (!circle[j].isBlocked)
                    g.setColor(Color.decode("#87CEFA")); //可走位置
                else
                    g.setColor(Color.decode("#000080")); //不可走位置
                g.drawOval(circle[j].x, circle[j].y, Circle.r * 2, Circle.r * 2);  // 绘制一个椭圆
                g.fillOval(circle[j].x, circle[j].y, Circle.r * 2, Circle.r * 2);  // 填充一个椭圆
            }

//        g.setColor(Color.red);
//        g.fillOval(cat.x, cat.y, Circle.r * 2, Circle.r * 2);
        ImageIcon catImage = new ImageIcon(StartGame.class.getResource("cat.png"));  //画猫
        Image catImg = catImage.getImage();
//        if(!cat.isWin)
        g.drawImage(catImg, cat.x - Circle.r / 2, cat.y, Circle.r * 3, Circle.r * 2, this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
//        //circles[i][j] = new Circle(x0 + (2 * i + j % 2) * Circle.r, y0 + 2 * j * Circle.r);
//        int clickFlag = clickValue(e);
//        if (e.getX() < x0 || e.getY() < y0) return;
//        int j = (e.getY() - y0) / (2 * Circle.r);
//        if ((e.getX() - x0) / Circle.r - j % 2 < 0) return;
//        int i = ((e.getX() - x0) / Circle.r - j % 2) / 2;
//        if (i >= 11 || j >= 11) return;
        int i, j;  //(i, j)为点击点的坐标
        if (e.getX() >= x0 && e.getY() >= y0) {  //排除棋盘外的位置
            j = (e.getY() - y0) / (2 * Circle.r);
            if ((e.getX() - x0) / Circle.r - j % 2 >= 0) {
                i = ((e.getX() - x0) / Circle.r - j % 2) / 2;
                if (i >= k || j >= k) return;
            } else return;
        } else return;
        if (cat.i == i && cat.j == j) {  //如果是猫的位置
            string = "点击位置是猫当前位置，禁止点击";
        } else if (cat.circle[i][j].isBlocked) {  //如果是阻塞位置
            string = "点击位置已经是墙了，禁止点击";
        } else {
            cat.circle[i][j].isBlocked = true;  //新增阻塞位置
            string = "您点击了（" + (i + 1) + "，" + (j + 1) + "）";
            cat.move(cat.i, cat.j);  //移动猫
            if (cat.isFail) string = "猫已经跑到地图边缘了，你输了";
            if (cat.isWin) string = "猫已经无路可走，你赢了";
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO 自动生成的方法存根
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO 自动生成的方法存根
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO 自动生成的方法存根
    }
}