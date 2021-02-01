package CatchTheCat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class StartGame extends JFrame implements ActionListener, MouseListener {
    //	定义组件
    MyChess chess = new MyChess();
    JPanel panel = new JPanel();
    JButton button1 = new JButton("重置"), button2 = new JButton("退出");

    //构造函数
    public StartGame() {
        super("抓 小 猫 ！");
        this.add(chess);
        this.add(panel, BorderLayout.SOUTH);
        this.panel.add(button1);
        this.panel.add(button2);
        this.chess.addMouseListener(this);
        this.button1.addActionListener(this);
        this.button2.addActionListener(this);
        this.setResizable(false);  //窗口是否可手动设置大小
        int size = Circle.r * 30;  //窗口大小
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();  //获得屏幕尺寸
        setBounds((d.width - size) / 2, (d.height - size) / 2, size, size);  //窗口居中
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new StartGame();
    }

    public void newGame() {
        for (int i = 0; i < MyChess.k; i++)
            for (int j = 0; j < MyChess.k; j++)
                chess.cat.circle[i][j].isBlocked = false;  //更新棋盘状态
        chess.initBlocked();  //重设阻塞位置
        chess.cat.isWin = false;
        chess.cat.isFail = false;
        chess.cat.setCatij(MyChess.k / 2, MyChess.k / 2);  //重设猫的坐标
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button1) {
            newGame();  //重新开始游戏
        } else
            System.exit(0);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO 自动生成的方法存根

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO 自动生成的方法存根
        if (chess.cat.isWin) {
            JOptionPane.showMessageDialog(this, "恭喜你，你赢了！(●ˇ∀ˇ●)");
            newGame();
        }
        if (chess.cat.isFail) {
            JOptionPane.showMessageDialog(this, "抱歉，你输了！⊙﹏⊙∥");
            newGame();
//            if (JOptionPane.showConfirmDialog(this, "再来一次？", "对不起，你输了！", JOptionPane.YES_NO_OPTION)
//                    == JOptionPane.YES_OPTION) {
//                newGame();
//            } else {
//                System.exit(0);
//            }
        }
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
