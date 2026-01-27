import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Book> listBook = new ArrayList<>();
        Scanner x = new Scanner(System.in);

        String msg = """
                Chương trình quản lý sách:
                1. Thêm 1 cuốn sách
                2. Xóa 1 cuốn sách
                3. Thay đổi sách
                4. Xuất thông tin
                5. Tìm sách Lập trình
                6. Lấy sách tối đa theo giá
                7. Tìm kiếm theo tác giả
                0. Thoát
                Chọn chức năng: """;

        int chon = 0;
        do {
            System.out.printf(msg);
            chon = x.nextInt();

            switch (chon) {
                case 1 -> {
                    Book newBook = new Book();
                    newBook.input();
                    listBook.add(newBook);
                }

                case 2 -> {
                    System.out.print("Nhập vào mã sách cần xóa: ");
                    int bookid = x.nextInt();
                    Book find = listBook.stream()
                            .filter(p -> p.getId() == bookid)
                            .findFirst()
                            .orElseThrow();
                    listBook.remove(find);
                    System.out.println("Đã xóa sách thành công");
                }

                case 3 -> {
                    System.out.print("Nhập vào mã sách cần điều chỉnh: ");
                    int bookid = x.nextInt();
                    // Tìm sách
                    Book find = listBook.stream()
                            .filter(p -> p.getId() == bookid)
                            .findFirst()
                            .orElseThrow();
                    System.out.println("Nhập thông tin mới cho sách:");
                    find.input();
                    System.out.println("Đã cập nhật thành công!");
                }

                case 4 -> {
                    System.out.println("Xuất thông tin danh sách");
                    // Sử dụng Lambda expression để in
                    listBook.forEach(p -> p.output());
                }

                case 5 -> {
                    List<Book> list5 = listBook.stream()
                            .filter(u -> u.getTitle().toLowerCase().contains("lập trình"))
                            .toList(); // Java 16+ có thể dùng .toList() trực tiếp
                    list5.forEach(Book::output);
                }

                case 6 -> {
                    System.out.print("Nhập số lượng sách (K): ");
                    int k = x.nextInt();
                    System.out.print("Nhập mức giá trần (P): ");
                    long p = x.nextLong();

                    List<Book> list6 = listBook.stream()
                            .filter(b -> b.getPrice() <= p)
                            .limit(k)
                            .toList();
                    list6.forEach(Book::output);
                }

                case 7 -> {
                    x.nextLine(); // Xóa bộ nhớ đệm
                    System.out.print("Nhập các tác giả (cách nhau bởi dấu phẩy): ");
                    String inputAuthors = x.nextLine();
                    Set<String> authorSet = new HashSet<>(Arrays.asList(inputAuthors.split(",")));

                    List<Book> list7 = listBook.stream()
                            .filter(b -> authorSet.contains(b.getAuthor().trim()))
                            .toList();
                    list7.forEach(Book::output);
                }

                case 0 -> System.out.println("Thoát chương trình!");

                default -> System.out.println("Vui lòng chọn chức năng đúng!");
            }
        } while (chon != 0);
    }
}