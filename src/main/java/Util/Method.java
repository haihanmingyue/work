package Util;



import com.jfoenix.controls.JFXButton;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import java.awt.*;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Method {


    /**
     * 获取屏幕尺寸
     */
    private Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
    private float width = (float) screenSize.getWidth();
    private float height = (float) screenSize.getHeight();

    public  float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    /**
     *给按钮添加图片
     */
    public static void buttonTuBiao(String url,JFXButton button,double width,double height){
        Image image = new Image(url);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        //给按钮设置图标
        button.setGraphic(imageView);
    }

    /**
     * 登录界面checkbox选与不选的事件
     * */
    public static void remberusers(String username, String password){
        Properties prop = new Properties();
                try {
                    System.out.println("记住密码");
                    FileOutputStream oFile = new FileOutputStream("user.properties", false); //这里true表示追加,false会将原文件清空后,重新添加.
                    prop.setProperty("loginName", username);
                    prop.setProperty("passWord",password);
                    prop.setProperty("flag","1");//用以判断是否输出用户名密码
                    prop.store(oFile, null);
                    oFile.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

    public static void remberusers2(String username,String password){
        Properties prop = new Properties();
            try {
                System.out.println("取消记住密码");
                FileOutputStream oFile = new FileOutputStream("user.properties", false);
                prop.setProperty("loginName", username);
                prop.setProperty("passWord",password);
                prop.setProperty("flag","0");
                prop.store(oFile, null);
                oFile.close();
            }catch (Exception e) {
                e.printStackTrace();
            }

    }

    /**
     * 判断权限
     * */
    public static boolean admin(int admin){
    if(admin==1){
        return true;
    }else{
        return false;
    }
}

    /**
     *鼠标经过变成 手 的样子
     * */
    public static void hand(Button button){
        button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                button.setCursor(Cursor.HAND);
            }
        });

    }

    public static void hand(JFXButton button){
        button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                button.setCursor(Cursor.HAND);
            }
        });
    }

    /**
     * 判断名族
     * */
    public static boolean minzu(String string){
        boolean temp = false;
        String[] minzu = new String[]{"蒙古族","藏族","苗族","壮族","回族","维吾尔族","彝族","布依族","朝鲜族",
                "侗族","白族","哈尼族","傣族","傈僳族","畲族","拉祜族","满族","瑶族","土家族","哈萨克族","黎族",
                "佤族","高山族","水族","东乡族","景颇族","土族","仫佬族","布朗族", "毛南族","锡伯族","普米族",
                "纳西族","柯尔克孜族","达斡尔族","羌族","撒拉族","仡佬族","阿昌族","塔吉克族","怒族","俄罗斯族","德昂族",
                "裕固族","塔塔尔族","鄂伦春族","门巴族","基诺族","乌孜别克族","鄂温克族","保安族","京族","独龙族","赫哲族","珞巴族","汉族"};
        List<String> list = new ArrayList<>();
        for(String s : minzu){
            list.add(s);
        }
        if(list.contains(string)){
            temp = true;
        }else {
            System.out.println("没有这个民族");
        }
        System.out.println(list);
        return temp;
    }

    /**
     * 判断数字
     * */
    public static boolean isNumberic(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断字母
     * */
    public static boolean check(String str) {
        char c = str.charAt(0);
        if (((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断汉字
     * */
    public static boolean isChinese(String str) {
        String regEx = "[\u4e00-\u9fa5]";
        Pattern pat = Pattern.compile(regEx);
        Matcher matcher = pat.matcher(str);
        boolean flg = false;
        if (matcher.find())
            flg = true;
        return flg;
    }

    /**
     * 验证邮箱
     */
    public static boolean isEmail(String str) {
        String regex = "^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        return match(regex, str);
    }


    /**
     * 验证输入密码条件(必须包含大写英文与数字同时出现8-16位)
     */
    public static boolean IsPassword(String str) {
//        String regex = "^(?!([a-z]*|[A-Z]*|\\d*|[a-zA-Z]*|[a-z\\d]*|[A-Z\\d]*)$)[a-zA-Z\\d]{8,16}$";
//        return match(regex, str);
        return !isChinese(str) && str.length()>7 && str.length()<17;
    }

    /**
     * 验证输入手机号码
     */
    public static boolean IsTel(String str) {
        String regex = "^[1]+[3,5,9,8,7]+\\d{9}$";
        return match(regex, str);
    }


    private static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

}
