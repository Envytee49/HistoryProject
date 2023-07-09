package helper;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileSystems;

public class URLCreator {
	public static URL createURLFromPath(String pathString){
		try {
            String passedInPath = "./src" + pathString;
            URL url = FileSystems.getDefault().getPath(passedInPath)
                    .toUri().toURL();
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
