
import code.Data;
import code.MinHeap;
import code.Point;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.List;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

public class WholePanel extends JPanel {
    JFrame frame;

    private CanvasPanel canvas;
    private JPanel topPanel;

    private JButton startSearch;

    private JLabel fromLabel;
    private JComboBox fromComboBox = new JComboBox();// from

    private JLabel toLabel;
    private JComboBox toComboBox = new JComboBox();// to

    private List<SearchPath> paths = new ArrayList<SearchPath>();
    private List<Position> positions = new ArrayList<Position>();

    private Position from;
    private Position to;

    Image background;
    int backgroundWidth, backgroundHeight;

    /*
    // Floyd-Warshall核心算法
    private List<Integer> searchResult = new ArrayList<Integer>();
    private int inf = 0x3f3f3f3f;// 极大值
    double[][] mapWeight;// 边权:距离
    int[][] mapPath;// 可达路劲
    */

    // 地图元素
    static final char START = 'S'; // 起点
    static final char END = 'E'; // 终点
    static final char SPACE = '.'; // 空地
    static final char WALL = 'W'; // 墙
    static final char VISITED = '-'; // 被访问过
    static final char ON_PATH = '@'; // 在结果路径上

    // 地图
    char[][] MAP;

    // 起点
    code.Point START_PNT = null;
    // 终点
    code.Point END_PNT = null;

