import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {
        GameProgress game1 = new GameProgress(24, 150, 3, 174.15);
        GameProgress game2 = new GameProgress(19, 370, 8, 254.32);
        GameProgress game3 = new GameProgress(78, 540, 12, 744.91);
        saveGame("D://Games//savegames//save1.dat", game1);
        saveGame("D://Games//savegames//save2.dat", game2);
        saveGame("D://Games//savegames//save3.dat", game3);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("D://Games//savegames//save1.dat");
        arrayList.add("D://Games//savegames//save2.dat");
        arrayList.add("D://Games//savegames//save3.dat");

        zipFiles("D://Games//savegames//zip.zip", arrayList);

        // удалите файлы сохранений, не лежащие в архиве.
        File game1Del = new File("D://Games//savegames//save1.dat");
        File game2Del = new File("D://Games//savegames//save2.dat");
        File game3Del = new File("D://Games//savegames//save3.dat");
        if (game1Del.delete()) System.out.println("Файл \"save1.dat\" удален");
        if (game2Del.delete()) System.out.println("Файл \"save2.dat\" удален");
        if (game3Del.delete()) System.out.println("Файл \"save3.dat\" удален");
    }

    private static void saveGame(String path, GameProgress game) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(game);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void zipFiles(String path, ArrayList<String> arrayList) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path))) {
            for (String arr : arrayList) {
                try (FileInputStream fis = new FileInputStream(arr)) {
                    ZipEntry entry = new ZipEntry(arr);
                    zout.putNextEntry(entry);
                    while (fis.available() > 0) {
                        zout.write(fis.read());
                    }
                    zout.closeEntry();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}