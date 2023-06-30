package crawl;

public abstract class CrawlHelper {
	
	//implement method for checking data in table
	public static boolean workTimeCheck(String workTime) {
        return workTime.equalsIgnoreCase("Trị vì") ||
                workTime.equalsIgnoreCase("trị vì") ||
                workTime.equalsIgnoreCase("Tại vị") ||
                workTime.equalsIgnoreCase("Nhiệm kỳ") ||
                workTime.equalsIgnoreCase("Năm tại ngũ") ||
                workTime.equalsIgnoreCase("Hoạt động");
    }
	
	public static boolean fatherCheck(String father) {
        return father.equalsIgnoreCase("Thân phụ") ||
                father.equalsIgnoreCase("Cha");                
    }
	
	public static boolean motherCheck(String mother) {
        return mother.equalsIgnoreCase("Thân mẫu") ||
                mother.equalsIgnoreCase("Mẹ");
                
    }
	
	public static boolean parentCheck(String parent) {
        return parent.equalsIgnoreCase("Bố mẹ") ||
                parent.equalsIgnoreCase("Cha mẹ");
    }
	
	
	
	public static boolean birthCheck(String birth) {
        return birth.equalsIgnoreCase("Ngày sinh") ||
                birth.equalsIgnoreCase("Sinh");
    }
	
	public static boolean realNameCheck(String realName) {
        return realName.equalsIgnoreCase("Húy") ||
                realName.equalsIgnoreCase("Tên thật") ||
                realName.equalsIgnoreCase("tên thật") ||
                realName.equalsIgnoreCase("Tên đầy đủ") ||
                realName.equalsIgnoreCase("Tên húy");
    }
	
	public static boolean diedCheck(String died) {
		return died.equalsIgnoreCase("Mất") ||
				died.equalsIgnoreCase("Chết");
	}
	
	public static boolean eraCheck(String era) {
        return era.equalsIgnoreCase("Hoàng tộc") ||
                era.equalsIgnoreCase("Triều đại") ||
                era.equalsIgnoreCase("Gia tộc") ||
                era.equalsIgnoreCase("Kỷ nguyên");
    }

}