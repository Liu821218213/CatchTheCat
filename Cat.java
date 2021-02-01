package CatchTheCat;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Cat {
    //定义成员变量
    public int i, j;  //i，j表示虚拟位置
    public int x, y;  //x，y表示实际位置
    public Circle[][] circle = new Circle[MyChess.k][MyChess.k];  //棋盘
    public boolean isFail, isWin;

    //猫的构造函数
    public Cat(int i, int j) {
        setCatij(i, j);  //初始化坐标
        isFail = false;
        isWin = false;
    }

    //设置猫的坐标
    public void setCatij(int i, int j) {
        this.i = i;
        this.j = j;
        this.x = MyChess.x0 + (2 * i + j % 2) * Circle.r;
        this.y = MyChess.y0 + 2 * j * Circle.r;
    }

    //利用Map实现java中的函数返回多个值
    public Map<String, Integer> getNext(int i, int j, int path) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        //奇数 滚动数组
        int[][] odd = {{0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 0},
                {0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 0}};
        //偶数 滚动数组
        int[][] even = {{-1, -1}, {0, -1}, {1, 0}, {0, 1}, {-1, 1}, {-1, 0},
                {-1, -1}, {0, -1}, {1, 0}, {0, 1}, {-1, 1}, {-1, 0}};
        int ni, nj;
        if (j % 2 == 1) {
            ni = i + odd[path][0];
            nj = j + odd[path][1];
        } else {
            ni = i + even[path][0];
            nj = j + even[path][1];
        }
        map.put("i", ni);
        map.put("j", nj);
        return map;
    }


    //走猫步
    public void move(int i, int j) {
        if (isBorder(i, j)) {
            isFail = true;
            return;
        }
        int path = findNextPath(i, j);  //path表示六个方向中的一个方向
        if (path >= 0) {
            Map<String, Integer> map = getNext(i, j, path);
            setCatij(map.get("i"), map.get("j"));  //改变坐标
        } else if (path == -1) {
            isWin = true;
        }
    }

    //判断是否是边界
    public boolean isBorder(int i, int j) {
        return i == 0 || j == 0 || i == (MyChess.k - 1) || j == (MyChess.k - 1);
    }

    //寻找下一方向
    public int findNextPath(int i, int j) {
        int minSetps = 1 << 15;  //当前最少步数
        int steps = 1 << 15;  //记录每个方向的最少步数
        int nextPath = -1;  //最少步数的方向
        int start = (int) (Math.random() * 6);  //六个方向中随机选择一个方向开始遍历，start范围为0，1，2，3，4，5
        for (int path = start; path < start + 6; path++) {
            Map<String, Integer> map = getNext(i, j, path);
            int ni = map.get("i"), nj = map.get("j");
            if (!circle[ni][nj].isBlocked)
                steps = findMinSteps(ni, nj, i, j);  //记录此方向的最少步数
            if (minSetps > steps) {
                minSetps = steps;
                nextPath = path;
            }
        }
        return nextPath;
    }


    //遍历一条经过某点的路径，并记录最少步数
    public int findMinSteps(int x, int y, int i, int j) {
        boolean[][] visited = new boolean[MyChess.k][MyChess.k];  //标记此方向已访问过的节点
        visited[x][y] = true;
        visited[i][j] = true;
        Queue<Circle> queue = new LinkedList<>();  //BFS遍历
        Circle cur = new Circle(x, y);
        queue.offer(cur);
        int minSteps = 1 << 15;
        while ((cur = queue.poll()) != null) {
            if (isBorder(cur.x, cur.y)) {
                minSteps = Math.min(minSteps, cur.step);  //记录到达边缘的最少步数
            } else {
                for (int path = 0; path < 6; path++) {
                    Map<String, Integer> map = getNext(cur.x, cur.y, path);
                    Circle next = new Circle(map.get("i"), map.get("j"));
                    next.step = cur.step + 1;
                    if (!visited[next.x][next.y] && !circle[next.x][next.y].isBlocked) {
                        visited[next.x][next.y] = true;
                        queue.offer(next);
                    }
                }
            }
        }
        return minSteps;
    }
}
