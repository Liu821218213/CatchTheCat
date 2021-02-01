package CatchTheCat;

public class Circle {
    static int r = 20;  //圆点半径
    int x, y;
    int step;  //表示当前搜索深度
    boolean isBlocked;  //标识这个circle能否走

    public Circle(int x, int y) {
        this.x = x;
        this.y = y;
        this.isBlocked = false;  //默认各位置都没有阻塞
        this.step = 0;  //默认初始BFS的深度为0
    }
}