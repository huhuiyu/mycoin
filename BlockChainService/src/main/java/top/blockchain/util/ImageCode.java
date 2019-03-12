package top.blockchain.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class ImageCode {
  private static final String CODES = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // 0123456789壹贰叁肆伍陆柒捌玖拾
  private static final Font FONT = new Font(null, Font.BOLD | Font.ITALIC, 28);
  private static final Random RANDOM = new Random();
  private static final int LENGTH = 5;
  private static final int MAX_LENGTH = 10;
  private static final int AMOUNT = 20;

  private static final FontMetrics FONT_METRICS;
  static {
    BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    Graphics graphics = image.getGraphics();
    graphics.setFont(FONT);
    FONT_METRICS = graphics.getFontMetrics();
  }

  public static String makeCode() {
    return makeCode(LENGTH);
  }

  public static String makeCode(int length) {
    length = length < LENGTH ? LENGTH : length;
    length = length > MAX_LENGTH ? MAX_LENGTH : length;
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < length; i++) {
      sb.append(CODES.charAt(RANDOM.nextInt(CODES.length())));
    }
    return sb.toString();
  }

  public static BufferedImage makeImage(String code) {
    int height = FONT_METRICS.getHeight(); // 绘图高度
    int width = FONT_METRICS.stringWidth(code); // 绘图宽度
    int ascent = FONT_METRICS.getMaxAscent();

    // 创建内存图片
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics graphics = image.getGraphics();
    graphics.setFont(FONT);
    // 绘制背景颜色
    graphics.setColor(Color.YELLOW);
    graphics.fillRect(0, 0, width, height);
    // 绘制文字
    graphics.setColor(Color.BLUE);
    graphics.drawString(code, 0, (FONT_METRICS.getHeight() / 2 + ascent / 2 - 2));
    // 绘制干扰线
    graphics.setColor(Color.BLACK);
    for (int i = 0; i < AMOUNT; i++) {
      int startx = RANDOM.nextInt(width);
      int starty = RANDOM.nextInt(height);
      int endx = RANDOM.nextInt(width);
      int endy = RANDOM.nextInt(height);
      graphics.drawLine(startx, starty, endx, endy);
    }
    return image;
  }

  public static void main(String[] args) {
    System.out.println(makeCode());
    System.out.println(makeCode(20));
    System.out.println(makeCode(3));

    try {
      ImageIO.write(makeImage(makeCode()), "jpeg", new FileOutputStream("test.jpg"));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("finish...");
  }
}
