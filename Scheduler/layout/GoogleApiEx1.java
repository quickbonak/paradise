package layout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;

import javax.swing.ImageIcon;

public class GoogleApiEx1
{

    public GoogleApiEx1()
    {
    }

    public void downloadMap(String location, String mapZoom)
    {
        try
        {
        	String imageURL = (new StringBuilder("https://maps.googleapis.com/maps/api/staticmap?&key=AIzaSyDBswYlBU_WNI_ySYCeXyzwnStSuwHc9gk&center=")).append(URLEncoder.encode(location, "UTF-8")).append("&zoom=").append(mapZoom).append("&size=300x200&scale=1&markers=").append(URLEncoder.encode(location, "UTF-8")).toString();
        	URL url = new URL(imageURL);
            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(location);
            byte b[] = new byte[2048];
            int length;
            while((length = is.read(b)) != -1) 
            os.write(b, 0, length);
            is.close();
            os.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public ImageIcon getMap(String location)
    {
        return new ImageIcon((new ImageIcon(location)).getImage().getScaledInstance(300, 200, 1));
    }

    public void fileDelete(String fileName)
    {
        File f = new File(fileName);
        f.deleteOnExit();
        
    }
}
