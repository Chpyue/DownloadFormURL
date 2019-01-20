import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * @author chpyue
 */
public class DownloadURLFile {


    /**
     * 通过URL获取文件名
     * @param url
     * @return
     */
    private static String getFileNameFormUrl(String url) {
        String name = new Long(System.currentTimeMillis()).toString() + ".X";
        Integer index = url.lastIndexOf("/");
        if(index>0){
            name = url.substring(index +1);
            if(name.trim().length()>0){
                return name;
            }
        }
        return name;
    }


    /**
     * 从网络Url中下载文件
     * @param urlStr
     * @param savePath
     * @throws IOException
     */
    public static void  downLoadFromUrl(String urlStr,String savePath) throws IOException{
        URL url = new URL(urlStr);
        String fileName = getFileNameFormUrl(urlStr);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();

        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //获取自己数组
        byte[] getData = readInputStream(inputStream);

        //文件保存位置
        File saveDir = new File(savePath);
        if(!saveDir.exists()){
            saveDir.mkdir();
        }
        File file = new File(saveDir+File.separator+fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if(fos!=null){
            fos.close();
        }
        if(inputStream!=null){
            inputStream.close();
        }


        System.out.println("info:"+url+" download success");

    }



    /**
     * 从输入流中获取字节数组
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    /**
     * 执行方法
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        //BufferedReader是可以按行读取文件
        FileInputStream inputStream = new FileInputStream("./urls.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String str = null;
        Integer times = -1;
        Integer i = 0;
        long startTime0=System.currentTimeMillis();   //获取开始时间

        while((str = bufferedReader.readLine()) != null && times!=0)
        {
            System.out.println(str);
            try {
                //伪代码
                long startTime=System.currentTimeMillis();   //获取开始时间
                downLoadFromUrl(str,"./pic");
                i++;
                long endTime=System.currentTimeMillis(); //获取结束时间
                System.out.println("NO."+i+"    download time： "+(endTime-startTime)+"ms      all time:"+(endTime-startTime0)+"ms");
            }catch (Exception e){
                System.out.println("Error");
            }
            times--;
        }
        long endTime0=System.currentTimeMillis(); //获取结束时间
        System.out.println("ALL TIME： "+(endTime0-startTime0)+"MS");;

        //close
        inputStream.close();
        bufferedReader.close();


        System.out.println("Hello World!");
    }
}

