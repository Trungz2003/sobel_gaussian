package org.example;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Main {
    public static void main(String[] args) {
        Sobel();
        Gaussian();
    }

    public static void Sobel(){
        try {
            // Đọc ảnh từ file
            File input = new File("src/main/java/org/example/z5953212122388_60579d1c33cd144cbce8fba20eddff98.jpg"); // Đường dẫn tới ảnh của bạn
            BufferedImage image = ImageIO.read(input);

            // Tạo ảnh đầu ra với cùng kích thước
            BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

            // Toán tử Sobel
            int[][] sobelX = {
                    {-1, 0, 1},
                    {-2, 0, 2},
                    {-1, 0, 1}
            };

            int[][] sobelY = {
                    {-1, -2, -1},
                    {0,  0,  0},
                    {1,  2,  1}
            };

            // Áp dụng Sobel cho từng điểm ảnh
            for (int x = 1; x < image.getWidth() - 1; x++) {
                for (int y = 1; y < image.getHeight() - 1; y++) {
                    int pixelX = 0;
                    int pixelY = 0;

                    // Tính gradient theo cả X và Y
                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            int rgb = new Color(image.getRGB(x + i, y + j)).getRed();
                            pixelX += rgb * sobelX[i + 1][j + 1];
                            pixelY += rgb * sobelY[i + 1][j + 1];
                        }
                    }

                    // Tính độ lớn của gradient
                    int magnitude = (int) Math.sqrt((pixelX * pixelX) + (pixelY * pixelY));

                    // Chuẩn hóa giá trị về khoảng [0, 255]
                    magnitude = Math.min(255, Math.max(0, magnitude));

                    // Gán giá trị mới cho điểm ảnh
                    Color newColor = new Color(magnitude, magnitude, magnitude);
                    output.setRGB(x, y, newColor.getRGB());
                }
            }

            // Lưu ảnh đã xử lý
            File outputFile = new File("sobel_output_sobel.jpg");
            ImageIO.write(output, "jpg", outputFile);
            System.out.println("Đã hoàn thành xử lý ảnh và lưu kết quả vào 'sobel_output.jpg'");

        } catch (IOException e) {
            System.out.println("Lỗi khi đọc hoặc ghi ảnh: " + e.getMessage());
        }
    }

    public static void Gaussian(){
        try {
            // Đọc ảnh từ file
            File input = new File("src/main/java/org/example/z5953212122388_60579d1c33cd144cbce8fba20eddff98.jpg"); // Đường dẫn tới ảnh của bạn
            BufferedImage image = ImageIO.read(input);

            // Tạo ảnh đầu ra với cùng kích thước
            BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

            // Ma trận lọc Gaussian 5x5
            int[][] gaussianKernel = {
                    {0, 0, -1, 0, 0},
                    {0, -1, -2, -1, 0},
                    {-1, -2, 16, -2, -1},
                    {0, -1, -2, -1, 0},
                    {0, 0, -1, 0, 0}
            };

            // Áp dụng Laplace of Gaussian cho từng điểm ảnh
            for (int x = 2; x < image.getWidth() - 2; x++) {
                for (int y = 2; y < image.getHeight() - 2; y++) {
                    int pixelValue = 0;

                    // Tính toán với bộ lọc Gaussian kết hợp Laplace
                    for (int i = -2; i <= 2; i++) {
                        for (int j = -2; j <= 2; j++) {
                            int rgb = new Color(image.getRGB(x + i, y + j)).getRed();
                            pixelValue += rgb * gaussianKernel[i + 2][j + 2];
                        }
                    }

                    // Chuẩn hóa giá trị về khoảng [0, 255]
                    pixelValue = Math.min(255, Math.max(0, pixelValue));

                    // Gán giá trị mới cho điểm ảnh
                    Color newColor = new Color(pixelValue, pixelValue, pixelValue);
                    output.setRGB(x, y, newColor.getRGB());
                }
            }

            // Lưu ảnh đã xử lý
            File outputFile = new File("laplace_gaussian_output.jpg");
            ImageIO.write(output, "jpg", outputFile);
            System.out.println("Đã hoàn thành xử lý ảnh Laplace Gaussian và lưu kết quả vào 'laplace_gaussian_output.jpg'");

        } catch (IOException e) {
            System.out.println("Lỗi khi đọc hoặc ghi ảnh: " + e.getMessage());
        }
    }
}
