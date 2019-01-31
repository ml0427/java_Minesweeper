package collection;

import java.util.Random;
import java.util.Scanner;

public class Michael_stepOnTheBombs {

	private static int bombsNumber = 5;

	public static void main(String[] args) {

		Scanner cin = new Scanner(System.in);

		System.out.print("請輸入寬度：");
		int width = Integer.parseInt(cin.nextLine().trim());
		System.out.print("請輸入高度：");
		int height = Integer.parseInt(cin.nextLine().trim());

		// 創造炸彈位置陣列
		String[][] bombsLsLs = new String[height][width];
		bombsLsLs = bombsScreen(height, width, bombsNumber, bombsLsLs);

		// 建立狀態(開地圖)
		boolean[][] statusLsLs = new boolean[height][width];

		// 使用者立旗
		boolean[][] flagLsLs = new boolean[height][width];

		for (int i = 0; i < statusLsLs.length; i++) {
			for (int j = 0; j < statusLsLs[i].length; j++) {
				statusLsLs[i][j] = false;
				flagLsLs[i][j] = false;
			}
		}

		// 創造畫面和更新畫面
		createAndUpdataScreen(height, width, bombsLsLs, statusLsLs, flagLsLs);

		// 計算炸彈數量
		countNumberOfBombs(bombsLsLs);

		System.out.println("輸入直行橫列，中間使用空白，例如要選擇第四行第二列，ex: 4 2");
		System.out.println("如果要插旗子，輸入p，ex: 4 2 p");
		System.out.print("請輸入直行橫列：");
		int inHeight, inWidth;

		while (cin.hasNext()) {

			// 使用者輸入
			String[] in = cin.nextLine().split(" ");

			inHeight = Integer.parseInt(in[1]);
			inWidth = Integer.parseInt(in[0]);

			if (in.length > 2 && "p".equals(in[2]) && statusLsLs[inHeight][inWidth] == false) {
				// 使用者立旗
				flagLsLs[inHeight][inWidth] = flagLsLs[inHeight][inWidth] ? false : true;
			} else {
				// 改變狀態
				statusLsLs[inHeight][inWidth] = true;
			}

			// 狀態檢查(開地圖)
			if(statusCheck(statusLsLs, bombsLsLs)) {
				createAndUpdataScreen(height, width, bombsLsLs, statusLsLs, flagLsLs);
				System.out.println("賽道的拉!");
				break;
			}
			// 更新畫面
			createAndUpdataScreen(height, width, bombsLsLs, statusLsLs, flagLsLs);
			// 檢查死亡
			if (statusLsLs[inHeight][inWidth] && !flagLsLs[inHeight][inWidth] && "*".equals(bombsLsLs[inHeight][inWidth])) {
				System.out.println("你已經死了!");
				break;
			}

			System.out.print("請輸入直行橫列：");
		}
		cin.close();
	}

