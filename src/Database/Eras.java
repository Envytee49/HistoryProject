package Database;
import Model.Era;
import com.fasterxml.jackson.core.type.TypeReference;
import database.EntityData;
import database.JsonHelper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Eras implements database.DataManipulation {
    // This is the database for the entity - aggregation
    /* Tập hợp các đối tượng lớp Era đã được khởi tạo hoặc lấy từ các file json đều được lưu ở đây */
    public static database.EntityData<Era> collection = new EntityData();
    private JsonHelper<Era> json = new JsonHelper<>();

    /* Folder chứa toàn bộ file json ghi dữ liệu của đối tượng lớp Era */
    public final static String DIR_NAME = "\\Era";


    @Override
        public static void writeJSON(Era era){
            String fileName = DIR_NAME + "\\" + era.getId() + ".json";
            JsonHelper.writeJSON(fileName, era);
        }


    @Override
    public void queryJSON() {

            collection.setEntityData(json.queryJSON(DIR_NAME));
            collection.sortById();
    }

    @Override
    public void saveToJSON() {
        for(Era era : collection.getEntityData()) {
            era.save();
        }
    }
}