    public WholePanel(JFrame frame) {
        this.frame = frame;

        // 背景地图
        background = (new ImageIcon("map/map3.jpg")).getImage();
        backgroundWidth = background.getWidth(null);
        backgroundHeight = background.getHeight(null);

        // 考察的位置点
        positions.add(new Position("P0", 29, 223, 0));// *P0(29,223)
        positions.add(new Position("P1", 70, 233, 1));// *P1(70,233)
        positions.add(new Position("P2", 136, 233, 2));// *P2(136,233)
        positions.add(new Position("P3", 134, 212, 3));// *P3(134,212)
        positions.add(new Position("P4", 112, 200, 4));// *P4(112,200)
        positions.add(new Position("P5", 113, 161, 5));// *P5(113,161)
        positions.add(new Position("P6", 112, 114, 6));// *P6(112,114)
        positions.add(new Position("P7", 66, 105, 7));// *P7(66,105)
        positions.add(new Position("P8", 53, 160, 8));// *P8(53,160)
        positions.add(new Position("P9", 133, 99, 9));// *P9(133,99)
        positions.add(new Position("P10", 168, 155, 10));// *P10(168,155)
        positions.add(new Position("P11", 175, 90, 11));// *P11(175,90)
        positions.add(new Position("P12", 197, 41, 12));// *P12(197,41)
        positions.add(new Position("P13", 241, 47, 13));// *P13(241,47)
        positions.add(new Position("P14", 258, 17, 14));// *P14(258,17)
        positions.add(new Position("P15", 331, 53, 15));// *P15(331,53)
        positions.add(new Position("P16", 361, 76, 16));// *P16(361,76)
        positions.add(new Position("P17", 339, 94, 17));// *P17(339,94)
        positions.add(new Position("P18", 321, 130, 18));// *P18(321,130)
        positions.add(new Position("P19", 315, 154, 19));// *P19(315,154)
        positions.add(new Position("P20", 296, 163, 20));// *P20(296,163)
        positions.add(new Position("P21", 309, 211, 21));// *P21E(309,211)
        positions.add(new Position("P22", 232, 209, 22));// *P22(232,209)
        positions.add(new Position("P23", 232, 131, 23));// *P23(232,131)

        from = positions.get(0);// 起点
        to = positions.get(positions.size() - 1);// 终点

        // 可达的路径
        paths.add(new SearchPath(positions.get(0), positions.get(1)));
        paths.add(new SearchPath(positions.get(0), positions.get(8)));
        paths.add(new SearchPath(positions.get(7), positions.get(8)));
        paths.add(new SearchPath(positions.get(1), positions.get(2)));
        paths.add(new SearchPath(positions.get(2), positions.get(3)));
        paths.add(new SearchPath(positions.get(3), positions.get(4)));
        paths.add(new SearchPath(positions.get(4), positions.get(5)));
        paths.add(new SearchPath(positions.get(5), positions.get(8)));
        paths.add(new SearchPath(positions.get(5), positions.get(6)));
        paths.add(new SearchPath(positions.get(6), positions.get(7)));
        paths.add(new SearchPath(positions.get(6), positions.get(9)));
        paths.add(new SearchPath(positions.get(5), positions.get(10)));
        paths.add(new SearchPath(positions.get(6), positions.get(10)));
        paths.add(new SearchPath(positions.get(9), positions.get(10)));
        paths.add(new SearchPath(positions.get(11), positions.get(10)));
        paths.add(new SearchPath(positions.get(23), positions.get(10)));
        paths.add(new SearchPath(positions.get(22), positions.get(10)));
        paths.add(new SearchPath(positions.get(3), positions.get(10)));
        paths.add(new SearchPath(positions.get(4), positions.get(10)));
        paths.add(new SearchPath(positions.get(11), positions.get(12)));
        paths.add(new SearchPath(positions.get(12), positions.get(13)));
        paths.add(new SearchPath(positions.get(13), positions.get(14)));
        paths.add(new SearchPath(positions.get(14), positions.get(15)));
        paths.add(new SearchPath(positions.get(15), positions.get(16)));
        paths.add(new SearchPath(positions.get(16), positions.get(17)));
        paths.add(new SearchPath(positions.get(17), positions.get(18)));
        paths.add(new SearchPath(positions.get(18), positions.get(23)));
        paths.add(new SearchPath(positions.get(18), positions.get(19)));
        paths.add(new SearchPath(positions.get(19), positions.get(20)));
        paths.add(new SearchPath(positions.get(19), positions.get(21)));
        paths.add(new SearchPath(positions.get(21), positions.get(22)));
        paths.add(new SearchPath(positions.get(22), positions.get(3)));
        paths.add(new SearchPath(positions.get(2), positions.get(3)));
        paths.add(new SearchPath(positions.get(1), positions.get(2)));
        paths.add(new SearchPath(positions.get(3), positions.get(4)));
        paths.add(new SearchPath(positions.get(13), positions.get(17)));
        paths.add(new SearchPath(positions.get(11), positions.get(23)));
        paths.add(new SearchPath(positions.get(22), positions.get(23)));
        paths.add(new SearchPath(positions.get(9), positions.get(11)));

        fromLabel = new JLabel("From: ", JLabel.RIGHT);
        for (Position p : positions) {
            String str = String.format("%s(%d,%d)", p.getName(), p.getX(), p.getY());
            System.out.println("Node: " + str);
            fromComboBox.addItem(str);
        }
        fromComboBox.setSelectedIndex(0);
        fromComboBox.addActionListener(new ComboListener());

        toLabel = new JLabel("To: ", JLabel.RIGHT);
        for (Position p : positions) {
            toComboBox.addItem(String.format("%s(%d,%d)", p.getName(), p.getX(), p.getY()));
        }
        toComboBox.setSelectedIndex(positions.size() - 1);
        toComboBox.addActionListener(new ComboListener());

        startSearch = new JButton("Search");
        startSearch.addActionListener(new ButtonListener());

        topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 5));

        topPanel.add(fromLabel);
        topPanel.add(fromComboBox);
        topPanel.add(toLabel);
        topPanel.add(toComboBox);
        topPanel.add(startSearch);

        canvas = new CanvasPanel();
        JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topPanel, canvas);
        setLayout(new BorderLayout());
        add(sp);
    }

    private class ComboListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            // needs to be filled
            JComboBox comb = (JComboBox) event.getSource();
            if (comb != null) {
                String selectedItem = (String) comb.getSelectedItem();
                System.out.println(selectedItem);
                if (selectedItem != null && !selectedItem.isEmpty()) {
                    if (comb == fromComboBox) {
                        from = positions.get(fromComboBox.getSelectedIndex());
                    } else if (comb == toComboBox) {
                        to = positions.get(toComboBox.getSelectedIndex());
                    }
                    MAP = null;
                    //searchResult.clear();
                    canvas.repaint();
                }
            }
        }
    } // end of ComboListener

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            // needs to be filled
            if (event.getSource() == startSearch) {
                System.out.println("startSearch:");
                System.out.println("From: " + fromComboBox.getSelectedItem());
                System.out.println("To: " + toComboBox.getSelectedItem());
                MAP = null;
                //searchResult.clear();
                if (from != to) {
                    searchAStar();
                } else {
                    System.out.println("错误,起点和终点相同!");
                }
                canvas.repaint();
            }
        }
    } // end of ButtonListener

    // CanvasPanel is the panel where shapes will be drawn
    private class CanvasPanel extends JPanel {

        // this method draws all shapes
        public void paintComponent(Graphics page) {
            super.paintComponent(page);

            setBackground(Color.WHITE);
            // System.out.println("paintComponent");
            Rectangle rc = page.getClipBounds();
            page.drawImage(background, rc.x, rc.y, rc.width, rc.height, 0, 0, background.getWidth(this),
                    background.getHeight(this), this);

            Rectangle rect = page.getClipBounds();

            int r = 4;
            for (SearchPath p : paths) {// 绘制可达路径
                int x0 = (int) ((float) p.from.getX() / backgroundWidth * rect.getWidth());
                int y0 = (int) ((float) p.from.getY() / backgroundHeight * rect.getHeight());
                int x1 = (int) ((float) p.to.getX() / backgroundWidth * rect.getWidth());
                int y1 = (int) ((float) p.to.getY() / backgroundHeight * rect.getHeight());
                page.setColor(Color.GREEN);
                page.drawLine(x0, y0, x1, y1);
            }

            for (Position p : positions) {// 绘制考察的位置点
                r = 2;
                int x0 = (int) ((float) p.getX() / backgroundWidth * rect.getWidth());
                int y0 = (int) ((float) p.getY() / backgroundHeight * rect.getHeight());
                page.setColor(Color.RED);
                page.fillOval(x0 - r, y0 - r, r * 2, r * 2);

                if (p.equals(from)) {
                    r = 4;
                    page.fillOval(x0 - r, y0 - r, r * 2, r * 2);
                } else if (p.equals(to)) {
                    r = 4;
                    page.fillRect(x0 - r, y0 - r, r * 2, r * 2);
                }

                page.setColor(Color.BLUE);
                page.drawString(p.getName(), x0, y0);
            }

            /*
            for (int i = 0; i < searchResult.size() - 1; i++) {// 绘制搜索结果:Floyd-Warshall
                Position p0 = positions.get(searchResult.get(i));
                Position p1 = positions.get(searchResult.get(i + 1));

                int x0 = (int) ((float) p0.getX() / backgroundWidth * rect.getWidth());
                int y0 = (int) ((float) p0.getY() / backgroundHeight * rect.getHeight());
                int x1 = (int) ((float) p1.getX() / backgroundWidth * rect.getWidth());
                int y1 = (int) ((float) p1.getY() / backgroundHeight * rect.getHeight());

                page.setColor(Color.RED);
                page.drawLine(x0, y0, x1, y1);
            }*/

            if (MAP != null) {// 绘制搜索结果:AStar
                r = 1;
                page.setColor(Color.RED);
                for (int y = 0; y < backgroundHeight; y++) {
                    for (int x = 0; x < backgroundWidth; x++) {
                        if (MAP[x][y] == '@') {
                            int x0 = (int) ((float) x / backgroundWidth * rect.getWidth());
                            int y0 = (int) ((float) y / backgroundHeight * rect.getHeight());
                            page.fillRect(x0 - r, y0 - r, r * 2, r * 2);
                        }
                    }
                }
            }
        }
    } // end of CanvasPanel class

    /*
    void searchStar() {
        int citysNumber = positions.size();
        mapWeight = new double[citysNumber][citysNumber]; // 边权
        mapPath = new int[citysNumber][citysNumber]; // // 最短路径

        // 初始化
        for (int i = 0; i < citysNumber; i++) {
            for (int j = 0; j < citysNumber; j++) {
                mapPath[i][j] = -1;
                if (i == j)
                    mapWeight[i][j] = 0;
                else
                    mapWeight[i][j] = inf;
            }
        }

        // 设置边权值:距离
        for (SearchPath p : paths) {
            mapWeight[p.getFrom().getIndex()][p.getTo().getIndex()] = p.getLength();
            mapWeight[p.getTo().getIndex()][p.getFrom().getIndex()] = p.getLength();
        }

        // Floyd-Warshall核心算法
        for (int k = 0; k < citysNumber; k++) {
            for (int i = 0; i < citysNumber; i++) {
                for (int j = 0; j < citysNumber; j++) {
                    if (mapWeight[i][j] > mapWeight[i][k] + mapWeight[k][j]) {
                        mapWeight[i][j] = mapWeight[i][k] + mapWeight[k][j];
                        mapPath[i][j] = k;
                    }
                }
            }
        }

        searchResult.add(from.getIndex());
        PrintShortestPath(from.getIndex(), to.getIndex());
        System.out.println("Shortest distance: " + mapWeight[from.getIndex()][to.getIndex()]);
    }

    void PrintShortestPath(int i, int j) {
        // 递归打印最短路径
        if (mapPath[i][j] == -1) {// 表示i->j没有中间节点
            searchResult.add(j);
            return;
        } else {
            PrintShortestPath(i, mapPath[i][j]);
            PrintShortestPath(mapPath[i][j], j);
        }
    }*/

    //A*
    void searchAStar() {
        genMap();
        //printMap();
        search();
        //printMap();
        printPath();
    }

    void printPath() {
        System.out.println("Shortest path: ");
        System.out.println(String.format("%s(%d,%d)", from.getName(), from.getX(), from.getY()));

        Position current = new Position(from.getName(), from.getX(), from.getY(), from.getIndex());
        for (SearchPath p : paths) {
            if (p.getFrom().getIndex() == current.getIndex() && isSearched(p.getTo())) {
                current = new Position(p.getTo().getName(), p.getTo().getX(), p.getTo().getY(), p.getTo().getIndex());
                System.out.println(String.format("%s(%d,%d)", current.getName(), current.getX(), current.getY()));
            } else if (p.getTo().getIndex() == current.getIndex() && isSearched(p.getFrom())) {
                current = new Position(p.getFrom().getName(), p.getFrom().getX(), p.getFrom().getY(), p.getFrom().getIndex());
                System.out.println(String.format("%s(%d,%d)", current.getName(), current.getX(), current.getY()));
            }
        }

        System.out.println(String.format("%s(%d,%d)", to.getName(), to.getX(), to.getY()));
    }

    boolean isSearched(Position p) {
        for (int y = 0; y < backgroundHeight; y++) {
            for (int x = 0; x < backgroundWidth; x++) {
                if (MAP[x][y] == '@' && p.getX() == x && p.getY() == y) {
                    return true;
                }
            }
        }
        return false;
    }

    void genMap() {//生成网格地图

        MAP = new char[backgroundWidth][backgroundHeight];

        for (int y = 0; y < backgroundHeight; y++) {
            for (int x = 0; x < backgroundWidth; x++) {
                MAP[x][y] = 'W';
            }
        }

        for (SearchPath p : paths) {//路径
            if (p.getLength() > 0) {
                int x0 = min(p.getFrom().getX(), p.getTo().getX());
                int y0 = min(p.getFrom().getY(), p.getTo().getY());
                int x1 = max(p.getFrom().getX(), p.getTo().getX());
                int y1 = max(p.getFrom().getY(), p.getTo().getY());

                MAP[p.getFrom().getX()][p.getFrom().getY()] = '.';
                MAP[p.getTo().getX()][p.getTo().getY()] = '.';

                if (p.getFrom().getY() == p.getTo().getY()) {//水平线
                    for (int x = x0; x <= x1; x++) {
                        MAP[x][p.getFrom().getY()] = '.';
                    }
                } else if (p.getFrom().getX() == p.getTo().getX()) {//垂直线
                    for (int y = y0; y <= y1; y++) {
                        MAP[p.getFrom().getX()][y] = '.';
                    }
                } else {//斜线
                    for (int x = x0; x <= x1; x++) {
                        for (int y = y0; y <= y1; y++) {
                            if (intersection(p.getFrom().getX(), p.getFrom().getY(), p.getTo().getX(), p.getTo().getY(), x - 0.5, y - 0.5, x - 0.5 + 1, y - 0.5)) {
                                MAP[x][y] = '.';
                            } else if (intersection(p.getFrom().getX(), p.getFrom().getY(), p.getTo().getX(), p.getTo().getY(), x - 0.5, y - 0.5 + 1, x - 0.5 + 1, y - 0.5 + 1)) {
                                MAP[x][y] = '.';
                            } else if (intersection(p.getFrom().getX(), p.getFrom().getY(), p.getTo().getX(), p.getTo().getY(), x - 0.5, y - 0.5, x - 0.5, y - 0.5 + 1)) {
                                MAP[x][y] = '.';
                            } else if (intersection(p.getFrom().getX(), p.getFrom().getY(), p.getTo().getX(), p.getTo().getY(), x - 0.5 + 1, y - 0.5, x - 0.5 + 1, y - 0.5 + 1)) {
                                MAP[x][y] = '.';
                            }
                        }
                    }
                }
            }
        }

        MAP[from.getX()][from.getY()] = 'S';
        MAP[to.getX()][to.getY()] = 'E';

        START_PNT = new Point(from.getX(), from.getY());
        END_PNT = new Point(to.getX(), to.getY());
    }

    public boolean intersection(double l1x1, double l1y1, double l1x2, double l1y2,
                                double l2x1, double l2y1, double l2x2, double l2y2) {
        // 快速排斥实验 首先判断两条线段在 x 以及 y 坐标的投影是否有重合。 有一个为真，则代表两线段必不可交。
        if (Math.max(l1x1, l1x2) < Math.min(l2x1, l2x2)
                || Math.max(l1y1, l1y2) < Math.min(l2y1, l2y2)
                || Math.max(l2x1, l2x2) < Math.min(l1x1, l1x2)
                || Math.max(l2y1, l2y2) < Math.min(l1y1, l1y2)) {
            return false;
        }
        // 跨立实验  如果相交则矢量叉积异号或为零，大于零则不相交
        if ((((l1x1 - l2x1) * (l2y2 - l2y1) - (l1y1 - l2y1) * (l2x2 - l2x1))
                * ((l1x2 - l2x1) * (l2y2 - l2y1) - (l1y2 - l2y1) * (l2x2 - l2x1))) > 0
                || (((l2x1 - l1x1) * (l1y2 - l1y1) - (l2y1 - l1y1) * (l1x2 - l1x1))
                * ((l2x2 - l1x1) * (l1y2 - l1y1) - (l2y2 - l1y1) * (l1x2 - l1x1))) > 0) {
            return false;
        }
        return true;
    }

    /**
     * 打印地图
     */
    void printMap() {
        for (int y = 0; y < backgroundHeight; y++) {
            for (int x = 0; x < backgroundWidth; x++) {
                System.out.printf("%c ", MAP[x][y]);
            }
            System.out.printf("\n");
        }
        System.out.printf("\n");
    }

    /**
     * 搜索算法
     */
    void search() {
        final MinHeap heap = new MinHeap(); // 用最小堆来记录扩展的点
        final int[][] directs = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}}; // 可以扩展的四个方向

        heap.add(new Data(START_PNT, 0, 0, null)); // 把起始点放入堆
        Data lastData = null; // 找到的最后一个点的数据,用来反推路径

        for (boolean finish = false; !finish && !heap.isEmpty(); ) {
            final Data data = heap.getAndRemoveMin(); // 取出f值最小的点
            final Point point = data.point;
            if (MAP[point.x][point.y] == SPACE) // 将取出的点标识为已访问点
            {
                MAP[point.x][point.y] = VISITED;
            }

            for (int[] d : directs) // 遍历四个方向的点
            {
                final Point newPnt = new Point(point.x + d[0], point.y + d[1]);
                if (newPnt.x >= 0 && newPnt.x < backgroundWidth && newPnt.y >= 0 && newPnt.y < backgroundHeight) {
                    char e = MAP[newPnt.x][newPnt.y];
                    if (e == END) // 如果是终点,则跳出循环,不用再找
                    {
                        lastData = data;
                        finish = true;
                        break;
                    }
                    if (e != SPACE) // 如果不是空地,就不需要再扩展
                    {
                        continue;
                    }

                    final Data inQueueData = heap.find(newPnt);
                    if (inQueueData != null) // 如果在堆里,则更新g值
                    {
                        if (inQueueData.g > data.g + 1) {
                            inQueueData.g = data.g + 1;
                            inQueueData.parent = data;
                        }
                    } else // 如果不在堆里,则放入堆中
                    {
                        double h = h(newPnt);
                        Data newData = new Data(newPnt, data.g + 1, h, data);
                        heap.add(newData);
                    }
                }
            }
        }

        // 反向找出路径
        for (Data pathData = lastData; pathData != null; ) {
            Point pnt = pathData.point;
            if (MAP[pnt.x][pnt.y] == VISITED) {
                MAP[pnt.x][pnt.y] = ON_PATH;
            }
            pathData = pathData.parent;
        }
    }

    /**
     * h函数
     */
    double h(Point pnt) {
//		return hBFS(pnt);
        return hEuclidianDistance(pnt);
//		return hPowEuclidianDistance(pnt);
//		return hManhattanDistance(pnt);
    }

    /**
     * 曼哈顿距离,小于等于实际值
     */
    double hManhattanDistance(Point pnt) {
        return Math.abs(pnt.x - END_PNT.x) + Math.abs(pnt.y - END_PNT.y);
    }

    /**
     * 欧式距离,小于等于实际值
     */
    double hEuclidianDistance(Point pnt) {
        return Math.sqrt(Math.pow(pnt.x - END_PNT.x, 2) + Math.pow(pnt.y - END_PNT.y, 2));
    }

    /**
     * 欧式距离平方,大于等于实际值
     */
    double hPowEuclidianDistance(Point pnt) {
        return Math.pow(pnt.x - END_PNT.x, 2) + Math.pow(pnt.y - END_PNT.y, 2);
    }

    /**
     * BFS的h值,恒为0
     */
    double hBFS(Point pnt) {
        return 0;
    }
} // end of Whole Panel Class