	/**
	 * 狀態檢查(開地圖)
	 * 
	 * @param statusLsLs
	 * @param bombsLsLs
	 */
	public static boolean statusCheck(boolean[][] statusLsLs, String[][] bombsLsLs) {

		boolean isChecking = false;
		boolean goodGame = true;

		for (int i = 0; i < statusLsLs.length; i++) {
			for (int j = 0; j < statusLsLs[i].length; j++) {

				// 如果狀態未開，而且裡面不是炸彈的話
				if (statusLsLs[i][j] == false && !"*".equals(bombsLsLs[i][j])) {
					goodGame = false;
				}

				// 找到使用者輸入的位置
				if (statusLsLs[i][j] && "0".equals(bombsLsLs[i][j])) {
					// 如果不是在最上邊
					if (i != 0) {
						if (!"*".equals(bombsLsLs[i - 1][j]) && !statusLsLs[i - 1][j]) {
							statusLsLs[i - 1][j] = true;
							isChecking = true;
						}
					}
					// 如果不是在最下邊
					if (i != bombsLsLs.length - 1) {
						if (!"*".equals(bombsLsLs[i + 1][j]) && !statusLsLs[i + 1][j]) {
							// 下+1
							statusLsLs[i + 1][j] = true;
							isChecking = true;
						}
					}
					// 如果不是在最右邊
					if (j != bombsLsLs[i].length - 1) {
						if (!"*".equals(bombsLsLs[i][j + 1]) && !statusLsLs[i][j + 1]) {
							// 右+1
							statusLsLs[i][j + 1] = true;
							isChecking = true;
						}
					}
					// 如果不是在最左邊
					if (j != 0) {
						if (!"*".equals(bombsLsLs[i][j - 1]) && !statusLsLs[i][j - 1]) {
							// 左+1
							statusLsLs[i][j - 1] = true;
							isChecking = true;
						}
					}
					// 如果不是在最右下
					if (i != bombsLsLs.length - 1 && j != bombsLsLs[i].length - 1) {
						if (!"*".equals(bombsLsLs[i + 1][j + 1]) && !statusLsLs[i + 1][j + 1]) {
							// 右下+1
							statusLsLs[i + 1][j + 1] = true;
							isChecking = true;
						}
					}
					// 如果不是在最左下
					if (i != bombsLsLs.length - 1 && j != 0) {
						if (!"*".equals(bombsLsLs[i + 1][j - 1]) && !statusLsLs[i + 1][j - 1]) {
							// 左下+1
							statusLsLs[i + 1][j - 1] = true;
							isChecking = true;
						}
					}
					// 如果不是在最右上
					if (i != 0 && j != bombsLsLs[i].length - 1) {
						if (!"*".equals(bombsLsLs[i - 1][j + 1]) && !statusLsLs[i - 1][j + 1]) {
							// 右上+1
							statusLsLs[i - 1][j + 1] = true;
							isChecking = true;
						}
					}
					// 如果不是在最左上
					if (i != 0 && j != 0) {
						if (!"*".equals(bombsLsLs[i - 1][j - 1]) && !statusLsLs[i - 1][j - 1]) {
							// 左上+1
							statusLsLs[i - 1][j - 1] = true;
							isChecking = true;
						}
					}
				}
			}
		}
		if (isChecking)
			statusCheck(statusLsLs, bombsLsLs);

		return goodGame;
	}

