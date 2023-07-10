package helper;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileSystems;


public class ImagePath {
	
	public static String getImageAbsolutePath(String imageName) {
	    
		try {
			String imagePath =  "./src"+ "/application/image/" + imageName;
		    URL imageURL;
			imageURL = FileSystems.getDefault().getPath(imagePath)
			        .toUri().toURL();
			if (imageURL != null) {
		        return imageURL.toExternalForm();
		    } else {
		        return null; // Image not found
		    }
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return imageName;
	    
	    
	}

}