	/**
	 * 計算炸彈數量
	 * 
	 * @param bombsLsLs
	 * @return
	 */
	public static String[][] countNumberOfBombs(String[][] bombsLsLs) {

		// 計算+1本炸彈左右邊的數字
		for (int i = 0; i < bombsLsLs.length; i++) {
			for (int j = 0; j < bombsLsLs[i].length; j++) {
				// 找到*的位置
				if ("*".equals(bombsLsLs[i][j])) {
					// 如果不是在最上邊
					if (i != 0) {
						if (!"*".equals(bombsLsLs[i - 1][j])) {
							// 上+1
							bombsLsLs[i - 1][j] = String.valueOf(Integer.parseInt(bombsLsLs[i - 1][j]) + 1);
						}
					}
					// 如果不是在最下邊
					if (i != bombsLsLs.length - 1) {
						if (!"*".equals(bombsLsLs[i + 1][j])) {
							// 下+1
							bombsLsLs[i + 1][j] = String.valueOf(Integer.parseInt(bombsLsLs[i + 1][j]) + 1);
						}
					}
					// 如果不是在最右邊
					if (j != bombsLsLs[i].length - 1) {
						if (!"*".equals(bombsLsLs[i][j + 1])) {
							// 右+1
							bombsLsLs[i][j + 1] = String.valueOf(Integer.parseInt(bombsLsLs[i][j + 1]) + 1);
						}
					}
					// 如果不是在最左邊
					if (j != 0) {
						if (!"*".equals(bombsLsLs[i][j - 1])) {
							// 左+1
							bombsLsLs[i][j - 1] = String.valueOf(Integer.parseInt(bombsLsLs[i][j - 1]) + 1);
						}
					}
					// 如果不是在最右下
					if (i != bombsLsLs.length - 1 && j != bombsLsLs[i].length - 1) {
						if (!"*".equals(bombsLsLs[i + 1][j + 1])) {
							// 右下+1
							bombsLsLs[i + 1][j + 1] = String.valueOf(Integer.parseInt(bombsLsLs[i + 1][j + 1]) + 1);
						}
					}
					// 如果不是在最左下
					if (i != bombsLsLs.length - 1 && j != 0) {
						if (!"*".equals(bombsLsLs[i + 1][j - 1])) {
							// 左下+1
							bombsLsLs[i + 1][j - 1] = String.valueOf(Integer.parseInt(bombsLsLs[i + 1][j - 1]) + 1);
						}
					}
					// 如果不是在最右上
					if (i != 0 && j != bombsLsLs[i].length - 1) {
						if (!"*".equals(bombsLsLs[i - 1][j + 1])) {
							// 右上+1
							bombsLsLs[i - 1][j + 1] = String.valueOf(Integer.parseInt(bombsLsLs[i - 1][j + 1]) + 1);
						}
					}
					// 如果不是在最左上
					if (i != 0 && j != 0) {
						if (!"*".equals(bombsLsLs[i - 1][j - 1])) {
							// 左上+1
							bombsLsLs[i - 1][j - 1] = String.valueOf(Integer.parseInt(bombsLsLs[i - 1][j - 1]) + 1);
						}
					}
				}
			}
		}
		return bombsLsLs;
	}

	/**
	 * 創造炸彈位置陣列
	 * 
	 * @param height
	 * @param width
	 * @param bombsNumber
	 * @param bombsLsLs
	 * @return
	 */
	public static String[][] bombsScreen(int height, int width, int bombsNumber, String[][] bombsLsLs) {

		int a, b;
		Random ran = new Random();
		for (int i = 0; i < bombsLsLs.length; i++) {
			for (int j = 0; j < bombsLsLs[i].length; j++) {
				bombsLsLs[i][j] = "0";
			}
		}
		for (int i = 0; i < bombsNumber; i++) {
			a = ran.nextInt(height);
			b = ran.nextInt(width);
			if ("*".equals(bombsLsLs[a][b])) {
				i--;
			} else {
				bombsLsLs[a][b] = "*";
			}
		}
		return bombsLsLs;
	}

	/**
	 * 創造畫面和更新畫面
	 * 
	 * @param height
	 * @param width
	 * @param bombsLs
	 * @param statusLsLs
	 * @return
	 */
	public static void createAndUpdataScreen(int height, int width, String[][] bombsLs, boolean[][] statusLsLs, boolean[][] flagLsLs) {

		String out;
		// 更新畫面
		for (int i = 0; i < 20; i++) {
			System.out.println();
		}

		// 畫出框線
		// 上框線
		System.out.print("    ");
		for (int i = 0; i < width; i++)
			System.out.print(i < 10 ? " " + i + " " : +i + " ");

		System.out.print("\n  ┌──");
		for (int i = 0; i < width; i++)
			System.out.print("───");
		System.out.println("┐");

		// 炸彈區
		for (int i = 0; i < statusLsLs.length; i++) {
			System.out.print(i < 10 ? " " + i + "│  " : i + "│  ");
			for (int j = 0; j < statusLsLs[i].length; j++) {
				out = statusLsLs[i][j] ? ("0".equals(bombsLs[i][j]) ? " " : bombsLs[i][j]) + "  " : "-  ";
				out = flagLsLs[i][j] ? "P  " : out;
				System.out.print(out);
			}
			System.out.println("│");
		}

		// 下框線
		System.out.print("  └──");
		for (int i = 0; i < width; i++)
			System.out.print("───");
		System.out.println("┘");
	}
}
